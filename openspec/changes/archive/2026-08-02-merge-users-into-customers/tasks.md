## 1. Database Migration — Thêm cột vào customers

- [x] 1.1 Tạo Flyway migration script `V12__add_auth_columns_to_customers.sql`:
  - `ALTER TABLE customers ADD COLUMN password_hash TEXT`
  - `ALTER TABLE customers ADD COLUMN status VARCHAR(20) DEFAULT 'ACTIVE'`
  - `ALTER TABLE customers ADD COLUMN avatar_url VARCHAR(500)`
  - `ALTER TABLE customers ADD COLUMN email_verified BOOLEAN DEFAULT FALSE`
  - `ALTER TABLE customers ADD COLUMN last_login_at TIMESTAMP`
  - Thêm unique index trên `email` nếu chưa có
- [x] 1.2 Verify migration chạy thành công trên local DB (app started + API hoạt động = Flyway OK)

## 2. Database Migration — Migrate dữ liệu từ users sang customers

- [x] 2.1 Tạo Flyway migration script `V13__migrate_users_to_customers.sql`:
  - UPDATE `customers` từ `users` qua email join: đồng bộ `password_hash`, `first_name`, `last_name`; set `has_account = true`
  - INSERT customer mới cho user chưa có customer tương ứng (dùng `users.id` làm `customers.id`)
- [x] 2.2 Verify data integrity sau migrate: đếm số lượng, kiểm tra sample data (app started + register/login works)

## 3. Database Migration — Xóa bảng users

- [x] 3.1 Tạo Flyway migration script `V14__drop_users_table.sql`:
  - `DROP TABLE IF EXISTS users CASCADE`
- [x] 3.2 Verify migration chạy thành công (app started = migrations applied)

## 4. Refactor Customer Entity

- [x] 4.1 Thêm các field mới vào [`Customer.java`](../../modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/Customer.java:20):
  - `passwordHash` (String, @Column TEXT, nullable)
  - `avatarUrl` (String, @Column VARCHAR 500)
  - `status` (CustomerStatus enum, @Enumerated STRING)
  - `emailVerified` (boolean)
  - `lastLoginAt` (LocalDateTime)
  - `CustomerStatus` enum: ACTIVE, INACTIVE, BANNED
- [x] 4.2 Xóa `@Transient private User user` field
- [x] 4.3 Cập nhật `getFullName()` method nếu cần

## 5. Xóa User Entity và UserRepository

- [x] 5.1 Xóa file [`User.java`](../../modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/User.java)
- [x] 5.2 Xóa file [`UserRepository.java`](../../modules/identity/src/main/java/com/v8n/modules/identity/domain/repository/UserRepository.java)

## 6. Cập nhật CustomerRepository

- [x] 6.1 Thêm method `searchByQuery(String query, Pageable pageable)` vào [`CustomerRepository.java`](../../modules/identity/src/main/java/com/v8n/modules/identity/domain/repository/CustomerRepository.java:9) (chuyển từ UserRepository)

## 7. Refactor AuthService

- [x] 7.1 Thay `UserRepository` → `CustomerRepository` trong [`AuthService.java`](../../modules/identity/src/main/java/com/v8n/modules/identity/application/service/AuthService.java:25)
- [x] 7.2 Thay toàn bộ `User` → `Customer` trong service
- [x] 7.3 Cập nhật `register()`: tạo `Customer` với `hasAccount=true`, `status=ACTIVE`
- [x] 7.4 Cập nhật `login()`: kiểm tra `hasAccount`, `status != BANNED`, `passwordHash`
- [x] 7.5 Cập nhật `getCurrentUser()`: dùng `customerRepository.findByIdNotDeleted()`
- [x] 7.6 Cập nhật `refreshToken()`: dùng `customerRepository.findByIdNotDeleted()`
- [x] 7.7 Cập nhật `buildAuthResponse()`: map từ `Customer` thay vì `User`
- [x] 7.8 Cập nhật `mapToUserResponse()` thành `mapToCustomerResponse()` dùng `CustomerResponse`

