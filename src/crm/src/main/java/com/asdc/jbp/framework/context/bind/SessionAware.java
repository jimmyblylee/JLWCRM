/**
 * Project Name : jbp-framework <br>
 * File Name : SessionAware.java <br>
 * Package Name : com.asdc.jbp.framework.context.bind <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.context.bind;

import com.asdc.jbp.framework.context.SessionMap;

/**
 * ClassName : SessionAware <br>
 * Description : provide session map for action <br>
 * notice: one (only controller can be aware) action implement this interface will be set session map <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public interface SessionAware {

    /**
     * Description : set session map <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param sessionMap
     */
    public void setSession(SessionMap sessionMap);
}
