ALTER TABLE users ADD COLUMN IF NOT EXISTS create_dt timestamp;
ALTER TABLE users ADD COLUMN IF NOT EXISTS create_user varchar(50);
ALTER TABLE users ADD COLUMN IF NOT EXISTS update_dt timestamp;
ALTER TABLE users ADD COLUMN IF NOT EXISTS update_user varchar(50);