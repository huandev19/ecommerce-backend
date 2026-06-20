-- Migration: V8__Create_User_Admin_Roles.sql
-- Tạo bảng user_admin_roles + seed Super Admin → role Super Admin
CREATE TABLE public.user_admin_roles (
    user_admin_id   TEXT NOT NULL REFERENCES user_admins(id) ON DELETE CASCADE,
    role_id         UUID NOT NULL REFERENCES role(id) ON DELETE CASCADE,
    assigned_at     TIMESTAMPTZ DEFAULT now() NOT NULL,
    assigned_by     TEXT REFERENCES user_admins(id),
    PRIMARY KEY (user_admin_id, role_id)
);

CREATE INDEX idx_user_admin_role_user ON user_admin_roles(user_admin_id);
CREATE INDEX idx_user_admin_role_role ON user_admin_roles(role_id);

-- Seed Super Admin user → Super Admin role
INSERT INTO user_admin_roles (user_admin_id, role_id)
VALUES ('00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
