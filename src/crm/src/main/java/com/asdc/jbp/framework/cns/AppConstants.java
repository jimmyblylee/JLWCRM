/**
 * Project Name : jbp-framework <br>
 * File Name : AppConstants.java <br>
 * Package Name : com.asdc.jbp.framework.cns <br>
 * Create Time : Apr 15, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.cns;

import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * ClassName : AppConstants <br>
 * Description : 将system.properties中的数据存入到系统内存中，可根据
 * {@link System#getProperty(String)} 来获取<br>
 * 这是一个定时任务，每半个小时执行一次，创建时间: 2016 年 4 月 15 日 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Component
public class AppConstants implements InitializingBean {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void afterPropertiesSet() throws Exception {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ResourceBundle bundle;
                try {
                    bundle = ResourceBundle.getBundle("system");
                } catch (Exception e) {
                    log.trace("There is no system.properties");
                    return;
                }
                try {
                    Enumeration<String> keys = bundle.getKeys();
                    while (keys.hasMoreElements()) {
                        String key = keys.nextElement();
                        System.setProperty(key, bundle.getString(key));
                    }
                } catch (Exception e) {
                    log.error("can not set properties");
                }
            }
        }, 0, 30000);
    }

}
