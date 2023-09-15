package io.github.hellfs.properties;

import io.github.hellfs.annotation.ApiLog;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * api-log-配置类
 * @author hellfs
 * @date 2023-07-23
 */
@Data
@ConfigurationProperties(prefix = "api-log")
public class ApiLogProperties {

    /**
     * 是否打印执行时间  {@link ApiLog#isExecutionTime()} 优先
     */
    private boolean isExecutionTime = true;

}
