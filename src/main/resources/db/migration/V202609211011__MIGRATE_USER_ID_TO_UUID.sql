BEGIN;


CREATE TABLE users_new (
   id UUID PRIMARY KEY DEFAULT uuid_generate_v7(),
   username character varying(100) NOT NULL,
   password character varying(100) NOT NULL,
   create_dt timestamp without time zone NOT NULL,
   create_user character varying(50) NOT NULL,
   update_dt timestamp without time zone NOT NULL,
   update_user character varying(50) NOT NULL,
   password_type passwordtype NOT NULL DEFAULT 'noop'::passwordtype,
   deleted_at timestamp with time zone,
   failed_login_attempts integer DEFAULT 0,
   account_non_locked boolean DEFAULT true,
   lock_time timestamp without time zone,
   old_id_bigint bigint
);

INSERT INTO users_new (
    id, username, password, create_dt, create_user,
    update_dt, update_user, password_type, deleted_at,
    failed_login_attempts, account_non_locked, lock_time, old_id_bigint
)
SELECT
    uuid_generate_v7(), username, password, create_dt, create_user,
    update_dt, update_user, password_type, deleted_at,
    failed_login_attempts, account_non_locked, lock_time, id
FROM users;

CREATE TABLE user_roles_new (
                                id UUID PRIMARY KEY DEFAULT uuid_generate_v7(),
                                user_id UUID NOT NULL,
                                role_id bigint NOT NULL,
                                created_at timestamp with time zone NOT NULL,
                                updated_at timestamp with time zone,
                                deleted_at timestamp with time zone,
                                old_id_uuid uuid,
                                old_user_id_bigint bigint,
                                CONSTRAINT user_roles_new_unique_user_role_id UNIQUE (user_id, role_id)
);

INSERT INTO user_roles_new (
    id, user_id, role_id, created_at, updated_at, deleted_at, old_id_uuid, old_user_id_bigint
)
SELECT
    ur.id, un.id, ur.role_id, ur.created_at, ur.updated_at, ur.deleted_at, ur.id, u.id
FROM user_roles ur
         JOIN users u ON ur.user_id = u.id
         JOIN users_new un ON u.id = un.old_id_bigint;

DROP TABLE user_roles CASCADE;
DROP TABLE users CASCADE;

ALTER TABLE users_new RENAME TO users;
ALTER TABLE user_roles_new RENAME TO user_roles;

ALTER TABLE user_roles
    ADD CONSTRAINT user_roles_user_id_fkey
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE user_roles
    ADD CONSTRAINT user_roles_unique_user_role_id
        UNIQUE (user_id, role_id);

CREATE INDEX IF NOT EXISTS user_roles_user_id_idx ON user_roles(user_id);
CREATE INDEX IF NOT EXISTS user_roles_role_id_idx ON user_roles(role_id);

COMMIT;