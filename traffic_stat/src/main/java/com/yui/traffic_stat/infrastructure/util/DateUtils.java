package com.yui.traffic_stat.infrastructure.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.FormatterClosedException;

/**
 * @Author yfy2001
 * @date 2024/4/24 12:40
 */
@Component
public class DateUtils {

    /**
     * 在原日期的基础上增加小时数
     * @param date
     * @param i
     * @return
     */
    public static Date addHour(Date date, int i){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, i);
        return c.getTime();
    }

    /**
     * 在原日期的基础上增加天数
     * @param date
     * @param i
     * @return
     */
    public Date addDay(Date date,int i){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, i);
        return c.getTime();
    }

    /**
     * 字符串转Date
     * @param dateString
     * @return
     */
    public static Date parseDateSecond(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateString);
            System.out.println("解析后的日期时间为：" + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date parseDateDay(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateString);
            System.out.println("解析后的日期时间为：" + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String formatDateDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString;
        dateString = sdf.format(date);
        System.out.println("解析后的日期时间为：" + dateString);
        return dateString;
    }

    public static int getHour(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    public static int getWeek(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

}
