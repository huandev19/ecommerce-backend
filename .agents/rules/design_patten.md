# DESIGN PATTERNS & PROJECT ARCHITECTURE

The project uses Hexagonal Architecture (Ports and Adapters) combined with Domain-Driven Design (DDD). Each module is organized according to a standard directory structure, clearly separating layers: Interface, Application, Domain, Infrastructure.

## 1. Overall Directory Structure

```
v8n-ecommerce/
├── modules/                     # Main system modules
│   ├── cart/                    # Cart management module
│   ├── catalog/                 # Product & category management module
│   ├── core/                    # Shared components module
│   ├── fulfillment/             # Order fulfillment & shipping module
│   ├── identity/               # User, authentication & authorization module
│   ├── inventory/               # Inventory management module
│   ├── notification/            # Notification management module
│   ├── order/                   # Order management module
│   ├── payment/                 # Payment management module
│   └── promotion/               # Promotion & discount management module
├── src/                         # Main source code
│   └── main/
│       ├── java/com/v8n/        # Java source code
│       │   └── V8nEcommerceApplication.java  # Application entry point
│       └── resources/           # Configuration resources
│           ├── application.yml          # Application configuration
│           ├── application-prod.yml     # Production configuration
│           └── db/migration/            # Database migration scripts
├── build.gradle                 # Gradle build configuration
├── settings.gradle              # Gradle module settings
├── .gitignore                   # Git ignore file
├── repomix-output.md            # Packed file of entire codebase
└── .agents/                     # Directory containing rules for AI Agent
    ├── AGENTS.md                # Mandatory guide file for AI Agent
    └── rules/                   # Detailed rule files
        ├── design_patten.md     # Design patterns & architecture description
        ├── implementation-workflow.md  # Mandatory implementation workflow
        └── repomix-index-guide.md      # Quick repomix lookup guide
```

## 2. Overall Architecture

The project uses Hexagonal Architecture (Ports and Adapters) combined with Domain-Driven Design (DDD). Each module is organized according to a standard directory structure, clearly separating layers: Interface, Application, Domain, Infrastructure.

### 2.1. Directory Structure in Each Module

Each business module (except the `core` module) follows this structure:

```
modules/[module-name]/src/main/java/com/v8n/modules/[module-name]/
├── application/                        # Application Layer
│   ├── dto/                            # Data Transfer Objects
│   │   ├── [Entity]Request.java        # DTO for incoming requests
│   │   └── [Entity]Response.java       # DTO for outgoing responses
│   └── service/                        # Application service classes
│       └── [Module]Service.java        # Service handling application logic
├── domain/                             # Domain Layer
│   ├── entity/                         # Domain Entities
│   │   └── [Entity].java              # Entity representing business objects
│   ├── event/                          # Domain Events
│   │   └── [Event].java               # Domain event
│   └── repository/                     # Repository Interfaces
│       └── [Entity]Repository.java     # Repository interface
└── interfaces/                         # Interface Layer
    └── rest/                           # REST API Controllers
        └── [Module]Controller.java     # Controller handling HTTP requests
```

### 2.2. Module `core` - Shared Components

The `core` module contains shared components used across the entire project, independent of any specific module.

```
modules/core/src/main/java/com/v8n/modules/core/
├── application/                        # Application Layer
│   ├── dto/                            # Shared DTOs
│   │   ├── ApiResponse.java            # Standard API response
│   │   └── PageResponse.java           # Response for paginated data
│   ├── exception/                      # Shared exceptions
│   │   ├── BusinessException.java      # Business exception
│   │   └── ErrorCode.java              # Error codes
│   └── service/                        # Shared services
│       └── BaseService.java            # Base service
├── domain/                             # Domain Layer
│   ├── entity/                         # Base entities
│   │   ├── BaseEntity.java             # Base entity with common fields (id, createdAt, updatedAt)
│   │   └── BaseEnum.java               # Base enum
│   ├── event/                          # Base events
│   │   └── DomainEvent.java            # Base domain event
│   └── repository/                     # Base repositories
│       └── BaseRepository.java         # Base repository with common CRUD methods
└── infrastructure/                     # Infrastructure Layer
    ├── config/                         # Configurations
    │   ├── BusinessValidationException.java  # Business validation exception
    │   ├── GlobalExceptionHandler.java       # Global exception handler
    │   ├── JpaAuditingConfig.java            # JPA Auditing configuration
    │   └── ResourceNotFoundException.java    # Resource not found exception
    └── security/                       # Security
        └── annotation/
            └── PublicEndpoint.java     # Annotation to mark public endpoints
```

