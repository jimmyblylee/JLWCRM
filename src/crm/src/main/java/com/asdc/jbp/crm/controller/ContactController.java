package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.entity.Contact;
import com.asdc.jbp.crm.service.ContactService;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.utils.ProxyStripper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.beans.IntrospectionException;

@SuppressWarnings("unused")
@Controller("ContactController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ContactController extends ControllerHelper {

    @Resource
    private ContactService service;

    public void query() throws IntrospectionException {
        Contact condition = workDTO.convertJsonToBeanByKey("pageQuery", Contact.class);
        workDTO.setResult(ProxyStripper.cleanFromProxies(service.query(condition, workDTO.getStart(), workDTO.getLimit())));
        workDTO.setTotle(service.getCount(condition));
    }

    public void create() {
        Contact entity = workDTO.convertJsonToBeanByKey("entity", Contact.class);
        service.create(entity);
    }

    public void update() {
        Contact entity = workDTO.convertJsonToBeanByKey("entity", Contact.class);
        service.update(entity);
    }

    public void remove() {
        service.remove(workDTO.getInteger("id"));

    }
}
