CREATE TABLE IF NOT EXISTS users (
    id bigint primary key not null,
    username varchar(100) not null,
    password varchar(100) not null
);
