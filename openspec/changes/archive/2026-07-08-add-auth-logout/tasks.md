## 1. Database & Migration

- [x] 1.1 Create Flyway migration script `V__create_revoked_tokens.sql` for `revoked_tokens` table with columns: `id`, `token_hash`, `token_type`, `user_id`, `user_type`, `revoked_at`, `expires_at`, `reason`, `ip_address`
- [x] 1.2 Add indexes on `(token_hash)` and `(user_id, user_type)`

## 2. Entity & Repository

- [x] 2.1 Create `RevokedToken` JPA entity in [`modules/identity/domain/entity/`](../../../../../modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/)
- [x] 2.2 Create `RevokedTokenRepository` in [`modules/identity/domain/repository/`](../../../../../modules/identity/src/main/java/com/v8n/modules/identity/domain/repository/) with methods: `findByTokenHash`, `findByUserIdAndUserType`, `deleteByExpiresAtBefore`

## 3. Redis Configuration

- [x] 3.1 Add `spring-boot-starter-data-redis` dependency to [`modules/identity/build.gradle`](../../../../../modules/identity/build.gradle)
- [x] 3.2 Create `RedisConfig` in [`modules/identity/infrastructure/config/`](../../../../../modules/identity/src/main/java/com/v8n/modules/identity/infrastructure/config/) with `RedisTemplate<String, String>` bean and connection factory

## 4. Token Blacklist Service

- [x] 4.1 Create `TokenBlacklistService` in [`modules/identity/infrastructure/security/`](../../../../../modules/identity/src/main/java/com/v8n/modules/identity/infrastructure/security/) with methods:
  - `void revoke(String token, String tokenType, String userId, String userType, String reason, String ipAddress)` — hash token, save to DB + Redis
  - `boolean isRevoked(String token)` — check Redis first, fallback DB
  - `void revokeAllForUser(String userId, String userType, String reason)` — revoke all tokens for user by inserting a marker revocation entry
- [x] 4.2 Implement SHA-256 hashing utility for token hashing

## 5. Extend JwtAuthenticationFilter

- [x] 5.1 Inject `TokenBlacklistService` into [`JwtAuthenticationFilter`](../../../../../modules/identity/src/main/java/com/v8n/modules/identity/infrastructure/security/JwtAuthenticationFilter.java)
- [x] 5.2 Add blacklist check after token validation: if `tokenBlacklistService.isRevoked(jwt)` → skip authentication, return 401
- [x] 5.3 Add Redis fallback logging

## 6. Customer Logout (Self-Service) — AuthService

- [x] 6.1 Add method `logout(String accessToken, String refreshToken, String userId, String ipAddress)` to [`AuthService`](../../../../../modules/identity/src/main/java/com/v8n/modules/identity/application/service/AuthService.java) — revoke both tokens
- [x] 6.2 Add method `logoutAll(String userId, String ipAddress)` to `AuthService` — revoke all tokens for user

## 7. Admin Logout (Self-Service) — AdminAuthService

- [x] 7.1 Add method `logout(String accessToken, String refreshToken, String userId, String ipAddress, String userAgent)` to [`AdminAuthService`](../../../../../modules/identity/src/main/java/com/v8n/modules/identity/application/service/AdminAuthService.java) — revoke both tokens + record login history
- [x] 7.2 Add method `logoutAll(String userId, String ipAddress, String userAgent)` to `AdminAuthService` — revoke all + record audit

## 8. REST Controllers — Self-Service Logout

- [x] 8.1 Add `@PostMapping("/logout")` to [`AuthController`](../../../../../modules/identity/src/main/java/com/v8n/modules/identity/interfaces/rest/AuthController.java) — call `authService.logout()`
- [x] 8.2 Add `@PostMapping("/logout/all")` to `AuthController` — call `authService.logoutAll()`
- [x] 8.3 Add `@PostMapping("/logout")` to [`AdminAuthController`](../../../../../modules/identity/src/main/java/com/v8n/modules/identity/interfaces/rest/AdminAuthController.java) — call `adminAuthService.logout()`
- [x] 8.4 Add `@PostMapping("/logout/all")` to `AdminAuthController` — call `adminAuthService.logoutAll()`

## 9. Admin Force-Logout — UserAdmin (Admin Users)

- [x] 9.1 Add method `revokeTokens(String targetUserId, String adminId)` to [`UserAdminService`](../../../../../modules/identity/src/main/java/com/v8n/modules/identity/application/service/UserAdminService.java) — call `tokenBlacklistService.revokeAllForUser()` + record login history
- [x] 9.2 Add `@PostMapping("/{id}/revoke-tokens")` with `@PreAuthorize("hasAuthority('user:update')")` to [`UserAdminController`](../../../../../modules/identity/src/main/java/com/v8n/modules/identity/interfaces/rest/UserAdminController.java:32) — call `userAdminService.revokeTokens()`

## 10. Admin Customer Management — List & Force-Logout

- [x] 10.1 Add method `listCustomers(String query, int page, int size)` to [`CustomerService`](../../../../../modules/identity/src/main/java/com/v8n/modules/identity/application/service/CustomerService.java) — query `UserRepository` with email/name search + pagination
- [x] 10.2 Add method `revokeTokens(String customerUserId, String adminId)` to `CustomerService` — call `tokenBlacklistService.revokeAllForUser()` with `userType = "customer"`
- [x] 10.3 Create [`AdminCustomerController`](../../../../../modules/identity/src/main/java/com/v8n/modules/identity/interfaces/rest/) at `/api/v1/admin/customers` with:
  - `@GetMapping` — list customers (search, pagination), `@PreAuthorize("hasAuthority('user:read')")`
  - `@PostMapping("/{id}/revoke-tokens")` — force-revoke customer, `@PreAuthorize("hasAuthority('user:update')")`

## 11. Security Configuration

- [x] 11.1 Update [`SecurityConfig`](../../../../../modules/identity/src/main/java/com/v8n/modules/identity/infrastructure/security/SecurityConfig.java) — ensure `/api/v1/auth/logout/**`, `/api/v1/auth/admin/logout/**`, `/api/v1/users/*/revoke-tokens`, `/api/v1/admin/customers/**` are secured (already covered by `.anyRequest().authenticated()`, verify)

## 12. Scheduled Cleanup

- [x] 12.1 Create scheduled task in `identity` module to clean up expired `revoked_tokens` records daily (delete where `expires_at < now() - 7 days`)

## 13. Error Codes

- [x] 13.1 Add `TOKEN_REVOKED` error code to [`ErrorCode`](../../../../../modules/core/src/main/java/com/v8n/modules/core/application/exception/ErrorCode.java) enum if needed
