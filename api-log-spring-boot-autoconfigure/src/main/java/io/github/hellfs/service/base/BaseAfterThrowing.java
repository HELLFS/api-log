package io.github.hellfs.service.base;

import io.github.hellfs.service.base.param.BaseParam;

/**
 * 前置扩展处理接口，方法抛出异常后执行
 * @author hellfs
 * @date 2023-09-07
 */
public interface BaseAfterThrowing {

    void afterThrowing(BaseParam baseParam);
}
