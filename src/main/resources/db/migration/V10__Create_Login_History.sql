-- Migration: V10__Create_Login_History.sql
-- Tạo bảng login_history + 4 indexes
CREATE TABLE public.login_history (
    id              TEXT DEFAULT (gen_random_uuid())::text NOT NULL,
    user_admin_id   TEXT REFERENCES user_admins(id) ON DELETE SET NULL,
    email           VARCHAR(255) NOT NULL,
    status          VARCHAR(20) NOT NULL,
    failure_reason  VARCHAR(50),
    ip_address      INET,
    user_agent      TEXT,
    attempted_at    TIMESTAMPTZ DEFAULT now() NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX idx_login_history_user ON login_history(user_admin_id);
CREATE INDEX idx_login_history_email ON login_history(email);
CREATE INDEX idx_login_history_attempted ON login_history(attempted_at);
CREATE INDEX idx_login_history_status ON login_history(status);
