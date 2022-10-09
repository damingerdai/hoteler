CREATE TYPE PASSWORDTYPE AS ENUM ('noop', 'base64', 'des', 'hmac', 'md5', 'rsa', 'sha');

ALTER TABLE users ADD COLUMN IF NOT EXISTS password_type PASSWORDTYPE NOT NULL DEFAULT 'noop';

UPDATE users SET password_type = 'noop';