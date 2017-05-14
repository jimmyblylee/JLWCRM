/**
 * Project Name : jbp-plugins-file <br>
 * File Name : FileOperator.java <br>
 * Package Name : com.asdc.jbp.attachment.service <br>
 * Create Time : May 10, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.attachment.ftp.service;

import java.io.InputStream;
import java.io.OutputStream;

import com.asdc.jbp.framework.exception.ServiceException;

/**
 * ClassName : FileOperator <br>
 * Description : file operator <br>
 * Create Time : May 10, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public interface FileOperator {

    public void upload(String uri, InputStream in) throws ServiceException;
    
    public void download(String uri, OutputStream out) throws ServiceException;
    
    public void remove(String uri) throws ServiceException;
}
