package io.github.hellfs.util;

import com.alibaba.fastjson2.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 日志打印时所需的工具类
 * @author hellfs
 * create by 2023-07-30
 */
public class LogHandlerUtil {

    /**
     * 占位符前置字符串
     */
    private final static String PLACEHOLDER_PREFIX = "${";
    /**
     * 占位符后置字符串
     */
    private final static String PLACEHOLDER_SUFFIX = "}";
    private final static String UNKNOWN = "未知";
    private final static String BRACE_PREFIX = "{";
    private final static String SPECIAL_PLACEHOLDER_REQ_PARAMS = "reqParams";
    private final static String SPECIAL_PLACEHOLDER_API_PARAMS = "apiParams";
    /**
     * 替换
     */
    private final static String PLACEHOLDER_PREFIX_PATTERN = "\\$\\{";

    /**
     * 获取打印格式语句中的所有占位符参数列表
     * 注意：只支持简单的一层占位符的情况，如：${xxx}
     * @param messageFormat  打印格式语句
     * @return  占位符参数列表
     */
    public static List<String> getLogParams(String messageFormat){
        List<String> data = new LinkedList<>();

        //查找初始值
        int startIndex = 0;

        while(startIndex < messageFormat.length()){
            startIndex = messageFormat.indexOf(PLACEHOLDER_PREFIX,startIndex);
            if(startIndex <= 0){
                break;
            }

            //查找开始值
            startIndex += 2;

            int endIndex = messageFormat.indexOf(PLACEHOLDER_SUFFIX,startIndex);
            if(endIndex <= 0){
                break;
            }
            String placeholder = messageFormat.substring(startIndex, endIndex);

            //只支持一层占位符，含有多层占位符，直接结束
            if(placeholder.indexOf(PLACEHOLDER_PREFIX) > 0){
                break;
            }

            //从上次结束下标开始查询
            startIndex = endIndex;

            data.add(placeholder);
        }

        return data;
    }

    /**
     * 解析打印格式，获取占位符对应的值（按顺序）  用于后续使用打印时不需要考虑顺序问题
     * @param placeholderList   占位符列表
     * @param apiParams         接口参数列表
     * @param requestParams     请求参数列表
     * @param extendDataMap     接口扩展数据列表
     * @return  数据列表
     */
    public static List<Object> placeholderData(List<String> placeholderList, JSONObject apiParams,
                                               Map<String, Object> requestParams, Map<String, String> extendDataMap){
        return placeholderList.stream().map(placeholder -> {
            if(SPECIAL_PLACEHOLDER_REQ_PARAMS.equals(placeholder)){
                return new LinkedList<>(requestParams.values());
            }else if(SPECIAL_PLACEHOLDER_API_PARAMS.equals(placeholder)){
                return new LinkedList<>(apiParams.values());
            }

            Object value = requestParams.containsKey(placeholder) ? requestParams.get(placeholder) : extendDataMap.get(placeholder);
            //没有获取到需要的属性时，对应的值为 未知
            if(Objects.isNull(value)){
                return UNKNOWN;
            }

            return value;
        }).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * 去除占位符
     * @param messageFormat     预去除占位符的打印语句格式字符串
     * @param placeholderList   占位符列表
     * @return  去除后的字符串
     */
    public static String removePlaceholder(String messageFormat,List<String> placeholderList){
        String logMessageFormat = messageFormat.replaceAll(PLACEHOLDER_PREFIX_PATTERN,BRACE_PREFIX);
        for (String placeholder : placeholderList) {
            logMessageFormat = logMessageFormat.replace(placeholder,"");
        }
        return logMessageFormat;
    }

}
