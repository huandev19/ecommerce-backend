-- Migration: V12__add_auth_columns_to_customers.sql
-- Thêm các cột authentication và profile vào bảng customers
-- để hợp nhất dữ liệu từ bảng users

ALTER TABLE public.customers
    ADD COLUMN IF NOT EXISTS password_hash TEXT,
    ADD COLUMN IF NOT EXISTS status VARCHAR(20) DEFAULT 'ACTIVE',
    ADD COLUMN IF NOT EXISTS avatar_url VARCHAR(500),
    ADD COLUMN IF NOT EXISTS email_verified BOOLEAN DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS last_login_at TIMESTAMPTZ;

-- Unique index trên email (soft unique: chỉ unique khi has_account = true)
CREATE UNIQUE INDEX IF NOT EXISTS uq_customers_email_account
    ON customers(email) WHERE has_account = TRUE;

CREATE INDEX IF NOT EXISTS idx_customers_email ON customers(email);
CREATE INDEX IF NOT EXISTS idx_customers_status ON customers(status);
