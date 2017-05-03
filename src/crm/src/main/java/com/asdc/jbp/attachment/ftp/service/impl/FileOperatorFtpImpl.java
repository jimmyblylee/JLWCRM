/*
 * Project Name : jbp-plugins-file-ftp-impl <br>
 * File Name : FileOperatorFtpImpl.java <br>
 * Package Name : com.asdc.jbp.attachment.entity <br>
 * Create Time : May 10, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.attachment.ftp.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asdc.jbp.attachment.ftp.dao.FtpDao;
import com.asdc.jbp.attachment.ftp.entity.SysAttachmentConfig;
import com.asdc.jbp.attachment.ftp.service.FileOperator;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.utils.StringUtils;

/**
 * ClassName : FileOperatorFtpImpl <br>
 * Description : ftp implementation of {@link FileOperator} <br>
 * Create Time : May 10, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@SuppressWarnings("unused")
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Transactional(readOnly = true)
public class FileOperatorFtpImpl implements FileOperator {

    @Resource
    private FtpDao dao;


    private FTPClient getFtpClient() throws ServiceException {
        SysAttachmentConfig config = (SysAttachmentConfig) dao.getSingleResultByNamedQuery("ftp.hql.queryAllCfg");
        FTPClient client = new FTPClient();
        try {
            client.connect(config.getIp(), config.getPort());
            client.login(config.getUsername(), config.getPassword());
            return client;
        } catch (IOException e) {
            throw new ServiceException("");
        }
    }




    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.attachment.service.FileOperator#upload(java.lang.String, java.io.InputStream)
     */
    @Override
    @Transactional
    public void upload(String uri, InputStream in) throws ServiceException {
        String[] fileUploadPath = uri.split("/");
        FTPClient client = getFtpClient();

        try {
            int reply = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                client.disconnect();
                throw new ServiceException("");
            }
            client.enterLocalPassiveMode();
            client.setFileType(FTP.BINARY_FILE_TYPE);
            for (int i = 0; i < fileUploadPath.length - 1; i++) {
                client.makeDirectory(fileUploadPath[i]);
                client.changeWorkingDirectory(fileUploadPath[i]);
            }
            client.storeFile(StringUtils.getFilename(uri), in);

        } catch (IOException e) {
            throw new ServiceException("");
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // ignore
            }
            if (client.isConnected()) {
                try {
                    client.disconnect();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.attachment.service.FileOperator#download(java.lang.String, java.io.OutputStream)
     */
    @Override
    public void download(String uri, OutputStream out) throws ServiceException {
        FTPClient client = getFtpClient();
        try {
            client.changeWorkingDirectory(StringUtils.delete(uri, StringUtils.getFilename(uri)));
            client.retrieveFile(StringUtils.getFilename(uri), out);

        } catch (IOException e) {
            throw new ServiceException("");
        } finally {
            if (client.isConnected()) {
                try {
                    client.disconnect();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.attachment.service.FileOperator#remove(java.lang.String)
     */
    @Override
    @Transactional
    public void remove(String uri) throws ServiceException {
        FTPClient client = getFtpClient();
        try {
            client.changeWorkingDirectory(StringUtils.delete(uri, StringUtils.getFilename(uri)));
            client.deleteFile(StringUtils.getFilename(uri));
        } catch (IOException e) {
            throw new ServiceException("");
        } finally {
            if (client.isConnected()) {
                try {
                    client.disconnect();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

}
