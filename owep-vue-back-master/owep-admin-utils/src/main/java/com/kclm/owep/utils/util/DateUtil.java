package com.kclm.owep.utils.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @ClassName: DateUtil
 * @Description: 时间工具类
 * @author: huangfei
 * @date: 2017年4月1日 上午11:41:46
 */
public class DateUtil {

    /**
     * 格式：年－月－日 小时:分钟:秒
     */
    public static final String FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";

    /**
     * 格式：年－月－日 小时:分钟
     */
    public static final String FORMAT_TWO = "yyyy-MM-dd HH:mm";

    /**
     * 格式：年月日-小时分钟秒
     */
    public static final String FORMAT_THREE = "yyyyMMdd-HHmmss";

    /**
     * 格式：年月日小时分钟秒
     */
    public static final String FORMAT_FOUR = "yyyyMMddHHmmss";

    /**
     * 格式：月-日 小时:分钟
     */
    public static final String FORMAT_FIVE = "MM-dd HH:mm";

    /**
     * 格式：年－月－日
     */
    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 格式：年月日
     */
    public static final String EIGHT_STYLE_DATE_FORMAT = "yyyyMMdd";

    /**
     * 格式：月－日
     */
    public static final String SHORT_DATE_FORMAT = "MM-dd";

    /**
     * 格式：小时:分钟:秒
     */
    public static final String LONG_TIME_FORMAT = "HH:mm:ss";

    /**
     * 格式：年-月
     */
    public static final String MONTG_DATE_FORMAT = "yyyy-MM";

    /**
     * 格式: 年/月
     */
    public static final String MONTH_DATE_FORMAT_2 = "yyyy/MM";

    /**
     * 格式：年月日小时分钟秒
     */
    public static final String FORMAT_FIVE_NEW = "yyyyMMddHHmmssSS";

    /**
     * 转换编码，时间转长字符串类型数据
     */
    public static final String FORMAT_TIMESTAMP = "%1$ty%1$tm%1$td%1$tH%1$tM%1$tS";

    /**
     * 年的加减
     */
    public static final int SUB_YEAR = Calendar.YEAR;

    /**
     * 月加减
     */
    public static final int SUB_MONTH = Calendar.MONTH;

    /**
     * 天的加减
     */
    public static final int SUB_DAY = Calendar.DATE;

    /**
     * 小时的加减
     */
    public static final int SUB_HOUR = Calendar.HOUR;

    /**
     * 分钟的加减
     */
    public static final int SUB_MINUTE = Calendar.MINUTE;

    /**
     * 秒的加减
     */
    public static final int SUB_SECOND = Calendar.SECOND;

    public DateUtil() {
    }

    /**
     * @Description： 通过传入时间参数获取其对应timestamp类型字符串数据
     * @author: huangfei
     * @since: 2016-3-10 下午5:09:44
     */
    public static String getStringToTimestamp(Date date) {
        String timestamp = String.format(FORMAT_TIMESTAMP, date);

        return timestamp;
    }

    /**
     * @Description： 字符串类型timestamp数据转换Date类型数据
     * @author: huangfei
     * @since: 2016-3-10 下午5:12:57
     */
    public static Date getTimestampToDate(String timestamp) {
        Date date = new Date(Long.valueOf(timestamp));

        return date;
    }

    /**
     * @Description： 获取当前时间类型日期
     * @author: huangfei
     * @since: 2015-5-14 上午12:50:25
     */
    public static Date getCurrentDate() {
        // Date date = new Date();

        /** 当前时间实例 Calendar 转换 Date 类型 */
        Calendar ca1 = Calendar.getInstance();
        /** 得到一个Calendar的实例 */
        ca1.setTime(new Date());
        /** 设置时间为当前时间,同上new Date() */
        Date date = ca1.getTime();

        /** getTime()返回毫秒数 */
        // long dateLong = date.getTime();
        return date;
    }

    /**
     * @Description： 获取当前的日期字符串类型(yyyy - MM - dd)
     * @author: huangfei
     * @since: 2015-5-14 下午12:01:14
     */
    public static String getCurrDate() {

        return DateUtil.formatDateTime(new Date(), DateUtil.LONG_DATE_FORMAT);
    }

