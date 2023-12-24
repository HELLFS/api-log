package io.github.hellfs.service.order;


import org.springframework.core.Ordered;

/**
 * ApiLogAspect 顺序默认实现
 *  自定义可实现 ApiLogOrder 即可
 * @author hellfs
 * create by 2023-12-17
 */
public class DefaultApiLogOrder implements ApiLogOrder {
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
