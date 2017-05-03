/*
 * Project Name : jbp-features-sys <br>
 * File Name : SysUser.java <br>
 * Package Name : com.asdc.jbp.sys.entity <br>
 * Create Time : Apr 28, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.entity;

import com.asdc.jbp.dict.entity.SysDict;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * ClassName : SysUser <br>
 * Description : entity of SYS_USER <br>
 * Create Time : Apr 28, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Entity
@Table(name = "SYS_USER")
@SuppressWarnings("unused")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 870831310921606782L;

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPT_ID")
    private SysDept dept;
    @Column(name = "USER_NAME")
    private String name;
    @Column(name = "LOGIN_ACCOUNT")
    private String account;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumnsOrFormulas({
        @JoinColumnOrFormula(column = @JoinColumn(name = "USER_SEX", referencedColumnName = "DICT_CODE")),
        @JoinColumnOrFormula(formula = @JoinFormula(referencedColumnName = "DICT_NATURE", value = "'SEX'"))})
    private SysDict sex;
    @Column(name = "USER_BIRTHDAY")
    private Date birthday;
    @Column(name = "USER_TEL")
    private String tel;
    @Column(name = "USER_FIXED_TEL")
    private String fixedTel;
    @Column(name = "USER_EMAIL")
    private String email;
    @Column(name = "USER_MAILING_ADDRESS")
    private String address;
    @Column(name = "USER_POSTALCODE")
    private String postalCode;
    @Column(name = "USER_TITLE")
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumnsOrFormulas({
        @JoinColumnOrFormula(column = @JoinColumn(name = "USER_ID_TYPE", referencedColumnName = "DICT_CODE")),
        @JoinColumnOrFormula(formula = @JoinFormula(referencedColumnName = "DICT_NATURE", value = "'USER_ID_TYPE'"))})
    private SysDict idType;
    @Column(name = "USER_ID_NUMBER")
    private String idNum;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumnsOrFormulas({
        @JoinColumnOrFormula(column = @JoinColumn(name = "USER_TYPE", referencedColumnName = "DICT_CODE")),
        @JoinColumnOrFormula(formula = @JoinFormula(referencedColumnName = "DICT_NATURE", value = "'USER_TYPE'"))})
    private SysDict type;
    @Column(name = "IS_ENABLED")
    private Boolean isEnabled;
    @Transient
    private SysUserPhoto sysUserPhoto;
    @Transient
    private List<Integer> groupId;
    @Transient
    private SysUserPwd sysUserPwd;
    @Column(name = "USER_REMARK")
    private String remark;
    @Column(name = "LAST_TIME")
    private Date lastTime;
    @Transient
    private Integer deptId;

    /**
     * Get the value of deptId.
     *
     * @return value of deptId
     */
    public Integer getDeptId() {
        return deptId;
    }

    /**
     * Set the value of deptId.
     *
     * @param deptId the deptId
     */
    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
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
     * @return the dept
     */
    public SysDept getDept() {
        return dept;
    }

    /**
     * @param dept
     *            the dept to set
     */
    public void setDept(SysDept dept) {
        this.dept = dept;
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
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account
     *            the account to set
     */
    public void setAccount(String account) {
        this.account = account;
    }


    /**
     * @return the sex
     */
    public SysDict getSex() {
        return sex;
    }

    /**
     * @param sex
     *            the sex to set
     */
    public void setSex(SysDict sex) {
        this.sex = sex;
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     *            the birthday to set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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
     * @return the fixedTel
     */
    public String getFixedTel() {
        return fixedTel;
    }

    /**
     * @param fixedTel
     *            the fixedTel to set
     */
    public void setFixedTel(String fixedTel) {
        this.fixedTel = fixedTel;
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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the idType
     */
    public SysDict getIdType() {
        return idType;
    }

    /**
     * @param idType
     *            the idType to set
     */
    public void setIdType(SysDict idType) {
        this.idType = idType;
    }

    /**
     * @return the idNum
     */
    public String getIdNum() {
        return idNum;
    }

    /**
     * @param idNum
     *            the idNum to set
     */
    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    /**
     * @return the type
     */
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
     * @return the sysUserPhoto
     */
    public SysUserPhoto getSysUserPhoto() {
        return sysUserPhoto;
    }

    /**
     * @param sysUserPhoto the sysUserPhoto to set
     */
    public void setSysUserPhoto(SysUserPhoto sysUserPhoto) {
        this.sysUserPhoto = sysUserPhoto;
    }

    /**
     * @return the groupId
     */
    public List<Integer> getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(List<Integer> groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the sysUserPwd
     */
    public SysUserPwd getSysUserPwd() {
        return sysUserPwd;
    }

    /**
     * @param sysUserPwd the sysUserPwd to set
     */
    public void setSysUserPwd(SysUserPwd sysUserPwd) {
        this.sysUserPwd = sysUserPwd;
    }

}
