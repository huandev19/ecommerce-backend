-- Migration: V6__Create_Role_Permission.sql
-- Tạo bảng role_permission + seed Super Admin (tất cả 25 permissions) + 5 role còn lại
CREATE TABLE role_permission (
    role_id UUID NOT NULL REFERENCES role(id) ON DELETE CASCADE,
    permission_id UUID NOT NULL REFERENCES permission(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (role_id, permission_id)
);

CREATE INDEX idx_role_permission_role ON role_permission(role_id);
CREATE INDEX idx_role_permission_perm ON role_permission(permission_id);

-- Super Admin: tất cả 25 permissions
INSERT INTO role_permission (role_id, permission_id)
SELECT '00000000-0000-0000-0000-000000000001', id FROM permission;

-- Catalog Manager: product:*
INSERT INTO role_permission (role_id, permission_id)
SELECT '00000000-0000-0000-0000-000000000002', id FROM permission WHERE code IN ('product:read', 'product:create', 'product:update', 'product:delete');

-- Order Manager: order:read, order:update
INSERT INTO role_permission (role_id, permission_id)
SELECT '00000000-0000-0000-0000-000000000003', id FROM permission WHERE code IN ('order:read', 'order:update');

-- Inventory Manager: inventory:read, inventory:update
INSERT INTO role_permission (role_id, permission_id)
SELECT '00000000-0000-0000-0000-000000000004', id FROM permission WHERE code IN ('inventory:read', 'inventory:update');

-- Marketing Manager: promotion:*
INSERT INTO role_permission (role_id, permission_id)
SELECT '00000000-0000-0000-0000-000000000005', id FROM permission WHERE code IN ('promotion:read', 'promotion:create', 'promotion:update', 'promotion:delete');

-- Viewer: chỉ đọc
INSERT INTO role_permission (role_id, permission_id)
SELECT '00000000-0000-0000-0000-000000000006', id FROM permission WHERE code IN ('product:read', 'order:read', 'inventory:read');
