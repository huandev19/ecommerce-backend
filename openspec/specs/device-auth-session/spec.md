# Device Auth Session

## Purpose

Provide device-aware authentication session management that enables per-device token lifecycle tracking and seamless integration with the token revocation system. Clients may optionally provide an `X-Device-Id` header for dedicated device identification.

## Requirements

### Requirement: JWT includes optional device_id claim
The system SHALL include an optional `device_id` claim in JWT tokens when the client provides an `X-Device-Id` header.

#### Scenario: Login with X-Device-Id header
- **WHEN** client sends login request with `X-Device-Id: iphone-123`
- **THEN** system SHALL generate JWT with claim `device_id: "iphone-123"`
- **AND** system SHALL generate a new access token (each login creates a new session)

#### Scenario: Login without X-Device-Id header
- **WHEN** client sends login request without `X-Device-Id` header
- **THEN** system SHALL generate JWT with claim `device_id: null`
- **AND** subsequent logout from this session SHALL use marker `logout:ALL_{userId}_{userType}_null`

### Requirement: isRevoked extracts deviceId from JWT
The system SHALL extract `device_id` from the JWT claims during authentication for use in `isRevoked()` checks.

#### Scenario: isRevoked constructs device-scoped marker
- **WHEN** `TokenBlacklistService.isRevoked()` processes a token with `device_id: "iphone-123"`
- **THEN** system SHALL check marker `logout:ALL_{userId}_{userType}_iphone-123` in Redis then DB
- **AND** system SHALL then check marker `blacklist:ALL_{userId}_{userType}` in Redis then DB
- **AND** system SHALL return `true` if EITHER marker exists

#### Scenario: isRevoked handles null deviceId
- **WHEN** `TokenBlacklistService.isRevoked()` processes a token with `device_id: null`
- **THEN** system SHALL check marker `logout:ALL_{userId}_{userType}_null`
