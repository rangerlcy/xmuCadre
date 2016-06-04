/*
 * COPYRIGHT © 2012-2015 厦门优策信息科技有限公司
 */
package com.cadre.model.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.DurationFieldType;
import org.joda.time.format.DateTimeFormat;
/**
 * @Type: com.cadre.model.utils.DateUtil.java
 * @ClassName: DateUtil
 * @Version: 1.0 2014年4月2日 下午3:32:52
 * @Description: <br/>
 * 引入joda-time来替换jdk原生的Date和Calendar<br/>
 * (日期的处理是系统中常用的功能。原生的Date由于设计问题使得在java中
 * 使用时间成为一项痛苦而繁杂的工作。
 * Calendar在一定程度上解决了部分Date的问题，但是却没有提供简单的方法，
 * 同时在Calendar上获取"正常"的日期也变得困难，
 * 而且Calendar上的多日历系统都是通过它的子类来实现的，显得非常笨重)
 * (joda-time是业界广泛接受的第三方开源时间实现方案，提供了更系统而直观的时间操作和使用接口，
 * (springFramework中也集成了对joda-time的支持)。
 * joda-time的作者也成为JDK8中新的时间实现的领导者)
 *
 * （joda-time简单易用的接口实际上已经基本不需要这个Util了，
 * 实现这个Util主要是列出一些joda-time的使用方式和作为DateUtil的延续）
 *
 */
public class DateUtil {

    public final static String DEFAULT_YMDHMS_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String DEFAULT_YMD_FORMAT = "yyyy-MM-dd";
    public final static String SOLR_TDATE_FORMATE = "yyyy-MM-dd'T'HH:mm:ss.000'Z'";
    public final static String SOLR_TDATE_RETURN_FORMATE = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    
    public final static String COMPACT_YMDHMS_FORMAT = "yyyyMMddHHmmss";
    public final static String COMPACT_YMD_FORMAT = "yyyyMMdd";

    /**
     * 获得当前时间的joda-time对象
     * @return
     */
    public static DateTime currentTime(){
        //这里网上推荐用SystemFacotry.getClock().getDateTime(), 但是SystemFacotry不是jdk的原生类，所以暂时不用
        return new DateTime();
    }

    /**
     * 将时间字符串直接转换为joda-time对象
     * @param timeStr
     * @return
     */
    public static DateTime time(String timeStr){
        return time(timeStr, null);
    }
    
    /**
     * 将时间转换为归一化的类型，即转换为绝对秒数的字符类型
     * @param timeStr
     * @return
     */
	public static String time2Str(String timeStr) {
		DateTime time = time(timeStr);
		if (time == null) {
			return "";
		}
		return String.valueOf(time.getMillis() / 1000);
	}
	
	/**
	 * 将时间转换为指定格式的字符串
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String time2Str(Date date, String pattern) {
		DateTime dateTime = new DateTime(date);
		return dateTime.toString(pattern);
	}

    /**
     * 将时间字符串直接转换为joda-time对象
     * @param timeStr
     * @param timePattern 时间格式
     * @return
     */
    public static DateTime time(String timeStr, String timePattern){
        if(null == timeStr || timeStr.equals("")){
            return null;
        }
        if(null != timePattern && !timePattern.equals("")){
            //有指定转换器
            return DateTimeFormat.forPattern(timePattern).parseDateTime(timeStr);
        } else {
            //没指定转换器
            if(timeStr.length() == 19){
                return DateTimeFormat.forPattern(DEFAULT_YMDHMS_FORMAT).parseDateTime(timeStr);
            } else if(timeStr.length() == 10){
                return DateTimeFormat.forPattern(DEFAULT_YMD_FORMAT).parseDateTime(timeStr);
            } else if(timeStr.length() == 24){
            	return DateTimeFormat.forPattern(SOLR_TDATE_FORMATE).parseDateTime(timeStr);
            } else if(timeStr.length() == 20){
            	return DateTimeFormat.forPattern(SOLR_TDATE_RETURN_FORMATE).parseDateTime(timeStr);
            } else {
                return new DateTime(timeStr);
            }
        }
    }

