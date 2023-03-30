/**
 * Copyright (c) 2016-2019  All rights reserved.
 * <p>
 * https://www.7-me.net
 * <p>
 * 版权所有，侵权必究！
 */

package wg.application.util;

import org.apache.commons.lang3.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期处理
 *
 * @author Mark sunlightcs@gmail.com
 */
public class DateUtils {
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date 日期
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date    日期
     * @param pattern 格式，如：DateUtils.DATE_TIME_PATTERN
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 字符串转换成日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
     */
    public static Date stringToDate(String strDate, String pattern) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }

        if (StringUtils.isBlank(pattern)) {
            if (!strDate.contains(":")) {
                pattern = DATE_PATTERN;
            } else {
                pattern = DATE_TIME_PATTERN;
            }
        }

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            return null;
            // throw new RuntimeException(e);
        }
    }

    public static Date toDate(Instant instant) {
        return new Date(instant.toEpochMilli());
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDate toLocalDate(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(java.time.LocalDate localDate) {
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    /************************************************************************
     * @author: wg
     * @description: 字符串 转 LocalDateTime
     * @params:
     * @return:
     * @createTime: 10:08  2023/3/30
     * @updateTime: 10:08  2023/3/30
     ************************************************************************/
    public static LocalDateTime toLocalDateTime(String str) {
        // 字符串 转 LocalDateTime
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parse = LocalDateTime.parse(str, fmt);
        return parse;
    }

    /************************************************************************
     * @author: wg
     * @description: localdatetime 转 字符串
     * @params:
     * @return:
     * @createTime: 10:10  2023/3/30
     * @updateTime: 10:10  2023/3/30
     ************************************************************************/
    public static String toLocalDateTimeString(LocalDateTime localDateTime) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return fmt.format(localDateTime);
    }
}
