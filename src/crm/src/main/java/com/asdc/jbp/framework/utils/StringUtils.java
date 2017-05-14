/**
 * Project Name : jbp-framework <br>
 * File Name : StringUtils.java <br>
 * Package Name : com.asdc.jbp.framework.utils <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC.COM JBP. All rights reserved.
 */
package com.asdc.jbp.framework.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * ClassName : StringUtils <br>
 * Description : Util Methods of String <br>
 * Simple inheritance of {@link org.springframework.util.StringUtils}, in case to remove dependency of spring framework <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 */
public abstract class StringUtils extends org.springframework.util.StringUtils {

    /**
     * Description : get strack string from exception trace <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param caught
     * @return the exception stact as a string
     */
    public static String getStackString(Throwable caught) {
        String exceptionStack = "";
        if (caught.getCause() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            try {
                caught.printStackTrace(pw);
                exceptionStack = sw.toString();
            } finally {
                try {
                    sw.close();
                    pw.close();
                } catch (Exception e) {
                    // ignore
                }
            }
        }
        return exceptionStack;
    }

    /**
     * Description : left pad <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param source
     * @param length
     * @param pad
     * @return the padded string
     */
    public static String leftPad(String source, int length, char pad) {
        if (source == null) {
            source = "";
        }
        if (source.length() >= length) {
            return source.substring(0, length);
        } else {
            return leftPad(pad + source, length, pad);
        }
    }

    /**
     * Description : encrypt string into md5 <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param source
     * @return the md5 string
     */
    public static String encryptByMD5(String source) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source.getBytes());
            byte b[] = md.digest();

            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return source;
    }

    /**
     * Description : generate password by given length, containing Upper Char, Lower Char and Number Char <br>
     * Create Time: May 1, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param length
     * @return the generated password by given length
     */
    public static String generatePwd(Integer length) {
        StringBuilder source = new StringBuilder();
        source.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        source.append(source.toString().toLowerCase());
        source.append("0123456789");

        List<Character> resultList = new LinkedList<>();
        Random r = new Random();
        resultList.add(source.charAt(Math.abs(r.nextInt() % 26)));
        resultList.add(source.charAt(Math.abs(r.nextInt() % 26) + 26));
        resultList.add(source.charAt(Math.abs(r.nextInt() % 10) + 52));
        for (int i = 3; i < length; i++) {
            resultList.add(source.charAt(Math.abs(r.nextInt() % 62)));
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int pos = Math.abs(r.nextInt() % (length - i));
            result.append(resultList.get(pos));
            resultList.remove(pos);
        }
        return result.toString();
    }

    /**
     * Description : get noNULL string, if the string is null return "" <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param source
     * @return the filled string
     */
    public static String fillNull(String source) {
        return source == null ? "" : source;
    }

    /**
     * Description : convert list to string split by comma <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param sourceList
     * @return the formated string
     */
    public static <T> String reverseList2CommaString(List<T> sourceList) {
        StringBuilder nstr = new StringBuilder();
        for (T key : sourceList) {
            nstr.append("'");
            nstr.append(key);
            nstr.append("',");
        }
        return nstr.length() > 0 ? nstr.toString().substring(0, nstr.length() - 1) : "''";
    }

    /**
     * Description : get the count of display one string in an other string <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param sub
     * @param source
     * @param fromIndex
     * @return the count
     */
    public static int countSubStringNum(String sub, String source, int fromIndex) {
        if (sub.length() > source.length()) {
            return 0;
        } else {
            int p = source.indexOf(sub, fromIndex);
            if (p == -1) {
                return 0;
            } else {
                return 1 + countSubStringNum(sub, source, p + 1);
            }
        }
    }
}
