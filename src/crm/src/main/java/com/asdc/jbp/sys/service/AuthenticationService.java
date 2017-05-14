/**
 * Project Name : jbp-features-sys <br>
 * File Name : AuthenticationService.java <br>
 * Package Name : com.asdc.jbp.sys.service <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.service;

import com.asdc.jbp.framework.exception.ServiceException;

/**
 * ClassName : AuthenticationService <br>
 * Description : 用户认证服务 <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public interface AuthenticationService {

    /**
     * Description : 根据账号密码校验用户登录信息 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param account
     * @param password
     * @return user id
     * @throws ServiceException
     *             无法找到该用户,请检查账号或密码的有效性
     */
    public Integer checkAccountAndPwd(String account, String password) throws ServiceException;
    
    Integer queryGroupTotle() throws ServiceException;

    Integer querySysGroupByManyfieldsCount(String queryCondition) throws ServiceException;
}
