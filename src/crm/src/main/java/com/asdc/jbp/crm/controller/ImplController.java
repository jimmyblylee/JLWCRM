package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.entity.Implementer;
import com.asdc.jbp.crm.service.ImplService;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@SuppressWarnings("unused")
@Controller("ImplController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ImplController extends ControllerHelper {
    @Resource
    private ImplService service;

    public void query() {
        Implementer condition = workDTO.convertJsonToBeanByKey("pageQuery", Implementer.class);
        workDTO.setResult(service.query(condition, workDTO.getStart(), workDTO.getLimit()));
        workDTO.setTotle(service.getCount(condition));
    }

    public void create() {
        service.create(workDTO.convertJsonToBeanByKey("entity", Implementer.class));
    }

    public void update() {
        service.update(workDTO.convertJsonToBeanByKey("entity", Implementer.class));
    }

    public void remove() {
        service.remove(workDTO.getInteger("id"));
    }
}
