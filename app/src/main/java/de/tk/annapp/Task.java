package de.tk.annapp;

import android.support.annotation.NonNull;

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

public class Task implements Serializable, Comparable<Task>{

    /*
    I drastically changed this bullshit
     */
    private String task;
    private Calendar date;
    private Subject subject;
    private String kind;
    private Calendar due;

    public Task(String task, Calendar date, String kind, Subject subject, Calendar due){
        this.task = task;
        this.due = due;
        this.kind = kind;
        this.subject = subject;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Calendar getDue() {
        return due;
    }

    public void setDue(Calendar due) {
        this.due = due;
    }

    //TODO Check for shortening Woatever this does
    /*private void dateDiff(){
        Calendar cal = Calendar.getInstance();
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
    }*/


    @Override
    public int compareTo(@NonNull Task task) { //TODO Check order
        return getDue().after(task.getDue())?1:
                getDate().after(task.getDate())?1:0;
    }
}