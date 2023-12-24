package io.github.hellfs.service.params;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.hellfs.util.ApiLogUtil;
import io.github.hellfs.util.HttpUtil;
import io.github.hellfs.util.ServletUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 参数处理类
 * @author hellfs
 * create by 2023-12-17
 */
public class ApiLogParamsHandler {

    /**
     * application/x-www-form-urlencoded   request.getParameterNames 和 request.getParameter 、 request.getParameterMap
     * application/json   HttpUtil.getBodyString
     * multipart/form-data   request.getParameterNames 和 request.getParameter 、 request.getParameterMap
     * todo 请求地址上的参数自动获取？对使用者无感知
     * 获取请求参数
     * @return Map
     */
    public Map<String, Object> requestParams(){
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            HttpServletRequest request = ServletUtil.getRequest();
            if(request == null){
                return map;
            }
            String contentType = request.getContentType();
            if(StringUtils.isNotEmpty(contentType) && contentType.contains("application/json")){
                //POST-application/json
                String bodyString = HttpUtil.getBodyString(request);
                JSONObject object = JSONObject.parseObject(bodyString);
                map.putAll(object);
            }else{
                //GET、DELETE、PUT(application/x-www-form-urlencoded 和 multipart/form-data)、
                // POST(application/x-www-form-urlencoded 和 multipart/form-data)
                Map<String, Object> paramMap = ServletUtil.getParamMap(request);
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
     * 获取请求参数
     * 判断本次参数个数：
     *  单个参数：判断是否为包装类，否则直接放入JSONObject并返回；是则转JSON对象返回
     *  多个参数：遍历组装，key和value
     * @param joinPoint  切入点对象
     * @return  参数json
     */
    public JSONObject apiParams(JoinPoint joinPoint){
        JSONObject data = JSONObject.of();

        //获取接口参数
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();

        for (int i = 0; i < args.length; i++) {
            //单参数
            if(args.length == 1){
                if(ApiLogUtil.isFilterObject(args[i])){
                    return data;
                }
                Class<?>[] parameterTypes = signature.getParameterTypes();
                //包装类
                if(ApiLogUtil.isWrapClass(parameterTypes[i]) || parameterTypes[i] == String.class){
                    data.put(parameterNames[i],args[i]);
                    return data;
                }

                //非包装类
                return JSONObject.parseObject(JSON.toJSONString(args[i]));
            }

            //多参数
            if(!ApiLogUtil.isFilterObject(args[i])){
                String parameterName = parameterNames[i];
                data.put(parameterName,args[i]);
            }
        }
        return data;
    }
}
