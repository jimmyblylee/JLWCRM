/**
 * Project Name : jbp-features-sys <br>
 * File Name : RelSysGroupRole.java <br>
 * Package Name : com.asdc.jbp.sys.entity <br>
 * Create Time : Apr 29, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.entity;

import java.io.Serializable;

import org.hibernate.envers.Audited;

/**
 * ClassName : RelSysGroupRole <br>
 * Description : entity of relation between role and group, which will never be used unless you want to do auditing with it <br>
 * Create Time : Apr 29, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Audited
public class RelSysGroupRole implements Serializable {

    private static final long serialVersionUID = 8360166501259190957L;

    private Integer id;
    private Integer groupId;
    private Integer roleId;
    
    public RelSysGroupRole() {
    }
    
    public RelSysGroupRole(Integer groupId, Integer roleId) {
        this.groupId = groupId;
        this.roleId = roleId;
    }

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
     * @return the groupId
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * @param groupId
     *            the groupId to set
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the roleId
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     *            the roleId to set
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
