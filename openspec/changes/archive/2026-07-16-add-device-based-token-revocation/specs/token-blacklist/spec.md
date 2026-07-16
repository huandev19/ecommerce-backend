# Token Blacklist

## Purpose

Provide a robust token revocation mechanism using Redis for fast lookup with database persistence for audit and recovery. Every authenticated request is checked against the blacklist before processing, with Redis as primary cache and PostgreSQL as fallback.

## ADDED Requirements

### Requirement: Token blacklist supports device-scoped revocation
The system SHALL support two marker formats for token revocation: device-scoped (`ALL_{userId}_{userType}_{deviceId}`) for user logout and user-scoped (`ALL_{userId}_{userType}`) for admin force-revoke.

#### Scenario: Device-scoped logout marker stored
- **WHEN** a user logs out with `X-Device-Id: iphone-123`
- **THEN** system SHALL store marker `logout:ALL_{userId}_{userType}_iphone-123` in Redis
- **AND** system SHALL insert a record into `revoked_tokens` with `tokenHash = "ALL_{userId}_{userType}_iphone-123"`
- **AND** system SHALL set Redis TTL to the token's remaining lifetime

#### Scenario: User-scoped force-revoke marker stored
- **WHEN** an admin force-revokes all tokens for a user
- **THEN** system SHALL store marker `blacklist:ALL_{userId}_{userType}` in Redis
- **AND** system SHALL set Redis TTL to 30 days
- **AND** system SHALL insert a record into `revoked_tokens` with `tokenHash = "ALL_{userId}_{userType}"`

#### Scenario: isRevoked checks both logout and blacklist prefixes
- **WHEN** `isRevoked(token)` is called
- **THEN** system SHALL first check `logout:ALL_{userId}_{userType}_{deviceId}` in Redis (then DB fallback)
- **AND** system SHALL then check `blacklist:ALL_{userId}_{userType}` in Redis (then DB fallback)
- **AND** system SHALL return `true` if EITHER check matches

### Requirement: Null deviceId handled gracefully
The system SHALL handle the absence of `X-Device-Id` header by using `null` as the deviceId value, forming marker `ALL_{userId}_{userType}_null`.

#### Scenario: Login without deviceId
- **WHEN** user logs in without sending `X-Device-Id` header
- **THEN** system SHALL generate JWT with `device_id: null`
- **AND** system SHALL not duplicate sessions — if a non-revoked token with `deviceId=null` exists for this user, return it

#### Scenario: Logout without deviceId
- **WHEN** user logs out and their JWT has `device_id: null`
- **THEN** system SHALL store marker `logout:ALL_{userId}_{userType}_null`
- **AND** system SHALL revoke only the null-device session (not device-specific sessions)

## MODIFIED Requirements

### Requirement: Token blacklist check on every request
The system SHALL check every authenticated request against the token blacklist before processing.

#### Scenario: Valid token not in blacklist
- **WHEN** request contains a valid JWT that is NOT in any blacklist marker
- **THEN** system SHALL authenticate the request normally
- **AND** system SHALL process the request

#### Scenario: Token in blacklist (device logout or force-revoke)
- **WHEN** request contains a JWT that matches either `logout:ALL_{userId}_{userType}_{deviceId}` or `blacklist:ALL_{userId}_{userType}` in Redis or DB
- **THEN** system SHALL NOT authenticate the request
- **AND** system SHALL return `401 Unauthorized`

#### Scenario: Redis unavailable fallback to DB
- **WHEN** Redis is unavailable
- **THEN** system SHALL fallback to check the `revoked_tokens` table in PostgreSQL for both marker formats
- **AND** system SHALL log a warning
- **AND** system SHALL still validate the token correctly

### Requirement: Token blacklist uses Redis cache
The system SHALL cache revoked token markers in Redis for fast lookup with appropriate TTL.

#### Scenario: Revoked token cached with TTL
- **WHEN** a token is revoked via logout
- **THEN** system SHALL store the marker in Redis with key `logout:ALL_{userId}_{userType}_{deviceId}`
- **AND** system SHALL set TTL equal to the token's remaining lifetime
- **AND** the entry SHALL be automatically removed after TTL expires

#### Scenario: Blacklist key auto-expires
- **WHEN** a marker's TTL in Redis expires
- **THEN** the blacklist entry SHALL be automatically removed
- **AND** subsequent requests with that token SHALL be checked only against remaining markers

### Requirement: Revoked token storage in database
The system SHALL persist revoked token markers in the `revoked_tokens` table for audit and recovery.

#### Scenario: Marker stored after revocation
- **WHEN** a token is revoked via logout
- **THEN** system SHALL insert a record into `revoked_tokens` with `tokenHash = "ALL_{userId}_{userType}_{deviceId}"`, type, user info, and timestamps

#### Scenario: Database cleanup
- **WHEN** a scheduled cleanup job runs daily
- **THEN** system SHALL delete `revoked_tokens` records where `expires_at` is older than 7 days

## REMOVED Requirements

### Requirement: Token blacklist uses SHA-256 hashing
**Reason**: Replaced by `ALL`-format markers for unified device-scoped and user-scoped revocation.
**Migration**: The `hashToken()` method and single-token `revoke()` method will be removed. All revocation uses `ALL_{userId}_{userType}[_{deviceId}]` format.
