/*
 * Project Name : jbp-features-sys <br>
 * File Name : Dept.java <br>
 * Package Name : com.asdc.jbp.sys.token <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.token;

import java.io.Serializable;

/**
 * ClassName : Dept <br>
 * Description : value object of SysDept for token <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public class Dept implements Serializable {

    private static final long serialVersionUID = 4771231776367042763L;

    private Integer id;
    private String name;

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
}
