package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.entity.Competitor;
import com.asdc.jbp.crm.service.CompetitorService;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.utils.ProxyStripper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.beans.IntrospectionException;

@SuppressWarnings("unused")
@Controller("CompetitorController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CompetitorController extends ControllerHelper {

    @Resource
    private CompetitorService service;

    public void query() throws IntrospectionException {
        Competitor condition = workDTO.convertJsonToBeanByKey("pageQuery", Competitor.class);

        workDTO.setResult(ProxyStripper.cleanFromProxies(service.query(condition, workDTO.getStart(), workDTO.getLimit())));

        workDTO.setTotle(service.getCount(condition));
    }

    public void create() {
        service.create(workDTO.convertJsonToBeanByKey("entity", Competitor.class));
    }

    public void update() {
        service.update(workDTO.convertJsonToBeanByKey("entity", Competitor.class));
    }

    public void remove(){
        service.remove(workDTO.getInteger("id"));
    }
}
