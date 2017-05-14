/**
 * Project Name jbp-features-sys
 * File Name  GlobalListController.java
 * Package Name com.asdc.jbp.sys.controller
 * Create Time 2016年6月5日
 * Create by name：liuyuanyuan -- email: yuanyuan_liu@asdc.com.cn
 * Copyright  2006-2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jfree.util.Log;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.sys.entity.SysGlobalVariable;
import com.asdc.jbp.sys.entity.SysUser;
import com.asdc.jbp.sys.service.GlobalListService;
import com.ibm.icu.text.SimpleDateFormat;

import java.util.Map;

/**
 * ClassName : UserController <br>
 * Description : 全局参数列表 <br>
 * Create Time : 2016.5.19 <br>
 * Create by : yuanyuan_liu@asdc.com.cn <br>
 *
 */
@Controller("GlobalListController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GlobalListController extends ControllerHelper {
	@Resource
	private GlobalListService globalService;

	/**
	 * Description : 分页查询 <br>
	 * Create Time: 2016年6月5日 <br>
	 * Create by : yuanyuan_liu@asdc.com.cn <br>
	 *
	 * @throws ServiceException
	 */
	public void queryAllGlobalList() throws ServiceException {
		int startData = workDTO.getStart();
		int limitData = workDTO.getLimit();
		SysGlobalVariable sysGlobalVariable = (SysGlobalVariable) workDTO.convertJsonToBeanByKey("pageQuery", SysGlobalVariable.class);
		List<SysGlobalVariable> globalItem = globalService.globalQueryItems(startData, limitData,sysGlobalVariable.getVariableName(),sysGlobalVariable.getIsEnabled());
		Integer totle = globalService.globalQueryTotle(sysGlobalVariable.getVariableName(),sysGlobalVariable.getIsEnabled());	
		workDTO.setResult(globalItem);
		workDTO.setTotle(totle);

	}

	/**
	 * Description : 根据id删除 <br>
	 * Create Time: 2016年6月5日 <br>
	 * Create by : yuanyuan_liu@asdc.com.cn <br>
	 *
	 * @throws ServiceException
	 */
	public void delGlobalList() throws ServiceException {

		String globalItemId = workDTO.get("globalId");
		int globalId = Integer.parseInt(globalItemId);
		globalService.globalDelSingleItems(globalId);

	}

	/**
	 * Description : 添加 <br>
	 * Create Time: 2016年6月5日 <br>
	 * Create by : yuanyuan_liu@asdc.com.cn <br>
	 *
	 * @throws ServiceException
	 */
	public void addGlobalList() throws ServiceException {
		SysGlobalVariable globalItme = workDTO.convertJsonToBeanByKey("globalItme", SysGlobalVariable.class);
		globalService.globalAddItems(globalItme);

	}

	/**
	 * Description : 修改 <br>
	 * Create Time: 2016年6月5日 <br>
	 * Create by : yuanyuan_liu@asdc.com.cn <br>
	 *
	 * @throws ServiceException
	 */
	public void updateGlobalList() throws ServiceException {
		SysGlobalVariable globalItem = workDTO.convertJsonToBeanByKey("globalItme", SysGlobalVariable.class);
		//先查询一下
		SysGlobalVariable sgv = globalService.globalUpdateItems(globalItem);
		workDTO.setResult(sgv);
	}

	public void setGlobalQuery(GlobalListService globalQuery) {
		this.globalService = globalQuery;
	}
	
	public void queryLoginList() throws ServiceException {	
		
		String loginItemName = workDTO.get("loginItem");
		log.info(loginItemName);
		List<SysGlobalVariable> loginItem = globalService.globalLoginItems(loginItemName);	
		workDTO.setResult(loginItem);
	}
	
	public void getGlobalIsExitByVariableValue() throws ServiceException {
		String variableName = workDTO.get("variableName").toString().replace("\"", "");
		SysGlobalVariable  sg = globalService.getGlobalByvariableValue(variableName);
		workDTO.setResult(sg);
	}

	/**
	 * Description : 根据id查找全局参数<br>
	 * Create Time: 2016年6月5日 <br>
	 * Create by : yuanyuan_liu@asdc.com.cn <br>
	 *
	 * @throws ServiceException
	 */
	public void getGlobalById() throws ServiceException {
		String variableName = workDTO.get("variableName").toString().replace("\"", "");
		SysGlobalVariable  sg = globalService.getGlobalById(variableName);
		workDTO.setResult(sg);
	}
	
	/**
	 * Description : 获取系统时间<br>
	 * Create Time: 2016年12月20日 <br>
	 * Create by : yuanyuan_liu@asdc.com.cn <br>
	 *
	 * @throws ServiceException
	 */
	public void getGlobalSysTime() throws ServiceException {
		Date GlobalSysTime = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
		String GlobalSysTimestr = dateFormat.format( GlobalSysTime ); 
		workDTO.setResult(GlobalSysTimestr);
	}
	
	
	

}
