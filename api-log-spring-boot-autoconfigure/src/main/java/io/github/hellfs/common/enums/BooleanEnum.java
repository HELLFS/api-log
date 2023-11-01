package io.github.hellfs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 布尔枚举，用于注解属性中，实现注解优先于配置
 * @author hellfs
 * create by 2023-09-17
 */
@Getter
@AllArgsConstructor
public enum BooleanEnum {

    NULL(null),TRUE(Boolean.TRUE),FALSE(Boolean.FALSE);

    private final Boolean value;
}
