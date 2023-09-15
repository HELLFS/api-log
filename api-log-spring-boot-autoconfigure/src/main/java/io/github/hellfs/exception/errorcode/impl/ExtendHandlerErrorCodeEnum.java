package io.github.hellfs.exception.errorcode.impl;

import io.github.hellfs.exception.errorcode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExtendHandlerErrorCodeEnum implements ErrorCode {

    UNKNOWN(40000,"扩展处理类执行异常"),
    BEAN_ERROR(40001,"扩展类注册bean失败"),
    ;

    private final Integer code;
    private final String message;
}
