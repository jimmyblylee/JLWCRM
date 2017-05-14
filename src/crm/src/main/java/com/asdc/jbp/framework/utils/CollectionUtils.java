/**
 * Project Name : jbp-framework <br>
 * File Name : CollectionUtils.java <br>
 * Package Name : com.asdc.jbp.framework.utils <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.utils;

import java.util.Collection;
import java.util.Comparator;

/**
 * ClassName : CollectionUtils <br>
 * Description : Simple inheritance of {@link org.springframework.util.CollectionUtils}, in case to remove dependency of spring framework <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public abstract class CollectionUtils extends org.springframework.util.CollectionUtils {

    /**
     * Description : add target into source by unique, if the {@link Comparator#compare(Object, Object)} equals zero, then add it <br>
     * Create Time: May 1, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param source
     * @param target
     * @param comparator
     */
    public static <T> void addByUnique(Collection<T> source, T target, Comparator<T> comparator) {
        for (T t : source) {
            if (comparator.compare(t, target) == 0) {
                return;
            }
        }
        source.add(target);
    }

    /**
     * Description : add all target into source by unique, if the {@link Comparator#compare(Object, Object)} equals zero, then add it <br>
     * Create Time: May 1, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param source
     * @param target
     * @param comparator
     */
    public static <T> void addAllByUnique(Collection<T> source, Collection<T> target, Comparator<T> comparator) {
        for (T t : target) {
            addByUnique(source, t, comparator);
        }
    }
}
