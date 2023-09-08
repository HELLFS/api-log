package io.github.hellfs.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Servlet相关工具类
 * @author hellfs
 * @date 2023-07-20
 */
public class ServletUtil {

    /**
     * 获取请求
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest(){
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if(!(requestAttributes instanceof ServletRequestAttributes)){
            return null;
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    /**
     * 获取请求参数
     * @param request 请求对象
     * @return  Map<String,String>
     */
    public static Map<String,String> getParamMap(HttpServletRequest request){
        Map<String,String> map = new LinkedHashMap<>();
        if(request == null){
            return map;
        }

        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            map.put(entry.getKey(), String.join(",", entry.getValue()));
        }
        return map;
    }
}
