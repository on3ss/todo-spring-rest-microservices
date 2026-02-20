-- Ensure the UUID extension is available (usually installed by default in newer Postgres)
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Create the todos table
CREATE TABLE IF NOT EXISTS todos (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    user_uuid UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

-- Optional: Create an index on user_uuid for faster queries
CREATE INDEX IF NOT EXISTS idx_todos_user_uuid ON todos(user_uuid);