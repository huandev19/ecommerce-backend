# v8n-ecommerce Backend

This is the backend service for the v8n-ecommerce platform, built using Java and Spring Boot. The architecture is modular, separating different domains of the e-commerce system into dedicated modules.

## Modules

The project is structured into several core modules to handle different business capabilities:

- **Identity**: Manages user authentication, authorization, roles, and permissions (Admin/Customer).
- **Inventory**: Handles product inventory, stock levels, and item reservations.
- **Order**: Manages order creation, processing, fulfillment, and status tracking.
- **Payment**: Integrates payment processing, payment sessions, collections, and refunds.
- **Promotion**: Manages discount rules, conditions, and promotional campaigns.
- **Notification**: Handles system notifications and alerts.

## Technology Stack

- **Framework**: Java & Spring Boot
- **Build Tool**: Gradle
- **Database**: PostgreSQL (Flyway for database migrations)
- **Architecture**: Modular Monolith

## Prerequisites

- Java 17+
- Gradle
- PostgreSQL Database

## Getting Started

1. **Clone the repository**:
   ```bash
   git clone https://github.com/huandev19/ecommerce-backend.git
   cd ecommerce-backend
   ```

2. **Configure Database**:
   Update the database connection settings in `src/main/resources/application.yml`.

3. **Build the project**:
   ```bash
   ./gradlew build
   ```

4. **Run the application**:
   You can run the application using the provided script or gradle:
   ```bash
   ./bootRun.sh
   # or
   ./gradlew bootRun
   ```

## API Documentation
*(To be updated with Swagger/OpenAPI details)*

## Deployment
For deployment instructions, please refer to the `AAPANEL_DEPLOY_GUIDE.md` included in the source code.
