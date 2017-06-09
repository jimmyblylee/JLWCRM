package com.asdc.jbp.crm.service;

import com.asdc.jbp.crm.entity.*;
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
public class ClewService {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Clew> query(Clew condition, Integer start, Integer limit) {
        String hql = "";
        hql += " from Clew p";
        hql += " left join fetch p.customer";
        hql += " left join fetch p.contact";
        hql += " left join fetch p.sales";
        hql += " where 1 = 1";

        if (!StringUtils.isEmpty(condition.getName())) {
            hql += "  and p.name like :name";
        }

        if (!ObjectUtils.isEmpty(condition.getCustomer()) && !StringUtils.isEmpty(condition.getCustomer().getName())) {
            hql += "  and p.customer.name like :customerName";
        }

        if (!ObjectUtils.isEmpty(condition.getContact()) && !StringUtils.isEmpty(condition.getContact().getName())) {
            hql += "  and p.contact.name like :contactName";
        }

        if (!ObjectUtils.isEmpty(condition.getBudget())) {
            hql += "  and p.budget > :budget";
        }

        if (!ObjectUtils.isEmpty(condition.getPeriod()) && !StringUtils.isEmpty(condition.getPeriod().getCode())) {
            hql += "  and p.period.code = :period";
        }

        if (!ObjectUtils.isEmpty(condition.getSales()) && !StringUtils.isEmpty(condition.getSales().getName())) {
            hql += "  and p.sales.name = :salesName";
        }

        Query query = em.createQuery(hql);
        query.setFirstResult(start).setMaxResults(limit);

        if (!StringUtils.isEmpty(condition.getName())) {
            query.setParameter("name", "%" + condition.getName() + "%");
        }

        if (!ObjectUtils.isEmpty(condition.getCustomer()) && !StringUtils.isEmpty(condition.getCustomer().getName())) {
            query.setParameter("customerName", "%" + condition.getCustomer().getName() + "%");
        }

        if (!ObjectUtils.isEmpty(condition.getContact()) && !StringUtils.isEmpty(condition.getContact().getName())) {
            query.setParameter("contactName", "%" + condition.getContact().getName() + "%");
        }

        if (!ObjectUtils.isEmpty(condition.getBudget())) {
            query.setParameter("budget", condition.getBudget());
        }

        if (!ObjectUtils.isEmpty(condition.getPeriod()) && !StringUtils.isEmpty(condition.getPeriod().getCode())) {
            query.setParameter("period", condition.getPeriod().getCode());
        }

        if (!ObjectUtils.isEmpty(condition.getSales()) && !StringUtils.isEmpty(condition.getSales().getName())) {
            query.setParameter("salesName", "%" + condition.getSales().getName() + "%");
        }
        //noinspection unchecked
        return query.getResultList();
    }

    public Integer getCount(Clew condition) {
        String hql = "";
        hql += " select count(p)";
        hql += " from Clew p";
        hql += " where 1=1";

        if (!StringUtils.isEmpty(condition.getName())) {
            hql += "  and p.name like :name";
        }

        if (!ObjectUtils.isEmpty(condition.getCustomer()) && !StringUtils.isEmpty(condition.getCustomer().getName())) {
            hql += "  and p.customer.name like :customerName";
        }

        if (!ObjectUtils.isEmpty(condition.getContact()) && !StringUtils.isEmpty(condition.getContact().getName())) {
            hql += "  and p.contact.name like :contactName";
        }

        if (!ObjectUtils.isEmpty(condition.getBudget())) {
            hql += "  and p.budget > :budget";
        }

        if (!ObjectUtils.isEmpty(condition.getPeriod()) && !StringUtils.isEmpty(condition.getPeriod().getCode())) {
            hql += "  and p.period.code = :period";
        }

        if (!ObjectUtils.isEmpty(condition.getSales()) && !StringUtils.isEmpty(condition.getSales().getName())) {
            hql += "  and p.sales.name = :salesName";
        }

        Query query = em.createQuery(hql);

        if (!StringUtils.isEmpty(condition.getName())) {
            query.setParameter("name", "%" + condition.getName() + "%");
        }

        if (!ObjectUtils.isEmpty(condition.getCustomer()) && !StringUtils.isEmpty(condition.getCustomer().getName())) {
            query.setParameter("customerName", "%" + condition.getCustomer().getName() + "%");
        }

        if (!ObjectUtils.isEmpty(condition.getContact()) && !StringUtils.isEmpty(condition.getContact().getName())) {
            query.setParameter("contactName", "%" + condition.getContact().getName() + "%");
        }

        if (!ObjectUtils.isEmpty(condition.getBudget())) {
            query.setParameter("budget", condition.getBudget());
        }

        if (!ObjectUtils.isEmpty(condition.getPeriod()) && !StringUtils.isEmpty(condition.getPeriod().getCode())) {
            query.setParameter("period", condition.getPeriod().getCode());
        }

        if (!ObjectUtils.isEmpty(condition.getSales()) && !StringUtils.isEmpty(condition.getSales().getName())) {
            query.setParameter("salesName", "%" + condition.getSales().getName() + "%");
        }
        return ((Number)query.getSingleResult()).intValue();
    }

    public void create(Clew entity) {

        if (entity.getCustomer() != null && entity.getCustomer().getId() != null) {
            entity.setCustomer(em.find(Customer.class, entity.getCustomer().getId()));
        }
        if (entity.getContact() != null && entity.getContact().getId() != null) {
            entity.setContact(em.find(Contact.class, entity.getContact().getId()));
        }
        if (entity.getPeriod() != null && entity.getPeriod().getId() != null) {
            entity.setPeriod(em.find(Dict.class, entity.getPeriod().getId()));
        }
        if (entity.getSales() != null && entity.getSales().getId() != null) {
            entity.setSales(em.find(Sales.class, entity.getSales().getId()));
        }

        entity.setCreate(new Date());
        if (entity.getCreate() != null) {
            entity.setUpdate(entity.getCreate());
        }
        em.persist(entity);
    }

    public void update(Clew entity) {

        Clew entityInDB = em.find(Clew.class, entity.getId());

        entityInDB.setCustomer(em.find(Customer.class, entity.getCustomer().getId()));
        entityInDB.setContact(em.find(Contact.class, entity.getContact().getId()));
        entityInDB.setPeriod(em.find(Dict.class, entity.getPeriod().getId()));
        entityInDB.setSales(em.find(Sales.class, entity.getSales().getId()));

        BeanUtils.copyProperties(entity, entityInDB, "id", "customer", "contact", "period", "sales");

    }

    public void remove(Integer id) {
        em.remove(em.find(Clew.class, id));
    }
}
