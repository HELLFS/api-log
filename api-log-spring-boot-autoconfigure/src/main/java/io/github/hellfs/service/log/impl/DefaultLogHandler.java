package io.github.hellfs.service.log.impl;

import com.alibaba.fastjson2.JSONObject;
import io.github.hellfs.annotation.ApiLog;
import io.github.hellfs.common.enums.BooleanEnum;
import io.github.hellfs.properties.*;
import io.github.hellfs.service.log.LogHandler;
import io.github.hellfs.service.log.param.LogHandlerParams;
import io.github.hellfs.util.ExtendDataUtil;
import io.github.hellfs.util.LogHandlerUtil;
import io.github.hellfs.util.LoggerObject;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 默认日志打印类
 * @author hellfs
 * create by 2023-07-23
 */
public class DefaultLogHandler extends LoggerObject implements LogHandler {

    private final String EXECUTE_TIME_MESSAGE_FORMAT = "，执行时间:{}ms";
    private final String THROW_MESSAGE_FORMAT = "，失败原因:{}";
    /**
     * 输出日志拼接对象
     */
    private StringBuilder builder;
    @Autowired
    private ApiLogHeadProperties headProperties;
    @Autowired
    private ApiLogBeforeProperties beforeProperties;
    @Autowired
    private ApiLogAfterReturningProperties afterReturningProperties;
    @Autowired
    private ApiLogAfterThrowingProperties afterThrowingProperties;
    @Autowired
    private ApiLogAfterProperties afterProperties;
    @Autowired
    private ExtendDataUtil extendDataUtil;

    @Override
    public void before(LogHandlerParams logHandlerParams) {
        if(!beforeProperties.isEnable()){
            return;
        }

        ApiLog apiLog = logHandlerParams.getApiLog();
        String beforeFormat = StringUtils.isNotEmpty(apiLog.beforeMessageFormat()) ?
                apiLog.beforeMessageFormat() : beforeProperties.getMessageFormat();

        builder = new StringBuilder();
        List<Object> placeholderData = this.handler(logHandlerParams, beforeFormat, builder);

        logger.info(builder.toString(),placeholderData.toArray());
    }

    @Override
    public void afterReturning(LogHandlerParams logHandlerParams) {
        if(!afterReturningProperties.isEnable()){
            return;
        }

        ApiLog apiLog = logHandlerParams.getApiLog();

        String afterReturningFormat = StringUtils.isNotEmpty(apiLog.afterReturningMessageFormat()) ?
                apiLog.afterReturningMessageFormat() : afterReturningProperties.getMessageFormat();

        builder = new StringBuilder();
        List<Object> placeholderData = this.handler(logHandlerParams, afterReturningFormat, builder);

        this.isPrintExecutionTime(logHandlerParams,placeholderData,afterReturningProperties.isExecutionTime());

        logger.info(builder.toString(),placeholderData.toArray());
    }

    @Override
    public void afterThrowing(LogHandlerParams logHandlerParams) {
        if(!afterThrowingProperties.isEnable()){
            return;
        }

        ApiLog apiLog = logHandlerParams.getApiLog();

        String afterThrowingFormat = StringUtils.isNotEmpty(apiLog.afterThrowingMessageFormat()) ?
                apiLog.afterThrowingMessageFormat() : afterThrowingProperties.getMessageFormat();

        builder = new StringBuilder();
        List<Object> placeholderData = this.handler(logHandlerParams, afterThrowingFormat, builder);

        this.isPrintExecutionTime(logHandlerParams,placeholderData,afterThrowingProperties.isExecutionTime());

        this.isPrintStackMessage(logHandlerParams,placeholderData,afterThrowingProperties.isStackMessage());

        logger.error(builder.toString(),placeholderData.toArray());
    }

