/**
 * Project Name : jbp-features-sys <br>
 * File Name : UserController.java <br>
 * Package Name : com.asdc.jbp.sys.controller <br>
 * Create Time : May 1, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.controller;

import java.beans.IntrospectionException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asdc.jbp.dict.entity.SysDict;
import com.asdc.jbp.dict.service.DictService;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.utils.ProxyStripper;
import com.asdc.jbp.sys.entity.SysGroup;
import com.asdc.jbp.sys.entity.SysUser;
import com.asdc.jbp.sys.service.AuthorityMgtService;
import com.asdc.jbp.sys.service.DeptMgtService;
import com.asdc.jbp.sys.service.GroupMgtService;
import com.asdc.jbp.sys.service.UserMgtService;
/**
 * ClassName : UserController <br>
 * Description : 用户管理控制器 <br>
 * Create Time : May 1, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Controller("UserController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@SuppressWarnings("unchecked")
public class UserController extends ControllerHelper {
    @Resource
    private UserMgtService userService;
    @Resource
    private DictService dictService;
    @Resource
    private AuthorityMgtService authService;
    @Resource
    private GroupMgtService groupService;
    @Resource
    private DeptMgtService deptService;

    /**
     * Description：通过查询条件获取用户信息，并返回至页面
     *
     * @throws ServiceException
     * @throws IntrospectionException
     * @return void
     * @author name：yuruixin
     **/
    public void querySysUserByDeptAndAccountAndName() throws ServiceException, IntrospectionException{
        SysUser sysUser = workDTO.convertJsonToBeanByKey("pageQuery", SysUser.class);
        int totle = userService.getSysUsersByDeptAndAccountAndNameCount(sysUser);
        List<SysUser> userList = userService.getSysUsersByDeptAndAccountAndName(sysUser, workDTO.getStart(), workDTO.getLimit());
        workDTO.setTotle(totle);
        workDTO.setResult(ProxyStripper.cleanFromProxies(userList));
    }

    /**
     * Description：更新用户信息
     *
     * @throws ServiceException
     * @return void
     * @author name：yuruixin
     **/
    public void updateUser() throws ServiceException {
        SysUser sysUser = (SysUser) workDTO.convertJsonToBeanByKey("pageQuery", SysUser.class);
        userService.updateSysUserInfo(sysUser);
        workDTO.setResult(sysUser.getAccount());
    }
    /**
     *
     * Description：修改用户密码
     * @throws ServiceException
     * @return void
     *
     */
    public void updateSysUserPassword() throws ServiceException{
        SysUser sysUser = (SysUser) workDTO.convertJsonToBeanByKey("pageQuery", SysUser.class);
        userService.updateSysUserPassword(sysUser);
        workDTO.setResult(sysUser.getAccount());
    }
    /**
     *
     * Description：修改用户头像
     * @throws ServiceException
     * @return void
     *
     */
    public void updateUserPhoto() throws ServiceException{
        SysUser sysUser = (SysUser) workDTO.convertJsonToBeanByKey("pageQuery", SysUser.class);
        userService.updateSysUserPassword(sysUser);
        workDTO.setResult(sysUser.getAccount());
    }


    /**
     * Description：用户恢复
     * @throws ServiceException
     * @return void
     * @author name：yuruixin
     **/
    public void repeatUser() throws ServiceException {
        Map<String, Object> map = workDTO.convertJsonToMapByKey("pageQuery");
        List<Integer> userIds =(List<Integer>) map.get("userIdJson");
        userService.repeatSysUser(userIds);
        workDTO.setResult(true);
    }

    /**
     * Description：用户添加
     * @throws ServiceException
     * @return void
     * @author name：yuruixin
     **/
    public void addUser() throws ServiceException {
        SysUser sysUser = (SysUser) workDTO.convertJsonToBeanByKey("pageQuery", SysUser.class);
        userService.creatSysUserInfo(sysUser);
        workDTO.setResult(sysUser.getAccount());
    }
    /**
     * Description：根据用户id查询所在用户组信息
     *
     * @throws ServiceException
     * @return void
     * @author name：yuruixin
     **/
    public void queryGroupsByUserId() throws ServiceException {
        Integer userId = workDTO.getLimit();
        List<SysGroup> groupList = authService.queryGroupsByUserId(userId);
        workDTO.setResult(groupList);
    }

    /**
     * Description：获取所有用户组
     *
     * @return void
     * @author name：yuruixin
     **/
    public void queryAllGroup() {
        List<SysGroup> groupList = groupService.queryAllSysGroup();
        workDTO.setResult(groupList);
    }

    /**
     * Description：批量更新用户的用户组信息
     *
     * @throws ServiceException
     * @return void
     * @author name：yuruixin
     **/
    public void replaceUserGroup() throws ServiceException {
        Map<String, Object> map = workDTO.convertJsonToMapByKey("pageQuery");
        List<Integer> userIds = (List<Integer>) map.get("userIdJson");
        List<Integer> groupIds = (List<Integer>) map.get("groupIdJson");
        authService.replaceUsersToGroups(groupIds, userIds);
        workDTO.setResult(true);
    }

    /**
     * Description：批量删除用户
     *
     * @throws ServiceException
     * @return void
     * @author name：yuruixin
     **/
    public void deleteUser() throws ServiceException {
        Map<String, Object> map = workDTO.convertJsonToMapByKey("pageQuery");
        List<Integer> userIds =(List<Integer>) map.get("userIdJson");
        userService.removeSysUser(userIds);
        workDTO.setResult(true);
    }


    /**
     * Description：更新个人信息
     * @throws ServiceException
     * @return void
     * @author name：liujing
     **/
    public void updatePersonal() throws ServiceException {
        SysUser sysUser = (SysUser) workDTO.convertJsonToBeanByKey("sysUser", SysUser.class);
        userService.updateSysUser(sysUser);
        workDTO.setResult(sysUser);
    }

    /**
     * Description：获取个人信息
     * @throws ServiceException
     * @return void
     * @author name：liujing
     **/
    public void getPersonInfo() throws ServiceException, IntrospectionException {
        String userId=workDTO.get("userId").toString().replace("\"", "");
        int sysUserId=Integer.valueOf(userId);
        SysUser sysUser=userService.getUserById(sysUserId);
        List<SysUser> userList = new ArrayList<SysUser>();
        userList.add(sysUser);
        workDTO.setResult(ProxyStripper.cleanFromProxies(userList));
    }

    /**
     * Description：得到用户头像信息
     * @throws ServiceException
     * @return void
     * @author name：liujing
     * @throws UnsupportedEncodingException
     **/
    public void getUserPhoto() throws ServiceException, IntrospectionException, UnsupportedEncodingException {
        Integer userId=workDTO.getLimit();
        String getUser=userService.getUserPhoto(userId);
        workDTO.setResult(getUser);
    }

    /**
     * Description：数据字典查询
     * @throws ServiceException
     * @return void
     * @author name：yuruixin
     * @throws IntrospectionException
     **/
    public void getAllDict() throws ServiceException, IntrospectionException {
        List<SysDict> dictList = dictService.queryAllDicts(0, -1);
        workDTO.setResult(ProxyStripper.cleanFromProxies(dictList));
    }

    /**
     * Description：
     * @param userService
     * @return void
     * @author name：yuruixin
     **/
    public void setUserService(UserMgtService userService) {
        this.userService = userService;
    }

    /**
     * Description：
     * @return void
     * @author name：yuruixin
     **/
    public void setAuthService(AuthorityMgtService authService) {
        this.authService=authService;
    }

    /**
     * @param dictService the dictService to set
     */
    public void setDictService(DictService dictService) {
        this.dictService = dictService;
    }

    /**
     * @param groupService the groupService to set
     */
    public void setGroupService(GroupMgtService groupService) {
        this.groupService = groupService;
    }

    /**
     * @param deptService the deptService to set
     */
    public void setDeptService(DeptMgtService deptService) {
        this.deptService = deptService;
    }

    /**
     * Description：获取对应字典表信息
     * @throws ServiceException
     * @return void
     * @author name：yuruixin
     * @throws IntrospectionException
     **/
    public void queryDictsByNature() throws ServiceException, IntrospectionException {
        String dictNature = workDTO.get("pageQuery");
        List<SysDict> dictList = dictService.queryDictsByNature(dictNature);
        workDTO.setResult(ProxyStripper.cleanFromProxies(dictList));
    }
    /**
     *
     * Description：通过账户来判断用户是否存在
     * @throws ServiceException
     * @return void
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     * @throws IntrospectionException
     *
     */
    public void queryUserIdByAccont() throws ServiceException, IntrospectionException{
        String account = workDTO.get("userAccount").toString().replace("\"", "");
        SysUser  user = authService.queryUserIdByAccont(account);
        workDTO.setResult(ProxyStripper.cleanFromProxies(user));
    }
    /**
     *
     * Description：通过deptId判断用户是否存在
     * @throws ServiceException
     * @throws IntrospectionException
     * @return void
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     *
     */
    public void queryUserByDeptId() throws ServiceException, IntrospectionException{
        String deptId = workDTO.get("deptid");
        int deptid = Integer.valueOf(deptId);
        boolean  flag = authService.queryUserByDeptId(deptid);
        workDTO.setResult(flag);
    }


}
