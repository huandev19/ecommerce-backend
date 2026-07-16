# Token Blacklist

## Purpose

Provide a robust token revocation mechanism using Redis for fast lookup with database persistence for audit and recovery. Every authenticated request is checked against the blacklist before processing, with Redis as primary cache and PostgreSQL as fallback.

## Requirements

### Requirement: Token blacklist check on every request
The system SHALL check every authenticated request against the token blacklist before processing. The check supports two marker formats: device-scoped (`logout:ALL_{userId}_{userType}_{deviceId}`) for user logout and user-scoped (`blacklist:ALL_{userId}_{userType}`) for admin force-revoke.

#### Scenario: Valid token not in any blacklist marker
- **WHEN** request contains a valid JWT that is NOT in any blacklist marker
- **THEN** system SHALL authenticate the request normally
- **AND** system SHALL process the request

#### Scenario: Token in blacklist (device logout)
- **WHEN** request contains a JWT that matches `logout:ALL_{userId}_{userType}_{deviceId}` in Redis or DB
- **THEN** system SHALL NOT authenticate the request
- **AND** system SHALL return `401 Unauthorized`

#### Scenario: Token in blacklist (force-revoke)
- **WHEN** request contains a JWT that matches `blacklist:ALL_{userId}_{userType}` in Redis or DB
- **THEN** system SHALL NOT authenticate the request
- **AND** system SHALL return `401 Unauthorized`

#### Scenario: Redis unavailable fallback to DB
- **WHEN** Redis is unavailable
- **THEN** system SHALL fallback to check the `revoked_tokens` table in PostgreSQL for both marker formats
- **AND** system SHALL log a warning
- **AND** system SHALL still validate the token correctly

### Requirement: Token blacklist uses Redis cache
The system SHALL cache revoked token markers in Redis for fast lookup with appropriate TTL.

#### Scenario: Logout marker cached with TTL
- **WHEN** a token is revoked via logout
- **THEN** system SHALL store the marker in Redis with key `logout:ALL_{userId}_{userType}_{deviceId}`
- **AND** system SHALL set TTL equal to the token's remaining lifetime
- **AND** the entry SHALL be automatically removed after TTL expires

#### Scenario: Force-revoke marker cached with long TTL
- **WHEN** an admin force-revokes all tokens for a user
- **THEN** system SHALL store the marker in Redis with key `blacklist:ALL_{userId}_{userType}`
- **AND** system SHALL set TTL to 30 days

#### Scenario: Redis key auto-expires
- **WHEN** a marker's TTL in Redis expires
- **THEN** the blacklist entry SHALL be automatically removed
- **AND** subsequent requests with that token SHALL be checked only against remaining markers

### Requirement: Revoked token storage in database
The system SHALL persist revoked token markers in the `revoked_tokens` table for audit and recovery.

#### Scenario: Marker stored after revocation
- **WHEN** a token is revoked via logout or force-revoke
- **THEN** system SHALL insert a record into `revoked_tokens` with the ALL-format marker, type, user info, and timestamps

#### Scenario: Database cleanup
- **WHEN** a scheduled cleanup job runs daily
- **THEN** system SHALL delete `revoked_tokens` records where `expires_at` is older than 7 days

### Requirement: Admin can force-revoke all tokens of any user
The system SHALL allow an authenticated admin to revoke all tokens of another user (admin or customer) via a dedicated admin endpoint, using the `blacklist:` prefix.

#### Scenario: Admin force-revokes another admin user
- **WHEN** admin with permission `user:update` sends `POST /api/v1/users/{id}/revoke-tokens`
- **THEN** system SHALL store marker `blacklist:ALL_{targetUserId}_admin` in Redis (TTL: 30 days)
- **AND** system SHALL record the force-revoke action in `login_history`
- **AND** system SHALL return `200 OK`

#### Scenario: Admin force-revokes a customer user
- **WHEN** admin sends `POST /api/v1/admin/customers/{id}/revoke-tokens`
- **THEN** system SHALL store marker `blacklist:ALL_{targetUserId}_customer` in Redis (TTL: 30 days)
- **AND** system SHALL store `reason = "admin_force_logout"` in `revoked_tokens`
- **AND** system SHALL return `200 OK`

#### Scenario: Non-admin cannot force-revoke
- **WHEN** non-admin or admin without `user:update` permission sends a force-revoke request
- **THEN** system SHALL return `403 Forbidden`
