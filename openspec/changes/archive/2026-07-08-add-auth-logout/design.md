## Context

Hệ thống v8n-ecommerce sử dụng kiến trúc JWT stateless với Spring Security (`SessionCreationPolicy.STATELESS`). Hiện tại có 2 luồng auth:
- **Customer/Store User Auth**: [`AuthController`](../../../../../modules/identity/src/main/java/com/v8n/modules/identity/interfaces/rest/AuthController.java:25) tại `/api/v1/auth`, entity [`User`](../../../../../modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/User.java:22) (table `users`)
- **Admin Auth**: [`AdminAuthController`](../../../../../modules/identity/src/main/java/com/v8n/modules/identity/interfaces/rest/AdminAuthController.java:24) tại `/api/v1/auth/admin`, entity [`UserAdmin`](../../../../../modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/UserAdmin.java:25) (table `user_admins`)

Cả 2 đều thiếu endpoint logout. Token JWT được tạo bởi [`JwtTokenProvider`](../../../../../modules/identity/src/main/java/com/v8n/modules/identity/infrastructure/security/JwtTokenProvider.java:27), validate bởi [`JwtAuthenticationFilter`](../../../../../modules/identity/src/main/java/com/v8n/modules/identity/infrastructure/security/JwtAuthenticationFilter.java:26). Không có cơ chế revoke token.

Project chưa có Redis dependency — đây là dependency mới cần thêm.

## Goals / Non-Goals

**Goals:**
- Cung cấp endpoint `POST /api/v1/auth/logout` cho customer user (self-service)
- Cung cấp endpoint `POST /api/v1/auth/admin/logout` cho admin user (self-service)
- Cung cấp endpoint `POST /api/v1/users/{id}/revoke-tokens` — admin force-revoke admin user
- Cung cấp `GET /api/v1/admin/customers` — admin list customer users (search, pagination)
- Cung cấp `POST /api/v1/admin/customers/{id}/revoke-tokens` — admin force-revoke customer
- Revoke cả access token và refresh token khi logout
- Kiểm tra token blacklist trong `JwtAuthenticationFilter` trước mỗi request authenticated
- Lưu token đã revoke vào DB (PostgreSQL) để audit và lịch sử
- Cache blacklist trong Redis để lookup nhanh (sub-millisecond)
- Hỗ trợ revoke tất cả token của một user (force logout all sessions)
- Audit trail cho tất cả hành động force-revoke (ghi lại admin nào đã force-revoke user nào)

**Non-Goals:**
- Không thay đổi cơ chế JWT signing/key hiện tại
- Không thay đổi login flow
- Không thay đổi cách refresh token hoạt động
- Không implement OAuth2/SSO
- Không implement full CRUD customer (chỉ list + revoke tokens)

## Decisions

### Decision 1: Token Blacklist Pattern (DB + Redis) over Blacklist-only-in-DB

- **Chọn**: DB (PostgreSQL) + Redis cache
- **Lý do**:
  - Redis đảm bảo latency check < 1ms mỗi request, không ảnh hưởng performance API
  - DB lưu lịch sử logout phục vụ audit, revoke hàng loạt, và phục hồi khi Redis down
  - Redis TTL tự động expire entries khi token hết hạn, không cần cleanup job cho cache
  - DB có thể có scheduled job cleanup định kỳ
- **Alternatives cân nhắc**:
  - *DB-only*: Mỗi request authenticated phải query DB → latency cao, tăng load DB
  - *Redis-only*: Mất dữ liệu khi Redis restart, không có audit trail

### Decision 2: Revoke cả Access Token lẫn Refresh Token

- **Chọn**: Cho phép revoke cả access token và refresh token
- **Lý do**: Access token ngắn hạn (1h) nhưng nếu bị lộ vẫn có thể dùng trong 1h. Revoke cả 2 đảm bảo logout hoàn toàn.
- **Cache strategy**: Chỉ cache revoke entries cho đến khi token hết hạn (TTL = thời gian sống còn lại của token)
  - Access token: cache 1h
  - Refresh token: cache 7 ngày
- **Flow logout**:
  1. Client gửi `Authorization: Bearer <access_token>` và body `{ "refreshToken": "<refresh_token>" }`
  2. Server lưu cả 2 token vào `revoked_tokens` table + Redis
  3. Redis set với TTL = thời gian hết hạn của token
  4. Response 200 OK

