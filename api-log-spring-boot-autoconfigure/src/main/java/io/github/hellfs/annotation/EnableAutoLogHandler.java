package io.github.hellfs.annotation;

import io.github.hellfs.registrar.LogHandlerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启日志打印类自动注册功能
 * 不需要使用者手动交给容器
 * @author hellfs
 * create by 2023-09-19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({LogHandlerRegistrar.class})
public @interface EnableAutoLogHandler {

    /**
     * 扫描的包路径列表
     * @return String[]
     */
    String[] value() default {};
}
