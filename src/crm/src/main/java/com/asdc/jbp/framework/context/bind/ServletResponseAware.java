/*
 * Project Name : jbp-framework <br>
 * File Name : ServletResponseAware.java <br>
 * Package Name : com.asdc.jbp.framework.context.bind <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.context.bind;

import javax.servlet.http.HttpServletResponse;

/**
 * ClassName : ServletResponseAware <br>
 * Description : 为Action提供 servlet response <br>
 * notice: 只需要一个action来实现该接口即可(ControllerHelper) <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public interface ServletResponseAware {

    /**
     * Description : set servlet response <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     */
    void setServletResponse(HttpServletResponse response);
}
