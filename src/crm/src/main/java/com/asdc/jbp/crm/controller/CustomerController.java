package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.entity.Customer;
import com.asdc.jbp.crm.entity.Dict;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.utils.BeanUtils;
import com.asdc.jbp.framework.utils.ProxyStripper;
import com.asdc.jbp.framework.utils.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.beans.IntrospectionException;

@SuppressWarnings("unused")
@Controller("CustomerController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CustomerController extends ControllerHelper {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    @Transactional(readOnly = true)
    public void query() throws IntrospectionException {
        String name = workDTO.get("name");
        String hql = "";
        hql += "  from Customer c";
        hql += "  left join fetch c.trade";
        hql += "  left join fetch c.quality";
        if (!StringUtils.isEmpty(name)) {
            hql += " where c.name like :name";
        }
        Query query = em.createQuery(hql);
        query.setFirstResult(workDTO.getStart()).setMaxResults(workDTO.getLimit());
        if (!StringUtils.isEmpty(name)) {
            query.setParameter("name", "%" + name + "%");
        }
        workDTO.setResult(ProxyStripper.cleanFromProxies(query.getResultList()));

    }

    @Transactional
    public void create() {
        Customer entity = workDTO.convertJsonToBeanByKey("entity", Customer.class);
        entity.setTrade(em.find(Dict.class, entity.getTrade().getId()));
        entity.setQuality(em.find(Dict.class, entity.getQuality().getId()));
        entity.setScope(em.find(Dict.class, entity.getScope().getId()));
        em.persist(entity);
    }

    @Transactional
    public void update() {
        Customer entity = workDTO.convertJsonToBeanByKey("entity", Customer.class);
        Customer entityInDB = em.find(Customer.class, entity.getId());

        entityInDB.setTrade(em.find(Dict.class, entity.getTrade().getId()));
        entityInDB.setQuality(em.find(Dict.class, entity.getQuality().getId()));
        entityInDB.setScope(em.find(Dict.class, entity.getScope().getId()));

        BeanUtils.copyProperties(entity, entityInDB, "id", "trade", "quality", "scope");
    }

    @Transactional
    public void delete() {
        em.remove(em.find(Customer.class, workDTO.getInteger("id")));
    }

}
