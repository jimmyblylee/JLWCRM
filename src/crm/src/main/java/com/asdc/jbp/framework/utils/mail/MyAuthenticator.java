/**
* Project Name jbp-framework
* File Name MyAuthenticator.java
* Package Name com.asdc.jbp.framework.utils.mail
* Create Time 2016年6月14日
* Create by name：liujing -- email: jing_liu@asdc.com.cn
* Copyright 2006, 2016, ASDC DAI. All rights reserved.
*/
package com.asdc.jbp.framework.utils.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * ClassName : MyAuthenticator <br>
 * Description : 发送方的用户名和密码 <br>
 * Create Time : June 14, 2016 <br>
 * Create by : jing_liu@asdc.com.cn <br>
 *
 */
public class MyAuthenticator extends Authenticator{
	String userName=null;
	String password=null;
	 
	public MyAuthenticator(){
	}
	public MyAuthenticator(String username, String password) { 
		this.userName = username; 
		this.password = password; 
	} 
	protected PasswordAuthentication getPasswordAuthentication(){
		return new PasswordAuthentication(userName, password);
	}
	
}
 
