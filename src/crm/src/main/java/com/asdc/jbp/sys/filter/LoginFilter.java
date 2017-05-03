package com.asdc.jbp.sys.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.message.Messages;
import com.asdc.jbp.framework.utils.ProxyStripper;

public class LoginFilter extends ControllerHelper implements Filter {
    @Override
    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String parameter = req.getParameter("controller");
        String method = req.getParameter("method");

        String url = req.getContextPath();
        String ajaxReqHeader = req.getHeader("X-Requested-With");

        Object user = req.getSession().getAttribute("sessionUser");

        if (parameter.equals("LoginController") ||
            parameter.equals("ForgetPasswordController") ||
            parameter.equals("GlobalListController") && method.equals("queryLoginList") ||
            parameter.equals("GlobalListController") && method.equals("getGlobalById") ||
            parameter.equals("UserController") && method.equals("queryUserIdByAccont") ||
            parameter.equals("DictController") && method.equals("getSelectDictInfo")) {

            chain.doFilter(request, response);//放行

        } else if (parameter.equals("getControllerSession") && method.equals("getSession")) {
            if (user == null) {
                resp.getWriter().write("loginTimeOut");
            } else {
                resp.getWriter().write("notLoginTimeout");
            }
        } else if (user == null && ajaxReqHeader != null && ajaxReqHeader.equals("XMLHttpRequest")) {
            resp.setStatus(401);
        } else {
            if (user == null) {
                String redirecturl = url + "/login.html";
                resp.sendRedirect(redirecturl);
            } else {
                chain.doFilter(request, response);//放行
            }
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {

    }

}
