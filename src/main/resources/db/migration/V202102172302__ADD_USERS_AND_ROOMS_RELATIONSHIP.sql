CREATE TABLE users_rooms (
    id bigint primary key not null,
    user_id bigint not null,
    room_id bigint not null,
    begin_date timestamp,
    end_date timestamp,
    create_dt timestamp,
    create_user varchar(50),
    update_dt timestamp,
    update_user varchar(50)
)