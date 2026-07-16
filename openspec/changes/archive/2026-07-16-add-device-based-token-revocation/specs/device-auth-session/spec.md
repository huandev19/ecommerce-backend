# Device Auth Session

## Purpose

Provide device-aware authentication session management that enables per-device token lifecycle tracking, session reuse for un-identified clients, and seamless integration with the token revocation system.

## Requirements

### Requirement: JWT includes optional device_id claim
The system SHALL include an optional `device_id` claim in JWT tokens when the client provides an `X-Device-Id` header.

#### Scenario: Login with X-Device-Id header
- **WHEN** client sends login request with `X-Device-Id: iphone-123`
- **THEN** system SHALL generate JWT with claim `device_id: "iphone-123"`
- **AND** system SHALL generate a new access token (no session reuse)

#### Scenario: Login without X-Device-Id header
- **WHEN** client sends login request without `X-Device-Id` header
- **THEN** system SHALL generate JWT with claim `device_id: null`
- **AND** system SHALL check for an existing non-expired, non-revoked token with `deviceId=null` for this user
- **AND** if such token exists, system SHALL return the existing token (session reuse)
- **AND** if no such token exists, system SHALL generate a new token

#### Scenario: Login with same deviceId returns new token
- **WHEN** client sends login request with `X-Device-Id: iphone-123` and already has an active session
- **THEN** system SHALL still generate a new token (new login creates new session)
- **AND** the previous token for that device SHALL NOT be automatically revoked

### Requirement: Admin login also supports device identification
The system SHALL support `X-Device-Id` header for admin authentication endpoints as well.

#### Scenario: Admin login with deviceId
- **WHEN** admin sends `POST /api/v1/auth/admin/login` with `X-Device-Id: admin-laptop`
- **THEN** system SHALL generate admin JWT with claim `device_id: "admin-laptop"`
- **AND** system SHALL include claim `actor_type: "admin"`

#### Scenario: Admin login without deviceId
- **WHEN** admin sends admin login without `X-Device-Id` header
- **THEN** system SHALL generate admin JWT with claim `device_id: null`
- **AND** system SHALL reuse existing null-device session if available

### Requirement: JWT filter extracts deviceId for blacklist check
The system SHALL extract `device_id` from the JWT claims during authentication for use in `isRevoked()` checks.

#### Scenario: isRevoked uses deviceId from JWT
- **WHEN** `JwtAuthenticationFilter` processes a request
- **THEN** system SHALL parse `device_id` claim from the JWT
- **AND** system SHALL pass it as part of the `ALL`-format marker check: `ALL_{userId}_{userType}_{deviceId}`
- **AND** if `device_id` is null, the marker becomes `ALL_{userId}_{userType}_null`
