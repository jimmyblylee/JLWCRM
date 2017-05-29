package com.asdc.jbp.crm.service;

import com.asdc.jbp.crm.entity.Customer;
import com.asdc.jbp.crm.entity.Dict;
import com.asdc.jbp.framework.utils.BeanUtils;
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
public class CustomerService {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Customer> query(Customer condition, Integer start, Integer limit) {
        String hql = "";
        hql += "  from Customer c";
        hql += "  left join fetch c.trade";
        hql += "  left join fetch c.quality";
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

    public Integer getCount(Customer condition) {
        String hql = "";
        hql += " select count(c)";
        hql += "  from Customer c";
        hql += "  left join fetch c.trade";
        hql += "  left join fetch c.quality";
        if (!StringUtils.isEmpty(condition.getName())) {
            hql += " where c.name like :name";
        }
        Query query = em.createQuery(hql);
        if (!StringUtils.isEmpty(condition.getName())) {
            query.setParameter("name", "%" + condition.getName() + "%");
        }
        return ((Number)query.getSingleResult()).intValue();
    }

    public void create(Customer entity) {
        entity.setTrade(em.find(Dict.class, entity.getTrade().getId()));
        entity.setQuality(em.find(Dict.class, entity.getQuality().getId()));
        entity.setScope(em.find(Dict.class, entity.getScope().getId()));
        em.persist(entity);
    }

    public void update(Customer entity) {
        Customer entityInDB = em.find(Customer.class, entity.getId());

        entityInDB.setTrade(em.find(Dict.class, entity.getTrade().getId()));
        entityInDB.setQuality(em.find(Dict.class, entity.getQuality().getId()));
        entityInDB.setScope(em.find(Dict.class, entity.getScope().getId()));

        BeanUtils.copyProperties(entity, entityInDB, "id", "trade", "quality", "scope");

    }

    public void remove(Integer id) {
        em.remove(em.find(Customer.class, id));
    }
}
