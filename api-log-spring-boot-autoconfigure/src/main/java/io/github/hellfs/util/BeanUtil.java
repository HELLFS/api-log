package io.github.hellfs.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * BeanUtil
 * @author hellfs
 * create by 2023-09-07
 */
public class BeanUtil implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }

    public <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }

    public <T> T getBean(String beanName,Class<T> clazz){
        return applicationContext.getBean(beanName,clazz);
    }
}
