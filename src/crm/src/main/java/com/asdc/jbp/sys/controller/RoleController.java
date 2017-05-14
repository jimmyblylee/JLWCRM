/**
 * Project Name : jbp-features-sys <br>
 * File Name : RoleController.java <br>
 * Package Name : com.asdc.jbp.sys.controller <br>
 * Create Time : May 1, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.controller;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.format.number.PercentFormatter;
import org.springframework.stereotype.Controller;

import com.asdc.jbp.dict.entity.SysDict;
import com.asdc.jbp.dict.service.DictService;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.dao.Parameter;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.utils.ProxyStripper;
import com.asdc.jbp.sys.entity.PermissionTree;
import com.asdc.jbp.sys.entity.RelSysRoleFunc;
import com.asdc.jbp.sys.entity.SysFunc;
import com.asdc.jbp.sys.entity.SysRole;
import com.asdc.jbp.sys.entity.SysUser;
import com.asdc.jbp.sys.service.AuthorityMgtService;
import com.asdc.jbp.sys.service.DeptMgtService;
import com.asdc.jbp.sys.service.UserMgtService;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * ClassName : RoleController <br>
 * Description : 角色管理控制器 <br>
 * Create Time : May 1, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Controller("RoleController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RoleController extends ControllerHelper {

    @Resource
    private AuthorityMgtService authService;
    @Resource
    private DeptMgtService deptService;
    @Resource
    private UserMgtService userMgtService;
    @Resource
    private DictService dictService;
 

    public void setAuthService(AuthorityMgtService authService) {
        this.authService = authService;
    }

    public void setDeptService(DeptMgtService deptService) {
        this.deptService = deptService;
    }

    public void setUserMgtService(UserMgtService userMgtService) {
        this.userMgtService = userMgtService;
    }

    public void setDictService(DictService dictService) {
        this.dictService = dictService;
    }

    /** 
     * Description：获取角色状态
     * @throws ServiceException
     * @return void
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonGenerationException 
     **/
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getStatusType() throws ServiceException, JsonGenerationException, JsonMappingException, IOException {
        List<String> roleStatus = new ArrayList<String>();
        List<String> selectStatus = new ArrayList<String>();
        Map statusType = new HashMap();
        List<SysDict> whetherDictList = dictService.getSelectDictInfo("WHETHER_TYPE","WHETHER_CODE");
        List<SysDict> statusDictList = dictService.getSelectDictInfo("STATUS_TYPE","STATUS_CODE");
        for (int i = 0; i < whetherDictList.size(); i++) {
                roleStatus.add(whetherDictList.get(i).getValue());
        }
        for (int i = 0; i < statusDictList.size(); i++) {
                selectStatus.add(statusDictList.get(i).getValue());
        }
        statusType.put("roleStatus",roleStatus);
        statusType.put("selectStatus",selectStatus);
        workDTO.setResult(statusType);
    }
    /** 
     * Description：角色列表
     * @throws ServiceException
     * @return void
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    @SuppressWarnings("unchecked")
    public void queryRoleInfo() throws ServiceException {
        Map<String, Object> pageQueryParams = workDTO.convertJsonToMapByKey("pageQuery");
        String roleStatus = (String) pageQueryParams.get("roleStatus");
        String pageQuery = (String) pageQueryParams.get("queryParams");
        int start = workDTO.getStart();
        int limit = workDTO.getLimit();
        log.debug("roleStatus:"+roleStatus);
    	Boolean isEnabled = true;
		if (!roleStatus.equals("true")) {
			isEnabled = false;
		}
        List<SysRole> queryAllRole = (List<SysRole>) authService
                .queryInfoByPage("sys.hql.queryAllRole", start, limit, Parameter.toList("pageQuery",
                        "%" + pageQuery + "%","isEnabled",isEnabled));
        int totle = authService.queryTotle("sys.hql.queryRoleTotle", Parameter.toList("pageQuery",
                "%" + pageQuery + "%","isEnabled",isEnabled));
        workDTO.setTotle(totle);
        workDTO.setResult(queryAllRole);
    }


    /** 
     * Description： 添加角色信息
     *      角色基本信息、角色所属组、角色所有用权限
     * @throws ServiceException
     * @return void
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    public void addRole() throws ServiceException {
        SysRole roleInfo = workDTO.convertJsonToBeanByKey("role", SysRole.class);
        roleInfo.setIsEnabled(true);
        authService.createRole(roleInfo);
        workDTO.setResult(true);

    }

    /** 
     * Description：根据角色名称查询角色信息
     * @return void
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     * @throws ServiceException 
     **/
    public void queryInfoByName() throws ServiceException {
        String roleName = workDTO.get("name");
        List<SysRole> queryRoleByRoleName = authService.queryRoleByRoleName(roleName);
        if (queryRoleByRoleName != null && queryRoleByRoleName.size() > 0) {
            workDTO.setWarn("31", "角色名已存在");
        }

    }

    /** 
     * Description：更新角色信息
     * @throws ServiceException
     * @return void
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    public void updateRole() throws ServiceException {
        SysRole roleInfo = workDTO.convertJsonToBeanByKey("role", SysRole.class);
        roleInfo.setIsEnabled(true);
        authService.updateRole(roleInfo);
        workDTO.setResult(true);

    }

    /** 
     * Description：删除角色信息
     * @throws ServiceException
     * @return void
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    @SuppressWarnings("unchecked")
    public void deleteRoleInfo() throws ServiceException {
        Map<String, Object> roleMap = workDTO.convertJsonToMapByKey("roleIdList");
        List<Integer> roleIdList =(List<Integer>) roleMap.get("roleId");
        authService.removeRoleByIds(roleIdList);
        workDTO.setResult(true);
    }
    /** 
     * Description：恢复角色信息
     * @throws ServiceException
     * @return void
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    @SuppressWarnings("unchecked")
    public void recoverRoleInfo() throws ServiceException {
        Map<String, Object> roleMap = workDTO.convertJsonToMapByKey("roleIdList");
        List<Integer> roleIdList =(List<Integer>) roleMap.get("roleId");
        authService.recoverRoleInfo(roleIdList);
        workDTO.setResult(true);
    }

    /** 
     * Description： 为角色添加权限
     * @throws ServiceException
     * @return void
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    public void roleAddFunc() throws ServiceException {
        Integer roleId = parseInt(workDTO.get("roleId"));
        List<Integer> funcIds = workDTO.get("funcIds");
        authService.assignFuncsToRole(roleId, funcIds);
    }

    /** 
     * Description： 根据roleID查看角色信息
     * @throws ServiceException
     * @return void
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    public void queryInfoById() throws ServiceException {
        Integer roleId = parseInt(workDTO.get("roleId"));
        SysRole role = authService.getRoleById(roleId);
        workDTO.setResult(role);
    }

    /** 
     * Description：为角色分配用户
     * @throws ServiceException
     * @return void
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    public void addUserByRole() throws ServiceException {
        List<SysUser> users = workDTO.converJsonToBeanListByKey("userInfo", SysUser.class);
        List<Integer> userIds = converUserBeanToUserIdList(users);
        Integer roleId = parseInt(workDTO.get("roleId"));
        authService.replaceUsersToRole(userIds, roleId);
        workDTO.setResult(true);
    }

    /** 
     * Description：角色移除用户
     * @throws ServiceException
     * @return void
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    public void removeUserByRole() throws ServiceException {
        List<Integer> roleIds = new ArrayList<Integer>();
        Integer roleId = parseInt(workDTO.get("roleId"));
        roleIds.add(roleId);
        int userId = parseInt(workDTO.get("userId"));
        authService.removeRolesFromUser(userId, roleIds);
        workDTO.setResult(true);
    }
   @SuppressWarnings({ "unchecked", "rawtypes" })
    public void queryFuncByRoleId() throws ServiceException {
        Integer roleId = parseInt(workDTO.get("roleId"));
        List mapList = new ArrayList();
        
        /*
         *   两种情况，没有子类，有子类
         *    ---》没有子类，就根据自己的状态进行选择。
         *    ---》有子类，   需要根据子类的状态来判断自己的状态
         */
        //1.得到父类的菜单--->功能管理
        SysFunc func = authService.getFuncById(0);
        Map map = new HashMap();
        map.put("id", func.getId());
        map.put("label", func.getName());
	    //2.是否被全部选中
        /*
         * 3.通过func得到对应的角色 ---》如果该菜单得到对应的角色---》下面还需要判断roleId
         *      -->如果该菜单没有对象的Role。那么肯定就是没选中状态
         *      --》如果存在对应的role
         *             --还需要判断改菜单是否存在子类，如果存在子类，根据子类判断自己的选中状态，是半选中呢，还是选中呢
         *             
         */
        //4.判断当前角色是否有func权限
        List<RelSysRoleFunc> queryFuncsByRoleId = authService.queryRelRoleFuncInfoByFuncId(func.getId()); //根据id后去角色对应
        int parentSize = authService.queryChildren(func.getId()).size();
        //判断该菜单是否有对应的角色   --》如果size()长度等于0；没有与角色对应的状态，也就是全部都是false;
        if (queryFuncsByRoleId != null && queryFuncsByRoleId.size() > 0){ 
        	boolean checked = false;
        	List<Map> mapListChild = new ArrayList();
        	//判断是否被选中
            for (RelSysRoleFunc funcRole : queryFuncsByRoleId){
            	//被选中了和没有被选中 
                if(funcRole.getRoleId().equals(roleId) || parentSize >0){
                	checked = true;
                	//如果没有子类。判断子类的状态，来得到自己的状态
            	    if(parentSize > 0){
	                     //所以这里需要传递两个参数过去
            	    	 mapListChild = getChildren(func.getId(),roleId);
	                     //获取子类后，通过子类进行判断 --》遍历所有的子类。来进行判断
	                     int signTrue =0;
	                     int signPart=0;
	                     int signFalse = 0;
	                     for (int i = 0; i < mapListChild.size(); i++) {
							//如果子类都都是true,那么就都是true
	                    	if(mapListChild.get(i).get("checkbox_true").equals(true)){
	                    		signTrue ++;
	                    	}
	                    	if(mapListChild.get(i).get("checkbox_true_part").equals(true)){
	                    		signPart ++;
	                    	}
	                    	if(mapListChild.get(i).get("checkbox_false").equals(true)){
 	                    		signFalse ++;
 	                    	}
						 }
	                     if(signTrue == mapListChild.size()){
	                    	map.put("checkbox_true", true);
	   	                    map.put("checkbox_false", false);
	   	                    map.put("checkbox_true_part", false);	
         	             } 
	                     if((signPart>0 && signPart <mapListChild.size()) || signPart==1 || signPart == mapListChild.size()
	                    		 ||(signTrue < mapListChild.size()&& signTrue >0)){
         	            	map.put("checkbox_true", false);
   	                        map.put("checkbox_false", false);
   	                        map.put("checkbox_true_part", true);	
         	             }
	                     if(signTrue == 0 && signPart ==0){
	                    	 map.put("checkbox_true", false);
   	                         map.put("checkbox_false", true);
   	                         map.put("checkbox_true_part", false);	
	                     }
	                     map.put("type","folder");
                         map.put("children", mapListChild);
                    }else{
	                     map.put("checkbox_true", true);
	                     map.put("checkbox_false", false);
	                     map.put("checkbox_true_part", false);	
	                	 map.put("type","doc");
                    }
            	    break;
                }
            }
            if(!checked){
            	map.put("checkbox_true", false);
            	map.put("checkbox_false", true);
            	map.put("checkbox_true_part", false);
            	map.put("type","doc");
            }
        }else{
        	//虽然不存在--但是有子节点，还是需要让显示出来的
        	if(parentSize > 0){
    			map.put("checkbox_true", false);
            	map.put("checkbox_false", true);
            	map.put("checkbox_true_part", false);
                map.put("type", "folder");
              	map.put("children",getChidrenNotChecked(func));
        	}else{
        		map.put("checkbox_true", false);
            	map.put("checkbox_false", true);
            	map.put("checkbox_true_part", false);
            	map.put("type","doc");
        	}
        }
        mapList.add(map);
        workDTO.setResult(mapList);
    }

    //全部没有选中的状态
    @SuppressWarnings("unchecked")
	public List<Map> getChidrenNotChecked(SysFunc func) throws ServiceException{
    	List mapList = new ArrayList();
    	List<SysFunc> funcList = authService.queryChildren(func.getId());
    	if(funcList !=null && funcList.size()>0){
    		for (int i = 0; i < funcList.size(); i++) {
        		Map map = new HashMap();
                map.put("id", funcList.get(i).getId());
                map.put("label", funcList.get(i).getName());
        		map.put("checkbox_true", false);
            	map.put("checkbox_false", true);
            	map.put("checkbox_true_part", false);
            	map.put("type", "folder");
            	map.put("children", getChidrenNotChecked(funcList.get(i)));
            	mapList.add(map);
    		}
    	}
    	return mapList;
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected List<Map> getChildren(Integer funcId, Integer roleId) throws ServiceException {
    	//PermissionTree  pt = new PermissionTree();
        List mapList = new ArrayList();
        List<SysFunc> funcList = authService.queryChildren(funcId);
        for(SysFunc func : funcList){
        	Map map = new HashMap();
            map.put("id", func.getId());
            map.put("label", func.getName());
            List<RelSysRoleFunc> queryFuncsByRoleId = authService.queryRelRoleFuncInfoByFuncId(func.getId()); //根据id后去角色对应
        	int parentSize = authService.queryChildren(func.getId()).size();
            if (queryFuncsByRoleId != null && queryFuncsByRoleId.size() > 0){ //判断是否被选中 是就被选中
            	boolean ischecked = false;
            	List<Map> mapListChild = new ArrayList();
                for (RelSysRoleFunc funcRole : queryFuncsByRoleId){
                    if(funcRole.getRoleId().equals(roleId) || parentSize > 0){
                        ischecked = true;
                        //这里应该也要区分下,如果是否存在下级
                        if(parentSize > 0){
                        	 mapListChild= getChildren(func.getId(),roleId);
                             //获取子类后，通过子类进行判断 --》遍历所有的子类。来进行判断
    	                     int signTrue =0;
    	                     int signPart=0;
    	                     int signFalse = 0;
    	                     for (int i = 0; i < mapListChild.size(); i++) {
    							//如果子类都都是true,那么就都是true
    	                    	if(mapListChild.get(i).get("checkbox_true").equals(true)){
    	                    		signTrue ++;
    	                    	}
    	                    	if(mapListChild.get(i).get("checkbox_true_part").equals(true)){
    	                    		signPart ++;
    	                    	}
    	                    	if(mapListChild.get(i).get("checkbox_false").equals(true)){
     	                    		signFalse ++;
     	                    	}
    						 }
     	                    if(signTrue == mapListChild.size()){
    	                    	map.put("checkbox_true", true);
    	   	                    map.put("checkbox_false", false);
    	   	                    map.put("checkbox_true_part", false);	
             	             } 
     	                   if((signPart>0 && signPart <mapListChild.size()) || signPart==1 || signPart == mapListChild.size()
  	                    		 ||(signTrue < mapListChild.size()&& signTrue >0)){
             	            	map.put("checkbox_true", false);
       	                        map.put("checkbox_false", false);
       	                        map.put("checkbox_true_part", true);	
             	             }
    	                     if(signTrue == 0 && signPart ==0){
    	                    	 map.put("checkbox_true", false);
       	                         map.put("checkbox_false", true);
       	                         map.put("checkbox_true_part", false);	
    	                     }
     	                     map.put("type", "folder");
                        	 map.put("children",mapListChild);
                        }else{
                        	map.put("checkbox_true", true);
 		                  	map.put("checkbox_false", false);
 		                  	map.put("checkbox_true_part", false);
 		                  	map.put("type","doc");
                        }
                        break;
                    }
                }
                if(!ischecked){
                	map.put("checkbox_true", false);
                  	map.put("checkbox_false", true);
                  	map.put("checkbox_true_part", false);	
                  	map.put("type","doc");
                }
            }else{
            	//虽然不存在--但是有子节点，还是需要让显示出来的
            	if(parentSize > 0){
        			map.put("checkbox_true", false);
                	map.put("checkbox_false", true);
                	map.put("checkbox_true_part", false);
                	map.put("type", "folder");
                	map.put("children", getChidrenNotChecked(func));
            	}else{
            		map.put("checkbox_true", false);
                	map.put("checkbox_false", true);
                	map.put("checkbox_true_part", false);
                	map.put("type","doc");
            	}
            }
           mapList.add(map);
        }
        return mapList;
    }

    /** 
     * Description：角色授权
     * @throws ServiceException
     * @return void
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    @SuppressWarnings("unchecked")
    public void addPerMissByRoleId() throws ServiceException {
    	
        Map<String, Object> addPerMap = workDTO.convertJsonToMapByKey("addPer");
        List<String> funcListStr = (List<String>) addPerMap.get("funcId");
        List<Integer> funcIdListInt= new ArrayList<Integer>();  
        
        for (int i = 0; i < funcListStr.size(); i++) {
        	funcIdListInt.add(Integer.parseInt(funcListStr.get(i)));
		}
        Integer roleId = (Integer) addPerMap.get("roleId"); 
        List<Integer> funcIdList= new ArrayList<Integer>();  
        for(Integer i : funcIdListInt){  
        	
            if(!funcIdList.contains(i)){  
                funcIdList.add(i);  
            }  
        }  
        funcIdList.size();
        authService.replaceFuncsToRole(roleId, funcIdList);
        workDTO.setResult(true);
        
    }
    /** 
     * Description：根据角色ID查询用户
     * @throws ServiceException
     * @throws IntrospectionException
     * @return void
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    public void queryUserByRoleId() throws ServiceException, IntrospectionException {
        Integer roleId = parseInt(workDTO.get("roleId"));
        List<SysUser> queryUsersByRoleId = authService.queryUsersByRoleId(roleId);
        workDTO.setResult(ProxyStripper.cleanFromProxies(queryUsersByRoleId));
    }

    /** 
     * Description：根据部门ID查询用户信息
     * @throws ServiceException
     * @throws IntrospectionException
     * @return void
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    public void queryUserByDeptId() throws ServiceException, IntrospectionException {
        Integer deptId = parseInt(workDTO.get("deptId"));
        SysUser userParams = new SysUser();
        userParams.setDeptId(deptId);
        userParams.setIsEnabled(true);
        List<SysUser> user = userMgtService.getSysUsersByDeptAndAccountAndName(userParams,0,-1);
        workDTO.setResult(ProxyStripper.cleanFromProxies(user));
    }

    /** 
     * Description：根据用户ID查询用户信息
     * @throws ServiceException
     * @throws IntrospectionException
     * @return void
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    public void queryUserByUserId() throws ServiceException, IntrospectionException {
        Integer userId = parseInt(workDTO.get("userId"));
        SysUser user = userMgtService.getSysUserById(userId);
        workDTO.setResult(ProxyStripper.cleanFromProxies(user));
    }

    /** 
     * Description： String to Integer
     * @param params
     * @return Integer
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    private Integer parseInt(Object params) {
        return new Integer((String) params);
    }

    private List<Integer> converUserBeanToUserIdList(List<SysUser> users) {
        List<Integer> userIdList = new ArrayList<Integer>();
        for (int i = 0; i < users.size(); i++) {
            userIdList.add(users.get(i).getId());
        }
        return userIdList;
    }

    protected List<Integer> converRoleBeanToRoleIdList(List<SysRole> role) {
        List<Integer> roleIdList = new ArrayList<Integer>();
        for (int i = 0; i < role.size(); i++) {
            roleIdList.add(role.get(i).getId());
        }
        return roleIdList;
    }
    
    private boolean getIsEnabled(String roleStatus) {
        boolean isEnabled = false;
        if(roleStatus.equals("可用") || roleStatus.equals("")){
            isEnabled = true;
        }
        return isEnabled;
    }
}
