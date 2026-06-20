-- Migration: V4__Create_Permission_Table.sql
-- Tạo bảng permission + seed 25 permissions
CREATE TABLE permission (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(100) NOT NULL UNIQUE,
    resource VARCHAR(50) NOT NULL,
    operation VARCHAR(20) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_permission_resource_operation UNIQUE (resource, operation)
);

CREATE INDEX idx_permission_resource ON permission(resource);

INSERT INTO permission (code, resource, operation, description) VALUES
('product:read', 'product', 'read', 'Xem sản phẩm'),
('product:create', 'product', 'create', 'Tạo sản phẩm mới'),
('product:update', 'product', 'update', 'Cập nhật sản phẩm'),
('product:delete', 'product', 'delete', 'Xóa sản phẩm'),
('order:read', 'order', 'read', 'Xem đơn hàng'),
('order:update', 'order', 'update', 'Cập nhật trạng thái đơn hàng'),
('user:read', 'user', 'read', 'Xem danh sách admin user'),
('user:create', 'user', 'create', 'Tạo admin user mới'),
('user:update', 'user', 'update', 'Cập nhật admin user'),
('user:delete', 'user', 'delete', 'Vô hiệu hóa admin user'),
('user:unlock', 'user', 'unlock', 'Mở khóa tài khoản admin'),
('inventory:read', 'inventory', 'read', 'Xem tồn kho'),
('inventory:update', 'inventory', 'update', 'Cập nhật tồn kho'),
('promotion:read', 'promotion', 'read', 'Xem khuyến mãi'),
('promotion:create', 'promotion', 'create', 'Tạo khuyến mãi'),
('promotion:update', 'promotion', 'update', 'Cập nhật khuyến mãi'),
('promotion:delete', 'promotion', 'delete', 'Xóa khuyến mãi'),
('setting:read', 'setting', 'read', 'Xem cấu hình hệ thống'),
('setting:update', 'setting', 'update', 'Cập nhật cấu hình'),
('login_history:read', 'login_history', 'read', 'Xem lịch sử đăng nhập'),
('login_history:export', 'login_history', 'export', 'Xuất báo cáo lịch sử đăng nhập'),
('role:read', 'role', 'read', 'Xem danh sách role'),
('role:create', 'role', 'create', 'Tạo role mới'),
('role:update', 'role', 'update', 'Cập nhật role'),
('role:delete', 'role', 'delete', 'Xóa role');
