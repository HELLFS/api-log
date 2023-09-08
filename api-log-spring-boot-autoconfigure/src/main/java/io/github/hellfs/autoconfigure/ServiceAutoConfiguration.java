package io.github.hellfs.autoconfigure;

import io.github.hellfs.aspect.ApiLogAspect;
import io.github.hellfs.service.base.impl.BaseHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 业务层相关自动配置类
 * @author hellfs
 * @date 2023-09-07
 */
@Configuration
public class ServiceAutoConfiguration {

    @Bean
    public ApiLogAspect apiLogAspect(){
        return new ApiLogAspect();
    }

    @Bean
    public BaseHandler baseHandler(){
        return new BaseHandler();
    }
}
