# DESIGN PATTERNS & ARCHITECTURE RULES — v8n-ecommerce Backend

Tài liệu này là bộ quy tắc bắt buộc mà mọi AI Agent và Developer phải tuân thủ khi viết code trong dự án Java Spring Boot `v8n-ecommerce`. Mọi thay đổi kiến trúc, thêm mới module, hoặc refactor phải dựa trên các pattern được định nghĩa dưới đây.

---

## 1. KIẾN TRÚC TỔNG THỂ: HEXAGONAL ARCHITECTURE (PORTS & ADAPTERS)

Dự án tuân thủ kiến trúc Hexagonal (Clean Architecture) với 4 layer rõ ràng trong mỗi module:

```
modules/<module-name>/src/main/java/com/v8n/modules/<module>/
├── domain/           # Core business logic - KHÔNG phụ thuộc vào bất kỳ framework nào
│   ├── entity/       # JPA Entities (@Entity)
│   ├── repository/   # Repository interfaces (Spring Data JPA)
│   ├── enums/        # Domain enums
│   └── service/      # Domain services (domain logic thuần, không annotation @Service)
├── application/      # Application services - Orchestration layer
│   ├── service/      # @Service, @Transactional - điều phối domain objects
│   ├── dto/          # Request/Response DTOs
│   └── mapper/       # Entity <-> DTO mappers
├── infrastructure/   # Adapters & external integrations
│   ├── security/     # JWT, Authentication filters
│   ├── config/       # @Configuration classes
│   └── persistence/  # Custom repository implementations (nếu có)
└── interfaces/       # REST Controllers (inbound adapters)
    └── rest/         # @RestController classes
```

### Quy tắc phụ thuộc (Dependency Rule)

```
interfaces → application → domain ← infrastructure
```

- `domain` **KHÔNG ĐƯỢC** import bất kỳ class nào từ `application`, `infrastructure`, `interfaces`.
- `application` **CHỈ ĐƯỢC** import từ `domain`.
- `infrastructure` **ĐƯỢC PHÉP** import từ `domain` và `application`.
- `interfaces` **ĐƯỢC PHÉP** import từ `application` và `domain`.

### Ví dụ đúng:

```java
// ✅ domain/entity/User.java - KHÔNG import gì từ application/infrastructure/interfaces
@Entity
@Table(name = "users")
public class User extends BaseEntity { ... }

// ✅ application/service/AuthService.java - CHỈ import từ domain
@Service
public class AuthService {
    private final UserRepository userRepository; // domain
    private final JwtTokenProvider jwtTokenProvider; // infrastructure
}

// ❌ SAI: domain import application
// domain/entity/User.java import application.dto.UserResponse → VI PHẠM
```

---

## 2. BASE ENTITY PATTERN (KẾ THỪA ENTITY CHUNG)

Tất cả Entity **PHẢI** kế thừa `BaseEntity` (trừ các entity đặc biệt như composite key tables).

```java
// BaseEntity cung cấp:
@MappedSuperclass
public abstract class BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; // Soft delete
}
```

### Quy tắc:
- Mọi entity chính (User, Product, Order, Customer, Address...) **PHẢI** extends `BaseEntity`.
- Composite key entities (RolePermission, UserAdminRole, UserAdminPermission) **KHÔNG** extends BaseEntity — chúng implements `Serializable` và dùng `@EmbeddableId`.
- Soft delete: Dùng `deleted_at` thay vì xóa cứng. Repository method dùng `findByIdNotDeleted()`.

---

## 3. REPOSITORY PATTERN (SPRING DATA JPA)

```java
// BaseRepository cung cấp các method dùng chung:
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, ID extends Serializable>
        extends JpaRepository<T, ID> {

    @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id AND e.deletedAt IS NULL")
    Optional<T> findByIdNotDeleted(@Param("id") ID id);

    @Query("SELECT e FROM #{#entityName} e WHERE e.deletedAt IS NULL")
    List<T> findAllNotDeleted();
}
```

### Quy tắc:
- Repository cho entity chính **PHẢI** extends `BaseRepository<T, UUID>`.
- Repository cho entity không có soft delete (Permission, Role) **ĐƯỢC PHÉP** extends `JpaRepository<T, ID>` trực tiếp.
- **KHÔNG** viết native query nếu không cần thiết. Ưu tiên `@Query` với JPQL hoặc Spring Data derived method names.
- Query method phải có tên rõ ràng, tuân thủ chuẩn Spring Data JPA.

### Ví dụ đúng:
```java
public interface AddressRepository extends BaseRepository<Address, UUID> {
    List<Address> findAllByCustomerId(UUID customerId);
    Optional<Address> findByCustomerIdAndDefaultShippingTrue(UUID customerId);
}

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    Optional<Permission> findByCode(String code);
}
```

---

## 4. DTO PATTERN (DATA TRANSFER OBJECT)

