package org.daming.hoteler.pojo;

import java.util.StringJoiner;

/**
 * @author gming001
 * @version 2024-02-29 15:00
 */
public class JobInfo {

    private String name;

    private String group;

    private String cron;

    private String state;

    private String timeZone;

    private String className;

    public String getName() {
        return name;
    }

    public JobInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public JobInfo setGroup(String group) {
        this.group = group;
        return this;
    }

    public String getCron() {
        return cron;
    }

    public JobInfo setCron(String cron) {
        this.cron = cron;
        return this;
    }

    public String getState() {
        return state;
    }

    public JobInfo setState(String state) {
        this.state = state;
        return this;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public JobInfo setTimeZone(String timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public JobInfo setClassName(String className) {
        this.className = className;
        return this;
    }

    public JobInfo() {
        super();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", JobInfo.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("group='" + group + "'")
                .add("cron='" + cron + "'")
                .add("state='" + state + "'")
                .add("timeZone='" + timeZone + "'")
                .add("className='" + className + "'")
                .toString();
    }
}
