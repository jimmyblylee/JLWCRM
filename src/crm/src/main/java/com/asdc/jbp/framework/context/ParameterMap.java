/**
 * Project Name : jbp-framework <br>
 * File Name : ParameterMap.java <br>
 * Package Name : com.asdc.jbp.framework.context <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.context;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName : ParameterMap <br>
 * Description : cover {@link HttpServletRequest#getParameter(String)} parameter of request into {@link Map} <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public final class ParameterMap extends AbstractMap<String, String[]> implements Serializable {

    private static final long serialVersionUID = 304637940232125085L;

    private Map<String, String[]> map;

    /**
     * Create a new instance of ParameterMap.
     * 
     * @param request
     */
    public ParameterMap(HttpServletRequest request) {
        map = request.getParameterMap();
    }

    /**
     * @see java.util.AbstractMap#entrySet()
     */
    @Override
    public Set<java.util.Map.Entry<String, String[]>> entrySet() {
        return map.entrySet();
    }

    /**
     * Returns the value to which the specified key is mapped, or {@code null} if this map contains no mapping for the key.
     * 
     * <p>
     * More formally, if this map contains a mapping from a key {@code k} to a value {@code v} such that {@code (key==null ? k==null : key.equals(k))} , then
     * this method returns {@code v}; otherwise it returns {@code null}. (There can be at most one such mapping.)
     * 
     * <p>
     * A return value of {@code null} does not <i>necessarily</i> indicate that the map contains no mapping for the key; it's also possible that the map
     * explicitly maps the key to {@code null}. The {@link #containsKey containsKey} operation may be used to distinguish these two cases.
     * 
     * @see AbstractMap#put(Object, Object)
     */
    public String[] get(String key) {
        return map.get(key);
    }

}
