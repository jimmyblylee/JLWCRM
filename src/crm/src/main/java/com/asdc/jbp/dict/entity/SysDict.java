/**
 * Project Name : jbp-plugin-dict-service <br>
 * File Name : SysDict.java <br>
 * Package Name : com.asdc.jbp.dict.entity <br>
 * Create Time : Apr 14, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.dict.entity;

import java.io.Serializable;

import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

/**
 * ClassName : SysDict <br>
 * Description : entity of SYS_DICT <br>
 * Create Time : Apr 14, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Audited
public class SysDict implements Serializable {

    private static final long serialVersionUID = 4588179303782952600L;

    private Integer id;
    private SysDict parent;
    private Boolean isNature;
    private String nature;
    private String code;
    private String value;
    @NotAudited
    private String label;
    @NotAudited
    private String pinyin;
    @NotAudited
    private String pinyinShort;
    @NotAudited
    private String desc;
    @NotAudited
    private Integer order;
    @NotAudited
    private String remarks;
    private Boolean isEnabled;
    
    @Transient
    private String childs;
    
    @Transient
    public String getChilds() {
		return childs;
	}
    @Transient
	public void setChilds(String childs) {
		this.childs = childs;
	}

	/**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the parent
     */
    public SysDict getParent() {
        return parent;
    }

    /**
     * @param parent 父级编号
     *            the parent to set
     */
    public void setParent(SysDict parent) {
        this.parent = parent;
    }

    /**
     * @return the isNature
     */
    public Boolean getIsNature() {
        return isNature;
    }

    /**
     * @param isNature 是否为字典类型
     *            the isNature to set
     */
    public void setIsNature(Boolean isNature) {
        this.isNature = isNature;
    }

    /**
     * @return the nature
     */
    public String getNature() {
        return nature;
    }

    /**
     * @param nature 类型
     *            the nature to set
     */
    public void setNature(String nature) {
        this.nature = nature;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code 编码
     *            the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value 数据值
     *            the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the label
     */
    @NotAudited
    public String getLabel() {
        return label;
    }

    /**
     * @param label 标签名
     *            the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the pinyin
     */
    @NotAudited
    public String getPinyin() {
        return pinyin;
    }

    /**
     * @param pinyin 数据值拼音
     *            the pinyin to set
     */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    /**
     * @return the pinyinShort
     */
    @NotAudited
    public String getPinyinShort() {
        return pinyinShort;
    }

    /**
     * @param pinyinShort 数据值拼音缩写
     *            the pinyinShort to set
     */
    public void setPinyinShort(String pinyinShort) {
        this.pinyinShort = pinyinShort;
    }

    /**
     * @return the desc
     */
    @NotAudited
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc 描述
     *            the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the order
     */
    @NotAudited
    public Integer getOrder() {
        return order;
    }

    /**
     * @param order 排序
     *            the order to set
     */
    public void setOrder(Integer order) {
        this.order = order;
    }

    /**
     * @return the remarks
     */
    @NotAudited
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks 备注信息
     *            the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the isEnabled
     */
    public Boolean getIsEnabled() {
        return isEnabled;
    }

    /**
     * @param isEnabled 是否有效标记
     *            the isEnabled to set
     */
    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

}
