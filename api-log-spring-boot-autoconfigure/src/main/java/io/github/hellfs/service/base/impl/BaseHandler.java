package io.github.hellfs.service.base.impl;

import io.github.hellfs.service.extendhandler.param.ExtendHandlerParams;
import io.github.hellfs.service.log.param.LogHandlerParams;
import io.github.hellfs.exception.ExtendHandlerException;
import io.github.hellfs.exception.LogHandlerException;
import io.github.hellfs.service.base.BaseAfter;
import io.github.hellfs.service.base.BaseAfterReturning;
import io.github.hellfs.service.base.BaseAfterThrowing;
import io.github.hellfs.service.base.BaseBefore;
import io.github.hellfs.service.base.param.BaseParam;
import io.github.hellfs.service.extendhandler.AfterExtendHandler;
import io.github.hellfs.service.extendhandler.AfterReturningExtendHandler;
import io.github.hellfs.service.extendhandler.AfterThrowingExtendHandler;
import io.github.hellfs.service.extendhandler.BeforeExtendHandler;
import io.github.hellfs.service.log.LogHandler;
import io.github.hellfs.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 根处理类
 * @author hellfs
 * @date 2023-09-07
 */
public class BaseHandler implements BaseBefore, BaseAfterReturning, BaseAfterThrowing, BaseAfter {

    @Autowired
    private LogHandler logHandler;
    @Autowired
    private BeanUtil beanUtil;

    @Override
    public void before(BaseParam baseParam) {
        //日志打印类
        try {
            LogHandlerParams logHandlerParams = new LogHandlerParams();
            BeanUtils.copyProperties(baseParam,logHandlerParams);
            logHandler.before(logHandlerParams);
        } catch (Exception e) {
            throw new LogHandlerException(e);
        }

        //扩展功能处理类
        try {
            BeforeExtendHandler bean = beanUtil.getBean(BeforeExtendHandler.class);
            if(bean != null){
                ExtendHandlerParams extendHandlerParams = new ExtendHandlerParams();
                BeanUtils.copyProperties(baseParam,extendHandlerParams);
                bean.before(extendHandlerParams);
            }
        } catch (BeansException e) {
            throw new ExtendHandlerException(e);
        }
    }

    @Override
    public void afterReturning(BaseParam baseParam) {
        //日志打印类
        try {
            LogHandlerParams logHandlerParams = new LogHandlerParams();
            BeanUtils.copyProperties(baseParam,logHandlerParams);
            logHandler.afterReturning(logHandlerParams);
        } catch (BeansException e) {
            throw new LogHandlerException(e);
        }

        //扩展功能处理类
        try {
            AfterReturningExtendHandler bean = beanUtil.getBean(AfterReturningExtendHandler.class);
            if(bean != null){
                ExtendHandlerParams extendHandlerParams = new ExtendHandlerParams();
                BeanUtils.copyProperties(baseParam,extendHandlerParams);
                bean.afterReturning(extendHandlerParams);
            }
        } catch (BeansException e) {
            throw new ExtendHandlerException(e);
        }
    }

    @Override
    public void afterThrowing(BaseParam baseParam) {
        //日志打印类
        try {
            LogHandlerParams logHandlerParams = new LogHandlerParams();
            BeanUtils.copyProperties(baseParam,logHandlerParams);
            logHandler.afterThrowing(logHandlerParams);
        } catch (BeansException e) {
            throw new LogHandlerException(e);
        }

        //扩展功能处理类
        try {
            AfterThrowingExtendHandler bean = beanUtil.getBean(AfterThrowingExtendHandler.class);
            if(bean != null){
                ExtendHandlerParams extendHandlerParams = new ExtendHandlerParams();
                BeanUtils.copyProperties(baseParam,extendHandlerParams);
                bean.afterThrowing(extendHandlerParams);
            }
        } catch (BeansException e) {
            throw new ExtendHandlerException(e);
        }
    }

    @Override
    public void after(BaseParam baseParam) {
        //日志打印类
        try {
            LogHandlerParams logHandlerParams = new LogHandlerParams();
            BeanUtils.copyProperties(baseParam,logHandlerParams);
            logHandler.after(logHandlerParams);
        } catch (BeansException e) {
            throw new LogHandlerException(e);
        }

        //扩展功能处理类
        try {
            AfterExtendHandler bean = beanUtil.getBean(AfterExtendHandler.class);
            if(bean != null){
                ExtendHandlerParams extendHandlerParams = new ExtendHandlerParams();
                BeanUtils.copyProperties(baseParam,extendHandlerParams);
                bean.after(extendHandlerParams);
            }
        } catch (BeansException e) {
            throw new ExtendHandlerException(e);
        }
    }
}
