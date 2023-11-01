package io.github.hellfs.exception.errorcode;

/**
 * 错误码接口
 * @author hellfs
 * create by 2023-07-20
 */
public interface ErrorCode {

    /**
     * 获取错误码
     * @return String
     */
    Integer getCode();

    /**
     * 获取错误描述
     * @return String
     */
    String getMessage();
}
