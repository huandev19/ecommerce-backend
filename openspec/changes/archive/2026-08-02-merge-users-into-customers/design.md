## Context

Hệ thống v8n-ecommerce hiện có 2 entity cho khách hàng:

- **`User`** (table `users`): Chứa thông tin xác thực (`password_hash`) và profile cơ bản. Nhiều field là `@Transient` (`phone`, `avatarUrl`, `status`, `emailVerified`, `lastLoginAt`) — không tồn tại trong DB.
- **`Customer`** (table `customers`): Chứa thông tin profile bổ sung (`phone`, `hasAccount`, `company`). Có `@Transient private User user` để giữ reference Java-level.

2 bảng được liên kết qua `email` (không có FK). `CustomerService` luôn query `User` trước rồi dùng email tìm `Customer`.

**Kiến trúc hiện tại:**

```
┌──────────────┐      ┌──────────────────┐
│  AuthService  │─────▶│  User (users)     │
│  register,    │      │  - passwordHash   │
│  login, JWT   │      │  - email          │
└──────────────┘      │  - firstName/last  │
                       │  - metadata        │
                       └────────┬───────────┘
                                │ email
┌──────────────────┐            │
│ CustomerService   │───────────▶│
│ profile, list     │            │
└─────────┬─────────┘   ┌────────▼──────────┐
          └─────────────▶│ Customer (customers)│
                         │ - phone, hasAccount │
                         └─────────────────────┘
```

**Entity hiện tại:**

### User (table `users`)
| Field | Type | DB Column? |
|---|---|---|
| id | UUID (BaseEntity) | ✅ |
| email | String | ✅ |
| passwordHash | String (TEXT) | ✅ |
| firstName | String | ✅ |
| lastName | String | ✅ |
| role | String | ✅ |
| roleId | UUID | ✅ |
| metadata | Map (jsonb) | ✅ |
| createdAt/updatedAt/deletedAt | (BaseEntity) | ✅ |
| phone | String | ❌ @Transient |
| avatarUrl | String | ❌ @Transient |
| status | UserStatus enum | ❌ @Transient |
| emailVerified | boolean | ❌ @Transient |
| lastLoginAt | LocalDateTime | ❌ @Transient |

### Customer (table `customers`)
| Field | Type | DB Column? |
|---|---|---|
| id | UUID (BaseEntity) | ✅ |
| user | User | ❌ @Transient |
| email | String | ✅ |
| firstName | String | ✅ |
| lastName | String | ✅ |
| phone | String | ✅ |
| hasAccount | boolean | ✅ |
| metadata | Map (jsonb) | ✅ |
| createdAt/updatedAt/deletedAt | (BaseEntity) | ✅ |
| company | String | ❌ @Transient |

## Goals / Non-Goals

**Goals:**
- Hợp nhất toàn bộ dữ liệu khách hàng vào một bảng `customers` duy nhất
- Thêm các cột `password_hash`, `status`, `avatar_url`, `email_verified`, `last_login_at` vào `customers`
- Xóa bảng `users` và tất cả code liên quan đến `User` entity
- `AuthService` hoạt động trực tiếp trên `Customer` entity
- `CustomerService` không còn phụ thuộc vào `UserRepository`
- Đảm bảo backward compatibility cho API authentication (login/register vẫn hoạt động)
- Migrate dữ liệu từ `users` sang `customers` an toàn, không mất dữ liệu

**Non-Goals:**
- Không thay đổi cấu trúc JWT token (vẫn dùng `userId` làm subject)
- Không thay đổi `UserAdmin` entity (admin auth vẫn dùng bảng `user_admins` riêng)
- Không thay đổi token blacklist mechanism
- Không thay đổi các module khác ngoài `identity`

## Decisions

### Decision 1: Gộp toàn bộ vào `customers`, xóa `users`

**Chọn:** Mở rộng `customers` với các cột từ `users`, sau đó xóa bảng `users`.

**Lý do:**
- `Customer` đã có sẵn các cột `phone`, `hasAccount` mà `User` thiếu
- `Customer` là entity "thật" hơn — các field của nó đều là DB columns, trong khi `User` có nhiều `@Transient`
- Ít phải thay đổi các module khác (Order, Cart, Address đều đã tham chiếu đến Customer, không tham chiếu đến User)

**Alternatives:**
- *Gộp vào `users` thay vì `customers`*: Không chọn vì `customers` đã có các cột thực tế hơn, và các module khác đã dùng Customer.
- *Giữ cả 2 bảng, thêm FK*: Không giải quyết được vấn đề trùng lặp dữ liệu.

### Decision 2: Customer entity mới

**Chọn:** Cấu trúc `Customer` sau khi merge:

```java
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    private String email;           // unique index
    private String passwordHash;    // nullable (guest customers)
    private String firstName;
    private String lastName;
    private String phone;
    private String avatarUrl;       // mới
    @Enumerated(EnumType.STRING)
    private CustomerStatus status;  // mới: ACTIVE, INACTIVE, BANNED
    private boolean emailVerified;  // mới
    private LocalDateTime lastLoginAt; // mới
    private boolean hasAccount;     // giữ nguyên
    private Map<String, Object> metadata;
    // @Transient company (giữ nguyên)
}
```

