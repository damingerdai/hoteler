package org.daming.hoteler.pojo.enums;

/**
 * room status
 *
 * @author gming001
 * @create 2020-12-22 22:39
 **/
public enum RoomStatus {

    NoUse(1, "no used"),
    InUsed(2, "in used");

    private int id;

    private String value;

    public int getId() {
        return this.id;
    }

    public String getValue() {
        return this.value;
    }

    RoomStatus(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public static RoomStatus getInstance(int id) {
        for (RoomStatus rs : values()) {
            if (rs.id ==  id) {
                return rs;
            }
        }
        throw new RuntimeException("no room status with " + id);
    }

    public static RoomStatus getInstance(String name) {
        for (RoomStatus rs : values()) {
            if (name.equals(rs.getValue())) {
                return rs;
            }
        }
        throw new RuntimeException("no room status with name " + name);
    }
}
