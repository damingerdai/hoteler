package org.daming.hoteler.pojo;

import org.daming.hoteler.pojo.enums.HotelerEvent;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author gming001
 * @version 2023-07-23 17:47
 */
public class HotelerMessage implements Serializable {

    private HotelerEvent event;

    private String content;

    public HotelerEvent getEvent() {
        return event;
    }

    public void setEvent(HotelerEvent event) {
        this.event = event;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HotelerMessage() {
        super();
    }

    public HotelerMessage(HotelerEvent event, String content) {
        super();
        this.event = event;
        this.content = content;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", HotelerMessage.class.getSimpleName() + "[", "]")
                .add("event=" + event)
                .add("content='" + content + "'")
                .toString();
    }
}
