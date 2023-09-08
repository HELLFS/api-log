package io.github.hellfs.exception.errorcode.impl;

import io.github.hellfs.exception.errorcode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiLogErrorCodeEnum implements ErrorCode {

    UNKNOWN(10000,"日志打印异常"),
    IMPLEMENTS_COUNT_MORE(10001,"LogHandler实现类存在多个"),
    ;

    private final Integer code;
    private final String message;
}
