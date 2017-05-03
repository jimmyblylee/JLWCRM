/*
 * Project Name jbp-features-sys
 * File Name ForgetPasswordController.java
 * Package Name com.asdc.jbp.sys.controller
 * Create Time 2016年6月16日
 * Create by name：liujing -- email: jing_liu@asdc.com.cn
 * Copyright 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.controller;

import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.sys.service.UserMgtService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

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
     * @return String
     * @author name：liujing
     **/
    @SuppressWarnings({"WeakerAccess", "unused"})
    public String resetPassword(String email) throws ServiceException{
        Integer userId=userService.getSysUserIdByUserEmail(email);
        return userService.generateUserPassword(userId);
    }


}
