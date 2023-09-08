package io.github.hellfs.properties;

import io.github.hellfs.annotation.ApiLog;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * api-log-head-配置类
 * @author hellfs
 * @date 2023-07-23
 */
@Data
@ConfigurationProperties(prefix = "api-log.head")
public class ApiLogHeadProperties {

    /**
     * 头信息前置符，也可以 {@link ApiLog#logHeadPrefix()}
     */
    private String prefix = "[";
    /**
     * 头信息后置符，也可以 {@link ApiLog#logHeadSuffix()}
     */
    private String suffix = "]";

}
