/**
 * Project Name : jbp-features-sys <br>
 * File Name : SysRole.java <br>
 * Package Name : com.asdc.jbp.sys.entity <br>
 * Create Time : Apr 29, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.entity;

import java.io.Serializable;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

/**
 * ClassName : SysRole <br>
 * Description : entity of Role <br>
 * Create Time : Apr 29, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Audited
public class SysRole implements Serializable {

    private static final long serialVersionUID = 5159782070389307355L;

    private Integer id;
    private String name;
    private String desc;
    private Boolean isBaseRole;
    private Boolean isEnabled;

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
     * @return the desc
     */
    @NotAudited
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
     * @return the isBaseRole
     */
    @NotAudited
    public Boolean getIsBaseRole() {
        return isBaseRole;
    }

    /**
     * @param isBaseRole
     *            the isBaseRole to set
     */
    public void setIsBaseRole(Boolean isBaseRole) {
        this.isBaseRole = isBaseRole;
    }

    /**
     * @return the isEnabled
     */
    public Boolean getIsEnabled() {
        return isEnabled;
    }

    /**
     * @param isEnabled
     *            the isEnabled to set
     */
    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
