package org.daming.hoteler.pojo.response;

import java.util.StringJoiner;

/**
 * data response
 *
 * @author gming001
 * @create 2021-02-09 18:21
 **/
public class DataResponse<T> extends CommonResponse {

    private static final long serialVersionUID = -7597687245611176517L;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public DataResponse(T data) {
        super();
        this.data = data;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DataResponse.class.getSimpleName() + "[", "]")
                .add("data=" + data)
                .toString();
    }
}
