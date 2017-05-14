/**
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
     * Description : 添加部门信息 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param dept
     * @return persisted dept
     * @throws ServiceException
     *             <li>ERR_SYS_011: dept参数不可为空</li>
     *             <li>ERR_SYS_011: 部门名称不可为空</li>
     */
    //public SysGroup createSysGroup(SysGroup dept) throws ServiceException;

    /**
     * Description : 更新部门信息 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param dept
     * @return persisted dept
     * @throws ServiceException
     *             <li>ERR_SYS_012: dept参数不可为空</li>
     *             <li>ERR_SYS_012: 部门名称不可为空</li>
     */
    //public SysGroup updateSysGroup(SysGroup dept) throws ServiceException;

    /**
     * Description : 根据Ids删除多条部门信息 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param ids
     * @throws ServiceException
     *             <li>ERR_SYS_013: 参数 ids不能为空</li>
     *             <li>ERR_SYS_013: 部门ID不能为空</li>
     *             <li>ERR_SYS_013: 无法根据部门ID找到部门信息</li>
     */
   // public void removeSysGroup(List<Integer> ids) throws ServiceException;

    /**
     * Description : 根据ID查询部门信息<br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param id
     * @return the persisted SysDept
     * @throws ServiceException
     *             <li>ERR_SYS_013: 部门ID不能为空</li>
     *             <li>ERR_SYS_013: 无法根据部门ID找到部门信息</li>
     */
    //public SysDept getSysGroupById(Integer id) throws ServiceException;

    /**
     * Description : 根据部门ID查询该部门所包含的子部门信息<br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param id
     * @return the children of given SysDept
     * @throws ServiceException
     *             <li>ERR_SYS_013: 部门ID不能为空</li>
     *             <li>ERR_SYS_013: 无法根据部门ID找到部门信息</li>
     */
    //public Set<SysDept> queryChildren(Integer id) throws ServiceException;

    /**
     * Description : 查询所有用户分组信息 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : haotian_yang@asdc.com.cn <br>
     *
     * @return all SysGroup list
     */
    public List<SysGroup> queryAllSysGroup();

    /**
     * Description : 根据部门ID查询人员信息 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param deptId
     * @return user list of dept by given dept id
     * @throws ServiceException
     *             <li>ERR_SYS_013: 部门ID不能为空</li>
     *             <li>ERR_SYS_013: 无法根据部门ID找到部门信息</li>
     */
    //public List<SysUser> queryUsersByGroupId(Integer deptId) throws ServiceException;



}
