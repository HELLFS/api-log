package io.github.hellfs.service.extendhandler;

import io.github.hellfs.service.extendhandler.param.ExtendHandlerParams;

/**
 * 前置扩展处理接口，方法正常执行后执行，如果抛出异常，则不执行，使用时实现该接口即可
 * @author hellfs
 * @date 2023-07-23
 */
public interface AfterReturningExtendHandler {

    void afterReturning(ExtendHandlerParams extendHandlerParams);
}
