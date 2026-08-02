#!/bin/bash
# ============================================================
# PostgreSQL Connection Reference for v8n-ecommerce
# Generated from: src/main/resources/application.yml
#
# Usage:
#   source scripts/db-connect.sh   # Load all functions into current shell
#   db-help                         # Show this cheatsheet
#   db-connect                      # Open interactive psql session
#   db-tables                       # List all tables in public schema
#   db-describe "user_admins"       # Describe table structure (columns, indexes, FKs)
#   db-query "SELECT * FROM role;"  # Run any SQL query (non-interactive)
#   db-count "user_admins"          # Count rows in a table
#   db-schema                       # Export full DDL schema
#   db-seed-info                    # Display important seed data (roles, permissions, admin)
# ============================================================

# ---- Connection Parameters (from application.yml) ----
export PGHOST=localhost
export PGPORT=5432
export PGDATABASE=v8n_db
export PGUSER=v8n_ecom
export PGPASSWORD='#123456@'

# ---- Color helpers ----
BOLD="\033[1m"
GREEN="\033[0;32m"
YELLOW="\033[0;33m"
CYAN="\033[0;36m"
RESET="\033[0m"

# ============================================================
# Helper Functions
# ============================================================

# db-help — Print this cheatsheet
db-help() {
    echo -e "${BOLD}v8n-ecommerce PostgreSQL Cheatsheet${RESET}"
    echo ""
    echo -e "  ${GREEN}db-connect${RESET}               Open interactive psql session"
    echo -e "  ${GREEN}db-tables${RESET}                List all tables in public schema"
    echo -e "  ${GREEN}db-describe${RESET} <table>      Describe table (\\d)"
    echo -e "  ${GREEN}db-query${RESET} \"<sql>\"        Run SQL query"
    echo -e "  ${GREEN}db-count${RESET} <table>         Count rows in a table"
    echo -e "  ${GREEN}db-schema${RESET}                Export full DDL (schema only, no data)"
    echo -e "  ${GREEN}db-seed-info${RESET}             Show seed data (roles, permissions, admin)"
    echo ""
    echo -e "  ${YELLOW}Connection:${RESET} postgresql://v8n_ecom@localhost:5432/v8n_db"
    echo ""
    echo -e "  ${YELLOW}psql shortcuts (inside db-connect):${RESET}"
    echo "    \\dt           — list tables"
    echo "    \\d  table     — describe table"
    echo "    \\di           — list indexes"
    echo "    \\du           — list users/roles"
    echo "    \\l            — list databases"
    echo "    \\x            — toggle expanded display"
    echo "    \\q            — quit"
}

# db-connect — Open interactive psql session
db-connect() {
    echo -e "${CYAN}Connecting to ${PGDATABASE} as ${PGUSER}@${PGHOST}:${PGPORT}...${RESET}"
    psql
}

# db-tables — List all tables in public schema
db-tables() {
    psql -c "
        SELECT
            schemaname AS schema,
            tablename AS table,
            pg_size_pretty(pg_total_relation_size(schemaname||'.'||tablename)) AS size
        FROM pg_tables
        WHERE schemaname = 'public'
        ORDER BY tablename;
    "
}

# db-describe <table> — Show table structure
db-describe() {
    if [ -z "$1" ]; then
        echo "Usage: db-describe <table_name>"
        echo "Example: db-describe \"user_admins\""
        return 1
    fi
    psql -c "\d+ $1"
}

# db-query "<sql>" — Run arbitrary SQL
db-query() {
    if [ -z "$1" ]; then
        echo "Usage: db-query \"<SQL statement>\""
        echo "Example: db-query \"SELECT id, email FROM user_admins LIMIT 5;\""
        return 1
    fi
    psql -c "$1"
}

# db-count <table> — Count rows in a table
db-count() {
    if [ -z "$1" ]; then
        echo "Usage: db-count <table_name>"
        echo "Example: db-count \"user_admins\""
        return 1
    fi
    psql -c "SELECT '$1' AS table_name, COUNT(*) AS row_count FROM $1;"
}

# db-schema — Export full DDL (schema only)
db-schema() {
    echo -e "${CYAN}Exporting schema for ${PGDATABASE}...${RESET}"
    pg_dump --schema-only --no-owner --no-privileges
}

# db-seed-info — Display important seed data
db-seed-info() {
    echo -e "${BOLD}=== v8n-ecommerce Seed Data ===${RESET}"
    echo ""

    echo -e "${YELLOW}▶ Super Admin Account:${RESET}"
    psql -c "
        SELECT id, email, first_name, last_name, is_active, created_at
        FROM user_admins
        WHERE id = '00000000-0000-0000-0000-000000000001';
    "

    echo ""
    echo -e "${YELLOW}▶ Roles (6 system roles):${RESET}"
    psql -c "
        SELECT id, name, description, is_system
        FROM role
        ORDER BY name;
    "

    echo ""
    echo -e "${YELLOW}▶ Permissions (25 total):${RESET}"
    psql -c "
        SELECT resource, string_agg(operation, ', ' ORDER BY operation) AS operations
        FROM permission
        GROUP BY resource
        ORDER BY resource;
    "

    echo ""
    echo -e "${YELLOW}▶ Role ↔ Permission Summary:${RESET}"
    psql -c "
        SELECT r.name AS role, COUNT(rp.permission_id) AS permission_count
        FROM role r
        LEFT JOIN role_permission rp ON r.id = rp.role_id
        GROUP BY r.name
        ORDER BY permission_count DESC;
    "

    echo ""
    echo -e "${YELLOW}▶ User ↔ Role Assignments:${RESET}"
    psql -c "
        SELECT ua.email, r.name AS role, uar.assigned_at
        FROM user_admin_roles uar
        JOIN user_admins ua ON uar.user_admin_id = ua.id
        JOIN role r ON uar.role_id = r.id
        ORDER BY ua.email, r.name;
    "
}

# ============================================================
# Print quick reference on source
# ============================================================
echo -e "${GREEN}[v8n-db]${RESET} PostgreSQL helpers loaded. Type ${BOLD}db-help${RESET} for cheatsheet."
echo -e "  Connection: ${CYAN}postgresql://${PGUSER}@${PGHOST}:${PGPORT}/${PGDATABASE}${RESET}"
