## Why

Token revocation currently uses SHA-256 hashing of individual JWT tokens, which cannot support device-level session management. The `revokeAllForUser()` method stores a marker `ALL_{userId}_{userType}` but `isRevoked()` only checks SHA-256 hashes, making bulk force-revoke ineffective. Additionally, there is no way to distinguish between sessions from different devices of the same user, and no mechanism to reuse sessions for clients that do not provide device identification.

## What Changes

- **Add `deviceId` to JWT claims**: Accept optional `X-Device-Id` header from client; embed `device_id` claim in JWT tokens for session differentiation.
- **Replace SHA-256 hashing with `ALL`-format markers**: Remove `hashToken()` approach. Use unified `ALL_{userId}_{userType}_{deviceId}` format for all revocation operations.
- **Two revocation prefixes**: `logout:` prefix for user-initiated logout (device-specific) and `blacklist:` prefix for admin force-revoke (all devices).
- **Session reuse for null deviceId**: If client sends no `X-Device-Id`, reuse existing non-expired token instead of creating duplicates.
- **Fix `isRevoked()`**: Check both `logout:ALL_{userId}_{userType}_{deviceId}` (device-specific) and `blacklist:ALL_{userId}_{userType}` (global) markers.

## Capabilities

### New Capabilities
- `device-auth-session`: Device-aware login session management supporting device identification, session reuse for un-identified clients, and per-device token lifecycle tracking.

### Modified Capabilities
- `token-blacklist`: Switch from SHA-256 single-token hashing to `ALL`-format markers. Add `LOGOUT_KEY_PREFIX` and `BLACKLIST_KEY_PREFIX`. Support device-scoped and user-scoped revocation checks.
- `customer-auth-logout`: Add `deviceId` parameter to logout flow. Change from individual token revocation to device-scoped `ALL`-format revocation.
- `admin-auth-logout`: Add `deviceId` parameter to admin logout flow. Use `BLACKLIST_KEY_PREFIX` for force-revoke of all user devices.
- `admin-force-logout`: Change backend to use `BLACKLIST_KEY_PREFIX` with `ALL_{userId}_{userType}` marker for cross-device revocation.

## Impact

- **Files modified**:
  - `JwtTokenProvider.java` — add `deviceId` claim to JWT, new `generateAccessToken` overloads
  - `TokenBlacklistService.java` — remove `hashToken()`, `revoke()` single-token method. Add `LOGOUT_KEY_PREFIX`. Rewrite `revokeAllForUser()` to accept `deviceId`. Rewrite `isRevoked()` to check both prefixes.
  - `AuthService.java` — `login()` adds session reuse logic for null deviceId. `logout()` switches to device-scoped ALL marker.
  - `AuthController.java` — extract `X-Device-Id` header, pass to service layer.
  - `AdminAuthController.java` — extract `X-Device-Id` header.
  - `UserAdminService.java` — `revokeTokens()` uses `BLACKLIST_KEY_PREFIX` format.
- **Database**: Schema may need new indexes on `RevokedToken` for the new marker patterns.
- **Deprecation**: `revoke(token)` method and `hashToken()` will be removed (no longer needed).
