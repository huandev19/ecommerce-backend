## Why

Hiện tại hệ thống có 2 bảng `users` và `customers` tồn tại song song với nhiều trường dữ liệu trùng lặp: `email`, `first_name`, `last_name`, `metadata`. Việc liên kết giữa 2 bảng được thực hiện qua `email` (không có FK), dẫn đến:

- **Dữ liệu trùng lặp**: Cùng một thông tin (email, tên) được lưu ở 2 nơi, dễ bị lệch (inconsistency).
- **Logic phức tạp không cần thiết**: `CustomerService` luôn phải query `User` trước rồi mới tìm `Customer` qua email.
- **Refactor dở dang**: Entity `User` có nhiều field `@Transient` (`phone`, `avatarUrl`, `status`, `emailVerified`, `lastLoginAt`) — cho thấy các field này đã được lên kế hoạch chuyển đi nhưng chưa hoàn thành.
- **Không có FK constraint**: Dễ dẫn đến dữ liệu mồ côi (orphan records).

## What Changes

- **Xóa bảng `users`**: Xóa entity [`User.java`](../../modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/User.java:22), repository [`UserRepository.java`](../../modules/identity/src/main/java/com/v8n/modules/identity/domain/repository/UserRepository.java:13), và tất cả reference.
- **Mở rộng bảng `customers`**: Thêm các cột `password_hash`, `status`, `avatar_url`, `email_verified`, `last_login_at` (hiện đang là `@Transient` trong `User`).
- **Refactor `Customer` entity**: Xóa `@Transient private User user`, thêm các field mới thành DB columns thực sự.
- **Refactor `AuthService`**: Sử dụng `CustomerRepository` thay vì `UserRepository` cho register/login.
- **Refactor `CustomerService`**: Bỏ dependency vào `UserRepository`, làm việc trực tiếp với `Customer`.
- **Refactor `AddressService`**: Cập nhật tham chiếu từ `User` sang `Customer` (nếu có).
- **Database migration**: Flyway script để thêm cột mới, migrate dữ liệu, và xóa bảng `users`.

## Capabilities

### New Capabilities
- `customer-identity`: Quản lý định danh khách hàng thống nhất trong một bảng `customers` duy nhất, bao gồm cả thông tin xác thực (password) và thông tin profile.

### Modified Capabilities
- `customer-auth-logout`: Cập nhật flow logout để làm việc với `Customer` entity thay vì `User`.
- `admin-force-logout`: Cập nhật force-revoke để làm việc với `Customer` entity.

### Removed
- Bảng `users` và toàn bộ code liên quan đến `User` entity.

## Impact

- **Files thay đổi**:
  - `Customer.java` — thêm field: `passwordHash`, `status`, `avatarUrl`, `emailVerified`, `lastLoginAt`; xóa `@Transient private User user`
  - `User.java` — **XÓA toàn bộ file**
  - `UserRepository.java` — **XÓA toàn bộ file**
  - `CustomerRepository.java` — thêm method `searchByQuery`
  - `AuthService.java` — thay `UserRepository` → `CustomerRepository`, `User` → `Customer`
  - `CustomerService.java` — bỏ `UserRepository`, làm việc trực tiếp với `Customer`
  - `AddressService.java` — cập nhật tham chiếu `User` → `Customer` (nếu có)
  - `AdminCustomerResponse.java` — cập nhật import `User.UserStatus` → `Customer.CustomerStatus`
  - `CustomerResponse.java` — loại bỏ `userId` field, gộp với `UserResponse`
  - `UserResponse.java` — **XÓA** (hợp nhất vào `CustomerResponse`)
  - `RegisterRequest.java`, `LoginRequest.java` — review nếu có tham chiếu đến `User`
  - `AuthController.java`, `AdminCustomerController.java` — cập nhật response type
  - `CustomerServiceTest.java`, `AddressServiceTest.java` — cập nhật test dùng `Customer` thay `User`
  - Flyway migration script mới
- **Database**: Thêm cột vào `customers`, migrate data, xóa bảng `users`
- **Breaking changes**: API response của customer endpoints sẽ thay đổi (gộp `UserResponse` + `CustomerResponse`)
