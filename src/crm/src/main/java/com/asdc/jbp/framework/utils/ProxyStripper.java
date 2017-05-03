/*
 * Project Name : jbp-framework <br>
 * File Name : ProxyStripper.java <br>
 * Package Name : com.asdc.jbp.framework.utils <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */

package com.asdc.jbp.framework.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.hibernate.collection.internal.PersistentSet;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.pojo.javassist.SerializableProxy;

/**
 * ClassName : ProxyStripper <br>
 * Description : Strip the proxy of entities managed by hibernate session, in case of session lost exception while fetching lazy fields with no session <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@SuppressWarnings("unused")
public abstract class ProxyStripper {

    /**
     * Description : Strip the proxy of target entity or collections of entity<br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return the object without proxy
     */
    public static <T> T cleanFromProxies(T value) throws IntrospectionException {
        T result = unproxyObject(value);
        cleanFromProxies(result, new ArrayList<>());
        return result;
    }

    private static void cleanFromProxies(Object value, ArrayList<Object> handledObjects) throws IntrospectionException {
        if ((value != null) && (!isProxy(value)) && !containsTotallyEqual(handledObjects, value)) {
            handledObjects.add(value);
            if (value instanceof Iterable) {
                for (Object item : (Iterable<?>) value) {
                    cleanFromProxies(item, handledObjects);
                }
            } else if (value.getClass().isArray()) {
                for (Object item : (Object[]) value) {
                    cleanFromProxies(item, handledObjects);
                }
            }
            BeanInfo beanInfo;
            beanInfo = Introspector.getBeanInfo(value.getClass());
            if (beanInfo != null) {
                for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
                    try {
                        if ((property.getWriteMethod() != null) && (property.getReadMethod() != null)) {
                            Object fieldValue = property.getReadMethod().invoke(value);
                            if (isProxy(fieldValue)) {
                                fieldValue = unproxyObject(fieldValue);
                                property.getWriteMethod().invoke(value, fieldValue);
                            }
                            cleanFromProxies(fieldValue, handledObjects);
                        }
                    } catch (Exception e) {
                        IntrospectionException ex = new IntrospectionException("***** Get property writer() or reader() exception! *****");
                        ex.addSuppressed(e);
                        throw ex;
                    }
                }
            }
        }
    }

    private static boolean isProxy(Object value) {
        return value != null && ((value instanceof HibernateProxy) || (value instanceof PersistentCollection));
    }

    @SuppressWarnings("unchecked")
    private static <T> T unproxyObject(T object) {
        if (isProxy(object)) {
            if (object instanceof PersistentCollection) {
                PersistentCollection persistentCollection = (PersistentCollection) object;
                return (T) unproxyPersistentCollection(persistentCollection);
            } else if (object instanceof HibernateProxy) {
                HibernateProxy hibernateProxy = (HibernateProxy) object;
                return (T) unproxyHibernateProxy(hibernateProxy);
            } else {
                return null;
            }
        }
        return object;
    }

    private static Object unproxyHibernateProxy(HibernateProxy hibernateProxy) {
        Object result = hibernateProxy.writeReplace();
        if (!(result instanceof SerializableProxy)) {
            return result;
        }
        return null;
    }

    private static Object unproxyPersistentCollection(PersistentCollection persistentCollection) {
        if (persistentCollection instanceof PersistentSet) {
            return persistentCollection.getStoredSnapshot() == null ? null : unproxyPersistentSet((Map<?, ?>) persistentCollection.getStoredSnapshot());
        }
        return persistentCollection.getStoredSnapshot();
    }

    private static <T> Set<T> unproxyPersistentSet(Map<T, ?> persistenceSet) {
        return new LinkedHashSet<>(persistenceSet.keySet());
    }

    private static boolean containsTotallyEqual(Collection<?> collection, Object value) {
        if (collection == null || collection.isEmpty()) {
            return false;
        }
        for (Object object : collection) {
            if (object == value) {
                return true;
            }
        }
        return false;
    }
}
