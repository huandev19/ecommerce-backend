-- Migration: V15__change_default_uuid_to_v7.sql
-- Chuyển tất cả DEFAULT gen_random_uuid() → uuidv7() cho các bảng
-- Yêu cầu: PostgreSQL 17+ (hàm uuidv7() built-in)
-- Nếu PostgreSQL < 17: cần tạo custom function uuidv7() trước

-- ============================================================
-- Bảng dùng id TEXT với DEFAULT (gen_random_uuid())::text
-- ============================================================

ALTER TABLE public.api_keys                  ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.application_methods       ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.audit_logs                ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.auth_identities           ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.campaigns                 ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.captures                  ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.cart_adjustments          ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.cart_items                ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.cart_tax_lines            ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.carts                     ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.customer_addresses        ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.customer_groups           ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.customers                 ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.event_logs                ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.fulfillment_addresses     ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.fulfillment_items         ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.fulfillment_labels        ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.fulfillments              ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.inventory_items           ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.inventory_levels          ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.login_history             ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.mfa_factors               ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.mfa_recovery_codes        ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.notifications             ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.order_addresses           ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.order_credit_lines        ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.order_items               ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.order_summaries           ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.order_timelines           ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.order_transactions        ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.orders                    ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.payment_collections       ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.payment_sessions          ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.payments                  ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.price_lists               ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.price_rules               ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.price_sets                ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.prices                    ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.product_categories        ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.product_collections       ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.product_images            ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.product_option_values     ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.product_options           ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.product_tags              ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.product_variants          ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.products                  ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.promotions                ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.provider_identities       ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.refunds                   ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.region_countries          ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.regions                   ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.revoked_tokens            ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.roles                     ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.shipping_option_rules     ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.shipping_options          ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.shipping_profiles         ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.store_currencies          ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.stores                    ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.user_admins               ALTER COLUMN id SET DEFAULT (uuidv7())::text;
ALTER TABLE public.verification_tokens       ALTER COLUMN id SET DEFAULT (uuidv7())::text;

-- ============================================================
-- Bảng dùng UUID type native (không cast ::text)
-- ============================================================

ALTER TABLE public.permission                ALTER COLUMN id SET DEFAULT uuidv7();
ALTER TABLE public.role                      ALTER COLUMN id SET DEFAULT uuidv7();

-- ============================================================
-- Cột activation_token trong user_admins (UUID type)
-- ============================================================

ALTER TABLE public.user_admins               ALTER COLUMN activation_token SET DEFAULT uuidv7();