## 3. Main Design Patterns

### 3.1. BaseEntity - Base Entity

All entities in the project inherit from `BaseEntity`. This entity provides common fields:

| Field | Type | Description |
|-------|------|-------------|
| `id` | `String` | Auto-generated UUID |
| `createdAt` | `Instant` | Creation time, auto-set on persist |
| `updatedAt` | `Instant` | Update time, auto-set on update |

**Usage:**
```java
@Entity
@Table(name = "payment_sessions")
public class PaymentSession extends BaseEntity {
    // Entity-specific fields
    private BigDecimal amount;
    // ...
}
```

### 3.2. BaseRepository - Base Repository

All repository interfaces inherit from `BaseRepository<Entity, String>`. This repository provides basic CRUD methods.

**Main methods:**
| Method | Description |
|--------|-------------|
| `findByIdAndDeletedFalse(String id)` | Find entity by ID, excluding soft-deleted entities |
| `findAllByDeletedFalse(Pageable pageable)` | Get all non-soft-deleted entities, paginated |

> **Note:** The project uses **soft delete** — entities are not removed from the database but are marked as `deleted = true`.

**Usage:**
```java
@Repository
public interface PaymentSessionRepository extends BaseRepository<PaymentSession, String> {
    // Custom query methods
    Optional<PaymentSession> findByOrderIdAndDeletedFalse(String orderId);
}
```

### 3.3. ApiResponse - Standard Response

All API endpoints in the project return a response following the standard `ApiResponse<T>` structure.

**Structure:**
| Field | Type | Description |
|-------|------|-------------|
| `success` | `boolean` | Success/failure status of the request |
| `data` | `T` | Returned data (generic type) |
| `message` | `String` | Message (usually for errors) |
| `errors` | `List<String>` | List of detailed errors |

**Main factory methods:**
| Method | Description |
|--------|-------------|
| `success(T data)` | Create success response with data |
| `fail(String message)` | Create failure response with message |
| `fail(String message, List<String> errors)` | Create failure response with message and error list |
| `validationFail(List<String> errors)` | Create validation error response |

**Usage:**
```java
// In Controller
@PostMapping
public ResponseEntity<ApiResponse<PaymentSessionResponse>> createSession(
    @Valid @RequestBody PaymentSessionRequest request
) {
    PaymentSessionResponse response = paymentService.createSession(request);
    return ResponseEntity.ok(ApiResponse.success(response));
}
```

### 3.4. PageResponse - Paginated Response

For endpoints requiring pagination, use `PageResponse<T>`.

**Structure:**
| Field | Type | Description |
|-------|------|-------------|
| `content` | `List<T>` | List of data |
| `page` | `int` | Current page number (0-based) |
| `size` | `int` | Page size |
| `totalElements` | `long` | Total number of elements |
| `totalPages` | `int` | Total number of pages |
| `last` | `boolean` | Is this the last page |

**Usage:**
```java
// In Service
Page<PaymentSession> page = paymentSessionRepository.findAllByDeletedFalse(pageable);
return PageResponse.from(page.map(paymentSessionMapper::toResponse));
```

### 3.5. Soft Delete

The project applies soft delete to most entities. Entities are not removed from the database but are marked as `deleted = true`.

**Key Principles:**
- Entities requiring soft delete need a `deleted` field (boolean)
- When deleting, set `deleted = true` instead of removing the record
- All default queries filter `deleted = false`
- Use `@SQLDelete(sql = "UPDATE table SET deleted = true WHERE id = ?")` for automatic soft delete when calling `delete()`

### 3.6. GlobalExceptionHandler - Global Exception Handling

All exceptions in the project are handled centrally through `GlobalExceptionHandler` in the `core` module. This handler catches exceptions and returns responses following the `ApiResponse` standard.

**Exceptions handled:**
| Exception | HTTP Status | Description |
|-----------|-------------|-------------|
| `MethodArgumentNotValidException` | 400 BAD_REQUEST | Validation errors from `@Valid` |
| `ResourceNotFoundException` | 404 NOT_FOUND | Resource not found |
| `BusinessValidationException` | 400 BAD_REQUEST | Business validation error |
| `Exception` (fallback) | 500 INTERNAL_SERVER_ERROR | Unknown error |

### 3.7. JPA Auditing - Automatic Timestamp Population

The project uses JPA Auditing to automatically populate `createdAt` and `updatedAt`.

