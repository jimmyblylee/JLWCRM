/*
 * Project Name : jbp-framework <br>
 * File Name : ApplicationAware.java <br>
 * Package Name : com.asdc.jbp.framework.context.bind <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.context.bind;

import com.asdc.jbp.framework.context.ApplicationMap;

/**
 * ClassName : ApplicationAware <br>
 * Description : 为Acction提供 Application<br>
 * notice: 只需要一个action来实现该接口即可(ControllerHelper) <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public interface ApplicationAware {

    /**
     * Description : set application map <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     */
    void setApplication(ApplicationMap applicationMap);
}
