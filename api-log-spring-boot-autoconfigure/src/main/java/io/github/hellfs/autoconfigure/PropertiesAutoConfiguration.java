package io.github.hellfs.autoconfigure;

import io.github.hellfs.properties.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Properties相关自动配置类
 * @author hellfs
 * @date 2023-09-07
 */
@Configuration
public class PropertiesAutoConfiguration {

    @Bean
    public ApiLogProperties apiLogProperties(){
        return new ApiLogProperties();
    }

    @Bean
    public ApiLogHeadProperties apiLogHeadProperties(){
        return new ApiLogHeadProperties();
    }

    @Bean
    public ApiLogBeforeProperties apiLogBeforeProperties(){
        return new ApiLogBeforeProperties();
    }

    @Bean
    public ApiLogAfterReturningProperties apiLogAfterReturningProperties(){
        return new ApiLogAfterReturningProperties();
    }

    @Bean
    public ApiLogAfterThrowingProperties apiLogAfterThrowingProperties(){
        return new ApiLogAfterThrowingProperties();
    }

    @Bean
    public ApiLogAfterProperties apiLogAfterProperties(){
        return new ApiLogAfterProperties();
    }
}
