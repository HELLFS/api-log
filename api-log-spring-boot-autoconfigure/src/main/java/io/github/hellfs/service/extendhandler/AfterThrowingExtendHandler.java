package io.github.hellfs.service.extendhandler;

import io.github.hellfs.service.extendhandler.param.ExtendHandlerParams;

/**
 * 前置扩展处理接口，方法抛出异常后执行，使用时实现该接口即可
 * @author hellfs
 * @date 2023-07-23
 */
public interface AfterThrowingExtendHandler extends ExtendHandler {

    void afterThrowing(ExtendHandlerParams extendHandlerParams);
}
