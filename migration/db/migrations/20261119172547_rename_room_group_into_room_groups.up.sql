ALTER TABLE room_group RENAME TO room_groups;

ALTER TABLE room_groups ALTER COLUMN active SET DEFAULT FALSE;