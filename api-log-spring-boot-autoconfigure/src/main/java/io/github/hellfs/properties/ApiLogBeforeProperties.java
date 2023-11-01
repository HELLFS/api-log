package io.github.hellfs.properties;

import io.github.hellfs.annotation.ApiLog;
import io.github.hellfs.annotation.extenddata.ExtendDataMethod;
import io.github.hellfs.annotation.extenddata.ExtendDataValue;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 前置日志打印-配置类
 * @author hellfs
 * create by 2023-07-23
 */
@Data
@ConfigurationProperties(prefix = "api-log.before")
public class ApiLogBeforeProperties {

    /**
     * 是否开启打印，控制 {@link ApiLog#beforeMessageFormat()}
     */
    private boolean enable = true;
    /**
     * 接口执行前置日志打印  {@link ApiLog#beforeMessageFormat()}  ${?}：占位符
     * 特殊定义占位符：
     *      reqParams：请求接口完整参数列表
     *      apiParams：接口接收参数列表
     *  自定义占位符，默认从请求参数中获取，获取不到，则 获取 {@link ExtendDataValue} 或 {@link ExtendDataMethod}；反之：未知
     *  可以配置统一实现，注解优先
     */
    private String messageFormat = "处理开始，参数列表:${reqParams}";

}
