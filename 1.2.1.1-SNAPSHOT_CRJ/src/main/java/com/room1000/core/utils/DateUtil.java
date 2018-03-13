package com.room1000.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.room1000.core.exception.BaseAppException;
import com.room1000.core.utils.db.service.impl.DBServiceImpl;
import pccom.common.util.SpringHelper;

/**
 * 
 * Description: 时间工具类
 * 
 * Created on 2017年5月26日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public final class DateUtil {

    /**
     * "yyyy-MM-dd"
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * "yyyyMMdd"
     */
    public static final String YYYYMMDD = "yyyyMMdd";

    /**
     * "yyyyMMddHH"
     */
    public static final String YYYYMMDDHH = "yyyyMMddHH";

    /**
     * "yyyy-MM"
     */
    public static final String YYYY_MM = "yyyy-MM";

    /**
     * "yyyyMM"
     */
    public static final String YYYYMM = "yyyyMM";

    /**
     * "yyyy-M-d"
     */
    public static final String YYYY_M_D = "yyyy-M-d";

    /**
     * "yyyy-MM-dd HH:mm:ss"
     */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * "yyyyMMddHHmmss"
     */
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * "HH:mm:ss"
     */
    public static final String HH_MM_SS = "HH:mm:ss";

    /**
     * "yyyy"
     */
    public static final String YEAR = "yyyy";

    /**
     * "MM"
     */
    public static final String MOUTH = "MM";

    /**
     * "dd"
     */
    public static final String DAY = "dd";

    /**
     * "HH"
     */
    public static final String HOUR = "HH";

    /**
     * "mm"
     */
    public static final String MINUTE = "mm";

    /**
     * "ss"
     */
    public static final String SECOND = "ss";

    /**
     * "EEEE"
     */
    public static final String WEEK_EEEE = "EEEE";

    /**
     * 24 * 60 * 60 * 1000L
     */
    public static final long ONEDAY_MILLISECONDS = 24 * 60 * 60 * 1000L;

    /**
     * 60 * 60 * 1000L
     */
    public static final long ONEHOUR_MILLISECONDS = 60 * 60 * 1000L;

    /**
     * 2
     */
    public static final int TWO = 2;

    /**
     * 4
     */
    public static final int FOUR = 4;

    /**
     * 5
     */
    public static final int FIVE = 5;

    /**
     * 6
     */
    public static final int SIX = 6;

    /**
     * 7
     */
    public static final int SEVEN = 7;

    /**
     * 8
     */
    public static final int EIGHT = 8;

    /**
     * 9
     */
    public static final int NINE = 9;

    /**
     * 10
     */
    public static final int TEN = 10;

    /**
     * 11
     */
    public static final int ELEVEN = 11;

    /**
     * 12
     */
    public static final int TWELVE = 12;

    /**
     * 28
     */
    public static final int TWENTY_EIGHT = 28;

    /**
     * 29
     */
    public static final int TWENTY_NINE = 29;

    /**
     * 30
     */
    public static final int THIRTY = 30;

    /**
     * 31
     */
    public static final int THIRTY_ONE = 31;

    /**
     * 100
     */
    public static final int ONE_HUNDRED = 100;

    /**
     * 400
     */
    public static final int FOUR_HUNDRED = 400;

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 构造函数
     */
    private DateUtil() {
    }

    /**
     * MAX_VALUE
     */
    public static final Date MAX_VALUE = offsetYear(getNowDate(), 100);

    /**
     * DATE_FORMAT_1
     * 
     * yyyy-MM-dd
     */
    public static final String DATE_FORMAT_1 = "yyyy-MM-dd";

    /**
     * DATE_FORMAT_2
     * 
     * yyyyMMdd
     */
    public static final String DATE_FORMAT_2 = "yyyyMMdd";

    /**
     * DATETIME_FORMAT_1
     * 
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String DATETIME_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";

    /**
     * DATETIME_FORMAT_3
     * 
     * yyyy-MM-dd HH-mm-ss
     */
    public static final String DATETIME_FORMAT_3 = "yyyy-MM-dd HH-mm-ss";

    /**
     * DATETIME_FORMAT_2
     * 
     * yyyyMMddHHmmss
     */
    public static final String DATETIME_FORMAT_2 = "yyyyMMddHHmmss";

    /**
     * DATETIME_FORMAT_4
     */
    public static final String DATETIME_FORMAT_4 = "yyyy/MM/dd HH:mm:ss";

    /**
     * DATETIME_FORMAT_5
     * 
     * yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String DATETIME_FORMAT_5 = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * DATETIME_FORMAT_6
     * 
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String DATETIME_FORMAT_6 = "yyyy-MM-dd HH:mm:ss";

    /**
     * DATE_FORMAT_3
     * 
     * yyyy年MM月dd日
     */
    public static final String DATE_FORMAT_3 = "yyyy年MM月dd日";

    /**
     * DIFFER_IN_SECOND
     */
    public static final int DIFFER_IN_SECOND = 0;

    /**
     * DIFFER_IN_MINUTE
     */
    public static final int DIFFER_IN_MINUTE = 1;

    /**
     * DIFFER_IN_HOUR
     */
    public static final int DIFFER_IN_HOUR = 2;

    /**
     * DIFFER_IN_DAYS
     */
    public static final int DIFFER_IN_DAYS = 3;

    /**
     * DEFAULT_DATE_FORMAT
     */
    public static final String DEFAULT_DATE_FORMAT = DATE_FORMAT_1;

    /**
     * DEFAULT_TIME_FORMAT
     */
    public static final String DEFAULT_TIME_FORMAT = DATETIME_FORMAT_1;

    /**
     * NAME_FILE_DATE_FORMAT
     */
    public static final String NAME_FILE_DATE_FORMAT = "yyyyMMdd_HHmmss";

    /**
     * DATE_FORMAT_SUPPORT
     */
    public static final String[] DATE_FORMAT_SUPPORT = {
        DATETIME_FORMAT_1, DATETIME_FORMAT_2, DATETIME_FORMAT_3, DATETIME_FORMAT_4, DATE_FORMAT_1, DATE_FORMAT_2,
        DATE_FORMAT_3
    };

    /**
     * 
     * Description: 获取当前时间并进行格式化为String
     * 
     * @author jinyanan
     *
     * @param formatStr 格式化字符串
     * @return String
     */
    public static String getTodayTimeString(String formatStr) {
        if (StringUtils.isEmpty(formatStr)) {
            formatStr = YYYY_MM_DD_HH_MM_SS;
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        String date = format.format(calendar.getTime());
        return date;
    }

    /**
     * 
     * Description: 根据传入format格式化日期
     * 
     * @author jinyanan
     *
     * @param format format
     * @return SimpleDateFormat
     */
    public static SimpleDateFormat getDateFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * 
     * Description: 把date以format进行格式化为String
     * 
     * @author jinyanan
     *
     * @param date date
     * @param format format
     * @return String
     */
    public static String date2StringByFormat(Date date, String format) {
        if (null == date) {
            throw new IllegalArgumentException("the date to be parsed can not be null !!!");
        }

        if (null == format) {
            throw new IllegalArgumentException("the format can not be null !!!");
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        return sdf.format(date);
    }

    /**
     * 
     * Description: 按照format把date转为string
     * 
     * @author jinyanan
     *
     * @param date date
     * @param format format
     * @return String
     */
    public static String date2String(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = getDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 
     * Description: 获取当前时间，使用默认format
     * 
     * @author jinyanan
     *
     * @param date date
     * @return String
     */
    public static String date2String(Date date) {
        return date2String(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * 
     * Description: 获取当前时间
     * 
     * @author jinyanan
     *
     * @return String
     */
    public static String getCurrentDate() {
        Date date = new Date();
        return date2String(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * 
     * Description: 文件命名用时间String
     * 
     * @author jinyanan
     * 
     * @return String
     */
    public static String getNameFileCurrentDate() {
        Date date = new Date();
        return date2String(date, NAME_FILE_DATE_FORMAT);
    }

    /**
     * 
     * Description: 把java.util.Date转为java.sql.Date
     * 
     * @author jinyanan
     *
     * @param date date
     * @return java.sql.Date
     */
    public static java.sql.Date dateToSqlDate(java.util.Date date) {
        if (date == null) {
            return null;
        }
        else if (date instanceof java.sql.Date) {
            return (java.sql.Date) date;
        }
        else {
            return new java.sql.Date(date.getTime());
        }
    }

    /**
     * 
     * Description: 把java.sql.Date转为java.util.Date
     * 
     * @author jinyanan
     *
     * @param date java.sql.Date
     * @return java.util.Date
     */
    public static java.util.Date sqlDate2Date(java.sql.Date date) {
        return new java.util.Date(date.getTime());
    }

    /**
     * 
     * Description: 把string转为java.sql.Date，会尝试各种format
     * 
     * @author jinyanan
     *
     * @param date date
     * @return java.sql.Date
     */
    public static java.sql.Date string2SQLDate(String date) {
        java.sql.Date ret = null;
        if (date == null || date.length() == 0) {
            return ret;
        }
        if (date.length() > 11) {
            if (date.indexOf('-') > 0) {
                if (date.indexOf(':') > 0) {
                    ret = string2SQLDate(date, DATETIME_FORMAT_1);
                }
                else {
                    ret = string2SQLDate(date, DATETIME_FORMAT_3);
                }
            }
            else if (date.indexOf('/') > 0) {
                ret = string2SQLDate(date, DATETIME_FORMAT_4);
            }
            else {
                ret = string2SQLDate(date, DATETIME_FORMAT_2);
            }
        }
        else {
            if (date.indexOf('-') > 0) {
                ret = string2SQLDate(date, DATE_FORMAT_1);
            }
            else if (date.length() == 8) {
                ret = string2SQLDate(date, DATE_FORMAT_2);
            }
            else {
                ret = string2SQLDate(date, DATE_FORMAT_3);
            }
        }
        return ret;
    }

    /**
     * 
     * Description: 按照format把string转为java.sql.Date
     * 
     * @author jinyanan
     *
     * @param date date
     * @param format 格式
     * @return java.sql.Date
     */
    public static java.sql.Date string2SQLDate(String date, String format) {
        boolean isSucc = true;
        Exception operateException = null;
        if (StringUtils.isEmpty(format)) {
            isSucc = false;
            operateException = new IllegalArgumentException("the date format string is null!");
        }
        SimpleDateFormat sdf = getDateFormat(format);
        if (sdf == null) {
            isSucc = false;
            operateException = new IllegalArgumentException(
                "the date format string is not matching available format object");
        }
        java.util.Date tmpDate = null;
        try {
            if (isSucc) {
                tmpDate = sdf.parse(date);
                String tmpDateStr = sdf.format(tmpDate);
                if (!tmpDateStr.equals(date)) {
                    isSucc = false;
                    operateException = new IllegalArgumentException(
                        "the date string " + date + " is not matching format: " + format);
                }
            }
        }
        catch (Exception e) {
            isSucc = false;
            operateException = e;
        }
        if (!isSucc) {
            logger.error("the date string " + date + " is not matching format: " + format, operateException);
            if (operateException instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) operateException;
            }
            else {
                throw new IllegalArgumentException("the date string " + date + " is not matching format: " + format
                    + ".\n cause by :" + operateException.toString());
            }
        }
        else {
            return new java.sql.Date(tmpDate.getTime());
        }
    }

    /**
     * 
     * Description: 把string转date，会尝试各种format格式
     * 
     * @author jinyanan
     *
     * @param date date
     * @return Date
     */
    public static Date string2Date(String date) {
        Date ret = null;
        if (date == null || date.length() == 0) {
            return ret;
        }
        if (date.length() > 11) {
            if (date.indexOf('-') > 0) {
                if (date.indexOf(':') > 0) {
                    ret = string2Date(date, DATETIME_FORMAT_1);
                }
                else {
                    ret = string2Date(date, DATETIME_FORMAT_3);
                }
            }
            else if (date.indexOf('/') > 0) {
                ret = string2Date(date, DATETIME_FORMAT_4);
            }
            else {
                ret = string2Date(date, DATETIME_FORMAT_2);
            }
        }
        else {
            if (date.indexOf('-') > 0) {
                ret = string2Date(date, DATE_FORMAT_1);
            }
            else if (date.length() == 8) {
                ret = string2Date(date, DATE_FORMAT_2);
            }
            else {
                ret = string2Date(date, DATE_FORMAT_3);
            }
        }
        return ret;
    }

    /**
     * 
     * Description: 按照format把string转date
     * 
     * @author jinyanan
     *
     * @param date 时间
     * @param format 格式
     * @return Date
     */
    public static Date string2Date(String date, String format) {
        if (StringUtils.isEmpty(format)) {
            throw new IllegalArgumentException("the date format string is null!");
        }
        SimpleDateFormat sdf = getDateFormat(format);
        if (sdf == null) {
            throw new IllegalArgumentException("the date format string is not matching available format object");
        }
        try {
            return sdf.parse(date);
        }
        catch (ParseException e) {
            throw new IllegalArgumentException("the date string " + date + " is not matching format: " + format, e);
        }
    }

    /**
     * 
     * Description: 获取系统当前时间 yyyy-MM-dd HH:mm:ss
     * 
     * @author jinyanan
     *
     * @return String
     */
    public static String getStandardNowTime() {
        SimpleDateFormat sdf = getDateFormat(DEFAULT_TIME_FORMAT);
        return sdf.format(new java.util.Date());
    }

    /**
     * 
     * Description: 获取系统当前时间
     * 
     * @author jinyanan
     *
     * @return java.sql.Date
     */
    public static java.sql.Date getNowDate() {
        return new java.sql.Date(new java.util.Date().getTime());
    }

    /**
     * 
     * Description: 根据date计算seconds后的时间
     * 
     * @author jinyanan
     *
     * @param date 当前时间
     * @param seconds 秒数
     * @return java.sql.Date 计算后的时间
     */
    public static java.sql.Date offsetSecond(java.sql.Date date, long seconds) {
        if (date == null) {
            return null;
        }
        long time = date.getTime();
        long time2 = time + (seconds * 1000);
        long time3 = time + (seconds * 1000) - 60 * 60 * 1000;
        java.sql.Date date2 = new java.sql.Date(time2);
        java.sql.Date date3 = new java.sql.Date(time3);

        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(date);
        Calendar calendarDate2 = Calendar.getInstance();
        calendarDate2.setTime(date2);
        Calendar calendarDate3 = Calendar.getInstance();
        calendarDate3.setTime(date3);

        long dstDate = calendarDate.get(Calendar.DST_OFFSET);
        long dstDate2 = calendarDate2.get(Calendar.DST_OFFSET);
        long dstDate3 = calendarDate3.get(Calendar.DST_OFFSET);

        long dstOffset = dstDate - dstDate2;
        // 前后两个日期偏移相同（含不偏移）或者夏令日开始的那个小时不用补偿，否则要补偿偏移量。
        if (dstOffset == 0 || (dstDate2 - dstDate3 != 0 && dstDate2 != 0)) {
            return date2;
        }
        else {
            return new java.sql.Date(time2 + dstOffset);
        }
    }

    /**
     * 
     * Description: 根据date计算minutes后的时间
     * 
     * @author jinyanan
     *
     * @param date 当前时间
     * @param minutes 要计算的分钟数
     * @return java.sql.Date 计算后的时间
     */
    public static java.sql.Date offsetMinute(java.sql.Date date, long minutes) {
        return offsetSecond(date, 60 * minutes);
    }

    /**
     * 
     * Description: 根据date计算hours后的时间
     * 
     * @author jinyanan
     *
     * @param date 当前时间
     * @param hours 要计算的小时数
     * @return java.sql.Date 计算后的时间
     */
    public static java.sql.Date offsetHour(java.sql.Date date, long hours) {
        return offsetMinute(date, 60 * hours);
    }

    /**
     * 
     * Description: 根据date计算days后的时间
     * 
     * @author jinyanan
     *
     * @param date 当前时间
     * @param days 要计算的天数
     * @return java.sql.Date 计算后的时间
     */
    public static java.sql.Date offsetDay(java.sql.Date date, int days) {
        return offsetHour(date, 24 * days);
    }

    /**
     * 
     * Description: 根据date计算weeks后的时间
     * 
     * @author jinyanan
     *
     * @param date 当前时间
     * @param weeks 要计算的周数
     * @return java.sql.Date 计算后的时间
     */
    public static java.sql.Date offsetWeek(java.sql.Date date, int weeks) {
        return offsetDay(date, 7 * weeks);
    }

    /**
     * 
     * Description: 根据date计算months后的时间
     * 
     * @author jinyanan
     *
     * @param date 当前时间
     * @param months 要计算的月数
     * @return java.sql.Date 计算后的时间
     */
    public static java.sql.Date offsetMonth(java.sql.Date date, int months) {
        if (date == null) {
            return null;
        }
        java.sql.Date tempDate = new java.sql.Date(date.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tempDate);
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, months);
        int newMaxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (curDay == maxDay) {
            calendar.set(Calendar.DAY_OF_MONTH, newMaxDay);
        }
        else {
            if (curDay > newMaxDay) {
                calendar.set(Calendar.DAY_OF_MONTH, newMaxDay);
            }
            else {
                calendar.set(Calendar.DAY_OF_MONTH, curDay);
            }
        }
        tempDate.setTime(calendar.getTimeInMillis());
        return tempDate;
    }

    /**
     * 
     * Description: 根据date计算years后的时间
     * 
     * @author jinyanan
     *
     * @param date 当前时间
     * @param years 要计算的年数
     * @return java.sql.Date 计算后的时间
     */
    public static java.sql.Date offsetYear(java.sql.Date date, int years) {
        if (date == null) {
            return null;
        }
        java.sql.Date newdate = (java.sql.Date) date.clone();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newdate);
        calendar.add(Calendar.YEAR, years);
        newdate.setTime(calendar.getTimeInMillis());
        return newdate;
    }

    /**
     * 
     * Description: 获取入参时间的当月最后一天
     * 
     * @author jinyanan
     *
     * @param date 要计算的时间
     * @return java.sql.Date
     */
    public static java.sql.Date getLastDayOfMonth(java.sql.Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, maxDay);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        date.setTime(calendar.getTimeInMillis());
        return date;
    }

    /**
     * 
     * Description: 获取入参时间的当月1号零点零分零秒
     * 
     * @author jinyanan
     *
     * @param date 要计算的时间
     * @return java.sql.Date
     */
    public static java.sql.Date getBeginDayOfMonth(java.sql.Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return new java.sql.Date(calendar.getTimeInMillis());
    }

    /**
     * 
     * Description: 计算两个日期之间的差距
     * 
     * @author jinyanan
     *
     * @param beginDate beginDate
     * @param endDate endDate
     * @param returnType 0:秒级；1分级:；2:小时级；3:天级；
     * @return long
     */
    public static long differDateInDays(java.sql.Date beginDate, java.sql.Date endDate, int returnType) {
        long begin = beginDate.getTime();
        long end = endDate.getTime();
        long surplus = begin - end;

        Calendar calendarBeginDate = Calendar.getInstance();
        calendarBeginDate.setTime(beginDate);

        Calendar calendarEndDate = Calendar.getInstance();
        calendarEndDate.setTime(endDate);
        long dstOffset = calendarBeginDate.get(Calendar.DST_OFFSET) - calendarEndDate.get(Calendar.DST_OFFSET);
        surplus += dstOffset;

        long ret = 0;
        switch (returnType) {
            case 0:
                // 秒
                ret = surplus / 1000;
                break;
            case 1:
                // 分
                ret = surplus / 1000 / 60;
                break;
            case 2:
                // 时
                ret = surplus / 1000 / 60 / 60;
                break;
            case 3:
                // 天
                ret = surplus / 1000 / 60 / 60 / 24;
                break;
            default:
                break;
        }
        return ret;
    }

    /**
     * 
     * Description: 判断date是否在beginDate和endDate之间（入参都是String类型）
     * 
     * @author jinyanan
     *
     * @param date date
     * @param beginDate beginDate
     * @param endDate endDate
     * @return true-在范围之内；false-不在范围内
     */
    public static boolean isInRange(String date, String beginDate, String endDate) {
        if (StringUtils.isEmpty(date) || StringUtils.isEmpty(beginDate) || StringUtils.isEmpty(endDate)) {
            throw new BaseAppException("");
        }
        int dateLen = date.length();
        int beginDateLen = date.length();
        int endDateLen = date.length();
        if (!EqualsUtil.equals(beginDateLen, dateLen) || EqualsUtil.equals(dateLen, endDateLen)) {
            throw new BaseAppException("");
        }
        boolean asc = isAsc(beginDate, endDate);
        if (asc) {
            if (date.compareTo(beginDate) >= 0 && date.compareTo(endDate) <= 0) {
                return true;
            }
        }
        else {
            if (date.compareTo(beginDate) >= 0 || date.compareTo(endDate) <= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * Description: 判断date是否在beginDate和endDate之间（入参都是Date类型）
     * 
     * @author jinyanan
     *
     * @param date date
     * @param beginDate beginDate
     * @param endDate endDate
     * @return true-在范围之内；false-不在范围内
     */
    public static boolean isInRange(Date date, Date beginDate, Date endDate) {
        long time = date.getTime();
        long beginTime = beginDate.getTime();
        long endTime = endDate.getTime();
        return time >= beginTime && time <= endTime;
    }

    /**
     * 
     * Description: 比较两个时间
     * 
     * @author jinyanan
     *
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @return 如果时间相同，返回1；如果beginDate早于endDate，返回0；如果beginDate晚于endDate，返回0；
     */
    public static int compare(Date beginDate, Date endDate) {
        int ret = 1;
        long beginTime = beginDate.getTime();
        long endTime = endDate.getTime();
        if (beginTime > endTime) {
            ret = 2;
        }
        if (beginTime == endTime) {
            ret = 1;
        }
        if (beginTime < endTime) {
            ret = 0;
        }
        return ret;
    }

    /**
     * 
     * Description:
     * 
     * @author jinyanan
     *
     * @param beginDateStr String
     * @param endDateStr String
     * @return boolean true-beginDateStr早于endDateStr, false-beginDateStr晚于endDateStr
     */
    private static boolean isAsc(String beginDateStr, String endDateStr) {
        return (beginDateStr.compareTo(endDateStr) < 0);
    }

    /**
     * 上一次查询数据库的时间(毫秒值) <br>
     */
    private static long latestQueryTimeMillis = 0;

    /**
     * 数据库时间与当前主机时间之间的差值(毫秒)
     */
    private static long intervalsOfDBandSysdate = 0;

    /**
     * 
     * Description: 获取数据库时间。基于本地时间和数据库时间的差值进行获取,超过20min，则自动跟数据库时间进行校准。该方法没有考虑线程同步，可能会有一点点影响，但没什么关系
     * 
     * @author jinyanan
     *
     * @return java.sql.Date
     * @throws BaseAppException <br>
     */
    public static java.sql.Date getSqlDBDateTime() throws BaseAppException {
        java.sql.Date retDate = null;
        long begin = System.currentTimeMillis();
        if (latestQueryTimeMillis == 0 || (begin - latestQueryTimeMillis > 1000L * 60 * 20)) { // 定为20分钟
            // 是首次查询，或者是查询超过了指定的间隔时间 <br>
            retDate = new java.sql.Date(getDBCurrentTime().getTime());
            long end = System.currentTimeMillis();
            if (end - begin <= 50) {
                // 查询小于50毫秒 <br>
                intervalsOfDBandSysdate = (begin / 1000 * 1000) - retDate.getTime();
                latestQueryTimeMillis = begin;
            }
            else {
                // 初始化为首次查询 <br>
                latestQueryTimeMillis = 0;
            }
        }
        else {
            // 两次查询在许可的时间间隔之内，不需要重新从数据库查询 <br>
            retDate = new java.sql.Date((begin / 1000 * 1000) - intervalsOfDBandSysdate);
        }
        return retDate;
    }

    /**
     * 
     * Description: 获取java.util.Date型的数据库时间
     * 
     * @author jinyanan
     *
     * @return java.util.Date
     * @throws BaseAppException <br>
     */
    public static java.util.Date getDBDateTime() throws BaseAppException {
        return sqlDate2Date(getSqlDBDateTime());
    }

    /**
     * 
     * Description: 返回数据库当前时间，带毫秒
     * 
     * @author jinyanan
     *
     * @return java.sql.Date java.sql.Date
     * @throws BaseAppException <br>
     */
    private static Date getDBCurrentTime() throws BaseAppException {
        DBServiceImpl dbService = (DBServiceImpl) SpringHelper.getBean("DBService");
        return dbService.getDBDateTime();
    }

    /**
     * 
     * Description: 计算时间偏移
     * 
     * @author jinyanan
     *
     * @param offset 偏移的值
     * @param dateTimeStr 具体的时间日期字符串
     * @param intout 是输入还是输出，如果是0表示输入，则在对应时间上加上偏移量返回，如果是1表示输出，则在对应时间上减去偏移量
     * @return Date
     */
    public static Date dateOffsetCalc(int offset, String dateTimeStr, int intout) {
        Date ret = string2Date(dateTimeStr);
        boolean hasTimeStr = false;
        if (dateTimeStr.length() > 11) {
            hasTimeStr = true;
        }
        else {
            hasTimeStr = false;
        }
        if (ret != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(ret);
            if (intout == 0) { // 表示是输入，加上偏移量
                if (hasTimeStr) {
                    cal.add(Calendar.SECOND, offset);
                }
                else {
                    cal.add(Calendar.DATE, offset);
                }
            }
            else {
                if (hasTimeStr) {
                    cal.add(Calendar.SECOND, (-1 * offset));
                }
                else {
                    cal.add(Calendar.DATE, (-1 * offset));
                }
            }
            ret = cal.getTime();
        }
        return ret;
    }

    /**
     * 
     * Description: 计算SqlDate的偏移量
     * 
     * @author jinyanan
     *
     * @param offset 偏移的值
     * @param dateTimeStr 具体的时间日期字符串
     * @param intout 是输入还是输出，如果是0表示输入，则在对应时间上加上偏移量返回，如果是1表示输出，则在对应时间上减去偏移量
     * @return Date
     */
    public static java.sql.Date sqlDateOffsetCalc(int offset, String dateTimeStr, int intout) {
        Date ret = dateOffsetCalc(offset, dateTimeStr, intout);
        if (ret != null) {
            return new java.sql.Date(ret.getTime());
        }
        return null;
    }

    /**
     * 
     * Description: 把字符串按照format格式化为Date
     * 
     * @author jinyanan
     *
     * @param date 字符串Str
     * @param format 格式化
     * @return Date
     */
    public static Date str2DateByFormat(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        }
        catch (ParseException e) {
            throw new IllegalArgumentException("the date string " + date + " is not matching format: " + format, e);
        }
    }

}
