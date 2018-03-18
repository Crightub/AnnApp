package de.tk.annapp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Jakob on 13.01.2018.
 */

public class Task implements Serializable {

    public String task;
    public String date;
    public String subject;
    public String kind;
    public boolean weekday;
    public long dateNumber;

    public Task(String _task, Date _date, String _kind, String _subject, String _day, boolean _cal){
        task = _task;
        weekday = _cal;
        if(!weekday){
            date = _day;
            dateDiff();
        }
        else{
            dateNumber = _date.getTime();
            date = _date.getDate() + "." + (_date.getMonth() + 1) + ".";
        }
        kind = _kind;
        subject = _subject;
    }

    public void dateDiff(){
        Calendar cal = new GregorianCalendar();
        int year = cal.getTime().getYear();
        int day = cal.getTime().getDate();
        int today = cal.getTime().getDay();
        int month = cal.getTime().getMonth();
        int dayInt = 0;
        int dayDiff;
        Date calInt;

        if(date == "Mo"){
            dayInt = 1;
        }
        else if(date == "Di"){
            dayInt = 2;
        }
        else if(date == "Mi"){
            dayInt = 3;
        }
        else if(date == "Do"){
            dayInt = 4;
        }
        else if(date == "Fr"){
            dayInt = 5;
        }
        else if(date == "Sa"){
            dayInt = 6;
        }
        else if(date == "So"){
            dayInt = 0;
        }

        dayDiff = dayInt - today;
        if(dayDiff < 0){
            dayDiff = dayDiff + 7;
        }

        int dayOfMonth = day + dayDiff;
        int realYear = year + 1900;
        //Jahres- und Monatsübergänge
        if(month == 0 || month == 2 || month == 4 || month == 6 || month == 7 || month == 9 || month == 11){
            if(dayOfMonth > 31){
                month = month + 1;
                dayOfMonth = dayOfMonth - 31;
            }
        }
        if(month == 3 || month == 5 || month == 8 || month == 10){
            if(dayOfMonth > 30){
                month = month + 1;
                dayOfMonth = dayOfMonth - 30;
            }
        }
        if(month == 1 && (realYear % 4) != 0){
            if(dayOfMonth > 28){
                month = month + 1;
                dayOfMonth = dayOfMonth - 28;
            }
        }
        if(month == 1 && (realYear % 4) == 0 && (realYear % 400) != 0){
            if(dayOfMonth > 29){
                month = month + 1;
                dayOfMonth = dayOfMonth - 29;
            }
        }
        if(month == 1 && (realYear % 4) == 0 && (realYear % 400) == 0){
            if(dayOfMonth > 28){
                month = month + 1;
                dayOfMonth = dayOfMonth - 28;
            }
        }
        if(month > 11){
            realYear = realYear + 1;
            month = 0;
        }

        calInt = new Date(realYear, month, dayOfMonth);
        dateNumber = calInt.getTime();
    }
}