package io.github.hellfs.common.enums;

/**
 * 通知类型 {@link }
 * @author hellfs
 * @date 2023-07-16
 */
public enum AdviceType {

    /**
     * 前置
     */
    BEFORE,
    /**
     * 后置，接口执行完毕后正常返回
     */
    AFTER_RETURNING,
    /**
     * 异常
     */
    AFTER_THROWING
}