### Quy tắc phân loại:
| Hậu tố | Purpose | Ví dụ |
|--------|---------|-------|
| `*Request` | Input từ client (validation) | `RegisterRequest`, `LoginRequest`, `CreateUserAdminRequest` |
| `*Response` | Output trả về client | `AuthResponse`, `AdminUserResponse`, `CustomerResponse` |
| `*Dto` / `*DTO` | Internal (giữa các service) | Không khuyến khích, dùng Response/Request |

### Quy tắc:
- **Request DTOs**: Sử dụng `@Valid` annotation (jakarta.validation.constraints) cho validation.
- **Response DTOs**: Sử dụng **Lombok `@Builder`** pattern.
- **KHÔNG** để Entity lọt ra ngoài Controller — luôn map qua DTO.
- Mapper class đặt trong `application/mapper/`.

### Ví dụ Response DTO với Builder:
```java
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private long expiresIn;
    private UserResponse user;
}
```

### Ví dụ Request DTO với Validation:
```java
@Getter
@Setter
public class RegisterRequest {
    @NotBlank @Email
    private String email;

    @NotBlank @Size(min = 8, max = 100)
    private String password;

    @NotBlank @Size(max = 100)
    private String firstName;
}
```

---

## 5. BUILDER PATTERN

**Bắt buộc sử dụng Lombok `@Builder`** cho tất cả Response DTO và các object phức tạp.

### Quy tắc:
- Response DTOs: Luôn dùng `@Builder` + `@AllArgsConstructor` + `@NoArgsConstructor`.
- Trong Service, khi tạo response: **CHỈ** dùng builder pattern, KHÔNG dùng constructor hay setter.
- Builder có thể dùng cho cả internal objects (ví dụ: mail content, query parameters).

### Ví dụ bắt buộc:
```java
// ✅ ĐÚNG
return AuthResponse.builder()
    .accessToken(accessToken)
    .refreshToken(refreshToken)
    .tokenType("Bearer")
    .expiresIn(jwtTokenProvider.getAccessTokenExpiration() / 1000)
    .user(mapToUserResponse(user))
    .build();

// ❌ SAI
AuthResponse response = new AuthResponse();
response.setAccessToken(accessToken);
response.setRefreshToken(refreshToken);
return response;
```

---

## 6. API RESPONSE WRAPPER PATTERN

**Mọi response từ REST API phải được wrap trong `ApiResponse<T>`.**

```java
@Getter
@Builder
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
            .success(true).message("Success").data(data)
            .timestamp(LocalDateTime.now()).build();
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
            .success(true).message(message).data(data)
            .timestamp(LocalDateTime.now()).build();
    }

    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
            .success(false).message(message).data(null)
            .timestamp(LocalDateTime.now()).build();
    }
}
```

### Quy tắc:
- `200 OK` → `ResponseEntity.ok(ApiResponse.success(data))`
- `201 Created` → `ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(data))`
- `204 No Content` → `ResponseEntity.noContent().build()` (không wrap)
- `400/401/403/404/500` → Ném `BusinessException` — GlobalExceptionHandler tự wrap.

---

## 7. GLOBAL EXCEPTION HANDLING PATTERN

### Cấu trúc:
```
infrastructure/
└── exception/
    ├── GlobalExceptionHandler.java  # @RestControllerAdvice
    ├── BusinessException.java       # RuntimeException
    └── ErrorCode.java               # Enum các mã lỗi
```

### Quy tắc:
- **KHÔNG** try-catch trong Controller. Để Exception lan lên GlobalExceptionHandler.
- Mọi business error **PHẢI** throw `BusinessException(ErrorCode, String message)`.
- `ErrorCode` là enum chứa tất cả mã lỗi (đảm bảo tính nhất quán).

### Ví dụ ErrorCode:
```java
public enum ErrorCode {
    USER_NOT_FOUND("USR-001"),
    EMAIL_ALREADY_EXISTS("USR-002"),
    INVALID_CREDENTIALS("AUTH-001"),
    ACCESS_DENIED("AUTH-002"),
    ROLE_NOT_FOUND("RBAC-001"),
    RESOURCE_NOT_FOUND("GEN-001"),
    INVALID_REQUEST("GEN-002"),
    INSUFFICIENT_STOCK("INV-001"),
    // ...
}
```

### Ví dụ sử dụng:
```java
// ✅ ĐÚNG - trong Service
throw new BusinessException(ErrorCode.USER_NOT_FOUND);
throw new BusinessException(ErrorCode.INVALID_REQUEST, "Token đã hết hạn");

// ❌ SAI - trong Controller
try { ... } catch (Exception e) { return ResponseEntity.badRequest()... }
```

---

## 8. SERVICE LAYER PATTERN

### Phân loại Service:

| Loại | Annotation | Vị trí | Trách nhiệm |
|------|-----------|--------|-------------|
| Application Service | `@Service` | `application/service/` | Orchestration, transaction, security |
| Domain Service | (Plain class) | `domain/service/` | Pure business rules, no framework dependency |

