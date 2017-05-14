/**
 * Project Name : jbp-framework <br>
 * File Name : JtaPlatform.java <br>
 * Package Name : com.asdc.jbp.framework.jta <br>
 * Create Time : Apr 19, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.jta;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;

/**
 * ClassName : JtaPlatform <br>
 * Description : Jta Support <br>
 * Create Time : Apr 19, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public class JtaPlatform extends AbstractJtaPlatform {

    private static final long serialVersionUID = 7290409453371487135L;

    private final TransactionManager txMgr;
    private final UserTransaction userTx;

    public JtaPlatform() {
        super();
        this.txMgr = new UserTransactionManager();
        this.userTx = new UserTransactionImp();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform#locateTransactionManager()
     */
    @Override
    protected TransactionManager locateTransactionManager() {
        return this.txMgr;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform#locateUserTransaction()
     */
    @Override
    protected UserTransaction locateUserTransaction() {
        return this.userTx;
    }

}
