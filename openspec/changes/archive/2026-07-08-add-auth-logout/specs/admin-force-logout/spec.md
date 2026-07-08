## ADDED Requirements

### Requirement: Admin can list customer users
The system SHALL provide a `GET /api/v1/admin/customers` endpoint that allows admins to search and list customer users with pagination.

#### Scenario: List all customers
- **WHEN** admin sends `GET /api/v1/admin/customers?page=0&size=20`
- **THEN** system SHALL query the `users` table
- **AND** system SHALL return paginated list of customers with: id, email, firstName, lastName, status, createdAt

#### Scenario: Search customers by email or name
- **WHEN** admin sends `GET /api/v1/admin/customers?q=john&page=0&size=20`
- **THEN** system SHALL filter customers where email or name contains "john"
- **AND** system SHALL return matching paginated results

#### Scenario: Non-admin cannot list customers
- **WHEN** non-admin sends `GET /api/v1/admin/customers`
- **THEN** system SHALL return `403 Forbidden`

### Requirement: Admin can force-revoke admin user tokens
The system SHALL provide a `POST /api/v1/users/{id}/revoke-tokens` endpoint for admin to force-logout another admin user.

#### Scenario: Successful force-revoke admin user
- **WHEN** admin with `user:update` permission sends `POST /api/v1/users/{id}/revoke-tokens`
- **THEN** system SHALL revoke all tokens for the target admin user via `TokenBlacklistService.revokeAllForUser()`
- **AND** system SHALL record a `LOGOUT` entry in `login_history` with reason `FORCE_LOGOUT_BY_ADMIN`
- **AND** system SHALL return `200 OK`

### Requirement: Admin can force-revoke customer tokens
The system SHALL provide a `POST /api/v1/admin/customers/{id}/revoke-tokens` endpoint for admin to force-logout a customer user.

#### Scenario: Successful force-revoke customer
- **WHEN** admin sends `POST /api/v1/admin/customers/{id}/revoke-tokens`
- **THEN** system SHALL revoke all tokens for the target customer user via `TokenBlacklistService.revokeAllForUser()`
- **AND** system SHALL set `reason = "admin_force_logout"` in `revoked_tokens`
- **AND** system SHALL return `200 OK`
