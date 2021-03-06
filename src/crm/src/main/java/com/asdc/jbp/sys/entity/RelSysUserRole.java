/*
 * Project Name : jbp-features-sys <br>
 * File Name : RelSysUserRole.java <br>
 * Package Name : com.asdc.jbp.sys.entity <br>
 * Create Time : Apr 29, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * ClassName : RelSysUserRole <br>
 * Description : entity of relation between user and role, which will never be used unless you want to do auditing with it <br>
 * Create Time : Apr 29, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Entity
@Table(name = "SYS_USER_ROLE")
public class RelSysUserRole implements Serializable {

    private static final long serialVersionUID = 4067156916701413130L;

    @Id
    @Column(name = "REL_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "USER_ID")
    private Integer userId;
    @Column(name = "ROLE_ID")
    private Integer roleId;

    public RelSysUserRole() {
    }

    public RelSysUserRole(Integer userId, Integer roleId) {
        this.userId = userId;
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
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
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
