# Admin Auth Logout

## Purpose

Provide admin users with the ability to securely log out by revoking their current device session using device-scoped `ALL`-format markers, and optionally revoke all active sessions. All logout events are recorded in the login history for audit purposes.

## ADDED Requirements

### Requirement: Admin logout uses device-scoped ALL marker
The system SHALL use the `LOGOUT_KEY_PREFIX` with `ALL_{userId}_{userType}_{deviceId}` format when an admin logs out.

#### Scenario: Admin logout with deviceId
- **WHEN** authenticated admin sends `POST /api/v1/auth/admin/logout` with `Authorization: Bearer <access_token>` and `X-Device-Id: laptop-456`
- **THEN** system SHALL store marker `logout:ALL_{userId}_admin_laptop-456` in Redis
- **AND** system SHALL record the logout event in `login_history`
- **AND** system SHALL return `200 OK` with success message

## MODIFIED Requirements

### Requirement: Admin can logout
The system SHALL provide a `POST /api/v1/auth/admin/logout` endpoint that allows authenticated admin users to revoke their current device session tokens, and record the logout event in login history.

#### Scenario: Successful admin logout
- **WHEN** authenticated admin sends `POST /api/v1/auth/admin/logout` with `Authorization: Bearer <access_token>`
- **THEN** system SHALL parse `device_id` from the JWT claims
- **AND** system SHALL store marker `logout:ALL_{userId}_admin_{deviceId}` in Redis and `revoked_tokens`
- **AND** system SHALL record logout event in `login_history` table
- **AND** system SHALL return `200 OK` with success message

#### Scenario: Admin logout without authentication
- **WHEN** unauthenticated request sends `POST /api/v1/auth/admin/logout`
- **THEN** system SHALL return `401 Unauthorized`

### Requirement: Admin can revoke all sessions
The system SHALL provide a mechanism to revoke all active tokens for an admin user.

#### Scenario: Revoke all admin sessions
- **WHEN** authenticated admin sends `POST /api/v1/auth/admin/logout/all`
- **THEN** system SHALL store marker `logout:ALL_{userId}_admin` in Redis
- **AND** system SHALL record the revoke-all event in `login_history`
- **AND** system SHALL return `200 OK` with success message

## REMOVED Requirements

### Requirement: Single-token SHA-256 revocation on admin logout
**Reason**: Admin logout now uses device-scoped `ALL`-format markers.
**Migration**: The `revoke()` method with SHA-256 hashing is removed. Admin logout uses `revokeAllForUser()` via `LOGOUT_KEY_PREFIX`.
