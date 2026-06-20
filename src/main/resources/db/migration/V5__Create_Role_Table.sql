-- Migration: V5__Create_Role_Table.sql
-- Tạo bảng role + seed 6 roles mặc định
CREATE TABLE role (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500),
    is_system BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP
);

CREATE INDEX idx_role_name ON role(name);

INSERT INTO role (id, name, description, is_system) VALUES
('00000000-0000-0000-0000-000000000001', 'Super Admin', 'Quản trị viên tối cao, toàn quyền hệ thống', TRUE),
('00000000-0000-0000-0000-000000000002', 'Catalog Manager', 'Quản lý sản phẩm và danh mục', TRUE),
('00000000-0000-0000-0000-000000000003', 'Order Manager', 'Quản lý và xử lý đơn hàng', TRUE),
('00000000-0000-0000-0000-000000000004', 'Inventory Manager', 'Quản lý kho hàng và tồn kho', TRUE),
('00000000-0000-0000-0000-000000000005', 'Marketing Manager', 'Quản lý khuyến mãi và giá', TRUE),
('00000000-0000-0000-0000-000000000006', 'Viewer', 'Chỉ xem, không có quyền sửa', TRUE);
