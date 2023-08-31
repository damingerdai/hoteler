package org.daming.hoteler.pojo.request;

import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * @author gming001
 * @version 2023-08-31 21:14
 */
public class AddPingJobTaskRequest {

    private LocalDateTime runDateTime;

    public LocalDateTime getRunDateTime() {
        return runDateTime;
    }

    public void setRunDateTime(LocalDateTime runDateTime) {
        this.runDateTime = runDateTime;
    }

    public AddPingJobTaskRequest(LocalDateTime runDateTime) {
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
