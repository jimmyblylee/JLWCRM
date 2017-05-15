/**
 * Project Name : jbp-plugins-file-ftp-impl <br>
 * File Name : FtpDao.java <br>
 * Package Name : com.asdc.jbp.attachment.dao <br>
 * Create Time : May 10, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.attachment.ftp.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.asdc.jbp.framework.dao.AbstractDao;

/**
 * ClassName : FtpDao <br>
 * Description : TODO <br>
 * Create Time : May 10, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Repository
public class FtpDao extends AbstractDao {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
