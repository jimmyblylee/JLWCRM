/**
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

    @Resource
    private UserMgtService userMgtService;

    /*
     * (non-Javadoc)
     * 
     * @see com.asdc.jbp.sys.service.DeptMgtService#createSysDept(com.asdc.jbp.sys.entity.SysDept)
     */
//    @Override
//    public SysDept createSysGroup(SysDept dept) throws ServiceException {
//        // validate
//        if (dept == null) {
//            throw new ServiceException("ERR_SYS_011", Messages.getMsg("sys", "ERR_SYS_MSG_DEPT_NULL"));
//        }
//        if (dept.getName() == null) {
//            throw new ServiceException("ERR_SYS_011", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_DEPT_NAME_NULL"));
//        }
//        dept.setIsEnabled(true);
//        dao.persist(dept);
//        return dept;
//    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asdc.jbp.sys.service.DeptMgtService#updateSysDept(com.asdc.jbp.sys.entity.SysDept)
     */
//    @Override
//    public SysDept updateSysDept(SysDept dept) throws ServiceException {
//        // validate
//        if (dept == null) {
//            throw new ServiceException("ERR_SYS_012", Messages.getMsg("sys", "ERR_SYS_MSG_DEPT_NULL"));
//        }
//        if (dept.getName() == null) {
//            throw new ServiceException("ERR_SYS_012", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_DEPT_NAME_NULL"));
//        }
//        SysDept deptInDB = getSysDeptById(dept.getId());
//
//        // fill default values
//        BeanUtils.copyProperties(dept, deptInDB, "parent", "isEnabled", "children", "hasChildren");
//        return deptInDB;
//    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asdc.jbp.sys.service.DeptMgtService#removeSysDept(java.util.List)
     */
//    @Override
//    public void removeSysDept(List<Integer> ids) throws ServiceException {
//        if (ids == null) {
//            throw new ServiceException("ERR_SYS_013", Messages.getMsg("sys", "ERR_SYS_MSG_DEPT_ID_NULL"));
//        }
//        for (Integer id : ids) {
//            getSysDeptById(id).setIsEnabled(false);
//        }
//    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asdc.jbp.sys.service.DeptMgtService#getSysDeptById(java.lang.Integer)
     */
//    @Override
//    public SysDept getSysDeptById(Integer id) throws ServiceException {
//        if (id == null) {
//            throw new ServiceException("ERR_SYS_013", Messages.getMsg("sys", "ERR_SYS_MSG_DEPT_ID_NULL"));
//        }
//        SysDept deptInDB = dao.find(SysDept.class, id);
//        if (deptInDB == null) {
//            throw new ServiceException("ERR_SYS_013", Messages.getMsg("sys", "ERR_SYS_MSG_CAN_NOT_FIND_DEPT_BY_ID"), id);
//        }
//        return deptInDB;
//    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asdc.jbp.sys.service.DeptMgtService#queryChildren(java.lang.Integer)
     */
//    @Override
//    public Set<SysDept> queryChildren(Integer id) throws ServiceException {
//        return getSysDeptById(id).getChildren();
//    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asdc.jbp.sys.service.DeptMgtService#queryAllSysDept()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SysGroup> queryAllSysGroup() {
        return (List<SysGroup>) dao.queryByNamedQuery("sys.hql.queryAllSysGroup");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asdc.jbp.sys.service.DeptMgtService#queryUsersByDeptId(java.lang.Integer)
     */
//    @SuppressWarnings("unchecked")
//    @Override
//    public List<SysUser> queryUsersByDeptId(Integer deptId) throws ServiceException {
//        getSysDeptById(deptId);
//        return (List<SysUser>) dao.queryByNamedQuery("sys.hql.queryAllSysUserByDeptId", Parameter.toList("deptId", deptId));
//    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asdc.jbp.sys.service.DeptMgtService#assignUsersToDept(java.lang.Integer, java.util.List)
     */
//    @Override
//    public void assignUsersToDept(Integer deptId, List<Integer> userIds) throws ServiceException {
//        SysDept dept = getSysDeptById(deptId);
//        for (Integer userId : userIds) {
//            userMgtService.getSysUserById(userId).setDept(dept);
//        }
//    }
}
