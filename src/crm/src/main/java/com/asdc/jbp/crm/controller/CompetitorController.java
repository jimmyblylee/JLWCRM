package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.entity.Competitor;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.utils.ProxyStripper;
import com.asdc.jbp.framework.utils.StringUtils;
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
public class CompetitorController extends ControllerHelper {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    @Transactional(readOnly = true)
    public void query() throws IntrospectionException {
        String name = workDTO.get("name");

        String hql = "";
        hql += "  from Competitor c";
        hql += " where 1=1";

        if (!StringUtils.isEmpty(name)) {
            hql += "  and c.name like :name";
        }

        Query query = em.createQuery(hql);
        query.setFirstResult(workDTO.getStart()).setMaxResults(workDTO.getLimit());

        if (!StringUtils.isEmpty(name)) {
            query.setParameter("name", name);
        }

        workDTO.setResult(ProxyStripper.cleanFromProxies(query.getResultList()));
    }

    @Transactional
    public void create() {
        em.persist(workDTO.convertJsonToBeanByKey("entity", Competitor.class));
    }

    @Transactional
    public void update() {
        em.merge(workDTO.converJsonToBeanListByKey("entity", Competitor.class));
    }

    @Transactional
    public void delete(){
        em.remove(em.find(Competitor.class, workDTO.getInteger("id")));
    }
}
