package com.sefryek.common.util;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: elahe-PC
 * Date: 22/11/13
 * Time: 6:46 PM
 */
public class DateUtil {
    public static final String DATE_YY_MM_DD_FORMAT = "yy-MM-dd";
    public static final String DATE_YYYY_MM_DD_FORMAT = "yyyy-MM-dd";
    public static final String DATE_DD_MM_YY_FORMAT = "dd-MM-yy";
    public static final String DATE_YYYY_MM_DD_SLASH_FORMAT = "yyyy/MM/dd";

    public static Date stringToDate(String date) throws ParseException {

        SimpleDateFormat sdf =new SimpleDateFormat(DATE_YY_MM_DD_FORMAT);
        date =date.replace("/","-");

        if (date.length() == 10)
               date =date.substring(2);

        return sdf.parse(date);
    }


    public static String dateToString(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_DD_MM_YY_FORMAT);
        return sdf.format(date);
    }

    public static String dateToStringYYY_MM_DD(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_YYYY_MM_DD_SLASH_FORMAT);
        return sdf.format(date);
    }

    public static String dateToStringYYY_MM_DD_with_Dash(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_YYYY_MM_DD_FORMAT);
        return sdf.format(date);
    }

    public static String getNameOfDayByLocale(Date date,Locale locale){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_WEEK);

        DateFormatSymbols dfs = new DateFormatSymbols(locale);
        String weekdays[] = dfs.getWeekdays();
        String nameOfDay = weekdays[day];
        return nameOfDay;
    }

    public static String getNameOfDayByLocale(int dayNo,Locale locale){
        DateFormatSymbols dfs = new DateFormatSymbols(locale);
        String weekdays[] = dfs.getWeekdays();
        String nameOfDay = weekdays[dayNo];
        return nameOfDay;
    }

    public static int getDayOfWeekNumber(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        return day;
    }
}
