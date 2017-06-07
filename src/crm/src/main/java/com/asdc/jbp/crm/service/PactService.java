package com.asdc.jbp.crm.service;

import com.asdc.jbp.crm.entity.Customer;
import com.asdc.jbp.crm.entity.Dict;
import com.asdc.jbp.crm.entity.Pact;
import com.asdc.jbp.crm.entity.Sales;
import com.asdc.jbp.framework.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Transactional
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PactService {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Pact> query(Pact condition, Integer start, Integer limit) {
        String hql = "";
        hql += " from Pact p";
        hql+= " left join fetch p.customer";
        hql+= " left join fetch p.sales";
        hql += " where 1 = 1";

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
        query.setFirstResult(start).setMaxResults(limit);

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
        //noinspection unchecked
        return query.getResultList();
    }

    public Integer getCount(Pact condition) {
        String hql = "";
        hql += " select count(p)";
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
        return ((Number)query.getSingleResult()).intValue();
    }

    public void create(Pact entity) {

        entity.setCustomer(em.find(Customer.class, entity.getCustomer().getId()));
        entity.setStatus(em.find(Dict.class, entity.getStatus().getId()));
        entity.setSales(em.find(Sales.class, entity.getSales().getId()));

        em.persist(entity);
    }

    public void update(Pact entity) {

        Pact entityInDB = em.find(Pact.class, entity.getId());

        entityInDB.setCustomer(em.find(Customer.class, entity.getCustomer().getId()));
        entityInDB.setStatus(em.find(Dict.class, entity.getStatus().getId()));
        entityInDB.setSales(em.find(Sales.class, entity.getSales().getId()));

        entityInDB.setUpdate(new Date());

        BeanUtils.copyProperties(entity, entityInDB, "id", "customer", "status", "sales");

    }

    public void remove(Integer id) {
        em.remove(em.find(Pact.class, id));
    }
}
