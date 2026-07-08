## Why

Hệ thống authentication hiện tại thiếu API logout cho cả customer (`AuthController`) và admin (`AdminAuthController`). Sau khi login, client nhận access token + refresh token nhưng không có cách nào để server-side revoke các token này. Điều này gây rủi ro bảo mật: token bị lộ không thể thu hồi, không có audit trail cho hành động logout. Cần triển khai logout API với cơ chế token blacklist để đảm bảo an toàn và khả năng kiểm soát session.

Ngoài ra, admin dashboard cần khả năng force-logout user từ xa — admin có thể revoke toàn bộ token của một user (admin hoặc customer) mà không cần user đó tự logout.

## What Changes

- Thêm `POST /api/v1/auth/logout` + `/logout/all` — logout cho customer/store user (self-service)
- Thêm `POST /api/v1/auth/admin/logout` + `/logout/all` — logout cho admin user (self-service)
- Thêm `POST /api/v1/users/{id}/revoke-tokens` — admin force-revoke token của admin user khác
- Thêm `GET /api/v1/admin/customers` — admin list customer users (có search, pagination)
- Thêm `POST /api/v1/admin/customers/{id}/revoke-tokens` — admin force-revoke token của customer
- Tạo entity `RevokedToken` (table `revoked_tokens`) lưu token đã logout vào PostgreSQL
- Tích hợp Redis cache để check blacklist nhanh, giảm latency mỗi request
- Mở rộng `JwtAuthenticationFilter`: kiểm tra token trong blacklist trước khi xác thực
- Thêm cơ chế revoke token hỗ trợ: revoke 1 token, revoke tất cả token của 1 user
- Scheduled job dọn dẹp `revoked_tokens` đã hết hạn trong DB

## Capabilities

### New Capabilities
- `customer-auth-logout`: Khả năng logout cho customer/store user (self-service), revoke access & refresh token
- `admin-auth-logout`: Khả năng logout cho admin user (self-service), revoke token, audit trail qua login history
- `token-blacklist`: Cơ chế blacklist token dùng Redis + DB, kiểm tra token revoked trước mỗi request authenticated
- `admin-force-logout`: Khả năng admin force-revoke token của user khác (cả admin và customer) từ dashboard, kèm audit trail

### Modified Capabilities
<!-- Không có spec hiện tại nào bị thay đổi requirement vì đây là tính năng hoàn toàn mới -->

## Impact

- **Mới**: `modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/RevokedToken.java` — entity mới
- **Mới**: `modules/identity/src/main/java/com/v8n/modules/identity/domain/repository/RevokedTokenRepository.java` — repository mới
- **Mới**: `modules/identity/src/main/java/com/v8n/modules/identity/infrastructure/security/TokenBlacklistService.java` — service blacklist
- **Mới**: `modules/identity/src/main/java/com/v8n/modules/identity/infrastructure/config/RedisConfig.java` — cấu hình Redis
- **Mới**: `modules/identity/src/main/java/com/v8n/modules/identity/interfaces/rest/AdminCustomerController.java` — list customer + revoke token
- **Thay đổi**: `JwtAuthenticationFilter.java` — thêm check blacklist trước khi authenticate
- **Thay đổi**: `AuthController.java` — thêm endpoint logout, logout/all
- **Thay đổi**: `AdminAuthController.java` — thêm endpoint logout, logout/all
- **Thay đổi**: `AuthService.java` — thêm method logout, logoutAll
- **Thay đổi**: `AdminAuthService.java` — thêm method logout, logoutAll
- **Thay đổi**: `UserAdminController.java` — thêm endpoint revoke-tokens cho admin user
- **Thay đổi**: `UserAdminService.java` — thêm method force-revoke cho admin user
- **Phụ thuộc mới**: Redis client (spring-boot-starter-data-redis) trong `build.gradle` của identity module
- **Migration**: Flyway script tạo table `revoked_tokens`
