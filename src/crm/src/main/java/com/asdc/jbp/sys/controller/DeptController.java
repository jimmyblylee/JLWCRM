package com.asdc.jbp.sys.controller;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.utils.ProxyStripper;
import com.asdc.jbp.sys.entity.SysDept;
import com.asdc.jbp.sys.entity.SysUser;
import com.asdc.jbp.sys.service.DeptMgtService;
import com.asdc.jbp.sys.service.UserMgtService;
import com.asdc.jbp.sys.token.Token;
/**
 * ClassName: DeptController.java <br>
 * Description: <br>
 * 机构管理的控制层 Create by: name：yangxuan <br>
 * email: xuan_yang@asdc.com.cn <br>
 * Create Time: 2016年5月31日<br>
 */
@Controller("DeptController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DeptController extends ControllerHelper {

	@Resource
	private DeptMgtService deptService;
	@Resource
	private UserMgtService userService;
	/**
	 * 
	 * Description：添加部门信息
	 * 
	 * @throws ServiceException
	 * @return void
	 * @author name：yangxuan <br>
	 *         email: xuan_yang@asdc.com.cn
	 * 
	 */

	public void createSysDept() throws ServiceException {
		SysDept dept = workDTO.convertJsonToBeanByKey("dept", SysDept.class);
		deptService.createSysDept(dept);
	}

	/**
	 * 
	 * Description：修改部门信息
	 * 
	 * @throws ServiceException
	 * @return void
	 * @author name：yangxuan <br>
	 *         email: xuan_yang@asdc.com.cn
	 *
	 */

	public void updateSysDept() throws ServiceException {
		SysDept dept = workDTO.convertJsonToBeanByKey("dept", SysDept.class);
		deptService.updateSysDept(dept);
	}

	/**
	 * 
	 * Description：通过deptid,单个删除部门信息
	 * 
	 * @throws ServiceException
	 * @return void
	 * @author name：yangxuan <br>
	 *         email: xuan_yang@asdc.com.cn
	 *
	 */
	public void removeSysDept() throws ServiceException {
		String deptId = workDTO.get("deptid");
		int deptid = Integer.valueOf(deptId);
		String deptIdStrs = diGuiGetDelId(deptid,true);
		String[] array = deptIdStrs.substring(0, deptIdStrs.length() - 1).split(",");
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++) {
			list.add(Integer.parseInt(array[i]));
		}
		deptService.removeSysDept(list);
	}
	/**
	 * 
	 * Description：通过deptid,多个恢复机构状态
	 * 
	 * @throws ServiceException
	 * @return void
	 * @author name：yangxuan <br>
	 *         email: xuan_yang@asdc.com.cn
	 *
	 */
	public void recoverDeptState() throws ServiceException {
		String deptId = workDTO.get("deptid");
		int deptid = Integer.valueOf(deptId);
		String deptIdStrs = diGuiGetDelId(deptid,false);
		String[] array = deptIdStrs.substring(0, deptIdStrs.length() - 1).split(",");
		List<Integer> list = new ArrayList<Integer>();	
		for (int i = 0; i < array.length; i++) {
			list.add(Integer.parseInt(array[i]));
		}
		deptService.recoverDeptState(list);
	}
	/**
	 * 
	 * Description：模糊查询、分页数据查询接口
	 * 
	 * @throws ServiceException
	 * @throws IntrospectionException
	 * @return void
	 * @author name：yangxuan <br>
	 *         email: xuan_yang@asdc.com.cn
	 *
	 */
	public void queryDeptBypaging() throws ServiceException, IntrospectionException {
		int start = workDTO.getStart();
		int limit = workDTO.getLimit();
		SysDept dept = workDTO.convertJsonToBeanByKey("pageQuery", SysDept.class);
		List<SysDept> deptList = deptService.queryDeptBypagingAndQuerying(start, limit, dept);
		int total = deptService.getCountBypagingAndQuerying(dept);
		workDTO.setTotle(total);
		workDTO.setResult(ProxyStripper.cleanFromProxies(deptList));
	}
	
	/**
	 * 
	 * Description：前台第一次得到的树的数据
	 * 
	 * @throws ServiceException
	 * @return void
	 * @author name：yangxuan <br>
	 *         email: xuan_yang@asdc.com.cn
	 */
	public void getDeptTreeParent() throws ServiceException {
		
		
		//获取当前用户id
		 Token token =  sessionDTO.get("token");
		 int userId  = token.getUser().getId();
		 SysUser user = userService.getUserById(userId);
		 int deptId1 = user.getDept().getId();
		List<Map<String,Object>>  lm = new ArrayList<Map<String,Object>>();
		//默认机构管理
		SysDept dept = deptService.getSysDeptById(deptId1);
		//如果存在的话，在获取另一个参数
		String deptId = workDTO.get("deptId");
		Map<String,Object>  map = new HashMap<String,Object>();
		map.put("label", dept.getName());
    	map.put("id", dept.getId());
    	map.put("controller","DeptController");
    	map.put("method", "getDeptTreeChildren");
    	int hideDeptid = 0; 
    	List<SysDept>  childrenList = deptService.queryChildren(deptId1);
        if(deptId != null && !deptId.equals("")){
            hideDeptid = Integer.valueOf(deptId);
        	map.put("hideCommon", hideDeptid);
        }
		// 得到所有的子类
		if (childrenList != null && childrenList.size() > 0 && deptId != null && !deptId.equals("")){
			map.put("type", "folder");
			map.put("children", getDeptTreeById(childrenList,hideDeptid));
		}else if(childrenList != null && childrenList.size() > 0 && (deptId == null || "".equals(deptId))){
			map.put("type", "folder");
			map.put("children", getDeptTreeById(childrenList));
		}else if(childrenList == null || childrenList.size() <= 0){
			map.put("type", "doc");
		}	
        lm.add(map);
		workDTO.setResult(lm);
	}
	
	/**
	 * 
	 * Description：点击树的父节点，请求子节点的数据
	 * 
	 * @throws ServiceException
	 * @return void
	 * @author name：yangxuan <br>
	 *         email: xuan_yang@asdc.com.cn
	 *
	 */
	public void getDeptTreeChildren() throws ServiceException {
		String deptId = workDTO.get("commonId");
		System.out.println("{}==="+deptId);
		int deptid = Integer.valueOf(deptId);
		String hideDeptId = workDTO.get("commonHideId");
		List<Map<String,Object>>  lm = new ArrayList<Map<String,Object>>();
		List<SysDept>  childrenList = deptService.queryChildren(deptid);
		if(hideDeptId !=null && !hideDeptId.equals("")){
			 int hideId = Integer.valueOf(hideDeptId);
			 lm = getDeptTreeById(childrenList,hideId);
		}else{
			 lm = getDeptTreeById(childrenList);
		}
		workDTO.setResult(lm);
	}
	/**
	 * 
	 * Description：过滤掉自己不能选择本身的上级机构
	 * @param deptId
	 * @param hideDeptId
	 * @return
	 * @throws ServiceException
	 * @return String
	 * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
	 *
	 */
	public List<Map<String,Object>> getDeptTreeById(List<SysDept> childrenList,int hideDeptId) throws ServiceException {
		List<Map<String,Object>>  lm = new ArrayList<Map<String,Object>>();
		// 得到所有的子类
		if (childrenList != null && childrenList.size() > 0) {
			for (int i = 0; i < childrenList.size(); i++) {
				Map<String,Object>  map = new HashMap<String,Object>();
			    SysDept dept =childrenList.get(i);
				if(dept.getId() != hideDeptId){
				    map.put("label", dept.getName());
			    	map.put("id", dept.getId());
			    	map.put("hideCommon", hideDeptId);
			    	map.put("controller","DeptController");
			    	map.put("method", "getDeptTreeChildren");
					List<SysDept> deptList = deptService.queryChildren(dept.getId());
					if(deptList.size() > 0){
						if (deptList.size() == 1 && deptList.get(0).getId() == hideDeptId) {
							map.put("type", "doc");
						}else{
							map.put("type", "folder");
							map.put("children",getNullListMap());
						}
					}else{
						map.put("type", "doc");
					}
				  lm.add(map);
				}
			}
		}
		return lm;
	}
	/**
	 * 
	 * Description：根据父类id得到直属子类
	 * @param childrenList
	 * @return
	 * @throws ServiceException
	 * @return String
	 * @author name：yangxuan <br>
	 *         email: xuan_yang@asdc.com.cn
	 */
	public List<Map<String,Object>> getDeptTreeById(List<SysDept> childrenList) throws ServiceException {
		List<Map<String,Object>>  lm = new ArrayList<Map<String,Object>>();
		// 得到所有的子类
		if (childrenList != null && childrenList.size() > 0) {
			for (int i = 0; i < childrenList.size(); i++) {
				    Map<String,Object>  map = new HashMap<String,Object>();
				    SysDept dept =childrenList.get(i);
				    map.put("label", dept.getName());
			    	map.put("id", dept.getId());
			    	map.put("controller","DeptController");
			    	map.put("method", "getDeptTreeChildren");
					if (deptService.queryChildren(dept.getId()).size() > 0) {
						map.put("type", "folder");
						map.put("children", getNullListMap());
					} else {
						map.put("type", "doc");
					}
					lm.add(map);
			}
		}
		return lm;
	}
	public List<Map<String,Object>> getNullListMap(){
		List<Map<String,Object>>  listMap = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("label", "");
		map.put("type", "");
		listMap.add(map);
		return listMap;
	}
	/**
	 * 
	 * Description：通过deptId,得到所有子类的id数据。
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 * @return String 返回id拼接的字符串:类似 "1,2,3,4,"
	 * @author name：yangxuan <br>
	 *         email: xuan_yang@asdc.com.cn
	 *
	 */
	private StringBuffer sb = new StringBuffer();
	public String diGuiGetDelId(int id,Boolean flag) throws ServiceException {
		sb.append(id + ",");
		List<SysDept> listDept =new ArrayList<SysDept>();
		if(flag){
			listDept = deptService.queryChildren(id);
		}else{
			listDept = deptService.queryChildrenRecover(id);
		}
		if (listDept.size() >= 0){
			for (int i = 0; i < listDept.size(); i++){
				diGuiGetDelId(listDept.get(i).getId().intValue(),flag);
			}
		}
		return sb.toString();
	}

	public void setDeptService(DeptMgtService deptService) {
		this.deptService = deptService;
	}
	
	/**
	 * 
	 * Description：上移位置改变排序位置方法
	 * @throws ServiceException
	 * @return void
	 * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
	 *
	 */
	public void moveUpSort() throws ServiceException{
        //获取要焦点本身机构
		int deptid = Integer.valueOf((String) workDTO.get("deptId"));
		//获取其上级的排序值
		int sort = Integer.valueOf((String) workDTO.get("maxSort"));
		//获取上级排序的机构id
		int upDeptid = Integer.valueOf((String) workDTO.get("upDeptid"));
		
		log.debug("deptId:"+deptid+",maxSort:"+sort+",upDeptid:"+upDeptid);
		
		//1.修改自身排序
		SysDept dept = deptService.getSysDeptById(deptid);
		dept.setSort(sort);
		deptService.updateSysDept(dept);
		//2.修改上级排序
		SysDept deptUp = deptService.getSysDeptById(upDeptid);
		deptUp.setSort(sort+1);
		deptService.updateSysDept(deptUp);
	}
	//下移controller
	public void moveDownSort() throws ServiceException{
		int deptid = Integer.valueOf((String) workDTO.get("deptId"));
		int sort = Integer.valueOf((String) workDTO.get("minSort"));
		int downDeptid = Integer.valueOf((String) workDTO.get("downDeptid"));
		//1.修改自身排序
		SysDept dept = deptService.getSysDeptById(deptid);
		dept.setSort(sort);
		deptService.updateSysDept(dept);
		//2.修改下级排序
		SysDept deptUp = deptService.getSysDeptById(downDeptid);
		deptUp.setSort(sort-1);
		deptService.updateSysDept(deptUp);
	}
	//置顶controlller
	public void moveTopSort() throws ServiceException{
		int deptid = Integer.valueOf((String) workDTO.get("deptId"));
		String tmpIds = workDTO.get("tmpIds");
		int minsort = Integer.valueOf((String) workDTO.get("minSort"));
		//1.修改自身排序
		SysDept dept = deptService.getSysDeptById(deptid);
		dept.setSort(minsort);
		deptService.updateSysDept(dept);
		//2.修改其他排序
		String[] array = tmpIds.substring(1, tmpIds.length() - 1).split(",");
		for (int i = 0; i < array.length; i++) {
			SysDept deptOhter = deptService.getSysDeptById(Integer.valueOf(array[i]));
			deptOhter.setSort(deptOhter.getSort()+1);
			deptService.updateSysDept(deptOhter);
		}
	}
	//置底controller
	public void moveLowSort()throws ServiceException{
		int deptid = Integer.valueOf((String) workDTO.get("deptId"));
		String tmpIds = workDTO.get("tmpIds");
		int maxsort = Integer.valueOf((String) workDTO.get("maxSort"));
		//1.修改自身排序
		SysDept dept = deptService.getSysDeptById(deptid);
		dept.setSort(maxsort);
		deptService.updateSysDept(dept);
		//2.修改其他排序
		String[] array = tmpIds.substring(1, tmpIds.length() - 1).split(",");
		for (int i = 0; i < array.length; i++) {
			SysDept deptOhter = deptService.getSysDeptById(Integer.valueOf(array[i]));
			deptOhter.setSort(deptOhter.getSort()-1);
			deptService.updateSysDept(deptOhter);
		}
	}
	
	public void getDept() throws ServiceException{
		SysDept  sys = new SysDept();
		sys.setId(1);
		sys.setIsEnabled(true);
		SysDept sys1  = deptService.queryDeptById(sys);
		workDTO.setResult(sys1);
	}
	
	//通过deptId来判断是否存在下级机构
	public void getChildDept()throws ServiceException{
		String deptId = workDTO.get("deptid");
		int deptid = Integer.valueOf(deptId);

		List<SysDept> deptList = deptService.queryChildren(deptid);
		if(deptList != null &&deptList.size() > 0){
			workDTO.setResult(true);
		}else{
			workDTO.setResult(false);
		}
	}
	/**
	 * 
	 * Description：查找最上级机构
	 * @return void
	 * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
	 * @throws IntrospectionException 
	 *
	 */
	public void queryParentDeptIsNull() throws IntrospectionException{
		 SysDept sysDept = deptService.queryParentDeptIsNull();
		 workDTO.setResult(ProxyStripper.cleanFromProxies(sysDept));
	}
	/**
	 * 
	 * Description：通过机构id判断机构是否可用
	 * @throws IntrospectionException
	 * @throws ServiceException
	 * @return void
	 * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
	 *
	 */
	public void queryDeptByDeptIdNoisEnabled() throws IntrospectionException, ServiceException{
		 String deptId = workDTO.get("deptid");
		 int deptid = Integer.valueOf(deptId);
		 boolean flag = deptService.queryDeptByDeptIdNoisEnabled(deptid);
		 workDTO.setResult(flag);
	}
	
	
	public void getOneDeptUserTree() throws ServiceException {
		Token token = (Token) sessionDTO.get("token");
		int userId = token.getUser().getId().intValue();
		SysUser user = userService.getUserById(Integer.valueOf(userId));
		int deptId1 = user.getDept().getId().intValue();
		SysDept dept = deptService.getSysDeptById(Integer.valueOf(deptId1));
		List<Map<String,Object>> lm = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("label", dept.getName());
		map.put("id", dept.getId());
		map.put("checkbox_false", Boolean.valueOf(true));
		map.put("user_dept_falg", "dept");
		List<SysDept> deptList = deptService.queryChildren(dept.getId());
		if (deptList != null && deptList.size() > 0) {
			map.put("type", "folder");
			map.put("children", getDeptUserByDiGui(deptList, dept, userId));
		} else {
			map.put("type", "doc");
		}
		lm.add(map);
		workDTO.setResult(lm);
	}

	public List getDeptUserByDiGui(List deptList, SysDept dept, int userId) throws ServiceException {
		List listMap = new ArrayList();
		if (deptList != null && deptList.size() > 0)
			listMap = getDeptTree(deptList, userId);
		List userList = deptService.getUserListByDeptId(dept.getId().intValue());
		if (userList != null && userList.size() > 0) {
			List userListMap = getUserTree(userList, userId);
			listMap.addAll(userListMap);
		}
		return listMap;
	}

	public List<Map<String,Object>> getDeptTree(List<SysDept> childrenList, int userid) throws ServiceException {
		List<Map<String,Object>> lm = new ArrayList<Map<String,Object>>();
		if (childrenList != null && childrenList.size() > 0) {
			for (int i = 0; i < childrenList.size(); i++) {
				Map<String,Object> map = new HashMap<String,Object>();
				SysDept dept = (SysDept) childrenList.get(i);
				map.put("label", dept.getName());
				map.put("id", dept.getId());
				map.put("hideCommon", Integer.valueOf(userid));
				map.put("checkbox_false", Boolean.valueOf(true));
				map.put("type", "folder");
				map.put("user_dept_falg", "dept");
				List<SysDept> deptList = deptService.queryChildren(dept.getId());
				map.put("children", getDeptUserByDiGui(deptList, dept, userid));
				lm.add(map);
			}

		}
		return lm;
	}

	private List<Map<String,Object>> getUserTree(List<SysUser> userList, int hideUserid) {
		List<Map<String,Object>> lm = new ArrayList<Map<String,Object>>();
		if (userList != null && userList.size() > 0) {
			for (int i = 0; i < userList.size(); i++) {
				Map<String,Object> map = new HashMap<String,Object>();
				SysUser user = (SysUser) userList.get(i);
				if (user.getId().intValue() != hideUserid) {
					map.put("label", user.getName());
					map.put("id", user.getId());
					map.put("checkbox_false", Boolean.valueOf(true));
					map.put("user_dept_falg", "user");
					lm.add(map);
				}
			}

		}
		return lm;
	}

	public void getTwoDeptUserTree() throws ServiceException {
		String deptId = (String) workDTO.get("commonId");
		int deptid = Integer.valueOf(deptId).intValue();
		String hideUserId = (String) workDTO.get("commonHideId");
		int userid = Integer.valueOf(hideUserId).intValue();
		List<SysDept> childrenList = deptService.queryChildren(Integer.valueOf(deptid));
		List<SysUser> userList = deptService.getUserListByDeptId(deptid);
		List deptLm = new ArrayList<Map<String,Object>>();
		List userLm = null;
		if (childrenList != null && childrenList.size() > 0)
			deptLm = getDeptTree(childrenList, userid);
		if (userList != null && userList.size() > 0) {
			userLm = getUserTree(userList, userid);
			deptLm.addAll(userLm);
		}
		workDTO.setResult(deptLm);
	}
	
	
}