### Decision 3: Kiến trúc Check Blacklist

- **Chọn**: Mở rộng `JwtAuthenticationFilter` hiện tại, thêm `TokenBlacklistService` dependency
- **Flow**:
  1. `JwtAuthenticationFilter.doFilterInternal()` extract JWT như hiện tại
  2. Trước khi set authentication, gọi `tokenBlacklistService.isRevoked(token)`
  3. `TokenBlacklistService` check Redis trước (O(1))
  4. Nếu không có trong Redis, fallback check DB (phòng Redis down)
  5. Nếu revoked → không set authentication, response 401
- **Lý do**: Tận dụng filter chain hiện có, minimal code change

### Decision 4: Entity Design cho RevokedToken

```
revoked_tokens
├── id            UUID (PK)
├── token_hash    VARCHAR(64) NOT NULL — SHA-256 hash của token (không lưu plaintext)
├── token_type    VARCHAR(10) NOT NULL — 'access' | 'refresh'
├── user_id       VARCHAR(36) — UUID của user (nullable, để revoke all)
├── user_type     VARCHAR(10) — 'customer' | 'admin'
├── revoked_at    TIMESTAMP NOT NULL
├── expires_at    TIMESTAMP NOT NULL — thời gian hết hạn gốc của token
├── reason        VARCHAR(50) — 'logout' | 'revoke_all' | 'security'
└── ip_address    VARCHAR(45) — IP thực hiện logout
```

- **Không lưu JWT plaintext** vì lý do bảo mật, chỉ lưu SHA-256 hash
- Index trên `(token_hash)` cho lookup nhanh
- Index trên `(user_id, user_type)` cho revoke all

### Decision 5: Redis Key Design

```
blacklist:{sha256_hash} → "1"  (SET)
  TTL = expires_at - now (giây)
```

Không cần value phức tạp, chỉ cần key tồn tại để biết token đã revoked.

### Decision 6: Admin Force-Logout Flow

- **Chọn**: 2 controller riêng biệt cho admin force-logout, mỗi controller xử lý 1 loại user
- **Chi tiết**:

**1. Force-revoke Admin User** — mở rộng [`UserAdminController`](../../../../../modules/identity/src/main/java/com/v8n/modules/identity/interfaces/rest/UserAdminController.java:32) hiện tại:
```
POST /api/v1/users/{id}/revoke-tokens
@PreAuthorize("hasAuthority('user:update')")
→ userAdminService.revokeTokens(id, adminId)
  → tokenBlacklistService.revokeAllForUser(userId, "admin", "admin_force_logout")
  → ghi login_history với status=LOGOUT, failureReason="FORCE_LOGOUT_BY_ADMIN"
```

**2. List & Force-revoke Customer** — tạo controller mới [`AdminCustomerController`](modules/identity/src/main/java/com/v8n/modules/identity/interfaces/rest/):
```
GET  /api/v1/admin/customers?q=&page=&size=
     → customerService.listCustomers(q, page, size)
     → query UserRepository (table users) với email/name LIKE search
     → trả về: id, email, firstName, lastName, status, createdAt

POST /api/v1/admin/customers/{id}/revoke-tokens
     → customerService.revokeTokens(id)
     → tokenBlacklistService.revokeAllForUser(userId, "customer", "admin_force_logout")
```

- **Audit trail cho force-revoke**:
  - `revoked_tokens.reason` = `"admin_force_logout"`
  - `revoked_tokens.ip_address` = IP của admin (người thực hiện)
  - Với admin user: ghi login_history với status=LOGOUT
  - Với customer user: không ghi login_history (customer không có login_history table)

## Risks / Trade-offs

- **[Redis availability] → Mitigation**: Fallback check DB nếu Redis không available. Log warning để monitor.
- **[DB cleanup] → Mitigation**: Scheduled job chạy daily xóa `revoked_tokens` đã hết hạn > 7 ngày. Redis tự cleanup qua TTL.
- **[Performance overhead] → Mitigation**: Redis check O(1). DB check chỉ fallback. Thêm 2 index cho `revoked_tokens`.
- **[Revoke all sessions] → Mitigation**: Khi revoke all, thêm entries cho tất cả token active của user bằng cách dùng `user_id` + `user_type` lookup pattern.
- **[Customer không có login_history] → Mitigation**: Dùng `revoked_tokens` table làm audit trail cho customer force-logout. Có thể query history qua `revoked_tokens.user_id`.
