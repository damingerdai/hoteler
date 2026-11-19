CREATE TABLE IF NOT EXISTS room_group (
    id uuid PRIMARY KEY NOT NULL,
    type varchar(20) NOT NULL,
    code varchar(50) NOT NULL,
    name varchar(100) NOT NULL,
    sort_order integer NOT NULL DEFAULT 0,
    active boolean NOT NULL DEFAULT true,
    create_dt timestamp NOT NULL,
    create_user varchar(50),
    update_dt timestamp NOT NULL,
    update_user varchar(50),
    deleted_at timestamp
);

ALTER TABLE rooms
    ADD COLUMN group_id uuid;

ALTER TABLE rooms
    ADD CONSTRAINT fk_rooms_group
    FOREIGN KEY (group_id)
    REFERENCES room_group(id);