### Quy tắc:
- Application Service **LUÔN** có `@Service` và `@Transactional` (readOnly = true mặc định, viết override).
- Mỗi Service chỉ phụ trách **MỘT** aggregate root (User, Order, Product...).
- **KHÔNG** gọi trực tiếp Repository từ Controller.
- **KHÔNG** để business logic trong Controller.
- Controller → Service → Repository (flow bắt buộc).

### Ví dụ:
```java
@Service
@Transactional(readOnly = true)
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // business logic + orchestration
    }
}
```

---

## 9. JWT SECURITY & FILTER CHAIN PATTERN

### Cấu trúc:
```
infrastructure/security/
├── JwtTokenProvider.java              # Tạo/validate JWT
├── JwtAuthenticationFilter.java       # OncePerRequestFilter
├── CustomAuthenticationEntryPoint.java # 401 handler
└── CustomAccessDeniedHandler.java     # 403 handler
```

### Quy tắc:
- JWT Filter **PHẢI** extends `OncePerRequestFilter` (đảm bảo chạy 1 lần/request).
- JWT Token chứa: `sub` (userId), `email`, `actor_type`, `permissions` (List<String>).
- Permissions trong JWT được parse thành `SimpleGrantedAuthority` để Spring Security kiểm tra.
- Access token expiration: mặt định **24h** (production phải ngắn hơn ~15-30 phút).
- Refresh token expiration: mặt định **7 ngày**.
- Sử dụng **HMAC-SHA256** (`Keys.hmacShaKeyFor`) để sign token.

### Ví dụ JWT Token Claims:
```json
{
  "sub": "550e8400-e29b-41d4-a716-446655440000",
  "email": "admin@v8n.com",
  "actor_type": "admin",
  "permissions": ["product:read", "product:write", "order:read"],
  "iat": 1717200000,
  "exp": 1717286400
}
```

---

## 10. RBAC AUTHORIZATION PATTERN (Role-Based Access Control)

### Quy tắc:
- Permission format: `<resource>:<operation>` (ví dụ: `product:read`, `order:write`).
- Wildcard `*:*` = Super Admin (full quyền, short-circuit).
- User permission = **Role Permissions ∪ User-level Permissions** (user-level chỉ mở rộng, không thu hẹp).
- Hard-sync khi update role permissions: Xóa toàn bộ `RolePermission` cũ, insert set mới.
- Không thể sửa/xóa System Roles (isSystem = true).
- Không thể tự sửa role hoặc deactivate chính mình.

### Entity Diagram:
```
UserAdmin ──┬── UserAdminRole ── Role ── RolePermission ── Permission
            │
            └── UserAdminPermission ── Permission (override / extra)
```

---

## 11. MẪU XỬ LÝ FORM VALIDATION

### Quy tắc:
- Sử dụng **Jakarta Bean Validation** annotations (`@NotBlank`, `@Email`, `@Size`, `@NotNull`, `@Valid`).
- Controller nhận `@Valid @RequestBody`.
- Business validation phức tạp (unique check, password strength...) → trong Service, throw `BusinessException`.

### Ví dụ:
```java
@PostMapping("/register")
public ResponseEntity<ApiResponse<AuthResponse>> register(
        @Valid @RequestBody RegisterRequest request) {
    // Validation tự động chạy trước khi vào method
    AuthResponse response = authService.register(request);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Registration successful", response));
}
```

---

## 12. SOFT DELETE PATTERN

### Quy tắc:
- **KHÔNG** xóa cứng dữ liệu người dùng (User, Address, Product, Order...).
- Soft delete: Set `deleted_at = NOW()`.
- Repository query mặc định filter `deleted_at IS NULL` (dùng `findByIdNotDeleted()`, `findAllNotDeleted()`).
- Entity hỗ trợ soft delete **PHẢI** extends `BaseEntity`.
- Khi "xóa": Gọi `repository.save(entity.setDeletedAt(LocalDateTime.now()))`.

### Ví dụ:
```java
// ✅ ĐÚNG
public void deactivateUser(String userId) {
    UserAdmin user = userAdminRepository.findById(userId)
        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    user.setIsActive(false);
    user.setDeletedAt(LocalDateTime.now());
    userAdminRepository.save(user);
}
```

---

## 13. LOGIN SECURITY & ACCOUNT LOCKING PATTERN

### Quy tắc:
- **Khóa tài khoản 15 phút** sau 5 lần đăng nhập sai liên tiếp.
- Theo dõi `failed_login_attempts` và `locked_until` trong `users_admin`.
- Mỗi lần đăng nhập thành công → reset `failed_login_attempts = 0`, `locked_until = NULL`.
- Ghi log lịch sử đăng nhập (`login_history`) với: email, status (SUCCESS/FAILED/LOCKED), IP, User-Agent, timestamp.
- Admin có thể mở khóa thủ công qua API `POST /admin/users/{id}/unlock`.

