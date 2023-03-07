CREATE TABLE IF NOT EXISTS permissions (
    id  UUID NOT NULL DEFAULT gen_random_uuid (),
    name varchar(100) not null,
    description text not null,
    create_dt timestamp not null,
    create_user varchar(50) not null,
    update_dt timestamp not null,
    update_user varchar(50) not null,
    PRIMARY KEY (id)
    );

INSERT INTO permissions (name, description, create_dt, create_user, update_dt, update_user) VALUES
 ('manage_dashboard', 'Dashboard Management', now(), 'system', now(), 'system'),
 ('manage_customer', 'Customer Management', now(), 'system', now(), 'system'),
 ('manage_room', 'Room Management', now(), 'system', now(), 'system'),
 ('manage_user', 'User Management', now(), 'system', now(), 'system');

CREATE TABLE IF NOT EXISTS role_permissions
(
    id           UUID NOT NULL DEFAULT gen_random_uuid ()
    , role_id    bigint NOT NULL
    , permission_id    UUID NOT NULL
    , created_at TIMESTAMPTZ NOT NULL
    , updated_at TIMESTAMPTZ
    , deleted_at TIMESTAMPTZ,
    PRIMARY KEY (id),
    FOREIGN KEY (role_id) REFERENCES roles (id),
    FOREIGN KEY (permission_id) REFERENCES permissions (id),
    CONSTRAINT role_permissions_unique_role_permission_id UNIQUE (role_id, permission_id)
    );

CREATE INDEX IF NOT EXISTS role_permissions_role_id_idx ON role_permissions USING btree (role_id);
CREATE INDEX IF NOT EXISTS role_permissions_permission_id_idx ON role_permissions USING btree (permission_id);

INSERT INTO role_permissions (role_id, permission_id, created_at, updated_at )
SELECT r.id, p.id, now(), now() FROM permissions p INNER JOIN roles r ON r.name = 'users' AND p.name in ('manage_dashboard', 'manage_customer', 'manage_room');

INSERT INTO role_permissions (role_id, permission_id, created_at, updated_at )
SELECT r.id, p.id, now(), now() FROM permissions p INNER JOIN roles r ON r.name = 'admin' AND p.name in ('manage_dashboard', 'manage_user', 'manage_room');