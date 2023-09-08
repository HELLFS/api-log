package io.github.hellfs.annotation.extenddata;

import io.github.hellfs.annotation.ApiLog;

import java.lang.annotation.*;

/**
 * 自定义参数，字符串类型，常量值
 * 用于 {@link ApiLog#beforeMessageFormat()}  自定义占位符
 * 用于 {@link ApiLog#afterReturningMessageFormat()}   自定义占位符
 * 用于 {@link ApiLog#afterThrowingMessageFormat()}   自定义占位符
 * @author hellfs
 * @date 2023-07-22
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ExtendDataValues.class)
@Documented
public @interface ExtendDataValue {

    /**
     * 键，仿照Map形式
     */
    String key();

    /**
     * 值，仿照Map形式
     */
    String value();
}
