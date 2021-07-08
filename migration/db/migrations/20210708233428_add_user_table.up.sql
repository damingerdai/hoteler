CREATE TABLE users (
    id bigint primary key not null,
    username varchar(100) not null,
    password varchar(100) not null,
    create_dt timestamp not null,
    create_user varchar(50) not null,
    update_dt timestamp not null,
    update_user varchar(50) not null
)
