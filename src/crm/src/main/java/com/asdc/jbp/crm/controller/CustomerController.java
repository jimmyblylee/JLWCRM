package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.entity.Customer;
import com.asdc.jbp.crm.service.CustomerService;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.utils.ProxyStripper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.beans.IntrospectionException;

@SuppressWarnings("unused")
@Controller("CustomerController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CustomerController extends ControllerHelper {

    @Resource
    private CustomerService service;

    public void query() throws IntrospectionException {
        Customer condition = new Customer();
        condition.setName(workDTO.get("name"));
        workDTO.setResult(ProxyStripper.cleanFromProxies(service.query(condition, workDTO.getStart(), workDTO.getLimit())));

    }

    public void create() {
        Customer entity = workDTO.convertJsonToBeanByKey("entity", Customer.class);
        service.create(entity);
    }

    public void update() {
        Customer entity = workDTO.convertJsonToBeanByKey("entity", Customer.class);
        service.update(entity);
    }

    public void remove() {
        service.remove(workDTO.getInteger("id"));
    }

}
