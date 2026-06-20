-- ALTER TABLE carts ADD COLUMN IF NOT EXISTS shipping_address_id text;
-- ALTER TABLE carts ADD CONSTRAINT IF NOT EXISTS fk_carts_shipping_address FOREIGN KEY (shipping_address_id) REFERENCES customer_addresses(id);

-- ALTER TABLE carts ADD COLUMN IF NOT EXISTS billing_address_id text;
-- ALTER TABLE carts ADD CONSTRAINT IF NOT EXISTS fk_carts_billing_address FOREIGN KEY (billing_address_id) REFERENCES customer_addresses(id);

-- ALTER TABLE orders ADD COLUMN IF NOT EXISTS shipping_address_id text;
-- ALTER TABLE orders ADD CONSTRAINT IF NOT EXISTS fk_orders_shipping_address FOREIGN KEY (shipping_address_id) REFERENCES customer_addresses(id);

-- ALTER TABLE orders ADD COLUMN IF NOT EXISTS billing_address_id text;
-- ALTER TABLE orders ADD CONSTRAINT IF NOT EXISTS fk_orders_billing_address FOREIGN KEY (billing_address_id) REFERENCES customer_addresses(id);