### Ví dụ logic trong Service:
```java
public AdminAuthResponse login(LoginRequest request, String ipAddress, String userAgent) {
    UserAdmin user = userAdminRepository.findByEmailActive(email)
        .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));

    // Check account lock
    if (user.isLocked()) {
        recordLoginHistory(user, email, LoginStatus.LOCKED, "Account locked", ipAddress, userAgent);
        throw new BusinessException(ErrorCode.INVALID_CREDENTIALS,
            "Tài khoản bị khóa đến " + user.getLockedUntil());
    }

    if (!passwordEncoder.matches(password, user.getPasswordHash())) {
        user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
        if (user.getFailedLoginAttempts() >= 5) {
            user.setLockedUntil(LocalDateTime.now().plusMinutes(15));
            recordLoginHistory(user, email, LoginStatus.LOCKED, "5 failed attempts", ipAddress, userAgent);
        } else {
            recordLoginHistory(user, email, LoginStatus.FAILED, "Wrong password", ipAddress, userAgent);
        }
        userAdminRepository.save(user);
        throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
    }

    // Success
    user.setFailedLoginAttempts(0);
    user.setLockedUntil(null);
    user.setLastLoginAt(LocalDateTime.now());
    userAdminRepository.save(user);
    recordLoginHistory(user, email, LoginStatus.SUCCESS, null, ipAddress, userAgent);
    return buildAdminAuthResponse(user);
}
```

---

## 14. MAPPER PATTERN

### Quy tắc:
- Entity → DTO mapping: Dùng **Mapper class riêng** trong `application/mapper/`.
- Mapper class đặt tên: `<Entity>Mapper` (ví dụ: `UserAdminMapper`, `RoleMapper`).
- Sử dụng **manual mapping** hoặc **MapStruct** (ưu tiên MapStruct nếu có nhiều field).
- **KHÔNG** viết mapping logic trong Service hoặc Controller.
- **KHÔNG** dùng `BeanUtils.copyProperties()` (không an toàn, khó debug).

### Ví dụ:
```java
@Component
public class UserAdminMapper {
    public AdminUserResponse toAdminUserResponse(UserAdmin user) {
        return AdminUserResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .phone(user.getPhone())
            .avatarUrl(user.getAvatarUrl())
            .isActive(user.getIsActive())
            .lockedUntil(user.getLockedUntil())
            .failedLoginAttempts(user.getFailedLoginAttempts())
            .roleIds(user.getRoles().stream().map(r -> r.getRole().getId()).collect(Collectors.toList()))
            .permissions(user.getEffectivePermissions())
            .lastLoginAt(user.getLastLoginAt())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
    }
}
```

---

## 15. DEPENDENCY INJECTION (DI) PATTERN

### Quy tắc:
- **Constructor Injection LÀ BẮT BUỘC**. KHÔNG dùng Field Injection (`@Autowired` trên field).
- Sử dụng Lombok `@RequiredArgsConstructor` để tự động generate constructor cho `final` fields.
- Tất cả dependencies phải là `private final`.

### Ví dụ:
```java
// ✅ ĐÚNG - Constructor Injection
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
}

// ❌ SAI - Field Injection
@Service
public class AuthService {
    @Autowired private UserRepository userRepository;
}
```

---

## 16. REST CONTROLLER PATTERN

### Quy tắc:
- Controller chỉ làm nhiệm vụ: **Nhận request → Gọi Service → Trả response**.
- **KHÔNG** có business logic, không gọi Repository trực tiếp.
- Endpoint naming: tuân thủ RESTful conventions.
  - `GET /api/resource` — list
  - `GET /api/resource/{id}` — detail
  - `POST /api/resource` — create
  - `PUT /api/resource/{id}` — full update
  - `PATCH /api/resource/{id}` — partial update
  - `DELETE /api/resource/{id}` — delete/deactivate
- Sử dụng `Principal` để lấy thông tin user hiện tại.
- Admin endpoints: `/admin/api/resource`.
- Storefront endpoints: `/storefront/api/resource`.

### Ví dụ:
```java
@RestController
@RequestMapping("/admin/api/users")
@RequiredArgsConstructor
public class UserAdminController {
    private final UserAdminService userAdminService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AdminUserResponse>>> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String status) {
        List<AdminUserResponse> users = userAdminService.listUsers(q, status);
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AdminUserResponse>> create(
            @Valid @RequestBody CreateUserAdminRequest request,
            Principal principal) {
        AdminUserResponse response = userAdminService.createUser(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }
}
```

---

## 17. MODULE ORGANIZATION (MULTI-MODULE GRADLE)

### Cấu trúc:
```
v8n-ecommerce/
├── modules/
│   ├── core/              # BaseEntity, BaseRepository, ApiResponse, GlobalExceptionHandler, ErrorCode
│   ├── identity/          # User, Auth, RBAC (UserAdmin, Role, Permission)
│   ├── catalog/           # Product, Category, Collection, Variant
│   ├── inventory/         # Inventory management
│   └── order/             # Order, Cart, Checkout
├── build.gradle           # Root
└── settings.gradle
```

