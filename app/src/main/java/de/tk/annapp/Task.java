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
    public int dateDiff;
    public int dateInt = 0;

    public Task(String _task, String _date, String _kind, String _subject){
        task = _task;
        if(_date == "Mo")
            dateInt = 2;
        else if(_date == "Di")
            dateInt = 3;
        else if(_date == "Mi")
            dateInt = 4;
        else if(_date == "Do")
            dateInt = 5;
        else if(_date == "Fr")
            dateInt = 6;
        else if(_date == "Sa")
            dateInt = 7;
        else if(_date == "So")
            dateInt = 1;
        date = _date;
        kind = _kind;
        subject = _subject;
    }

    public int dateDiff(){
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        dateDiff = dateInt - dayOfWeek;
        if(dateDiff < 0){
            dateDiff = dateDiff + 7;
        }
        return 0;
    }
}