    /**
     * 比较一个日期值是否是在两个日期值之间
     * @param min
     * @param max
     * @param compareDate
     * @return
     */
    public static boolean betweenTwoDate(Date min,Date max,Date compareDate) {
    	if(compareDate == null) return false;
    	if(min == null && max == null) return false;
    	if(min == null && max != null) {
    		return max.after(compareDate);
    	}
    	if(min != null && max == null) {
    		return min.before(compareDate);
    	}
    	return (min.before(compareDate) && max.after(compareDate));
    }
    
    /**
     * 比较当前日期值是否是在两个日期值之间
     * @param min
     * @param max
     * @return
     */
    public static boolean nowBetweenTwoDate(Date min,Date max){
    	return betweenTwoDate(min,max,new Date());
    }
    
    /**
     * 将字符串转换成时间
     * @param str
     * @param pattern
     * @return
     */
    public static Date parseDate(String str,String pattern) {
    	if(StringUtils.isEmpty(str)) {
    		return null;
    	}
    	
    	SimpleDateFormat format = new SimpleDateFormat(pattern);
    	try {
			return format.parse(str);
		} catch (ParseException e) {
			throw new RuntimeException("Could not parse date: " + e.getMessage(), e);
		}
    }
    /**
     * 获得当前格式化以后的时间（yyyy-MM-dd HH:mm:ss）
     * @return
     */
    public static String now(){
        return now(DEFAULT_YMDHMS_FORMAT);
    }

    /**
     * 获得当前格式化以后的时间
     * @param pattern
     * @return
     */
    public static String now(String pattern){
        return currentTime().toString(pattern);
    }

    /**
     * 获得两个时间点之间的时间跨度
     * @param time1 开始的时间点
     * @param time2 结束的时间点
     * @param timeUnit 跨度的时间单位 see {@link JodaTime} （支持的时间单位有DAY,HOUR,MINUTE,SECOND,MILLI）
     */
    public static long lengthBetween(DateTime time1, DateTime time2, DurationFieldType timeUnit){
        Duration duration = Days.daysBetween(time1, time2).toStandardDuration();
        if(timeUnit == JodaTime.DAY){
            return duration.getStandardDays();
        } else if(timeUnit == JodaTime.HOUR){
            return duration.getStandardHours();
        } else if(timeUnit == JodaTime.MINUTE){
            return duration.getStandardMinutes();
        } else if(timeUnit == JodaTime.SECOND){
            return duration.getStandardSeconds();
        } else if(timeUnit == JodaTime.MILLI){
            return duration.getMillis();
        } else {
            throw new RuntimeException("TimeUnit not supported except DAY,HOUR,MINUTE,SECOND,MILLI");
        }
    }

    /**
     * 还原回jdk的原生Date对象
     * @return
     */
    public static Date parseDate(DateTime dateTime){
        return dateTime.toDate();
    }

    /**
     * 还原回jdk的原生Calendar对象
     * @return
     */
    public static Calendar parseCalendar(DateTime dateTime){
        return dateTime.toCalendar(Locale.getDefault());
    }
    
    /**
     * 根据日期获取当天的开始时间字符串
     * @param date
     * @return
     */
    public static String startTimeStr(Date date){
    	
    	String dateStr = new DateTime(date.getTime()).toString(DEFAULT_YMD_FORMAT) + " 00:00:00";
    	return dateStr;
    }
    
    /**
     * 根据日期获取当天的开始时间
     * @param date
     * @return
     */
    public static Date startTime(Date date){
    	String dateStr = new DateTime(date.getTime()).toString(DEFAULT_YMD_FORMAT) + " 00:00:00";
    	return DateTimeFormat.forPattern(DEFAULT_YMDHMS_FORMAT).parseDateTime(dateStr).toDate();
    }
    
    /**
     * 根据日期获取当天的结束时间
     * @param date
     * @return
     */
    public static Date endTime(Date date){
    	String dateStr = new DateTime(date.getTime()).toString(DEFAULT_YMD_FORMAT) + " 23:59:59";
    	return DateTimeFormat.forPattern(DEFAULT_YMDHMS_FORMAT).parseDateTime(dateStr).toDate();
    }
    
