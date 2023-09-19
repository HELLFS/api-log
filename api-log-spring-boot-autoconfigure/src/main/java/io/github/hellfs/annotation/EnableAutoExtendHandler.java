package io.github.hellfs.annotation;

import io.github.hellfs.registrar.ExtendHandlerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启扩展类自动注册功能
 * 不需要使用者手动交给容器
 * @author hellfs
 * @date 2023-09-14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({ExtendHandlerRegistrar.class})
public @interface EnableAutoExtendHandler {

    /**
     * 扫描的包路径列表
     */
    String[] value() default {};
}
