CREATE TABLE IF NOT EXISTS rooms (
    id bigint primary key not null,
    roomname varchar(100) not null,
    status integer,
    create_dt timestamp,
    create_user varchar(50),
    update_dt timestamp,
    update_user varchar(50)
);