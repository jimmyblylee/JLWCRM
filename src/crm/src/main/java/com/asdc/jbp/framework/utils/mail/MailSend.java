/**
* Project Name jbp-framework
* File Name MailSend.java
* Package Name com.asdc.jbp.framework.utils.mail
* Create Time 2016年6月14日
* Create by name：liujing -- email: jing_liu@asdc.com.cn
* Copyright 2006, 2016, ASDC DAI. All rights reserved.
*/
package com.asdc.jbp.framework.utils.mail;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * ClassName : MailSend <br>
 * Description : 发送邮件方法 <br>
 * Create Time : June 14, 2016 <br>
 * Create by : jing_liu@asdc.com.cn <br>
 *
 */
public class MailSend {
	
	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 1,
            TimeUnit.MINUTES,
            new ArrayBlockingQueue<Runnable>(1024),
            Executors.defaultThreadFactory(),
            new DiscardOldestPolicy());
	/**
     * 以文本格式发送邮件
     * 
     * @param mailInfo 待发送的邮件的信息
     */
    public static void sendTextMail(MailSenderInfo mailInfo) {
        // 判断是否需要身份认证 
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        if (mailInfo.isValidate()) {
            // 如果需要身份认证，则创建一个密码验证器 
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session 
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            // 根据session创建一个邮件消息 
            final Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址 
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // 设置邮件消息的发送者 
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中 
            Address to = new InternetAddress(mailInfo.getToAddress());
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            // 设置邮件消息的主题 
            mailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件消息发送的时间 
            mailMessage.setSentDate(new Date());
            // 设置邮件消息的主要内容 
            String mailContent = mailInfo.getContent();
            mailMessage.setText(mailContent);
            
          
            // 发送邮件 
//            executor.submit(new Runnable() {
//
//                public void run() {
//                    try {
                        Transport.send(mailMessage);
//                    } catch (MessagingException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            });
        } catch (MessagingException ex) {
            throw new RuntimeException(ex);

        }
    }

    /**
     * 以HTML格式发送邮件
     * 
     * @param mailInfo 待发送的邮件信息
     */
    public static void sendHtmlMail(MailSenderInfo mailInfo) {
        // 判断是否需要身份认证 
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        //如果需要身份认证，则创建一个密码验证器  
        if (mailInfo.isValidate()) {
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session 
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            // 根据session创建一个邮件消息 
            final Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址 
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // 设置邮件消息的发送者 
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中 
            Address to = new InternetAddress(mailInfo.getToAddress());
            // Message.RecipientType.TO属性表示接收者的类型为TO 
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            // 设置邮件消息的主题 
            mailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件消息发送的时间 
            mailMessage.setSentDate(new Date());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象 
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart 
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容 
            html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            // 将MiniMultipart对象设置为邮件内容 
            mailMessage.setContent(mainPart);
            // 发送邮件 
            executor.submit(new Runnable() {
                public void run() {
                    try {
                        Transport.send(mailMessage);
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (MessagingException ex) {
            throw new RuntimeException(ex);

        }
    }
}
