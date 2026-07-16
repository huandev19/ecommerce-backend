# Project Rules

## SQL Writing Rules

1. **Always check the entity class before writing SQL** — Read the Java entity class (JPA `@Entity`) to verify actual table name (`@Table`), column names (`@Column`), and relationships before writing any SQL. Never invent or guess column names.

2. **Check `@Transient` fields** — Fields marked with `@Transient` are NOT stored in the database. Verify each field is not transient before including it in INSERT/UPDATE statements.

3. **Check `BaseEntity` / `@MappedSuperclass`** — If the entity extends a superclass, read it to find inherited columns like `id`, `created_at`, `updated_at`, `deleted_at`.

4. **Verify column types** — Check JPA annotations for column type hints:
   - `@JdbcTypeCode(SqlTypes.VARCHAR)` on `UUID` fields → stored as text
   - `@JdbcTypeCode(SqlTypes.JSON)` with `columnDefinition = "jsonb"` → JSON column
   - `@Enumerated(EnumType.STRING)` → stored as string

5. **Don't assume relationships exist as FK columns** — `@ManyToOne`/`@OneToMany` with `@JoinColumn` creates FK columns. But if the field is `@Transient`, there is no FK column even if the entity has a reference.

6. **Preview the SQL before suggesting** — Explain what you plan to insert/update before writing the actual SQL.
