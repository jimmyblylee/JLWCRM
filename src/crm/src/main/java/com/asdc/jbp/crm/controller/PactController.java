package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.entity.Customer;
import com.asdc.jbp.crm.entity.Pact;
import com.asdc.jbp.crm.entity.Sales;
import com.asdc.jbp.crm.service.CustomerService;
import com.asdc.jbp.crm.service.PactService;
import com.asdc.jbp.crm.service.SalesService;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.utils.ProxyStripper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.util.List;

@SuppressWarnings("unused")
@Controller("PactController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PactController extends ControllerHelper {

    @Resource
    private PactService service;

    @Resource
    private CustomerService customerService;

    @Resource
    private SalesService salesService;

    public void query() throws IntrospectionException {
        Pact condition = workDTO.convertJsonToBeanByKey("pageQuery", Pact.class);
        if(!ObjectUtils.isEmpty(condition.getCustomer())){
            Customer customer = condition.getCustomer();
            List<Customer> customerList = customerService.query(customer,0,1);
            condition.setCustomer(customerList.get(0));
        }

        if(!ObjectUtils.isEmpty(condition.getSales())){
            Sales sales = condition.getSales();
            List<Sales> salesList = salesService.query(sales,0,1);
            condition.setSales(salesList.get(0));
        }
        workDTO.setResult(ProxyStripper.cleanFromProxies(service.query(condition, workDTO.getStart(), workDTO.getLimit())));
        workDTO.setTotle(service.getCount(condition));
    }

    public void create() {
        Pact entity = workDTO.convertJsonToBeanByKey("entity", Pact.class);
        if(entity.getCustomer() != null){
            Customer customer = entity.getCustomer();
            List<Customer> customerList = customerService.query(customer,0,1);
            entity.setCustomer(customerList.get(0));
        }

        if(entity.getSales() != null){
            Sales sales = entity.getSales();
            List<Sales> salesList = salesService.query(sales,0,1);
            entity.setSales(salesList.get(0));
        }
        service.create(entity);
    }

    public void update() {
        Pact entity = workDTO.convertJsonToBeanByKey("entity", Pact.class);
        service.update(entity);
    }

    public void remove() {
        service.remove(workDTO.getInteger("id"));
    }
}
