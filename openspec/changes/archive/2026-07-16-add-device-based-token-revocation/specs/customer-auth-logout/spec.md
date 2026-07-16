# Customer Auth Logout

## Purpose

Provide customer users with the ability to securely log out by revoking their current device session using device-scoped `ALL`-format markers, and optionally revoke all active sessions across devices.

## ADDED Requirements

### Requirement: Logout uses device-scoped ALL marker
The system SHALL use the `LOGOUT_KEY_PREFIX` with `ALL_{userId}_{userType}_{deviceId}` format when a customer logs out.

#### Scenario: Customer logout with deviceId
- **WHEN** authenticated customer sends `POST /api/v1/auth/logout` with `Authorization: Bearer <access_token>` and `X-Device-Id: iphone-123`
- **THEN** system SHALL store marker `logout:ALL_{userId}_customer_iphone-123` in Redis
- **AND** system SHALL record the token in `revoked_tokens` with `tokenHash = "ALL_{userId}_customer_iphone-123"`
- **AND** system SHALL return `200 OK` with success message

#### Scenario: Customer logout without deviceId (null device)
- **WHEN** authenticated customer sends `POST /api/v1/auth/logout` with `Authorization: Bearer <access_token>` and NO `X-Device-Id` header
- **THEN** system SHALL store marker `logout:ALL_{userId}_customer_null`
- **AND** system SHALL revoke only the null-device session (other devices remain active)

### Requirement: Logout all sessions uses user-scoped ALL marker
The system SHALL use the `LOGOUT_KEY_PREFIX` with `ALL_{userId}_{userType}` format when a customer revokes all sessions.

#### Scenario: Revoke all customer sessions
- **WHEN** authenticated customer sends `POST /api/v1/auth/logout/all`
- **THEN** system SHALL store marker `logout:ALL_{userId}_customer` in Redis
- **AND** system SHALL revoke all existing tokens for that customer (all devices)
- **AND** system SHALL return `200 OK` with success message

## MODIFIED Requirements

### Requirement: Customer can logout
The system SHALL provide a `POST /api/v1/auth/logout` endpoint that allows authenticated customer users to revoke their current device session tokens.

#### Scenario: Successful customer logout
- **WHEN** authenticated customer sends `POST /api/v1/auth/logout` with `Authorization: Bearer <access_token>`
- **THEN** system SHALL parse `device_id` from the JWT claims
- **AND** system SHALL store marker `logout:ALL_{userId}_customer_{deviceId}` in Redis and `revoked_tokens`
- **AND** system SHALL return `200 OK` with success message

#### Scenario: Logout without authentication
- **WHEN** unauthenticated request sends `POST /api/v1/auth/logout`
- **THEN** system SHALL return `401 Unauthorized`

### Requirement: Customer can revoke all sessions
The system SHALL provide a mechanism to revoke all active tokens for a customer user.

#### Scenario: Revoke all customer sessions (no deviceId)
- **WHEN** authenticated customer sends `POST /api/v1/auth/logout/all`
- **THEN** system SHALL store marker `logout:ALL_{userId}_customer` in Redis
- **AND** system SHALL record the revoke-all event in `revoked_tokens`
- **AND** system SHALL return `200 OK` with success message

## REMOVED Requirements

### Requirement: Single-token SHA-256 revocation on logout
**Reason**: Logout now uses device-scoped `ALL`-format markers instead of individual SHA-256 token hashes.
**Migration**: The `revoke()` method with SHA-256 hashing is removed. Logout uses `revokeAllForUser()` with deviceId.

### Requirement: Refresh token revocation on logout
**Reason**: With device-scoped `ALL`-format markers, both access and refresh tokens for a device are revoked together by the device marker.
**Migration**: The logout endpoint no longer requires `refreshToken` in the request body. The device marker revokes all tokens for that device session.
