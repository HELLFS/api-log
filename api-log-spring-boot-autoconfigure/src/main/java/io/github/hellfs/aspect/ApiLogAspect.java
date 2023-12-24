package io.github.hellfs.aspect;

import com.alibaba.fastjson2.JSONObject;
import io.github.hellfs.annotation.ApiLog;
import io.github.hellfs.service.base.impl.BaseHandler;
import io.github.hellfs.service.base.param.BaseParam;
import io.github.hellfs.service.order.ApiLogOrder;
import io.github.hellfs.service.params.ApiLogParamsHandler;
import io.github.hellfs.util.LoggerObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;

import java.util.Map;

/**
 * {@link ApiLog} 切面类
 */
@Aspect
public class ApiLogAspect extends LoggerObject implements Ordered {

    /**
     * 接口执行开始时间戳
     */
    private volatile long start;
    /**
     * 接口执行结束时间戳
     */
    private volatile long end;

    @Autowired
    private BaseHandler baseHandler;
    @Autowired
    private ApiLogParamsHandler apiLogParamsHandler;
    @Autowired
    private ApiLogOrder apiLogOrder;

    @Around(value = "@annotation(apiLog)")
    public Object around(ProceedingJoinPoint joinPoint, ApiLog apiLog) throws Throwable{
        Object proceed = null;
        BaseParam baseParam = new BaseParam();
        baseParam.setJoinPoint(joinPoint).setApiLog(apiLog);
        try {
            //获取请求参数
            Map<String, Object> requestParams = apiLogParamsHandler.requestParams();
            baseParam.setRequestParams(requestParams);

            JSONObject apiParams = apiLogParamsHandler.apiParams(joinPoint);
            baseParam.setApiParams(apiParams);
        } catch (Throwable e) {
            this.log("参数获取失败",e);
        }

        try {
            baseHandler.before(baseParam);
        } catch (Exception e) {
            this.log("前置方法执行失败",e);
        }

        start = System.currentTimeMillis();

        try {
            try {
                proceed = joinPoint.proceed(joinPoint.getArgs());
            } finally {
                end = System.currentTimeMillis();
            }
            baseParam.setExecuteTime(end - start);
            try {
                baseHandler.afterReturning(baseParam);
            } catch (Throwable ex) {
                this.log("后置方法执行失败",ex);
            }
        } catch (Throwable e) {
            baseParam.setExecuteTime(end - start).setThrowable(e);
            try {
                baseHandler.afterThrowing(baseParam);
            } catch (Throwable ex) {
                this.log("异常方法执行失败",e);
            }
            throw e;
        } finally {
            baseParam.setExecuteTime(end - start);
            try {
                baseHandler.after(baseParam);
            } catch (Throwable ex) {
                this.log("最终方法执行失败",ex);
            }
        }
        return proceed;
    }

    /**
     * 打印相关日志报错时，打印信息
     * @param message   打印相关通知日志
     * @param e         异常信息
     */
    private void log(String message, Throwable e){
        final String loggerExceptionFormat = "[api-log]%s，可向Github提交issue，项目地址可点击依赖查看<url>，失败原因:{}";
        logger.error(String.format(loggerExceptionFormat,message),e);
    }

    @Override
    public int getOrder() {
        return apiLogOrder.getOrder();
    }
}
