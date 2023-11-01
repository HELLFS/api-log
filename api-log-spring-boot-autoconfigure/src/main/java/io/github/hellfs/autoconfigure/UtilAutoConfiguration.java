package io.github.hellfs.autoconfigure;

import io.github.hellfs.util.BeanUtil;
import io.github.hellfs.util.ExtendDataUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 工具类相关自动配置类
 * @author hellfs
 * create by 2023-09-07
 */
@Configuration
public class UtilAutoConfiguration {

    @Bean
    public BeanUtil beanUtil(){
        return new BeanUtil();
    }

    @Bean
    public ExtendDataUtil extendDataUtil(){
        return new ExtendDataUtil();
    }
}
