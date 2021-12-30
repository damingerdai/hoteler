package org.daming.hoteler.pojo;

import java.io.Serializable;
import java.util.StringJoiner;

public class Role implements Serializable {

    private long id;

    private String name;

    private String description;

    public long getId() {
        return id;
    }

    public Role setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Role setDescription(String description) {
        this.description = description;
        return this;
    }

    public Role() {
        super();
    }

    public Role(long id, String name, String description) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Role.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .toString();
    }
}
