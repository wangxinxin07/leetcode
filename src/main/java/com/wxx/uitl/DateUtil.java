package com.wxx.uitl;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @Author: liangshuai-hj
 * @Description: 时间的工具类  (SimpleDateFormat 在多线程有问题)
 * @Date: 2019/4/16 21:26
 * @Version 1.0
 */
public class DateUtil {

    public static final DateTimeFormatter DATA_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取指定时间的string 字符串
     *
     * @param time
     * @return
     */
    public static String getStrFromDateFull(LocalDateTime time) {
        try {
            return time.format(DATA_FORMAT);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取指定时间的string 字符串
     *
     * @param time
     * @return
     */
    public static String getStrFromDateFull(Date time) {
        try {
            Instant instant = time.toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
            return localDateTime.format(DATA_FORMAT);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 自定义格式化时间转换
     *
     * @param time
     * @return
     */
    public static LocalDateTime getLocalDateFromStrFull(String time, DateTimeFormatter formatter) {
        try {
            return LocalDateTime.parse(time, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 字符串时间转换
     *
     * @param time 格式yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static LocalDateTime getLocalDateFromStrFull(String time) {
        try {
            return LocalDateTime.parse(time, DATA_FORMAT);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 字符串时间转换
     *
     * @param time 格式yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date getDateFromStrFull(String time) {
        LocalDateTime dateTime = getLocalDateFromStrFull(time);
        return getDate(dateTime);
    }

    /**
     * 自定义格式化时间转换
     *
     * @param time
     * @return
     */
    public static Date getDateFromStrFull(String time, DateTimeFormatter formatter) {
        LocalDateTime dateTime = getLocalDateFromStrFull(time, formatter);
        return getDate(dateTime);
    }

    /**
     * 正数 加多少秒 负数 减多少秒
     *
     * @param date
     * @param second
     * @return
     */
    public static Date addSecond(Date date, Long second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, second.intValue());
        return calendar.getTime();
    }

    /**
     * @param date
     * @param formatter
     * @return
     */
    public static String getFormatDate(String date, String formatter) {
        try {
            return (new SimpleDateFormat(formatter)).format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 根据日期获取yyyyMM
     *
     * @param date
     * @return
     */
    public static String getMonthByDate(String date) {
        return DateUtil.getFormatDate(date, "yyyyMM");
    }

    /**
     * 根据日期获取yyyyMM
     *
     * @param date
     * @return
     */
    public static String getMonthByDate(Date date) {
        return DateUtil.getFormatDate(date, "yyyyMM");
    }

    /**
     * 根据日期获取yyyyMM
     *
     * @return
     */
    public static String getMonthByDate() {
        return DateUtil.getFormatDate(new Date(), "yyyyMM");
    }

    /**
     * @param date
     * @param formatter
     * @return
     */
    public static String getFormatDate(Date date, String formatter) {
        try {
            return (new SimpleDateFormat(formatter)).format(date);
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 自定义格式化时间转换
     *
     * @param time
     * @return
     */
    public static Date getDateFromStrFull(String time, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime dateTime = getLocalDateFromStrFull(time, formatter);
        return getDate(dateTime);
    }

    /**
     * LocalDateTime 转 Date
     *
     * @param dateTime
     * @return
     */
    public static Date getDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = dateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * 获取当前时间的string 字符串
     *
     * @return
     */
    public static String getNowDateStr() {
        return getStrFromDateFull(LocalDateTime.now());
    }

    /**
     * 返回指定格式的日期字符串
     *
     * @param pattern
     * @return
     */
    public static String getNowDataStrPattern(String pattern) {
        try {
            Instant instant = new Date().toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
            return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获得指定日期的前n天
     *
     * @param date
     * @param beforeDay
     * @return
     */
    public static Date getSpecifiedDayBefore(Date date, int beforeDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day - beforeDay);
        return calendar.getTime();
    }

    /**
     * 获取今日剩余秒数
     *
     * @return
     */
    public static int getTodayRemainingSeconds() {
        Calendar curDate = Calendar.getInstance();
        Calendar tommorowDate = new GregorianCalendar(curDate
                .get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate
                .get(Calendar.DATE) + 1, 0, 0, 0);
        return (int) (tommorowDate.getTimeInMillis() - curDate.getTimeInMillis()) / 1000;

    }

    /**
     * 当前时刻在两个时间之间
     *
     * @param beginTime 开始时间 2018-05-10 00:00:00
     * @param endTime   结束时间 2019-05-10 00:00:00
     * @return 是否在这两个时间之内
     */
    public static boolean nowIsBetween(String beginTime, String endTime) {
        LocalDateTime begin = getLocalDateFromStrFull(beginTime);
        LocalDateTime end = getLocalDateFromStrFull(endTime);
        if (begin == null || end == null) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        if (begin.isBefore(now) && end.isAfter(now)) {
            return true;
        }
        return false;
    }


    /**
     * 当前时刻在两个时间之间
     *
     * @param begin 开始时间 2018-05-10 00:00:00
     * @param end   结束时间 2019-05-10 00:00:00
     * @return 是否在这两个时间之内
     */
    public static boolean nowIsBetween(LocalDateTime begin, LocalDateTime end, LocalDateTime now) {
        return (begin.isBefore(now) && end.isAfter(now));
    }


    /**
     * 当前时刻在两个时间之间
     *
     * @param begin 开始时间 2018-05-10 00:00:00
     * @param end   结束时间 2019-05-10 00:00:00
     * @return 是否在这两个时间之内
     */
    public static boolean nowIsBetween(Date begin, Date end, Date now) {
        return (begin.before(now) && end.after(now));
    }


//    /**
//     * 某个时间是否在每天的某个时间段之间
//     *
//     * @param begin 09:22:22
//     * @param end   21:33:22
//     * @return 是否在之间
//     */
//    public static boolean everyDayNowIsBetween(String begin, String end) {
//        String dayStr = new DateTime().toString("yyyy-MM-dd");
//        String beginTime = dayStr.concat(" ").concat(begin);
//        String endTime = dayStr.concat(" ").concat(end);
//        return DateUtil.nowIsBetween(beginTime, endTime);
//    }

    /**
     * 获取时间的开始一刻
     *
     * @param date 时间
     * @return 时间的开始一刻
     */
    public static Date getStartTime(Date date) {
        if (date == null)
            return null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取时间的最后一刻
     *
     * @param date 时间
     * @return 时间的最后一刻
     */
    public static Date getEndTime(Date date) {
        if (date == null)
            return null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }
    /**
     * 获取本月第一天
     *
     * @return
     */
    public static Date getMonthFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 0);
        return calendar.getTime();
    }
}
