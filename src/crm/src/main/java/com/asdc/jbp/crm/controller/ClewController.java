package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.entity.*;
import com.asdc.jbp.crm.service.ClewService;
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

@SuppressWarnings("unused")
@Controller("ClewController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClewController extends ControllerHelper {

    @Resource
    private ClewService service;

    public void query() throws IntrospectionException {
        Clew condition = workDTO.convertJsonToBeanByKey("pageQuery", Clew.class);
        workDTO.setResult(ProxyStripper.cleanFromProxies(service.query(condition, workDTO.getStart(), workDTO.getLimit())));
        workDTO.setTotle(service.getCount(condition));
    }

    public void create() {
        Clew entity = workDTO.convertJsonToBeanByKey("entity", Clew.class);
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
