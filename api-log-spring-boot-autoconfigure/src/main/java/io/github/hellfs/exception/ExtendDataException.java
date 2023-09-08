package io.github.hellfs.exception;

import io.github.hellfs.exception.errorcode.ErrorCode;
import io.github.hellfs.exception.errorcode.impl.ExtendDataErrorCodeEnum;
import lombok.Data;

/**
 * ExtendData相关异常类
 * @author hellfs
 * @date 2023-07-20
 */
@Data
public class ExtendDataException extends ApiLogException{

    public ExtendDataException() {
        super(ExtendDataErrorCodeEnum.UNKNOWN);
        super.errorCode = ExtendDataErrorCodeEnum.UNKNOWN;
    }

    public ExtendDataException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public ExtendDataException(Throwable e) {
        super(e);
    }
}
