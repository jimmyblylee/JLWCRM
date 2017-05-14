/**
* Project Name jbp-framework
* File Name Email.java
* Package Name com.asdc.jbp.framework.utils.mail
* Create Time 2016年6月14日
* Create by name：liujing -- email: jing_liu@asdc.com.cn
* Copyright 2006, 2016, ASDC DAI. All rights reserved.
*/
package com.asdc.jbp.framework.utils.mail;
import java.io.Serializable;

/**
 * ClassName : Email <br>
 * Description : email类 <br>
 * Create Time : June 14, 2016 <br>
 * Create by : jing_liu@asdc.com.cn <br>
 *
 */

public class Email implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    public static final String REDIS_KEY_NAME="rediskey";
    
    public Email(){
        
    }
    
    public Email(String mailReceiver, String subject, String content) {
        
        this.mailReceiver = mailReceiver;
        this.subject = subject;
        this.content = content;
    }
    
    private String mailReceiver;
    private String subject;
    private String content;        
    
    public String getMailReceiver() {
        return mailReceiver;
    }
    public void setMailReceiver(String mailReceiver) {
        this.mailReceiver = mailReceiver;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
