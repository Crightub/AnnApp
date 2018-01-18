package de.tk.annapp;

import java.io.Serializable;

/**
 * Created by Jakob on 13.01.2018.
 */

public class Task implements Serializable {

    public String task;
    public String date;

    public Task(String _task, String _date){
        task = _task;
        date = _date;
    }
}