### Quy tắc phụ thuộc giữa các module:
- `core` — KHÔNG phụ thuộc module nào.
- `identity` — depends on `core`.
- `catalog` — depends on `core`.
- `inventory` — depends on `core`, `catalog`.
- `order` — depends on `core`, `identity`, `catalog`, `inventory`.

### Quy tắc build.gradle mỗi module:
```groovy
dependencies {
    api project(':modules:core')               // nếu cần BaseEntity, BaseRepository
    implementation project(':modules:catalog') // nếu cần entity từ catalog
}
```

**KHÔNG** tạo circular dependency giữa các module.

---

## 18. NAMING CONVENTIONS — QUY TẮC ĐẶT TÊN TOÀN DIỆN

Áp dụng thống nhất **100%** trong toàn bộ dự án. Mọi PR vi phạm quy tắc đặt tên sẽ bị từ chối merge.

---

### 18.1 FILE NAMING (Tên tệp)

| Đối tượng | Quy tắc | Ví dụ |
|-----------|---------|-------|
| Java Class/Interface/Enum | **PascalCase**, tên file = tên class | `UserAdmin.java`, `AuthService.java`, `LoginStatus.java` |
| SQL Migration | **V`<number>`__`<mô_tả_ngắn_gọn>`.sql** (Flyway) | `V1__baseline.sql`, `V2__add_login_history.sql` |
| SQL Seeder | **`seed_<entity_name>`.sql** | `seed_permissions.sql`, `seed_roles.sql` |
| Properties/YAML | **kebab-case** | `application.yml`, `application-dev.yml`, `error-messages.properties` |
| Gradle | **`build.gradle`**, **`settings.gradle`** | Không đổi tên |
| Docker | **`Dockerfile`**, **`docker-compose.yml`** | Không đổi tên |
| CI/CD | **kebab-case** | `deploy-staging.yml`, `run-tests.yml` |

---

### 18.2 PACKAGE NAMING

| Quy tắc | Ví dụ |
|----------|-------|
| **lowercase**, dùng dấu chấm phân cấp | `com.v8n.modules.identity` |
| Package name = tên module (số ít) | `identity`, `catalog`, `inventory`, `order`, `core` |
| Sub-package trong module: tên layer | `domain`, `application`, `infrastructure`, `interfaces` |
| DTO package: `dto.request` và `dto.response` | `com.v8n.modules.identity.application.dto.request` |
| KHÔNG viết tắt trong package name | ❌ `dto.req` → ✅ `dto.request` |
| KHÔNG dùng ký tự đặc biệt, số, underscore | ❌ `my_package`, `package1` → ✅ `mypackage` |

**Cấu trúc package chuẩn:**
```
com.v8n.modules.<module>/
├── domain/
│   ├── entity/
│   ├── repository/
│   ├── enums/
│   └── service/          # Domain service (plain class, không @Service)
├── application/
│   ├── service/           # Application service (@Service)
│   ├── dto/
│   │   ├── request/       # *Request DTOs
│   │   └── response/      # *Response DTOs
│   └── mapper/            # Entity <-> DTO mappers
├── infrastructure/
│   ├── security/
│   ├── config/
│   ├── exception/
│   └── persistence/
└── interfaces/
    └── rest/              # @RestController
```

---

### 18.3 CLASS / INTERFACE / ENUM NAMING

| Thành phần | Hậu tố / Quy tắc | Ví dụ |
|------------|-------------------|-------|
| **Entity** | PascalCase, tên bảng dạng số ít | `UserAdmin`, `Product`, `ProductVariant`, `Role` |
| **Repository** | `<Entity>Repository` | `UserAdminRepository`, `ProductRepository` |
| **Application Service** | `<Entity>Service` hoặc `<Domain>Service` | `UserAdminService`, `AuthService`, `LoginHistoryService` |
| **Domain Service** | `<Domain>DomainService` | `ProductPricingDomainService` (nếu cần phân biệt) |
| **Controller (Admin)** | `<Entity>Controller` | `UserAdminController`, `RoleController` |
| **Controller (Storefront)** | `<Entity>Controller` (đặt trong module storefront) | `ProductController`, `CartController` |
| **Mapper** | `<Entity>Mapper` | `UserAdminMapper`, `RoleMapper` |
| **Request DTO** | `<Action><Entity>Request` | `CreateUserAdminRequest`, `UpdateRoleRequest`, `LoginRequest` |
| **Response DTO** | `<Entity>Response` hoặc `<Context>Response` | `AdminUserResponse`, `RoleDetailResponse`, `AuthResponse` |
| **Exception** | `<Mô_tả>Exception` | `BusinessException`, `ResourceNotFoundException` (nếu tách riêng) |
| **Enum** | PascalCase, mô tả trạng thái/hành động | `LoginStatus`, `UserStatus`, `OrderStatus`, `ErrorCode` |
| **Config** | `<Mô_tả>Config` | `SecurityConfig`, `CorsConfig`, `JpaConfig` |
| **Filter** | `<Mô_tả>Filter` | `JwtAuthenticationFilter` |
| **Provider/Util** | `<Mô_tả>Provider` / `<Mô_tả>Util` | `JwtTokenProvider`, `DateUtil` |

