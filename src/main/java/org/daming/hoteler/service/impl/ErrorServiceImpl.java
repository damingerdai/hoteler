package org.daming.hoteler.service.impl;

import org.daming.hoteler.base.exceptions.ExceptionBuilder;
import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.config.service.IErrorCodeService;
import org.daming.hoteler.constants.ErrorCodeConstants;
import org.daming.hoteler.service.IErrorService;
import org.springframework.stereotype.Service;

/**
 * ErrorService
 *
 * @author gming001
 * @create 2020-12-25 21:48
 **/
@Service
public class ErrorServiceImpl implements IErrorService {

    private IErrorCodeService errorCodeService;

    @Override
    public HotelerException createHotelerException(int code, Object[] params, Throwable cause) {
        var message = this.errorCodeService.getMessage(code, params);
        return ExceptionBuilder.buildException(code, message, cause);
    }

    @Override
    public HotelerException createHotelerSystemException(String message, Throwable cause) {
        return ExceptionBuilder.buildException(ErrorCodeConstants.SYSTEM_ERROR_CODEE, message, cause);
    }

    @Override
    public HotelerException createHotelerException(int code, Throwable cause) {
        var message = this.errorCodeService.getMessage(code);
        return ExceptionBuilder.buildException(code, message, cause);
    }

    @Override
    public HotelerException createHotelerException(int code) {
        var message = this.errorCodeService.getMessage(code);
        return ExceptionBuilder.buildException(code, message);
    }

    @Override
    public HotelerException createSqlHotelerException(Exception ex, Object... params) {
        var message = this.errorCodeService.getMessage(ErrorCodeConstants.SQL_ERROR_CODE, params);
        return ExceptionBuilder.buildException(ErrorCodeConstants.SQL_ERROR_CODE, message, ex);
    }

    public ErrorServiceImpl(IErrorCodeService errorCodeService) {
        super();
        this.errorCodeService = errorCodeService;
    }
}
