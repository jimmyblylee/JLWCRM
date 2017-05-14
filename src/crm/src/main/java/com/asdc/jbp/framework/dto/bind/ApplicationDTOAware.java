/**
 * Project Name : jbp-framework <br>
 * File Name : ApplicationDTOAware.java <br>
 * Package Name : com.asdc.jbp.framework.dto.bind <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.dto.bind;

import com.asdc.jbp.framework.dto.ApplicationDTO;

/**
 * ClassName : ApplicationDTOAware <br>
 * Description : provide application dto for action <br>
 * notice: one (only controller can be aware) action implement this interface will be set application dto <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public interface ApplicationDTOAware {

    /**
     * Description : set application dto <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param applicationDTO
     */
    public void setApplicationDTO(ApplicationDTO applicationDTO);
}
