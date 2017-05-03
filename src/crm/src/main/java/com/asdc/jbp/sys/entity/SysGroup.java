/*
 * Project Name : jbp-features-sys <br>
 * File Name : SysGroup.java <br>
 * Package Name : com.asdc.jbp.sys.entity <br>
 * Create Time : Apr 28, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * ClassName : SysGroup <br>
 * Description : entity of SYS_GROUP <br>
 * Create Time : Apr 28, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Entity
@Table(name = "SYS_GROUP")
public class SysGroup implements Serializable {

    private static final long serialVersionUID = -6426284023586236390L;

    @Id
    @Column(name = "GROUP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "GROUP_NAME")
    private String name;
    @Column(name = "GROUP_DESC")
    private String desc;
    @Column(name = "IS_ENABLED")
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
