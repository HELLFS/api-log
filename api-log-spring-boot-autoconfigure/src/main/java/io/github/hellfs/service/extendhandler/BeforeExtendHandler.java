package io.github.hellfs.service.extendhandler;

import io.github.hellfs.service.extendhandler.param.ExtendHandlerParams;

/**
 * 前置扩展处理接口，方法执行前执行，使用时实现该接口即可
 * @author hellfs
 * @date 2023-07-23
 */
public interface BeforeExtendHandler {

    void before(ExtendHandlerParams extendHandlerParams);
}
