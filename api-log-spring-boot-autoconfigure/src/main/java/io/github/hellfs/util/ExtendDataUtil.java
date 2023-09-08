package io.github.hellfs.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import io.github.hellfs.annotation.extenddata.ExtendDataMethod;
import io.github.hellfs.annotation.extenddata.ExtendDataValue;
import io.github.hellfs.common.enums.ExTendDataMethodModel;
import io.github.hellfs.exception.ExtendDataException;
import io.github.hellfs.exception.errorcode.impl.ExtendDataErrorCodeEnum;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 获取扩展数据列表
 * @author hellfs
 * @date 2023-09-07
 */
public class ExtendDataUtil  {

    @Autowired
    private BeanUtil beanUtil;

    /**
     * 获取自定义(扩展)参数
     * @param joinPoint 切入点对象
     * @return  Map<String,String>
     */
    public Map<String,String> extendData(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        Map<String, String> extendDataValueMap = this.extendDataValue(method);
        Map<String, String> extendDataMethodMap = this.extendDataMethod(method);

        Map<String, String> map = new LinkedHashMap<>();
        map.putAll(extendDataValueMap);
        map.putAll(extendDataMethodMap);
        return map;
    }

    /**
     * 获取常量类型-自定义参数
     * @param method    方法对象
     * @return  Map<String,String>
     */
    private Map<String,String> extendDataValue(Method method){
        Map<String, String> map = new LinkedHashMap<>();
        ExtendDataValue[] extendDataValues = method.getAnnotationsByType(ExtendDataValue.class);
        for (ExtendDataValue extendDataValue : extendDataValues) {
            map.put(extendDataValue.key(),extendDataValue.value());
        }
        return map;
    }

    /**
     * 获取方法类型-自定义参数
     * @param method    方法对象
     * @return  Map<String,String>
     */
    private Map<String,String> extendDataMethod(Method method){
        Map<String, String> map = new LinkedHashMap<>();
        try {
            ExtendDataMethod[] extendDataMethods = method.getAnnotationsByType(ExtendDataMethod.class);
            for (ExtendDataMethod extendDataMethod : extendDataMethods) {
                String key = extendDataMethod.key();
                Class<?> clazz = extendDataMethod.clazz();
                String methodName = extendDataMethod.methodName();
                ExTendDataMethodModel model = extendDataMethod.model();

                Object obj;
                if(ExTendDataMethodModel.CLASS.equals(model)){
                    obj = clazz.newInstance();
                }else{
                    obj = beanUtil.getBean(clazz);
                }

                Method declaredMethod = clazz.getDeclaredMethod(methodName);
                Object value = declaredMethod.invoke(obj);
                map.put(key, JSON.toJSONString(value, JSONWriter.Feature.NullAsDefaultValue));
            }
        } catch (Exception e) {
            throw new ExtendDataException(ExtendDataErrorCodeEnum.GET_EXTEND_DATA_FAILED);
        }
        return map;
    }
}
