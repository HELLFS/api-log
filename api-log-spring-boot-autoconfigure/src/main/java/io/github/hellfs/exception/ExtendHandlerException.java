package io.github.hellfs.exception;

import io.github.hellfs.exception.errorcode.ErrorCode;
import io.github.hellfs.exception.errorcode.impl.ExtendHandlerErrorCodeEnum;
import lombok.Data;

/**
 * 扩展功能类相关异常类
 * @author hellfs
 * @date 2023-07-20
 */
@Data
public class ExtendHandlerException extends ApiLogException{

    public ExtendHandlerException() {
        super(ExtendHandlerErrorCodeEnum.UNKNOWN);
        super.errorCode = ExtendHandlerErrorCodeEnum.UNKNOWN;
    }

    public ExtendHandlerException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public ExtendHandlerException(Throwable e) {
        super(e);
    }

    public ExtendHandlerException(ErrorCode errorCode,Throwable e) {
        super(errorCode,e);
    }
}
