/**
 * Project Name : jbp-features-sys <br>
 * File Name : RelSysUserGroup.java <br>
 * Package Name : com.asdc.jbp.sys.entity <br>
 * Create Time : Apr 29, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.entity;

import java.io.Serializable;

import org.hibernate.envers.Audited;

/**
 * ClassName : RelSysUserGroup <br>
 * Description : entity of relation between user and group, which will never be used unless you want to do auditing with it <br>
 * Create Time : Apr 29, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Audited
public class RelSysUserGroup implements Serializable {

    private static final long serialVersionUID = 7749437876618674804L;

    private Integer id;
    private Integer userId;
    private Integer groupId;
    
    public RelSysUserGroup() {
    }
    
    public RelSysUserGroup(Integer userId, Integer groupId) {
        this.userId = userId;
        this.groupId = groupId;
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
}