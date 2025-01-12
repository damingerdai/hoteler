package org.daming.hoteler.pojo.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class ListPageResponse<T> extends ListResponse<T> {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ListPageResponse(List<T> data, int count) {
        super(data);
        this.count = count;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("count", count)
                .toString();
    }
}
