package io.github.hellfs.service.base.impl;

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
import io.github.hellfs.service.extendhandler.param.ExtendHandlerParams;
import io.github.hellfs.service.log.LogHandler;
import io.github.hellfs.service.log.param.LogHandlerParams;
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
    @Autowired(required = false)
    private BeforeExtendHandler beforeExtendHandler;
    @Autowired(required = false)
    private AfterReturningExtendHandler afterReturningExtendHandler;
    @Autowired(required = false)
    private AfterThrowingExtendHandler afterThrowingExtendHandler;
    @Autowired(required = false)
    private AfterExtendHandler afterExtendHandler;

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
            if(beforeExtendHandler != null){
                ExtendHandlerParams extendHandlerParams = new ExtendHandlerParams();
                BeanUtils.copyProperties(baseParam,extendHandlerParams);
                beforeExtendHandler.before(extendHandlerParams);
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
            if(afterReturningExtendHandler != null){
                ExtendHandlerParams extendHandlerParams = new ExtendHandlerParams();
                BeanUtils.copyProperties(baseParam,extendHandlerParams);
                afterReturningExtendHandler.afterReturning(extendHandlerParams);
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
            if(afterThrowingExtendHandler != null){
                ExtendHandlerParams extendHandlerParams = new ExtendHandlerParams();
                BeanUtils.copyProperties(baseParam,extendHandlerParams);
                afterThrowingExtendHandler.afterThrowing(extendHandlerParams);
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
            if(afterExtendHandler != null){
                ExtendHandlerParams extendHandlerParams = new ExtendHandlerParams();
                BeanUtils.copyProperties(baseParam,extendHandlerParams);
                afterExtendHandler.after(extendHandlerParams);
            }
        } catch (BeansException e) {
            throw new ExtendHandlerException(e);
        }
    }
}
