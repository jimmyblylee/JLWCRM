package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.entity.Activity;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.utils.ProxyStripper;
import com.asdc.jbp.framework.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.beans.IntrospectionException;

@SuppressWarnings("unused")
@Controller("ActivityController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ActivityController extends ControllerHelper {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    @Transactional(readOnly = true)
    public void query() throws IntrospectionException {
        String date = workDTO.get("date");
        String content = workDTO.get("content");

        String hql = "";
        hql += "  from Activity a";
        hql += " where 1=1";
        if (!StringUtils.isEmpty(date)) {
            hql += "  and a.date = :date";
        }
        if (!StringUtils.isEmpty(content)) {
            hql += "  and a.content like :content";
        }
        Query query = em.createQuery(hql);
        query.setFirstResult(workDTO.getStart()).setMaxResults(workDTO.getLimit());
        if (!StringUtils.isEmpty(date)) {
            query.setParameter("date", date);
        }
        if (!StringUtils.isEmpty(content)) {
            query.setParameter("content", content);
        }

        workDTO.setResult(ProxyStripper.cleanFromProxies(query.getResultList()));
    }

    @Transactional
    public void create() {
        em.persist(workDTO.convertJsonToBeanByKey("entity", Activity.class));
    }

    @Transactional
    public void update() {
        em.merge(workDTO.convertJsonToBeanByKey("entity", Activity.class));
    }

    @Transactional
    public void delete() {
        em.remove(em.find(Activity.class, workDTO.getInteger("id")));
    }
}
