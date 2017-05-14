/**
 * Project Name : jbp-plugins-log <br>
 * File Name : ModifiedEntityTypeEntity.java <br>
 * Package Name : com.asdc.jbp.log.entity <br>
 * Create Time : Apr 27, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.log.entity;

import java.io.Serializable;

/**
 * ClassName : ModifiedEntityTypeEntity <br>
 * Description : entity that containing revision id and modified entities names<br>
 * Create Time : Apr 27, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public class ModifiedEntityTypeEntity implements Serializable {

    private static final long serialVersionUID = 7646860683263187574L;

    private int id;
    private RevisionEntity revision;
    private String entityClassName;
    private Integer entityId;
    private Integer revisionType;
    
    public ModifiedEntityTypeEntity() {
        
    }

    public ModifiedEntityTypeEntity(RevisionEntity revision, String entityClassName, Integer entityId, Integer revisionType) {
        this.revision = revision;
        this.entityClassName = entityClassName;
        this.entityId = entityId;
        this.revisionType = revisionType;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the revision
     */
    public RevisionEntity getRevision() {
        return revision;
    }

    /**
     * @param revision
     *            the revision to set
     */
    public void setRevision(RevisionEntity revision) {
        this.revision = revision;
    }

    /**
     * @return the entityClassName
     */
    public String getEntityClassName() {
        return entityClassName;
    }

    /**
     * @param entityClassName
     *            the entityClassName to set
     */
    public void setEntityClassName(String entityClassName) {
        this.entityClassName = entityClassName;
    }

    /**
     * @return the entityId
     */
    public Integer getEntityId() {
        return entityId;
    }

    /**
     * @param entityId the entityId to set
     */
    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    /**
     * @return the revisionType
     */
    public Integer getRevisionType() {
        return revisionType;
    }

    /**
     * @param revisionType the revisionType to set
     */
    public void setRevisionType(Integer revisionType) {
        this.revisionType = revisionType;
    }
}
