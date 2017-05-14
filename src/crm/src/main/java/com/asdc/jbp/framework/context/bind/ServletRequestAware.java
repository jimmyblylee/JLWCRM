/**
 * Project Name : jbp-framework <br>
 * File Name : ServletRequestAware.java <br>
 * Package Name : com.asdc.jbp.framework.context.bind <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.context.bind;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName : ServletRequestAware <br>
 * Description : 为Action提供 servlet request<br>
 * notice: 只需要一个action来实现该接口即可(ControllerHelper) <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public interface ServletRequestAware {

    /**
     * Description : set servlet request <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param request
     */
    public void setServletRequest(HttpServletRequest request);
}
