package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.service.RiskService;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@SuppressWarnings("unused")
@Controller("RiskController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RiskController extends ControllerHelper {

    @Resource
    private RiskService service;

    public void queryProjectRisk() {
        workDTO.setResult(service.getProjectRist());
    }

    public void querySalesRisk() {
        workDTO.setResult(service.getSalesRisk(workDTO.get("year")));
    }

    public void queryMgmtRisk() {
        workDTO.setResult(service.getMgmtRisk(workDTO.get("year")));
    }
}
