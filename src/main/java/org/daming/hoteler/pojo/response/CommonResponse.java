package org.daming.hoteler.pojo.response;

import java.io.Serializable;

/**
 * common reponse
 *
 * @author gming001
 * @create 2021-02-03 17:31
 **/
public class CommonResponse implements Serializable {

    private static final long serialVersionUID = 2069729986373230583L;

    private static final int SUCCESS_STATUS = 200;

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public CommonResponse() {
        super();
        this.status = SUCCESS_STATUS;
    }
}
