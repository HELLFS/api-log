package io.github.hellfs.exception.errorcode.impl;

import io.github.hellfs.exception.errorcode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExtendDataErrorCodeEnum implements ErrorCode {

    UNKNOWN(20000,"获取自定义数据异常"),
    GET_EXTEND_DATA_FAILED(20001,"获取extendDataMethod失败"),
    ;

    private final Integer code;
    private final String message;
}
