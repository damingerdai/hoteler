ALTER TABLE room_groups ALTER COLUMN active SET DEFAULT TRUE;

ALTER TABLE room_groups RENAME TO room_group;
