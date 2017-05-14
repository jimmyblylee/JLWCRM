package com.asdc.jbp.sys.entity;

import java.io.Serializable;

import org.hibernate.envers.Audited;

/**
 * <p>
 * 文件名称: SysGlobalVariable.java
 * </p>
 * 
 * <p>
 * 文件功能: SYS_GLOBAL_VARIABLE表对应的实体
 * </p>
 *
 * <p>
 * 编程者: yuruixin
 * </p>
 * 
 * <p>
 * 邮件: ruixin_yu@asdc.com.cn;
 * </p>
 * 
 * <p>
 * 初作时间: 2016年5月17日 上午9:28:24
 * </p>
 * 
 * <p>
 * 版本: version 1.0
 * </p>
 *
 * <p>
 * 输入说明:
 * </p>
 *
 * <p>
 * 输出说明:
 * </p>
 *
 * <p>
 * 程序流程:
 * </p>
 * 
 * <p>=
 * ===========================================
 * </p>
 * <p>
 * 修改序号:
 * </p>
 * <p>
 * 时间:
 * </p>
 * <p>
 * 修改者:
 * </p>
 * <p>
 * 修改内容:
 * </p>
 * <p>=
 * ===========================================
 * </p>
 */
// @Audited
public class SysGlobalVariable implements Serializable {

	private static final long serialVersionUID = 8369343045423948465L;
	private Integer variableID;
	private String variableDescribe;
	private String variableName;
	private String variableValue;
	private Boolean isEnabled;

	
	public SysGlobalVariable() {
	}
	
	public SysGlobalVariable(String variableDescribe) {
		this.variableDescribe = variableDescribe;
	}

	/**
	 * @return the variableID
	 */
	public Integer getVariableID() {
		return variableID;
	}

	/**
	 * @param variableID
	 *            the variableID to set
	 */
	public void setVariableID(Integer variableID) {
		this.variableID = variableID;
	}

	/**
	 * @return the variableDescribe
	 */
	public String getVariableDescribe() {
		return variableDescribe;
	}

	/**
	 * @param variableDescribe
	 *            the variableDescribe to set
	 */
	public void setVariableDescribe(String variableDescribe) {
		this.variableDescribe = variableDescribe;
	}

	/**
	 * @return the variableName
	 */
	public String getVariableName() {
		return variableName;
	}

	/**
	 * @param variableName
	 *            the variableName to set
	 */
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	/**
	 * @return the variableValue
	 */
	public String getVariableValue() {
		return variableValue;
	}

	/**
	 * @param variableValue
	 *            the variableValue to set
	 */
	public void setVariableValue(String variableValue) {
		this.variableValue = variableValue;
	}

	/**
	 * @return the isEnabled
	 */
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	/**
	 * @param isEnabled
	 *            the isEnabled to set
	 */

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	

}
