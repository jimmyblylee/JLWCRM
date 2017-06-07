package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.entity.*;
import com.asdc.jbp.crm.service.ClewService;
import com.asdc.jbp.crm.service.ContactService;
import com.asdc.jbp.crm.service.CustomerService;
import com.asdc.jbp.crm.service.SalesService;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.utils.ProxyStripper;
import com.asdc.jbp.framework.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.beans.IntrospectionException;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unused")
@Controller("ClewController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClewController extends ControllerHelper {

    @Resource
    private ClewService service;

    @Resource
    private CustomerService customerService;

    @Resource
    private ContactService contactService;

    @Resource
    private SalesService salesService;

    public void query() throws IntrospectionException {
        Clew condition = workDTO.convertJsonToBeanByKey("pageQuery", Clew.class);
        workDTO.setResult(ProxyStripper.cleanFromProxies(service.query(condition, workDTO.getStart(), workDTO.getLimit())));
        workDTO.setTotle(service.getCount(condition));
    }

    public void create() {
        Clew entity = workDTO.convertJsonToBeanByKey("entity", Clew.class);
        if(entity.getCustomer() != null){
            Customer customer = entity.getCustomer();
            List<Customer> customerList = customerService.query(customer,0,1);
            if(customerList.size()>0){
                entity.setCustomer(customerList.get(0));
            }
        }

        if(entity.getContact() != null){
            Contact contact = entity.getContact();
            List<Contact> contactList = contactService.query(contact,0,1);
            if(contactList.size()>0){
                entity.setContact(contactList.get(0));
            }
        }

        if(entity.getSales() != null){
            Sales sales = entity.getSales();
            List<Sales> salesList = salesService.query(sales,0,1);
            if(salesList.size()>0){
                entity.setSales(salesList.get(0));
            }
        }
        service.create(entity);
    }

    public void update() {
        Clew entity = workDTO.convertJsonToBeanByKey("entity", Clew.class);
        service.update(entity);
    }

    public void remove() {
        service.remove(workDTO.getInteger("id"));
    }
}
