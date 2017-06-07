package com.asdc.jbp.crm.service;

import com.asdc.jbp.crm.entity.Cost;
import com.asdc.jbp.crm.entity.Dict;
import com.asdc.jbp.crm.entity.Pact;
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
public class CostService {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Cost> query(Cost condition, Integer start, Integer limit) {
        String hql = "";
        hql += "  from Cost r";
        hql += "  left join fetch r.type";
        hql += "  left join fetch r.pact";
        hql += " where 1 = 1 ";
        if (!ObjectUtils.isEmpty(condition.getPact()) && !StringUtils.isEmpty(condition.getPact().getName())) {
            hql += "    and r.pact.name like :pactName";
        }
        if (!StringUtils.isEmpty(condition.getTime())) {
            hql += "    and r.time > :time";
        }
        if (!ObjectUtils.isEmpty(condition.getType()) && !StringUtils.isEmpty(condition.getType().getCode())) {
            hql += "  and r.type.code = :typeCode";
        }
        Query query = em.createQuery(hql);
        if (!ObjectUtils.isEmpty(condition.getPact()) && !StringUtils.isEmpty(condition.getPact().getName())) {
            query.setParameter("pactName", "%" + condition.getPact().getName() + "%");
        }
        if (!StringUtils.isEmpty(condition.getTime())) {
            query.setParameter("time", condition.getTime());
        }
        if (!ObjectUtils.isEmpty(condition.getType()) && !StringUtils.isEmpty(condition.getType().getCode())) {
            query.setParameter("typeCode", condition.getType().getCode());
        }

        //noinspection unchecked
        return query.setFirstResult(start).setMaxResults(limit).getResultList();
    }

    @Transactional(readOnly = true)
    public Integer getCount(Cost condition) {
        String hql = "";
        hql += " select count(r)";
        hql += "  from Cost r";
        hql += " where 1 = 1 ";
        if (!ObjectUtils.isEmpty(condition.getPact()) && !StringUtils.isEmpty(condition.getPact().getName())) {
            hql += "    and r.pact.name like :pactName";
        }
        if (!StringUtils.isEmpty(condition.getTime())) {
            hql += "    and r.time > :time";
        }
        if (!ObjectUtils.isEmpty(condition.getType()) && !StringUtils.isEmpty(condition.getType().getCode())) {
            hql += "  and r.type.code = :typeCode";
        }
        final Query query = em.createQuery(hql);
        if (!ObjectUtils.isEmpty(condition.getPact()) && !StringUtils.isEmpty(condition.getPact().getName())) {
            query.setParameter("pactName", "%" + condition.getPact().getName() + "%");
        }
        if (!StringUtils.isEmpty(condition.getTime())) {
            query.setParameter("time", condition.getTime());
        }
        if (!ObjectUtils.isEmpty(condition.getType()) && !StringUtils.isEmpty(condition.getType().getCode())) {
            query.setParameter("typeCode", condition.getType().getCode());
        }

        //noinspection unchecked
        return ((Number) query.getSingleResult()).intValue();
    }

    public void create(Cost entity) {
        if (!ObjectUtils.isEmpty(entity.getPact())) {
            entity.setPact(em.find(Pact.class, entity.getPact().getId()));
        }
        entity.setType(em.find(Dict.class, entity.getType().getId()));
        em.persist(entity);

    }

    public void update(Cost entity) {
        Cost entityInDB = em.find(Cost.class, entity.getId());
        entityInDB.setNum(entity.getNum());
        entityInDB.setType(em.find(Dict.class, entity.getType().getId()));
        if (!ObjectUtils.isEmpty(entity.getPact())) {
            entityInDB.setPact(em.find(Pact.class, entity.getPact().getId()));
        } else {
            entityInDB.setPact(null);
        }
        entityInDB.setComment(entity.getComment());
    }

    public void remove(Integer id) {
        em.remove(em.find(Cost.class, id));
    }
}
