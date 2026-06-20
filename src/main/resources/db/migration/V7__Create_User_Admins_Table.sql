-- Migration: V7__Create_User_Admins_Table.sql
-- Tạo bảng user_admins (19 cột) + soft unique index + seed Super Admin mặc định
CREATE TABLE public.user_admins (
    id                  TEXT DEFAULT (gen_random_uuid())::text NOT NULL,
    email               VARCHAR(255) NOT NULL,
    first_name          VARCHAR(100),
    last_name           VARCHAR(100),
    password_hash       TEXT,
    activation_token    UUID DEFAULT gen_random_uuid() NOT NULL,
    password_set_at     TIMESTAMPTZ,
    avatar_url          VARCHAR(500),
    phone               VARCHAR(50),
    is_active           BOOLEAN NOT NULL DEFAULT TRUE,
    last_login_at       TIMESTAMPTZ,
    failed_login_attempts INTEGER NOT NULL DEFAULT 0,
    locked_until        TIMESTAMPTZ,
    metadata            JSONB DEFAULT '{}'::jsonb NOT NULL,
    created_at          TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at          TIMESTAMPTZ DEFAULT now() NOT NULL,
    deleted_at          TIMESTAMPTZ,
    PRIMARY KEY (id)
);

-- Soft unique: chỉ unique khi user còn hoạt động
CREATE UNIQUE INDEX uq_user_admins_email_active
    ON user_admins(email) WHERE is_active = TRUE;

CREATE INDEX idx_user_admins_email ON user_admins(email);
CREATE INDEX idx_user_admins_is_active ON user_admins(is_active);

-- Seed Super Admin mặc định
-- email: admin@v8n.com, password: Admin@123 (BCrypt hash)
INSERT INTO user_admins (id, email, first_name, last_name, password_hash, activation_token, password_set_at, is_active, failed_login_attempts)
VALUES (
    '00000000-0000-0000-0000-000000000001',
    'admin@v8n.com',
    'Super',
    'Admin',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
    '00000000-0000-0000-0000-000000000001',
    NOW(),
    TRUE,
    0
);
