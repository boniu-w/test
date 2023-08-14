/**
 * Copyright (c) 2016-2019  All rights reserved.
 * <p>
 * https://www.7-me.net
 * <p>
 * 版权所有，侵权必究！
 */

package wg.application.util;

import cn.hutool.core.date.LocalDateTimeUtil;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
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
    public static Date parseToDate(String strDate, String pattern) {
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
            DateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            return null;
            // throw new RuntimeException(e);
        }
    }

    public static LocalDateTime parseToLocalDateTime(String dateStr, String pattern) {
        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime parse = LocalDateTime.parse(dateStr, ofPattern);
        return parse;
    }

    public static Date toDate(Instant instant) {
        return new Date(instant.toEpochMilli());
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) return null;
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        // // 格式化 LocalDateTime
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // String formattedDateTime = localDateTime.format(formatter);
        //
        // // 判断格式是否正确
        // boolean isValidFormat = formattedDateTime.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
        // System.out.println(isValidFormat); // true or false，取决于格式是否正确
    }

    public static LocalDate toLocalDate(Date date) {
        if (date == null) return null;
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(LocalDate localDate) {
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
        return LocalDateTime.parse(str, fmt);
    }

    public static LocalDateTime toLocalDateTime(String str, String dateParrern) {
        // 字符串 转 LocalDateTime
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(dateParrern);
        return LocalDateTime.parse(str, fmt);
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

    public static String toLocalDateTimeString(LocalDateTime localDateTime, String dateParrern) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(dateParrern);
        return fmt.format(localDateTime);
    }

    public static boolean isDate(String dateStr) {
        if (StringUtil.isBlank(dateStr)) return false;
        dateStr = dateStr.replaceAll("[\"|\']", "")
                .replaceAll("，", "")
                .replaceAll("。", "")
                .replaceAll("；", "")
                .replaceAll("　", "")
                .replaceAll("－", "-")
                .replaceAll("／", "/")
                .replaceAll("．", ".")
                .replaceAll("：", ":")
                .replaceAll("[年|月|日|时|分|秒|毫秒|微秒]", "");
        String[] pa = {"yyyy-MM-dd", "yyyy/mm/dd", "yyyyMMdd", "yyyy.MM.dd",
                "yyyy-MM-dd hh:mm:ss", "yyyy/mm/dd hh:mm:ss", "yyyyMMdd hh:mm:ss", "yyyy.MM.dd hh:mm:ss",
                "yyyy-MM-dd hh:mm", "yyyy/mm/dd hh:mm", "yyyyMMdd hh:mm", "yyyy.MM.dd hh:mm",
                "MM.dd.yyyy", "MMddyyyy", "MM-dd-yyyy", "MM/dd/yyyy",
                "MM.dd.yyyy hh:mm:ss", "MMddyyyy hh:mm:ss", "MM-dd-yyyy hh:mm:ss", "MM/dd/yyyy hh:mm:ss",
                "MM.dd.yyyy hh:mm", "MMddyyyy hh:mm", "MM-dd-yyyy hh:mm", "MM/dd/yyyy hh:mm"};

        if (StringUtil.isNumber(dateStr) && dateStr.length() == 13) {
            try {
                LocalDateTime of = LocalDateTimeUtil.of(Long.parseLong(dateStr));
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        for (String pattern : pa) {
            try {
                Date date = parseToDate(dateStr, pattern);
                if (date != null) {
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }

        return false;
    }

}