**Configuration:**
- `JpaAuditingConfig` enables JPA Auditing
- `BaseEntity` uses `@CreatedDate` and `@LastModifiedDate`

### 3.8. DTO Pattern - Data Transfer

Each module has DTO pairs for each main entity: `[Entity]Request.java` and `[Entity]Response.java`.

**Naming Convention:**
| Type | Pattern | Description |
|------|---------|-------------|
| Request DTO | `[Entity]Request.java` | DTO for incoming requests |
| Response DTO | `[Entity]Response.java` | DTO for outgoing responses |

### 3.9. PublicEndpoint Annotation - Marking Public Endpoints

The project uses the `@PublicEndpoint` annotation to mark endpoints that don't require authentication.

**Usage:**
```java
@RestController
public class AuthController {
    @PublicEndpoint
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(...) {
        // This endpoint doesn't require JWT token
    }
}
```

## 4. Code Conventions

### 4.1. Naming

| Element | Convention | Example |
|---------|-----------|---------|
| Package | Lowercase, plural | `com.v8n.modules.identity.application.service` |
| Class/Interface | PascalCase | `PaymentSessionRequest`, `PaymentService` |
| Method | camelCase | `createSession()`, `findByOrderId()` |
| Variable | camelCase | `paymentSession`, `orderId` |
| Constant | UPPER_SNAKE_CASE | `MAX_RETRY_ATTEMPTS` |
| Database table | Lowercase, plural, snake_case | `payment_sessions`, `order_items` |
| Database column | snake_case | `created_at`, `order_id` |
| REST API endpoint | Lowercase, plural, kebab-case | `/api/payment-sessions` |
| SQL migration file | `V[sequence]__[description].sql` | `V1__baseline.sql` |

### 4.2. REST Endpoint Structure

#### Admin Endpoints:
| HTTP Method | Endpoint Pattern | Description |
|-------------|-----------------|-------------|
| `POST` | `/api/admin/[module]/[entity]s` | Create new |
| `PUT` | `/api/admin/[module]/[entity]s/{id}` | Update |
| `DELETE` | `/api/admin/[module]/[entity]s/{id}` | Delete |
| `GET` | `/api/admin/[module]/[entity]s` | Get paginated list |
| `GET` | `/api/admin/[module]/[entity]s/{id}` | Get detail by ID |

#### Store Endpoints:
| HTTP Method | Endpoint Pattern | Description |
|-------------|-----------------|-------------|
| `GET` | `/api/store/[module]/[entity]s` | APIs for end users |

### 4.3. Validation Rules

- Use Jakarta Bean Validation annotations (`@NotNull`, `@NotBlank`, `@Positive`, etc.) in Request DTOs.
- Use `@Valid` in controller methods to enable validation.

## 5. Rules for Creating or Modifying Code

### 5.1. Before Coding

1. Read `repomix-output.md` to understand the CURRENT codebase structure (use grep/search, do NOT read the entire 9465-line file — see `repomix-index-guide.md`).
2. Identify which module contains the code to modify.
3. Follow **implementation-workflow.md**: Create plan → Present to user → WAIT FOR USER APPROVAL → Only then implement.
4. Check if the entity inherits from `BaseEntity`.
5. Check if the repository inherits from `BaseRepository<Entity, String>`.

### 5.2. While Coding

