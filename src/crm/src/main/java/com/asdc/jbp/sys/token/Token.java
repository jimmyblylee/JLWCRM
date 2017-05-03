/*
 * Project Name : jbp-plugins-token <br>
 * File Name : Token.java <br>
 * Package Name : com.asdc.jbp.sys.token <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.token;

import java.io.Serializable;

/**
 * ClassName : Token <br>
 * Description : user token <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public class Token implements Serializable {

    private static final long serialVersionUID = -2115771740731204716L;

    private User user;
    private Dept dept;
    private FuncTree func;

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(User user) {
        this.user = user;
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
     * @return the func
     */
    public FuncTree getFunc() {
        return func;
    }

    /**
     * @param func
     *            the func to set
     */
    public void setFunc(FuncTree func) {
        this.func = func;
    }
}
