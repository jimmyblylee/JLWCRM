/**
 * Project Name : jbp-framework <br>
 * File Name : DateUtils.java <br>
 * Package Name : com.asdc.jbp.framework.utils <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ClassName : DateUtils <br>
 * Description : date util class <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public abstract class DateUtils {

    /**
     * Description : get age by birthday <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param date
     * @return age by int
     */
    public static int getAgeByBirthday(Date date) {
        Calendar now = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(date);
        if (birth.get(Calendar.DAY_OF_YEAR) > now.get(Calendar.DAY_OF_YEAR)) {
            return now.get(Calendar.YEAR) - birth.get(Calendar.YEAR) - 1;
        } else {
            return now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        }
    }

    /**
     * Description : convert long to date string by yyyy-MM-dd <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param dateLong
     * @return yyy-MM-dd formated date string
     */
    public static String formatDateToYMD(Long dateLong) {
        Date Date = new Date(dateLong);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(Date);
    }

    /**
     * Description : convert long to date string by yyyy年MM月dd日 <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param dateLong
     * @return yyyy年MM月dd日 formated string
     */
    public static String formatDateToYMD2(Long dateLong) {
        Date Date = new Date(dateLong);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(Date);
    }

    /**
     * Description : convert date string to date by formate yyyy年MM月dd日 <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param dateString
     * @return date
     */
    public static Date formatYMD2ToDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("exception happens while formatting date!", e);
        }
    }

    /**
     * Description : convert date string to date by formate yyyy-MM-dd <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param dateString
     * @return date
     */
    public static Date formatYMDToDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("exception happens while formatting date!", e);
        }
    }

    /**
     * Description : convert date string yyyy年MM月dd日 to yyyyMMdd <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param dateString
     * @return yyyyMMdd formated string
     */
    public static String formatYMDToString(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(formatYMDToDate(dateString));
    }

    /**
     * Description : formate chinese date string into yyyy-MM-dd HH:mm:ss <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param dateString
     * @return yyyy-MM-dd HH:mm:ss formated string
     */
    public static String formatDateTime(String dateString) {
        if (dateString == null || dateString.length() < 1) {
            return "";
        }

        if (dateString.indexOf("年") > 0) {
            if (dateString.indexOf("月") > 0) {
                dateString = StringUtils.replace(dateString, "年", "-");
                if (dateString.indexOf("日") > 0 || dateString.indexOf("号") > 0) {
                    dateString = StringUtils.replace(dateString, "月", "-");
                } else {
                    dateString = StringUtils.replace(dateString, "月", "");
                }
            } else {
                dateString = StringUtils.replace(dateString, "年", "");
            }
        }

        dateString = StringUtils.replace(dateString, "日", "");
        dateString = StringUtils.replace(dateString, "号", "");

        dateString = StringUtils.replace(dateString, "/", "-");
        dateString = StringUtils.replace(dateString, "\\", "-");
        dateString = StringUtils.replace(dateString, ".", "-");
        dateString = StringUtils.replace(dateString, "－", "-");
        if (dateString.length() <= 10) {
            dateString = formatDate(dateString);
        }
        if (dateString != null) {
            dateString = dateString + " 00:00:00";
        }

        return dateString;
    }

    /**
     * Description : formate chinese date string into yyyy-MM-dd<br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param dateString
     * @return yyyy-MM-dd formated string
     */
    public static String formatDate(String dateString) {

        if (dateString == null || dateString.length() < 1) {
            return "";
        }

        if (dateString.indexOf("年") > 0) {
            if (dateString.indexOf("月") > 0) {
                dateString = StringUtils.replace(dateString, "年", "-");
                if (dateString.indexOf("日") > 0 || dateString.indexOf("号") > 0) {
                    dateString = StringUtils.replace(dateString, "月", "-");
                } else {
                    dateString = StringUtils.replace(dateString, "月", "");
                }
            } else {
                dateString = StringUtils.replace(dateString, "年", "");
            }
        }

        dateString = StringUtils.replace(dateString, "日", "");
        dateString = StringUtils.replace(dateString, "号", "");

        dateString = StringUtils.replace(dateString, "/", "-");
        dateString = StringUtils.replace(dateString, "\\", "-");
        dateString = StringUtils.replace(dateString, ".", "-");
        dateString = StringUtils.replace(dateString, "－", "-");

        if (dateString.length() == 10) {
            return wrap(dateString);
        } else if (dateString.length() == 7) {
            dateString = dateString + "-01";
            return wrap(dateString);
        } else if (dateString.length() == 5) {
            if (dateString.startsWith("0")) {
                dateString = "20" + dateString;
            } else {
                dateString = "19" + dateString;
            }
            return wrap(dateString);
        } else if (dateString.length() == 9) {

            String year = dateString.substring(0, 4);
            String month = dateString.substring(5, 7);
            String day = "";

            if (month.indexOf("-") > 0) {
                month = dateString.substring(5, 6);
                day = dateString.substring(7, 9);
            } else {
                day = dateString.substring(8, 9);
            }

            dateString = year + "-0" + month + "-0" + day;
            return wrap(dateString);
        } else if (dateString.length() == 8) {
            String year = dateString.substring(0, 4);
            String month = dateString.substring(4, 6);
            String day = dateString.substring(6, 8);
            if (month.equals("00")) {
                month = "01";
            }
            if (day.equals("00")) {
                day = "01";
            }
            dateString = year + "-" + month + "-" + day;
            return wrap(dateString);
        } else if (dateString.length() == 6) {
            String year = dateString.substring(0, 4);
            String month = dateString.substring(4, 6);

            dateString = year + "-" + month + "-01";
            return wrap(dateString);
        } else if (dateString.length() == 4) {
            String year = dateString.substring(0, 4);
            dateString = year + "-01" + "-01";
            return wrap(dateString);
        } else if (dateString.length() == 3) {
            String year = dateString.substring(0, 2);
            String month = dateString.substring(2, 3);
            if (year.startsWith("0")) {
                year = "20" + year;
            } else {
                year = "19" + year;
            }
            dateString = year + "-0" + month + "-01";
            return wrap(dateString);
        } else if (dateString.length() > 10) {
            dateString = dateString.substring(0, 10);
            return dateString;
        }
        return null;
    }

    /**
     * Description : check the date and return date string with format yyyy-MM-dd<br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param dateString
     * @return yyyy-MM-dd formated string
     */
    private static String wrap(String dateString) {
        if (checkValidEnDate(dateString)) {
            return dateString;
        } else {
            return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
    }

    /**
     * Description : check the "yyyy-MM-dd" formated date string <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param dateString
     * @return true for the string is yyyy-MM-dd formated
     */
    public static boolean checkValidEnDate(String dateString) {
        boolean result = false;
        Calendar cal = Calendar.getInstance();
        if (dateString != null && dateString.length() == 10) {

            String year = dateString.substring(0, 4);
            String month = dateString.substring(5, 7);
            String day = dateString.substring(8, 10);

            int y = 0;
            int m = 0;
            int d = 0;
            try {
                y = Integer.parseInt(year);
                m = Integer.parseInt(month) - 1;
                d = Integer.parseInt(day);
            } catch (NumberFormatException e) {
                return false;
            }

            cal.set(y, m, d);

            String converted = format(cal.getTime(), "yyyy-MM-dd");

            if (dateString.equals(converted)) {
                result = true;
            } else {
                result = false;
            }

        } else {
            return false;
        }
        return result;
    }

    /**
     * Description : format the date by given pattern <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param date
     * @param pattern
     * @return target pattern formated string
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
}
