/*
 * Project Name : jbp-plugins-file <br>
 * File Name : FileServiceImpl.java <br>
 * Package Name : com.asdc.jbp.attachment.service.impl <br>
 * Create Time : May 10, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.attachment.service.impl;

import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asdc.jbp.attachment.dao.SysAttachmentDao;
import com.asdc.jbp.attachment.entity.SysAttachment;
import com.asdc.jbp.attachment.ftp.dao.FtpDao;
import com.asdc.jbp.attachment.ftp.entity.SysAttachmentConfig;
import com.asdc.jbp.attachment.ftp.service.FileOperator;
import com.asdc.jbp.attachment.service.FileService;
import com.asdc.jbp.framework.dao.Parameter;
import com.asdc.jbp.framework.exception.ServiceException;

/**
 * ClassName : FileServiceImpl <br>
 * Description : impl of {@link FileService} <br>
 * Create Time : May 10, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional(readOnly = true)
public class FileServiceImpl implements FileService {

	@Resource
    private FtpDao ftpDao;
    @Resource
    private FileOperator fileOperator;

    @Resource
    private SysAttachmentDao dao;

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.attachment.service.FileService#getAttByNatureAndRefId(java.lang.String, java.lang.Integer)
     */
    @Override
    public SysAttachment getAttByNatureAndRefId(String nature, Integer refId) throws ServiceException {
        return (SysAttachment) dao.getSingleResultByNamedQuery("att.hql.findByNatureAndRefId", Parameter.toList("nature", nature, "ref", refId));
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.attachment.service.FileService#uploadAtt(com.asdc.jbp.attachment.entity.SysAttachment, java.io.InputStream)
     */
    @Override
    @Transactional
    public void uploadAtt(SysAttachment att, InputStream in) throws ServiceException {
        dao.persist(att);
        fileOperator.upload(att.getUri(), in);
    }

    @Override
    @Transactional
    public String uploadFileByBasePath(SysAttachment att, InputStream in) throws ServiceException {
    	SysAttachmentConfig config = (SysAttachmentConfig) ftpDao.getSingleResultByNamedQuery("ftp.hql.queryAllCfg");
        StringBuilder sbUri = new StringBuilder();
        String upLoadUri = att.getUri();
        sbUri.append(config.getBasePath());
        sbUri.append(att.getUri());
        att.setUri(sbUri.toString());
        uploadAtt(att,in);
        return upLoadUri;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.attachment.service.FileService#downloadAtt(java.lang.Integer, java.io.OutputStream)
     */
    @Override
    public void downloadAtt(Integer id, OutputStream out) throws ServiceException {
        fileOperator.download(getAttById(id).getUri(), out);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.attachment.service.FileService#getAttById(java.lang.Integer)
     */
    @Override
    public SysAttachment getAttById(Integer id) throws ServiceException {
        return dao.find(SysAttachment.class, id);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.attachment.service.FileService#removeAttById(java.lang.Integer)
     */
    @Override
    @Transactional
    public void removeAttById(Integer id) throws ServiceException {
        SysAttachment att = getAttById(id);
        dao.remove(att);
        fileOperator.remove(att.getUri());

    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.attachment.service.FileService#updateAtt(com.asdc.jbp.attachment.entity.SysAttachment)
     */
    @Override
    @Transactional
    public void updateAtt(SysAttachment att) throws ServiceException {
        SysAttachment attInDB = getAttById(att.getId());
        attInDB.setDesc(att.getDesc());
        attInDB.setName(att.getName());
    }

}