---

### 18.4 VARIABLE NAMING (Tên biến)

| Ngữ cảnh | Quy tắc | Ví dụ |
|----------|---------|-------|
| **Local variable & method param** | **camelCase** | `userId`, `emailAddress`, `orderItem`, `loginHistoryRepository` |
| **Instance field (private)** | **camelCase** | `private String accessToken;`, `private final UserRepository userRepository;` |
| **Static final constant** | **UPPER_SNAKE_CASE** | `private static final int MAX_LOGIN_ATTEMPTS = 5;`, `public static final String DEFAULT_ROLE = "ADMIN";` |
| **Boolean variable** | Bắt đầu bằng `is`, `has`, `can`, `should` | `isActive`, `hasPermission`, `canEdit`, `shouldNotify` |
| **Collection variable** | Dùng danh từ số nhiều hoặc hậu tố `List`/`Map`/`Set` | `users`, `permissionList`, `roleMap`, `productIds` |
| **UUID / ID** | `<entity>Id` | `userId`, `productId`, `roleId` |
| **Tên biến quá ngắn** | ❌ CẤM dùng 1 ký tự (trừ vòng lặp `i`, `j`, `k`) | ❌ `u`, `p` → ✅ `user`, `product` |

**Ví dụ:**
```java
// ✅ ĐÚNG
private static final int MAX_RETRY_ATTEMPTS = 3;
private final UserAdminRepository userAdminRepository;
private String refreshToken;
private List<UserAdminResponse> adminUsers;
private boolean isEmailVerified;
UUID userId = UUID.fromString("...");

// ❌ SAI
private static final int maxRetryAttempts = 3;  // Không UPPER_SNAKE_CASE cho constant
private final UserAdminRepository repo;           // Viết tắt
private String rt;                                // Tên quá ngắn
private boolean emailVerified;                    // Thiếu prefix "is"
List<UserAdminResponse> ua;                       // Tên quá ngắn, không rõ nghĩa
```

---

### 18.5 METHOD / FUNCTION NAMING (Tên phương thức)

| Loại method | Quy tắc | Ví dụ |
|-------------|---------|-------|
| **Public method** | **camelCase**, động từ + danh từ | `createUser()`, `findUserById()`, `deactivateUser()` |
| **Private helper** | **camelCase**, mô tả hành động | `buildAuthResponse()`, `mapToEntity()`, `validateEmailUniqueness()` |
| **Repository query method** | Spring Data JPA convention: `find...By...` / `count...By...` / `exists...By...` | `findByEmailIgnoreCase()`, `countByStatus()`, `existsByEmail()` |
| **Boolean return method** | Bắt đầu bằng `is`, `has`, `can`, `should` | `isLocked()`, `hasPermission()`, `canDelete()` |
| **Getter/Setter** | Lombok `@Getter`/`@Setter` — không viết tay | Trừ khi có logic đặc biệt |
| **Static factory method** | `of()`, `from()`, `create()`, `success()`, `error()` | `ApiResponse.success(data)`, `ErrorCode.from(code)` |

**Động từ chuẩn theo CRUD:**

| Operation | Tiền tố method | Ví dụ |
|-----------|----------------|-------|
| **Create** | `create`, `register`, `add` | `createUser()`, `register()`, `addItemToCart()` |
| **Read (single)** | `get`, `find`, `retrieve` | `getUserById()`, `findByEmail()`, `retrieveOrder()` |
| **Read (list)** | `list`, `getAll`, `findAll`, `search` | `listUsers()`, `searchProducts()` |
| **Update** | `update`, `modify`, `change` | `updateUser()`, `changePassword()` |
| **Delete/Deactivate** | `delete`, `remove`, `deactivate`, `archive` | `deleteAddress()`, `deactivateUser()` |

**Ví dụ đúng/sai:**
```java
// ✅ ĐÚNG - Repository
Optional<UserAdmin> findByEmailIgnoreCase(String email);
List<UserAdmin> findAllByStatus(UserStatus status);
boolean existsByEmail(String email);
long countByDeletedAtIsNull();

// ✅ ĐÚNG - Service
public AdminUserResponse createUser(CreateUserAdminRequest request);
public List<AdminUserResponse> listUsers(String q, String status);
public void deactivateUser(UUID userId);

// ❌ SAI
public AdminUserResponse user_create(CreateUserAdminRequest r);     // snake_case, tên param tối nghĩa
public List<AdminUserResponse> get_all();                          // snake_case
```

---

### 18.6 ROUTER / ENDPOINT NAMING (Tên API route)

