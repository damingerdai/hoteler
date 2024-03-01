package org.daming.hoteler.pojo.request;

import java.time.ZonedDateTime;
import java.util.StringJoiner;

/**
 * @author gming001
 * @version 2023-08-31 21:14
 */
public class AddPingJobTaskRequest {

    private ZonedDateTime runDateTime;

    public ZonedDateTime getRunDateTime() {
        return runDateTime;
    }

    public void setRunDateTime(ZonedDateTime runDateTime) {
        this.runDateTime = runDateTime;
    }

    public AddPingJobTaskRequest(ZonedDateTime runDateTime) {
        super();
        this.runDateTime = runDateTime;
    }

    public AddPingJobTaskRequest() {
        super();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AddPingJobTaskRequest.class.getSimpleName() + "[", "]")
                .add("runDateTime=" + runDateTime)
                .toString();
    }
}
