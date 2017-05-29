package com.asdc.jbp.crm.service;

import com.asdc.jbp.crm.entity.Activity;
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
public class ActivityService {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Activity> query(Activity condition, Integer start, Integer limit) {
        String hql = "";
        hql += "  from Activity a";
        hql += " where 1=1";
        if (!StringUtils.isEmpty(condition.getDate())) {
            hql += "  and a.date = :date";
        }
        if (!StringUtils.isEmpty(condition.getContent())) {
            hql += "  and a.content like :content";
        }
        Query query = em.createQuery(hql);
        query.setFirstResult(start).setMaxResults(limit);
        if (!StringUtils.isEmpty(condition.getDate())) {
            query.setParameter("date", condition.getDate());
        }
        if (!StringUtils.isEmpty(condition.getContent())) {
            query.setParameter("content", condition.getContent());
        }
        //noinspection unchecked
        return query.getResultList();
    }

    public Integer getCount(Activity condition) {
        String hql = "";
        hql += " select count(a)";
        hql += "  from Activity a";
        hql += " where 1=1";
        if (!StringUtils.isEmpty(condition.getDate())) {
            hql += "  and a.date = :date";
        }
        if (!StringUtils.isEmpty(condition.getContent())) {
            hql += "  and a.content like :content";
        }
        Query query = em.createQuery(hql);
        if (!StringUtils.isEmpty(condition.getDate())) {
            query.setParameter("date", condition.getDate());
        }
        if (!StringUtils.isEmpty(condition.getContent())) {
            query.setParameter("content", condition.getContent());
        }
        return ((Number)query.getSingleResult()).intValue();
    }

    public void create(Activity entity) {
        em.persist(entity);
    }

    public void update(Activity entity) {
        em.merge(entity);

    }

    public void remove(Integer id) {
        em.remove(em.find(Activity.class, id));
    }
}