| Quy tắc | Mô tả | Ví dụ |
|----------|-------|-------|
| **lowercase, kebab-case** | URL toàn chữ thường, dấu gạch ngang | `/admin/api/login-history` |
| **Danh từ số nhiều** cho resource | collection → plural | `/users`, `/products`, `/roles` |
| **Danh từ số ít** cho singleton | resource đơn → singular | `/auth`, `/me`, `/profile` |
| **Tiền tố phân vùng** | `/{actor}/api/{resource}` | `/admin/api/users`, `/storefront/api/products` |
| **Nested resource** | `/{parent}/{parentId}/{child}` | `/admin/api/products/{id}/variants` |
| **Action không CRUD** | Động từ ở cuối URL | `/admin/api/users/{id}/unlock`, `/admin/api/users/{id}/deactivate` |
| **Query param** | filter, search, sort, pagination | `?q=search&status=active&page=1&size=20&sort=createdAt,desc` |
| **Path variable** | `{id}` cho UUID, `{slug}` cho slug | `/admin/api/users/{id}`, `/storefront/api/products/{slug}` |

**Mapping HTTP Method → Endpoint:**

| HTTP Method | Endpoint Pattern | Purpose | Ví dụ |
|-------------|-----------------|---------|-------|
| `GET` | `/admin/api/{resources}` | List (có phân trang) | `GET /admin/api/users?page=1&size=20` |
| `GET` | `/admin/api/{resources}/{id}` | Detail | `GET /admin/api/users/550e8400-...` |
| `POST` | `/admin/api/{resources}` | Create | `POST /admin/api/users` |
| `PUT` | `/admin/api/{resources}/{id}` | Full update | `PUT /admin/api/users/550e8400-...` |
| `PATCH` | `/admin/api/{resources}/{id}` | Partial update | `PATCH /admin/api/users/550e8400-...` |
| `DELETE` | `/admin/api/{resources}/{id}` | Delete/Deactivate | `DELETE /admin/api/users/550e8400-...` |
| `POST` | `/admin/api/{resources}/{id}/{action}` | Special action | `POST /admin/api/users/550e8400-.../unlock` |

**Quy tắc cấm trong URL:**
- ❌ **KHÔNG** dùng uppercase: `/admin/api/Users`
- ❌ **KHÔNG** dùng snake_case: `/admin/api/login_history`
- ❌ **KHÔNG** dùng camelCase: `/admin/api/loginHistory`
- ❌ **KHÔNG** có động từ trong URL CRUD (trừ action đặc biệt): ❌ `/getUsers`, ❌ `/createUser`
- ❌ **KHÔNG** để trailing slash: ❌ `/admin/api/users/`

**Ví dụ bộ endpoint cho một resource:**
```
GET    /admin/api/roles                          # List roles
GET    /admin/api/roles/{id}                     # Detail role
POST   /admin/api/roles                          # Create role
PUT    /admin/api/roles/{id}                     # Full update role
DELETE /admin/api/roles/{id}                     # Delete role (soft)
GET    /admin/api/roles/{id}/permissions         # List permissions of role
PUT    /admin/api/roles/{id}/permissions         # Sync permissions for role
GET    /admin/api/permissions                    # List all permissions (reference data)
POST   /admin/api/users/{id}/unlock              # Unlock user account
```

---

### 18.7 TABLE & COLUMN NAMING (Cơ sở dữ liệu)

| Đối tượng | Quy tắc | Ví dụ |
|-----------|---------|-------|
| **Table name** | **snake_case, số nhiều** | `users_admin`, `products`, `order_items`, `login_history` |
| **Primary Key** | `id` (UUID) | `id UUID PRIMARY KEY` |
| **Foreign Key** | `<bảng_số_ít>_id` | `user_id`, `role_id`, `product_id`, `created_by_id` |
| **Column** | **snake_case, số ít** | `first_name`, `password_hash`, `failed_login_attempts`, `deleted_at` |
| **Timestamp column** | `<hành_động>_at` | `created_at`, `updated_at`, `deleted_at`, `last_login_at`, `locked_until` |
| **Boolean column** | `<trạng_thái>` hoặc `is_<trạng_thái>` | `is_active`, `is_system`, `is_default` |
| **Index** | `idx_<table>_<column>` | `idx_users_admin_email`, `idx_login_history_user_id` |
| **Unique constraint** | `uq_<table>_<column>` | `uq_users_admin_email`, `uq_permissions_code` |
| **Foreign Key constraint** | `fk_<table>_<ref_table>` | `fk_user_admin_roles_user_id`, `fk_user_admin_roles_role_id` |
| **Composite PK table** | snake_case, mô tả mối quan hệ | `user_admin_roles`, `role_permissions`, `user_admin_permissions` |
| **Enum value (DB)** | **UPPER_SNAKE_CASE** | `'ACTIVE'`, `'DEACTIVATED'`, `'PENDING'`, `'SUCCESS'`, `'FAILED'`, `'LOCKED'` |

