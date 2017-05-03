/*
 * Project Name : jbp-framework <br>
 * File Name : LoggerPostProcesor.java <br>
 * Package Name : com.asdc.jbp.framework.log <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.log;

import com.asdc.jbp.framework.utils.ReflectionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * ClassName : LoggerPostProcesor <br>
 * Description : inject log into every {@link Log} annotated field by spring <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Component
public final class LoggerPostProcesor implements BeanPostProcessor {

    /**
     * @see BeanPostProcessor#postProcessAfterInitialization(Object, String)
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * @see BeanPostProcessor#postProcessBeforeInitialization(Object, String)
     */
    @Override
    public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(), field -> {
            if (field.getAnnotation(Log.class) != null) {
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, bean, LoggerFactory.getLogger(bean.getClass()));
            }
        });
        return bean;
    }
}
