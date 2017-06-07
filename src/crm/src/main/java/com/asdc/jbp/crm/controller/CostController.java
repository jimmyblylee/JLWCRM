package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.entity.Cost;
import com.asdc.jbp.crm.service.CostService;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.utils.ProxyStripper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.beans.IntrospectionException;

@SuppressWarnings("unused")
@Controller("CostController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CostController extends ControllerHelper {

    @Resource
    private CostService service;

    public void query() throws IntrospectionException {
        Cost condition = workDTO.convertJsonToBeanByKey("pageQuery", Cost.class);
        workDTO.setResult(ProxyStripper.cleanFromProxies(service.query(condition, workDTO.getStart(), workDTO.getLimit())));
        workDTO.setTotle(service.getCount(condition));
    }

    public void create() {
        service.create(workDTO.convertJsonToBeanByKey("entity", Cost.class));
    }

    public void update() {
        service.update(workDTO.convertJsonToBeanByKey("entity", Cost.class));
    }

    public void remove() {
        service.remove(workDTO.getInteger("id"));
    }
}
