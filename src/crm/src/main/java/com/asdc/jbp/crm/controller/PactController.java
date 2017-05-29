package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.entity.Pact;
import com.asdc.jbp.crm.service.PactService;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.utils.ProxyStripper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.beans.IntrospectionException;

@SuppressWarnings("unused")
@Controller("PactController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PactController extends ControllerHelper {

    @Resource
    private PactService service;

    public void query() throws IntrospectionException {
        Pact condition = workDTO.convertJsonToBeanByKey("pageQuery", Pact.class);

        workDTO.setResult(ProxyStripper.cleanFromProxies(service.query(condition, workDTO.getStart(), workDTO.getLimit())));
        workDTO.setTotle(service.getCount(condition));
    }

    public void create() {
        Pact entity = workDTO.convertJsonToBeanByKey("entity", Pact.class);
        service.create(entity);
    }

    public void update() {
        Pact entity = workDTO.convertJsonToBeanByKey("entity", Pact.class);
        service.update(entity);
    }

    public void delete() {
        service.remove(workDTO.getInteger("id"));
    }
}
