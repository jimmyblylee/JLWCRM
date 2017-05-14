/**
 * Project Name : jbp-features-sys <br>
 * File Name : SysCommonDao.java <br>
 * Package Name : com.asdc.jbp.sys.dao <br>
 * Create Time : May 1, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.asdc.jbp.framework.dao.AbstractDao;

/**
 * ClassName : SysCommonDao <br>
 * Description : common Dao for system management <br>
 * Create Time : May 1, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Repository("SysCommonDao")
public class SysCommonDao extends AbstractDao {

    @PersistenceContext(unitName = "sys_mgmt")
    private EntityManager em;
    
    /* (non-Javadoc)
     * @see com.asdc.jbp.framework.dao.AbstractDao#getEntityManager()
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
