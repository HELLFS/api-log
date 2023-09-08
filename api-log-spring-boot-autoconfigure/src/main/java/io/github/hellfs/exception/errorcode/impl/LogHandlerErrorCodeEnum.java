package io.github.hellfs.exception.errorcode.impl;

import io.github.hellfs.exception.errorcode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LogHandlerErrorCodeEnum implements ErrorCode {

    UNKNOWN(30000,"日志打印异常"),
    ;

    private final Integer code;
    private final String message;
}
