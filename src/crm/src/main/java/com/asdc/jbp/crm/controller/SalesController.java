package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.entity.Sales;
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
@Controller("SalesController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SalesController extends ControllerHelper {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    @Transactional(readOnly = true)
    public void query() throws IntrospectionException {
        String name = workDTO.get("name");
        String hql = "";
        hql += "  from Sales s";
        if (!StringUtils.isEmpty(name)) {
            hql += " where s.name like :name";
        }
        Query query = em.createQuery(hql);
        query.setFirstResult(workDTO.getStart()).setMaxResults(workDTO.getLimit());
        if (!StringUtils.isEmpty(name)) {
            query.setParameter("name", "%" + name + "%");
        }
        workDTO.setResult(ProxyStripper.cleanFromProxies(query.getResultList()));
    }

    @Transactional
    public void create() {
        em.persist(workDTO.convertJsonToBeanByKey("entity", Sales.class));
    }

    @Transactional
    public void update() {
        Sales entity = workDTO.convertJsonToBeanByKey("entity", Sales.class);

        Sales entityInDB = em.find(Sales.class, entity.getId());

        BeanUtils.copyProperties(entity, entityInDB, "id");
    }

    @Transactional
    public void delete() {
        em.remove(em.find(Sales.class, workDTO.getInteger("id")));
    }

}
