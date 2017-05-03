/*
 * Project Name : jbp-framework <br>
 * File Name : ParameterAware.java <br>
 * Package Name : com.asdc.jbp.framework.context.bind <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.context.bind;

import com.asdc.jbp.framework.context.ParameterMap;

/**
 * ClassName : ParameterAware <br>
 * Description : provide http attribute parameter map for action <br>
 * notice: one (only controller can be aware) action implement this interface will be set http attribute parameter map <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public interface ParameterAware {

    /**
     * Description : set http servlet attribute parameter map <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     */
    void setParameters(ParameterMap parameters);
}
