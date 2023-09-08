package io.github.hellfs.service.base;

import io.github.hellfs.service.base.param.BaseParam;

/**
 * 前置扩展处理接口
 * @author hellfs
 * @date 2023-09-07
 */
public interface BaseAfter {

    void after(BaseParam baseParam);
}