## 8. Refactor CustomerService

- [x] 8.1 Bỏ `UserRepository` dependency trong [`CustomerService.java`](../../modules/identity/src/main/java/com/v8n/modules/identity/application/service/CustomerService.java:26)
- [x] 8.2 Cập nhật `getCustomerByUserId()`: dùng `customerRepository.findByIdNotDeleted()` trực tiếp
- [x] 8.3 Cập nhật `updateProfile()`: dùng `customerRepository.findByIdNotDeleted()` trực tiếp
- [x] 8.4 Cập nhật `createCustomerIfNotExists()`: không cần tạo từ `User` nữa (đã xóa method)
- [x] 8.5 Cập nhật `listCustomers()`: query `CustomerRepository.searchByQuery()` thay vì `UserRepository.searchByQuery()`
- [x] 8.6 Cập nhật `mapToResponse()`: chỉ cần `Customer`, không cần `User`

## 9. Cập nhật DTOs

- [x] 9.1 Cập nhật [`CustomerResponse.java`](../../modules/identity/src/main/java/com/v8n/modules/identity/application/dto/CustomerResponse.java:10): thêm `status`, `emailVerified`, `hasAccount`, `lastLoginAt`; bỏ `userId`
- [x] 9.2 Xóa [`UserResponse.java`](../../modules/identity/src/main/java/com/v8n/modules/identity/application/dto/UserResponse.java) (đã xóa)
- [x] 9.3 Cập nhật [`AdminCustomerResponse.java`](../../modules/identity/src/main/java/com/v8n/modules/identity/application/dto/AdminCustomerResponse.java): thay import `User.UserStatus` → `Customer.CustomerStatus`
- [x] 9.4 Cập nhật [`AuthResponse.java`](../../modules/identity/src/main/java/com/v8n/modules/identity/application/dto/AuthResponse.java): thay `UserResponse` → `CustomerResponse`

## 10. Cập nhật AddressService

- [x] 10.1 Cập nhật [`AddressService.java`](../../modules/identity/src/main/java/com/v8n/modules/identity/application/service/AddressService.java:9) — bỏ `UserRepository`, `getCustomerByUserId()` dùng `customerRepository.findByIdNotDeleted()` trực tiếp

## 11. Cập nhật Controllers

- [x] 11.1 Cập nhật [`AuthController.java`](../../modules/identity/src/main/java/com/v8n/modules/identity/interfaces/rest/AuthController.java): response type trả về `CustomerResponse`
- [x] 11.2 [`AdminCustomerController.java`](../../modules/identity/src/main/java/com/v8n/modules/identity/interfaces/rest/AdminCustomerController.java): không cần thay đổi (đã dùng `AdminCustomerResponse`)

## 12. Cập nhật Tests

- [x] 12.1 Cập nhật [`CustomerServiceTest.java`](../../modules/identity/src/test/java/com/v8n/modules/identity/application/service/CustomerServiceTest.java): dùng `Customer` thay `User`
- [x] 12.2 Cập nhật [`AddressServiceTest.java`](../../modules/identity/src/test/java/com/v8n/modules/identity/application/service/AddressServiceTest.java): dùng `Customer` thay `User`
- [x] 12.3 Tạo `AuthServiceTest.java` — test register, login, getCurrentUser với `Customer`

## 13. Build & Verify

- [x] 13.1 Compile identity module: `./gradlew :modules:identity:compileJava`
- [x] 13.2 Chạy identity module tests: `./gradlew :modules:identity:test`
- [x] 13.3 Compile toàn bộ project: `./gradlew compileJava`
- [x] 13.4 Chạy toàn bộ tests: `./gradlew test`
- [x] 13.5 Verify API endpoints: register, login, get profile, update profile, list customers (admin)
