# Customer Auth Logout

## Purpose

Provide customer users with the ability to securely log out by revoking their current device session using device-scoped `ALL`-format markers, and optionally revoke all active sessions across devices.

## Requirements

### Requirement: Customer can logout
The system SHALL provide a `POST /api/v1/auth/logout` endpoint that allows authenticated customer users to revoke their current device session using a device-scoped `ALL`-format marker.

#### Scenario: Successful customer logout (with deviceId)
- **WHEN** authenticated customer sends `POST /api/v1/auth/logout` with `Authorization: Bearer <access_token>` and `X-Device-Id: iphone-123`
- **THEN** system SHALL parse `device_id` from the JWT claims
- **AND** system SHALL store marker `logout:ALL_{userId}_customer_iphone-123` in Redis and `revoked_tokens`
- **AND** system SHALL return `200 OK` with success message

#### Scenario: Customer logout without deviceId (null device)
- **WHEN** authenticated customer sends `POST /api/v1/auth/logout` with `Authorization: Bearer <access_token>` and NO `X-Device-Id` header
- **THEN** system SHALL store marker `logout:ALL_{userId}_customer_null`
- **AND** system SHALL revoke only the null-device session (other devices remain active)

#### Scenario: Logout with already revoked token
- **WHEN** customer sends `POST /api/v1/auth/logout` with a token that was already revoked
- **THEN** system SHALL return `200 OK` (idempotent — token is already invalid)

#### Scenario: Logout without authentication
- **WHEN** unauthenticated request sends `POST /api/v1/auth/logout`
- **THEN** system SHALL return `401 Unauthorized`

### Requirement: Customer can revoke all sessions
The system SHALL provide a mechanism to revoke all active tokens for a customer user across all devices.

#### Scenario: Revoke all customer sessions
- **WHEN** authenticated customer sends `POST /api/v1/auth/logout/all`
- **THEN** system SHALL store marker `logout:ALL_{userId}_customer` in Redis
- **AND** system SHALL revoke all existing tokens for that customer (all devices)
- **AND** system SHALL return `200 OK` with success message
