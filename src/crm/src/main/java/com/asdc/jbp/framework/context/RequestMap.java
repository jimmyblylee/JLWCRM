/*
 * Project Name : jbp-framework <br>
 * File Name : RequestMap.java <br>
 * Package Name : com.asdc.jbp.framework.context <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.context;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName : RequestMap <br>
 * Description : cover {@link HttpServletRequest} attribute parameter into {@link Map} <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public final class RequestMap extends AbstractMap<String, Object> implements Serializable {

    private static final long serialVersionUID = -2140605368052685224L;

    private Set<Entry<String, Object>> entries;
    private HttpServletRequest request;

    /**
     * Create a new instance of RequestMap.
     */
    public RequestMap(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * clear: Removes all attributes from the request as well as clears entries in this map.
     *
     * @see AbstractMap#clear()
     */
    @Override
    public void clear() {
        entries = null;
        Enumeration<?> keys = request.getAttributeNames();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            request.removeAttribute(key);
        }
    }

    /**
     * @see AbstractMap#entrySet()
     */
    @SuppressWarnings("Duplicates")
    @Override
    public Set<Entry<String, Object>> entrySet() {
        if (entries == null) {
            entries = new HashSet<>();
            Enumeration<?> enumeration = request.getAttributeNames();
            while (enumeration.hasMoreElements()) {
                final String key = enumeration.nextElement().toString();
                final Object value = request.getAttribute(key);
                entries.add(new Entry<String, Object>() {
                    @SuppressWarnings("unchecked")
                    public boolean equals(Object obj) {
                        if (obj instanceof  Entry) {
                            Entry<Object, Object> entry = (Entry<Object, Object>) obj;
                            return ((key == null) ? (entry.getKey() == null) : key.equals(entry.getKey()))
                                && ((value == null) ? (entry.getValue() == null) : value.equals(entry.getValue()));
                        } else {
                            return false;
                        }
                    }

                    public int hashCode() {
                        return ((key == null) ? 0 : key.hashCode()) ^ ((value == null) ? 0 : value.hashCode());
                    }

                    public String getKey() {
                        return key;
                    }

                    public Object getValue() {
                        return value;
                    }

                    public Object setValue(Object obj) {
                        request.setAttribute(key, obj);
                        return value;
                    }
                });
            }
        }

        return entries;
    }

    /**
     * Returns the request attribute associated with the given key or <tt>null</tt> if it doesn't exist.
     *
     * @param key
     *            the name of the request attribute.
     * @return the request attribute or <tt>null</tt> if it doesn't exist.
     */
    @Override
    public Object get(Object key) {
        return request.getAttribute(key.toString());
    }

    /**
     * Saves an attribute in the request.
     *
     * @param key
     *            the name of the request attribute.
     * @param value
     *            the value to set.
     * @return the object that was just set.
     */
    @Override
    public Object put(String key, Object value) {
        entries = null;
        request.setAttribute(key, value);
        return get(key);
    }

    /**
     * Removes the specified request attribute.
     *
     * @param key
     *            the name of the attribute to remove.
     * @return the value that was removed or <tt>null</tt> if the value was not found (and hence, not removed).
     */
    @Override
    public Object remove(Object key) {
        entries = null;
        Object value = get(key);
        request.removeAttribute(key.toString());
        return value;
    }

}
