/*
 * Project Name : jbp-framework <br>
 * File Name : ExtendInfo.java <br>
 * Package Name : com.asdc.jbp.framework.dto <br>
 * Create Time : Apr 25, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.dto;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName : ExtendInfo <br>
 * Description : Extend information that developer want put into workDTO while invoking the target method <br>
 * Create Time : Apr 25, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
@Documented
public @interface ExtendInfo {

    Info[] infos();
}