    @Override
    public void after(LogHandlerParams logHandlerParams) {
        if(!afterProperties.isEnable()){
            return;
        }
        ApiLog apiLog = logHandlerParams.getApiLog();

        String afterFormat = StringUtils.isNotEmpty(apiLog.afterMessageFormat()) ?
                apiLog.afterMessageFormat() : afterProperties.getMessageFormat();

        builder = new StringBuilder();
        List<Object> placeholderData = this.handler(logHandlerParams, afterFormat, builder);

        this.isPrintExecutionTime(logHandlerParams,placeholderData,afterProperties.isExecutionTime());

        logger.info(builder.toString(),placeholderData.toArray());
    }

    /**
     * 主要处理逻辑
     *  1.拼接输出日志
     *  2.根据占位符列表组装对应的数据列表
     *
     * @param logHandlerParams   相关数据对象
     * @param messageFormat         日志输出格式
     * @param builder               输出日志拼接对象
     * @return  placeholderData
     */
    private List<Object> handler(LogHandlerParams logHandlerParams, String messageFormat,StringBuilder builder){
        ApiLog apiLog = logHandlerParams.getApiLog();

        JoinPoint joinPoint = logHandlerParams.getJoinPoint();
        JSONObject apiParams = logHandlerParams.getApiParams();
        Map<String, Object> requestParams = logHandlerParams.getRequestParams();

        //获取自定义参数
        Map<String, String> extendDataMap = extendDataUtil.extendData(joinPoint);

        String logHeadPrefix = StringUtils.isNotEmpty(apiLog.logHeadPrefix()) ? apiLog.logHeadPrefix() : headProperties.getPrefix();
        String logHeadSuffix = StringUtils.isNotEmpty(apiLog.logHeadSuffix()) ? apiLog.logHeadSuffix() : headProperties.getSuffix();

        //拼接日志
        builder.append(logHeadPrefix).append(apiLog.logHead()).append(logHeadSuffix);

        //所需占位符列表
        List<String> placeholderList = LogHandlerUtil.getLogParams(messageFormat);
        String message = LogHandlerUtil.removePlaceholder(messageFormat, placeholderList);
        builder.append(message);
        //占位符对应的数据列表
        return LogHandlerUtil.placeholderData(placeholderList, apiParams, requestParams, extendDataMap);
    }

    /**
     * 是否打印执行时间
     * @param logHandlerParams      处理所需的相关参数列表
     * @param placeholderData       打印数据列表
     * @param configValue           配置文件中保存的值
     */
    private void isPrintExecutionTime(LogHandlerParams logHandlerParams, List<Object> placeholderData, boolean configValue){
        ApiLog apiLog = logHandlerParams.getApiLog();
        long executeTime = logHandlerParams.getExecuteTime();

        BooleanEnum isExecutionTime = apiLog.isExecutionTime();
        Boolean isPrintTime = isExecutionTime.equals(BooleanEnum.NULL) ? configValue : isExecutionTime.getValue();

        //是否打印执行时间
        if(Boolean.TRUE.equals(isPrintTime)){
            builder.append(EXECUTE_TIME_MESSAGE_FORMAT);
            placeholderData.add(executeTime);
        }
    }

    /**
     * 是否异常打印
     * @param logHandlerParams      处理所需的相关参数列表
     * @param placeholderData       打印数据列表
     * @param configValue           配置文件中保存的值
     */
    private void isPrintStackMessage(LogHandlerParams logHandlerParams, List<Object> placeholderData, boolean configValue){
        ApiLog apiLog = logHandlerParams.getApiLog();
        Throwable e = logHandlerParams.getThrowable();

        BooleanEnum isStackMessage = apiLog.isStackMessage();
        Boolean isPrintTime = isStackMessage.equals(BooleanEnum.NULL) ? configValue : isStackMessage.getValue();

        //是否打印执行时间
        if(Boolean.TRUE.equals(isPrintTime)){
            builder.append(THROW_MESSAGE_FORMAT);
            placeholderData.add(e);
        }
    }
}
