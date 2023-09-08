package io.github.hellfs.annotation.extenddata;

import java.lang.annotation.*;

/**
 * 自定义参数，字符串类型，方法返回值 重复注解，方便使用
 * @author hellfs
 * @date 2023-07-22
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExtendDataMethods {

    ExtendDataMethod[] value();
}
