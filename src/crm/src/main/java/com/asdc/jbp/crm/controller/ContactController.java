package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.entity.Contact;
import com.asdc.jbp.crm.entity.Customer;
import com.asdc.jbp.crm.service.ContactService;
import com.asdc.jbp.crm.service.CustomerService;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.utils.ProxyStripper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.util.List;

@SuppressWarnings("unused")
@Controller("ContactController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ContactController extends ControllerHelper {

    @Resource
    private ContactService service;

    @Resource
    private CustomerService customerService;

    public void query() throws IntrospectionException {
        Contact condition = workDTO.convertJsonToBeanByKey("pageQuery", Contact.class);
        workDTO.setResult(ProxyStripper.cleanFromProxies(service.query(condition, workDTO.getStart(), workDTO.getLimit())));
        workDTO.setTotle(service.getCount(condition));
    }

    public void create() {
        Contact entity = workDTO.convertJsonToBeanByKey("entity", Contact.class);
        if(entity.getCustomer() != null){
            Customer customer = entity.getCustomer();
            List<Customer> customerList = customerService.query(customer,0,1);
            if(customerList.size()>0){
                entity.setCustomer(customerList.get(0));
            }
        }
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
