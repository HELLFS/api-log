package io.github.hellfs.autoconfigure;

import io.github.hellfs.filter.RepeatableFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器相关自动配置类
 * @author hellfs
 * @date 2023-09-07
 */
@Configuration
public class FilterAutoConfiguration {

    /**
     * 主要作用：实现重复读取参数的request
     * @return  FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new RepeatableFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("repeatableFilter");
        registrationBean.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
        return registrationBean;
    }
}

