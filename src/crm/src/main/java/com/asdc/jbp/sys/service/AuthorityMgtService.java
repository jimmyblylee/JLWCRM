/**
 * Project Name : jbp-features-sys <br>
 * File Name : AuthorityMgtService.java <br>
 * Package Name : com.asdc.jbp.sys.service <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.service;

import java.util.List;

import com.asdc.jbp.framework.dao.Parameter;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.sys.entity.RelSysRoleFunc;
import com.asdc.jbp.sys.entity.SysFunc;
import com.asdc.jbp.sys.entity.SysGroup;
import com.asdc.jbp.sys.entity.SysRole;
import com.asdc.jbp.sys.entity.SysUser;

/**
 * ClassName : AuthorityMgtService <br>
 * Description : 提供 用户组、角色、功能、用户查询功能 <br>
 * 
 * <table style='border:1px solid black;border-collapse:collapse;'>
 * 
 * <tr style='background-color: black; color:white;font-weight:600;'>
 * <th width='80px'>Main</th>
 * <th width='120px'>Group</th>
 * <th width='180px'>Role</th>
 * <th width='160px'>User</th>
 * <th width='160px'>Func</th>
 * </tr>
 * 
 * <tr style='background-color: #ddd;'>
 * <td><b>Group</b></td>
 * <td>
 * <li>getGroupById</li>
 * <li>createGourp</li>
 * <li>updateGroup</li>
 * <li>removeGroupById</li>
 * <li>removeGroupByIds</li></td>
 * <td>
 * <li>queryRolesByGroupId</li>
 * <li>replaceRolesToGroup</li>
 * <li>assignRolesToGroup</li>
 * <li>removeRolesFromGroup</li></td>
 * <td>
 * <li>queryUsersByGroupId</li>
 * <li>replaceUsersToGroup</li>
 * <li>assignUsersToGroup</li>
 * <li>removeUsersFromGroup</li></td>
 * <td>
 * <li>queryFuncsByGroupId</li></td>
 * </tr>
 * 
 * <tr>
 * <td><b>Role</b></td>
 * <td>
 * <li>queryGroupsByRoleId</li></td>
 * <td>
 * <li>getRoleById</li>
 * <li>createRole</li>
 * <li>updateRole</li>
 * <li>removeRoleById</li>
 * <li>removeRoleByIds</li>
 * <li>queryAllRole</li></td>
 * <td>
 * <li>queryUsersByRoleId</li></td>
 * <td>
 * <li>queryFuncsByRoleId</li>
 * <li>replaceFuncsToRole</li>
 * <li>assignFuncsToRole</li>
 * <li>removeFuncsFromRole</li></td>
 * </tr>
 * 
 * <tr style='background-color: #ddd;'>
 * <td><b>User</b></td>
 * <td>
 * <li>queryGroupsByUserId</li></td>
 * <td>
 * <li>queryRolesByUserId</li>
 * <li>replaceRolesToUser</li>
 * <li>assignRolesToUser</li>
 * <li>removeRolesFromUser</li></td>
 * <td></td>
 * <td>
 * <li>queryFuncsByUserId</li></td>
 * </tr>
 * 
 * <tr>
 * <td><b>Func</b></td>
 * <td>
 * <li>queryGroupsByFuncId</li></td>
 * <td>
 * <li>queryRolesByFuncId</li></td>
 * <td>
 * <li>queryUsersByFuncId</td>
 * <td>
 * <li>getFuncById</li>
 * <li>createFunc</li>
 * <li>updateFunc</li>
 * <li>removeFuncById</li>
 * <li>removeFuncByIds</li></td>
 * </tr>
 * </table>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public interface AuthorityMgtService extends AuthorizationService {

    // ################
    // Main Group vs. Group Role User and Func
    // ################

    //
    // ### Main Group vs. Group
    //
    /**
     * Description : 根据组ID获取组信息 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param groupId 用户组id
     * @return 指定id查询出的数据
     * @throws ServiceException
     *             <li>ERR_SYS_023: id不能为空</li>
     *             <li>ERR_SYS_023: 无法根据指定id找到信息</li>
     */
    public SysGroup getGroupById(Integer groupId) throws ServiceException;

    /**
     * Description : 添加新的用户组 ,若isEnabled为空,则设置默认值<br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param group
     * @return persisted SysGroup
     * @throws ServiceException
     *             <li>ERR_SYS_021: 参数group为空</li>
     */
    public SysGroup createGroup(SysGroup group) throws ServiceException;

    /**
     * Description : 更新用户组信息 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param group
     * @return persisted SysGroup
     * @throws ServiceException
     *             <li>ERR_SYS_021: 参数group不能为空</li>
     *             <li>ERR_SYS_023: groupId 不能为空</li>
     *             <li>ERR_SYS_023: 无法根据指定id找到信息</li>
     */
    public SysGroup updateGroup(SysGroup group) throws ServiceException;

    /**
     * Description : 根据ID删除信息 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param groupId 用户组ID
     * @throws ServiceException
     *             <li>ERR_SYS_023: id不能为空</li>
     *             <li>ERR_SYS_023: 无法根据指定id找到信息</li>
     */
    public void removeGroupById(Integer groupId) throws ServiceException;

    /**
     * Description : 根据ID集合删除信息 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param groupIds id集合
     * @throws ServiceException
     *             <li>ERR_SYS_023: id不能为空</li>
     *             <li>ERR_SYS_023: 无法根据指定id找到信息</li>
     */
    public void removeGroupByIds(List<Integer> groupIds) throws ServiceException;

    //
    // ### Main Group vs. Role
    //
    /**
     * Description : 根据用户组ID查询对应的角色 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param groupId 用户组ID
     * @return 指定的角色信息
     * @throws ServiceException
     *             <li>ERR_SYS_023: id不能为空</li>
     *             <li>ERR_SYS_023: 无法根据指定id找到信息</li>
     */
    public List<SysRole> queryRolesByGroupId(Integer groupId) throws ServiceException;

    /**
     * Description : 删除原来的角色信息，然后根据roleIds进行更新<br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param groupId 用户组ID
     * @param roleIds 新的角色信息ID
     * @throws ServiceException
     *             根据groupId查询不到group信息或根据roleId查询不到角色信息
     */
    public void replaceRolesToGroup(Integer groupId, List<Integer> roleIds) throws ServiceException;

    /**
     * Description : 根据roleIds分配组角色，如果已存在则删除旧数据创建新的
     * relation data for auditing. <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param groupId
     * @param roleIds
     * @throws ServiceException
     *              根据groupId查询不到group信息或根据roleId查询不到角色信息
     */
    public void assignRolesToGroup(Integer groupId, List<Integer> roleIds) throws ServiceException;

    /**
     * Description : 删除组和角色之间的对应关系
     * will be ignore. <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param groupId
     * @param roleIds
     * @throws ServiceException
     *            根据groupId查询不到group信息或根据roleId查询不到角色信息
     */
    public void removeRolesFromGroup(Integer groupId, List<Integer> roleIds) throws ServiceException;

    // ### Main Group vs. User
    /**
     * Description : 根据groupId查询用户 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param groupId
     * @return 指定ID查询出的用户信息
     * @throws ServiceException
     *             无法根据ID查询出组信息
     */
    public List<SysUser> queryUsersByGroupId(Integer groupId) throws ServiceException;

    /**
     * Description : 更新用户分组信息 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param groupId
     * @param userIds
     * @throws ServiceException
     *             无法根据groupID找到组信息或无法根据userid找到用户信息
     */
    public void replaceUsersToGroup(Integer groupId, List<Integer> userIds) throws ServiceException;

    /**
     * Description : 根据组ID分配用户，若该组中已存在用户则删除旧数据添加新数据
     * relation data for auditing. <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param groupId
     * @param userId
     * @throws ServiceException
     *             无法根据制定参数找到相关信息
     */
    public void assignUsersToGroup(Integer groupId, List<Integer> userId) throws ServiceException;

    /**
     * Description : 删除用户与组之间的对应关系
     * be ignore. <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param groupId
     * @param userIds
     * @throws ServiceException
     *             无法根据groupID找到组信息或无法根据userid找到用户信息
     */
    public void removeUsersFromGroup(Integer groupId, List<Integer> userIds) throws ServiceException;

    // ####### Main Group vs. Func
    /**
     * Description : 根据groupId查询系统功能 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param groupId
     * @return SysFunc list by given group id
     * @throws ServiceException
     *             无法根据指定Id找到信息
     */
    public List<SysFunc> queryFuncsByGroupId(Integer groupId) throws ServiceException;

    // ################
    // Main Role vs. Group Role User and Func
    // ################

    //
    // ### Main Role vs. Group
    //
    /**
     * Description : 根据角色id查询组信息 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param roleId
     * @return SysGroup list by given role id
     * @throws ServiceException
     *             无法根据指定Id找到信息
     */
    public List<SysGroup> queryGroupsByRoleId(Integer roleId) throws ServiceException;

    //
    // ### Main Role vs. Role
    //
    /**
     * Description : 根据roleId查询角色信息 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param roleId
     * @return SysRole by given id
     * @throws ServiceException
     *             <li>ERR_SYS_033: roleId不能为空</li>
     *             <li>ERR_SYS_033: 无法根据指定Id找到信息</li>
     */
    public SysRole getRoleById(Integer roleId) throws ServiceException;

    /**
     * Description : 添加角色信息
     *  <li>默认情况下isBaseRoel会设置为默认值 false</li>
     *  <li>如果isEnabled为空，则设置为默认值 false</li>
     * false as default <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param role
     * @return persisted SysRole
     * @throws ServiceException
     *             <li>ERR_SYS_021: 用户组信息为空</li>
     */
    public SysRole createRole(SysRole role) throws ServiceException;

    /**
     * Description : 更新角色信息
     *  <li>默认情况下isBaseRoel会设置为默认值 false</li>
     *  <li>如果isEnabled为空，则设置为默认值 false</li>
     * with false as default <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param role
     * @return persisted SysRole
     * @throws ServiceException
     *             <li>ERR_SYS_032: 参数role不能为空</li>
     *             <li>ERR_SYS_033: roleId不能为空</li>
     *             <li>ERR_SYS_033: 无法根据指定Id找到信息</li>
     */
    public SysRole updateRole(SysRole role) throws ServiceException ;

    /**
     * Description : 根据id删除单条角色信息<br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param roleId
     * @throws ServiceException
     *             <li>ERR_SYS_033: roleId不能为空</li>
     *             <li>ERR_SYS_033: 无法根据指定Id找到信息</li>
     */
    public void removeRoleById(Integer roleId) throws ServiceException;

    /**
     * Description : 根据ID列表删除多条数据 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param roleIds
     * @throws ServiceException
     *             <li>ERR_SYS_033: roleId不能为空</li>
     *             <li>ERR_SYS_033: 无法根据指定Id找到信息</li>
     */
    public void removeRoleByIds(List<Integer> roleIds) throws ServiceException;

    //
    // ### Main Role vs. User
    //
    /**
     * Description : 根据roleId查询用户信息 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param roleId
     * @return 指定角色ID的用户信息
     * @throws ServiceException
     *             无法根据制定角色ID找到用户信息
     */
    public List<SysUser> queryUsersByRoleId(Integer roleId) throws ServiceException;

    //
    // ### Main Role vs. Func
    //
    /**
     * Description : 根据角色ID查询功能列表 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param roleId
     * @return SysFunc list of role
     * @throws ServiceException
     *             无法根据制定角色ID找到相关信息
     */
    public List<SysFunc> queryFuncsByRoleId(Integer roleId) throws ServiceException;

    /**
     * Description : 根据roleId更新功能 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param roleId
     * @param funcIds
     * @throws ServiceException
     *             无法根据ID查询到RoleInfo或无法根据funcId查询到功能信息
     */
    public void replaceFuncsToRole(Integer roleId, List<Integer> funcIds) throws ServiceException;

    /**
     * Description : 为角色分配新的功能，若以存在则替换掉
     * relation data for auditing. <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param roleId
     * @param funcIds
     * @throws ServiceException
     *              无法根据ID查询到RoleInfo或无法根据funcId查询到功能信息
     */
    public void assignFuncsToRole(Integer roleId, List<Integer> funcIds) throws ServiceException;

    /**
     * Description : 根据roleId删除所拥有功能
     * ignore. <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param roleId
     * @param funcIds
     * @throws ServiceException
     *             无法根据ID查询到RoleInfo或无法根据funcId查询到功能信息
     */
    public void removeFuncsFromRole(Integer roleId, List<Integer> funcIds) throws ServiceException;

    // ################
    // Main User vs. Group, Role and Func
    // ################

    //
    // ### Main User vs. Group
    //
    /*
     * (non-Javadoc)
     * 
     * @see com.asdc.jbp.sys.service.AuthorizationService#queryGroupsByUserId(java.lang.Integer)
     */
    public List<SysGroup> queryGroupsByUserId(Integer userId) throws ServiceException;

    //
    // ### Main User vs. Role
    //
    /*
     * (non-Javadoc)
     * 
     * @see com.asdc.jbp.sys.service.AuthorizationService#queryRolesByUserId(java.lang.Integer)
     */
    public List<SysRole> queryRolesByUserId(Integer userId) throws ServiceException;

    /**
     * Description : 根据userid删除原有角色Id 替换为新角色
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param userId
     * @param roleIds
     * @throws ServiceException
     *             无法根据userid查询到用户信息或无法根据roleid查询到角色信息
     */
    public void replaceRolesToUser(Integer userId, List<Integer> roleIds) throws ServiceException;

    /**
     * Description : 为用户分配角色信息，若已存在则直接更新
     * relation data for auditing. <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param userId
     * @param roleIds
     * @throws ServiceException
     *              无法根据userid查询到用户信息或无法根据roleid查询到角色信息
     */
    public void assignRolesToUser(Integer userId, List<Integer> roleIds) throws ServiceException;

    /**
     * Description : 根据userid删除用户角色
     * ignore. <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param userId
     * @param roleIds
     * @throws ServiceException
     *             无法根据userid查询到用户信息或无法根据roleid查询到角色信息
     */
    public void removeRolesFromUser(Integer userId, List<Integer> roleIds) throws ServiceException;

    //
    // ### Main User vs. Func
    //
    /*
     * (non-Javadoc)
     * 
     * @see com.asdc.jbp.sys.service.AuthorizationService#queryFuncsByUserId(java.lang.Integer)
     */
    public List<SysFunc> queryFuncsByUserId(Integer userId) throws ServiceException;

    // ################
    // Main Func vs. Group Role User and Func
    // ################

    //
    // ### Main Func vs. Group
    //
    /**
     * Description : 根据funcId查询组信息<br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param funcId
     * @return SysGroup list of SysFunc
     * @throws ServiceException
     *             无法根据funcid查询到信息
     */
    public List<SysGroup> queryGroupsByFuncId(Integer funcId) throws ServiceException;

    //
    // ### Main Func vs. Role
    //
    /**
     * Description : 根据funcId查询角色信息 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param funcId
     * @return SysRole list of SysFunc
     * @throws ServiceException
     *             无法根据funcid查询到信息
     */
    public List<SysRole> queryRolesByFuncId(Integer funcId) throws ServiceException;

    //
    // ### Main Func vs. User
    //
    /**
     * Description : 根据funcId查询用户信息
     * this role <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param funcId
     * @return list of SysUser
     * @throws ServiceException
     *             无法根据funcid查询到信息
     */
    public List<SysUser> queryUsersByFuncId(Integer funcId) throws ServiceException;

    //
    // ### Main Func vs. Func
    //
    /**
     * Description : 根据funcID查询功能详细 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param funcId
     * @return SysFunc by given id
     * @throws ServiceException
     *             <li>ERR_SYS_043: id不能为空</li>
     *             <li>ERR_SYS_043: 无法根据指定ID查询到功能信息</li>
     */
    public SysFunc getFuncById(Integer funcId) throws ServiceException;

    /**
     * Description : 添加新功能 <br>
     * <b>BE CARE</b>:
     * <li>若isVisable为空,则为其设置为默认值"true";</li>
     * <li>若isEnabled为空,则为其设置为默认值"false";</li>
     * <li>isBaseFunc在任何情况下都会被设置为默认值“false”!</li><br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param func
     * @return psersisted SysFunc
     * @throws ServiceException 如果添加失败，则请看下面错误信息
     *             <li>ERR_SYS_043: ID不能为空</li>
     *             <li>ERR_SYS_043: 无法根据ID找到信息</li>
     *             <li>ERR_SYS_041: 参数code不能为空</li>
     *             <li>ERR_SYS_041: 参数name不能为空</li>
     *             <li>ERR_SYS_041: 参数type不能为空</li>
     *             <li>ERR_SYS_041: 如果type是一个链接LIKE,则url不能为空</li>
     */
    public SysFunc createFunc(SysFunc func) throws ServiceException;

    /**
     * Description : 更新功能信息 <br>
     * <b>BE CARE</b>:
     * <li>若isVisable为空,则为其设置为默认值"true";</li>
     * <li>若isEnabled为空,则为其设置为默认值"false";</li>
     * <li>isBaseFunc在任何情况下都会被设置为默认值“false”!</li><br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param func
     * @return persisted SysFunc
     * @throws ServiceException 如果更新失败，则请看下面错误信息
     *             <li>ERR_SYS_043: ID不能为空</li>
     *             <li>ERR_SYS_043: 无法根据ID找到信息</li>
     *             <li>ERR_SYS_041: 参数code不能为空</li>
     *             <li>ERR_SYS_041: 参数name不能为空</li>
     *             <li>ERR_SYS_041: 参数type不能为空</li>
     *             <li>ERR_SYS_041: 如果type是一个链接LIKE,则url不能为空</li>
     */
    public SysFunc updateFunc(SysFunc func) throws ServiceException;

    /**
     * Description : 根据funcId删除一条功能信息<br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param funcId
     * @throws ServiceException
     *             <li>ERR_SYS_043: ID不能为空</li>
     *             <li>ERR_SYS_043: 无法根据ID找到信息</li>
     */
    public void removeFuncById(Integer funcId) throws ServiceException;

    /**
     * Description : 根据funcIds删除多条条功能信息<br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param funcIds
     * @throws ServiceException
     *             <li>ERR_SYS_043: ID不能为空</li>
     *             <li>ERR_SYS_043: 无法根据ID找到信息</li>
     */
    public void removeFuncByIds(List<Integer> funcIds) throws ServiceException;
    

    /** 
     * Description： 根据queryName查询制定信息
     * @param queryName hsqlName
     * @param start 开始与 默认0
     * @param limit 查询{limit}条数据 如果limit为0，则添加初始值5 若为-1则查询所有
     * @param pageParams 其他查询条件
     * @throws ServiceException
     * @return List<?>
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    public List<?> queryInfoByPage(String queryName, int start, int limit,List<Parameter> params) throws ServiceException;
    /** 
     * Description：根据queryName查询制定信息
     * @param start 开始与 默认0
     * @param limit 查询{limit}条数据 如果limit为0，则添加初始值20
     * @throws ServiceException
     * @return List<SysGroup>
     * @author name：haotian_yang <br>email: haotian_yang@asdc.com.cn
     **/
    public List<?> queryGroup(String queryName, int start, int limit,String queryCondition) throws ServiceException;
    /** 
     * Description：获取某表数据总数总数
     * @param queryName HsqlName
     * @param params 其他查询条件
     * @throws ServiceException
     * @return Integer
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    public Integer queryTotle(String queryName, List<Parameter> params) throws ServiceException;
    
    public List<SysFunc> queryAllSysFuncGroup();
    
    /** 
     * Description：批量更新用户角色
     * @param userId
     * @param roleIds
     * @throws ServiceException
     * @return void
     * @author name：yuruixin
     **/
    public void replaceRolesToUsers(List<Integer> userId, List<Integer> roleIds) throws ServiceException;

    public int queryGroupCountByQueryCondtion(String queryCondition) throws ServiceException;
    /** 
     * Description：根据角色名称查询角色信息
     * @param roleName 角色名称
     * @throws ServiceException
     * @return List<{@link SysRole}>
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    public List<SysRole> queryRoleByRoleName(String roleName) throws ServiceException;
    
    /** 
     * Description：根据功能ID查询角色与功能对应关系
     * @param funcId
     * @throws ServiceException
     * @return List<RelSysRoleFunc>
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    public List<RelSysRoleFunc> queryRelRoleFuncInfoByFuncId(Integer funcId) throws ServiceException;

    /** 
     * Description：查询所有功能
     * @throws ServiceException
     * @return List<SysFunc>
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    public List<SysFunc> queryAllParentSysFuncGroup() throws ServiceException;

    /** 
     * Description： 根据角色ID更新用户
     * @param userIds
     * @param roleIds
     * @return void
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     * @throws ServiceException 
     **/
    public void replaceUsersToRole(List<Integer> userIdList, Integer roleId) throws ServiceException;
    
    /*
     * Description：下拉菜单
     * @param funcId
     * @throws ServiceException
     * @return void
     * @author name：zhoushuai <br>email:shuai_zhou@asdc.com.cn
     **/

	List<SysFunc> queryParentByFuncId(Integer funcId) throws ServiceException;

	public List<SysFunc> queryChildren(Integer funcId) throws ServiceException;




	List<SysFunc> getSysFuncName(SysFunc sysFunc,int start, int limit) throws ServiceException;



	int getSysFuncNameCount(SysFunc func) throws ServiceException;



	int getSysFuncParentCount(SysFunc sysFunc) throws ServiceException;

	public List<SysFunc> getSysFuncParent(SysFunc sysFunc, int start, int limit) throws ServiceException;

	/** 
	 * Description：批量更新用户的用户组信息
	 * @param groupIds
	 * @param userIds
	 * @throws ServiceException
	 * @return void
	 * @author name：yuruixin
	 **/
	public void replaceUsersToGroups(List<Integer> groupIds, List<Integer> userIds) throws ServiceException;
    
	/** 
	 * Description：更新某一用户的用户组信息
	 * @param userId
	 * @param groupIds
	 * @throws ServiceException
	 * @return void
	 * @author name：yuruixin
	 **/
	public void replaceUserToGroups(Integer userId, List<Integer> groupIds) throws ServiceException;

    /** 
     * Description：恢复角色信息
     * @param roleList role id list
     * @throws ServiceException
     * @return void
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    public void recoverRoleInfo(List<Integer> roleList) throws ServiceException;
    
    /** 
     * Description：恢复功能信息
     * @param roleList role id list
     * @throws ServiceException
     * @return void
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    public void recoverFuncInfo(List<Integer> funcList) throws ServiceException;
    /**
     * 
     * Description:通过菜单id判断该菜单是否存在下级菜单
     * @param funcParentId
     * @return
     * @throws ServiceException
     * @return boolean
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     *
     */
    public boolean getChildrenCountByParentId(int funcParentId) throws ServiceException;
    /**
     * 
     * Description：查询上级菜单为null的菜单（为了和机构、字典统一。这里采用null来查找。和字典一样为空的只有一个）
     * @return
     * @throws ServiceException
     * @return SysFunc
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     *
     */
    public List<SysFunc> getFuncByParentIsNull() throws ServiceException;
    /**
     * 
     * Description：菜单查询不需要使用分页参数
     * @param queryName
     * @param params
     * @return
     * @throws ServiceException
     * @return List<?>
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     *
     */
    public List<?> queryInfoNoPage(String queryName,List<Parameter> params)  throws ServiceException;
    
    /**
     * 
     * Description：根据用户账号判断是否已经存在此账号了！
     * @param account
     * @return
     * @throws ServiceException
     * @return boolean
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     *
     */
    public SysUser queryUserIdByAccont(String account) throws ServiceException;

	public boolean queryUserByDeptId(int deptid)throws ServiceException;
    /**
     * 
     * Description：根据code来判断是否存在此菜单了，存在就不能添加了
     * @param code
     * @return
     * @return SysFunc
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     *
     */
	public SysFunc queryFuncByCode(String code)throws ServiceException;

    
}
