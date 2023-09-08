package io.github.hellfs.autoconfigure;

import io.github.hellfs.exception.ApiLogException;
import io.github.hellfs.service.log.LogHandler;
import io.github.hellfs.service.log.impl.DefaultLogHandler;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.github.hellfs.exception.errorcode.impl.ApiLogErrorCodeEnum.IMPLEMENTS_COUNT_MORE;

/**
 * FactoryBean相关自动配置类
 * @author hellfs
 * @date 2023-09-07
 */
@Configuration
public class FactoryBeanAutoConfiguration {

    @Configuration
    public static class LogHandlerFactoryBean implements FactoryBean<LogHandler> {
        private Class<? extends LogHandler> clazz;

        @Override
        public LogHandler getObject() throws Exception {
            //获取 LogHandler 接口所有实现类
            ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
            provider.addIncludeFilter(new AssignableTypeFilter(LogHandler.class));
            Set<BeanDefinition> candidateComponents = provider.findCandidateComponents("*");

            //排除默认日志打印类
            List<BeanDefinition> definitions = candidateComponents.stream()
                    .filter(beanDefinition -> !DefaultLogHandler.class.getCanonicalName().equals(beanDefinition.getBeanClassName()))
                    .collect(Collectors.toList());

            if(definitions.isEmpty()){
                clazz = DefaultLogHandler.class;
                return new DefaultLogHandler();
            }

            //判断除默认日志打印实现类以外，只能存在一个实现类，出现多个即异常
            if(definitions.size() > 1){
                throw new ApiLogException(IMPLEMENTS_COUNT_MORE);
            }

            String beanClassName = definitions.get(0).getBeanClassName();
            clazz = (Class<? extends LogHandler>) Class.forName(beanClassName);
            return (LogHandler) clazz.newInstance();
        }

        @Override
        public Class<?> getObjectType() {
            return clazz;
        }
    }
}
