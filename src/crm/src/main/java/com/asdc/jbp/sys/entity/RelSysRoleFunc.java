/*
 * Project Name : jbp-features-sys <br>
 * File Name : RelSysRoleFunc.java <br>
 * Package Name : com.asdc.jbp.sys.entity <br>
 * Create Time : Apr 29, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * ClassName : RelSysRoleFunc <br>
 * Description : entity of relation between role and function, which will never be used unless you want to do auditing with it <br>
 * Create Time : Apr 29, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 */
@SuppressWarnings("unused")
@Entity
@Table(name = "SYS_ROLE_FUNC")
public class RelSysRoleFunc implements Serializable {

    private static final long serialVersionUID = -4333771094237821672L;

    @Id
    @Column(name = "REL_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ROLE_ID")
    private Integer roleId;
    @Column(name = "FUNC_ID")
    private Integer funcId;

    public RelSysRoleFunc() {
    }

    public RelSysRoleFunc(Integer roleId, Integer funcId) {
        this.roleId = roleId;
        this.funcId = funcId;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the roleId
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * @return the funcId
     */
    public Integer getFuncId() {
        return funcId;
    }

    /**
     * @param funcId the funcId to set
     */
    public void setFuncId(Integer funcId) {
        this.funcId = funcId;
    }
}
