/*
 * Project Name : jbp-framework <br>
 * File Name : PinyinUtils.java <br>
 * Package Name : com.asdc.jbp.framework.utils <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */

package com.asdc.jbp.framework.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * ClassName : PinyinUtils <br>
 * Description : chinese pinyin asstant class <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@SuppressWarnings("unused")
public abstract class PinyinUtils {

    /**
     * Description : convert chinese Strings to chinese pinyin by full spell <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return the pinyin by full spell
     */
    public static String inverse(String input) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c <= 255) {
                sb.append(c);
            } else {
                String pinyin = null;
                try {
                    String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    pinyin = pinyinArray[0];
                } catch (BadHanyuPinyinOutputFormatCombination | NullPointerException e) {
                    // ignore
                }
                if (pinyin != null) {
                    sb.append(pinyin);
                }
            }
        }
        return sb.toString();
    }

    /**
     * Description : convert chinese string into chinese pinyin by first spell contact <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return the pinyin by firlst spell contact
     */
    public static String inverseShort(String input) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c <= 255) {
                sb.append(c);
            } else {
                String pinyin = null;
                try {
                    String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    pinyin = pinyinArray[0];
                } catch (BadHanyuPinyinOutputFormatCombination | NullPointerException e) {
                    // ignore
                }
                if (pinyin != null && pinyin.length() > 0) {
                    sb.append(pinyin.substring(0, 1));
                }
            }
        }
        return sb.toString();
    }
}
