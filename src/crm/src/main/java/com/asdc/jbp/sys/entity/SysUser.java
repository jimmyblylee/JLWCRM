/**
 * Project Name : jbp-features-sys <br>
 * File Name : SysUser.java <br>
 * Package Name : com.asdc.jbp.sys.entity <br>
 * Create Time : Apr 28, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.asdc.jbp.dict.entity.SysDict;

/**
 * ClassName : SysUser <br>
 * Description : entity of SYS_USER <br>
 * Create Time : Apr 28, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Audited
public class SysUser implements Serializable {

    private static final long serialVersionUID = 870831310921606782L;

    private Integer id;
    private SysDept dept;
    private String name;
    private String account;
    private String sexCode;
    private SysDict sex;
    private Date birthday;
    private String tel;
    private String fixedTel;
    private String email;
    private String address;
    private String postalCode;
    private String title;
    private String idTypeCode;
    private SysDict idType;
    private String idNum;
    private String typeCode;
    private SysDict type;
    private Boolean isEnabled;
    private Integer deptId;
    private SysUserPhoto sysUserPhoto;
    private List<Integer> groupId;
    private SysUserPwd sysUserPwd;
    private String remark;
    private Date lastTime;

	@Override
	public String toString() {
		return "SysUser [id=" + id + ", dept=" + dept + ", name=" + name + ", account=" + account + ", sexCode=" + sexCode + ", sex=" + sex + ", birthday="
		        + birthday + ", tel=" + tel + ", fixedTel=" + fixedTel + ", email=" + email + ", address=" + address + ", postalCode=" + postalCode + ", title="
		        + title + ", idTypeCode=" + idTypeCode + ", idType=" + idType + ", idNum=" + idNum + ", typeCode=" + typeCode + ", type=" + type
		        + ", isEnabled=" + isEnabled + ", deptId=" + deptId + ", sysUserPhoto=" + sysUserPhoto + ", groupId=" + groupId + ", sysUserPwd=" + sysUserPwd
		        + ", remark=" + remark + ", ------------- lastTime=" + lastTime + ", typeId=" + typeId + "]";
	}
	/**
	 * 增加无参构造方法
	 */
	public SysUser(){
	}
	public SysUser(Integer id) {
		this.id = id;
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
	 * @return the typeId
	 */
	public Integer getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Integer typeId;

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
     * @return the sexCode
     */
    public String getSexCode() {
        return sexCode;
    }

    /**
     * @param sexCode
     *            the sexCode to set
     */
    public void setSexCode(String sexCode) {
        this.sexCode = sexCode;
    }

    /**
     * @return the sex
     */
    @NotAudited
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
     * @return the idTypeCode
     */
    public String getIdTypeCode() {
        return idTypeCode;
    }

    /**
     * @param idTypeCode
     *            the idTypeCode to set
     */
    public void setIdTypeCode(String idTypeCode) {
        this.idTypeCode = idTypeCode;
    }

    /**
     * @return the idType
     */
    @NotAudited
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
	 * @return the deptId
	 */
	public Integer getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
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
