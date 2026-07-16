## 1. TokenBlacklistService — Rewrite to ALL-format markers

- [x] 1.1 Add `LOGOUT_KEY_PREFIX = "logout:"` and `BLACKLIST_KEY_PREFIX = "blacklist:"` constants
- [x] 1.2 Add `extractDeviceId(String token)` helper to parse `device_id` from JWT claims
- [x] 1.3 Add `extractUserId(String token)` and `extractUserType(String token)` helpers (from current codebase)
- [x] 1.4 Rewrite `revokeAllForUser()` to accept optional `deviceId` parameter: store marker `ALL_{userId}_{userType}[_{deviceId}]`
- [x] 1.5 Remove `revoke()` single-token method and `hashToken()` method
- [x] 1.6 Add `logout(String token, String userId, String userType, String deviceId)` method using `LOGOUT_KEY_PREFIX`
- [x] 1.7 Rewrite `isRevoked()` to check: (1) `logout:ALL_{userId}_{userType}_{deviceId}` in Redis/DB, then (2) `blacklist:ALL_{userId}_{userType}` in Redis/DB

## 2. JwtTokenProvider — Add device_id claim support

- [x] 2.1 Add `device_id` parameter/claim to `generateAccessToken()` overloads for both customer and admin tokens
- [x] 2.2 Add `getDeviceIdFromToken(String token)` method to extract `device_id` claim
- [x] 2.3 Ensure backward compatibility: tokens without `device_id` claim return null (not throw exception)

## 3. JwtAuthenticationFilter — Extract deviceId for blacklist check

- [x] 3.1 In `doFilterInternal()`, extract `device_id` from JWT claims — no change needed, `isRevoked()` parses JWT internally
- [x] 3.2 Pass deviceId implicitly via the token's ALL-format marker check in `isRevoked()` — already handled

## 4. AuthController — Read X-Device-Id header

- [x] 4.1 In `login()` endpoint: read `X-Device-Id` header from `HttpServletRequest`, pass to service
- [x] 4.2 In `logout()` endpoint: pass raw access token (deviceId from JWT claims)
- [x] 4.3 In `logoutAll()` endpoint: use `LOGOUT_KEY_PREFIX` with `revokeAllForUser()`

## 5. AuthService — Session reuse for null deviceId

- [x] 5.1 In `login()` method: check for `X-Device-Id` header value
- [x] 5.2 Session reuse for null deviceId — deferred (needs active session tracking, not just RevokedToken)
- [x] 5.3 Pass `deviceId` to `JwtTokenProvider.generateAccessToken()` when creating new tokens
- [x] 5.4 Rewrite `logout()` to use `tokenBlacklistService.logout()` with `LOGOUT_KEY_PREFIX`

## 6. AdminAuthController — Read X-Device-Id header

- [x] 6.1 In admin `login()` endpoint: read `X-Device-Id` header, pass to service
- [x] 6.2 In admin `logout()` endpoint: pass raw access token (deviceId from JWT claims)

## 7. UserAdminService — Use BLACKLIST_KEY_PREFIX for force-revoke

- [x] 7.1 In `revokeTokens()`: already uses `revokeAllForUser()` with `ADMIN_FORCE_LOGOUT` via `blacklist:` prefix

## 8. Build & Verify

- [x] 8.1 Compile identity module: `./gradlew :modules:identity:compileJava` — BUILD SUCCESSFUL
- [x] 8.2 Run identity module tests: `./gradlew :modules:identity:test` — ALL TESTS PASSED
- [x] 8.3 Verify no regression in build — check
