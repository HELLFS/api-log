package io.github.hellfs.registrar;

import io.github.hellfs.annotation.EnableAutoLogHandler;
import io.github.hellfs.exception.ExtendHandlerException;
import io.github.hellfs.exception.errorcode.impl.ExtendHandlerErrorCodeEnum;
import io.github.hellfs.service.log.LogHandler;
import io.github.hellfs.service.log.impl.DefaultLogHandler;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 日志打印处理类自动注册类
 * @author hellfs
 * @date 2023-09-19
 */
public class LogHandlerRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        try {
            //扫描包路径列表
            Set<String> packageNames = new HashSet<>();

            String className = annotationMetadata.getClassName();
            Class<?> clazz = Class.forName(className);
            String packageName = clazz.getPackage().getName();
            packageNames.add(packageName);

            Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(EnableAutoLogHandler.class.getCanonicalName());
            for (String packageName1 : (String[]) annotationAttributes.get("value")) {
                if(StringUtils.hasText(packageName1)){
                    packageNames.add(packageName1);
                }
            }

            //动态注册bean
            ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false, environment);
            provider.addIncludeFilter(new AssignableTypeFilter(LogHandler.class));

            for (String name : packageNames) {
                Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(name);

                //未查询到实现类则使用默认类
                String defaultLogHandlerCanonicalName = DefaultLogHandler.class.getCanonicalName();
                if(beanDefinitions.isEmpty() && !beanDefinitionRegistry.containsBeanDefinition(defaultLogHandlerCanonicalName)){
                    RootBeanDefinition beanDefinition = new RootBeanDefinition(DefaultLogHandler.class);
                    beanDefinitionRegistry.registerBeanDefinition(defaultLogHandlerCanonicalName,beanDefinition);
                    break;
                }

                for (BeanDefinition beanDefinition : beanDefinitions) {
                    String beanClassName = beanDefinition.getBeanClassName();
                    //未注册则注册，已注册跳过
                    if(!beanDefinitionRegistry.containsBeanDefinition(beanClassName)){
                        beanDefinitionRegistry.registerBeanDefinition(beanClassName,beanDefinition);
                    }
                }
            }
        } catch (Exception e) {
            throw new ExtendHandlerException(ExtendHandlerErrorCodeEnum.BEAN_ERROR,e);
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
