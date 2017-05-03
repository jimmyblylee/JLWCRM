/*
 * Project Name : jbp-features-sys <br>
 * File Name : Func.java <br>
 * Package Name : com.asdc.jbp.sys.token <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.token;

import java.io.Serializable;

/**
 * ClassName : Func <br>
 * Description : value object of SysFunc for token <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@SuppressWarnings("WeakerAccess")
public class Func implements Serializable {

    private static final long serialVersionUID = 7844571318393852478L;

    private Integer id;
    private String code;
    private String name;
    private Integer order;
    private Dict type;
    private String url;
    private String icon;
    private Boolean isVisible;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the order
     */
    public Integer getOrder() {
        return order;
    }

    /**
     * @param order
     *            the order to set
     */
    public void setOrder(Integer order) {
        this.order = order;
    }

    /**
     * @return the type
     */
    public Dict getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(Dict type) {
        this.type = type;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     *            the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return the isVisible
     */
    public Boolean getIsVisible() {
        return isVisible;
    }

    /**
     * @param isVisible
     *            the isVisible to set
     */
    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }
}
