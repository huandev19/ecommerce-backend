-- Migration: V13__migrate_users_to_customers.sql
-- Migrate dữ liệu từ bảng users sang bảng customers

-- 1. Cập nhật customer đã tồn tại: đồng bộ password_hash, first_name, last_name
--    và set has_account = true
UPDATE public.customers c
SET
    password_hash = u.password_hash,
    first_name = COALESCE(NULLIF(c.first_name, ''), u.first_name),
    last_name = COALESCE(NULLIF(c.last_name, ''), u.last_name),
    has_account = TRUE
FROM public.users u
WHERE c.email = u.email
  AND c.email IS NOT NULL;

-- 2. Tạo customer mới cho user chưa có customer tương ứng
--    Dùng users.id làm customers.id để JWT token cũ vẫn hợp lệ
INSERT INTO public.customers (id, email, password_hash, first_name, last_name,
                               has_account, status, created_at, updated_at)
SELECT
    u.id,
    u.email,
    u.password_hash,
    u.first_name,
    u.last_name,
    TRUE,
    'ACTIVE',
    u.created_at,
    u.updated_at
FROM public.users u
WHERE u.email IS NOT NULL
  AND u.email NOT IN (
      SELECT email FROM public.customers WHERE email IS NOT NULL
  );

-- 3. Log số lượng đã migrate (không ảnh hưởng data)
DO $$
DECLARE
    updated_count INTEGER;
    inserted_count INTEGER;
BEGIN
    SELECT COUNT(*) INTO updated_count
    FROM public.customers c
    INNER JOIN public.users u ON c.email = u.email
    WHERE c.has_account = TRUE;

    SELECT COUNT(*) INTO inserted_count
    FROM public.customers
    WHERE has_account = TRUE
      AND email IS NOT NULL;

    RAISE NOTICE 'Migration complete: % customers updated, % total customers with accounts',
        updated_count, inserted_count;
END $$;
