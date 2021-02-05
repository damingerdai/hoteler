package org.daming.hoteler.api.advice;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.pojo.ApiError;
import org.daming.hoteler.pojo.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * common exception handler
 *
 * @author gming001
 * @create 2020-12-13 19:06
 **/
@RestControllerAdvice
public class HotelerExceptionHandler {

    @ExceptionHandler(value = HotelerException.class)
    public ErrorResponse baseErrorHandler(Exception e) throws Exception {
        HotelerException de = (HotelerException) e;
        var response = new ErrorResponse();
        var apiError = new ApiError().setCode("ERR-" + de.getCode()).setMessage(de.getMessage());
        response.setError(apiError);

        return response;
    }

    @ExceptionHandler(value = Exception.class)
    public ErrorResponse defaultErrorHandler(Exception e) throws Exception {
        // LoggerManager.getErrorLogger().error("ERR-" + ErrorCodeConstant.ERR_SYSTEM+ ", " + e.getMessage(),e);
        var response = new ErrorResponse();
        var error = new ApiError().setCode("ERR-600001").setMessage(e.getMessage());
        response.setError(error);

        return response;
    }
}
