package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.entity.Activity;
import com.asdc.jbp.crm.service.ActivityService;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.utils.ProxyStripper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.beans.IntrospectionException;

@SuppressWarnings("unused")
@Controller("ActivityController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ActivityController extends ControllerHelper {

    @Resource
    private ActivityService service;

    public void query() throws IntrospectionException {
        Activity condition = new Activity();
        condition.setDate(workDTO.get("date"));
        condition.setContent(workDTO.get("content"));
        workDTO.setResult(ProxyStripper.cleanFromProxies(service.query(condition, workDTO.getStart(), workDTO.getLimit())));
    }

    public void create() {
        service.create(workDTO.convertJsonToBeanByKey("entity", Activity.class));
    }

    public void update() {
        service.update(workDTO.convertJsonToBeanByKey("entity", Activity.class));
    }

    public void remove() {
        service.remove(workDTO.getInteger("id"));
    }
}
