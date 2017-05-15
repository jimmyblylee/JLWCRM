/**
 * Project Name : jbp-plugin-log <br>
 * File Name : RevisionEntityDao.java <br>
 * Package Name : com.asdc.jbp.log.dao <br>
 * Create Time : Apr 25, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.log.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.exception.AuditException;
import org.hibernate.envers.query.internal.property.RevisionNumberPropertyName;
import org.hibernate.envers.query.order.internal.PropertyAuditOrder;
import org.springframework.stereotype.Repository;

import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.message.Messages;
import com.asdc.jbp.log.entity.RevisionEntity;

/**
 * ClassName : RevisionEntityDao <br>
 * Description : dao of Reversion entities <br>
 * Create Time : Apr 25, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Repository
public class RevisionEntityDao {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    @SuppressWarnings("unchecked")
    public List<RevisionEntity> queryAll(int start, int limit) {
        return em.createNamedQuery("log.hql.queryAll").setFirstResult(start).setMaxResults(limit).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<String> queryChangesEntityName(int revId) {
        return em.createNamedQuery("log.sql.queryModByRevId").setParameter("rev", revId).getResultList();
    }

    public List<?> queryAuditingLogByEntityName(int revId, String entityName) throws ServiceException {
        try {
            return AuditReaderFactory.get(em).createQuery().forRevisionsOfEntity(Class.forName(entityName), true, true)
                    .addOrder(new PropertyAuditOrder(new RevisionNumberPropertyName(), true)).getResultList();
        } catch (AuditException e) {
            throw new ServiceException("ERR_LOG_001", Messages.getMsg("log", "ERR_LOG_CAN_NOT_GET_AUDIT_LOG"), entityName);
        } catch (ClassNotFoundException e) {
            throw new ServiceException("ERR_LOG_001", Messages.getMsg("log", "ERR_LOG_CAN_NOT_FIND_CLASS"), entityName);
        }
    }
}
