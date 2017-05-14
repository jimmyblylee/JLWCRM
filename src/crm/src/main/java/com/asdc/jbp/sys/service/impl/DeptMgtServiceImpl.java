/**
 * Project Name : jbp-features-sys <br>
 * File Name : DeptMgtServiceImpl.java <br>
 * Package Name : com.asdc.jbp.sys.service.impl <br>
 * Create Time : May 1, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asdc.jbp.dict.entity.SysDict;
import com.asdc.jbp.framework.dao.JpaOrmOperator;
import com.asdc.jbp.framework.dao.Parameter;
import com.asdc.jbp.framework.exception.ErrLevel;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.message.Messages;
import com.asdc.jbp.framework.utils.BeanUtils;
import com.asdc.jbp.sys.entity.SysDept;
import com.asdc.jbp.sys.entity.SysFunc;
import com.asdc.jbp.sys.entity.SysUser;
import com.asdc.jbp.sys.service.DeptMgtService;
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
public class DeptMgtServiceImpl implements DeptMgtService {

	@Resource(name = "SysCommonDao")
	private JpaOrmOperator dao;

	@Resource
	private UserMgtService userMgtService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.DeptMgtService#createSysDept(com.asdc.jbp.sys.entity.SysDept)
	 */
	@Override
	public SysDept createSysDept(SysDept dept) throws ServiceException {
		// validate
		if (dept == null) {
			throw new ServiceException("ERR_SYS_011", Messages.getMsg("sys", "ERR_SYS_MSG_DEPT_NULL"));
		}
		if (dept.getName() == null) {
			throw new ServiceException("ERR_SYS_011", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_DEPT_NAME_NULL"));
		}
		dept.setIsEnabled(true);
		dao.persist(dept);
		return dept;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.DeptMgtService#updateSysDept(com.asdc.jbp.sys.entity.SysDept)
	 */
	@Override
	public SysDept updateSysDept(SysDept dept) throws ServiceException {
		// validate
		if (dept == null) {
			throw new ServiceException("ERR_SYS_012", Messages.getMsg("sys", "ERR_SYS_MSG_DEPT_NULL"));
		}
		if (dept.getName() == null) {
			throw new ServiceException("ERR_SYS_012", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_DEPT_NAME_NULL"));
		}
		SysDept deptInDB = getSysDeptById(dept.getId());

		// fill default values
		BeanUtils.copyProperties(dept, deptInDB, "isEnabled", "children", "hasChildren");
		return deptInDB;
	}
	
	@Override
	public SysDept updateSysDeptComplate(SysDept dept) throws ServiceException {
		// validate
		if (dept == null) {
			throw new ServiceException("ERR_SYS_012", Messages.getMsg("sys", "ERR_SYS_MSG_DEPT_NULL"));
		}
		if (dept.getName() == null) {
			throw new ServiceException("ERR_SYS_012", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_DEPT_NAME_NULL"));
		}
		SysDept deptInDB = getSysDeptById(dept.getId());
		
		// fill default values
		BeanUtils.copyProperties(dept, deptInDB, "children", "hasChildren");
		return deptInDB;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.DeptMgtService#removeSysDept(java.util.List)
	 */
	@Override
	public void removeSysDept(List<Integer> ids) throws ServiceException {
		if (ids == null) {
			throw new ServiceException("ERR_SYS_013", Messages.getMsg("sys", "ERR_SYS_MSG_DEPT_ID_NULL"));
		}
		for (Integer id : ids) {
			getSysDeptById(id).setIsEnabled(false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.DeptMgtService#getSysDeptById(java.lang.Integer)
	 */
	@Override
	public SysDept getSysDeptById(Integer id) throws ServiceException{
		if (id == null) {
			throw new ServiceException("ERR_SYS_013", Messages.getMsg("sys", "ERR_SYS_MSG_DEPT_ID_NULL"));
		}
		SysDept deptInDB = dao.find(SysDept.class, id);
		if (deptInDB == null) {
			throw new ServiceException("ERR_SYS_013", Messages.getMsg("sys", "ERR_SYS_MSG_CAN_NOT_FIND_DEPT_BY_ID"), id);
		}

		return deptInDB;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.DeptMgtService#queryChildren(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysDept> queryChildren(Integer deptId) throws ServiceException {
		return (List<SysDept>) dao.queryByNamedQuery("sys.hql.queryChildrenDeptByDeptId", Parameter.toList("deptId", deptId));
	}
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.DeptMgtService#queryAllSysDept()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysDept> queryAllSysDept() {
		return (List<SysDept>) dao.queryByNamedQuery("sys.hql.queryAllSysDept");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.DeptMgtService#queryUsersByDeptId(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysUser> queryUsersByDeptId(Integer deptId) throws ServiceException {
		getSysDeptById(deptId);
		return (List<SysUser>) dao.queryByNamedQuery("sys.hql.queryAllSysUserByDeptId", Parameter.toList("deptId", deptId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.DeptMgtService#assignUsersToDept(java.lang.Integer, java.util.List)
	 */
	@Override
	public void assignUsersToDept(Integer deptId, List<Integer> userIds) throws ServiceException {
		SysDept dept = getSysDeptById(deptId);
		for (Integer userId : userIds) {
			userMgtService.getSysUserById(userId).setDept(dept);
		}
	}

	@Override
	public Integer queryDeptTotle() throws ServiceException {
		int totle = dao.getAllCount("sys.hql.queryDeptTotle");
		return totle;
	}

	@Override
	public Integer getCountByDeptId(String deptId) throws ServiceException {
		int total = 0;
		
		if (deptId != null && !"".equals(deptId)) {
			int deptid = Integer.valueOf(deptId);
			int isExist = queryDeptIsExistByDeptId(deptid);
			
			if(isExist != 0){
				total = queryChildren(deptid).size() + 1;
			}
		} 
		return total;
	}

	@Override
	public Integer getCountByLikeQuery(String queryCondition) throws ServiceException {
		String keyWord = "%" + queryCondition + "%";
		int totle = dao.getCountByNamedQuery("sys.hql.querySysDeptByKeyWordCount",
		        Parameter.toList("name", keyWord, "email", keyWord, "address", keyWord, "tel", keyWord));
		return totle;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysDept> queryDeptByLikeQuery(int start, int limit, String keyWord) throws ServiceException {
		if (limit == 0) {
			limit = 5;
		}
		String keyWords = "%" + keyWord + "%";
		return (List<SysDept>) dao.queryByNamedQuery("sys.hql.querySysDeptByKeyWord", start, limit,
		        Parameter.toList("name", keyWords, "email", keyWords, "address", keyWords, "tel", keyWords));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysDept> queryAllDeptByPading(int start, int limit) throws ServiceException {
		if (limit == 0) {
			limit = 5;
		}
		return (List<SysDept>) dao.queryByNamedQuery("sys.hql.queryAllSysDept", start, limit);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<SysDept> queryDeptByDeptId(int start, int limit, String queryCondition) throws ServiceException {
		if (limit == 0) {
			limit = 5;
		}
		
		List<SysDept> queryByNamedQuery = new ArrayList<SysDept>();
		
		int deptId = Integer.valueOf(queryCondition);
		int isExist = queryDeptIsExistByDeptId(deptId);
		if(isExist == 0){
			return null;
		}
		
		SysDept sd_parent = getDeptByDeptId(deptId);
		queryByNamedQuery.add(sd_parent);
		List<SysDept> listDept = (List<SysDept>) dao.queryByNamedQuery("sys.hql.queryChildrenDeptByDeptId", start, limit - 1,
		        Parameter.toList("deptId", deptId));
		
		queryByNamedQuery.addAll(listDept);
		return queryByNamedQuery;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysDept> queryDeptBypagingAndQuerying(int start,int limit,SysDept dept) throws ServiceException {
		
		if(dept.getName()!=null && !dept.getName().equals("")){
			dept.setName("%"+dept.getName()+"%");
		}else{
			dept.setName("%%");
		}
	    List<SysDept> deptList =(List<SysDept>) dao.queryByNamedQuery("sys.hql.queryAllDeptComplex",start, limit,
				   Parameter.toList("isEnabled",dept.getIsEnabled(),"id",dept.getId(),"name",dept.getName(),"email",dept.getName(),
				                 "address",dept.getName(),"tel",dept.getName()));

	    List<SysDept> listdept =new ArrayList<SysDept>();
	    for (int i = 0; i < deptList.size(); i++) {
			listdept = queryChildren(deptList.get(i).getId());
			if(listdept.size() > 0){
				deptList.get(i).setChilds("childs");
			}
		}
		return deptList;
	}

	@Override
	public int getCountBypagingAndQuerying(SysDept dept) throws ServiceException{
		if(dept.getName()!=null && !dept.getName().equals("")){
			dept.setName("%"+dept.getName()+"%");
		}else{
			dept.setName(null);
		}
		// TODO Auto-generated method stub
		int totle = dao.getCountByNamedQuery("sys.hql.queryCountDeptComplex",
		        Parameter.toList("isEnabled",dept.getIsEnabled(),"id",dept.getId(),"name", dept.getName(), "email", dept.getName(), 
		        		"address", dept.getName(), "tel", dept.getName()));
		return totle;
	}

	@Override
	public void recoverDeptState(List<Integer> ids)throws ServiceException {
		// TODO Auto-generated method stub
		if (ids == null) {
			throw new ServiceException("ERR_SYS_013", Messages.getMsg("sys", "ERR_SYS_MSG_DEPT_ID_NULL"));
		}
		for (Integer id : ids) {
			getSysDeptById(id).setIsEnabled(true);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysDept> queryDeptAllByIsEnabled(int start, int limit,boolean isEnabled) throws ServiceException {
		// TODO Auto-generated method stub
		return (List<SysDept>) dao.queryByNamedQuery("sys.hql.queryAllSysDept", start, limit,Parameter.toList("isEnabled", isEnabled));
	}

	@Override
	public int getCountByqueryDeptIsEnabled(boolean isEnabled) throws ServiceException {
		// TODO Auto-generated method stub
		return  dao.getCountByNamedQuery("sys.hql.queryDeptTotle", Parameter.toList("isEnabled", isEnabled));
	}

	@Override
	public SysDept getDeptByDeptId(int deptId) throws ServiceException {
		
		return (SysDept) dao.getSingleResultByNamedQuery("sys.hql.queryDeptByDeptId", Parameter.toList("deptId", deptId));
	}

	@Override
	public int queryDeptIsExistByDeptId(int deptId) throws ServiceException {
		
		return dao.getCountByNamedQuery("sys.hql.queryDeptIsExistByDeptId", Parameter.toList("deptId", deptId));
	}

	@Override
	public SysDept queryParentDeptIsNull() {
		
	  return (SysDept) dao.getSingleResultByNamedQuery("sys.hql.queryParentDeptIsNull");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysDept> queryChildrenRecover(int deptId) throws ServiceException {
		// TODO Auto-generated method stub
		return (List<SysDept>) dao.queryByNamedQuery("sys.hql.queryChildrenRecoverDeptByDeptId",Parameter.toList("deptId", deptId));
	}
	
	@Override
	public SysDept queryDeptById(SysDept dept) throws ServiceException{
		@SuppressWarnings("unchecked")
		List<SysDept> list =  
		       (List<SysDept>) dao.queryByNamedQuery("sys.hql.queryDeptById", 
		    		   Parameter.toList("isEnabled",dept.getIsEnabled(),"id",dept.getId(),
		    				    "name", dept.getName(), "email", dept.getName(), 
				        		"address", dept.getName(), "tel", dept.getName()));
		
		if(list != null && list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean queryDeptByDeptIdNoisEnabled(int deptid) throws ServiceException {
		SysDept  dept = (SysDept) dao.getSingleResultByNamedQuery("sys.hql.queryParentDeptIsNull");
		boolean flag = true;
		//表示机构可用
		if(dept.isEnabled() != true){
			flag = false;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysUser> getUserListByDeptId(int deptId1) throws ServiceException {
		// TODO Auto-generated method stub
		List<SysUser> listUser =null;
		if(deptId1 != 0){
			listUser = (List<SysUser>) dao.queryByNamedQuery("sys.hql.getUserListByDeptId",Parameter.toList("deptId",deptId1));
		}
		return listUser;
	}

}
