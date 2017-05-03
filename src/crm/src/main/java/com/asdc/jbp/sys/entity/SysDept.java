/*
 * Project Name : jbp-features-sys <br>
 * File Name : SysDept.java <br>
 * Package Name : com.asdc.jbp.sys.entity <br>
 * Create Time : Apr 28, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.entity;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * ClassName : SysDept <br>
 * Description : entity of SYS_DEPT <br>
 * Create Time : Apr 28, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Entity
@Table(name = "SYS_DEPT")
@SuppressWarnings("unused")
public class SysDept implements Serializable {

    private static final long serialVersionUID = 8923542512586932351L;

    @Id
    @Column(name = "DEPT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPT_PARENT_ID")
    private SysDept parent;
    @Column(name = "DEPT_NAME")
    private String name;
    @Column(name = "DEPT_TEL")
    private String tel;
    @Column(name = "DEPT_FAX")
    private String fax;
    @Column(name = "DEPT_EMAIL")
    private String email;
    @Column(name = "DEPT_URL")
    private String url;
    @Column(name = "DEPT_ADDRESS")
    private String address;
    @Column(name = "DEPT_POSTALCODE")
    private String postalCode;
    @Column(name = "IS_ENABLED")
    private Boolean isEnabled;
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<SysDept> children = new LinkedHashSet<>();
    @Formula("(SELECT CASE WHEN (COUNT(1) > 0) THEN 1 ELSE 0 END FROM SYS_DEPT DEPT WHERE DEPT.DEPT_PARENT_ID = DEPT_ID)")
    private Boolean hasChildren;
    @Column(name = "DEPT_ORDER_BY")
    private Integer sort;

    @Transient
    private String childs;
    @Transient
    public String getChilds() {
        return childs;
    }
    @Transient
    public void setChilds(@SuppressWarnings("SameParameterValue") String childs) {
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
    public SysDept getParent() {
        return parent;
    }

    /**
     * @param parent
     *            the parent to set
     */
    public void setParent(SysDept parent) {
        this.parent = parent;
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
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel
     *            the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax
     *            the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode
     *            the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the isEnabled
     */
    public Boolean isEnabled() {
        return isEnabled;
    }

    /**
     * @param isEnabled
     *            the isEnabled to set
     */
    public void setEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * @return the isEnabled
     */
    public Boolean getIsEnabled() {
        return isEnabled;
    }

    /**
     * @param isEnabled the isEnabled to set
     */
    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * @return the children
     */
    public Set<SysDept> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(Set<SysDept> children) {
        this.children = children;
    }

    /**
     * @return the hasChildren
     */
    public Boolean getHasChildren() {
        return hasChildren;
    }

    /**
     * @param hasChildren the hasChildren to set
     */
    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }


    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "SysDept [id=" + id + ", name=" + name + ", tel=" + tel + ", fax=" + fax + ", email=" + email + ", url=" + url + ", address=" + address
            + ", postalCode=" + postalCode + ", isEnabled=" + isEnabled + ", sort=" + sort + "]";
    }



}
