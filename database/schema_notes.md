# Database Architecture & Conventions

## Supabase PostgreSQL Guidelines
1. **auth.users**: Managed by Supabase Auth directly. Never edit this table directly through application code.
2. **public.profiles**: The primary profile table. The primary key `id` is a `UUID` referencing `auth.users(id)` with `ON DELETE CASCADE`.
3. **Primary Keys**: UUIDs are recommended for all public tables (`gen_random_uuid()`).
4. **Timestamps**: All tables must include `created_at` and `updated_at` with default `CURRENT_TIMESTAMP`.
5. **Row Level Security (RLS)**: Configure RLS policies for client-side queries, while the Spring Boot backend securely interacts via authenticated database connection.
6. **Flyway Migrations**:
   - `V1__initial_schema.sql`: Base DDL for all application tables and indexes.
   - `V2__seed_data.sql`: Seed data for categories and initial reference rows.

