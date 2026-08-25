-- 1. Enable extension pgcrypto
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- 2. Create custom function uuidv7
CREATE OR REPLACE FUNCTION uuidv7() 
RETURNS uuid 
AS $$
DECLARE
    unix_time_ms bytea;
    retval bytea;
BEGIN
    unix_time_ms := substring(int8send(floor(extract(epoch FROM clock_timestamp()) * 1000)::bigint) FROM 3 FOR 6);
    retval := unix_time_ms || gen_random_bytes(10);
    retval := set_byte(retval, 6, (get_byte(retval, 6) & 15) | 112);
    retval := set_byte(retval, 8, (get_byte(retval, 8) & 63) | 128);
    RETURN encode(retval, 'hex')::uuid;
END;
$$ LANGUAGE plpgsql VOLATILE;