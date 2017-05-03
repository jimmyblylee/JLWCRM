/*
 * Project Name jbp-features-sys
 * File Name  GlobalListService.java
 * Package Name com.asdc.jbp.sys.controller
 * Create Time 2016年6月5日
 * Create by name：liuyuanyuan -- email: yuanyuan_liu@asdc.com.cn
 * Copyright  2006-2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.service;

import java.util.List;

import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.sys.entity.SysGlobalVariable;

/**
 * ClassName : GlobalListService <br>
 * Description : 全局列表服务 <br>
 * Create Time : 2016.5.19 <br>
 * Create by : yuanyuan_liu@asdc.com.cn <br>
 *
 */
public interface GlobalListService {

    /**
     * Description : 查询所有列表 <br>
     * Create Time: 2016.5.19 <br>
     * Create by : yuanyuan_liu@asdc.com.cn <br>
     *
     */

    List<SysGlobalVariable> globalQueryItems(int start, int limit, String queryData, Boolean globalIsEnabled) throws ServiceException;

    /**
     * Description : 查询总数 <br>
     * Create Time: 2016.5.19 <br>
     * Create by : yuanyuan_liu@asdc.com.cn <br>
     *
     */

    Integer globalQueryTotle(String queryData, Boolean globalIsEnabled) throws ServiceException;

    /**
     * Description : 根据Ids删除<br>
     * Create Time: 2016.05.24 <br>
     * Create by : yuanyuan_liu@asdc.com.cn <br>
     *
     *
     */

    void globalDelSingleItems(Integer globalId) throws ServiceException;

    /**
     * Description : 添加列表内容<br>
     * Create Time: 2016.05.24 <br>
     * Create by : yuanyuan_liu@asdc.com.cn <br>
     *
     */

    SysGlobalVariable globalAddItems(SysGlobalVariable globalItme) throws ServiceException;

    /**
     * Description : 更新列表内容<br>
     * Create Time: 2016.05.24 <br>
     * Create by : yuanyuan_liu@asdc.com.cn <br>
     *
     */

    SysGlobalVariable globalUpdateItems(SysGlobalVariable globalItme) throws ServiceException;

    /**
     * Description : 查询登陆系统名称参数 <br>
     * Create Time: 2016.7.13 <br>
     * Create by : yuanyuan_liu@asdc.com.cn <br>
     *
     */

    List<SysGlobalVariable> globalLoginItems(String queryData) throws ServiceException;

    SysGlobalVariable getGlobalByvariableValue(String variableName)throws ServiceException;
    @SuppressWarnings("unused")
    boolean getGlobalIsExit(String variableName) throws ServiceException;
    /**
     * Description : 根据id查找全局参数 <br>
     * Create Time: 2016.8.22 <br>
     * Create by : xuan_yang@asdc.com.cn <br>
     */
    SysGlobalVariable getGlobalById(String variableName)throws ServiceException;
}
