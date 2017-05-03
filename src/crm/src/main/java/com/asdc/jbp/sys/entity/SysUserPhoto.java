/*
 * Project Name : jbp-features-sys <br>
 * File Name : SysUserPhoto.java <br>
 * Package Name : com.asdc.jbp.sys.entity <br>
 * Create Time : Apr 29, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;

/**
 * ClassName : SysUserPhoto <br>
 * Description : entity of user photo <br>
 * Create Time : Apr 29, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Entity
@Table(name = "SYS_USER")
public class SysUserPhoto implements Serializable {

    private static final long serialVersionUID = -5833570425573395847L;

    @Id
    @Column(name = "USER_ID")
    private Integer id;
    @Column(name = "USER_PHOTO")
    private Blob photo;
    @Transient
    private String photoStr;

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
     * @return the photo
     */
    public Blob getPhoto() {
        return photo;
    }

    /**
     * @param photo
     *            the photo to set
     */
    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

	/**
	 * @return the photoStr
	 */
	public String getPhotoStr() {
		return photoStr;
	}

	/**
	 * @param photoStr the photoStr to set
	 */
	@SuppressWarnings("unused")
    public void setPhotoStr(String photoStr) {
		this.photoStr = photoStr;
	}

}