    /**
     * 根据日期获取当天的结束时间字符串
     * @param date
     * @return
     */
    public static String endTimeStr(Date date){
    	String dateStr = new DateTime(date.getTime()).toString(DEFAULT_YMD_FORMAT) + " 23:59:59";
    	return dateStr;
    }
    
	/**
	 * 某个日期的几天前或者几天后的当天开始时间
	 * 
	 * @param date
	 *            给定的日期
	 * @param dayOffset
	 *            日期偏移量1标示一天后,-1标示1天前
	 * @return
	 */
	public static long startTimeWithDayOffset(Date date, int dayOffset) {
		if(date == null) date = new Date();
		long time = date.getTime() + Long.valueOf(dayOffset) * (1000 * 60 * 60 * 24);
		return startTime(new Date(time)).getTime();
	}

	/**
	 * 某个日期的几天前或者几天后的当天结束时间
	 * 
	 * @param date
	 *            给定的日期
	 * @param dayOffset
	 *            日期偏移量1标示一天后,-1标示1天前
	 * @return
	 */
	public static long endTimeWithDayOffset(Date date, int dayOffset) {
		if(date == null) date = new Date();
		long time = date.getTime() + Long.valueOf(dayOffset) * (1000 * 60 * 60 * 24);
		return endTime(new Date(time)).getTime();
	}
    
    /**
     * 判断是否是同一天
     * @param date1
     * @param date2
     * @return
     */
    @SuppressWarnings("deprecation")
	public static boolean sameDay(Date date1,Date date2){
    	return date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDate() == date2.getDate();
    }

    public static void main(String[] args){
        System.out.println(DateUtil.now("yyyy-MM-dd"));
        System.out.println(DateUtil.time("2013-04-20 11:15:15").toString("yyyy年MM月dd日HH时mm分ss秒"));

        System.out.println(
                DateUtil.lengthBetween(
                        DateUtil.time("2013-04-20 11:15:15"),
                        DateUtil.time("2013-04-18 11:15:15"),
                        JodaTime.SECOND
                )
        );
        
        System.out.println(DateUtil.time2Str(new Date(), DateUtil.DEFAULT_YMDHMS_FORMAT));
    }
	
	public static String nowDate(){
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_YMD_FORMAT);
		return sdf.format(new Date());
	}
	
	public static String formatStr(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_YMD_FORMAT);
		if(date == null){
			return "";
		}
		return sdf.format(date);		
	}
}

/**
 * jodaTime枚举类似类<br/>
 *
 * @author weic
 * @version 1.0 13-4-26 下午5:08
 */
class JodaTime {

    /**
     * 时代(1000年)
     */
    public static DurationFieldType ERA = DurationFieldType.eras();
    /**
     * 世纪(100年)
     */
    public static DurationFieldType CENTURY = DurationFieldType.centuries();
    /**
     * 一年中的第几周
     */
    public static DurationFieldType WEEK_YEAR = DurationFieldType.weekyears();
    /**
     * 年
     */
    public static DurationFieldType YEAR = DurationFieldType.years();
    /**
     * 月
     */
    public static DurationFieldType MONTH = DurationFieldType.months();
    /**
     * 日
     */
    public static DurationFieldType DAY = DurationFieldType.days();
    /**
     * 周
     */
    public static DurationFieldType WEEK = DurationFieldType.weeks();
    /**
     * 半天
     */
    public static DurationFieldType HALF_DAY = DurationFieldType.halfdays();
    /**
     * 时
     */
    public static DurationFieldType HOUR = DurationFieldType.hours();
    /**
     * 分
     */
    public static DurationFieldType MINUTE = DurationFieldType.minutes();
    /**
     * 秒
     */
    public static DurationFieldType SECOND = DurationFieldType.seconds();
    /**
     * 毫秒
     */
    public static DurationFieldType MILLI = DurationFieldType.millis();
}
