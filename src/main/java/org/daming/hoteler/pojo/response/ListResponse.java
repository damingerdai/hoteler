package org.daming.hoteler.pojo.response;

import java.util.List;
import java.util.StringJoiner;

/**
 * list response
 *
 * @author gming001
 * @create 2021-02-09 18:28
 **/
public class ListResponse<T> extends CommonResponse {

    private static final long serialVersionUID = -4481212158971459689L;

    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public ListResponse(List<T> data) {
        super();
        this.data = data;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ListResponse.class.getSimpleName() + "[", "]")
                .add("data=" + data)
                .toString();
    }
}
