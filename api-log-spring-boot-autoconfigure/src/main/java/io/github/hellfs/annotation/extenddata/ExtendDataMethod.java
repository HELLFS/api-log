package io.github.hellfs.annotation.extenddata;

import io.github.hellfs.annotation.ApiLog;
import io.github.hellfs.common.enums.ExTendDataMethodModel;

import java.lang.annotation.*;

/**
 * 自定义参数，无参方法类型，方法返回值，
 * 用于 {@link ApiLog#beforeMessageFormat()}  自定义占位符
 * 用于 {@link ApiLog#afterReturningMessageFormat()}   自定义占位符
 * 用于 {@link ApiLog#afterThrowingMessageFormat()}   自定义占位符
 * @author hellfs
 * @date 2023-07-22
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ExtendDataMethods.class)
@Documented
public @interface ExtendDataMethod {

    String key();

    Class<?> clazz();

    String methodName();

    ExTendDataMethodModel model() default ExTendDataMethodModel.CLASS;
}
