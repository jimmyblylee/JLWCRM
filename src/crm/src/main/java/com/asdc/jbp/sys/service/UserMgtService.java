/*
 * Project Name : jbp-features-sys <br>
 * File Name : UserMgtService.java <br>
 * Package Name : com.asdc.jbp.sys.service <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.sys.entity.SysUser;

/**
 * ClassName : UserMgtService <br>
 * Description : 用户管理<br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@SuppressWarnings("unused")
public interface UserMgtService {

    /**
     * Description : 添加用户信息
     * <li>若密码为空，则添加初始密码 ChangeMe</li>
     * <li>若isEnabled为空, 则添加默认值"1"</li>
     * <li>若user_type为空, 则添加默认值"NORMAL"</li> <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return 创建后用户信息
     * @throws ServiceException
     *             <li>ERR_SYS_001: 用户不能为空！</li>
     *             <li>ERR_SYS_003: 账号不能为空！</li>
     *             <!--<li>ERR_SYS_003: can not find SysUser with given account</li>-->
     *             <li>ERR_SYS_003: 系统中已存在一个相同账号的账户！</li>
     */
    SysUser createSysUser(SysUser user) throws ServiceException;

    /**
     * Description : 更新用户信息 <br>
     * <li>若isEnabled为空, 则添加默认值"1"</li>
     * <li>若user_type为空, 则添加默认值"NORMAL"</li><br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return 更新后用户信息
     * @throws ServiceException
     *             <li>ERR_SYS_002: 用户不能为空 ！</li>
     *             <li>ERR_SYS_003: 账号不能为空！</li>
     *             <li>ERR_SYS_003: 无法根据指定账号找到用户信息！</li>
     *             <li>ERR_SYS_003: 根据制定账号查询出多条用户信息！</li>
     *             <li>ERR_SYS_002: 系统中不存在指定账号的用户信息！</li>
     */
    SysUser updateSysUser(SysUser user) throws ServiceException;

    /**
     * Description : 根据userId更新用户头像 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param userId 用户ID
     * @param base64PhotoStr base64编码后的照片
     * @throws ServiceException
     *             <li>ERR_SYS_003: 用户id不能为空</li>
     *             <li>ERR_SYS_003: 无法根据制定ID找到用户信息</li>
     */
    void updateUserPhoto(Integer userId, String base64PhotoStr) throws ServiceException;

    /**
     * Description : 根据userId获取用户头像 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param userId 用户ID
     * @return 照片流/null
     * @throws ServiceException
     *             <li>ERR_SYS_003: 用户id不能为空</li>
     *             <li>ERR_SYS_003: 无法根据制定ID找到用户信息</li>
     */
    String getUserPhoto(Integer userId) throws ServiceException, UnsupportedEncodingException;

    /**
     * Description : 禁用用户 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @throws ServiceException
     *             <li>ERR_SYS_003: 用户ID或ids不能为空</li>
     *             <li>ERR_SYS_003: 无法根据制定ID找到用户信息</li>
     */
    void removeSysUser(List<Integer> ids) throws ServiceException;

    /**
     * Description : 根据userId查询用户信息<br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param id 用户ID
     * @return 根据指定ID查询出的用户信息
     * @throws ServiceException
     *             <li>ERR_SYS_003: 用户id不能为空</li>
     *             <li>ERR_SYS_003: 无法根据制定ID找到用户信息</li>
     */
    SysUser getSysUserById(Integer id) throws ServiceException;

    /**
     * Description : 根据账号查询用户ID <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param account 用户账号
     * @return 指定账号对应的用户id
     * @throws ServiceException
     *             <li>ERR_SYS_003: 账号不能为空</li>
     *             <li>ERR_SYS_003: 无法根据指定账号找到用户信息</li>
     *             <li>ERR_SYS_003: 系统中存在多个相同账号的信息</li>
     */
    Integer getSysUserIdByUserAccount(String account) throws ServiceException;

    /**
     * Description : 根据邮箱查询用户ID <br>
     * Create Time: June 17, 2016 <br>
     * Create by : jing_liu@asdc.com.cn <br>
     *
     * @param email 用户账号
     * @return 指定账号对应的用户id
     * @throws ServiceException
     *             <li>ERR_SYS_003: 邮箱不能为空</li>
     *             <li>ERR_SYS_003: 无法根据指定邮箱找到用户信息</li>
     *             <li>ERR_SYS_003: 系统中存在多个相同邮箱的信息</li>
     */
    Integer getSysUserIdByUserEmail(String email) throws ServiceException;

    /**
     * Description : 根据ID重置密码 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param userId 用户ID
     * @param password 新密码
     * @throws ServiceException
     *             <li>ERR_SYS_003: 用户id不能为空</li>
     *             <li>ERR_SYS_003: 无法根据id找到用户信息</li>
     */
    void resetUserPassword(Integer userId, String password) throws ServiceException;

    /**
     * Description : 修改当前登录账号的密码<br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param password 新密码
     * @throws ServiceException
     *             <li>ERR_SYS_003: 用户id不能为空</li>
     *             <li>ERR_SYS_003: 无法根据id找到用户信息</li>
     *             <li>ERR_SYS_002: 无法获取当前会话中的token</li>
     *             <li>ERR_SYS_002: 匿名用户无法修改密码</li>
     */
    void resetMyPassword(String password) throws ServiceException;

    /**
     * Description : 随机生成用户密码 <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param userId 用户id
     * @return 随机密码
     * @throws ServiceException
     *             <li>ERR_SYS_003: 用户id不能为空</li>
     *             <li>ERR_SYS_003: 无法根据id找到用户信息</li>
     *             <li>ERR_SYS_002: 匿名用户无法修改密码</li>
     */
    String generateUserPassword(Integer userId) throws ServiceException;

    /**
     * Description : 校验登陆账号是否已存在于数据库<br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param account 用户账号
     * @return true 存在
     */
    boolean validateAccountIsInDBorNot(String account);

    /**
     * Description：通过部门，账号，姓名查询用户信息。若某属性为空则取消这一条件
     * @author name：yuruixin
     **/
    List<SysUser> getSysUsersByDeptAndAccountAndName(SysUser sysUser, int start, int limit) throws ServiceException;

    /**
	 * Description：获取符合查询条件的用户总数
	 * @author name：yuruixin
	 **/
    int getSysUsersByDeptAndAccountAndNameCount(SysUser sysUser) throws ServiceException;

    /**
     * Description：修改用户头像，个人信息及所属用户组
     * @author name：yuruixin
     **/
    SysUser updateSysUserInfo(SysUser user) throws ServiceException;

    /**
     * Description：创建用户
     * @author name：yuruixin
     **/
    SysUser creatSysUserInfo(SysUser user) throws ServiceException;

    /**
     * Description：根据用户Id获取用户信息
     * @author name：yuruixin
     **/
    SysUser getUserById(Integer id) throws ServiceException;

    /**
     * Description：恢复用户
     * @author name：yuruixin
     **/
    void repeatSysUser(List<Integer> ids) throws ServiceException;
    /**
     *
     * Description：用与验证邮件是否已注册
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     *
     */
    Integer getCountByEmail(String email) throws ServiceException;

    SysUser registerSysUser(SysUser sysUser) throws ServiceException;

    SysUser creatSysUser(SysUser sysUser) throws ServiceException;

	SysUser updateSysUserPassword(SysUser sysUser) throws ServiceException;

	SysUser updateSysUserLastTime(SysUser user) throws ServiceException;

	 SysUser  getUserByName(String username) throws ServiceException;
}