**Ví dụ CREATE TABLE đúng chuẩn:**
```sql
CREATE TABLE users_admin (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100),
    phone VARCHAR(20),
    avatar_url VARCHAR(500),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    failed_login_attempts INT NOT NULL DEFAULT 0,
    locked_until TIMESTAMP,
    last_login_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    deleted_at TIMESTAMP,
    CONSTRAINT uq_users_admin_email UNIQUE (email)
);
```

---

### 18.8 CONFIGURATION & PROPERTIES NAMING

| Ngữ cảnh | Quy tắc | Ví dụ |
|----------|---------|-------|
| **application.yml key** | **kebab-case**, phân cấp bằng `.` (YAML indent) | `app.jwt.access-token-expiration`, `spring.datasource.url` |
| **Environment variable** | **UPPER_SNAKE_CASE** | `JWT_SECRET_KEY`, `DB_PASSWORD`, `REDIS_HOST`, `APP_CORS_ALLOWED_ORIGINS` |
| **Spring property** | tuân thủ Spring convention | `spring.jpa.hibernate.ddl-auto`, `server.port` |
| **Custom app property** | prefix `app.` + mô tả | `app.jwt.secret-key`, `app.cors.allowed-origins`, `app.upload.max-file-size` |

**Ví dụ application.yml:**
```yaml
app:
  jwt:
    secret-key: ${JWT_SECRET_KEY}
    access-token-expiration: 86400000
    refresh-token-expiration: 604800000
  cors:
    allowed-origins: ${APP_CORS_ALLOWED_ORIGINS:http://localhost:3000}
```

---

### 18.9 TỔNG KẾT CASE STYLES

| Style | Sử dụng cho | Ví dụ |
|-------|-------------|-------|
| **PascalCase** | Class, Interface, Enum, Annotation | `UserAdminController`, `LoginStatus` |
| **camelCase** | Method, Variable, Parameter | `createUser()`, `userId`, `firstName` |
| **UPPER_SNAKE_CASE** | Static final constant, Enum value (DB), Env variable | `MAX_LOGIN_ATTEMPTS`, `JWT_SECRET_KEY` |
| **snake_case** | Table name, Column name, SQL objects | `users_admin`, `first_name` |
| **kebab-case** | URL endpoint, File name (non-Java), YAML keys | `/admin/api/login-history`, `application-dev.yml` |
| **lowercase** | Package name | `com.v8n.modules.identity` |

---

## 19. TESTING PATTERN

### Quy tắc:
- Unit test: JUnit 5 + Mockito. Test Service layer bằng mock repository.
- Test class đặt trong `src/test/java/` mirror structure của `src/main/java/`.
- Test method naming: `test<Method>_<Scenario>_<ExpectedBehavior>()`.
- Sử dụng `@ExtendWith(MockitoExtension.class)` cho unit test Service.
- **KHÔNG** mock value objects, **CHỈ** mock external dependencies (repository, external APIs).

### Ví dụ:
```java
@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
    @Mock private UserRepository userRepository;
    @Mock private CustomerRepository customerRepository;
    @Mock private AddressRepository addressRepository;
    @InjectMocks private AddressService addressService;

    @Test
    void testDeleteAddress_softDelete_setsDeletedAt() {
        when(userRepository.findByIdNotDeleted(userId)).thenReturn(Optional.of(mockUser));
        when(customerRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockCustomer));
        when(addressRepository.findByIdAndCustomerId(addressId, customerId)).thenReturn(Optional.of(mockAddress));

        addressService.deleteAddress(addressId, userId);

        assertNotNull(mockAddress.getDeletedAt());
        verify(addressRepository).save(mockAddress);
    }
}
```

---

## 20. CROSS-CUTTING CONCERNS CHECKLIST

Khi tạo bất kỳ feature mới, **PHẢI** kiểm tra các điểm sau:

- [ ] **Layer đúng**: Entity ở domain, Service ở application, Controller ở interfaces.
- [ ] **DTO mapping**: Không leak Entity ra ngoài Controller.
- [ ] **Builder pattern**: Response tạo bằng `.builder()`, không dùng setter.
- [ ] **ApiResponse wrap**: Tất cả response được wrap trong `ApiResponse<T>`.
- [ ] **Exception handling**: Throw `BusinessException`, không try-catch trong Controller.
- [ ] **Validation**: Jakarta Bean Validation trên Request DTO.
- [ ] **Constructor Injection**: `@RequiredArgsConstructor` + `private final`.
- [ ] **Soft delete**: Entity extends `BaseEntity`, dùng `findByIdNotDeleted()`.
- [ ] **Security**: Endpoint có `@PreAuthorize` hoặc filter theo role/permission.
- [ ] **Transaction**: `@Transactional` trên application service method ghi DB.
- [ ] **Logging**: Dùng `log.info()` / `log.error()` (SLF4J) cho các hành động quan trọng.
- [ ] **Test**: Unit test cho Service, mock repository.

---

*Cập nhật lần cuối: 2026-07-01*
*Version: 1.0.0*