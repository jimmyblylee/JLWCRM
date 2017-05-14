/**
 * Project Name : jbp-framework <br>
 * File Name : Parameter.java <br>
 * Package Name : com.asdc.jbp.framework.dao <br>
 * Create Time : May 1, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.dao;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.utils.StringUtils;

/**
 * ClassName : Parameter <br>
 * Description : parameter of query <br>
 * Create Time : May 1, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public class Parameter implements Serializable {

    private static final long serialVersionUID = -6332857545019135685L;

    private String name;
    private Object value;

    public Parameter(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Description : convert given params into list of {@link Parameter} <br>
     * Create Time: May 1, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param objs
     *            arrays formated by key, value, key, vlaue, key, value...
     * @return list of {@link Parameter}
     * @throws ServiceException
     *             if the params are not dual, and keys are not String
     */
    public static List<Parameter> toList(Object... objs) throws ServiceException {
        if (objs == null || objs.length == 0 || objs.length % 2 == 1) {
            throw new ServiceException("", "");
        } else {
            List<Parameter> params = new LinkedList<>();
            for (int i = 0; i < objs.length; i += 2) {
                Object key = objs[i];
                if (!(key instanceof String) && StringUtils.isEmpty(key)) {
                    throw new ServiceException("", "");
                }
                params.add(new Parameter((String) key, objs[i + 1]));
            }
            return params;
        }
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }
}
