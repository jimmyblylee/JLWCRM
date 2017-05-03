/*
 * Project Name : jbp-features-sys <br>
 * File Name : DeptMgtService.java <br>
 * Package Name : com.asdc.jbp.sys.service <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.service;

import java.util.List;

import com.asdc.jbp.sys.entity.SysGroup;

/**
 * ClassName : GroupMgtService <br>
 * Description : 用户组管理服务 <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : haotian_yang@asdc.com.cn <br>
 *
 */
public interface GroupMgtService {

    /**
     * Description : 查询所有用户分组信息 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : haotian_yang@asdc.com.cn <br>
     *
     * @return all SysGroup list
     */
    List<SysGroup> queryAllSysGroup();



}
