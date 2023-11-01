package io.github.hellfs.annotation.extenddata;

import io.github.hellfs.annotation.ApiLog;
import io.github.hellfs.common.enums.ExTendDataMethodModel;

import java.lang.annotation.*;

/**
 * 自定义参数，无参方法类型，方法返回值，
 * 用于 {@link ApiLog#beforeMessageFormat()}  自定义占位符
 * 用于 {@link ApiLog#afterReturningMessageFormat()}   自定义占位符
 * 用于 {@link ApiLog#afterThrowingMessageFormat()}   自定义占位符
 * 用于 {@link ApiLog#afterMessageFormat()}   自定义占位符
 * @author hellfs
 * create by 2023-07-22
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ExtendDataMethods.class)
@Documented
public @interface ExtendDataMethod {

    /**
     * 键
     * @return String
     */
    String key();

    /**
     * 类对象
     * @return Class
     */
    Class<?> clazz();

    /**
     * 方法名
     * @return String
     */
    String methodName();

    /**
     * 普通类还是bean
     * @return ExTendDataMethodModel
     */
    ExTendDataMethodModel model() default ExTendDataMethodModel.CLASS;
}
