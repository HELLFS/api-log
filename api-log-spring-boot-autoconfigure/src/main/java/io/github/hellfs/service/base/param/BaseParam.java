package io.github.hellfs.service.base.param;

import com.alibaba.fastjson2.JSONObject;
import io.github.hellfs.annotation.ApiLog;
import lombok.Data;
import lombok.experimental.Accessors;
import org.aspectj.lang.JoinPoint;

import java.util.Map;

/**
 * 根处理参数
 * @author hellfs
 * create by 2023-09-07
 */
@Data
@Accessors(chain = true)
public class BaseParam {

    /**
     * 接口执行时间，单位：毫秒
     */
    private long executeTime;
    /**
     * 接口切入点对象
     */
    private JoinPoint joinPoint;
    /**
     * 自定义注解对象
     */
    private ApiLog apiLog;
    /**
     * 请求参数
     */
    private Map<String, Object> requestParams;
    /**
     * 接口参数
     */
    private JSONObject apiParams;
    /**
     * 异常对象
     */
    private Throwable throwable;
}
