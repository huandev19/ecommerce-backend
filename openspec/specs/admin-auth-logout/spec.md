# Admin Auth Logout

## Purpose

Provide admin users with the ability to securely log out by revoking their current device session using device-scoped `ALL`-format markers, and optionally revoke all active sessions. All logout events are recorded in the login history for audit purposes.

## Requirements

### Requirement: Admin can logout
The system SHALL provide a `POST /api/v1/auth/admin/logout` endpoint that allows authenticated admin users to revoke their current device session, and record the logout event in login history.

#### Scenario: Successful admin logout (with deviceId)
- **WHEN** authenticated admin sends `POST /api/v1/auth/admin/logout` with `Authorization: Bearer <access_token>` and `X-Device-Id: laptop-456`
- **THEN** system SHALL parse `device_id` from the JWT claims
- **AND** system SHALL store marker `logout:ALL_{userId}_admin_laptop-456` in Redis and `revoked_tokens`
- **AND** system SHALL record logout event in `login_history` table
- **AND** system SHALL return `200 OK` with success message

#### Scenario: Admin logout without deviceId
- **WHEN** authenticated admin sends `POST /api/v1/auth/admin/logout` without `X-Device-Id` header
- **THEN** system SHALL store marker `logout:ALL_{userId}_admin_null`
- **AND** system SHALL record logout event in `login_history`

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
