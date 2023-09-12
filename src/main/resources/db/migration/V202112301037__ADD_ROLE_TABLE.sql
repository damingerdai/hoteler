CREATE TABLE IF NOT EXISTS roles (
    id bigint primary key not null,
    name varchar(100) not null,
    description text not null,
    create_dt timestamp not null,
    create_user varchar(50) not null,
    update_dt timestamp not null,
    update_user varchar(50) not null
);

INSERT INTO roles (id, name, description, create_dt, create_user, update_dt, update_user) VALUES(1, 'admin', 'Admin', now(), 'system', now(), 'system');
INSERT INTO roles (id, name, description, create_dt, create_user, update_dt, update_user) VALUES(2, 'users', 'Standard User', now(), 'system', now(), 'system');
