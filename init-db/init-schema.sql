-- Create schemas for microservices isolation
CREATE SCHEMA IF NOT EXISTS auth_schema;
CREATE SCHEMA IF NOT EXISTS todo_schema;

-- Optional: Set search path or permissions if needed
-- GRANT ALL PRIVILEGES ON SCHEMA auth_schema TO ${POSTGRES_USERNAME};
-- GRANT ALL PRIVILEGES ON SCHEMA todo_schema TO ${POSTGRES_USERNAME};