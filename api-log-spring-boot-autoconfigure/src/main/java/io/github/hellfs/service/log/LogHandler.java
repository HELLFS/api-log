package io.github.hellfs.service.log;

import io.github.hellfs.service.log.param.LogHandlerParams;

/**
 * 日志打印处理类接口
 * 使用方式：实现当前接口即可
 * @author hellfs
 * @date 2023-07-23
 */
public interface LogHandler {

    /**
     * 方法执行前执行
     */
    void before(LogHandlerParams logHandlerParams);

    /**
     * 方法正常执行后执行，如果抛出异常，则不执行
     */
    void afterReturning(LogHandlerParams logHandlerParams);

    /**
     * 方法抛出异常后执行
     */
    void afterThrowing(LogHandlerParams logHandlerParams);

    /**
     * 不管方法是正常执行还是抛出异常，都会执行。类比异常捕获处理的 finally 代码块
     */
    void after(LogHandlerParams logHandlerParams);
}
