ALTER TABLE users DROP COLUMN IF EXISTS deleted_at;
ALTER TABLE rooms DROP COLUMN IF EXISTS deleted_at;
ALTER TABLE customers DROP COLUMN IF EXISTS deleted_at;
ALTER TABLE roles DROP COLUMN IF EXISTS deleted_at;
ALTER TABLE customer_checkin_record DROP COLUMN IF EXISTS deleted_at;