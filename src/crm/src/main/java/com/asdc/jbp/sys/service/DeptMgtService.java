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

import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.sys.entity.SysDept;
import com.asdc.jbp.sys.entity.SysUser;

/**
 * ClassName : DeptMgtService <br>
 * Description : 部门管理服务 <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@SuppressWarnings("unused")
public interface DeptMgtService {

    /**
     * Description : 添加部门信息 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return persisted dept
     * @throws ServiceException
     *             <li>ERR_SYS_011: dept参数不可为空</li>
     *             <li>ERR_SYS_011: 部门名称不可为空</li>
     */
    SysDept createSysDept(SysDept dept) throws ServiceException;

    /**
     * Description : 更新部门信息 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return persisted dept
     * @throws ServiceException
     *             <li>ERR_SYS_012: dept参数不可为空</li>
     *             <li>ERR_SYS_012: 部门名称不可为空</li>
     */
    SysDept updateSysDept(SysDept dept) throws ServiceException;

    /**
     * Description : 根据Ids删除多条部门信息 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @throws ServiceException
     *             <li>ERR_SYS_013: 参数 ids不能为空</li>
     *             <li>ERR_SYS_013: 部门ID不能为空</li>
     *             <li>ERR_SYS_013: 无法根据部门ID找到部门信息</li>
     */
    void removeSysDept(List<Integer> ids) throws ServiceException;

    /**
     * Description : 根据ID查询部门信息<br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return the persisted SysDept
     * @throws ServiceException
     *             <li>ERR_SYS_013: 部门ID不能为空</li>
     *             <li>ERR_SYS_013: 无法根据部门ID找到部门信息</li>
     */
    SysDept getSysDeptById(Integer id) throws ServiceException;

    /**
     * Description : 根据部门ID查询该部门所包含的子部门信息<br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return the children of given SysDept
     * @throws ServiceException
     *             <li>ERR_SYS_013: 部门ID不能为空</li>
     *             <li>ERR_SYS_013: 无法根据部门ID找到部门信息</li>
     */
    List<SysDept> queryChildren(Integer id) throws ServiceException;

    /**
     * Description : 查询所有部门信息 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return all SysDept list
     */
    List<SysDept> queryAllSysDept();

    /**
     * Description : 根据部门ID查询人员信息 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return user list of dept by given dept id
     * @throws ServiceException
     *             <li>ERR_SYS_013: 部门ID不能为空</li>
     *             <li>ERR_SYS_013: 无法根据部门ID找到部门信息</li>
     */
    List<SysUser> queryUsersByDeptId(Integer deptId) throws ServiceException;

    /**
     * Description : 更新人员部门信息--为指定人员更新部门信息 relation data for auditing. <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @throws ServiceException
     *             <li>ERR_SYS_013: 部门ID不能为空</li>
     *             <li>ERR_SYS_013: 无法根据部门ID找到部门信息</li>
     *             <li>ERR_SYS_003: 用户ID不能为空</li>
     *             <li>ERR_SYS_003: 无法根据用户ID找到用户信息</li>
     */
    void assignUsersToDept(Integer deptId, List<Integer> userIds) throws ServiceException;

    /**
     *
     * Description：查询部门表得到总条数
     *
     * @return Integer 所有部门总条数
     * @author name：yangxuan <br>
     *         email: xuan_yang@asdc.com.cn
     *
     */
    Integer queryDeptTotle() throws ServiceException;

    /**
     *
     * Description：根据机构id得到
     *
     * @return Integer 根据机构id得到子类的总条数
     * @author name：yangxuan <br>
     *         email: xuan_yang@asdc.com.cn
     *
     */
    Integer getCountByDeptId(String deptId) throws ServiceException;

    /**
     *
     * Description：根据部门名称、部门电话、部门电子邮件、部门地址。进行模糊查询，得到总条数
     *
     * @param queryCondition
     *            模糊插叙匹配的关键字
     * @return Integer 模糊查询得到总条数
     * @author name：yangxuan <br>
     *         email: xuan_yang@asdc.com.cn
     *
     */
    Integer getCountByLikeQuery(String queryCondition) throws ServiceException;

    /**
     * Description：通过名称、电话号码、邮件、所属地址模糊查询机构列表
     *
     * @param start
     *            第几条开始查
     * @param limit
     *            共查几条
     * @param keyWords
     *            模糊查询关键字
     * @return List<SysDept> 机构列表
     * @author name：yangxuan <br>
     *         email: xuan_yang@asdc.com.cn
     */
    List<SysDept> queryDeptByLikeQuery(int start, int limit, String keyWords) throws ServiceException;

    /**
     * Description：查找所有
     *
     * @param start
     *            第几条开始查
     * @param limit
     *            共查几条
     * @return List<SysDept> 机构列表
     * @author name：yangxuan <br>
     *         email: xuan_yang@asdc.com.cn
     */
    List<SysDept> queryAllDeptByPading(int start, int limit) throws ServiceException;

    /**
     *
     * Description：通过父级部门id分页查找子级部门
     *
     * @param start
     *            第几条开始取
     * @param limit
     *            取多少条
     * @param deptId
     *            父级部门id
     * @return List<SysDept> 返回部门信息
     * @author name：yangxuan <br>
     *         email: xuan_yang@asdc.com.cn
     *
     */
    List<SysDept> queryDeptByDeptId(int start, int limit, String deptId) throws ServiceException;


    List<SysDept> queryDeptBypagingAndQuerying(int start,int limit,SysDept SysDept)throws ServiceException;

    int getCountBypagingAndQuerying(SysDept SysDept)throws ServiceException;
    /**
     *
     * Description：恢复机构状态
     * @param ids 机构ID
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     *
     */
    void recoverDeptState(List<Integer> ids)throws ServiceException;
    /**
     *
     * Description：分页查询激活、未激活的机构
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     *
     */
    List<SysDept> queryDeptAllByIsEnabled(int start, int limit,boolean isEnabled)throws ServiceException;
    /**
     *
     * Description：激活、未激活的机构查询总条数
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     *
     */
    int getCountByqueryDeptIsEnabled(boolean isEnabled)throws ServiceException;
    /**
     *
     * Description：根据id得到部门信息
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     *
     */
    SysDept getDeptByDeptId(int deptId)throws ServiceException;
    /**
     *
     * Description：判断此部门是否存在
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     *
     */
    int queryDeptIsExistByDeptId(int deptId)throws ServiceException;

    /**
     *
     * Description：查询上级部门顶级上级部门
     * @return void
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     *
     */
    SysDept queryParentDeptIsNull();
    /**
     *
     * Description：通过的上级机构id以及是否为禁用，得到子类
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     *
     */
    List<SysDept> queryChildrenRecover(int id) throws ServiceException;

    SysDept queryDeptById(SysDept dept) throws ServiceException;
    /**
     *
     * Description：通过deptId判断机构是否可用
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     *
     */
    boolean queryDeptByDeptIdNoisEnabled(int deptid)throws ServiceException;

    SysDept updateSysDeptComplate(SysDept dept) throws ServiceException;
    /**
     *
     * Description：通过机构id得到下面的用户
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     *
     */
    List<SysUser> getUserListByDeptId(int deptId1)  throws ServiceException;
}
