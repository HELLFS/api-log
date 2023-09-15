package io.github.hellfs.exception;

import io.github.hellfs.exception.errorcode.ErrorCode;

/**
 * 异常基类
 */
public class ApiLogException extends RuntimeException{

    protected ErrorCode errorCode;

    public ApiLogException() {
    }

    public ApiLogException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiLogException(Throwable e) {
        super(e);
    }

    public ApiLogException(ErrorCode errorCode,Throwable e) {
        super(errorCode.getMessage(),e);
        this.errorCode = errorCode;
    }
}
