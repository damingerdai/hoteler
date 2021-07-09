CREATE TABLE customers (
    id bigint primary key not null,
    name varchar(100) not null,
    gender char(1) default 'N',
    card_id char(18) not null ,
    phone bigint,
    create_dt timestamp,
    create_user varchar(50),
    update_dt timestamp,
    update_user varchar(50)
)