--
-- PostgreSQL database dump
--
-- Dumped from database version 18.4 (Homebrew)
-- Dumped by pg_dump version 18.4 (Homebrew)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
-- SET transaction_timeout = 0;  -- Removed: not supported on PostgreSQL < 17
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: api_keys; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.api_keys (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    title character varying(255) NOT NULL,
    token_hash text NOT NULL,
    type character varying(50) DEFAULT 'secret'::character varying NOT NULL,
    created_by text,
    last_used_at timestamp with time zone,
    revoked_at timestamp with time zone,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: application_methods; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.application_methods (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    promotion_id text NOT NULL,
    type character varying(50) DEFAULT 'fixed'::character varying NOT NULL,
    target_type character varying(50) DEFAULT 'order'::character varying NOT NULL,
    allocation character varying(50) DEFAULT 'total'::character varying NOT NULL,
    value integer DEFAULT 0 NOT NULL,
    max_quantity integer,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: audit_logs; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.audit_logs (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    entity_type character varying(100) NOT NULL,
    entity_id text NOT NULL,
    action character varying(50) NOT NULL,
    changes jsonb DEFAULT '{}'::jsonb NOT NULL,
    performed_by text,
    performer_type character varying(50) DEFAULT 'system'::character varying NOT NULL,
    ip_address inet,
    user_agent text,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: auth_identities; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.auth_identities (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    app_metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    user_metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: campaigns; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.campaigns (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    promotion_id text,
    name character varying(255) NOT NULL,
    description text,
    starts_at timestamp with time zone,
    ends_at timestamp with time zone,
    budget jsonb DEFAULT '{}'::jsonb NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: captures; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.captures (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    payment_id text NOT NULL,
    amount integer NOT NULL,
    created_by text,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: cart_adjustments; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.cart_adjustments (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    cart_item_id text NOT NULL,
    code character varying(100),
    amount integer DEFAULT 0 NOT NULL,
    description text,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: cart_items; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.cart_items (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    cart_id text NOT NULL,
    variant_id text,
    title character varying(255) NOT NULL,
    quantity integer DEFAULT 1 NOT NULL,
    unit_price integer DEFAULT 0 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone,
    thumbnail character varying(500)
);


--
-- Name: cart_tax_lines; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.cart_tax_lines (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    cart_item_id text NOT NULL,
    code character varying(100),
    rate numeric(5,4) DEFAULT 0 NOT NULL,
    amount integer DEFAULT 0 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: carts; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.carts (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    customer_id text,
    email character varying(255),
    currency_code text NOT NULL,
    region_id text,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    completed_at timestamp with time zone,
    deleted_at timestamp with time zone,
    shipping_address_id text,
    billing_address_id text
);


--
-- Name: currencies; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.currencies (
    code text NOT NULL,
    name character varying(100) NOT NULL,
    symbol character varying(10) NOT NULL,
    decimal_digits integer DEFAULT 2 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: customer_addresses; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.customer_addresses (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    customer_id text NOT NULL,
    first_name character varying(100),
    last_name character varying(100),
    company character varying(255),
    address_1 character varying(255) NOT NULL,
    address_2 character varying(255),
    city character varying(100) NOT NULL,
    province character varying(100),
    postal_code character varying(50),
    country_code character varying(2) NOT NULL,
    phone character varying(50),
    is_default_shipping boolean DEFAULT false NOT NULL,
    is_default_billing boolean DEFAULT false NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: customer_group_customers; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.customer_group_customers (
    customer_id text NOT NULL,
    customer_group_id text NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: customer_groups; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.customer_groups (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    name character varying(255) NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: customers; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.customers (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(100),
    last_name character varying(100),
    phone character varying(50),
    has_account boolean DEFAULT false NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: discount_conditions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.discount_conditions (
    id character varying(48) NOT NULL,
    discount_id character varying(48) NOT NULL,
    condition_type character varying(50) NOT NULL,
    operator character varying(20) NOT NULL,
    condition_value text NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    deleted_at timestamp without time zone
);


--
-- Name: discount_rules; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.discount_rules (
    id character varying(48) NOT NULL,
    discount_id character varying(48) NOT NULL,
    rule_type character varying(50) NOT NULL,
    rule_value integer DEFAULT 0 NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    deleted_at timestamp without time zone
);


--
-- Name: discounts; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.discounts (
    id character varying(48) NOT NULL,
    code character varying(50) NOT NULL,
    starts_at timestamp without time zone DEFAULT now() NOT NULL,
    ends_at timestamp without time zone,
    usage_limit integer,
    usage_count integer DEFAULT 0 NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    deleted_at timestamp without time zone,
    type character varying(50) DEFAULT 'PERCENTAGE'::character varying NOT NULL,
    value integer DEFAULT 0 NOT NULL,
    currency_code character varying(3),
    min_requirement_amount integer DEFAULT 0 NOT NULL,
    max_discount_amount integer DEFAULT 0 NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    is_public boolean DEFAULT false NOT NULL,
    description character varying(500),
    metadata jsonb DEFAULT '{}'::jsonb
);


--
-- Name: event_logs; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.event_logs (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    event_type character varying(100) NOT NULL,
    severity character varying(20) DEFAULT 'info'::character varying NOT NULL,
    source character varying(50) DEFAULT 'system'::character varying NOT NULL,
    message text NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);

--
-- Name: fulfillment_addresses; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.fulfillment_addresses (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    fulfillment_id text NOT NULL,
    first_name character varying(100),
    last_name character varying(100),
    company character varying(255),
    address_1 character varying(255) NOT NULL,
    address_2 character varying(255),
    city character varying(100) NOT NULL,
    province character varying(100),
    postal_code character varying(50),
    country_code character varying(2) NOT NULL,
    phone character varying(50),
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: fulfillment_items; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.fulfillment_items (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    fulfillment_id text NOT NULL,
    order_item_id text,
    title character varying(255) NOT NULL,
    sku character varying(100),
    quantity integer DEFAULT 1 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone,
    fulfilled_quantity integer DEFAULT 0 NOT NULL,
    returned_quantity integer DEFAULT 0 NOT NULL,
    variant_title character varying(255),
    variant_id text
);


--
-- Name: fulfillment_labels; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.fulfillment_labels (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    fulfillment_id text NOT NULL,
    tracking_number character varying(255),
    tracking_url character varying(1000),
    label_url character varying(1000),
    carrier character varying(100),
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: fulfillments; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.fulfillments (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    order_id text,
    provider_id character varying(100),
    shipping_option_id text,
    status character varying(50) DEFAULT 'pending'::character varying NOT NULL,
    data jsonb DEFAULT '{}'::jsonb NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    shipped_at timestamp with time zone,
    delivered_at timestamp with time zone,
    canceled_at timestamp with time zone,
    deleted_at timestamp with time zone,
    carrier character varying(100),
    tracking_number character varying(255),
    display_id bigint
);


--
-- Name: inventory_items; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.inventory_items (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    sku character varying(100),
    title character varying(255),
    requires_shipping boolean DEFAULT true NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone,
    quantity integer DEFAULT 0 NOT NULL,
    reserved_quantity integer DEFAULT 0 NOT NULL,
    incoming_quantity integer DEFAULT 0 NOT NULL,
    warehouse_id character varying(255),
    location character varying(255),
    overselling boolean DEFAULT false NOT NULL,
    restock_threshold integer DEFAULT 0,
    variant_id character varying(255)
);


--
-- Name: inventory_levels; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.inventory_levels (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    inventory_item_id text NOT NULL,
    location_id text NOT NULL,
    stocked_quantity integer DEFAULT 0 NOT NULL,
    reserved_quantity integer DEFAULT 0 NOT NULL,
    incoming_quantity integer DEFAULT 0 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone,
    delta_quantity integer DEFAULT 0,
    current_quantity integer DEFAULT 0,
    reason character varying(255),
    note character varying(500),
    reference_type character varying(100),
    reference_id character varying(255),
    created_by character varying(255)
);


--
-- Name: mfa_factors; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.mfa_factors (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    auth_identity_id text NOT NULL,
    factor_type character varying(50) NOT NULL,
    secret text,
    phone character varying(50),
    is_enabled boolean DEFAULT false NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: mfa_recovery_codes; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.mfa_recovery_codes (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    auth_identity_id text NOT NULL,
    code_hash text NOT NULL,
    used_at timestamp with time zone,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: notifications; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.notifications (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    to_address character varying(255) NOT NULL,
    channel character varying(50) NOT NULL,
    template character varying(100),
    status character varying(50) DEFAULT 'pending'::character varying NOT NULL,
    data jsonb DEFAULT '{}'::jsonb NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone,
    content text,
    recipient_id uuid,
    recipient_email character varying(255),
    type character varying(50),
    subject character varying(255),
    sent_at timestamp with time zone,
    read_at timestamp with time zone,
    failed_at timestamp with time zone,
    error_message character varying(500)
);


--
-- Name: order_addresses; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.order_addresses (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    order_id text NOT NULL,
    type character varying(50) NOT NULL,
    first_name character varying(100),
    last_name character varying(100),
    company character varying(255),
    address_1 character varying(255) NOT NULL,
    address_2 character varying(255),
    city character varying(100) NOT NULL,
    province character varying(100),
    postal_code character varying(50),
    country_code character varying(2) NOT NULL,
    phone character varying(50),
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: order_carts; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.order_carts (
    order_id text NOT NULL,
    cart_id text NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: order_credit_lines; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.order_credit_lines (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    order_id text NOT NULL,
    amount integer NOT NULL,
    reference character varying(255),
    reference_id text,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: order_fulfillments; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.order_fulfillments (
    order_id text NOT NULL,
    fulfillment_id text NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: order_items; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.order_items (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    order_id text NOT NULL,
    variant_id text,
    title character varying(255) NOT NULL,
    sku character varying(100),
    quantity integer DEFAULT 1 NOT NULL,
    unit_price integer DEFAULT 0 NOT NULL,
    total integer DEFAULT 0 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: order_status_history; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.order_status_history (
    id character varying(36) NOT NULL,
    order_id character varying(36) NOT NULL,
    from_status character varying(50),
    to_status character varying(50) NOT NULL,
    note text,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone
);


--
-- Name: order_summaries; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.order_summaries (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    order_id text NOT NULL,
    item_total integer DEFAULT 0 NOT NULL,
    tax_total integer DEFAULT 0 NOT NULL,
    shipping_total integer DEFAULT 0 NOT NULL,
    discount_total integer DEFAULT 0 NOT NULL,
    paid_total integer DEFAULT 0 NOT NULL,
    refunded_total integer DEFAULT 0 NOT NULL,
    current_order_total integer DEFAULT 0 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: order_timelines; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.order_timelines (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    order_id text NOT NULL,
    previous_status character varying(50),
    new_status character varying(50) NOT NULL,
    reason text,
    action_by text,
    action_type character varying(50) DEFAULT 'system'::character varying NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: order_transactions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.order_transactions (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    order_id text NOT NULL,
    amount integer NOT NULL,
    currency_code text NOT NULL,
    reference character varying(255),
    reference_id text,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: orders; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.orders (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    display_id bigint NOT NULL,
    customer_id text,
    cart_id text,
    email character varying(255),
    currency_code text NOT NULL,
    region_id text,
    status character varying(50) DEFAULT 'pending'::character varying NOT NULL,
    payment_status character varying(50) DEFAULT 'not_paid'::character varying NOT NULL,
    fulfillment_status character varying(50) DEFAULT 'not_fulfilled'::character varying NOT NULL,
    subtotal integer DEFAULT 0 NOT NULL,
    tax_total integer DEFAULT 0 NOT NULL,
    shipping_total integer DEFAULT 0 NOT NULL,
    discount_total integer DEFAULT 0 NOT NULL,
    total integer DEFAULT 0 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    canceled_at timestamp with time zone,
    deleted_at timestamp with time zone,
    shipping_address_id text,
    billing_address_id text
);


--
-- Name: orders_display_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.orders_display_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: orders_display_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.orders_display_id_seq OWNED BY public.orders.display_id;


--
-- Name: payment_collections; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.payment_collections (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    order_id text,
    currency_code text NOT NULL,
    amount integer DEFAULT 0 NOT NULL,
    authorized_amount integer DEFAULT 0 NOT NULL,
    captured_amount integer DEFAULT 0 NOT NULL,
    refunded_amount integer DEFAULT 0 NOT NULL,
    status character varying(50) DEFAULT 'not_paid'::character varying NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: payment_sessions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.payment_sessions (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    payment_collection_id text NOT NULL,
    payment_id text,
    provider_id character varying(100) NOT NULL,
    amount integer NOT NULL,
    currency_code text NOT NULL,
    status character varying(50) DEFAULT 'pending'::character varying NOT NULL,
    data jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone,
    canceled_at timestamp with time zone,
    provider_session_id character varying(255),
    expires_at timestamp with time zone,
    confirmed_at timestamp with time zone,
    error_message character varying(500),
    provider character varying(50) DEFAULT 'stripe'::character varying NOT NULL
);


--
-- Name: payments; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.payments (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    payment_collection_id text NOT NULL,
    provider_id character varying(100) NOT NULL,
    amount integer NOT NULL,
    currency_code text NOT NULL,
    status character varying(50) DEFAULT 'pending'::character varying NOT NULL,
    data jsonb DEFAULT '{}'::jsonb NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    captured_at timestamp with time zone,
    canceled_at timestamp with time zone,
    deleted_at timestamp with time zone,
    payment_session_id text,
    provider_transaction_id character varying(255)
);


--
-- Name: price_lists; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.price_lists (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    title character varying(255),
    description text,
    type character varying(50) DEFAULT 'sale'::character varying NOT NULL,
    status character varying(50) DEFAULT 'draft'::character varying NOT NULL,
    starts_at timestamp with time zone,
    ends_at timestamp with time zone,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: price_rules; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.price_rules (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    price_id text NOT NULL,
    attribute character varying(100) NOT NULL,
    operator character varying(50) DEFAULT 'eq'::character varying NOT NULL,
    value jsonb DEFAULT '[]'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: price_sets; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.price_sets (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: prices; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.prices (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    price_set_id text NOT NULL,
    price_list_id text,
    currency_code text NOT NULL,
    amount integer NOT NULL,
    min_quantity integer,
    max_quantity integer,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: product_categories; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_categories (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    parent_category_id text,
    name character varying(255) NOT NULL,
    handle character varying(255) NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    is_internal boolean DEFAULT false NOT NULL,
    rank integer DEFAULT 0 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: product_category_products; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_category_products (
    product_id text NOT NULL,
    category_id text NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: product_collections; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_collections (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    title character varying(255) NOT NULL,
    handle character varying(255) NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone,
    rank integer DEFAULT 0 NOT NULL,
    is_active boolean DEFAULT true NOT NULL
);


--
-- Name: product_images; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_images (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    product_id text NOT NULL,
    url character varying(1000) NOT NULL,
    rank integer DEFAULT 0 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: product_option_values; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_option_values (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    option_id text NOT NULL,
    value character varying(255) NOT NULL,
    rank integer DEFAULT 0 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: product_options; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_options (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    product_id text NOT NULL,
    title character varying(255) NOT NULL,
    rank integer DEFAULT 0 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: product_product_tags; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_product_tags (
    product_id text NOT NULL,
    tag_id text NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: product_tags; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_tags (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    value character varying(255) NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: product_types; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_types (
    id character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    slug character varying(255) NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone
);


--
-- Name: product_variant_inventory_items; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_variant_inventory_items (
    variant_id text NOT NULL,
    inventory_item_id text NOT NULL,
    required_quantity integer DEFAULT 1 NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: product_variant_price_sets; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_variant_price_sets (
    variant_id text NOT NULL,
    price_set_id text NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: product_variants; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_variants (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    product_id text NOT NULL,
    title character varying(255) NOT NULL,
    sku character varying(100),
    barcode character varying(100),
    ean character varying(100),
    upc character varying(100),
    inventory_quantity integer DEFAULT 0 NOT NULL,
    allow_backorder boolean DEFAULT false NOT NULL,
    manage_inventory boolean DEFAULT true NOT NULL,
    weight integer,
    height integer,
    width integer,
    length integer,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: products; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.products (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    handle character varying(255) NOT NULL,
    title character varying(500) NOT NULL,
    subtitle character varying(500),
    description text,
    thumbnail character varying(500),
    weight integer,
    height integer,
    width integer,
    length integer,
    origin_country character varying(2),
    hs_code character varying(50),
    mid_code character varying(50),
    material character varying(255),
    collection_id text,
    category_id text,
    type_id text,
    status character varying(50) DEFAULT 'draft'::character varying NOT NULL,
    discountable boolean DEFAULT true NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: promotions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.promotions (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    code character varying(100) NOT NULL,
    type character varying(50) DEFAULT 'standard'::character varying NOT NULL,
    status character varying(50) DEFAULT 'draft'::character varying NOT NULL,
    starts_at timestamp with time zone,
    ends_at timestamp with time zone,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: provider_identities; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.provider_identities (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    auth_identity_id text NOT NULL,
    provider character varying(50) NOT NULL,
    provider_user_id character varying(255) NOT NULL,
    provider_metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: refunds; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.refunds (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    payment_id text NOT NULL,
    amount integer NOT NULL,
    reason character varying(255),
    note text,
    created_by text,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone,
    currency_code character varying(3) DEFAULT 'USD'::character varying NOT NULL,
    failed_at timestamp with time zone,
    status character varying(50) DEFAULT 'PENDING'::character varying NOT NULL,
    provider_refund_id character varying(255),
    processed_at timestamp with time zone,
    failure_message character varying(500),
    payment_collection_id character varying(255)
);


--
-- Name: region_countries; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.region_countries (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    region_id text NOT NULL,
    country_code character varying(2) NOT NULL,
    country_name character varying(255) NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: regions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.regions (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    name character varying(255) NOT NULL,
    currency_code text NOT NULL,
    tax_rate numeric(5,4) DEFAULT 0 NOT NULL,
    tax_code character varying(50),
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: reservation_items; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.reservation_items (
    id character varying(255) NOT NULL,
    inventory_item_id character varying(255) NOT NULL,
    line_item_id uuid NOT NULL,
    quantity integer DEFAULT 0 NOT NULL,
    expires_at timestamp without time zone,
    status character varying(20) DEFAULT 'RESERVED'::character varying NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone,
    deleted_at timestamp without time zone
);


--
-- Name: roles; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.roles (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    name character varying(100) NOT NULL,
    description text,
    permissions jsonb DEFAULT '[]'::jsonb NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: shipping_option_rules; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.shipping_option_rules (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    shipping_option_id text NOT NULL,
    attribute character varying(100) NOT NULL,
    operator character varying(50) DEFAULT 'eq'::character varying NOT NULL,
    value jsonb DEFAULT '[]'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: shipping_options; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.shipping_options (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    shipping_profile_id text NOT NULL,
    region_id text NOT NULL,
    name character varying(255) NOT NULL,
    price_type character varying(50) DEFAULT 'flat_rate'::character varying NOT NULL,
    amount integer DEFAULT 0 NOT NULL,
    data jsonb DEFAULT '{}'::jsonb NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: shipping_profiles; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.shipping_profiles (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    name character varying(255) NOT NULL,
    type character varying(50) DEFAULT 'default'::character varying NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: store_currencies; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.store_currencies (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    store_id text NOT NULL,
    currency_code text NOT NULL,
    is_default boolean DEFAULT false NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: stores; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.stores (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    name character varying(255) NOT NULL,
    default_currency_code text,
    default_region_id text,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(100),
    last_name character varying(100),
    password_hash text,
    role character varying(50) DEFAULT 'admin'::character varying NOT NULL,
    role_id text,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: verification_tokens; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.verification_tokens (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    auth_identity_id text,
    identifier character varying(255) NOT NULL,
    token_hash text NOT NULL,
    type character varying(50) NOT NULL,
    expires_at timestamp with time zone NOT NULL,
    used_at timestamp with time zone,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: orders display_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders ALTER COLUMN display_id SET DEFAULT nextval('public.orders_display_id_seq'::regclass);


--
-- Name: api_keys api_keys_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.api_keys
    ADD CONSTRAINT api_keys_pkey PRIMARY KEY (id);


--
-- Name: application_methods application_methods_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.application_methods
    ADD CONSTRAINT application_methods_pkey PRIMARY KEY (id);


--
-- Name: audit_logs audit_logs_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.audit_logs
    ADD CONSTRAINT audit_logs_pkey PRIMARY KEY (id);


--
-- Name: auth_identities auth_identities_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_identities
    ADD CONSTRAINT auth_identities_pkey PRIMARY KEY (id);


--
-- Name: campaigns campaigns_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.campaigns
    ADD CONSTRAINT campaigns_pkey PRIMARY KEY (id);


--
-- Name: captures captures_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.captures
    ADD CONSTRAINT captures_pkey PRIMARY KEY (id);


--
-- Name: cart_adjustments cart_adjustments_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cart_adjustments
    ADD CONSTRAINT cart_adjustments_pkey PRIMARY KEY (id);


--
-- Name: cart_items cart_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cart_items
    ADD CONSTRAINT cart_items_pkey PRIMARY KEY (id);


--
-- Name: cart_tax_lines cart_tax_lines_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cart_tax_lines
    ADD CONSTRAINT cart_tax_lines_pkey PRIMARY KEY (id);


--
-- Name: carts carts_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.carts
    ADD CONSTRAINT carts_pkey PRIMARY KEY (id);


--
-- Name: currencies currencies_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.currencies
    ADD CONSTRAINT currencies_pkey PRIMARY KEY (code);


--
-- Name: customer_addresses customer_addresses_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.customer_addresses
    ADD CONSTRAINT customer_addresses_pkey PRIMARY KEY (id);


--
-- Name: customer_group_customers customer_group_customers_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.customer_group_customers
    ADD CONSTRAINT customer_group_customers_pkey PRIMARY KEY (customer_id, customer_group_id);


--
-- Name: customer_groups customer_groups_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.customer_groups
    ADD CONSTRAINT customer_groups_pkey PRIMARY KEY (id);


--
-- Name: customers customers_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (id);


--
-- Name: discount_conditions discount_conditions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.discount_conditions
    ADD CONSTRAINT discount_conditions_pkey PRIMARY KEY (id);


--
-- Name: discount_rules discount_rules_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.discount_rules
    ADD CONSTRAINT discount_rules_pkey PRIMARY KEY (id);


--
-- Name: discounts discounts_code_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.discounts
    ADD CONSTRAINT discounts_code_key UNIQUE (code);


--
-- Name: discounts discounts_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.discounts
    ADD CONSTRAINT discounts_pkey PRIMARY KEY (id);


--
-- Name: event_logs event_logs_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.event_logs
    ADD CONSTRAINT event_logs_pkey PRIMARY KEY (id);


--
-- Name: fulfillment_addresses fulfillment_addresses_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fulfillment_addresses
    ADD CONSTRAINT fulfillment_addresses_pkey PRIMARY KEY (id);


--
-- Name: fulfillment_items fulfillment_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fulfillment_items
    ADD CONSTRAINT fulfillment_items_pkey PRIMARY KEY (id);


--
-- Name: fulfillment_labels fulfillment_labels_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fulfillment_labels
    ADD CONSTRAINT fulfillment_labels_pkey PRIMARY KEY (id);


--
-- Name: fulfillments fulfillments_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fulfillments
    ADD CONSTRAINT fulfillments_pkey PRIMARY KEY (id);


--
-- Name: inventory_items inventory_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.inventory_items
    ADD CONSTRAINT inventory_items_pkey PRIMARY KEY (id);


--
-- Name: inventory_levels inventory_levels_inventory_item_id_location_id_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.inventory_levels
    ADD CONSTRAINT inventory_levels_inventory_item_id_location_id_key UNIQUE (inventory_item_id, location_id);


--
-- Name: inventory_levels inventory_levels_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.inventory_levels
    ADD CONSTRAINT inventory_levels_pkey PRIMARY KEY (id);


--
-- Name: mfa_factors mfa_factors_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.mfa_factors
    ADD CONSTRAINT mfa_factors_pkey PRIMARY KEY (id);


--
-- Name: mfa_recovery_codes mfa_recovery_codes_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.mfa_recovery_codes
    ADD CONSTRAINT mfa_recovery_codes_pkey PRIMARY KEY (id);


--
-- Name: notifications notifications_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.notifications
    ADD CONSTRAINT notifications_pkey PRIMARY KEY (id);


--
-- Name: order_addresses order_addresses_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_addresses
    ADD CONSTRAINT order_addresses_pkey PRIMARY KEY (id);


--
-- Name: order_carts order_carts_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_carts
    ADD CONSTRAINT order_carts_pkey PRIMARY KEY (order_id, cart_id);


--
-- Name: order_credit_lines order_credit_lines_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_credit_lines
    ADD CONSTRAINT order_credit_lines_pkey PRIMARY KEY (id);


--
-- Name: order_fulfillments order_fulfillments_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_fulfillments
    ADD CONSTRAINT order_fulfillments_pkey PRIMARY KEY (order_id, fulfillment_id);


--
-- Name: order_items order_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_pkey PRIMARY KEY (id);


--
-- Name: order_status_history order_status_history_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_status_history
    ADD CONSTRAINT order_status_history_pkey PRIMARY KEY (id);


--
-- Name: order_summaries order_summaries_order_id_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_summaries
    ADD CONSTRAINT order_summaries_order_id_key UNIQUE (order_id);


--
-- Name: order_summaries order_summaries_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_summaries
    ADD CONSTRAINT order_summaries_pkey PRIMARY KEY (id);


--
-- Name: order_timelines order_timelines_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_timelines
    ADD CONSTRAINT order_timelines_pkey PRIMARY KEY (id);


--
-- Name: order_transactions order_transactions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_transactions
    ADD CONSTRAINT order_transactions_pkey PRIMARY KEY (id);


--
-- Name: orders orders_display_id_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_display_id_key UNIQUE (display_id);


--
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- Name: payment_collections payment_collections_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payment_collections
    ADD CONSTRAINT payment_collections_pkey PRIMARY KEY (id);


--
-- Name: payment_sessions payment_sessions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payment_sessions
    ADD CONSTRAINT payment_sessions_pkey PRIMARY KEY (id);


--
-- Name: payments payments_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payments
    ADD CONSTRAINT payments_pkey PRIMARY KEY (id);


--
-- Name: reservation_items pk_reservation_items; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.reservation_items
    ADD CONSTRAINT pk_reservation_items PRIMARY KEY (id);


--
-- Name: price_lists price_lists_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.price_lists
    ADD CONSTRAINT price_lists_pkey PRIMARY KEY (id);


--
-- Name: price_rules price_rules_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.price_rules
    ADD CONSTRAINT price_rules_pkey PRIMARY KEY (id);


--
-- Name: price_sets price_sets_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.price_sets
    ADD CONSTRAINT price_sets_pkey PRIMARY KEY (id);


--
-- Name: prices prices_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.prices
    ADD CONSTRAINT prices_pkey PRIMARY KEY (id);


--
-- Name: product_categories product_categories_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_categories
    ADD CONSTRAINT product_categories_pkey PRIMARY KEY (id);


--
-- Name: product_category_products product_category_products_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_category_products
    ADD CONSTRAINT product_category_products_pkey PRIMARY KEY (product_id, category_id);


--
-- Name: product_collections product_collections_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_collections
    ADD CONSTRAINT product_collections_pkey PRIMARY KEY (id);


--
-- Name: product_images product_images_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_images
    ADD CONSTRAINT product_images_pkey PRIMARY KEY (id);


--
-- Name: product_option_values product_option_values_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_option_values
    ADD CONSTRAINT product_option_values_pkey PRIMARY KEY (id);


--
-- Name: product_options product_options_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_options
    ADD CONSTRAINT product_options_pkey PRIMARY KEY (id);


--
-- Name: product_product_tags product_product_tags_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_product_tags
    ADD CONSTRAINT product_product_tags_pkey PRIMARY KEY (product_id, tag_id);


--
-- Name: product_tags product_tags_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_tags
    ADD CONSTRAINT product_tags_pkey PRIMARY KEY (id);


--
-- Name: product_types product_types_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_types
    ADD CONSTRAINT product_types_pkey PRIMARY KEY (id);


--
-- Name: product_types product_types_slug_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_types
    ADD CONSTRAINT product_types_slug_key UNIQUE (slug);


--
-- Name: product_variant_inventory_items product_variant_inventory_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_variant_inventory_items
    ADD CONSTRAINT product_variant_inventory_items_pkey PRIMARY KEY (variant_id, inventory_item_id);


--
-- Name: product_variant_price_sets product_variant_price_sets_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_variant_price_sets
    ADD CONSTRAINT product_variant_price_sets_pkey PRIMARY KEY (variant_id, price_set_id);


--
-- Name: product_variants product_variants_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_variants
    ADD CONSTRAINT product_variants_pkey PRIMARY KEY (id);


--
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- Name: promotions promotions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.promotions
    ADD CONSTRAINT promotions_pkey PRIMARY KEY (id);


--
-- Name: provider_identities provider_identities_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.provider_identities
    ADD CONSTRAINT provider_identities_pkey PRIMARY KEY (id);


--
-- Name: provider_identities provider_identities_provider_provider_user_id_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.provider_identities
    ADD CONSTRAINT provider_identities_provider_provider_user_id_key UNIQUE (provider, provider_user_id);


--
-- Name: refunds refunds_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.refunds
    ADD CONSTRAINT refunds_pkey PRIMARY KEY (id);


--
-- Name: region_countries region_countries_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.region_countries
    ADD CONSTRAINT region_countries_pkey PRIMARY KEY (id);


--
-- Name: region_countries region_countries_region_id_country_code_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.region_countries
    ADD CONSTRAINT region_countries_region_id_country_code_key UNIQUE (region_id, country_code);


--
-- Name: regions regions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.regions
    ADD CONSTRAINT regions_pkey PRIMARY KEY (id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: shipping_option_rules shipping_option_rules_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.shipping_option_rules
    ADD CONSTRAINT shipping_option_rules_pkey PRIMARY KEY (id);


--
-- Name: shipping_options shipping_options_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.shipping_options
    ADD CONSTRAINT shipping_options_pkey PRIMARY KEY (id);


--
-- Name: shipping_profiles shipping_profiles_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.shipping_profiles
    ADD CONSTRAINT shipping_profiles_pkey PRIMARY KEY (id);


--
-- Name: store_currencies store_currencies_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.store_currencies
    ADD CONSTRAINT store_currencies_pkey PRIMARY KEY (id);


--
-- Name: store_currencies store_currencies_store_id_currency_code_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.store_currencies
    ADD CONSTRAINT store_currencies_store_id_currency_code_key UNIQUE (store_id, currency_code);


--
-- Name: stores stores_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.stores
    ADD CONSTRAINT stores_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: verification_tokens verification_tokens_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.verification_tokens
    ADD CONSTRAINT verification_tokens_pkey PRIMARY KEY (id);


--
-- Name: idx_audit_logs_action; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_audit_logs_action ON public.audit_logs USING btree (action);


--
-- Name: idx_audit_logs_changes; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_audit_logs_changes ON public.audit_logs USING gin (changes);


--
-- Name: idx_audit_logs_created; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_audit_logs_created ON public.audit_logs USING btree (created_at DESC);


--
-- Name: idx_audit_logs_entity; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_audit_logs_entity ON public.audit_logs USING btree (entity_type, entity_id, created_at DESC);


--
-- Name: idx_audit_logs_performer; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_audit_logs_performer ON public.audit_logs USING btree (performed_by);


--
-- Name: idx_cart_adjustments_item; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_cart_adjustments_item ON public.cart_adjustments USING btree (cart_item_id);


--
-- Name: idx_cart_items_cart; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_cart_items_cart ON public.cart_items USING btree (cart_id);


--
-- Name: idx_cart_items_variant; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_cart_items_variant ON public.cart_items USING btree (variant_id);


--
-- Name: idx_cart_tax_lines_item; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_cart_tax_lines_item ON public.cart_tax_lines USING btree (cart_item_id);


--
-- Name: idx_customer_addresses_customer; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_customer_addresses_customer ON public.customer_addresses USING btree (customer_id);


--
-- Name: idx_customers_active; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_customers_active ON public.customers USING btree (id) WHERE (deleted_at IS NULL);


--
-- Name: idx_customers_email_unique; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX idx_customers_email_unique ON public.customers USING btree (email) WHERE (deleted_at IS NULL);


--
-- Name: idx_customers_metadata; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_customers_metadata ON public.customers USING gin (metadata);


--
-- Name: idx_event_logs_metadata; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_event_logs_metadata ON public.event_logs USING gin (metadata);


--
-- Name: idx_event_logs_severity; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_event_logs_severity ON public.event_logs USING btree (severity, created_at DESC);


--
-- Name: idx_event_logs_source; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_event_logs_source ON public.event_logs USING btree (source, created_at DESC);


--
-- Name: idx_event_logs_ttl; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_event_logs_ttl ON public.event_logs USING btree (created_at);


--
-- Name: idx_event_logs_type; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_event_logs_type ON public.event_logs USING btree (event_type, created_at DESC);


--
-- Name: idx_fulfillment_items_variant_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_fulfillment_items_variant_id ON public.fulfillment_items USING btree (variant_id);


--
-- Name: idx_fulfillments_carrier; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_fulfillments_carrier ON public.fulfillments USING btree (carrier);


--
-- Name: idx_fulfillments_display_id; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX idx_fulfillments_display_id ON public.fulfillments USING btree (display_id);


--
-- Name: idx_fulfillments_order; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_fulfillments_order ON public.fulfillments USING btree (order_id) WHERE (deleted_at IS NULL);


--
-- Name: idx_inventory_items_variant_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_inventory_items_variant_id ON public.inventory_items USING btree (variant_id);


--
-- Name: idx_inventory_levels_item; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_inventory_levels_item ON public.inventory_levels USING btree (inventory_item_id);


--
-- Name: idx_inventory_levels_location; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_inventory_levels_location ON public.inventory_levels USING btree (location_id);


--
-- Name: idx_order_addresses_order; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_order_addresses_order ON public.order_addresses USING btree (order_id);


--
-- Name: idx_order_credit_lines_order; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_order_credit_lines_order ON public.order_credit_lines USING btree (order_id);


--
-- Name: idx_order_items_order; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_order_items_order ON public.order_items USING btree (order_id);


--
-- Name: idx_order_items_variant; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_order_items_variant ON public.order_items USING btree (variant_id);


--
-- Name: idx_order_timelines_metadata; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_order_timelines_metadata ON public.order_timelines USING gin (metadata);


--
-- Name: idx_order_timelines_order; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_order_timelines_order ON public.order_timelines USING btree (order_id, created_at DESC);


--
-- Name: idx_order_timelines_status; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_order_timelines_status ON public.order_timelines USING btree (new_status);


--
-- Name: idx_order_transactions_order; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_order_transactions_order ON public.order_transactions USING btree (order_id);


--
-- Name: idx_orders_active; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_orders_active ON public.orders USING btree (id) WHERE (deleted_at IS NULL);


--
-- Name: idx_orders_customer; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_orders_customer ON public.orders USING btree (customer_id, created_at DESC) WHERE (deleted_at IS NULL);


--
-- Name: idx_orders_metadata; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_orders_metadata ON public.orders USING gin (metadata);


--
-- Name: idx_orders_status_created; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_orders_status_created ON public.orders USING btree (status, created_at DESC) WHERE (deleted_at IS NULL);


--
-- Name: idx_payments_collection; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_payments_collection ON public.payments USING btree (payment_collection_id);


--
-- Name: idx_payments_status; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_payments_status ON public.payments USING btree (status);


--
-- Name: idx_price_rules_price; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_price_rules_price ON public.price_rules USING btree (price_id);


--
-- Name: idx_prices_currency; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_prices_currency ON public.prices USING btree (currency_code);


--
-- Name: idx_prices_set; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_prices_set ON public.prices USING btree (price_set_id);


--
-- Name: idx_product_category_products_category; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_product_category_products_category ON public.product_category_products USING btree (category_id);


--
-- Name: idx_product_images_product; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_product_images_product ON public.product_images USING btree (product_id);


--
-- Name: idx_product_product_tags_tag; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_product_product_tags_tag ON public.product_product_tags USING btree (tag_id);


--
-- Name: idx_product_variant_inventory_items_item; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_product_variant_inventory_items_item ON public.product_variant_inventory_items USING btree (inventory_item_id);


--
-- Name: idx_product_variant_price_sets_set; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_product_variant_price_sets_set ON public.product_variant_price_sets USING btree (price_set_id);


--
-- Name: idx_product_variants_fts; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_product_variants_fts ON public.product_variants USING gin (to_tsvector('english'::regconfig, (((title)::text || ' '::text) || (COALESCE(sku, ''::character varying))::text)));


--
-- Name: idx_product_variants_metadata; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_product_variants_metadata ON public.product_variants USING gin (metadata);


--
-- Name: idx_product_variants_product; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_product_variants_product ON public.product_variants USING btree (product_id) WHERE (deleted_at IS NULL);


--
-- Name: idx_product_variants_sku; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_product_variants_sku ON public.product_variants USING btree (sku) WHERE (deleted_at IS NULL);


--
-- Name: idx_product_variants_sku_unique; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX idx_product_variants_sku_unique ON public.product_variants USING btree (sku) WHERE ((deleted_at IS NULL) AND (sku IS NOT NULL));


--
-- Name: idx_products_active; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_products_active ON public.products USING btree (id, handle) WHERE (deleted_at IS NULL);


--
-- Name: idx_products_category_status; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_products_category_status ON public.products USING btree (category_id, status) WHERE (deleted_at IS NULL);


--
-- Name: idx_products_collection_status; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_products_collection_status ON public.products USING btree (collection_id, status) WHERE (deleted_at IS NULL);


--
-- Name: idx_products_fts; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_products_fts ON public.products USING gin (to_tsvector('english'::regconfig, (((title)::text || ' '::text) || COALESCE(description, ''::text))));


--
-- Name: idx_products_handle_unique; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX idx_products_handle_unique ON public.products USING btree (handle) WHERE (deleted_at IS NULL);


--
-- Name: idx_products_metadata; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_products_metadata ON public.products USING gin (metadata);


--
-- Name: idx_products_sales; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_products_sales ON public.products USING btree (collection_id, status, created_at DESC) WHERE (deleted_at IS NULL);


--
-- Name: idx_provider_identities_auth; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_provider_identities_auth ON public.provider_identities USING btree (auth_identity_id);


--
-- Name: idx_region_countries_country; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_region_countries_country ON public.region_countries USING btree (country_code);


--
-- Name: idx_shipping_options_region; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_shipping_options_region ON public.shipping_options USING btree (region_id);


--
-- Name: idx_users_email_unique; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX idx_users_email_unique ON public.users USING btree (email) WHERE (deleted_at IS NULL);


--
-- Name: api_keys api_keys_created_by_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.api_keys
    ADD CONSTRAINT api_keys_created_by_fkey FOREIGN KEY (created_by) REFERENCES public.users(id);


--
-- Name: application_methods application_methods_promotion_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.application_methods
    ADD CONSTRAINT application_methods_promotion_id_fkey FOREIGN KEY (promotion_id) REFERENCES public.promotions(id) ON DELETE CASCADE;


--
-- Name: campaigns campaigns_promotion_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.campaigns
    ADD CONSTRAINT campaigns_promotion_id_fkey FOREIGN KEY (promotion_id) REFERENCES public.promotions(id) ON DELETE CASCADE;


--
-- Name: captures captures_created_by_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.captures
    ADD CONSTRAINT captures_created_by_fkey FOREIGN KEY (created_by) REFERENCES public.users(id);


--
-- Name: captures captures_payment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.captures
    ADD CONSTRAINT captures_payment_id_fkey FOREIGN KEY (payment_id) REFERENCES public.payments(id) ON DELETE CASCADE;


--
-- Name: cart_adjustments cart_adjustments_cart_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cart_adjustments
    ADD CONSTRAINT cart_adjustments_cart_item_id_fkey FOREIGN KEY (cart_item_id) REFERENCES public.cart_items(id) ON DELETE CASCADE;


--
-- Name: cart_items cart_items_cart_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cart_items
    ADD CONSTRAINT cart_items_cart_id_fkey FOREIGN KEY (cart_id) REFERENCES public.carts(id) ON DELETE CASCADE;


--
-- Name: cart_items cart_items_variant_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cart_items
    ADD CONSTRAINT cart_items_variant_id_fkey FOREIGN KEY (variant_id) REFERENCES public.product_variants(id);


--
-- Name: cart_tax_lines cart_tax_lines_cart_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cart_tax_lines
    ADD CONSTRAINT cart_tax_lines_cart_item_id_fkey FOREIGN KEY (cart_item_id) REFERENCES public.cart_items(id) ON DELETE CASCADE;


--
-- Name: carts carts_currency_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.carts
    ADD CONSTRAINT carts_currency_code_fkey FOREIGN KEY (currency_code) REFERENCES public.currencies(code);


--
-- Name: carts carts_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.carts
    ADD CONSTRAINT carts_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customers(id);


--
-- Name: carts carts_region_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.carts
    ADD CONSTRAINT carts_region_id_fkey FOREIGN KEY (region_id) REFERENCES public.regions(id);


--
-- Name: customer_addresses customer_addresses_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.customer_addresses
    ADD CONSTRAINT customer_addresses_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customers(id) ON DELETE CASCADE;


--
-- Name: customer_group_customers customer_group_customers_customer_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.customer_group_customers
    ADD CONSTRAINT customer_group_customers_customer_group_id_fkey FOREIGN KEY (customer_group_id) REFERENCES public.customer_groups(id) ON DELETE CASCADE;


--
-- Name: customer_group_customers customer_group_customers_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.customer_group_customers
    ADD CONSTRAINT customer_group_customers_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customers(id) ON DELETE CASCADE;


--
-- Name: discount_conditions discount_conditions_discount_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.discount_conditions
    ADD CONSTRAINT discount_conditions_discount_id_fkey FOREIGN KEY (discount_id) REFERENCES public.discounts(id) ON DELETE CASCADE;


--
-- Name: discount_rules discount_rules_discount_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.discount_rules
    ADD CONSTRAINT discount_rules_discount_id_fkey FOREIGN KEY (discount_id) REFERENCES public.discounts(id) ON DELETE CASCADE;


--
-- Name: carts fk_carts_billing_address; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.carts
    ADD CONSTRAINT fk_carts_billing_address FOREIGN KEY (billing_address_id) REFERENCES public.customer_addresses(id);


--
-- Name: carts fk_carts_shipping_address; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.carts
    ADD CONSTRAINT fk_carts_shipping_address FOREIGN KEY (shipping_address_id) REFERENCES public.customer_addresses(id);


--
-- Name: discount_conditions fk_discount_conditions_discount; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.discount_conditions
    ADD CONSTRAINT fk_discount_conditions_discount FOREIGN KEY (discount_id) REFERENCES public.discounts(id) ON DELETE CASCADE;


--
-- Name: order_status_history fk_order_status_history_order; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_status_history
    ADD CONSTRAINT fk_order_status_history_order FOREIGN KEY (order_id) REFERENCES public.orders(id);


--
-- Name: orders fk_orders_billing_address; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk_orders_billing_address FOREIGN KEY (billing_address_id) REFERENCES public.customer_addresses(id);


--
-- Name: orders fk_orders_shipping_address; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk_orders_shipping_address FOREIGN KEY (shipping_address_id) REFERENCES public.customer_addresses(id);


--
-- Name: reservation_items fk_reservation_items_inventory_item; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.reservation_items
    ADD CONSTRAINT fk_reservation_items_inventory_item FOREIGN KEY (inventory_item_id) REFERENCES public.inventory_items(id);


--
-- Name: fulfillment_addresses fulfillment_addresses_fulfillment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fulfillment_addresses
    ADD CONSTRAINT fulfillment_addresses_fulfillment_id_fkey FOREIGN KEY (fulfillment_id) REFERENCES public.fulfillments(id) ON DELETE CASCADE;


--
-- Name: fulfillment_items fulfillment_items_fulfillment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fulfillment_items
    ADD CONSTRAINT fulfillment_items_fulfillment_id_fkey FOREIGN KEY (fulfillment_id) REFERENCES public.fulfillments(id) ON DELETE CASCADE;


--
-- Name: fulfillment_items fulfillment_items_order_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fulfillment_items
    ADD CONSTRAINT fulfillment_items_order_item_id_fkey FOREIGN KEY (order_item_id) REFERENCES public.order_items(id);


--
-- Name: fulfillment_labels fulfillment_labels_fulfillment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fulfillment_labels
    ADD CONSTRAINT fulfillment_labels_fulfillment_id_fkey FOREIGN KEY (fulfillment_id) REFERENCES public.fulfillments(id) ON DELETE CASCADE;


--
-- Name: fulfillments fulfillments_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fulfillments
    ADD CONSTRAINT fulfillments_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id);


--
-- Name: fulfillments fulfillments_shipping_option_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fulfillments
    ADD CONSTRAINT fulfillments_shipping_option_id_fkey FOREIGN KEY (shipping_option_id) REFERENCES public.shipping_options(id);


--
-- Name: inventory_levels inventory_levels_inventory_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.inventory_levels
    ADD CONSTRAINT inventory_levels_inventory_item_id_fkey FOREIGN KEY (inventory_item_id) REFERENCES public.inventory_items(id) ON DELETE CASCADE;


--
-- Name: mfa_factors mfa_factors_auth_identity_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.mfa_factors
    ADD CONSTRAINT mfa_factors_auth_identity_id_fkey FOREIGN KEY (auth_identity_id) REFERENCES public.auth_identities(id) ON DELETE CASCADE;


--
-- Name: mfa_recovery_codes mfa_recovery_codes_auth_identity_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.mfa_recovery_codes
    ADD CONSTRAINT mfa_recovery_codes_auth_identity_id_fkey FOREIGN KEY (auth_identity_id) REFERENCES public.auth_identities(id) ON DELETE CASCADE;


--
-- Name: order_addresses order_addresses_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_addresses
    ADD CONSTRAINT order_addresses_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;


--
-- Name: order_carts order_carts_cart_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_carts
    ADD CONSTRAINT order_carts_cart_id_fkey FOREIGN KEY (cart_id) REFERENCES public.carts(id) ON DELETE CASCADE;


--
-- Name: order_carts order_carts_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_carts
    ADD CONSTRAINT order_carts_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;


--
-- Name: order_credit_lines order_credit_lines_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_credit_lines
    ADD CONSTRAINT order_credit_lines_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;


--
-- Name: order_fulfillments order_fulfillments_fulfillment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_fulfillments
    ADD CONSTRAINT order_fulfillments_fulfillment_id_fkey FOREIGN KEY (fulfillment_id) REFERENCES public.fulfillments(id) ON DELETE CASCADE;


--
-- Name: order_fulfillments order_fulfillments_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_fulfillments
    ADD CONSTRAINT order_fulfillments_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;


--
-- Name: order_items order_items_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;


--
-- Name: order_items order_items_variant_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_variant_id_fkey FOREIGN KEY (variant_id) REFERENCES public.product_variants(id);


--
-- Name: order_summaries order_summaries_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_summaries
    ADD CONSTRAINT order_summaries_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;


--
-- Name: order_timelines order_timelines_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_timelines
    ADD CONSTRAINT order_timelines_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;


--
-- Name: order_transactions order_transactions_currency_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_transactions
    ADD CONSTRAINT order_transactions_currency_code_fkey FOREIGN KEY (currency_code) REFERENCES public.currencies(code);


--
-- Name: order_transactions order_transactions_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_transactions
    ADD CONSTRAINT order_transactions_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;


--
-- Name: orders orders_cart_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_cart_id_fkey FOREIGN KEY (cart_id) REFERENCES public.carts(id);


--
-- Name: orders orders_currency_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_currency_code_fkey FOREIGN KEY (currency_code) REFERENCES public.currencies(code);


--
-- Name: orders orders_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customers(id);


--
-- Name: orders orders_region_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_region_id_fkey FOREIGN KEY (region_id) REFERENCES public.regions(id);


--
-- Name: payment_collections payment_collections_currency_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payment_collections
    ADD CONSTRAINT payment_collections_currency_code_fkey FOREIGN KEY (currency_code) REFERENCES public.currencies(code);


--
-- Name: payment_collections payment_collections_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payment_collections
    ADD CONSTRAINT payment_collections_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id);


--
-- Name: payment_sessions payment_sessions_currency_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payment_sessions
    ADD CONSTRAINT payment_sessions_currency_code_fkey FOREIGN KEY (currency_code) REFERENCES public.currencies(code);


--
-- Name: payment_sessions payment_sessions_payment_collection_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payment_sessions
    ADD CONSTRAINT payment_sessions_payment_collection_id_fkey FOREIGN KEY (payment_collection_id) REFERENCES public.payment_collections(id) ON DELETE CASCADE;


--
-- Name: payment_sessions payment_sessions_payment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payment_sessions
    ADD CONSTRAINT payment_sessions_payment_id_fkey FOREIGN KEY (payment_id) REFERENCES public.payments(id);


--
-- Name: payments payments_currency_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payments
    ADD CONSTRAINT payments_currency_code_fkey FOREIGN KEY (currency_code) REFERENCES public.currencies(code);


--
-- Name: payments payments_payment_collection_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payments
    ADD CONSTRAINT payments_payment_collection_id_fkey FOREIGN KEY (payment_collection_id) REFERENCES public.payment_collections(id) ON DELETE CASCADE;


--
-- Name: price_rules price_rules_price_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.price_rules
    ADD CONSTRAINT price_rules_price_id_fkey FOREIGN KEY (price_id) REFERENCES public.prices(id) ON DELETE CASCADE;


--
-- Name: prices prices_currency_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.prices
    ADD CONSTRAINT prices_currency_code_fkey FOREIGN KEY (currency_code) REFERENCES public.currencies(code);


--
-- Name: prices prices_price_list_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.prices
    ADD CONSTRAINT prices_price_list_id_fkey FOREIGN KEY (price_list_id) REFERENCES public.price_lists(id);


--
-- Name: prices prices_price_set_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.prices
    ADD CONSTRAINT prices_price_set_id_fkey FOREIGN KEY (price_set_id) REFERENCES public.price_sets(id) ON DELETE CASCADE;


--
-- Name: product_categories product_categories_parent_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_categories
    ADD CONSTRAINT product_categories_parent_category_id_fkey FOREIGN KEY (parent_category_id) REFERENCES public.product_categories(id);


--
-- Name: product_category_products product_category_products_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_category_products
    ADD CONSTRAINT product_category_products_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.product_categories(id) ON DELETE CASCADE;


--
-- Name: product_category_products product_category_products_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_category_products
    ADD CONSTRAINT product_category_products_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE;


--
-- Name: product_images product_images_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_images
    ADD CONSTRAINT product_images_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE;


--
-- Name: product_option_values product_option_values_option_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_option_values
    ADD CONSTRAINT product_option_values_option_id_fkey FOREIGN KEY (option_id) REFERENCES public.product_options(id) ON DELETE CASCADE;


--
-- Name: product_options product_options_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_options
    ADD CONSTRAINT product_options_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE;


--
-- Name: product_product_tags product_product_tags_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_product_tags
    ADD CONSTRAINT product_product_tags_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE;


--
-- Name: product_product_tags product_product_tags_tag_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_product_tags
    ADD CONSTRAINT product_product_tags_tag_id_fkey FOREIGN KEY (tag_id) REFERENCES public.product_tags(id) ON DELETE CASCADE;


--
-- Name: product_variant_inventory_items product_variant_inventory_items_inventory_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_variant_inventory_items
    ADD CONSTRAINT product_variant_inventory_items_inventory_item_id_fkey FOREIGN KEY (inventory_item_id) REFERENCES public.inventory_items(id) ON DELETE CASCADE;


--
-- Name: product_variant_inventory_items product_variant_inventory_items_variant_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_variant_inventory_items
    ADD CONSTRAINT product_variant_inventory_items_variant_id_fkey FOREIGN KEY (variant_id) REFERENCES public.product_variants(id) ON DELETE CASCADE;


--
-- Name: product_variant_price_sets product_variant_price_sets_price_set_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_variant_price_sets
    ADD CONSTRAINT product_variant_price_sets_price_set_id_fkey FOREIGN KEY (price_set_id) REFERENCES public.price_sets(id) ON DELETE CASCADE;


--
-- Name: product_variant_price_sets product_variant_price_sets_variant_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_variant_price_sets
    ADD CONSTRAINT product_variant_price_sets_variant_id_fkey FOREIGN KEY (variant_id) REFERENCES public.product_variants(id) ON DELETE CASCADE;


--
-- Name: product_variants product_variants_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_variants
    ADD CONSTRAINT product_variants_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE;


--
-- Name: products products_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.product_categories(id);


--
-- Name: products products_collection_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_collection_id_fkey FOREIGN KEY (collection_id) REFERENCES public.product_collections(id);


--
-- Name: provider_identities provider_identities_auth_identity_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.provider_identities
    ADD CONSTRAINT provider_identities_auth_identity_id_fkey FOREIGN KEY (auth_identity_id) REFERENCES public.auth_identities(id) ON DELETE CASCADE;


--
-- Name: refunds refunds_created_by_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.refunds
    ADD CONSTRAINT refunds_created_by_fkey FOREIGN KEY (created_by) REFERENCES public.users(id);


--
-- Name: refunds refunds_payment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.refunds
    ADD CONSTRAINT refunds_payment_id_fkey FOREIGN KEY (payment_id) REFERENCES public.payments(id) ON DELETE CASCADE;


--
-- Name: region_countries region_countries_region_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.region_countries
    ADD CONSTRAINT region_countries_region_id_fkey FOREIGN KEY (region_id) REFERENCES public.regions(id) ON DELETE CASCADE;


--
-- Name: regions regions_currency_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.regions
    ADD CONSTRAINT regions_currency_code_fkey FOREIGN KEY (currency_code) REFERENCES public.currencies(code);


--
-- Name: shipping_option_rules shipping_option_rules_shipping_option_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.shipping_option_rules
    ADD CONSTRAINT shipping_option_rules_shipping_option_id_fkey FOREIGN KEY (shipping_option_id) REFERENCES public.shipping_options(id) ON DELETE CASCADE;


--
-- Name: shipping_options shipping_options_region_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.shipping_options
    ADD CONSTRAINT shipping_options_region_id_fkey FOREIGN KEY (region_id) REFERENCES public.regions(id);


--
-- Name: shipping_options shipping_options_shipping_profile_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.shipping_options
    ADD CONSTRAINT shipping_options_shipping_profile_id_fkey FOREIGN KEY (shipping_profile_id) REFERENCES public.shipping_profiles(id);


--
-- Name: store_currencies store_currencies_currency_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.store_currencies
    ADD CONSTRAINT store_currencies_currency_code_fkey FOREIGN KEY (currency_code) REFERENCES public.currencies(code);


--
-- Name: store_currencies store_currencies_store_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.store_currencies
    ADD CONSTRAINT store_currencies_store_id_fkey FOREIGN KEY (store_id) REFERENCES public.stores(id) ON DELETE CASCADE;


--
-- Name: stores stores_default_currency_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.stores
    ADD CONSTRAINT stores_default_currency_code_fkey FOREIGN KEY (default_currency_code) REFERENCES public.currencies(code);


--
-- Name: users users_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- Name: verification_tokens verification_tokens_auth_identity_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.verification_tokens
    ADD CONSTRAINT verification_tokens_auth_identity_id_fkey FOREIGN KEY (auth_identity_id) REFERENCES public.auth_identities(id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--