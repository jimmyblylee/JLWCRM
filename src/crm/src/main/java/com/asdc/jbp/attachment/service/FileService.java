/*
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
 */
@SuppressWarnings("unused")
public interface FileService {

    SysAttachment getAttByNatureAndRefId(String nature, Integer refId) throws ServiceException;

    void uploadAtt(SysAttachment att, InputStream in) throws ServiceException;

    void downloadAtt(@SuppressWarnings("SameParameterValue") Integer id, OutputStream out) throws ServiceException;

    SysAttachment getAttById(Integer id) throws ServiceException;

    void removeAttById(Integer id) throws ServiceException;

    void updateAtt(SysAttachment att) throws ServiceException;

    String uploadFileByBasePath(SysAttachment att, InputStream inputStream) throws ServiceException;
}
