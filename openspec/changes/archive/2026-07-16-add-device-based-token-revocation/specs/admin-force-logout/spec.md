# Admin Force Logout

## Purpose

Allow administrators to force-logout other users (both admin and customer users) by revoking all their active tokens using `BLACKLIST_KEY_PREFIX` with `ALL`-format markers. This is essential for security management such as terminating compromised sessions or enforcing access policies.

## ADDED Requirements

### Requirement: Admin force-revoke uses BLACKLIST_KEY_PREFIX
The system SHALL use `BLACKLIST_KEY_PREFIX` with `ALL_{userId}_{userType}` format when an admin force-revokes all tokens for a target user.

#### Scenario: Force-revoke stores blacklist:ALL marker
- **WHEN** admin with `user:update` permission sends `POST /api/v1/users/{id}/revoke-tokens`
- **THEN** system SHALL store marker `blacklist:ALL_{targetUserId}_admin` in Redis
- **AND** system SHALL record a `LOGOUT` entry in `login_history` with reason `FORCE_LOGOUT_BY_ADMIN`
- **AND** system SHALL return `200 OK`
- **AND** subsequent requests from that user's ANY device SHALL be rejected (all devices revoked)

## MODIFIED Requirements

### Requirement: Admin can force-revoke admin user tokens
The system SHALL provide a `POST /api/v1/users/{id}/revoke-tokens` endpoint for admin to force-logout another admin user.

#### Scenario: Successful force-revoke admin user
- **WHEN** admin with `user:update` permission sends `POST /api/v1/users/{id}/revoke-tokens`
- **THEN** system SHALL call `TokenBlacklistService.revokeAllForUser(targetUserId, "admin", "ADMIN_FORCE_LOGOUT")`
- **AND** system SHALL store marker `blacklist:ALL_{targetUserId}_admin` in Redis (TTL: 30 days)
- **AND** system SHALL record a `LOGOUT` entry in `login_history` with reason `FORCE_LOGOUT_BY_ADMIN`
- **AND** system SHALL return `200 OK`

### Requirement: Admin can force-revoke customer tokens
The system SHALL provide a `POST /api/v1/admin/customers/{id}/revoke-tokens` endpoint for admin to force-logout a customer user.

#### Scenario: Successful force-revoke customer
- **WHEN** admin sends `POST /api/v1/admin/customers/{id}/revoke-tokens`
- **THEN** system SHALL call `TokenBlacklistService.revokeAllForUser(targetUserId, "customer", "ADMIN_FORCE_LOGOUT")`
- **AND** system SHALL store marker `blacklist:ALL_{targetUserId}_customer` in Redis (TTL: 30 days)
- **AND** system SHALL set `reason = "admin_force_logout"` in `revoked_tokens`
- **AND** system SHALL return `200 OK`
