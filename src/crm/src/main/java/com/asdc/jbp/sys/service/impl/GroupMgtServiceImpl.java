/*
 * Project Name : jbp-features-sys <br>
 * File Name : DeptMgtServiceImpl.java <br>
 * Package Name : com.asdc.jbp.sys.service.impl <br>
 * Create Time : May 1, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asdc.jbp.framework.dao.JpaOrmOperator;
import com.asdc.jbp.sys.entity.SysGroup;
import com.asdc.jbp.sys.service.DeptMgtService;
import com.asdc.jbp.sys.service.GroupMgtService;
import com.asdc.jbp.sys.service.UserMgtService;

/**
 * ClassName : DeptMgtServiceImpl <br>
 * Description : Implementation of {@link DeptMgtService} <br>
 * Create Time : May 1, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional(readOnly = true)
public class GroupMgtServiceImpl implements GroupMgtService {

    @Resource(name = "SysCommonDao")
    private JpaOrmOperator dao;
    @SuppressWarnings("unchecked")
    @Override
    public List<SysGroup> queryAllSysGroup() {
        return (List<SysGroup>) dao.queryByNamedQuery("sys.hql.queryAllSysGroup");
    }
}
