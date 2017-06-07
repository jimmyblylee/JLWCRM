package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.entity.Project;
import com.asdc.jbp.crm.service.ProjectService;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.utils.ProxyStripper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.beans.IntrospectionException;

@SuppressWarnings("unused")
@Controller("ProjectController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProjectController extends ControllerHelper {

    @Resource
    private ProjectService service;

    public void query() throws IntrospectionException {
        Project condition = workDTO.convertJsonToBeanByKey("pageQuery", Project.class);
        workDTO.setResult(ProxyStripper.cleanFromProxies(service.query(condition, workDTO.getStart(), workDTO.getLimit())));
        workDTO.setTotle(service.getCount(condition));
    }

    public void create() {
        service.create(workDTO.convertJsonToBeanByKey("entity", Project.class));
    }

    public void update() {
        service.update(workDTO.convertJsonToBeanByKey("entity", Project.class));
    }

    public void remove() {
        service.remove(workDTO.getInteger("id"));
    }
}
