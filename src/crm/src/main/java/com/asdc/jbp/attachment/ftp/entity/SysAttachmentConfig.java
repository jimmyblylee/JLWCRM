/**
 * Project Name : jbp-plugins-file-ftp-impl <br>
 * File Name : SysAttachmentConfig.java <br>
 * Package Name : com.asdc.jbp.attachment.entity <br>
 * Create Time : May 10, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.attachment.ftp.entity;

import java.io.Serializable;

/**
 * ClassName : SysAttachmentConfig <br>
 * Description : entity of attachment ftp configuration <br>
 * Create Time : May 10, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public class SysAttachmentConfig implements Serializable {

    private static final long serialVersionUID = -668276064579724473L;
    
    private String ip;
    private Integer port;
    private String username;
    private String password;
    private String basePath;

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     *            the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * @param port
     *            the port to set
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the basePath
     */
    public String getBasePath() {
        return basePath;
    }

    /**
     * @param basePath
     *            the basePath to set
     */
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
