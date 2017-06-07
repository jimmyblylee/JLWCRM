package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.entity.RecPay;
import com.asdc.jbp.crm.service.RecPayService;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.utils.ProxyStripper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.beans.IntrospectionException;

@SuppressWarnings("unused")
@Controller("RecPayController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RecPayController extends ControllerHelper {

    @Resource
    private RecPayService service;

    public void query() throws IntrospectionException {
        RecPay condition = workDTO.convertJsonToBeanByKey("pageQuery", RecPay.class);
        workDTO.setResult(ProxyStripper.cleanFromProxies(service.query(condition, workDTO.getStart(), workDTO.getLimit())));
        workDTO.setTotle(service.getCount(condition));
    }

    public void create() {
        service.create(workDTO.convertJsonToBeanByKey("entity", RecPay.class));
    }

    public void update() {
        service.update(workDTO.convertJsonToBeanByKey("entity", RecPay.class));
    }

    public void remove() {
        service.remove(workDTO.getInteger("id"));
    }
}
