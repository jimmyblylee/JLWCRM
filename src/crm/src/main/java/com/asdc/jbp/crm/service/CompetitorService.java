package com.asdc.jbp.crm.service;

import com.asdc.jbp.crm.entity.Activity;
import com.asdc.jbp.crm.entity.Competitor;
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
public class CompetitorService {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Competitor> query(Competitor condition, Integer start, Integer limit) {

        String hql = "";
        hql += "  from Competitor c";
        hql += " where 1=1";

        if (!StringUtils.isEmpty(condition.getName())) {
            hql += "  and c.name like :name";
        }

        Query query = em.createQuery(hql);
        query.setFirstResult(start).setMaxResults(limit);

        if (!StringUtils.isEmpty(condition.getName())) {
            query.setParameter("name", condition.getName());
        }
        //noinspection unchecked
        return query.getResultList();
    }

    public void create(Competitor entity) {
        em.persist(entity);
    }

    public void update(Competitor entity) {
        em.merge(entity);

    }

    public void remove(Integer id) {
        em.remove(em.find(Competitor.class, id));
    }
}
