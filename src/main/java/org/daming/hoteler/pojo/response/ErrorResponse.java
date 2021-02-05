package org.daming.hoteler.pojo.response;

import org.daming.hoteler.pojo.ApiError;

/**
 * error reponse
 *
 * @author gming001
 * @create 2021-02-04 14:11
 **/
public class ErrorResponse extends CommonResponse {

    private static final long serialVersionUID = -8004008325771258589L;

    private ApiError error;

    public ApiError getError() {
        return error;
    }

    public void setError(ApiError error) {
        this.error = error;
    }

    public ErrorResponse() {
        super();
        this.setStatus(-1);
    }

    public ErrorResponse(ApiError error) {
        super();
        this.setStatus(-1);
        this.error = error;
    }
}
