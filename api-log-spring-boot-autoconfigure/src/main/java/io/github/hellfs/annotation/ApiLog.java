package io.github.hellfs.annotation;

import io.github.hellfs.annotation.extenddata.ExtendDataMethod;
import io.github.hellfs.annotation.extenddata.ExtendDataValue;

import java.lang.annotation.*;

/**
 * 日志打印注解
 * @author hellfs
 * @date 2023-07-16
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiLog {

    /**
     * 日志头信息
     * 默认日志格式：log.info("[logHead]...")
     */
    String logHead();

    /**
     * 日志头信息的前置拼接符，也可以配置统一实现，注解优先
     */
    //String logHeadPrefix() default "[";
    String logHeadPrefix() default "";

    /**
     * 日志头信息的后置拼接符，也可以配置统一实现，注解优先
     */
    //String logHeadSuffix() default "]";
    String logHeadSuffix() default "";

    /**
     * 接口执行前置日志打印  ${?}：占位符
     * 特殊定义占位符：
     *      reqParams：请求接口完整参数列表
     *      apiParams：接口接收参数列表
     *  自定义占位符，默认从请求参数中获取，获取不到，则 获取 {@link ExtendDataValue} 或 {@link ExtendDataMethod}；反之：未知
     *  可以配置统一实现，注解优先
     */
    //String beforeMessageFormat() default "";
    String beforeMessageFormat() default "处理开始，参数列表:${reqParams}";

    /**
     * 接口执行后置(执行成功时触发)日志打印  ${}：占位符
     * 特殊定义占位符：
     *      reqParams：请求接口完整参数列表
     *      apiParams：接口接收参数列表
     *  自定义占位符，默认从请求参数中获取，获取不到，则 获取 {@link ExtendDataValue} 或 {@link ExtendDataMethod}；反之：未知
     * 可以配置统一实现，注解优先
     */
    //String afterMessageFormat() default "处理成功，参数列表:${reqParams}";
    String afterReturningMessageFormat() default "";

    /**
     * 接口执行异常日志打印  ${}：占位符
     * 特殊定义占位符：
     *      reqParams：请求接口完整参数列表
     *      apiParams：接口接收参数列表
     *  自定义占位符，默认从请求参数中获取，获取不到，则 获取 {@link ExtendDataValue} 或 {@link ExtendDataMethod}；反之：未知
     * 可以配置统一实现，注解优先
     * 注意：默认追加异常信息打印   案例：结束，参数列表:${}，失败原因:{}    如不需要，则 {@link ApiLog#isStackMessage()} 控制
     */
    //String throwMessageFormat() default "处理异常，参数列表:${reqParams}";
    String afterThrowingMessageFormat() default "";

    /**
     * 无论方法是否正常执行，都会执行该方法  ${}：占位符
     * 特殊定义占位符：
     *      reqParams：请求接口完整参数列表
     *      apiParams：接口接收参数列表
     *  自定义占位符，默认从请求参数中获取，获取不到，则 获取 {@link ExtendDataValue} 或 {@link ExtendDataMethod}；反之：未知
     * 可以配置统一实现，注解优先
     * 注意：默认追加异常信息打印   案例：结束，参数列表:${}，失败原因:{}    如不需要，则 {@link ApiLog#isStackMessage()} 控制
     */
    String afterMessageFormat() default "";

    /**
     * 是否打印执行时间
     * 可以配置统一实现，注解优先
     */
    boolean executionTime() default true;

    /**
     * 是否追加堆栈异常信息打印
     * 可以配置统一实现，注解优先
     */
    boolean isStackMessage() default true;
}
