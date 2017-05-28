package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.entity.*;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.utils.ProxyStripper;
import com.asdc.jbp.framework.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.beans.IntrospectionException;
import java.util.Date;

@SuppressWarnings("unused")
@Controller("ClewController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClewController extends ControllerHelper {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    @Transactional(readOnly = true)
    public void query() throws IntrospectionException {
        Clew condition = workDTO.convertJsonToBeanByKey("condition", Clew.class);
        String hql = "";
        hql += " from Clew p";
        hql += " where 1=1";

        if (!StringUtils.isEmpty(condition.getName())) {
            hql += "  and p.name like :name";
        }

        if (!ObjectUtils.isEmpty(condition.getCustomer()) && !ObjectUtils.isEmpty(condition.getCustomer().getId())) {
            hql += "  and p.customer.id = :customerId";
        }

        if (!ObjectUtils.isEmpty(condition.getContact()) && !ObjectUtils.isEmpty(condition.getContact().getId())) {
            hql += "  and p.contact.id = :contactId";
        }

        if (!ObjectUtils.isEmpty(condition.getBudget())) {
            hql += "  and p.budget > :budget";
        }

        if (!ObjectUtils.isEmpty(condition.getPeriod()) && !StringUtils.isEmpty(condition.getPeriod().getCode())) {
            hql += "  and p.period.code = :period";
        }

        if (!ObjectUtils.isEmpty(condition.getSales()) && !ObjectUtils.isEmpty(condition.getSales().getId())) {
            hql += "  and p.sales.id = :sales";
        }

        Query query = em.createQuery(hql);
        query.setFirstResult(workDTO.getStart()).setMaxResults(workDTO.getLimit());

        if (!StringUtils.isEmpty(condition.getName())) {
            query.setParameter("name", "%" + condition.getName() + "%");
        }

        if (!ObjectUtils.isEmpty(condition.getCustomer()) && !ObjectUtils.isEmpty(condition.getCustomer().getId())) {
            query.setParameter("customerId", condition.getCustomer().getId());
        }

        if (!ObjectUtils.isEmpty(condition.getContact()) && !ObjectUtils.isEmpty(condition.getContact().getId())) {
            query.setParameter("contactId", condition.getContact().getId());
        }

        if (!ObjectUtils.isEmpty(condition.getBudget())) {
            query.setParameter("budget", condition.getBudget());
        }

        if (!ObjectUtils.isEmpty(condition.getPeriod()) && !StringUtils.isEmpty(condition.getPeriod().getCode())) {
            query.setParameter("period", condition.getPeriod().getCode());
        }

        if (!ObjectUtils.isEmpty(condition.getSales()) && !ObjectUtils.isEmpty(condition.getSales().getId())) {
            query.setParameter("sales", condition.getSales().getId());
        }

        workDTO.setResult(ProxyStripper.cleanFromProxies(query.getResultList()));
    }

    @Transactional
    public void create() {
        Clew entity = workDTO.convertJsonToBeanByKey("entity", Clew.class);

        entity.setCustomer(em.find(Customer.class, entity.getCustomer().getId()));
        entity.setContact(em.find(Contact.class, entity.getContact().getId()));
        entity.setPeriod(em.find(Dict.class, entity.getPeriod().getId()));
        entity.setSales(em.find(Sales.class, entity.getSales().getId()));

        entity.setCreate(new Date());
        entity.setUpdate(entity.getCreate());

        em.persist(entity);
    }

    @Transactional
    public void update() {
        Clew entity = workDTO.convertJsonToBeanByKey("entity", Clew.class);

        Clew entityInDB = em.find(Clew.class, entity.getId());

        entityInDB.setCustomer(em.find(Customer.class, entity.getCustomer().getId()));
        entityInDB.setContact(em.find(Contact.class, entity.getContact().getId()));
        entityInDB.setPeriod(em.find(Dict.class, entity.getPeriod().getId()));
        entityInDB.setSales(em.find(Sales.class, entity.getSales().getId()));

        BeanUtils.copyProperties(entity, entityInDB, "id", "customer", "contact", "period", "sales");
    }

    @Transactional
    public void delete() {
        em.remove(em.find(Clew.class, workDTO.getInteger("id")));
    }
}
