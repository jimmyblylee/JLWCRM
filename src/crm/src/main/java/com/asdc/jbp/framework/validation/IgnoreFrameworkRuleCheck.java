/**
 * Project Name : jbp-framework <br>
 * File Name : IgnoreFrameworkRuleCheck.java <br>
 * Package Name : com.asdc.jbp.framework.validation <br>
 * Create Time : Apr 13, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName : IgnoreFrameworkRuleCheck <br>
 * Description : Ignore check for the rule of framework defination <br>
 * Create Time : Apr 13, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface IgnoreFrameworkRuleCheck {

}
