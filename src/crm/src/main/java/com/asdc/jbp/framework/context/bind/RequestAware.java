/**
 * Project Name : jbp-framework <br>
 * File Name : RequestAware.java <br>
 * Package Name : com.asdc.jbp.framework.context.bind <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.context.bind;

import com.asdc.jbp.framework.context.RequestMap;

/**
 * ClassName : RequestAware <br>
 * Description : provide http request map for action <br>
 * notice: one (only controller can be aware) action implement this interface will be set http request map <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public interface RequestAware {

    /**
     * Description : set http request <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param request
     */
    public void setRequest(RequestMap request);
}
