/**
 * Project Name : jbp-features-sys <br>
 * File Name : AuthorizationService.java <br>
 * Package Name : com.asdc.jbp.sys.service <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.service;

import java.util.List;

import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.sys.entity.SysFunc;
import com.asdc.jbp.sys.entity.SysGroup;
import com.asdc.jbp.sys.entity.SysRole;
import com.asdc.jbp.sys.token.Token;

/**
 * ClassName : AuthorizationService <br>
 * Description : 提供用户角色、功能、用户组查询服务 <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public interface AuthorizationService {

    /**
     * Description : 根据userId获取token <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param userId
     * @return user token
     * @throws ServiceException 用户不存在
     */
    public Token getUserTokenByUserId(Integer userId) throws ServiceException;

    /**
     * Description : 根据 userID查询当前用户的功能列表 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param userId 用户ID
     * @return 该用户所拥有功能
     * @throws ServiceException 用户不存在
     */
    public List<SysFunc> queryFuncsByUserId(Integer userId) throws ServiceException;

    /**
     * Description : 根据 userID 查询该用户角色 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param userId  用户ID
     * @return 该用户所拥有角色
     * @throws ServiceException 用户不存在
     */
    public List<SysRole> queryRolesByUserId(Integer userId) throws ServiceException;

    /**
     * Description : 根据userID获取当前所属组<br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param userId 用户ID
     * @return 当前用户所属组
     * @throws ServiceException 用户不存在
     */
    public List<SysGroup> queryGroupsByUserId(Integer userId) throws ServiceException;

}
