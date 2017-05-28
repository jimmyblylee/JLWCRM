package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.entity.Customer;
import com.asdc.jbp.crm.entity.Dict;
import com.asdc.jbp.crm.entity.Pact;
import com.asdc.jbp.crm.entity.Sales;
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
@Controller("PactController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PactController extends ControllerHelper {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    @Transactional(readOnly = true)
    public void query() throws IntrospectionException {
        Pact condition = workDTO.convertJsonToBeanByKey("condition", Pact.class);
        String hql = "";
        hql += " from Pact p";
        hql += " where 1=1";

        if (!ObjectUtils.isEmpty(condition.getCustomer()) && !ObjectUtils.isEmpty(condition.getCustomer().getId())) {
            hql += "  and p.customer.id = :customerId";
        }

        if (!ObjectUtils.isEmpty(condition.getStatus()) && !StringUtils.isEmpty(condition.getStatus().getCode())) {
            hql += "  and p.status.code = :status";
        }

        if (!ObjectUtils.isEmpty(condition.getSum())) {
            hql += "  and p.sum > :sumValue";
        }

        if (!ObjectUtils.isEmpty(condition.getSales())) {
            hql += "  and p.sales.id = :salesId";
        }

        Query query = em.createQuery(hql);
        query.setFirstResult(workDTO.getStart()).setMaxResults(workDTO.getLimit());

        if (!ObjectUtils.isEmpty(condition.getCustomer()) && !ObjectUtils.isEmpty(condition.getCustomer().getId())) {
            query.setParameter("customerId", condition.getCustomer().getId());
        }

        if (!ObjectUtils.isEmpty(condition.getStatus()) && !StringUtils.isEmpty(condition.getStatus().getCode())) {
            query.setParameter("status", condition.getStatus().getCode());
        }

        if (!ObjectUtils.isEmpty(condition.getSum())) {
            query.setParameter("sumValue", condition.getSum());
        }

        if (!ObjectUtils.isEmpty(condition.getSales())) {
            query.setParameter("salesId", condition.getSales().getId());
        }

        workDTO.setResult(ProxyStripper.cleanFromProxies(query.getResultList()));
    }

    @Transactional
    public void create() {
        Pact entity = workDTO.convertJsonToBeanByKey("entity", Pact.class);

        entity.setCustomer(em.find(Customer.class, entity.getCustomer().getId()));
        entity.setStatus(em.find(Dict.class, entity.getStatus().getId()));
        entity.setSales(em.find(Sales.class, entity.getSales().getId()));

        em.persist(entity);
    }

    @Transactional
    public void update() {
        Pact entity = workDTO.convertJsonToBeanByKey("entity", Pact.class);

        Pact entityInDB = em.find(Pact.class, entity.getId());

        entityInDB.setCustomer(em.find(Customer.class, entity.getCustomer().getId()));
        entityInDB.setStatus(em.find(Dict.class, entity.getStatus().getId()));
        entityInDB.setSales(em.find(Sales.class, entity.getSales().getId()));

        entityInDB.setUpdate(new Date());

        BeanUtils.copyProperties(entity, entityInDB, "id", "customer", "status", "sales");
    }

    @Transactional
    public void delete() {
        em.remove(em.find(Pact.class, workDTO.getInteger("id")));
    }
}
