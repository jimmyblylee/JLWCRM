/**
 * Project Name : jbp-plugins-file <br>
 * File Name : FileService.java <br>
 * Package Name : com.asdc.jbp.attachment.service <br>
 * Create Time : May 10, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.attachment.service;

import java.io.InputStream;
import java.io.OutputStream;

import com.asdc.jbp.attachment.entity.SysAttachment;
import com.asdc.jbp.framework.exception.ServiceException;

/**
 * ClassName : FileService <br>
 * Description : service of file operation <br>
 * Create Time : May 10, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public interface FileService {

    public SysAttachment getAttByNatureAndRefId(String nature, Integer refId) throws ServiceException;
    
    public void uploadAtt(SysAttachment att, InputStream in) throws ServiceException;
    
    public void downloadAtt(Integer id, OutputStream out) throws ServiceException;
    
    public SysAttachment getAttById(Integer id) throws ServiceException;
    
    public void removeAttById(Integer id) throws ServiceException;
    
    public void updateAtt(SysAttachment att) throws ServiceException;

	public String uploadFileByBasePath(SysAttachment att,
			InputStream inputStream) throws ServiceException;
}
