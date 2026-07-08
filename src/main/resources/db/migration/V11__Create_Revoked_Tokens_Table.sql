-- Migration: V11__Create_Revoked_Tokens_Table.sql
-- Tạo bảng revoked_tokens lưu token đã logout/revoke
-- Hỗ trợ customer logout, admin logout, và admin force-revoke

CREATE TABLE public.revoked_tokens (
    id              TEXT DEFAULT (gen_random_uuid())::text NOT NULL,
    token_hash      VARCHAR(64) NOT NULL,
    token_type      VARCHAR(20) NOT NULL,
    user_id         TEXT,
    user_type       VARCHAR(20),
    revoked_at      TIMESTAMPTZ DEFAULT now() NOT NULL,
    expires_at      TIMESTAMPTZ NOT NULL,
    reason          VARCHAR(100),
    ip_address      VARCHAR(45),
    PRIMARY KEY (id)
);

-- Index cho lookup token_hash nhanh (check blacklist)
CREATE INDEX idx_revoked_tokens_token_hash ON revoked_tokens(token_hash);

-- Index cho revoke tất cả token của một user
CREATE INDEX idx_revoked_tokens_user ON revoked_tokens(user_id, user_type);
