# Admin Auth Logout

## Purpose

Provide admin users with the ability to securely log out by revoking their current session tokens, and optionally revoke all active sessions. All logout events are recorded in the login history for audit purposes.

## Requirements

### Requirement: Admin can logout
The system SHALL provide a `POST /api/v1/auth/admin/logout` endpoint that allows authenticated admin users to revoke their current session tokens, and record the logout event in login history.

#### Scenario: Successful admin logout
- **WHEN** authenticated admin sends `POST /api/v1/auth/admin/logout` with `Authorization: Bearer <access_token>` and body `{ "refreshToken": "<refresh_token>" }`
- **THEN** system SHALL revoke both access token and refresh token
- **AND** system SHALL record logout event in `login_history` table
- **AND** system SHALL return `200 OK` with success message

#### Scenario: Admin logout without refresh token
- **WHEN** authenticated admin sends `POST /api/v1/auth/admin/logout` with only `Authorization: Bearer <access_token>`
- **THEN** system SHALL revoke the access token
- **AND** system SHALL return `200 OK` with success message

#### Scenario: Admin logout with already revoked token
- **WHEN** admin sends `POST /api/v1/auth/admin/logout` with a token that was already revoked
- **THEN** system SHALL return `200 OK` (idempotent)

#### Scenario: Admin logout without authentication
- **WHEN** unauthenticated request sends `POST /api/v1/auth/admin/logout`
- **THEN** system SHALL return `401 Unauthorized`

### Requirement: Admin can revoke all sessions
The system SHALL provide a mechanism to revoke all active tokens for an admin user.

#### Scenario: Revoke all admin sessions
- **WHEN** authenticated admin sends `POST /api/v1/auth/admin/logout/all`
- **THEN** system SHALL revoke all existing tokens for that admin user
- **AND** system SHALL record the revoke-all event in `login_history`
- **AND** system SHALL return `200 OK` with success message
