/**
 * Project Name : jbp-plugin-log <br>
 * File Name : RevisionEntity.java <br>
 * Package Name : com.asdc.jbp.log.entity <br>
 * Create Time : Apr 25, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.log.entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;

import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import com.asdc.jbp.log.listener.EntityRevListener;

/**
 * ClassName : RevisionEntity <br>
 * Description : default revision entity <br>
 * Create Time : Apr 25, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@org.hibernate.envers.RevisionEntity(EntityRevListener.class)
public class RevisionEntity implements Serializable {

    private static final long serialVersionUID = 3257523111637118587L;

    /* revision columns */
    @RevisionNumber
    private int id;
    @RevisionTimestamp
    private long timestamp;
    private Set<ModifiedEntityTypeEntity> modifiedEntityTypes = new HashSet<>();
    private Set<String> modifiedEntityNames = new HashSet<>();

    /* operation columns */
    /** feature code which is saved in function table, please see {@link #getFeature()} {@link #getFunction()} for more information */
    private String feature;
    /** function code which is saved in funciton table, please see {@link #getFunction()} for more information */
    private String function;
    private String operatiorIp;
    private int operatorId;
    private String operatorName;

    @Transient
    public Date getRevisionDate() {
        return new Date(timestamp);
    }

    public void addModifiedEntityType(String entityClassName, Integer entityId, Integer revisionType) {
        modifiedEntityTypes.add(new ModifiedEntityTypeEntity(this, entityClassName, entityId, revisionType));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RevisionEntity)) {
            return false;
        }

        final RevisionEntity that = (RevisionEntity) o;
        if (id != that.id || timestamp != that.timestamp) {
            return false;
        }
        if (modifiedEntityTypes != null ? modifiedEntityTypes.equals(that.modifiedEntityTypes) : that.modifiedEntityTypes != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = id;
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + (modifiedEntityTypes != null ? modifiedEntityTypes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RevisionEntity( id = " + id + ", revisionDate = " + DateFormat.getDateTimeInstance().format(getRevisionDate()) + ", modifiedEntityNames = "
                + modifiedEntityTypes + ")";
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
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp
     *            the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the modifiedEntityTypes
     */
    public Set<ModifiedEntityTypeEntity> getModifiedEntityTypes() {
        return modifiedEntityTypes;
    }

    /**
     * @param modifiedEntityTypes
     *            the modifiedEntityTypes to set
     */
    public void setModifiedEntityTypes(Set<ModifiedEntityTypeEntity> modifiedEntityTypes) {
        this.modifiedEntityTypes = modifiedEntityTypes;
    }

    /**
     * @return the modifiedEntityNames
     */
    public Set<String> getModifiedEntityNames() {
        return modifiedEntityNames;
    }

    /**
     * @param modifiedEntityNames the modifiedEntityNames to set
     */
    public void setModifiedEntityNames(Set<String> modifiedEntityNames) {
        this.modifiedEntityNames = modifiedEntityNames;
    }

    /**
     * @return the feature
     */
    public String getFeature() {
        return feature;
    }

    /**
     * @param feature
     *            the feature to set
     */
    public void setFeature(String feature) {
        this.feature = feature;
    }

    /**
     * @return the function
     */
    public String getFunction() {
        return function;
    }

    /**
     * @param function
     *            the function to set
     */
    public void setFunction(String function) {
        this.function = function;
    }

    /**
     * @return the operatiorIp
     */
    public String getOperatiorIp() {
        return operatiorIp;
    }

    /**
     * @param operatiorIp
     *            the operatiorIp to set
     */
    public void setOperatiorIp(String operatiorIp) {
        this.operatiorIp = operatiorIp;
    }

    /**
     * @return the operatorId
     */
    public int getOperatorId() {
        return operatorId;
    }

    /**
     * @param operatorId
     *            the operatorId to set
     */
    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * @return the operatorName
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * @param operatorName
     *            the operatorName to set
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}
