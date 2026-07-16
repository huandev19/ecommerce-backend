## Context

The current token revocation system uses two approaches:
1. **`revoke(token)`**: SHA-256 hash of individual JWT → stored in DB + Redis. Used by user logout.
2. **`revokeAllForUser(userId, userType)`**: Marker string `ALL_{userId}_{userType}` → stored in DB + Redis. Used by admin force-revoke.

The `isRevoked()` method only checks SHA-256 hashes of the incoming JWT, so the `ALL` marker from `revokeAllForUser()` is never matched — making admin force-revoke ineffective. Additionally, there is no device-level session tracking, so all logins from the same user cannot be distinguished.

**Current architecture constraints:**
- JWT tokens contain `sub` (userId) and optionally `actor_type` for admin tokens
- No `device_id` claim exists
- Revoked tokens are persisted in `RevokedToken` entity (DB) + Redis cache
- Redis keys use `blacklist:{SHA256}` format

## Goals / Non-Goals

**Goals:**
- Add optional `device_id` to JWT claims for session differentiation
- Replace SHA-256 hashing with unified `ALL_{userId}_{userType}_{deviceId}` marker format
- Two Redis prefixes: `logout:` for user logout (per-device), `blacklist:` for admin revoke (all-devices)
- Session reuse: if client sends no `X-Device-Id`, reuse existing token instead of creating new
- Fix `isRevoked()` to check both prefixes
- Remove deprecated `hashToken()` and single-token `revoke()` method

**Non-Goals:**
- Not changing the login flow for identified clients (with deviceId)
- Not adding User-Agent or IP-based device detection (client-driven only)
- Not modifying the `RevokedToken` DB entity schema (marker format fits existing `tokenHash` field)

## Decisions

### Decision 1: Device ID source — optional client header `X-Device-Id`

**Chosen:** Read `X-Device-Id` header from `HttpServletRequest`. If absent → `deviceId = null`.

**Rationale:**
- Client has full control over device identity
- No server-side device fingerprinting complexity
- Backward compatible: old clients that don't send the header still work

**Alternative considered:** Server-generated UUID on first login. Rejected because client cannot correlate sessions across re-logins, and null-device reuse would be ambiguous.

### Decision 2: ALL-format marker structure

**Chosen:**
| Operation | Prefix | Marker | Redis Key |
|-----------|--------|--------|-----------|
| User logout (device-specific) | `LOGOUT_KEY_PREFIX` = `"logout:"` | `ALL_{userId}_{userType}_{deviceId}` | `logout:ALL_uuid_customer_iphone` |
| Admin force-revoke (all devices) | `BLACKLIST_KEY_PREFIX` = `"blacklist:"` | `ALL_{userId}_{userType}` | `blacklist:ALL_uuid_admin` |

**Rationale:**
- `deviceId` at the end enables wildcard-free check: admin revoke checks the broader `ALL_{userId}_{userType}` which covers all devices
- When `deviceId = null`, the marker becomes `ALL_{userId}_{userType}_null` (only affects the null-device session)
- Two prefixes allow different TTLs: logout can be short-lived (token expiry), admin revoke is longer (30 days)

### Decision 3: `isRevoked()` check order

**Chosen:**
1. `logout:ALL_{userId}_{userType}_{deviceId}` — check device-specific logout (Redis → DB)
2. `blacklist:ALL_{userId}_{userType}` — check global admin revoke (Redis → DB)

**Rationale:**
- Device-specific check is faster (user ID + device ID from JWT, no need to parse separately)
- Global check catches admin force-revoke regardless of device
- Redis-first, DB-fallback pattern is preserved

### Decision 4: Session reuse for null deviceId

**Chosen:** On login without `X-Device-Id`, query `RevokedToken` table for an existing non-expired token with `userId + userType + deviceId=null`. If found and not revoked, return the existing token. Otherwise create new.

**Rationale:**
- Prevents unbounded token creation for clients without device identification
- Avoids forcing re-login on every page refresh for browser-based clients
- Security is acceptable: the null-device bucket treats all unidentified clients as one session

## Risks / Trade-offs

| Risk | Mitigation |
|------|------------|
| [Security] Multiple browsers on same machine share null-device session → one logout logs out all | Documented behavior; clients should send `X-Device-Id` for dedicated sessions |
| [Performance] Each login checks `RevokedToken` table for null-device reuse | Index on `(userId, userType, tokenHash)` covers the lookup |
| [Data migration] Existing `RevokedToken` rows with SHA-256 hashes become orphaned | Existing tokens expire naturally; no migration needed |
| [Backward compatibility] Old JWT tokens lack `device_id` claim → `extractDeviceId()` returns null | Handled gracefully; null deviceId behaves as legacy session |