    /**
     * @Description： 获取当前的日期字符串类型(yyyy - MM - dd HH : mm : ss)
     * @author: huangfei
     * @since: 2015-5-14 下午12:01:27
     */
    public static String getCurrDates() {

        return DateUtil.formatDateTime(new Date(), DateUtil.FORMAT_ONE);
    }

    /**
     * @Description： 获取当前的日期字符串类型(yyyy - MM - dd HH : mm : ss)
     * @author: huangfei
     * @since: 2015-5-14 下午12:01:27
     */
    public static String getCurrDates_ymd() {

        return DateUtil.formatDateTime(new Date(), DateUtil.FORMAT_FIVE_NEW);
    }

    /**
     * @Description： 把符合日期格式的字符串转换为日期类型(固定格式)
     * @author: huangfei
     * @since: 2015-5-14 上午12:53:34
     * @param: dateStr时间格式字符串。例如：2015-05-23 23:10:55
     */
    public static Date strToDate(String dateStr) {
        Date d = null;

        /**
         * FORMAT_ONE:yyyy-MM-dd HH:mm:ss； FORMAT_TWO:yyyy-MM-dd HH:mm
         */
        SimpleDateFormat formater = new SimpleDateFormat(FORMAT_ONE);
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr);
        } catch (Exception e) {
            d = null;
        }

        return d;
    }

    /**
     * @Description： 把符合日期格式的字符串转换为日期类型(自定义格式)
     * @author: huangfei
     * @since: 2015-5-14 上午12:59:19
     * @param: dateStr时间格式字符串。例如：2015-05-23 23:10:55
     * @param: format转换格式。例如：FORMAT_ONE:yyyy-MM-dd HH:mm:ss
     */
    public static Date strToDate(String dateStr, String format) {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr);
        } catch (Exception e) {
            d = null;
        }

        return d;
    }

    /**
     * @Description： 获得当前日期字符串，格式"yyyy-MM-dd HH:mm:ss"
     * @author: huangfei
     * @since: 2015-5-14 上午11:36:05
     */
    public static String getStrNow() {
        Calendar today = Calendar.getInstance();

        return formatDateTime(today.getTime(), FORMAT_ONE);
    }

    /**
     * @Description： 把日期转换为字符串(固定格式)
     * @author: huangfei
     * @since: 2015-5-14 上午1:00:46
     * @param: date时间类型数据
     */
    public static String formatDateTime(Date date) {
        String result = null;

        /**
         * FORMAT_ONE:yyyy-MM-dd HH:mm: FORMAT_TWO:yyyy-MM-dd HH:mm
         */
        SimpleDateFormat formater = new SimpleDateFormat(FORMAT_ONE);
        try {
            result = formater.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @param date
     * @param format
     * @return
     * @Title: formatDateTime
     * @Description: 把日期转换为字符串(自定义格式)
     * @date: 2017年4月1日 上午11:42:23
     * @return: String
     */
    public static String formatDateTime(Date date, String format) {
        String result = null;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            result = formater.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @param format
     * @return
     * @Title: getCurrDateStr
     * @Description: 获取当前时间的指定字符串格式，封裝formatDateTime方法
     * @date: 2017年4月1日 上午11:42:38
     * @return: String
     */
    public static String getCurrDateStr(String format) {

        return formatDateTime(new Date(), format);
    }

    /**
     * @param date
     * @param num
     * @return
     * @throws Exception
     * @Title: calculateDateTime
     * @Description: 通过传入参数计算时间，获取指定日期计算后的年/月/日
     * @date: 2017年4月1日 上午11:42:55
     * @return: Date
     */
    public static Date calculateDateTime(Date date, Integer num) throws Exception {
        SimpleDateFormat formater = new SimpleDateFormat(FORMAT_ONE);
        String result = formater.format(date);

        Date dt = formater.parse(result);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        /** 例如：num为正数8，表示当前时间增加8天；num为正数-8，表示当前时间减去8天 */
        rightNow.add(Calendar.DATE, num);
        /** 例如：num为正数8，表示当前时间增加8个月；num为正数-8，表示当前时间减去8个月 */
        // rightNow.add(Calendar.MONTH, num);
        /** 例如：num为正数8，表示当前时间增加8年；num为正数-8，表示当前时间减去8年 */
        // rightNow.add(Calendar.YEAR, num);

        Date dtResult = rightNow.getTime();
        return dtResult;
    }

    /**
     * @param year
     * @param month
     * @return
     * @Title: getDaysOfMonth
     * @Description: 获得某年内某月的天数
     * @date: 2017年4月1日 上午11:43:28
     * @return: int
     */
    public static int getDaysOfMonth(String year, String month) {
        int days = 0;
        if (month.equals("1") || month.equals("3") || month.equals("5") || month.equals("7") || month.equals("8") || month.equals("10") || month.equals("12")) {
            days = 31;
        } else if (month.equals("4") || month.equals("6") || month.equals("9") || month.equals("11")) {
            days = 30;
        } else {
            if ((Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0) || Integer.parseInt(year) % 400 == 0) {
                days = 29;
            } else {
                days = 28;
            }
        }

        return days;
    }

    /**
     * @param year
     * @param month
     * @return
     * @Title: getDaysOfMonth
     * @Description: 获取某年某月的天数
     * @date: 2017年4月1日 上午11:44:12
     * @return: int
     */
    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * @return
     * @Title: getToday
     * @Description: 获得当前天
     * @date: 2017年4月1日 上午11:44:32
     * @return: int
     */
    public static int getToday() {
        Calendar calendar = Calendar.getInstance();

        return calendar.get(Calendar.DATE);
    }

    /**
     * @Description： 获得当前传入日期天
     * @author: huangfei
     * @since: 2015-5-14 上午11:18:26
     */
    public static int getToday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.DATE);
    }

    /**
     * @Description： 获得当前日期月份
     * @author: huangfei
     * @since: 2015-5-14 上午11:16:23
     */
    public static int getToMonth() {
        Calendar calendar = Calendar.getInstance();

        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * @Description： 获得当前传入日期月份
     * @author: huangfei
     * @since: 2015-5-14 上午11:20:54
     */
    public static int getToMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * @Description： 获得当前日期年份
     * @author: huangfei
     * @since: 2015-5-14 上午11:16:56
     */
    public static int getToYear() {
        Calendar calendar = Calendar.getInstance();

        return calendar.get(Calendar.YEAR);
    }

    /**
     * @Description： 获得当前传入日期年份
     * @author: huangfei
     * @since: 2015-5-14 上午11:19:07
     */
    public static int getToYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.YEAR);
    }

    /**
     * @Description： 两个日期相减得到秒数(secTime - firstTime)
     * @author: huangfei
     * @since: 2015-5-14 上午10:48:04
     * @param: firstTime 时间格式字符串。例如：2015-05-23 23:10:55
     * @param: secTime 时间格式字符串。例如：2015-05-23 23:10:55
     */
    public static long timeSubSecond(String firstTime, String secTime) {
        /** 字符串转日期类型数据，然后获取毫秒数 */
        long first = strToDate(firstTime, FORMAT_ONE).getTime();
        long second = strToDate(secTime, FORMAT_ONE).getTime();

        return (second - first) / 1000;
    }

    /**
     * @Description： 两个日期相减得到秒数
     * @author: huangfei
     * @since: 2015-5-14 上午11:07:22
     * @param: firstTime 时间类型
     * @param: secTime 时间类型
     */
    public static long timeSubSecond(Date firstTime, Date secTime) {
        long first = firstTime.getTime();
        long second = secTime.getTime();

        return (second - first) / 1000;
    }

    /**
     * @Description： 比较两个日期的年差
     * @author: huangfei
     * @since: 2015-5-14 上午11:25:43
     * @param: before时间格式字符串。例如：2015-05-23 23:10:55
     * @param: after时间格式字符串。例如：2015-05-23 23:10:55
     */
    public static int yearDiff(String before, String after) {
        Date beforeDay = strToDate(before, LONG_DATE_FORMAT);
        Date afterDay = strToDate(after, LONG_DATE_FORMAT);

        return getToYear(afterDay) - getToYear(beforeDay);
    }

    /**
     * @Description： 比较指定日期与当前日期的差(年差)
     * @author: huangfei
     * @since: 2015-5-14 上午11:27:35
     * @param: after指定日期, 时间格式字符串。例如：2015-05-23 23:10:55
     */
    public static int yearDiffCurr(String after) {
        Date beforeDay = new Date();
        Date afterDay = strToDate(after, LONG_DATE_FORMAT);

        return getToYear(beforeDay) - getToYear(afterDay);
    }

    /**
     * @Description： 比较指定日期与当前日期的差(天数差)
     * @author: huangfei
     * @since: 2015-5-14 上午11:29:58
     * @param: before指定日期, 时间格式字符串。例如：2015-05-23 23:10:55
     */
    public static long dayDiffCurr(String before) {
        Date currDate = DateUtil.strToDate(getCurrDate(), LONG_DATE_FORMAT);
        Date beforeDate = strToDate(before, LONG_DATE_FORMAT);

        return (currDate.getTime() - beforeDate.getTime()) / 86400000;
    }

    /**
     * @Description： 判断2个时间相差多少天
     * @author: huangfei
     * @since: 2015-5-14 下午1:50:51
     * @param: pBeginTime日期类型参数
     * @param: pEndTime日期类型参数
     */
    public static Long timeDiffForDay(Date pBeginTime, Date pEndTime) {
        Long beginL = pBeginTime.getTime();
        Long endL = pEndTime.getTime();
        Long day = (endL - beginL) / 86400000;

        return day;
    }

    /**
     * @Description： 计算两个日期相差的天数，如果date2 > date1 返回正数，否则返回负数
     * @author: huangfei
     * @since: 2015-5-14 上午11:21:47
     * @param: date1 日期类型参数
     * @param: date2 日期类型参数
     */
    public static long dayDiff(Date date1, Date date2) {

        return (date2.getTime() - date1.getTime()) / 86400000;
    }

    /**
     * @Description： 获取每月的第一周
     * @author: huangfei
     * @since: 2015-5-14 上午11:34:04
     * @param: year 年。例如：2015
     * @param: month 月。例如：4
     */
    public static int getFirstWeekdayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
        c.set(year, month - 1, 1);

        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * @Description： 获取每月的最后一周的天数
     * @author: huangfei
     * @since: 2015-5-14 上午11:32:27
     * @param: year 年。例如：2015
     * @param: month 月。例如：4
     */
    public static int getLastWeekdayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
        c.set(year, month - 1, getDaysOfMonth(year, month));

        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * @Description： 根据生日获取星座
     * @author: huangfei
     * @since: 2015-5-14 上午11:37:13
     * @param: birth 生日。例如："1987-12-04"
     */
    public static String getAstro(String birth) {
        if (!isDate(birth)) {
            birth = "2000" + birth;
        }
        if (!isDate(birth)) {
            return "";
        }

        int month = Integer.parseInt(birth.substring(birth.indexOf("-") + 1, birth.lastIndexOf("-")));
        int day = Integer.parseInt(birth.substring(birth.lastIndexOf("-") + 1));
        String s = "魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯";
        int[] arr = {20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22};
        int start = month * 2 - (day < arr[month - 1] ? 2 : 0);

        return s.substring(start, start + 2) + "座";
    }

    /**
     * @Description： 判断日期是否有效, 包括闰年的情况 YYYY-mm-dd
     * @author: huangfei
     * @since: 2015-5-14 上午11:39:24
     * @param: date YYYY-mm-dd格式时间字符串
     */
    public static boolean isDate(String date) {
        StringBuffer reg = new StringBuffer("^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
        reg.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
        reg.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
        reg.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
        reg.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
        reg.append("1-9])|(1[0-9])|(2[0-8]))))))");

        Pattern p = Pattern.compile(reg.toString());

        return p.matcher(date).matches();
    }

    /**
     * @Description： 取得指定日期过 months 月后的日期 (当 months 为负数表示指定月之前)
     * @author: huangfei
     * @since: 2015-5-14 上午11:42:14
     * @param: date 日期类型参数
     * @param: months 月份参数，任意输入
     */
    public static Date nextMonth(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.MONTH, months);

        return cal.getTime();
    }

    /**
     * @Description： 取得指定日期过 day 天后的日期 (当 day 为负数表示指日期之前);
     * @author: huangfei
     * @since: 2015-5-14 上午11:46:43
     * @param: date 日期类型参数
     * @param: day 天参数，任意输入
     */
    public static Date nextDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DAY_OF_YEAR, day);

        return cal.getTime();
    }

    /**
     * @Description： 取得当前日期参数 day 天前日期(当 day 为负数表示指定时间之后)
     * @author: huangfei
     * @since: 2015-5-14 上午11:49:44
     * @param: d 日期类型参数
     * @param: day 天参数，任意输入
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);

        return now.getTime();
    }

    /**
     * @Description： 取得当前日期 day天后的日期(当 day 为负数表示指定时间之前)
     * @author: huangfei
     * @since: 2015-5-14 上午11:52:03
     * @param: format转换格式。例如：FORMAT_ONE:yyyy-MM-dd HH:mm:ss
     * @param: day 天参数，任意输入
     */
    public static String nextDay(int day, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, day);

        return formatDateTime(cal.getTime(), format);
    }

    /**
     * @Description： 取得当前日期参数过 month月后该日期的前一天(当 month 为负数表示指定时间之前)
     * @author: huangfei
     * @since: 2015-5-14 下午1:58:57
     */
    public static Date getMonthDelay(Date currentDay, int month) {
        Calendar current = Calendar.getInstance();
        current.setTime(currentDay);

        current.add(Calendar.MONTH, month);
        current.add(Calendar.HOUR, -24);

        return current.getTime();
    }

    /**
     * @Description： 取得指定日期过 week 周后的日期 (当 week 为负数表示指定月之前)
     * @author: huangfei
     * @since: 2015-5-14 上午11:58:38
     * @param: date 日期类型参数
     * @param: week 周参数，任意输入
     */
    public static Date nextWeek(Date date, int week) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.WEEK_OF_MONTH, week);

        return cal.getTime();
    }

    /**
     * @Description： 取得指定日期过 dayNum 天后的日期 (当 dayNum 为负数表示指定月之前)
     * @author: huangfei
     * @since: 2015-5-14 下午2:05:42
     */
    public static Date getBeforeDate(Date mirror, int dayNum) {
        Calendar current = Calendar.getInstance();
        current.setTime(mirror);

        current.add(Calendar.HOUR, -24 * dayNum);
        return current.getTime();
    }

    /**
     * @Description： 获取字符串类型指定格式昨天的日期
     * @author: huangfei
     * @since: 2015-5-14 下午12:03:55
     */
    public static String getBefoDay() {

        return befoDay(DateUtil.LONG_DATE_FORMAT);
    }

    /**
     * @Description： 根据时间类型格式化获取昨天的日期
     * @author: huangfei
     * @since: 2015-5-14 下午12:05:07
     * @param: format转换格式。例如：FORMAT_ONE:yyyy-MM-dd HH:mm:ss
     */
    public static String befoDay(String format) {

        return DateUtil.formatDateTime(DateUtil.nextDay(new Date(), -1), format);
    }

    /**
     * @Description： 根据时间类型格式化获取上月的月份
     * @author: huangfei
     * @since: 2015-5-14 下午12:06:58
     * @param: format转换格式。例如：FORMAT_ONE:yyyy-MM-dd HH:mm:ss
     */
    public static String befoMonth(String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -1);

        return DateUtil.formatDateTime(cal.getTime(), format);
    }

    /**
     * @Description： 获取指定格式的明天日期
     * @author: huangfei
     * @since: 2015-5-14 下午12:07:45
     */
    public static String afterDay() {

        return DateUtil.formatDateTime(DateUtil.nextDay(new Date(), 1), DateUtil.LONG_DATE_FORMAT);
    }

    /**
     * @Description： 通过传入格式化数据获取本月第一天
     * @author: huangfei
     * @since: 2015-5-14 下午12:09:13
     * @param: format转换格式。例如：FORMAT_ONE:yyyy-MM-dd HH:mm:ss
     */
    public static String getFirstDayOfMonth(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);

        return formatDateTime(cal.getTime(), format);
    }

    /**
     * @Description： 通过日期类型参数获取某月第一天
     * @author: huangfei
     * @since: 2015-5-14 下午12:10:41
     * @param: date日期类型参数
     */
    public static Date getFirstDayOfMonth(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);

        return cal.getTime();
    }

    /**
     * @Description： 通过格式化数据获取本月最后一天
     * @author: huangfei
     * @since: 2015-5-14 下午12:12:14
     * @param: format转换格式。例如：FORMAT_ONE:yyyy-MM-dd HH:mm:ss
     */
    public static String getLastDayOfMonth(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);

        return formatDateTime(cal.getTime(), format);
    }

    /**
     * @Description： 根据传入日期类参数获取某月最后一天
     * @author: huangfei
     * @since: 2015-5-14 下午12:12:38
     * @param: date日期类型参数
     */
    public static Date getLastDayOfMonth(Date date) {
        if (date == null) {
            date = new Date();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);

        return cal.getTime();
    }

    /**
     * @Description： 根据传入日期类型参数返回该时间的时间戳(Thu May 14 23 : 59 : 59 CST 2015)最后时刻
     * @author: huangfei
     * @since: 2015-5-14 下午12:25:15
     * @param: date日期类型参数
     */
    public static Date getEndOfDay(Date day) {

        return getEndOfDay(day, Calendar.getInstance());
    }

    /**
     * @Description： getEndOfDay服务类
     * @author: huangfei
     * @since: 2015-5-14 下午1:48:54
     */
    public static Date getEndOfDay(Date day, Calendar cal) {
        if (day == null)
            day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));

        return cal.getTime();
    }

    /**
     * @Description： 获取上个月第一天
     * @author: huangfei
     * @since: 2015-5-14 下午2:08:11
     */
    public static Date getStartOfLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, -1);

        return cal.getTime();
    }

    /**
     * @Description： 获取本月第一天，时间戳Thu May 14 00:00:00 CST 2015初始时刻
     * @author: huangfei
     * @since: 2015-5-14 下午2:08:55
     */
    public static Date getStartOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));

        return cal.getTime();
    }

    /**
     * @Description： 获取上个月最后一天，时间戳Thu Apr 30 23:59:59 CST 2015，最后时刻
     * @author: huangfei
     * @since: 2015-5-14 下午2:10:11
     */
    public static Date getEndOfLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);

        return cal.getTime();
    }

    /**
     * @Description： 获取上个月最后一天，时间戳Thu Apr 30 00:00:00 CST 2015，初始时刻
     * @author: huangfei
     * @since: 2015-5-14 下午2:14:24
     */
    public static Date getMinEndOfLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * @Description： 获取当前时间最后一天日期
     * @author: huangfei
     * @since: 2015-5-14 下午2:16:38
     */
    public static int getDaysOfLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);

        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @Description： 获取两个日期中间的工作日天数
     * @author: huangfei
     * @since: 2015-5-14 下午2:18:16
     * @param: starttime时间格式字符串。例如：2015-05-23 23:10:55
     * @param: endtime时间格式字符串。例如：2015-05-23 23:10:55
     */
    public static int getWorkTime(String starttime, String endtime) {
        // 设置时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(LONG_DATE_FORMAT);
        // 开始日期
        Date dateFrom = null;
        Date dateTo = null;
        try {
            dateFrom = dateFormat.parse(starttime);
            dateTo = dateFormat.parse(endtime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int workdays = 0;
        Calendar cal = null;
        while (dateFrom.before(dateTo) || dateFrom.equals(dateTo)) {
            cal = Calendar.getInstance();
            // 设置日期
            cal.setTime(dateFrom);
            if ((cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) && (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)) {
                // 进行比较，如果日期不等于周六也不等于周日，工作日+1
                workdays++;
            }
            // 日期加1
            cal.add(Calendar.DAY_OF_MONTH, 1);
            dateFrom = cal.getTime();
        }

        return workdays;
    }

    /**
     * @Description： 获取两个日期中间全部日期集合
     * @author: huangfei
     * @since: 2016-3-24 下午2:11:19
     */
    public static List<String> getBetweenDays(String date1, String date2) {
        SimpleDateFormat format = new SimpleDateFormat(LONG_DATE_FORMAT);

        List<String> list = new ArrayList<String>();
        if (date1.equals(date2)) {
            System.out.println("两个日期相邻同");

            list.add(date1);
            return list;
        }

        String tmp;
        if (date1.compareTo(date2) > 0) {
            tmp = date1;
            date1 = date2;
            date2 = tmp;
        }

        tmp = format.format(strToDate(date1, LONG_DATE_FORMAT).getTime() + 3600 * 24 * 1000);

        int num = 0;
        while (tmp.compareTo(date2) < 0) {
            list.add(tmp);
            num++;
            tmp = format.format(strToDate(tmp, LONG_DATE_FORMAT).getTime() + 3600 * 24 * 1000);
        }

        if (num == 0) {
            System.out.println("两个日期相邻");

            list.add(date1);
            list.add(date2);
            return list;
        }

        return list;
    }

    /**
     * @Description： 获取本周第一天
     * @author: huangfei
     * @since: 2015-5-14 下午2:23:21
     * @param: date日期类型参数
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());// Monday
        return cal.getTime();
    }

    /**
     * @Description： 获取本周最后一天
     * @author: huangfei
     * @since: 2015-5-14 下午2:26:18
     * @param: date日期类型参数
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6);// Sunday

        return cal.getTime();
    }

    /**
     * @Description： 获取当前日期类型参数次月的最后一天
     * @author: huangfei
     * @since: 2015-5-14 下午2:30:09
     * @param: date日期类型参数
     * @param: format转换格式。例如：FORMAT_ONE:yyyy-MM-dd HH:mm:ss
     */
    public static String getNextMonth0fDay(Date date, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 2);
        cal.add(Calendar.DATE, -1);

        return formatDateTime(cal.getTime(), format);
    }

    /**
     * @Description： 获得某一天的下周一的日期, 默认是当天
     * @author: huangfei
     * @since: 2015-5-14 下午2:34:07
     * @param: date日期类型参数
     */
    public static Date getNextMonday(Date date) {
        final Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }

        final int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        cal.add(Calendar.DATE, (7 - dayOfWeek + 2) % 7);

        return cal.getTime();
    }

    /**
     * @Description： 拿某一时间的 24小时计时法中的小时数, 默认当前时间
     * @author: huangfei
     * @since: 2015-5-14 下午2:35:55
     * @param: date日期类型参数
     */
    public static int getHourOfDay(Date date) {
        final Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }

        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * @Description： 判断当前两个日期是否在同一天
     * @author: huangfei
     * @since: 2016年12月10日 上午10:04:24
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    /**
     * @param startTime
     * @param endTime
     * @return boolean
     * @Title: compare_date
     * @Description: 判断两个时间大于或小于
     * @author huangfei
     * @date: 2017年6月13日 上午11:04:58
     */
    public static boolean compare_date(String startTime, String endTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(startTime);
            Date dt2 = df.parse(endTime);
            if (dt1.getTime() > dt2.getTime()) {
                return false;
            } else if (dt1.getTime() <= dt2.getTime()) {
                return true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * @Description： 判断两个日期是否相邻
     * @author: huangfei
     * @since: 2016年12月14日 下午5:40:38
     * @param: date1日期类型参数, 前一天
     * @param: date2日期类型参数, 后一天
     */
    public static boolean isAdjacentDate(Date date1, Date date2) {
        boolean flag = false;

        String dayStr = DateUtil.formatDateTime(nextDay(date1, 1), FORMAT_FOUR);
        String dayTimeStr = dayStr.substring(0, 8);

        String nextStr = DateUtil.formatDateTime(date2, FORMAT_FOUR);
        String nextDayStr = nextStr.substring(0, 8);

        if (dayTimeStr.equals(nextDayStr)) {
            flag = true;
        } else {
            flag = false;
        }

        return flag;
    }

    /**
     * @Description： 获取传入日期参数的起始时间
     * @author: huangfei
     * @since: 2016年12月14日 下午6:45:46
     * @param: date日期类型参数
     */
    public static Date getCurrentDateStartTime(Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date startTime = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.SECOND, -1);

        String strDate = DateUtil.formatDateTime(date, LONG_DATE_FORMAT);
        startTime = DateUtil.strToDate(strDate + " 00:00:00", FORMAT_ONE);

        return startTime;
    }

    /**
     * @Description： 获取传入日期参数的当天最后时间
     * @author: huangfei
     * @since: 2016年12月14日 下午6:52:12
     * @param: date日期类型参数
     */
    public static Date getCurrentDateEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date endTime = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.SECOND, -1);

        String strDate = formatDateTime(date, LONG_DATE_FORMAT);
        endTime = strToDate(strDate + " 23:59:59", FORMAT_ONE);

        return endTime;

    }


    public static Date formatDateString(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    /*******************
     * 把LocalDateTime转换成java.util.Date对象
     * @param dateTime
     * @return
     */
    public static Date toDate(LocalDateTime dateTime) {
        if(dateTime == null) {
            throw new RuntimeException("日期为null");
        }
        //
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static void main(String[] args) throws Exception {
        // System.out.println(getStringToTimestamp(new Date()));
        // System.out.println(getTimestampToDate("160310171411"));
        // System.out.println(strToDate("2015-05-23 23:10:55"));
        // System.out.println(strToDate("2015-05-23 23:10:55", FORMAT_ONE));
        // System.out.println(formatDateTime(new Date(), FORMAT_FOUR));

        // System.out.println(calculateDateTime(strToDate("20161214165659",
        // FORMAT_FOUR), 1));
        // System.out.println(strToDate("20161214165659", FORMAT_FOUR));
        // System.out.println(formatDateTime(calculateDateTime(strToDate("20161214165659",
        // FORMAT_FOUR), 1)));
        // System.out.println(dayDiff(new Date(),
        // calculateDateTime(strToDate("20161214165659", FORMAT_FOUR), 1)));

        //System.out.println("判断两日是否在同一天：" + isAdjacentDate(new Date(), calculateDateTime(strToDate("20161215165659", FORMAT_FOUR), 1)));

        // System.out.println(timeDiffForDay(new Date(),
        // calculateDateTime(strToDate("20161213152059", FORMAT_FOUR), 1)));

        // System.out.println(dayDiff(calculateDateTime(new Date(), 3), new
        // Date()));
        // System.out.println(yearDiff("2015-05-23 23:10:55",
        // "2016-05-23 23:10:55"));
        // System.out.println(dayDiffCurr("2016-12-10 01:10:55"));
        // System.out.println(getFirstWeekdayOfMonth(2015, 4));
        // System.out.println(getStrNow());
        // System.out.println(getAstro("1987-12-04"));
        // System.out.println(isDate("1987-12-32"));
        // System.out.println(nextMonth(new Date(), -10));
        // System.out.println(nextDay(new Date(), 180));

        // System.out.println(getToMonth(nextDay(new Date(), 20)));
        // System.out.println(getToday(nextDay(new Date(), 20)));
        // System.out.println(getDateBefore(new Date(), 10));
        // System.out.println(nextDay(-10, FORMAT_ONE));
        // System.out.println(nextWeek(new Date(), 3));
        // System.out.println(getCurrDates());
        // System.out.println(getBefoDay());
        // System.out.println(befoDay(FORMAT_ONE));
        // System.out.println(befoMonth(FORMAT_ONE));
        // System.out.println(afterDay());
        // System.out.println(getFirstDayOfMonth(FORMAT_ONE));
        // System.out.println(getFirstDayOfMonth(new Date()));
        // System.out.println(getLastDayOfMonth(new Date()));
        // System.out.println(getLastDayOfMonth(FORMAT_ONE));
        // System.out.println(getEndOfDay(new Date()));

        // System.out.println(getMonthDelay(new Date(), -5));
        // System.out.println(getBeforeDate(new Date(), 5));
        // System.out.println(getStartOfLastMonth());
        // System.out.println(getStartOfToday());
        // System.out.println(getEndOfLastMonth());
        // System.out.println(getMinEndOfLastMonth());
        // System.out.println(getDaysOfLastMonth());
        // System.out.println(getWorkTime("2015-05-23 23:10:55",
        // "2015-06-23 23:10:55"));
        // System.out.println(getLastDayOfWeek(new Date()));
        // System.out.println(getNextMonth0fDay(new Date(), FORMAT_ONE));
        // System.out.println(getHourOfDay(new Date()));

        // String date1 = "2016-02-26";
        // String date2 = "2015-03-11";
        // System.out.println(getBetweenDays(date1, date2));

        // System.out.println(timeSubSecond("2015-05-23 23:10:55",
        // "2016-05-23 23:10:55") / 60 / 60 / 24);

        //
        Date date = toDate(LocalDateTime.now().plusDays(8));
        System.out.println(date);
    }


    public static LocalDateTime stringToLocalDateTime(String string) {
        final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd[['T'hh][:mm][:ss]]")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
                .toFormatter();
        final LocalDateTime localDateTime = LocalDateTime.parse(string, formatter);
        return localDateTime;
    }

}