/**
 * Project Name jbp-features-sys
 * File Name GroupController.java
 * Package Name com.asdc.jbp.sys.controller
 * Create Time 2016年6月5日
 * Create by name：yanghaotian -- email: haotian_yang@asdc.com.cn
 * Copyright  2006, 2016, ASDC DAI. All rights reserved.
 */

package com.asdc.jbp.sys.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.dao.Parameter;
import com.asdc.jbp.framework.dto.WorkDTO;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.sys.entity.SysGroup;
import com.asdc.jbp.sys.entity.SysRole;
import com.asdc.jbp.sys.service.AuthorityMgtService;
import com.asdc.jbp.sys.service.GroupMgtService;

/**
 * ClassName : GroupController <br>
 * Description : 用户组管理控制器 <br>
 * Create Time : May 1, 2016 <br>
 * Create by : haotian_yang@asdc.com.cn <br>
 *
 */
@Controller("GroupController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GroupController extends ControllerHelper {

    @Resource
    private AuthorityMgtService authService;

    public void workDTO(WorkDTO workDTO) {
        this.workDTO = workDTO;
    }

    public void setAuthService(AuthorityMgtService authService) {
        this.authService = authService;
    }

    public void setGroupService(GroupMgtService groupService) {
        this.groupService = groupService;
    }

    @Resource
    private GroupMgtService groupService;

    /**
     * Description : 创建用户组 <br>
     * Create Time: 2016年6月5日 <br>
     * Create by : haotian_yang@asdc.com.cn <br>
     *
     * @throws ServiceException
     */
    public void createSysGroup() throws ServiceException {
        SysGroup sysGroup = workDTO.convertJsonToBeanByKey("addGroup", SysGroup.class);
        sysGroup.setIsEnabled(true);
        authService.createGroup(sysGroup);
    }

    /**
     * Description : 修改用户组 <br>
     * Create Time: 2016年6月5日 <br>
     * Create by : haotian_yang@asdc.com.cn <br>
     *
     * @throws ServiceException
     */
    public void updateSysGroup() throws ServiceException {
        SysGroup sysGroup = workDTO.convertJsonToBeanByKey("update", SysGroup.class);
        authService.updateGroup(sysGroup);
    }

    /**
     * Description : 根据groupId删除用户组 <br>
     * Create Time: 2016年6月5日 <br>
     * Create by : haotian_yang@asdc.com.cn <br>
     *
     * @throws ServiceException
     */
    public void removeSysGroup() throws ServiceException {
        SysGroup sysGroup = workDTO.convertJsonToBeanByKey("deleteGroup", SysGroup.class);
        authService.removeGroupById(sysGroup.getId());
    }
    
    /**
     * Description : 根据groupId恢复用户组 <br>
     * Create Time: 2016年6月5日 <br>
     * Create by : haotian_yang@asdc.com.cn <br>
     *
     * @throws ServiceException
     */
    public void recoveSysGroup() throws ServiceException {
        SysGroup sysGroup = workDTO.convertJsonToBeanByKey("recoveGroup", SysGroup.class);
        sysGroup.setIsEnabled(true);
        authService.updateGroup(sysGroup);
    }

    /**
     * Description : 查询所有权限 <br>
     * Create Time: 2016年6月5日 <br>
     * Create by : haotian_yang@asdc.com.cn <br>
     *
     * @throws ServiceException
     */
    @SuppressWarnings("unchecked")
	public void queryAllSysGroup() throws ServiceException {
        int start = workDTO.getStart();
        int limit = workDTO.getLimit();
        Map<String, Object> pageQueryParams = workDTO.convertJsonToMapByKey("pageQuery");
        String groupName = (String) pageQueryParams.get("groupName");
        String groupStatus = (String) pageQueryParams.get("groupIsEnabled");
        
        Boolean groupStatusType = false;
        
        if(groupStatus.equals("true")){
            groupStatusType = true;
        }
        String name = "";
        if(groupName.length()!=0 && "".equals(name)){
        	name = groupName;
        }
		List<SysGroup> queryAllSysGroup = (List<SysGroup>) authService
                .queryInfoByPage("sys.hql.querySysGroupByName", start, limit, Parameter.toList("isEnabled",groupStatusType,"name",
                        "%" + name + "%","desc","%" + name + "%"));
		
		List<SysGroup> queryAllSysGrouptotal = (List<SysGroup>) authService
                .queryInfoByPage("sys.hql.querySysGroupByName",0,-1, Parameter.toList("isEnabled",groupStatusType,"name",
                        "%" + name + "%","desc","%" + name + "%"));
       
        int total = queryAllSysGrouptotal.size(); 
        workDTO.setResult(queryAllSysGroup);
        workDTO.setTotle(total);
    }

    /**
     * Description : 根据用户组ID查询对应的角色 <br>
     * Create Time: 2016年6月5日 <br>
     * Create by : haotian_yang@asdc.com.cn <br>
     *
     * @throws ServiceException
     */
    public void queryRolesByGroupId() throws ServiceException {
        SysGroup sysGroup = workDTO.convertJsonToBeanByKey("queryRolesByGroupId", SysGroup.class);
        List<SysRole> roleList = authService.queryRolesByGroupId(sysGroup.getId());
        workDTO.setResult(roleList);
    }

    /**
     * Description : 查询所有权限，并且根据groupId区分获取的权限和未获取的权限 <br>
     * Create Time: 2016年6月5日 <br>
     * Create by : haotian_yang@asdc.com.cn <br>
     *
     * @throws ServiceException
     */
    @SuppressWarnings("unchecked")
    public void queryRoles() throws ServiceException {
        List<SysRole> queryAllRole = (List<SysRole>) authService.queryInfoByPage("sys.hql.queryAllRole", 0, -1, Parameter.toList("pageQuery",
                "%%","isEnabled",true));
        SysGroup sysGroup = workDTO.convertJsonToBeanByKey("queryRolesByGroupId", SysGroup.class);
        List<SysRole> roleList = authService.queryRolesByGroupId(sysGroup.getId());
        if (roleList.size() > 0) {
            for (int i = 0; i < queryAllRole.size(); i++) {
                for (int j = 0; j < roleList.size(); j++) {
                    if (queryAllRole.get(i).getId() != roleList.get(j).getId()) {
                        queryAllRole.get(i).setIsEnabled(false);
                        queryAllRole.get(i).getIsEnabled();
                    } else {
                        queryAllRole.get(i).setIsEnabled(true);
                        break;
                    }
                }
            }
        } else {
            for (int i = 0; i < queryAllRole.size(); i++) {
                queryAllRole.get(i).setIsEnabled(false);
            }
        }
        workDTO.setResult(queryAllRole);
    }

    /**
     * Description : 用户组赋权 <br>
     * Create Time: 2016年6月5日 <br>
     * Create by : haotian_yang@asdc.com.cn <br>
     *
     * @throws ServiceException
     */
    public void replaceRolesToGroup() throws ServiceException {
        int queryRolesByGroupId = 0;
        List<Integer> GroupList = new ArrayList();
        String roleStr = workDTO.convertJsonToBeanByKey("replaceRolesToGroup", String.class);
        SysGroup sysGroup = workDTO.convertJsonToBeanByKey("queryRolesByGroupId", SysGroup.class);
        if (roleStr.length() > 0) {
            String allRoleStr[] = roleStr.split("_");
            for (int i = 0; i < allRoleStr.length; i++) {
                queryRolesByGroupId = Integer.parseInt(allRoleStr[i]);
                GroupList.add(i, queryRolesByGroupId);
            }
            authService.replaceRolesToGroup(sysGroup.getId(), GroupList);
        } else {
            authService.replaceRolesToGroup(sysGroup.getId(), GroupList);
        }
    }
    
    /**
     * Description : 批量删除用户组 <br>
     * Create Time: 2016年6月13日 <br>
     * Create by : haotian_yang@asdc.com.cn <br>
     *
     * @throws ServiceException
     */
    public void deleteGroupList() throws ServiceException {
        int deleteGroupId = 0;
        List<Integer> GroupDeleteList = new ArrayList();
        String GroupDeleteStr = workDTO.convertJsonToBeanByKey("deleteGroupCheckList", String.class);
        if (GroupDeleteStr.length() > 0) {
            String allRoleStr[] = GroupDeleteStr.split("_");
            for (int i = 0; i < allRoleStr.length; i++) {
                deleteGroupId = Integer.parseInt(allRoleStr[i]);
                GroupDeleteList.add(i, deleteGroupId);
            }
            authService.removeGroupByIds(GroupDeleteList);
        } else {
            authService.removeGroupByIds(GroupDeleteList);
        }
    }
    
    /**
     * Description : 批量恢复用户组 <br>
     * Create Time: 2016年6月13日 <br>
     * Create by : haotian_yang@asdc.com.cn <br>
     *
     * @throws ServiceException
     */
    public void recoveGroupList() throws ServiceException {
        int recoveGroupId = 0;
        List<Integer> GroupRecoveList = new ArrayList();
        String GroupRecoveStr = workDTO.convertJsonToBeanByKey("recoveGroupCheckList", String.class);
        if (GroupRecoveStr.length() > 0) {
            String allRoleStr[] = GroupRecoveStr.split("_");
            for (int i = 0; i < allRoleStr.length; i++) {
                recoveGroupId = Integer.parseInt(allRoleStr[i]);
                SysGroup sysGroup = authService.getGroupById(recoveGroupId);
                sysGroup.setIsEnabled(true);
                authService.updateGroup(sysGroup);
            }     
        } else {
            
        }
    }
}
