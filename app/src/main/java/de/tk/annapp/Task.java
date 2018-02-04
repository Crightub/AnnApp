package de.tk.annapp;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jakob on 13.01.2018.
 */

public class Task implements Serializable {

    public String task;
    public String date;
    public String subject;
    public String kind;

    public Task(String _task, String _date, String _kind, String _subject){
        task = _task;
        date = _date;
        kind = _kind;
        subject = _subject;
    }
}
