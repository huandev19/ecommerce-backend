## ADDED Requirements

### Requirement: Token blacklist check on every request
The system SHALL check every authenticated request against the token blacklist before processing.

#### Scenario: Valid token not in blacklist
- **WHEN** request contains a valid JWT that is NOT in the blacklist
- **THEN** system SHALL authenticate the request normally
- **AND** system SHALL process the request

#### Scenario: Token in blacklist
- **WHEN** request contains a JWT that IS in the blacklist (Redis or DB)
- **THEN** system SHALL NOT authenticate the request
- **AND** system SHALL return `401 Unauthorized`

#### Scenario: Redis unavailable fallback to DB
- **WHEN** Redis is unavailable
- **THEN** system SHALL fallback to check the `revoked_tokens` table in PostgreSQL
- **AND** system SHALL log a warning
- **AND** system SHALL still validate the token correctly

### Requirement: Token blacklist uses Redis cache
The system SHALL cache revoked tokens in Redis for fast lookup with TTL matching token expiration.

#### Scenario: Revoked token cached with TTL
- **WHEN** a token is revoked via logout
- **THEN** system SHALL store the token hash in Redis with key `blacklist:{sha256}`
- **AND** system SHALL set TTL equal to the token's remaining lifetime
- **AND** the entry SHALL be automatically removed after TTL expires

#### Scenario: Redis key auto-expires
- **WHEN** a token's TTL in Redis expires
- **THEN** the blacklist entry SHALL be automatically removed
- **AND** subsequent requests with that expired token SHALL be rejected by JWT validation (token expired), not by blacklist

### Requirement: Revoked token storage in database
The system SHALL persist revoked token records in the `revoked_tokens` table for audit and recovery.

#### Scenario: Token stored after revocation
- **WHEN** a token is revoked
- **THEN** system SHALL insert a record into `revoked_tokens` with token hash, type, user info, and timestamps

#### Scenario: Database cleanup
- **WHEN** a scheduled cleanup job runs daily
- **THEN** system SHALL delete `revoked_tokens` records where `expires_at` is older than 7 days

### Requirement: Admin can force-revoke all tokens of any user
The system SHALL allow an authenticated admin to revoke all tokens of another user (admin or customer) via a dedicated admin endpoint.

#### Scenario: Admin force-revokes another admin user
- **WHEN** admin with permission `user:update` sends `POST /api/v1/users/{id}/revoke-tokens`
- **THEN** system SHALL revoke all tokens for the target admin user
- **AND** system SHALL record the force-revoke action in `login_history`
- **AND** system SHALL return `200 OK`

#### Scenario: Admin force-revokes a customer user
- **WHEN** admin sends `POST /api/v1/admin/customers/{id}/revoke-tokens`
- **THEN** system SHALL revoke all tokens for the target customer user
- **AND** system SHALL store `reason = "admin_force_logout"` in `revoked_tokens`
- **AND** system SHALL return `200 OK`

#### Scenario: Non-admin cannot force-revoke
- **WHEN** non-admin or admin without `user:update` permission sends a force-revoke request
- **THEN** system SHALL return `403 Forbidden`
