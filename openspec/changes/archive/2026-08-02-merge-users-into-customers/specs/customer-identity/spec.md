# Customer Identity

## Purpose

Quản lý định danh khách hàng thống nhất trong một bảng `customers` duy nhất, bao gồm cả thông tin xác thực (password) và thông tin profile. Thay thế cho kiến trúc 2 bảng `users` + `customers` trùng lặp trước đây.

## ADDED Requirements

### Requirement: Customer đăng ký tài khoản
Hệ thống SHALL cho phép khách hàng đăng ký tài khoản mới qua `POST /api/v1/auth/register`, tạo record trong bảng `customers` với `has_account = true`.

#### Scenario: Đăng ký thành công
- **WHEN** khách gửi `POST /api/v1/auth/register` với email, password, firstName, lastName, phone hợp lệ
- **THEN** hệ thống SHALL tạo `Customer` record với `has_account = true`, `status = ACTIVE`, `password_hash` được mã hóa
- **AND** hệ thống SHALL trả về `201 Created` với JWT token và `CustomerResponse`

#### Scenario: Đăng ký với email đã tồn tại
- **WHEN** khách gửi `POST /api/v1/auth/register` với email đã có trong `customers` và `has_account = true`
- **THEN** hệ thống SHALL trả về `409 Conflict` với thông báo email đã tồn tại

### Requirement: Customer đăng nhập
Hệ thống SHALL cho phép khách hàng đăng nhập qua `POST /api/v1/auth/login` sử dụng email và password.

#### Scenario: Đăng nhập thành công
- **WHEN** khách gửi `POST /api/v1/auth/login` với email và password đúng
- **THEN** hệ thống SHALL tìm `Customer` có `email` tương ứng và `has_account = true`
- **AND** hệ thống SHALL kiểm tra `password_hash` khớp với password nhập vào
- **AND** hệ thống SHALL kiểm tra `status != BANNED`
- **AND** hệ thống SHALL cập nhật `last_login_at` về thời điểm hiện tại
- **AND** hệ thống SHALL trả về JWT token với `sub` là `customer.id`

#### Scenario: Đăng nhập với tài khoản bị khóa
- **WHEN** khách gửi `POST /api/v1/auth/login` với tài khoản có `status = BANNED`
- **THEN** hệ thống SHALL trả về `401 Unauthorized` với thông báo tài khoản bị khóa

#### Scenario: Đăng nhập với guest customer (has_account = false)
- **WHEN** khách gửi `POST /api/v1/auth/login` với email của customer có `has_account = false`
- **THEN** hệ thống SHALL trả về `401 Unauthorized` (khách chưa đăng ký tài khoản)

### Requirement: Customer profile được lưu trong một bảng duy nhất
Tất cả thông tin profile của khách hàng (email, firstName, lastName, phone, avatarUrl) và thông tin xác thực (passwordHash, status, emailVerified, lastLoginAt) SHALL được lưu trong bảng `customers`.

#### Scenario: Lấy thông tin profile
- **WHEN** authenticated customer gửi `GET /api/v1/auth/me`
- **THEN** hệ thống SHALL query `Customer` từ `customerRepository.findByIdNotDeleted(userId)`
- **AND** hệ thống SHALL trả về `CustomerResponse` với đầy đủ thông tin profile và status

#### Scenario: Cập nhật profile
- **WHEN** authenticated customer gửi `PUT /api/v1/auth/profile` với firstName, lastName, phone mới
- **THEN** hệ thống SHALL cập nhật trực tiếp `Customer` record
- **AND** không cần đồng bộ giữa 2 bảng

### Requirement: Admin quản lý danh sách khách hàng
Hệ thống SHALL cho phép admin xem danh sách khách hàng qua `GET /api/v1/admin/customers` với tìm kiếm và phân trang.

#### Scenario: Admin xem danh sách khách hàng
- **WHEN** admin gửi `GET /api/v1/admin/customers?query=email&page=0&size=20`
- **THEN** hệ thống SHALL query `CustomerRepository.searchByQuery()` để tìm kiếm theo email, firstName, lastName
- **AND** hệ thống SHALL trả về `PageResponse<AdminCustomerResponse>` với `status`, `emailVerified`, `hasAccount`

## REMOVED Requirements

### Requirement: Bảng users và User entity
- Bảng `users` và entity [`User.java`](../../../modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/User.java) SHALL bị xóa hoàn toàn.
- [`UserRepository.java`](../../../modules/identity/src/main/java/com/v8n/modules/identity/domain/repository/UserRepository.java) SHALL bị xóa.
- [`UserResponse.java`](../../../modules/identity/src/main/java/com/v8n/modules/identity/application/dto/UserResponse.java) SHALL bị xóa (hợp nhất vào `CustomerResponse`).
- Mọi tham chiếu đến `User` entity trong codebase SHALL được thay thế bằng `Customer`.
