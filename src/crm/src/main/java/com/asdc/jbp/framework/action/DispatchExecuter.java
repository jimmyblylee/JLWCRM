/**
 * Project Name : jbp-framework <br>
 * File Name : DispatchExecuter.java <br>
 * Package Name : com.asdc.jbp.framework.action <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ContextLoader;

import com.asdc.jbp.framework.context.ActionContext;
import com.asdc.jbp.framework.context.ApplicationMap;
import com.asdc.jbp.framework.context.ParameterMap;
import com.asdc.jbp.framework.context.RequestMap;
import com.asdc.jbp.framework.context.SessionMap;
import com.asdc.jbp.framework.context.bind.ApplicationAware;
import com.asdc.jbp.framework.context.bind.ParameterAware;
import com.asdc.jbp.framework.context.bind.RequestAware;
import com.asdc.jbp.framework.context.bind.ServletRequestAware;
import com.asdc.jbp.framework.context.bind.ServletResponseAware;
import com.asdc.jbp.framework.context.bind.SessionAware;
import com.asdc.jbp.framework.dto.ApplicationDTO;
import com.asdc.jbp.framework.dto.ExtendInfo;
import com.asdc.jbp.framework.dto.Info;
import com.asdc.jbp.framework.dto.SessionDTO;
import com.asdc.jbp.framework.dto.WorkDTO;
import com.asdc.jbp.framework.dto.bind.ApplicationDTOAware;
import com.asdc.jbp.framework.dto.bind.SessionDTOAware;
import com.asdc.jbp.framework.dto.bind.WorkDTOAware;
import com.asdc.jbp.framework.exception.ErrLevel;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.message.Messages;
import com.asdc.jbp.framework.utils.StringUtils;

/**
 * ClassName : DispatchExecuter <br>
 * Description : Controller转发控制器 <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
class DispatchExecuter {

    private static ThreadLocal<DispatchExecuter> instance = new ThreadLocal<DispatchExecuter>();

    /**
     * Description : 获取Controller/Method 参数，转发到相应的控制器<br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param request
     * @param response
     * @throws Exception
     * @throws ServiceException
     */
    public void serviceAction(HttpServletRequest request, HttpServletResponse response) throws Exception, ServiceException {
        WorkDTO dto = ActionContext.getContext().getWorkDTO();

        String controllerName = dto.getController();
        String methodName = dto.getMethod();
        Logger dispatcherLog = LoggerFactory.getLogger(getClass());
        dispatcherLog.trace("request info : controller name is {} and method name is {}", controllerName, methodName);
        dto.removeController();
        dto.removeMethod();

        Object bean = null;
        Method method = null;

        try {
            if (StringUtils.isEmpty(controllerName)) {
                throw dto.setIssue(new ServiceException("ERR_FRAMEWORK_001", ErrLevel.WARN, Messages.getMsg("ERR_FRAMEWORK_CONTROLLERID_NULL")));
            }
            bean = ContextLoader.getCurrentWebApplicationContext().getBean(controllerName);
            if (bean == null) {
                throw dto.setIssue(new ServiceException("ERR_FRAMEWORK_001", ErrLevel.WARN, Messages.getMsg("ERR_FRAMEWORK_BEAN_NULL"), controllerName));
            }
            if (bean.getClass().getAnnotation(Controller.class) == null) {
                throw dto.setIssue(new ServiceException("ERR_FRAMEWORK_001", ErrLevel.WARN, Messages.getMsg("ERR_FRAMEWORK_NONE_CONTROLLER"), controllerName));
            }
            if (AopUtils.isAopProxy(bean)) {
                throw dto.setIssue(new ServiceException("ERR_FRAMEWORK_001", ErrLevel.WARN, Messages.getMsg("ERR_FRAMEWORK_PROXY_CONTROLLER"), controllerName));
            }
        } catch (NoSuchBeanDefinitionException e) {
            throw dto.setIssue(new ServiceException("ERR_FRAMEWORK_001", ErrLevel.WARN, Messages.getMsg("ERR_FRAMEWORK_CONTROLLER_NULL"), e, controllerName));
        } catch (BeansException | IllegalArgumentException e) {
            throw dto.setIssue(new ServiceException("ERR_FRAMEWORK_001", ErrLevel.ERR, Messages.getMsg("ERR_FRAMEWORK_CONTROLLER_CREATE"), e, controllerName));
        }

        try {
            if (StringUtils.isEmpty(methodName)) {
                throw dto.setIssue(new ServiceException("ERR_FRAMEWORK_001", Messages.getMsg("ERR_FRAMEWORK_METHODID_NULL")));
            }
            method = bean.getClass().getMethod(methodName, new Class<?>[0]);
        } catch (SecurityException | NullPointerException | NoSuchMethodException e) {
            throw dto.setIssue(new ServiceException("ERR_FRAMEWORK_001", ErrLevel.WARN, Messages.getMsg("ERR_FRAMEWORK_METHOD_NULL"), e, methodName,
                    bean.getClass().getName()));
        }
        // bind data from method or bean definition by @ExtendInfo
        if (bean.getClass().getAnnotation(ExtendInfo.class) != null) {
            Info[] infos = bean.getClass().getAnnotation(ExtendInfo.class).infos();
            for (Info info : infos) {
                dto.put(info.key(), info.value());
            }
        }
        if (method.getAnnotation(ExtendInfo.class) != null) {
            Info[] infos = method.getAnnotation(ExtendInfo.class).infos();
            for (Info info : infos) {
                dto.put(info.key(), info.value());
            }
        }
        // bind data to target bean
        bindData(bean, request, response);

        Logger log = LoggerFactory.getLogger(bean.getClass());
        log.debug(method.getName() + " start");
        try {
            method.invoke(bean, new Object[0]);
        } catch (InvocationTargetException e) {
            Throwable realCause = e.getCause();
            if (realCause instanceof ServiceException) {
                ServiceException se = (ServiceException) realCause;
                dto.setIssue(se);
                if (ErrLevel.ERR.equals(se.getErrLevel())) {
                    throw se;
                } else {
                    log.warn(se.getMessage());
                }
            } else if (realCause instanceof AccessDeniedException) {
                dto.setWarn("ERR_SECURITY_001", Messages.getMsg("ERR_ACCESS_DENIED"));
                log.warn(Messages.getMsg("ERR_ACCESS_DENIED"));
            } else {
                throw dto.setIssue(new ServiceException("ERR_UNKNOWN_001", Messages.getMsg("ERR_CONTROLLER_BIZ_UNKNOWN_FAILURE"), realCause,
                        bean.getClass().getName(), method.getName()));
            }
        } catch (Exception e) {
            throw new ServiceException("ERR_UNKNOWN_001", Messages.getMsg("ERR_CONTROLLER_INVOKE_FAILURE"), e, bean.getClass().getName(), method.getName());
        } finally {
            log.debug(method.getName() + " end");
        }
    }

    /**
     * Description : 封装request、response请求 <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param bean
     * @param request
     * @param response
     */
    private void bindData(Object bean, HttpServletRequest request, HttpServletResponse response) {
        if (bean instanceof ApplicationAware) {
            ApplicationAware aa = (ApplicationAware) bean;
            aa.setApplication(ActionContext.getContext().getApplication());
        }

        if (bean instanceof ParameterAware) {
            ParameterAware pa = (ParameterAware) bean;
            pa.setParameters(ActionContext.getContext().getParameters());
        }

        if (bean instanceof RequestAware) {
            RequestAware ra = (RequestAware) bean;
            ra.setRequest(ActionContext.getContext().getRequest());
        }

        if (bean instanceof SessionAware) {
            SessionAware sa = (SessionAware) bean;
            sa.setSession(ActionContext.getContext().getSession());
        }

        if (bean instanceof ApplicationDTOAware) {
            ApplicationDTOAware aa = (ApplicationDTOAware) bean;
            aa.setApplicationDTO(ActionContext.getContext().getApplicationDTO());
        }

        if (bean instanceof SessionDTOAware) {
            SessionDTOAware sa = (SessionDTOAware) bean;
            sa.setSessionDTO(ActionContext.getContext().getSessionDTO());
        }

        if (bean instanceof WorkDTOAware) {
            WorkDTOAware wa = (WorkDTOAware) bean;
            wa.setWorkDTO(ActionContext.getContext().getWorkDTO());
        }

        if (bean instanceof ServletRequestAware) {
            ServletRequestAware sra = (ServletRequestAware) bean;
            sra.setServletRequest(request);
        }

        if (bean instanceof ServletResponseAware) {
            ServletResponseAware sra = (ServletResponseAware) bean;
            sra.setServletResponse(response);
        }
    }

    /**
     * Description : 创建操作的Context <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param request
     * @param response
     * @param context
     * @param springRequestMap
     * @return
     * @throws ServiceException
     */
    public ActionContext createActionContext(HttpServletRequest request, HttpServletResponse response, ServletContext context,
            Map<String, Object> springRequestMap) throws ServiceException {
        ActionContext extraContext = new ActionContext();
        // orginal data
        ApplicationMap applicationMap = new ApplicationMap(context);
        ParameterMap parameterMap = new ParameterMap(request);
        RequestMap requestMap = new RequestMap(request);
        SessionMap sessionMap = new SessionMap(request);

        // app data binding
        ApplicationDTO applicationDTO = new ApplicationDTO(applicationMap);
        SessionDTO sessionDTO = new SessionDTO(sessionMap);
        WorkDTO workDTO = new WorkDTO(springRequestMap);

        extraContext.setApplication(applicationMap);
        extraContext.setParameters(parameterMap);
        extraContext.setRequst(requestMap);
        extraContext.setSession(sessionMap);

        extraContext.setApplicationDTO(applicationDTO);
        extraContext.setSessionDTO(sessionDTO);
        extraContext.setWorkDTO(workDTO);

        extraContext.setServletRequest(request);
        extraContext.setServletResponse(response);

        extraContext.setLocale(request.getLocale());

        return extraContext;
    }

    /**
     * Description : 获取当前线程中的实例 <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return
     */
    public static DispatchExecuter getInstance() {
        return instance.get();
    }

    /**
     * Description : 向当前线程中设置实例 <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param instance
     */
    public static void setInstance(DispatchExecuter instance) {
        DispatchExecuter.instance.set(instance);
    }
}
