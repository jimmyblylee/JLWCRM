/**
 * Project Name : jbp-plugin-log <br>
 * File Name : JBPEntityRevListener.java <br>
 * Package Name : com.asdc.jbp.log.listener <br>
 * Create Time : Apr 25, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.log.listener;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.envers.EntityTrackingRevisionListener;
import org.hibernate.envers.RevisionType;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import com.asdc.jbp.framework.context.ActionContext;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.message.Messages;
import com.asdc.jbp.framework.utils.ReflectionUtils;
import com.asdc.jbp.log.entity.RevisionEntity;

/**
 * ClassName : JBPEntityRevListener <br>
 * Description : JBP Entities listener for Auditing log and Operating log <br>
 * Create Time : Apr 25, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public class EntityRevListener implements EntityTrackingRevisionListener {

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.envers.RevisionListener#newRevision(java.lang.Object)
     */
    @Override
    public void newRevision(Object revisionEntity) {
        if (revisionEntity instanceof RevisionEntity) {
            RevisionEntity entity = (RevisionEntity) revisionEntity;
            // feature
            try {
                entity.setFeature(ActionContext.getContext().getWorkDTO().<String> get("feature"));
            } catch (Throwable e) {
                // ignore
            }
            // function
            try {
                entity.setFeature(ActionContext.getContext().getWorkDTO().<String> get("function"));
            } catch (Throwable e) {
                // ignore
            }
            // IP
            try {
                HttpServletRequest request = ActionContext.getContext().getServletRequest();
                entity.setOperatiorIp(request.getHeader("x-forwarded-for") == null ? request.getRemoteAddr() : request.getHeader("x-forwarded-for"));
            } catch (Throwable e) {
                // ignore
            }
            try {
                Object userObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                // oerpator's id
                entity.setOperatorId((Integer) ReflectionUtils.getField(ReflectionUtils.findField(userObj.getClass(), "id"), userObj));
                // operator's name
                entity.setOperatorName((String) ReflectionUtils.getField(ReflectionUtils.findField(userObj.getClass(), "name"), userObj));
            } catch (Throwable e) {
                // anonymous
                entity.setOperatorId(-100);
                entity.setOperatorName("anonymous");
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.envers.EntityTrackingRevisionListener#entityChanged(java.lang.Class, java.lang.String, java.io.Serializable,
     * org.hibernate.envers.RevisionType, java.lang.Object)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void entityChanged(Class entityClass, String entityName, Serializable entityId, RevisionType revisionType, Object revisionEntity) {
        try {
            ((RevisionEntity) revisionEntity).addModifiedEntityType(entityName, (int) entityId, (int) revisionType.getRepresentation());
        } catch (NumberFormatException e) {
            try {
                LoggerFactory.getLogger(getClass()).warn("ERR_LOG_002: " + Messages.getMsg("log", "ERR_LOG_TARGET_ID_IS_NOT_AN_INTEGER"), entityId, entityName);
            } catch (ServiceException e1) {
                LoggerFactory.getLogger(getClass()).warn(
                        "ERR_LOG_002: Can not cast target id {} of entity {} into Integer, while auditing modified entity name. Auditing can only audit modificaiton with integer id, and it will saved with -9999 as default",
                        entityId, entityName);
            }
            ((RevisionEntity) revisionEntity).addModifiedEntityType(entityName, -9999, (int) revisionType.getRepresentation());
        }
    }
}
