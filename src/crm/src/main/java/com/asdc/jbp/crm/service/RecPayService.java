package com.asdc.jbp.crm.service;

import com.asdc.jbp.crm.entity.Pact;
import com.asdc.jbp.crm.entity.RecPay;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Transactional
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RecPayService {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<RecPay> query(RecPay condition, Integer start, Integer limit) {
        String hql = "";
        hql += "  from RecPay r";
        hql += "  left join fetch r.pact";
        hql += " where 1 = 1 ";
        if (!ObjectUtils.isEmpty(condition.getPact()) && !StringUtils.isEmpty(condition.getPact().getName())) {
            hql += "    and r.pact.name like :pactName";
        }
        if (!StringUtils.isEmpty(condition.getDate())) {
            hql +=  " and r.date > :date";
        }
        Query query = em.createQuery(hql);
        if (!ObjectUtils.isEmpty(condition.getPact()) && !StringUtils.isEmpty(condition.getPact().getName())) {
            query.setParameter("pactName", "%" + condition.getPact().getName() + "%");
        }
        if (!StringUtils.isEmpty(condition.getDate())) {
            query.setParameter("date", condition.getDate());
        }

        //noinspection unchecked
        return query.setFirstResult(start).setMaxResults(limit).getResultList();
    }

    @Transactional(readOnly = true)
    public Integer getCount(RecPay condition) {
        String hql = "";
        hql += " select count(r)";
        hql += "  from RecPay r";
        hql += " where 1 = 1 ";
        if (!ObjectUtils.isEmpty(condition.getPact()) && !StringUtils.isEmpty(condition.getPact().getName())) {
            hql += "    and r.pact.name like :pactName";
        }
        if (!StringUtils.isEmpty(condition.getDate())) {
            hql +=  " and r.date > :date";
        }
        Query query = em.createQuery(hql);
        if (!ObjectUtils.isEmpty(condition.getPact()) && !StringUtils.isEmpty(condition.getPact().getName())) {
            query.setParameter("pactName", "%" + condition.getPact().getName() + "%");
        }
        if (!StringUtils.isEmpty(condition.getDate())) {
            query.setParameter("date", condition.getDate());
        }

        //noinspection unchecked
        return ((Number) query.getSingleResult()).intValue();

    }

    public void create(RecPay entity) {
        entity.setPact(em.find(Pact.class, entity.getPact().getId()));
        em.persist(entity);
    }

    public void update(RecPay entity) {
        RecPay entityInDB = em.find(RecPay.class, entity.getId());
        entityInDB.setNum(entity.getNum());
        entityInDB.setDate(entity.getDate());
        entityInDB.setComment(entity.getComment());
        entityInDB.setPact(entity.getPact());
    }

    public void remove(Integer id) {
        em.remove(em.find(RecPay.class, id));
    }
}
