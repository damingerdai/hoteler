CREATE TABLE IF NOT EXISTS user_roles
  (
     id           UUID NOT NULL DEFAULT gen_random_uuid ()
     , user_id    bigint NOT NULL
     , role_id    bigint NOT NULL
     , created_at TIMESTAMPTZ NOT NULL
     , updated_at TIMESTAMPTZ
     , deleted_at TIMESTAMPTZ,
     PRIMARY KEY (id),
     FOREIGN KEY (user_id) REFERENCES users (id),
     FOREIGN KEY (role_id) REFERENCES roles (id),
     CONSTRAINT user_roles_unique_user_role_id UNIQUE (user_id, role_id)
  );

CREATE INDEX IF NOT EXISTS user_roles_user_id_idx ON user_roles USING btree (user_id);
CREATE INDEX IF NOT EXISTS user_roles_role_id_idx ON user_roles USING btree (role_id);

INSERT INTO user_roles  (user_id, role_id, created_at, updated_at) SELECT users.id, roles.id, statement_timestamp(), statement_timestamp() FROM users LEFT JOIN roles ON roles.name = 'users';

INSERT INTO user_roles  (user_id, role_id, created_at, updated_at) SELECT users.id, roles.id, statement_timestamp(), statement_timestamp() FROM users LEFT JOIN roles ON roles.name = 'admin' WHERE users.username = 'admin';
