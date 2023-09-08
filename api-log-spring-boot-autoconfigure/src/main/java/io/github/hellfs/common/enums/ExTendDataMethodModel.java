package io.github.hellfs.common.enums;

import io.github.hellfs.annotation.extenddata.ExtendDataMethod;

/**
 * 获取方式，用于实现 扩展数据-方法 返回值获取方式
 * {@link ExtendDataMethod#model()}
 * @author hellfs
 * @date 2023-07-20
 */
public enum ExTendDataMethodModel {

    /**
     * 以类的形式获取，即不交由Spring容器管理
     */
    CLASS,
    /**
     * 以SpringBean的形式获取
     */
    BEAN
}
