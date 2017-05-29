package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.entity.Sales;
import com.asdc.jbp.crm.service.SalesService;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.utils.ProxyStripper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.beans.IntrospectionException;

@SuppressWarnings("unused")
@Controller("SalesController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SalesController extends ControllerHelper {

    private SalesService service;

    public void query() throws IntrospectionException {

        Sales condition = new Sales();
        condition.setName(workDTO.get("name"));
        workDTO.setResult(ProxyStripper.cleanFromProxies(service.query(condition, workDTO.getStart(), workDTO.getLimit())));
    }

    public void create() {
        service.create(workDTO.convertJsonToBeanByKey("entity", Sales.class));
    }

    public void update() {
        service.update(workDTO.convertJsonToBeanByKey("entity", Sales.class));
    }

    public void remove() {
        service.remove(workDTO.getInteger("id"));
    }

}
