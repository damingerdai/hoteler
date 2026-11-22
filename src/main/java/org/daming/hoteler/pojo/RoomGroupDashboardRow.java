package org.daming.hoteler.pojo;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class RoomGroupDashboardRow {

    private String groupId;

    private String type;

    private String code;

    private String name;

    private int sortOrder;

    private int total;

    private int vacant;

    private int reserved;

    private int occupied;

    private int cleaning;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getVacant() {
        return vacant;
    }

    public void setVacant(int vacant) {
        this.vacant = vacant;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    public int getOccupied() {
        return occupied;
    }

    public void setOccupied(int occupied) {
        this.occupied = occupied;
    }

    public int getCleaning() {
        return cleaning;
    }

    public void setCleaning(int cleaning) {
        this.cleaning = cleaning;
    }

    public RoomGroupDashboardRow() {
    }

    public RoomGroupDashboardRow(String groupId, String type, String code, String name, int sortOrder, int total, int vacant, int reserved, int occupied, int cleaning) {
        this.groupId = groupId;
        this.type = type;
        this.code = code;
        this.name = name;
        this.sortOrder = sortOrder;
        this.total = total;
        this.vacant = vacant;
        this.reserved = reserved;
        this.occupied = occupied;
        this.cleaning = cleaning;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RoomGroupDashboardRow that)) return false;
        return sortOrder == that.sortOrder && total == that.total && vacant == that.vacant && reserved == that.reserved && occupied == that.occupied && cleaning == that.cleaning && Objects.equals(groupId, that.groupId) && Objects.equals(type, that.type) && Objects.equals(code, that.code) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, type, code, name, sortOrder, total, vacant, reserved, occupied, cleaning);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("groupId", groupId)
                .append("type", type)
                .append("code", code)
                .append("name", name)
                .append("sortOrder", sortOrder)
                .append("total", total)
                .append("vacant", vacant)
                .append("reserved", reserved)
                .append("occupied", occupied)
                .append("cleaning", cleaning)
                .toString();
    }
}
