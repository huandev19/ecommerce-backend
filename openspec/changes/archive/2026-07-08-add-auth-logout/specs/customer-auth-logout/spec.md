## ADDED Requirements

### Requirement: Customer can logout
The system SHALL provide a `POST /api/v1/auth/logout` endpoint that allows authenticated customer users to revoke their current session tokens.

#### Scenario: Successful customer logout
- **WHEN** authenticated customer sends `POST /api/v1/auth/logout` with `Authorization: Bearer <access_token>` and body `{ "refreshToken": "<refresh_token>" }`
- **THEN** system SHALL revoke both access token and refresh token
- **AND** system SHALL return `200 OK` with success message

#### Scenario: Logout without refresh token
- **WHEN** authenticated customer sends `POST /api/v1/auth/logout` with only `Authorization: Bearer <access_token>`
- **THEN** system SHALL revoke the access token
- **AND** system SHALL return `200 OK` with success message

#### Scenario: Logout with already revoked token
- **WHEN** customer sends `POST /api/v1/auth/logout` with a token that was already revoked
- **THEN** system SHALL return `200 OK` (idempotent — token is already invalid)

#### Scenario: Logout without authentication
- **WHEN** unauthenticated request sends `POST /api/v1/auth/logout`
- **THEN** system SHALL return `401 Unauthorized`

### Requirement: Customer can revoke all sessions
The system SHALL provide a mechanism to revoke all active tokens for a customer user.

#### Scenario: Revoke all customer sessions
- **WHEN** authenticated customer sends `POST /api/v1/auth/logout/all`
- **THEN** system SHALL revoke all existing tokens for that customer user
- **AND** system SHALL return `200 OK` with success message
