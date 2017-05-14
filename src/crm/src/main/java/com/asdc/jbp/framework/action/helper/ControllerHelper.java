/**
 * Project Name : jbp-framework <br>
 * File Name : ControllerHelper.java <br>
 * Package Name : com.asdc.jbp.framework.action.helper <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.action.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.asdc.jbp.framework.context.bind.ServletRequestAware;
import com.asdc.jbp.framework.context.bind.ServletResponseAware;
import com.asdc.jbp.framework.dto.SessionDTO;
import com.asdc.jbp.framework.dto.WorkDTO;
import com.asdc.jbp.framework.dto.bind.SessionDTOAware;
import com.asdc.jbp.framework.dto.bind.WorkDTOAware;
import com.asdc.jbp.framework.log.Log;

/**
 * ClassName : ControllerHelper <br>
 * Description : 这是Controller的抽象类，在此对base context 进行了封装 ，不需要去实现 *Aware;也不需使用ActionContext.get*()<br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public abstract class ControllerHelper implements WorkDTOAware, SessionDTOAware, ServletRequestAware, ServletResponseAware {

    protected SessionDTO sessionDTO;
    protected WorkDTO workDTO;
    protected HttpServletRequest servletRequest;
    protected HttpServletResponse servletResponse;
    @Log
    protected Logger log;

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.servletResponse = response;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.servletRequest = request;
    }

    @Override
    public void setSessionDTO(SessionDTO sessionDTO) {
        this.sessionDTO = sessionDTO;
    }

    @Override
    public void setWorkDTO(WorkDTO workDTO) {
        this.workDTO = workDTO;
    }

}
