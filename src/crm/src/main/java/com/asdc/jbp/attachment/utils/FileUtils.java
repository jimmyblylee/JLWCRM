/**
 * Project Name : jbp-plugins-file <br>
 * File Name : FileUtils.java <br>
 * Package Name : com.asdc.jbp.attachment.utils <br>
 * Create Time : May 10, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.attachment.utils;

import java.io.FileNotFoundException;

import javax.activation.MimetypesFileTypeMap;

/**
 * ClassName : FileUtils <br>
 * Description : uitls of file operation <br>
 * Create Time : May 10, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public abstract class FileUtils {

    private static MimetypesFileTypeMap mapper = new MimetypesFileTypeMap(FileUtils.class.getResourceAsStream("/META-INF/mime.types"));

    public static String getMimeType(String filename) throws FileNotFoundException {
        String type = mapper.getContentType(filename);
        return type;
    }
    
}
