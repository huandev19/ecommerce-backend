-- Migration: V9__Create_User_Admin_Permissions.sql
-- Tạo bảng user_admin_permissions (user-level permission override)
CREATE TABLE public.user_admin_permissions (
    user_admin_id   TEXT NOT NULL REFERENCES user_admins(id) ON DELETE CASCADE,
    permission_id   UUID NOT NULL REFERENCES permission(id) ON DELETE CASCADE,
    assigned_at     TIMESTAMPTZ DEFAULT now() NOT NULL,
    assigned_by     TEXT REFERENCES user_admins(id),
    PRIMARY KEY (user_admin_id, permission_id)
);

CREATE INDEX idx_ua_perm_user ON user_admin_permissions(user_admin_id);
