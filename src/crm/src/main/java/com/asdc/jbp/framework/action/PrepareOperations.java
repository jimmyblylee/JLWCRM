/**
 * Project Name : jbp-framework <br>
 * File Name : PrepareOperations.java <br>
 * Package Name : com.asdc.jbp.framework.action <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asdc.jbp.framework.context.ActionContext;
import com.asdc.jbp.framework.exception.ServiceException;

/**
 * ClassName : PrepareOperations <br>
 * Description : prepare operations <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
class PrepareOperations {

    private DispatchExecuter dispatchExecuter;
    private ServletContext servletContext;

    /**
     * 为PrepareOperations创建一个新的实例
     * 
     * @param dispatchExecuter
     * @param servletContext
     */
    public PrepareOperations(DispatchExecuter dispatchExecuter, ServletContext servletContext) {
        this.dispatchExecuter = dispatchExecuter;
        this.servletContext = servletContext;
    }

    /**
     * Description : 创建 context <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param request
     * @param response
     * @param workDTO
     * @return
     * @throws ServiceException
     */
    public ActionContext createActionContext(HttpServletRequest request, HttpServletResponse response, Map<String, Object> workDTO) throws ServiceException {
        ActionContext ctx;
        ActionContext oldContext = ActionContext.getContext();
        if (oldContext != null) {
            /* thread will not shut down sometimes if the target is singleton, this is prepare for this */
            ctx = new ActionContext(new HashMap<String, Object>(oldContext.getContextMap()));
        } else {
            ctx = dispatchExecuter.createActionContext(request, response, servletContext, workDTO);
        }
        ActionContext.setContext(ctx);
        return ctx;
    }

    /**
     * Description : 将dispatchExecuter设置为当前线程<br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     */
    public void assignDispatcherToThread() {
        DispatchExecuter.setInstance(dispatchExecuter);
    }

    /**
     * Description : 设置编码（UTF-8）及区域(zh_CN) <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param request
     * @param response
     */
    public void setEncodingAndLocale(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setLocale(request.getLocale());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Description : 清除当前线程中所有信息 <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     */
    public void cleanup() {
        ActionContext.setContext(null);
        DispatchExecuter.setInstance(null);
    }
}
