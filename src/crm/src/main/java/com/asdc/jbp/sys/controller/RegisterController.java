/**
 * Project Name jbp-features-sys
 * File Name RegisterController.java
 * Package Name com.asdc.jbp.sys.controller
 * Create Time 2016年6月5日
 * Create by name：liujing -- email: jing_liu@asdc.com.cn
 * Copyright 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.controller;

import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.sys.entity.SysUser;
import com.asdc.jbp.sys.entity.SysUserPwd;
import com.asdc.jbp.sys.service.DeptMgtService;
import com.asdc.jbp.sys.service.UserMgtService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * ClassName: RegisterController.java <br>
 * Description: 用户注册控制器<br>
 * Create by: jing_liu@asdc.com.cn <br>
 * Create Time: 2016年6月1日<br>
 */
@Controller("RegisterController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RegisterController extends ControllerHelper {

    @Resource
    private UserMgtService userService;

    @Resource
    private DeptMgtService deptService;

    /**
     * Description: 用户注册<br>
     * Create by: jing_liu@asdc.com.cn <br>
     * Create Time: 2016年6月1日<br>
     */

    public void register() throws ServiceException {
        SysUser user = workDTO.convertJsonToBeanByKey("creatUser", SysUser.class);
        //默认机构是总公司
        user.setDept(deptService.getDeptByDeptId(2));
        //默认用户组应该是普通用户组
        if(user.getGroupId() ==null || "".equals(user.getGroupId())){
            List<Integer> groupIdList = new ArrayList<Integer>();
            groupIdList.add(0);
            user.setGroupId(groupIdList);
        }
        SysUserPwd userPwd = workDTO.convertJsonToBeanByKey("creatUser", SysUserPwd.class);
        userPwd.setPassword(userPwd.getPassword());
        user.setSysUserPwd(userPwd);
        userService.creatSysUser(user);
        workDTO.setResult( true);
    }

    /**
     * Description: 检查验证码输入是否正确<br>
     * Create by: jing_liu@asdc.com.cn <br>
     * Create Time: 2016年6月17日<br>
     */
    public void checkVerifyCode() {
        String verifyCode = workDTO.convertJsonToBeanByKey("verifyCode", String.class);
        workDTO.clear();
        workDTO.put("valid", sessionDTO.containsKey("verifyCode") && sessionDTO.get("verifyCode") != null && sessionDTO.get("verifyCode").equals(verifyCode));
    }

    /**
     * Description: 生成验证码<br>
     * Create by: jing_liu@asdc.com.cn <br>
     * Create Time: 2016年6月17日<br>
     */
    public static final String ALLCHAR = "0123456789";
    public String getVerifyCode(){
        StringBuffer verifyCode = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            verifyCode.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return verifyCode.toString();
    }
    /**
     *
     * Description：验证电子邮件
     * @return void
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     * @throws ServiceException
     */
    public void verifyEmail() throws ServiceException{
        String email =  workDTO.get("email").toString().replace("\"", "");
        Integer count = userService.getCountByEmail(email);
        if(count == 0){
            workDTO.setResult(true);
        }else{
            workDTO.setResult(false);
        }
    }
    public void setUserService(UserMgtService userService) {
        this.userService = userService;
    }

}
