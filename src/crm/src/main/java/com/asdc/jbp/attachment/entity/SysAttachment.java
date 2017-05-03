/*
 * Project Name : jbp-plugins-file <br>
 * File Name : SysAttachment.java <br>
 * Package Name : com.asdc.jbp.attachment.entity <br>
 * Create Time : May 10, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.attachment.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.asdc.jbp.framework.utils.StringUtils;

/**
 * ClassName : SysAttachment <br>
 * Description : entity of sys_attachment <br>
 * Create Time : May 10, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@SuppressWarnings("unused")
@Entity
@Table(name = "SYS_ATT_INFO")
public class SysAttachment implements Serializable {

    private static final long serialVersionUID = -7512088510969776268L;

    @Id
    @Column(name = "ATT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ATT_NATURE")
    private String nature;

    @Column(name = "ATT_REF_ID")
    private Integer ref;

    @Column(name = "ATT_ORI_NAME")
    private String name;

    @Column(name = "ATT_URI")
    private String uri;

    @Column(name = "ATT_DESC")
    private String desc;

    @Column(name = "ATT_OP_ID")
    private Integer operatorId;

    @Column(name = "ATT_OP_NAME")
    private String operatorName;

    @Column(name = "ATT_OP_TIME")
    private Date operateTime;
    @SuppressWarnings("FieldCanBeLocal")
    @Transient
    private String suffix;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the nature
     */
    public String getNature() {
        return nature;
    }

    /**
     * @param nature
     *            the nature to set
     */
    public void setNature(String nature) {
        this.nature = nature;
    }

    /**
     * @return the ref
     */
    public Integer getRef() {
        return ref;
    }

    /**
     * @param ref
     *            the ref to set
     */
    public void setRef(@SuppressWarnings("SameParameterValue") Integer ref) {
        this.ref = ref;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri
     *            the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc
     *            the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the operatorId
     */
    public Integer getOperatorId() {
        return operatorId;
    }

    /**
     * @param operatorId
     *            the operatorId to set
     */
    public void setOperatorId(Integer operatorId) {
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

    /**
     * @return the operateTime
     */
    public Date getOperateTime() {
        return operateTime;
    }

    /**
     * @param operateTime
     *            the operateTime to set
     */
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    /**
     * @return the suffix
     */
    public String getSuffix() {
        return StringUtils.getFilenameExtension(getName());
    }

    /**
     * @param suffix the suffix to set
     */
    public void setSuffix(String suffix) {
        // ignore
    	this.suffix = suffix;
    }
}
