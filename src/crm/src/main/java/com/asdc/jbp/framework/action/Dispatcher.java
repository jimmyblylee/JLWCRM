/*
 * Project Name : jbp-framework <br>
 * File Name : Dispatcher.java <br>
 * Package Name : com.asdc.jbp.framework.action <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;

import com.asdc.jbp.framework.exception.ErrLevel;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.ui.AppConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ClassName : Dispatcher <br>
 * Description : 统一拦截器 <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Controller("action.Dispatcher")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequestMapping("/dispatch")
public class Dispatcher implements ServletContextAware {

    private Logger log = LoggerFactory.getLogger(getClass());

    private ServletContext servletContext;

    /**
     * Description : post 请求 <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param workDTO 请求参数
     * @param servletRequest httpRequest
     * @param servletResponse httpResponse
     */
    @RequestMapping(method = RequestMethod.POST)
    public void post(@RequestParam Map<String, Object> workDTO, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        // prepare context
        DispatchExecuter dispatchExecuter = new DispatchExecuter();
        PrepareOperations prepareOperations = new PrepareOperations(dispatchExecuter, servletContext);
        try {
            prepareOperations.createActionContext(servletRequest, servletResponse, workDTO);
            prepareOperations.assignDispatcherToThread();
            prepareOperations.setEncodingAndLocale(servletRequest, servletResponse);

            // try to dispatch
            dispatchExecuter.serviceAction(servletRequest, servletResponse);
        } catch (Throwable e) {
            if (e instanceof ServiceException) {
                ServiceException se = (ServiceException) e;
                if (ErrLevel.ERR.equals(se.getErrLevel())) {
                    log.error("{}: {}", se.getErrCode(), se.getMessage());
                    log.error("Error Stacking:", e);
                } else {
                    log.warn("{}: {}", se.getErrCode(), se.getMessage());
                }
            } else {
                log.error("Error Stacking:", e);
            }
        } finally {
            String selfCtrlStreamKey = AppConstant.CNS_REQUEST.LET_ME_CTRL_THE_STREAM.toString();
            // build response result in json for ajax call
            if (!workDTO.containsKey(selfCtrlStreamKey) || Boolean.FALSE.equals(workDTO.get(selfCtrlStreamKey))) {
                buildJsonResultASendToClient(workDTO, servletResponse);
            }
            // clean up the thread
            prepareOperations.cleanup();
        }
    }

    /**
     * Description : get 请求，转发为post <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param workDTO 请求参数
     * @param servletRequest httpRequest
     * @param servletResponse httpResponse
     */
    @RequestMapping(method = RequestMethod.GET)
    public void get(@RequestParam Map<String, Object> workDTO, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        post(workDTO, servletRequest, servletResponse);
    }

    /**
     * Description : 构建请求响应结果，以JSON形式写入response <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     */
    private void buildJsonResultASendToClient(Map<String, Object> workDTO, HttpServletResponse response) {
        try {
            response.setCharacterEncoding("UTF-8");
            if (!"text/html;charset=UTF-8".equals(response.getContentType())) {
                response.setContentType("text/json;charset=UTF-8");
            }
            if (workDTO.containsKey("success") && !(Boolean)workDTO.get("success")) {
                response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
            ObjectMapper mapper = new ObjectMapper();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            mapper.setDateFormat(dateFormat);
            mapper.writeValue(response.getOutputStream(), workDTO);
        } catch (Exception e) {
            try {
                response.flushBuffer();
            } catch (IOException e1) {
                log.error(e.getMessage(), e1);
            }
            log.error(e.getMessage(), e);
        }
    }

    /**
     * @see ServletContextAware#setServletContext(ServletContext)
     */
    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