1. New entities MUST inherit `BaseEntity` (if it's a main entity requiring CRUD).
2. New repositories MUST inherit `BaseRepository<Entity, String>`.
3. Request/response DTOs must be placed in the correct `application/dto/` package.
4. Controllers must return `ResponseEntity<ApiResponse<T>>`.
5. Paginated endpoints must use `PageResponse<T>`.
6. Use `@Valid` for request bodies requiring validation.
7. Service methods should throw `ResourceNotFoundException` when an entity is not found (no need for try-catch in controller).
8. Use `@PublicEndpoint` if the endpoint doesn't require authentication.
9. For soft delete, add the `deleted` field and use `@SQLDelete`.
10. Follow naming conventions: Java class (PascalCase), method/variable (camelCase), DB table/column (snake_case).

### 5.3. After Coding

1. No unused imports, no wildcard imports.
2. Response DTO should only contain necessary fields.
3. Complete validation annotations on Request DTO.
4. SQL migration (if schema changes) placed in `resources/db/migration/`.

## 6. Illustrative Examples

### 6.1. Example of Entity Inheriting BaseEntity

```java
@Entity
@Table(name = "user_admins")
@SQLDelete(sql = "UPDATE user_admins SET deleted = true WHERE id = ?")
public class UserAdmin extends BaseEntity {
    private String username;
    private String password;
    private String email;
    private String avatarUrl;
    private boolean deleted = false;
    // Getters, Setters, Constructors
}
```

### 6.2. Example of Repository Inheriting BaseRepository

```java
@Repository
public interface UserAdminRepository extends BaseRepository<UserAdmin, String> {
    Optional<UserAdmin> findByUsernameAndDeletedFalse(String username);
    Optional<UserAdmin> findByEmailAndDeletedFalse(String email);
    boolean existsByUsernameAndDeletedFalse(String username);
}
```

### 6.3. Example of Controller Endpoint

```java
@RestController
@RequestMapping("/api/admin/identity/user-admins")
public class UserAdminController {
    private final UserAdminService userAdminService;

    @PostMapping
    public ResponseEntity<ApiResponse<AdminUserResponse>> createUserAdmin(
        @Valid @RequestBody CreateUserAdminRequest request
    ) {
        AdminUserResponse response = userAdminService.createUserAdmin(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<AdminUserResponse>>> getUserAdmins(
        @PageableDefault(size = 20) Pageable pageable
    ) {
        PageResponse<AdminUserResponse> response = userAdminService.getUserAdmins(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
```

### 6.4. Example of Service Using ResourceNotFoundException

```java
@Service
public class UserAdminService {
    public AdminUserResponse getUserAdminById(String id) {
        UserAdmin userAdmin = userAdminRepository.findByIdAndDeletedFalse(id)
            .orElseThrow(() -> new ResourceNotFoundException("UserAdmin not found: " + id));
        return userAdminMapper.toResponse(userAdmin);
    }
}
```

## 7. Safety Principles

1. **ABSOLUTELY DO NOT** change core structures (BaseEntity, BaseRepository, ApiResponse, GlobalExceptionHandler) unless explicitly requested.
2. When unsure about implementation, ask the user instead of making assumptions.
3. Implement only ONE task at a time — do not arbitrarily expand the scope of changes.
4. Do not use wildcard imports (e.g., `import java.util.*`).

## 8. Main Modules and Responsibilities

| Module | Responsibility | Package |
|--------|---------------|---------|
| **core** | Base entities, repositories, ApiResponse, PageResponse, GlobalExceptionHandler, Security annotations | `com.v8n.modules.core` |
| **identity** | User management (customer & admin), roles, permissions, authentication, JWT, address, login history | `com.v8n.modules.identity` |
| **catalog** | Products, categories, variants, collections, stores | `com.v8n.modules.catalog` |
| **cart** | Cart and line items | `com.v8n.modules.cart` |
| **order** | Orders, order items, order status history | `com.v8n.modules.order` |
| **payment** | Payments, payment sessions, payment collections, refunds | `com.v8n.modules.payment` |
| **inventory** | Inventory items, inventory levels, reservations | `com.v8n.modules.inventory` |
| **fulfillment** | Order fulfillment and fulfillment items | `com.v8n.modules.fulfillment` |
| **promotion** | Promotions, discounts, discount conditions, discount rules | `com.v8n.modules.promotion` |
| **notification** | Push notifications | `com.v8n.modules.notification` |

## 9. Module Dependency Diagram

```
                    ┌─────────────┐
                    │    core     │◄──────────── ALL MODULES DEPEND ON CORE
                    └─────────────┘
                           ▲
          ┌────────────────┼────────────────┐
          │                │                │
   ┌──────┴──────┐  ┌──────┴──────┐  ┌──────┴──────┐
   │   identity  │  │   catalog   │  │    cart     │
   └─────────────┘  └─────────────┘  └──────┬──────┘
          ▲                                  │
          │                           ┌──────┴──────┐
          │                           │    order    │
          │                           └──────┬──────┘
          │                                  │
          │                    ┌─────────────┼─────────────┐
          │                    │             │             │
          │             ┌──────┴──────┐ ┌────┴─────┐ ┌─────┴──────┐
          │             │   payment   │ │inventory │ │fulfillment │
          │             └─────────────┘ └──────────┘ └────────────┘
          │
   ┌──────┴──────┐  ┌──────────────┐
   │ notification│  │  promotion   │
   └─────────────┘  └──────────────┘
```

- All modules depend on `core`
- Other modules may have cross-dependencies (e.g., `order` depends on `cart` to create orders from cart)

---

*Last updated: 2026-07-02*
*Version: 2.0.0*
*This file describes all design patterns and architecture of the project, mandatory for all AI Agents to follow when implementing any changes.*