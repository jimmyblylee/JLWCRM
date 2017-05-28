package com.asdc.jbp.crm.controller;

import com.asdc.jbp.crm.entity.Contact;
import com.asdc.jbp.crm.entity.Customer;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.utils.ProxyStripper;
import com.asdc.jbp.framework.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.beans.IntrospectionException;

@SuppressWarnings("unused")
@Controller("ContactController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ContactController extends ControllerHelper {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    @Transactional(readOnly = true)
    public void query() throws IntrospectionException {
        String name = workDTO.get("name");
        String hql = "";
        hql += "  from Contact c";
        hql += "  left join fetch c.customer";
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
        Contact contact = workDTO.convertJsonToBeanByKey("entity", Contact.class);
        contact.setCustomer(em.find(Customer.class, contact.getCustomer().getId()));
        em.persist(contact);
    }

    @Transactional
    public void update() {
        Contact entity = workDTO.convertJsonToBeanByKey("entity", Contact.class);
        Contact entityInDB = em.find(Contact.class, entity.getId());

        entityInDB.setCustomer(em.find(Customer.class, entity.getCustomer().getId()));

        BeanUtils.copyProperties(entity, entityInDB, "id", "customer");
    }

    @Transactional
    public void delete() {
        em.remove(em.find(Contact.class, workDTO.getInteger("id")));

    }
}