**Lý do:**
- `passwordHash` nullable để hỗ trợ guest checkout (customer không có tài khoản)
- `status` enum giúp quản lý trạng thái tài khoản
- `hasAccount` phân biệt giữa registered user và guest customer
- Giữ `company` là `@Transient` (không cần thiết lưu DB ở thời điểm này)

### Decision 3: Migrate dữ liệu qua email

**Chọn:** JOIN 2 bảng qua `email` để migrate dữ liệu trong Flyway script.

```sql
-- Thêm cột mới
ALTER TABLE customers ADD COLUMN password_hash TEXT;
ALTER TABLE customers ADD COLUMN status VARCHAR(20) DEFAULT 'ACTIVE';
ALTER TABLE customers ADD COLUMN avatar_url VARCHAR(500);
ALTER TABLE customers ADD COLUMN email_verified BOOLEAN DEFAULT FALSE;
ALTER TABLE customers ADD COLUMN last_login_at TIMESTAMP;

-- Migrate dữ liệu
UPDATE customers c SET
    password_hash = u.password_hash,
    first_name = COALESCE(c.first_name, u.first_name),
    last_name = COALESCE(c.last_name, u.last_name),
    has_account = true
FROM users u
WHERE c.email = u.email;

-- Tạo customer mới cho user chưa có customer tương ứng
INSERT INTO customers (id, email, password_hash, first_name, last_name, has_account, status, created_at, updated_at)
SELECT u.id, u.email, u.password_hash, u.first_name, u.last_name, true, 'ACTIVE', u.created_at, u.updated_at
FROM users u
WHERE u.email NOT IN (SELECT email FROM customers WHERE email IS NOT NULL);

-- Xóa bảng users
DROP TABLE IF EXISTS users CASCADE;
```

**Lý do:**
- `email` là natural key hiện tại để liên kết 2 bảng
- `COALESCE` ưu tiên dữ liệu từ `customers` (bảng đích)
- Tạo `Customer` mới cho `User` chưa có `Customer` tương ứng (phòng trường hợp data không đồng bộ)
- Giữ nguyên `id` từ `users` cho các customer mới tạo (để JWT token cũ vẫn hợp lệ)

### Decision 4: AuthService refactor

**Chọn:** Thay `UserRepository` bằng `CustomerRepository`, dùng `hasAccount = true` để phân biệt registered user.

**Register flow mới:**
```
AuthService.register(request)
  → customerRepository.existsByEmail(email)
  → Tạo Customer mới với hasAccount=true, passwordHash=encoded
  → customerRepository.save()
  → JWT với customer.getId()
```

**Login flow mới:**
```
AuthService.login(email, password)
  → customerRepository.findByEmail(email)
  → Kiểm tra hasAccount == true
  → passwordEncoder.matches(password, customer.getPasswordHash())
  → customer.setLastLoginAt(now)
  → JWT với customer.getId()
```

**Lý do:**
- `hasAccount` flag thay thế cho việc kiểm tra `passwordHash != null`
- Giữ nguyên logic JWT: `userId` trong token chính là `customer.id`

### Decision 5: Response DTO hợp nhất

**Chọn:** Gộp `UserResponse` + `CustomerResponse` → `CustomerResponse` mới.

```java
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponse {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phone;
    private String company;
    private String avatarUrl;
    private String status;       // từ User
    private boolean emailVerified; // từ User (nay đã có thật)
    private boolean hasAccount;  // từ Customer
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt; // từ User (nay đã có thật)
}
```

**Lý do:**
- Loại bỏ `userId` field (không còn tách biệt User và Customer)
- Thêm các field trước đây chỉ có trong `UserResponse`: `status`, `emailVerified`, `lastLoginAt`
- `UserResponse.java` sẽ bị xóa

## Risks / Trade-offs

| Risk | Mitigation |
|------|------------|
| **Mất dữ liệu**: Data không đồng bộ giữa `users` và `customers` | Flyway script xử lý cả 2 trường hợp: User có Customer và User không có Customer. Backup DB trước khi chạy migration. |
| **JWT token cũ không hợp lệ**: ID thay đổi với customer mới tạo | Dùng `users.id` làm `customers.id` cho các customer mới tạo từ user. |
| **Breaking API**: Response DTO thay đổi | Frontend cần cập nhật để dùng `CustomerResponse` mới. Thông báo trước cho team frontend. |
| **Rollback phức tạp**: Đã xóa bảng `users` | Flyway migration có rollback script (undo). Backup DB trước khi deploy. |
| **Ảnh hưởng đến test**: Unit test dùng `User` entity | Cập nhật tất cả test trong `CustomerServiceTest` và `AddressServiceTest`. |

## Migration Strategy

### Phase 1: Chuẩn bị (không downtime)
1. Tạo Flyway migration script thêm cột mới vào `customers`
2. Deploy code mới (vẫn giữ `users` table, nhưng code không dùng nữa)
3. Verify hệ thống hoạt động bình thường

### Phase 2: Migrate dữ liệu (short downtime hoặc zero-downtime)
1. Chạy migration script migrate data từ `users` → `customers`
2. Verify data integrity

### Phase 3: Dọn dẹp
1. Tạo Flyway script xóa bảng `users`
2. Xóa toàn bộ code `User.java`, `UserRepository.java`, `UserResponse.java`
3. Deploy lần cuối
