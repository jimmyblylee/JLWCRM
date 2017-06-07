package com.asdc.jbp.crm.service;

import com.asdc.jbp.crm.entity.Dict;
import com.asdc.jbp.crm.entity.Implementer;
import com.asdc.jbp.framework.utils.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Transactional
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ImplService {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Implementer> query(Implementer condition, Integer start, Integer limit) {
        String hql = "";
        hql += "  from Implementer i";
        hql += "  left join fetch i.role";
        hql += " where 1=1";
        if (!StringUtils.isEmpty(condition.getName())) {
            hql += " and i.name like :name";
        }
        if (!ObjectUtils.isEmpty(condition.getPay())) {
            hql += " and i.pay > :pay";
        }
        Query query = em.createQuery(hql);
        if (!StringUtils.isEmpty(condition.getName())) {
            query.setParameter("name", "%" + condition.getName() + "%");
        }
        if (!ObjectUtils.isEmpty(condition.getPay())) {
            query.setParameter("pay", condition.getPay());
        }
        //noinspection unchecked
        return query.setFirstResult(start).setMaxResults(limit).getResultList();
    }

    @Transactional(readOnly = true)
    public Integer getCount(Implementer condition) {
        String hql = "";
        hql += " select count(i)";
        hql += "   from Implementer i";
        hql += "  where 1=1";
        if (!StringUtils.isEmpty(condition.getName())) {
            hql += " and i.name like :name";
        }
        if (!ObjectUtils.isEmpty(condition.getPay())) {
            hql += " and i.pay > :pay";
        }
        Query query = em.createQuery(hql);
        if (!StringUtils.isEmpty(condition.getName())) {
            query.setParameter("name", "%" + condition.getName() + "%");
        }
        if (!ObjectUtils.isEmpty(condition.getPay())) {
            query.setParameter("pay", condition.getPay());
        }
        return ((Number) query.getSingleResult()).intValue();
    }

    public void create(Implementer entity) {
        entity.setRole(em.find(Dict.class, entity.getRole().getId()));
        em.persist(entity);
    }

    public void update(Implementer entity) {
        Implementer entityInDB = em.find(Implementer.class, entity.getId());
        entityInDB.setName(entity.getName());
        entityInDB.setRole(em.find(Dict.class, entity.getRole().getId()));
        entityInDB.setPay(entity.getPay());
        entityInDB.setTel(entity.getTel());
        entityInDB.setMail(entity.getMail());
        entityInDB.setImg(entity.getImg());
    }

    public void remove(Integer id) {
        em.remove(em.find(Implementer.class, id));
    }
}
