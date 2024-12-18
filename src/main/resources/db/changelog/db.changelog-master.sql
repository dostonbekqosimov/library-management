-- liquibase formatted sql

-- changeset doston:1
-- Create the profile table if it does not exist
CREATE TABLE IF NOT EXISTS profile (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       username VARCHAR(255) NOT NULL,
                                       password VARCHAR(255) NOT NULL,
                                       role VARCHAR(50),
                                       created_date DATETIME,
                                       visible BOOLEAN
);

-- changeset your_name:2
-- Insert default admin profile if not already exists
INSERT INTO profile (
    username,
    password,
    role,
    created_date,
    visible
)
SELECT
    'admin',
    '$2a$10$hp07X7FdJvaHCKR3fCunZOct14PAG0rl03KXvQkiAHtRJuz6ZFaV.',
    'ROLE_ADMIN',
    NOW(),
    TRUE
WHERE NOT EXISTS (
    SELECT 1
    FROM profile
    WHERE username = 'admin'
);