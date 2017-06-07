package com.asdc.jbp.crm.service;

import com.asdc.jbp.crm.entity.Activity;
import com.asdc.jbp.crm.entity.Contact;
import com.asdc.jbp.crm.entity.Customer;
import com.asdc.jbp.framework.utils.StringUtils;
import org.springframework.beans.BeanUtils;
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
public class ContactService {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Contact> query(Contact condition, Integer start, Integer limit) {
        String hql = "";
        hql += "  from Contact c";
        hql += "  left join fetch c.customer";
        if (!StringUtils.isEmpty(condition.getName())) {
            hql += " where c.name like :name";
        }
        Query query = em.createQuery(hql);
        query.setFirstResult(start).setMaxResults(limit);
        if (!StringUtils.isEmpty(condition.getName())) {
            query.setParameter("name", "%" + condition.getName() + "%");
        }
        //noinspection unchecked
        return query.getResultList();
    }

    public Integer getCount(Contact condition) {
        String hql = "";
        hql += " select count(c)";
        hql += "  from Contact c";
        if (!StringUtils.isEmpty(condition.getName())) {
            hql += " where c.name like :name";
        }
        Query query = em.createQuery(hql);
        if (!StringUtils.isEmpty(condition.getName())) {
            query.setParameter("name", "%" + condition.getName() + "%");
        }
        return ((Number)query.getSingleResult()).intValue();
    }

    public void create(Contact entity) {
        entity.setCustomer(em.find(Customer.class, entity.getCustomer().getId()));
        em.persist(entity);
    }

    public void update(Contact entity) {
        Contact entityInDB = em.find(Contact.class, entity.getId());

        entityInDB.setCustomer(em.find(Customer.class, entity.getCustomer().getId()));

        BeanUtils.copyProperties(entity, entityInDB, "id", "customer");

    }

    public void remove(Integer id) {
        em.remove(em.find(Contact.class, id));
    }
}
