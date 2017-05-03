/*
 * Project Name : jbp-framework <br>
 * File Name : ApplicationMap.java <br>
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

import javax.servlet.ServletContext;

/**
 * ClassName : ApplicationMap <br>
 * Description : cover {@link ServletContext} into {@link Map} <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public final class ApplicationMap extends AbstractMap<String, Object> implements Serializable {

    private static final long serialVersionUID = 2828640566937368725L;

    private ServletContext context;
    private Set<Entry<String, Object>> entries;

    /**
     * Create a new instance of ApplicationMap.
     */
    public ApplicationMap(ServletContext context) {
        this.context = context;
    }

    /**
     * Removes all entries from the Map and removes all attributes from the servlet context.
     */
    public void clear() {
        entries = null;
        Enumeration<?> e = context.getAttributeNames();
        while (e.hasMoreElements()) {
            context.removeAttribute(e.nextElement().toString());
        }
    }

    /**
     * Creates a Set of all servlet context attributes as well as context init parameters.
     *
     * @return a Set of all servlet context attributes as well as context init parameters.
     */
    @SuppressWarnings("Duplicates")
    public Set<Entry<String, Object>> entrySet() {
        if (entries == null) {
            entries = new HashSet<>();

            // Add servlet context attributes
            Enumeration<?> enumeration = context.getAttributeNames();

            while (enumeration.hasMoreElements()) {
                final String key = enumeration.nextElement().toString();
                final Object value = context.getAttribute(key);
                entries.add(new Entry<String, Object>() {
                    @SuppressWarnings("unchecked")
                    public boolean equals(Object obj) {
                        if (obj instanceof  Entry) {
                            Entry<String, Object> entry = (Entry<String, Object>) obj;

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
                        context.setAttribute(key, obj);
                        return value;
                    }
                });
            }

            // Add servlet context init params
            enumeration = context.getInitParameterNames();

            while (enumeration.hasMoreElements()) {
                final String key = enumeration.nextElement().toString();
                final Object value = context.getInitParameter(key);
                entries.add(new Entry<String, Object>() {
                    @SuppressWarnings("unchecked")
                    public boolean equals(Object obj) {
                        if (obj instanceof Entry) {
                            Entry<String, Object> entry = (Entry<String, Object>) obj;
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
                        context.setAttribute(key, obj);
                        return value;
                    }
                });
            }
        }

        return entries;
    }

    /**
     * Returns the servlet context attribute or init parameter based on the given key. If the entry is not found, <tt>null</tt> is returned.
     *
     * @param key
     *            the entry key.
     * @return the servlet context attribute or init parameter or <tt>null</tt> if the entry is not found.
     */
    public Object get(Object key) {
        // Try context attributes first, then init params
        // This gives the proper shadowing effects
        String keyString = key.toString();
        Object value = context.getAttribute(keyString);
        return (value == null) ? context.getInitParameter(keyString) : value;
    }

    /**
     * Sets a servlet context attribute given a attribute name and value.
     *
     * @param key
     *            the name of the attribute.
     * @param value
     *            the value to set.
     * @return the attribute that was just set.
     */
    public Object put(String key, Object value) {
        entries = null;
        context.setAttribute(key, value);
        return get(key);
    }

    /**
     * Removes the specified servlet context attribute.
     *
     * @param key
     *            the attribute to remove.
     * @return the entry that was just removed.
     */
    public Object remove(Object key) {
        entries = null;
        Object value = get(key);
        context.removeAttribute(key.toString());
        return value;
    }
}
