package io.github.hellfs.exception;

import io.github.hellfs.exception.errorcode.ErrorCode;
import io.github.hellfs.exception.errorcode.impl.LogHandlerErrorCodeEnum;
import lombok.Data;

/**
 * 整个启动器异常类
 * @author hellfs
 * @date 2023-07-20
 */
@Data
public class LogHandlerException extends ApiLogException{

    public LogHandlerException() {
        super(LogHandlerErrorCodeEnum.UNKNOWN);
        this.errorCode = LogHandlerErrorCodeEnum.UNKNOWN;
    }

    public LogHandlerException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public LogHandlerException(Throwable e) {
        super(e);
    }

    public LogHandlerException(ErrorCode errorCode,Throwable e){
        super(errorCode,e);
    }
}
