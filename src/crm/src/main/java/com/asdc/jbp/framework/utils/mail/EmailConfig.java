/**
* Project Name jbp-framework
* File Name EmailConfig.java
* Package Name com.asdc.jbp.framework.utils.mail
* Create Time 2016年6月14日
* Create by name：liujing -- email: jing_liu@asdc.com.cn
* Copyright 2006, 2016, ASDC DAI. All rights reserved.
*/
package com.asdc.jbp.framework.utils.mail;

import java.io.Serializable;

/**
 * ClassName : EmailConfig <br>
 * Description : email配置 <br>
 * Create Time : June 14, 2016 <br>
 * Create by : jing_liu@asdc.com.cn <br>
 *
 */
public class EmailConfig implements Serializable{
    private static final long serialVersionUID = 1L;
    private EmailConfig(){
        
    }
    private static EmailConfig config=new EmailConfig();
    
    /**
     * 获取默认配置
     * @return
     */
    public static EmailConfig getInstance(){
    	config.mshost="smtp.163.com";   //邮件服务器
        config.msport="25";   //邮件服务器端口
        config.mssender="15936216273@163.com";   //发送方邮箱
        config.mspassword="ww8817883";   //发送方邮箱密码
        return config;
    }
    
    /**
     * 自定义配置
     * @param mshost mialServer 邮件发送者mail配置
     * @param msport 端口
     * @param mssender  邮件发送者 
     * @param mspassword 邮件发送者密码
     */
    public EmailConfig(String mshost, String msport, String mssender,
			String mspassword) {
		super();
		this.mshost = mshost;
		this.msport = msport;
		this.mssender = mssender;
		this.mspassword = mspassword;
	}
	private String mshost;
    private String msport;
    private String mssender;
    private String mspassword;
    private String mstimeout;
    private String msmailurlvalid;
    //邮件链接 project info
    private String idmpurl;
    
    public String getIdmpurl() {
        return idmpurl;
    }

    public String getMsmailurlvalid() {
        return msmailurlvalid;
    }
    
    public String getMstimeout() {
        return mstimeout;
    }
    
    public String getMshost() {
        return mshost;
    }
    
    public String getMsport() {
        return msport;
    }
    
    public String getMssender() {
        return mssender;
    }
    
    public String getMspassword() {
        return mspassword;
    }    
    
}