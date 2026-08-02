-- Migration: V14__drop_users_table.sql
-- Xóa bảng users sau khi đã migrate toàn bộ dữ liệu sang customers

-- Kiểm tra không có user nào chưa được migrate trước khi drop
DO $$
DECLARE
    orphan_count INTEGER;
BEGIN
    SELECT COUNT(*) INTO orphan_count
    FROM public.users u
    WHERE u.email IS NOT NULL
      AND u.email NOT IN (
          SELECT email FROM public.customers WHERE email IS NOT NULL
      );

    IF orphan_count > 0 THEN
        RAISE EXCEPTION 'Cannot drop users table: % orphan users found without matching customers', orphan_count;
    END IF;
END $$;

DROP TABLE IF EXISTS public.users CASCADE;
