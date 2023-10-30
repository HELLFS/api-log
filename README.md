# api-log
## 简介

基于`2.1.6.RELEASE`版本的`spring-boot-starter-parent`父依赖开发。

基于`Spring-Aop`实现，主要用于打印日志操作。如果默认实现的日志打印功能无法满足自身项目需求，可以使用自定义日志打印功能来实现多元化需求；如果需要在打印日志时做额外操作，例如：记录日志等，可以实现扩展处理类来完成功能。

**注意：默认已实现`可重复读取InputStream的Request`**



## 配置文件总览

**注意：注解配置优先于全局配置，配置文件中的值为默认值**

```yml
# ApiLog相关配置信息
api-log:
  # 日志头信息
  head:
    # 头信息前置符 {@link ApiLog#logHeadPrefix()} 注解优先
    prefix: '['
    # 头信息后置符 {@link ApiLog#logHeadSuffix()} 注解优先
    suffix: ']'
  # 前置日志打印
  before:
    # 是否开启打印
    enable: true
    # 接口执行前置日志打印格式 {@link ApiLog#beforeMessageFormat()} 注解优先
    message-format: '处理开始，参数列表:${reqParams}'
  # 后置日志打印
  alter-returning:
    # 是否开启打印
    enable: true
    # 是否打印执行时间 {@link ApiLog#isExecutionTime()} 注解优先
    execution-time: true
    # 接口执行后置(执行成功时触发)日志打印格式 {@link ApiLog#afterReturningMessageFormat()} 注解优先
    message-format: '处理成功，参数列表:${reqParams}'
  # 异常日志打印
  after-throwing:
    enable: true
    # 是否打印执行时间 {@link ApiLog#isExecutionTime()} 注解优先
    execution-time: true
    # 堆栈信息是否追加 {@link ApiLog#isStackMessage()} 注解优先
    stack-message: true
  # 最终日志打印，类比 finally 代码块执行时机
  after:
    # 是否开启打印
    enable: false
    # 是否打印执行时间 {@link ApiLog#isExecutionTime()} 注解优先
    execution-time: true
    # 最终日志打印格式  {@link ApiLog#afterMessageFormat()}  注解优先
    message-format: '最终执行，参数列表:${reqParams}'
```

`${...}`为占位符，用于控制打印日志的格式，特殊占位符(固定)有：`${reqParams}`请求接口完整参数列表；`${apiParams}`接口接收参数列表。当然也可以自定义占位符，具体使用请参照 **高级使用**-**自定义参数**



## 使用

1.引入相关依赖

```xml
<dependency>
    <groupId>io.github.hellfs</groupId>
    <artifactId>api-log-spring-boot-starter</artifactId>
    <version>${last.version}</version>
</dependency>
```

2.在需要打印日志的接口上添加注解

```java
@ApiLog(logHead = "案例")
@GetMapping(value = "/demo")
public Object demo(){
    return "demo";
}
```



## 高级使用

### 自定义参数

日志打印时如需对接口日志上打印自定义参数，则需要配合`@ExtendDataValue`和`@ExtendDataMethod`使用

#### @ExtendDataValue

注解介绍：

```java
public @interface ExtendDataValue {

    /**
     * 键，仿照Map形式
     */
    String key();

    /**
     * 值，仿照Map形式
     */
    String value();
}
```

数据类型为字符串，使用注解传入key-value，然后在打印格式语句中添加 `${key}`即可。同时可以添加多个。

案例：以下案例使用注解中的属性体现

```java
@ApiLog(logHead = "案例", beforeMessageFormat = "处理开始，自定义参数列表:{姓名:${name},年龄:${age}},参数列表:${reqParams}")
@ExtendDataValue(key = "name",value = "张三")
@ExtendDataValue(key = "age",value = "10")
@GetMapping(value = "/demo")
public Object demo(){
    return "demo";
}
```

#### @ExtendDataMethod

数据类型为方法返回值，然后在打印格式语句中添加 `${key}`即可。同时可以添加多个。

注解介绍：

```java
public @interface ExtendDataMethod {

    /**
     * 键
     */
    String key();

    /**
     * 类对象
     */
    Class<?> clazz();

    /**
     * 方法名
     */
    String methodName();

    /**
     * 普通类还是bean
     */
    ExTendDataMethodModel model() default ExTendDataMethodModel.CLASS;
}
```

ExTendDataMethodModel

```java
public enum ExTendDataMethodModel {

    /**
     * 以类的形式获取，即不交由Spring容器管理
     */
    CLASS,
    /**
     * 以SpringBean的形式获取
     */
    BEAN
}
```

案例：以下案例使用注解中的属性体现

```java
@ApiLog(logHead = "案例", beforeMessageFormat = "处理开始，自定义参数列表:{姓名:${name},年龄:${age}},参数列表:${reqParams}")
@ExtendDataMethod(key = "name", clazz = xxx.class, methodName = "getName", model = ExTendDataMethodModel.CLASS)
@ExtendDataMethod(key = "age", clazz = xxx.class, methodName = "getAge", model = ExTendDataMethodModel.BEAN)
@GetMapping(value = "/demo")
public Object demo(){
    return "demo";
}
```

### 自定义日志打印类

默认实现的日志打印类不符合自身需求，可以进行定制化。

实现步骤：

- 实现`LogHandler`类，并实现其中方法
- 入口类中添加`@EnableAutoLogHandler`或者在其他类中添加但必须传入实现类的包路径

LogHanlder

```java
public interface LogHandler {

    /**
     * 方法执行前执行
     */
    void before(LogHandlerParams logHandlerParams);

    /**
     * 方法正常执行后执行，如果抛出异常，则不执行
     */
    void afterReturning(LogHandlerParams logHandlerParams);

    /**
     * 方法抛出异常后执行
     */
    void afterThrowing(LogHandlerParams logHandlerParams);

    /**
     * 不管方法是正常执行还是抛出异常，都会执行。类比异常捕获处理的 finally 代码块
     */
    void after(LogHandlerParams logHandlerParams);
}
```

LogHandlerParams

```java
public class LogHandlerParams {

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
```

### 自定义扩展处理类

如需在打印日志以外做额外操作，则可以使用该功能

实现步骤：

- 实现扩展接口，分别为`BeforeExtendHandler`、`AfterReturningExtendHandler`、`AfterThrowingExtendHandler`、`AfterExtendHandler`，其含义分别为前置扩展接口、后置扩展接口、异常扩展接口和最终扩展接口。（理解：Spring AOP提供的通知注解）
- 注入容器方式
  - 入口类添加`@EnableAutoExtendHandler`或者在非入口类中添加且必须传如实现类的包路径
  - 实现类添加注入容器的的注解，例如：@Component、@Service等

