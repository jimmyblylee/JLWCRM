/**
 * Project Name : jbp-framework <br>
 * File Name : ServiceException.java <br>
 * Package Name : com.asdc.jbp.framework.exception <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.exception;

import com.asdc.jbp.framework.utils.StringUtils;

/**
 * ClassName : ServiceException <br>
 * Description : 自定义异常 <br>
 * This exception contains a code and a formated message, and some parameters for formated message <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public class ServiceException extends Throwable {

    private static final long serialVersionUID = -2008267826561330901L;

    private final String CNS_DEFAULT_ERR_CODE = "ERR-UNKNOWN-01";
    private String errCode = CNS_DEFAULT_ERR_CODE;
    private ErrLevel errLevel = ErrLevel.ERR;
    private Object[] vars;

    /**
     * Create a new instance of ServiceException. <br>
     * {@code errCode = "ERR-UNKNOWN-01" and errLevel = ErrLevel.ERR}
     * 
     * @param formatedMessage
     *            formated error message
     * @param vars
     *            vars for formated error message eg. " this {} should be {} "
     */
    public ServiceException(String formatedMessage, Object... vars) {
        super(formatedMessage);
        this.vars = vars;
    }

    /**
     * Create a new instance of ServiceException. <br>
     * {@code errCode = "ERR-UNKNOWN-01" and errLevel = ErrLevel.ERR}
     * 
     * @param formatedMessage
     *            formated error message
     * @param cause
     *            error cause
     * @param vars
     *            vars for formated error message eg. " this {} should be {} "
     */
    public ServiceException(String formatedMessage, Throwable cause, Object... vars) {
        super(formatedMessage, cause);
        this.vars = vars;
    }

    /**
     * Create a new instance of ServiceException. <br>
     * {@code errLevel = ErrLevel.ERR}
     * 
     * @param code
     *            error code
     * @param formatedMessage
     *            formated error message
     * @param vars
     *            vars for formated error message eg. " this {} should be {} "
     */
    public ServiceException(String code, String formatedMessage, Object... vars) {
        super(formatedMessage);
        this.errCode = code;
        this.vars = vars;
    }

    /**
     * Create a new instance of ServiceException.
     * 
     * @param code
     *            error code
     * @param formatedMessage
     *            formated error message
     * @param cause
     *            error cause
     * @param vars
     *            vars for formated error message eg. " this {} should be {} "
     */
    public ServiceException(String code, String formatedMessage, Throwable cause, Object... vars) {
        super(formatedMessage, cause);
        this.errCode = code;
        this.vars = vars;
    }

    /**
     * Create a new instance of ServiceException.
     * 
     * @param code
     *            error code
     * @param level
     *            error level
     * @param formatedMessage
     *            formated error message
     * @param vars
     *            vars for formated error message eg. " this {} should be {} "
     */
    public ServiceException(String code, ErrLevel level, String formatedMessage, Object... vars) {
        super(formatedMessage);
        this.errCode = code;
        this.errLevel = level;
        this.vars = vars;
    }

    /**
     * @param code
     *            error code
     * @param level
     *            error level
     * @param formatedMessage
     *            formated error message
     * @param cause
     *            error cause
     * @param vars
     *            vars for formated error message eg. " this {} should be {} "
     */
    public ServiceException(String code, ErrLevel level, String formatedMessage, Throwable cause, Object... vars) {
        super(formatedMessage, cause);
        this.errCode = code;
        this.errLevel = level;
        this.vars = vars;
    }

    /**
     * @return the errorCode
     */
    public String getErrCode() {
        return errCode;
    }

    /**
     * @param errCode
     *            the errorCode to set
     */
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    /**
     * @return the errorLevel
     */
    public ErrLevel getErrLevel() {
        return errLevel;
    }

    /**
     * @param level
     *            the level to set
     */
    public void setErrLevel(ErrLevel level) {
        this.errLevel = level;
    }

    /**
     * Description : return the formated message replaced with given vars by "{}" <br>
     * Create Time : Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     * 
     * @return formated string by replaced the {} with vars
     */
    @Override
    public String getMessage() {
        String msg = super.getMessage();
        if (vars != null && vars.length > 0) {
            for (Object var : vars) {
                if (var != null) {
                    msg = msg.replaceFirst("\\{\\}", var.toString());
                }
            }
        }
        return msg;
    }

    /**
     * Description : get strack string from exception trace <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return exception stack as one single string
     */
    public String getStackString() {
        return StringUtils.getStackString(this);
    }
}
