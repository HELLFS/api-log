package io.github.hellfs.service.extendhandler;

import io.github.hellfs.service.extendhandler.param.ExtendHandlerParams;

/**
 * 前置扩展处理接口，使用时实现该接口即可
 * 不管方法是正常执行还是抛出异常，都会执行。类似异常捕获处理的 finally 代码块
 * @author hellfs
 * @date 2023-07-23
 */
public interface AfterExtendHandler extends ExtendHandler {

    void after(ExtendHandlerParams extendHandlerParams);
}
