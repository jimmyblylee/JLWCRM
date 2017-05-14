/**
 * Project Name : jbp-features-sys <br>
 * File Name : User.java <br>
 * Package Name : com.asdc.jbp.sys.token <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.token;

import java.io.Serializable;

/**
 * ClassName : User <br>
 * Description : value object of SysUser for Token <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public class User implements Serializable {

    private static final long serialVersionUID = -961920146834289308L;

    private Integer id;
    private Dept dept;
    private String name;
    private String account;
    private String email;
    private Dict type;

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
     * @return the dept
     */
    public Dept getDept() {
        return dept;
    }

    /**
     * @param dept
     *            the dept to set
     */
    public void setDept(Dept dept) {
        this.dept = dept;
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
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account
     *            the account to set
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the type
     */
    public Dict getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(Dict type) {
        this.type = type;
    }
}
