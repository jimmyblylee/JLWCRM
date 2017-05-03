/*
 * Project Name : jbp-framework <br>
 * File Name : WorkDTOAware.java <br>
 * Package Name : com.asdc.jbp.framework.dto.bind <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.dto.bind;

import com.asdc.jbp.framework.dto.WorkDTO;

/**
 * ClassName : WorkDTOAware <br>
 * Description : 为Action提供 workDTO <br>
 * notice: 只需要一个action来实现该接口即可(ControllerHelper)<br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public interface WorkDTOAware {

    /**
     * Description : set work dto <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     */
    void setWorkDTO(WorkDTO workDTO);
}
