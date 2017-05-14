/**
* Project Name jbp-features-sys
* File Name ForgetPasswordController.java
* Package Name com.asdc.jbp.sys.controller
* Create Time 2016年6月16日
* Create by name：liujing -- email: jing_liu@asdc.com.cn
* Copyright 2006, 2016, ASDC DAI. All rights reserved.
*/
package com.asdc.jbp.sys.controller;
import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.utils.mail.Email;
import com.asdc.jbp.framework.utils.mail.EmailConfig;
import com.asdc.jbp.framework.utils.mail.MailUtil;
import com.asdc.jbp.sys.service.UserMgtService;

/**
 * ClassName : ForgetPasswordController <br>
 * Description : 忘记密码控制器 <br>
 * Create Time : June 16, 2016 <br>
 * Create by : jing_liu@asdc.com.cn <br>
 *
 */
@Controller("ForgetPasswordController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ForgetPasswordController extends ControllerHelper{
	
	@Resource
    private UserMgtService userService;
	
	/**
	 * Description：通过邮箱重置用户密码，并返回新密码
	 * 
	 * @throws ServiceException
	 * @return String
	 * @author name：liujing
	 **/
	public String resetPassword(String email) throws ServiceException{				
		Integer userId=userService.getSysUserIdByUserEmail(email);
        String pwd=userService.generateUserPassword(userId);
        return pwd;          
	}
	
	/**
	 * Description：通过邮件给用户发送新密码
	 * 
	 * @throws Exception
	 * @throws ServiceException
	 * @return void
	 * @author name：liujing
	 **/
    public void sendEmail() throws Exception, ServiceException {
    	String emailForgetPwd=workDTO.get("emailForgetPwd").toString().replace("\"", "");	    	
    	String pwd=resetPassword(emailForgetPwd);
    	EmailConfig config = EmailConfig.getInstance();
        Email em = new Email();
        em.setMailReceiver(emailForgetPwd); 
        em.setSubject("新密码");
        em.setContent("您的新密码是："+pwd);
        log.debug("邮件内容{}",em.getContent());
        MailUtil.sendWithIrpc(em, config);
        workDTO.setResult(true);	       
    }	  

  
}
