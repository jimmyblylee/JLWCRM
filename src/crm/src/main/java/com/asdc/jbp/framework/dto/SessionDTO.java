/*
 * Project Name : jbp-framework <br>
 * File Name : SessionDTO.java <br>
 * Package Name : com.asdc.jbp.framework.dto <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.dto;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

import com.asdc.jbp.framework.ui.AppConstant;

/**
 * ClassName : SessionDTO <br>
 * Description : session DTO in each thread <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public class SessionDTO extends AbstractMap<String, Object> implements AppConstant, Serializable {

    private static final long serialVersionUID = 2675989221547664933L;

    protected Map<String, Object> map;

    public SessionDTO(Map<String, Object> session) {
        map = session;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractMap#entrySet()
     */
    @Override
    public Set<Entry<String, Object>> entrySet() {
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
     * @see #put(Object, Object)
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) map.get(key);
    }

    /**
     * Associates the specified value with the specified key in this map. If the map previously contained a mapping for the key, the old value is replaced.
     *
     * @param key
     *            key with which the specified value is to be associated
     * @param value
     *            value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or <tt>null</tt> if there was no mapping for <tt>key</tt>. (A <tt>null</tt> return can also
     *         indicate that the map previously associated <tt>null</tt> with <tt>key</tt>.)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object put(String key, Object value) {
        return map.put(key, value);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractMap#putAll(java.util.Map)
     */
    @Override
    public void putAll(Map<? extends String, ?> m) {
        map.putAll(m);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractMap#remove(java.lang.Object)
     */
    @Override
    public Object remove(Object key) {
        return map.remove(key);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractMap#clear()
     */
    @Override
    public void clear() {
        map.clear();
    }
}
