package com.asdc.jbp.crm.service;

import com.asdc.jbp.crm.entity.Dict;
import com.asdc.jbp.crm.entity.Implementer;
import com.asdc.jbp.crm.entity.Pact;
import com.asdc.jbp.crm.entity.Project;
import org.springframework.beans.BeanUtils;
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
public class ProjectService {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    public List<Project> query(Project condition, Integer start, Integer limit) {
        String hql = "";
        hql += "  from Project p";
        hql += "  left join fetch p.pact";
        hql += "  left join fetch p.status";
        hql += "  left join fetch p.charger";
        hql += " where 1 = 1 ";
        if (!ObjectUtils.isEmpty(condition.getPact()) && !StringUtils.isEmpty(condition.getPact().getName())) {
            hql += "   and p.pact.name like :pactName";
        }
        if (!ObjectUtils.isEmpty(condition.getStatus()) && !ObjectUtils.isEmpty(condition.getStatus().getId())) {
            hql += "   and p.status.id = :statusId";
        }
        Query query = em.createQuery(hql);
        if (!ObjectUtils.isEmpty(condition.getPact()) && !StringUtils.isEmpty(condition.getPact().getName())) {
            query.setParameter("pactName", "%" + condition.getPact().getName() + "%");
        }
        if (!ObjectUtils.isEmpty(condition.getStatus()) && !ObjectUtils.isEmpty(condition.getStatus().getId())) {
            query.setParameter("statusId", condition.getStatus().getId());
        }
        //noinspection unchecked
        return query.setFirstResult(start).setMaxResults(limit).getResultList();
    }

    public Integer getCount(Project condition) {
        String hql = "";
        hql += " select count(p)";
        hql += "  from Project p";
        hql += " where 1 = 1 ";
        if (!ObjectUtils.isEmpty(condition.getPact()) && !StringUtils.isEmpty(condition.getPact().getName())) {
            hql += "   and p.pact.name like :pactName";
        }
        if (!ObjectUtils.isEmpty(condition.getStatus()) && !ObjectUtils.isEmpty(condition.getStatus().getId())) {
            hql += "   and p.status.id = :statusId";
        }
        Query query = em.createQuery(hql);
        if (!ObjectUtils.isEmpty(condition.getPact()) && !StringUtils.isEmpty(condition.getPact().getName())) {
            query.setParameter("pactName", "%" + condition.getPact().getName() + "%");
        }
        if (!ObjectUtils.isEmpty(condition.getStatus()) && !ObjectUtils.isEmpty(condition.getStatus().getId())) {
            query.setParameter("statusId", condition.getStatus().getId());
        }
        return ((Number) query.getSingleResult()).intValue();
    }

    public void create(Project entity) {
        entity.setPact(em.find(Pact.class, entity.getPact().getId()));
        entity.setStatus(em.find(Dict.class, entity.getStatus().getId()));
        entity.setCharger(em.find(Implementer.class, entity.getCharger().getId()));
        em.persist(entity);
    }

    public void update(Project entity) {
        Project entityInDB = em.find(Project.class, entity.getId());

        entityInDB.setPact(em.find(Pact.class, entity.getPact().getId()));
        entityInDB.setStatus(em.find(Dict.class, entity.getStatus().getId()));
        entityInDB.setCharger(em.find(Implementer.class, entity.getCharger().getId()));

        BeanUtils.copyProperties(entity, entityInDB, "id", "pact", "status", "charger");
    }

    public void remove(Integer id) {
        em.remove(em.find(Project.class, id));
    }

}
