/**
* Project Name jbp-framework
* File Name MailUtil.java
* Package Name com.asdc.jbp.framework.utils.mail
* Create Time 2016年6月14日
* Create by name：liujing -- email: jing_liu@asdc.com.cn
* Copyright 2006, 2016, ASDC DAI. All rights reserved.
*/
package com.asdc.jbp.framework.utils.mail;

import java.rmi.ServerException;
import java.util.Calendar;
import java.util.Date;

/**
 * ClassName : MailUtil <br>
 * Description : 发送邮件需要使用的通用方法 <br>
 * Create Time : June 14, 2016 <br>
 * Create by : jing_liu@asdc.com.cn <br>
 *
 */
public class MailUtil {
    public static final String DEFAULT_ALGORITHM                = "SHA-1";
    public static final String VMENCODING                       = "UTF-8";

    /**
     * Description： 获取日期
     * 
     * @param date
     * @return
     * @return String
     * @author name：liujing
     * @throws ServerException 
     **/
    public static String getDateSimpleStr(Date yearDate) throws ServerException {
        if (yearDate == null) {
            throw new ServerException("参数【yearDate】不能为空");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(yearDate);
        StringBuilder sb = new StringBuilder();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH) + 1;
        int d = c.get(Calendar.DATE);
        sb.append(y).append("年");
        if (m < 10) {
            sb.append("0");
        }
        sb.append(m).append("月");
        if (d < 10) {
            sb.append("0");
        }
        sb.append(d).append("日");
        return sb.toString();
    }

    /**
     * Description： 获取时间
     * 
     * @param date
     * @return
     * @return String
     * @author name：liujing
     * @throws ServerException 
     **/
    public static String getTimeSimpleStr(Date hourDate) throws ServerException {
        if (hourDate == null) {
        	throw new ServerException("参数【hourDate】不能为空");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(hourDate);
        StringBuilder sb = new StringBuilder();
        int h = c.get(Calendar.HOUR_OF_DAY);
        int m = c.get(Calendar.MINUTE);
        //int s=c.get(Calendar.SECOND);
        if (h < 10) {
            sb.append("0");
        }
        sb.append(h).append(":");
        if (m < 10) {
            sb.append("0");
        }
        sb.append(m);
        return sb.toString();
    }

    /** 
    * Description：
    *   发送HTML格式邮件
    * @param email
    * @param config
    * @return
    		void
    * @author name：liujing
     * @throws ServerException 
     **/
    public static void sendWithIrpc(Email email, EmailConfig config) throws ServerException {
        if (email == null || config == null) {
            throw new ServerException( "参数【email】或者【config】不能为空");
        }
        //这个类主要是设置邮件  
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost(config.getMshost());
        mailInfo.setMailServerPort(config.getMsport());
        mailInfo.setValidate(true);
        mailInfo.setUserName(config.getMssender());
        mailInfo.setPassword(config.getMspassword());//您的邮箱密码   
        mailInfo.setFromAddress(config.getMssender());
        mailInfo.setToAddress(email.getMailReceiver());
        mailInfo.setSubject(email.getSubject());
        mailInfo.setContent(email.getContent());
        //这个类主要来发送邮件  
        //SimpleMailSender sms = new SimpleMailSender();  
        MailSend.sendHtmlMail(mailInfo);//发送HTML格式  
    }

    public static String[] getSubjectAndContentWithMark(String emailTemplateContent) {
        //log.debug("template content: "+emailTemplateContent);
        String[] sac = emailTemplateContent.split("\\*{5}");
        sac[0] = sac[0].replaceFirst("\\r\\n", "");
        return sac;
    }   

}
