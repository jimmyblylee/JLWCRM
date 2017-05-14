/**
 * Project Name : jbp-features-sys <br>
 * File Name : SysFunc.java <br>
 * Package Name : com.asdc.jbp.sys.entity <br>
 * Create Time : Apr 28, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.asdc.jbp.dict.entity.SysDict;

/**
 * ClassName : SysFunc <br>
 * Description : entity of SYS_FUNC <br>
 * Create Time : Apr 28, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Audited
public class SysFunc implements Serializable {

    private static final long serialVersionUID = -8794636321970124215L;

    private Integer id;
    private SysFunc parent;
    private String code;
    private String name;
    private Integer order;
    private String typeCode;
    private SysDict type;
    private String url;
    private String desc;
    private String icon;
    private Boolean isVisible;
    private Boolean isBaseFunc;
    private Boolean isEnabled;
    private Set<SysFunc> children;
    private Boolean hasChildren;
    private Boolean isRoot;
    
    /** 虚拟参数*/
    private String flagType;
    
    public String getFlagType() {
		return flagType;
	}
	public void setFlagType(String flagType) {
		this.flagType = flagType;
	}

	@Transient
    private String visible;
    @Transient
    public String getVisible(){
		return visible;
	}
    @Transient
	public void setVisible(String visible) {
		this.visible = visible;
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
    public SysFunc getParent() {
        return parent;
    }

    /**
     * @param parent
     *            the parent to set
     */
    public void setParent(SysFunc parent) {
        this.parent = parent;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the order
     */
    public Integer getOrder() {
        return order;
    }

    /**
     * @param order
     *            the order to set
     */
    public void setOrder(Integer order) {
        this.order = order;
    }

    /**
     * @return the typeCode
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * @param typeCode
     *            the typeCode to set
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * @return the type
     */
    @NotAudited
    public SysDict getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(SysDict type) {
        this.type = type;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the desc
     */
    @NotAudited
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc
     *            the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the icon
     */
    @NotAudited
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     *            the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return the isVisible
     */
    public Boolean getIsVisible() {
        return isVisible;
    }

    /**
     * @param isVisible
     *            the isVisible to set
     */
    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    /**
     * @return the isBaseFunc
     */
    public Boolean getIsBaseFunc() {
        return isBaseFunc;
    }

    /**
     * @param isBaseFunc
     *            the isBaseFunc to set
     */
    public void setIsBaseFunc(Boolean isBaseFunc) {
        this.isBaseFunc = isBaseFunc;
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

    /**
     * @return the children
     */
    @NotAudited
    public Set<SysFunc> getChildren() {
        return children;
    }

    /**
     * @param children
     *            the children to set
     */
    public void setChildren(Set<SysFunc> children) {
        this.children = children;
    }

    /**
     * @return the hasChildren
     */
    @NotAudited
    public Boolean getHasChildren() {
        return hasChildren;
    }

    /**
     * @param hasChildren
     *            the hasChildren to set
     */
    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    /**
     * @return the isRoot
     */
    @NotAudited
    public Boolean getIsRoot() {
        return isRoot;
    }

    /**
     * @param isRoot
     *            the isRoot to set
     */
    public void setIsRoot(Boolean isRoot) {
        this.isRoot = isRoot;
    }
}
