package io.github.hellfs.aspect;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.hellfs.annotation.ApiLog;
import io.github.hellfs.service.base.impl.BaseHandler;
import io.github.hellfs.service.base.param.BaseParam;
import io.github.hellfs.util.LoggerObject;
import io.github.hellfs.util.ServletUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@link ApiLog} 切面类
 */
@Aspect
public class ApiLogAspect extends LoggerObject {

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

    @Around(value = "@annotation(apiLog)")
    public Object around(ProceedingJoinPoint joinPoint, ApiLog apiLog) throws Throwable{
        Object proceed = null;

        //获取请求参数
        Map<String, Object> requestParams = this.requestParams();
        //获取接口参数
        Object[] args = joinPoint.getArgs();
        JSONObject apiParams = this.apiParams(args);


        BaseParam baseParam = new BaseParam();
        baseParam.setJoinPoint(joinPoint).setApiLog(apiLog).setRequestParams(requestParams)
                .setApiParams(apiParams);
        try {
            baseHandler.before(baseParam);
            start = System.currentTimeMillis();
            try {
                proceed = joinPoint.proceed(args);
            } finally {
                end = System.currentTimeMillis();
            }
            baseParam.setExecuteTime(end - start);
            baseHandler.afterReturning(baseParam);
        } catch (Throwable e) {
            baseParam.setExecuteTime(end - start).setThrowable(e);
            baseHandler.afterThrowing(baseParam);
            throw e;
        } finally {
            baseParam.setExecuteTime(end - start);
            baseHandler.after(baseParam);
        }
        return proceed;
    }


    /**
     * 请求参数拼接
     * @param args  请求入参
     * @return  参数json
     */
    private JSONObject apiParams(Object[] args){
        final String empty = "";
        StringBuilder builder = new StringBuilder();
        for (Object arg : args) {
            if(!isFilterObject(arg)){
                String str = JSON.toJSONString(arg);
                builder.append(str).append(empty);
            }
        }

        if(StringUtils.isEmpty(builder)){
            return JSONObject.of();
        }
        return JSONObject.parseObject(builder.toString().trim());
    }

    /**
     * 请求参数(完整参数，不管接口是否需要)
     * @return  参数json
     */
    private Map<String, Object> requestParams(){
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            HttpServletRequest request = ServletUtil.getRequest();

            String bodyString = this.getBodyString(request);
            JSONObject object = JSONObject.parseObject(bodyString);
            map.putAll(object);

            // body参数为空，获取Parameter的数据
            if (map.isEmpty())
            {
                Map<String, String> paramMap = ServletUtil.getParamMap(request);
                map.putAll(paramMap);
            }
        } catch (Exception e) {
            //不处理
        }
        if(map.isEmpty()){
            return map;
        }
        return map;
    }

    /**
     * 获取请求体数据
     * @param request 请求对象
     * @return String
     */
    public String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try (InputStream inputStream = request.getInputStream()) {
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        catch (IOException e) {
            logger.warn("[ApiLog]获取请求体失败，失败原因:{}",e);
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    logger.error(ExceptionUtils.getMessage(e));
                }
            }
        }
        return sb.toString();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}
