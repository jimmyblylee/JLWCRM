package com.asdc.jbp.crm.service;

import com.asdc.jbp.crm.entity.Activity;
import com.asdc.jbp.crm.entity.Sales;
import com.asdc.jbp.framework.utils.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Transactional
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SalesService {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Sales> query(Sales condition, Integer start, Integer limit) {
        String hql = "";
        hql += "  from Sales s";
        if (!StringUtils.isEmpty(condition.getName())) {
            hql += " where s.name like :name";
        }
        Query query = em.createQuery(hql);
        query.setFirstResult(start).setMaxResults(limit);
        if (!StringUtils.isEmpty(condition.getName())) {
            query.setParameter("name", "%" + condition.getName() + "%");
        }
        //noinspection unchecked
        return query.getResultList();
    }

    public void create(Sales entity) {
        em.persist(entity);
    }

    public void update(Sales entity) {
        em.merge(entity);

    }

    public void remove(Integer id) {
        em.remove(em.find(Sales.class, id));
    }
}
