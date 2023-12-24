package io.github.hellfs.autoconfigure;

import io.github.hellfs.aspect.ApiLogAspect;
import io.github.hellfs.properties.*;
import io.github.hellfs.service.base.impl.BaseHandler;
import io.github.hellfs.service.log.LogHandler;
import io.github.hellfs.service.log.impl.DefaultLogHandler;
import io.github.hellfs.service.order.ApiLogOrder;
import io.github.hellfs.service.order.DefaultApiLogOrder;
import io.github.hellfs.service.params.ApiLogParamsHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 业务层相关自动配置类
 * @author hellfs
 * create by 2023-09-07
 */
@EnableConfigurationProperties({
        ApiLogHeadProperties.class, ApiLogBeforeProperties.class, ApiLogAfterReturningProperties.class,
        ApiLogAfterThrowingProperties.class,ApiLogAfterProperties.class})
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

    @Bean
    @ConditionalOnMissingBean
    public LogHandler defaultLogHandler(){
        return new DefaultLogHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiLogOrder defaultApiLogOrder(){
        return new DefaultApiLogOrder();
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiLogParamsHandler apiLogParamsHandler(){
        return new ApiLogParamsHandler();
    }
}
