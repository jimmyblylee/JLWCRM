/**
 * Project Name jbp-features-sys
 * File Name GlobalListServiceImpl.java
 * Package Name com.asdc.jbp.sys.controller
 * Create Time 2016年6月5日
 * Create by name：yanghaotian -- email: haotian_yang@asdc.com.cn
 * Copyright  2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.jfree.util.Log;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asdc.jbp.framework.dao.JpaOrmOperator;
import com.asdc.jbp.framework.dao.Parameter;
import com.asdc.jbp.framework.exception.ErrLevel;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.message.Messages;
import com.asdc.jbp.framework.utils.BeanUtils;
import com.asdc.jbp.sys.entity.SysGlobalVariable;
import com.asdc.jbp.sys.entity.SysUser;
import com.asdc.jbp.sys.service.GlobalListService;

/**
 * ClassName : GlobalListServerImpl <br>
 * Description :实现 {@link GlobalListService} <br>
 * Create Time : 2016.5.19 <br>
 * Create by : yuanyuan_liu@asdc.com.cn <br>
 *
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional(readOnly = true)
public class GlobalListServiceImpl implements GlobalListService {

	@Resource(name = "SysCommonDao")
	private JpaOrmOperator dao;
	/**
	 * Description : 查询所有列表 <br>
	 * Create Time: 2016.5.19 <br>
	 * Create by : yuanyuan_liu@asdc.com.cn <br>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<SysGlobalVariable> globalQueryItems(int start, int limit, String queryData, Boolean globalIsEnabled) throws ServiceException {
		
		if(queryData != null || "".equals("queryData")){
			queryData = "%" + queryData + "%";
		}
		
		return (List<SysGlobalVariable>) dao.queryByNamedQuery("sys.hql.getGlobalVariable", start, limit,
			        Parameter.toList("queryData", queryData , "globalIsEnabled", globalIsEnabled));

	}
	
	/**
	 * Description : 查询总数 <br>
	 * Create Time: 2016.5.19 <br>
	 * Create by : yuanyuan_liu@asdc.com.cn <br>
	 * 
	 */
	public Integer globalQueryTotle(String queryData, Boolean globalIsEnabled) throws ServiceException {

		if(queryData != null || "".equals("queryData")){
			queryData = "%" + queryData + "%";
		}
		return dao.getCountByNamedQuery("sys.hql.getGlobalTotle", Parameter.toList("queryData", queryData, "globalIsEnabled", globalIsEnabled));
	}
	
	/**
	 * Description : 根据Ids删除<br>
	 * Create Time: 2016.05.24 <br>
	 * Create by : yuanyuan_liu@asdc.com.cn <br>
	 * 
	 * @return
	 * 
	 */
	public void globalDelSingleItems(Integer globalId) throws ServiceException {
		if (globalId == null) {
			throw new ServiceException("ERR_SYS_041", Messages.getMsg("sys", "ERR_SYS_MSG_FUNC_CREATE_CODE_NULL"));
		}
		SysGlobalVariable globalIsEnable=dao.find(SysGlobalVariable.class, globalId);
		if (globalIsEnable.getIsEnabled() == true) {
			globalIsEnable.setIsEnabled(false);			
		}else{
			globalIsEnable.setIsEnabled(true);				
		}
	}

	/**
	 * Description : 添加列表内容<br>
	 * Create Time: 2016.05.24 <br>
	 * Create by : yuanyuan_liu@asdc.com.cn <br>
	 * 
	 */
	public SysGlobalVariable globalAddItems(SysGlobalVariable globalItme) throws ServiceException {
		if (globalItme == null) {
			throw new ServiceException("ERR_SYS_041", Messages.getMsg("sys", "内容不能为空"));
		}
		if (globalItme.getVariableDescribe() == null) {
			throw new ServiceException("ERR_SYS_041", ErrLevel.WARN, Messages.getMsg("sys", "描述不能为空"));
		}
		if (globalItme.getVariableName() == null) {
			throw new ServiceException("ERR_SYS_041", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_FUNC_CREATE_NAME_NULL"));
		}
		if (globalItme.getVariableValue() == null) {
			throw new ServiceException("ERR_SYS_041", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_FUNC_CREATE_TYPE_NULL"));
		}
		if (globalItme.getIsEnabled() == null) {
			globalItme.setIsEnabled(true);
		}
		dao.persist(globalItme);
		return globalItme;
	}
	/**
	 * Description : 更新列表内容<br>
	 * Create Time: 2016.05.24 <br>
	 * Create by : yuanyuan_liu@asdc.com.cn <br>
	 * 
	 */
	public SysGlobalVariable globalUpdateItems(SysGlobalVariable globalItme) throws ServiceException {
		if (globalItme == null) {
			throw new ServiceException("ERR_SYS_042", Messages.getMsg("sys", "内容不能为空"));
		}
		if (globalItme.getVariableID() == null) {
			throw new ServiceException("ERR_SYS_042", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_FUNC_CREATE_CODE_NULL"));
		}
		if (globalItme.getVariableDescribe() == null) {
			throw new ServiceException("ERR_SYS_042", ErrLevel.WARN, Messages.getMsg("sys", "描述不能为空"));
		}
		if (globalItme.getVariableName() == null) {
			throw new ServiceException("ERR_SYS_042", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_FUNC_CREATE_NAME_NULL"));
		}
		if (globalItme.getVariableValue() == null) {
			throw new ServiceException("ERR_SYS_042", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_FUNC_CREATE_TYPE_NULL"));
		}
		SysGlobalVariable globalItmeInDB = dao.find(SysGlobalVariable.class, globalItme.getVariableID());

		SysGlobalVariable sgv = new SysGlobalVariable();
		//说明不用进行查询验证
		if(globalItmeInDB.getVariableName().equals(globalItme.getVariableName())){
			BeanUtils.copyProperties(globalItme, globalItmeInDB, "isEnabled");
			if (globalItme.getIsEnabled() == null) {
				globalItme.setIsEnabled(true);
			}
		}else{
			sgv= getGlobalByvariableValue(globalItme.getVariableName());
			if(sgv.getIsEnabled() == null){
				BeanUtils.copyProperties(globalItme, globalItmeInDB, "isEnabled");
				if (globalItme.getIsEnabled() == null) {
					globalItme.setIsEnabled(true);
				}
			}
		}
		return sgv;
	}
	
	/**
	 * Description : 查询登陆系统名称参数 <br>
	 * Create Time: 2016.7.13 <br>
	 * Create by : yuanyuan_liu@asdc.com.cn <br>
	 * 
	 */
	@SuppressWarnings("unchecked")
    public List<SysGlobalVariable> globalLoginItems(String queryData) throws ServiceException{		
		return (List<SysGlobalVariable>) dao.queryByNamedQuery("sys.hql.getGlobalLogin",Parameter.toList("queryData", queryData));
	}

	@Override
	public SysGlobalVariable getGlobalByvariableValue(String variableName) throws ServiceException {
	   Integer totle =   dao.getCountByNamedQuery("sys.hql.getCountByvariableValue",Parameter.toList("variableName", variableName));
	   SysGlobalVariable sgv = new SysGlobalVariable();
		if(totle == 0){
			//返回true  -->表示可以添加
			sgv.setIsEnabled(null);
		}else{
			sgv = (SysGlobalVariable) dao.getSingleResultByNamedQuery("sys.hql.getGlobalByvariableValue", Parameter.toList("variableName", variableName));
		}
	   return sgv;
	}
	@Override
	public boolean getGlobalIsExit(String variableName) throws ServiceException {
	   Integer totle =   dao.getCountByNamedQuery("sys.hql.getCountByvariableValue",Parameter.toList("variableName", variableName));
	   boolean flag = true;
	   if(totle != 0){
		   flag = false;
	   }
	   return flag;
	}
	@Override
	public SysGlobalVariable getGlobalById(String variableName) throws ServiceException {
		// TODO Auto-generated method stub
		 SysGlobalVariable sgv =  (SysGlobalVariable) dao.getSingleResultByNamedQuery("sys.hql.getGlobalByvariableValue", Parameter.toList("variableName", variableName));
	     return sgv;
	}
}
