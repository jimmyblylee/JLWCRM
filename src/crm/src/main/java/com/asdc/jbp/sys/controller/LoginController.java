/**

 * Project Name : jbp-features-sys <br>
 * File Name : LoginController.java <br>
 * Package Name : com.asdc.jbp.sys.controller <br>
 * Create Time : May 1, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Controller;

import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.context.ActionContext;
import com.asdc.jbp.framework.exception.ErrLevel;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.message.Messages;
import com.asdc.jbp.framework.utils.VerifyCodeUtils;
import com.asdc.jbp.sys.entity.SysUser;
import com.asdc.jbp.sys.service.AuthenticationService;
import com.asdc.jbp.sys.service.AuthorizationService;
import com.asdc.jbp.sys.service.UserMgtService;
import com.asdc.jbp.sys.token.Func;
import com.asdc.jbp.sys.token.FuncTree;
import com.asdc.jbp.sys.token.Token;

import sun.misc.BASE64Encoder;


/**
 * ClassName : LoginController <br>
 * Description : 登陆 <br>
 * Create Time : May 1, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Controller("LoginController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LoginController extends ControllerHelper {

    @Resource
    private AuthenticationService authenticationService;
    @Resource
    private AuthorizationService authorizationService;
    @Resource
    private UserMgtService userService;
    
    protected HttpServletRequest servletRequest;
    protected HttpServletResponse servletResponse;
    
    
    @SuppressWarnings("restriction")
	static BASE64Encoder encoder = new sun.misc.BASE64Encoder();  
	/**
	 * 
	 * Description：前台获取验证码
	 * @throws ServiceException
	 * @return void
	 * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
	 * @throws IOException 
	 *
	 */
	@SuppressWarnings("restriction")
	public void getVerifyImg() throws ServiceException, IOException {
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4).toLowerCase();
        byte [] b  = VerifyCodeUtils.outputImageByte(200, 80, null, verifyCode);
        String str =  encoder.encode(b);
        Map<String,String> map = new HashMap<String,String>();
        map.put("number", verifyCode);
        map.put("image", str);
    	workDTO.put("result",map);		
    }

	public void checkVerifyImg(){
        String rand = workDTO.get("rand");
        rand = rand.replaceAll("\"", "");
        HttpSession session = getServletRequest().getSession(false);
        String randSession = (String) session.getAttribute("verifyCode");
        if(randSession !=null && randSession.equals(rand.toLowerCase())){
        	 workDTO.put("valid", true);
        }else{
        	 workDTO.put("valid", false);
        }
    }
	/**
	 * Description : 检查用户名和密码 <br>
	 * Create Time : May 1, 2016 <br>
	 * Create by : xiangyu_li@asdc.com.cn <br>
	 *
	 */	
    public void checkAccountSys() throws ServiceException {  
    	String userName = workDTO.convertJsonToBeanByKey("account", String.class);
    	String password = workDTO.convertJsonToBeanByKey("password", String.class);
        Integer userId = authenticationService.checkAccountAndPwd(userName,password);
        registerTokenIntoSession(userId);
    }

    private void registerTokenIntoSession(Integer userId) throws ServiceException {
        Token token = authorizationService.getUserTokenByUserId(userId);
        Authentication auth = new PreAuthenticatedAuthenticationToken(token.getUser().getAccount(), token.getUser(), getAuthorities(token.getFunc()));
        auth.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(auth);
        sessionDTO.put("token", token);
        servletRequest.getSession().setAttribute("sessionUser",token.getUser().getAccount());
        workDTO.clear();
        workDTO.put("success", true);
        SysUser sysUser = userService.getSysUserById(userId);
  
        log.debug("==================上次登录时间：======="+sysUser.getLastTime());
        workDTO.put("logintime",sysUser.getLastTime());
        //存本次登录时间
        Date lastTime = new Date();
        sysUser.setLastTime(lastTime);
        userService.updateSysUserLastTime(sysUser);
        workDTO.put("token", token);
    }
    
    private List<GrantedAuthority> getAuthorities(FuncTree funcTree) {
        List<GrantedAuthority> result = new LinkedList<>();
        for (Func func : funcTree.getAllCloneSubFuncs()){
        	if(func.getCode()==null || "".equals(func.getCode())){
        		func.setCode("null");
        	}
            result.add(new SimpleGrantedAuthority(func.getCode()));
        }
        return result;
    }

    public void checkAccountDev() throws ServiceException {
        if (!"dev".equals(System.getenv())) {
            throw new ServiceException("ERR_SYS_100", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_NOT_ENV_MODE"));
        }        
        // 获得对应用户信息
        Integer userId = userService.getSysUserIdByUserAccount(workDTO.<String> get("account"));
        registerTokenIntoSession(userId);
    }

    public void registerOrGetCurrentToken() throws ServiceException{
        if (!sessionDTO.containsKey("token")){        	
            workDTO.put("token","token");
        }else{
            Token token = sessionDTO.get("token");
            if (!workDTO.containsKey("token")) {
                workDTO.put("token", token);
            }
            log.trace("{} is in the session", token.getUser().getName());
            log.trace("Authorities are {}", token.getFunc().getAllCloneSubFuncs());
        }
    }

    public void logout() { 
    	servletRequest.getSession().removeAttribute("sessionUser");
        sessionDTO.remove("token");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(ActionContext.getContext().getServletRequest(), ActionContext.getContext().getServletResponse(), auth);
    }
    
    public void queryUserIsExitByAccountAndPwd() throws ServiceException {  
    	String userName = workDTO.convertJsonToBeanByKey("account", String.class);
    	String password = workDTO.convertJsonToBeanByKey("password", String.class);
    	log.debug("userName:"+userName+",password:"+password);
        Integer userId = authenticationService.checkAccountAndPwd(userName,password); 
        if(userId.equals(null)){
        	 workDTO.put("error", false);
        }else{
        	 workDTO.put("success", true);
        }
    }
    
 
    public void registerTokenCookie() throws ServiceException {
    	int userId = workDTO.convertJsonToBeanByKey("userId", Integer.class);
    	String dataTime = workDTO.convertJsonToBeanByKey("dateTime", String.class);
    	
        Token token = authorizationService.getUserTokenByUserId(userId);
        Authentication auth = new PreAuthenticatedAuthenticationToken(token.getUser().getAccount(), token.getUser(), getAuthorities(token.getFunc()));
        auth.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(auth);
        sessionDTO.put("token", token);
        servletRequest.getSession().setAttribute("sessionUser",token.getUser().getAccount());
        workDTO.clear();
        workDTO.put("success", true);
        
        if(!"".equals(dataTime) && dataTime !=null){
        	 SysUser sysUser = userService.getSysUserById(userId);
             log.debug("==================上次登录时间：================"+sysUser.getLastTime());
             workDTO.put("logintime",sysUser.getLastTime());
             //存本次登录时间
			 Date lastTime = new Date();
             sysUser.setLastTime(lastTime);
             userService.updateSysUserLastTime(sysUser);
        }
        workDTO.put("token", token);
    }
    
    /**
     * 
     * Description：获取登录时间
     * @return
     * @return String
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     *
     */
    public String getLoginTime(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
      	String lastLogin = df.format(date);
      	String nyr = lastLogin.substring(0, 10);
      	String sfm = lastLogin.substring(11, 19);
      	String sq = dateFm.format(new Date());
      	String loginTime = nyr + " " + sq +"  " +sfm;
      	return loginTime;
    }
    
    public void setAuthenticationService(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	public void setAuthorizationService(AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	public void setUserService(UserMgtService userService) {
		this.userService = userService;
	}
    
    public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}

}
