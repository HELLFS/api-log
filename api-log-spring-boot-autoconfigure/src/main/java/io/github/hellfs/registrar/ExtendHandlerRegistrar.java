package io.github.hellfs.registrar;

import io.github.hellfs.annotation.EnableExtendHandler;
import io.github.hellfs.exception.ExtendHandlerException;
import io.github.hellfs.exception.errorcode.impl.ExtendHandlerErrorCodeEnum;
import io.github.hellfs.service.extendhandler.ExtendHandler;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
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
 * 扩展处理类自动注册类
 * @author hellfs
 * @date 2023-09-14
 */
public class ExtendHandlerRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

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

            Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(EnableExtendHandler.class.getCanonicalName());
            for (String packageName1 : (String[]) annotationAttributes.get("value")) {
                if(StringUtils.hasText(packageName1)){
                    packageNames.add(packageName1);
                }
            }

            //动态注册bean
            ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false, environment);
            provider.addIncludeFilter(new AssignableTypeFilter(ExtendHandler.class));

            for (String name : packageNames) {
                Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(name);
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
