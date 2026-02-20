-- Ensure UUID generation support
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- 1. Create roles table (assuming Role entity uses UUID as PK)
CREATE TABLE IF NOT EXISTS roles (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

-- 2. Create users table
CREATE TABLE IF NOT EXISTS users (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

-- 3. Create user_roles join table
CREATE TABLE IF NOT EXISTS user_roles (
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(uuid) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(uuid) ON DELETE CASCADE
);

-- Optional indexes for performance
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_user_roles_user_id ON user_roles(user_id);
CREATE INDEX IF NOT EXISTS idx_user_roles_role_id ON user_roles(role_id);

-- Optional: Insert default roles (e.g., ROLE_USER, ROLE_ADMIN)
-- These will only be inserted if the roles table is empty (to avoid duplicates)
INSERT INTO roles (uuid, name, description, created_at, updated_at)
SELECT gen_random_uuid(), 'ROLE_USER', 'Default user role', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_USER');

INSERT INTO roles (uuid, name, description, created_at, updated_at)
SELECT gen_random_uuid(), 'ROLE_ADMIN', 'Administrator role', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_ADMIN');