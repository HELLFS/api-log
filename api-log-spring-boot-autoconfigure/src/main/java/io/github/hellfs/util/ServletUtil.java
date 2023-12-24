package io.github.hellfs.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Servlet相关工具类
 * @author hellfs
 * create by 2023-07-20
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
     * @return  Map
     */
    public static Map<String,Object> getParamMap(HttpServletRequest request){
        Map<String,Object> map = new LinkedHashMap<>();
        if(request == null){
            return map;
        }

        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            if(entry.getValue().length == 0){
                map.put(entry.getKey(), null);
            }else{
                if(entry.getValue().length == 1){
                    map.put(entry.getKey(), entry.getValue()[0]);
                }else{
                    List<String> list = Stream.of(entry.getValue()).collect(Collectors.toList());
                    map.put(entry.getKey(),list);
                }
            }
        }
        return map;
    }
}
