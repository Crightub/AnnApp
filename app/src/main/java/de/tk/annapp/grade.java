package de.tk.annapp;

import java.util.Calendar;
import java.util.Date;

public class grade {

    //actual grade
    public int grade;

    //grade iswritten = true;
    boolean iswritten;

    int rating;

    //date when the grade was added
    public Date date;

    public grade(int _grade, boolean _iswritten, int _rating){
        grade = _grade;
        iswritten = _iswritten;
        rating = _rating;
        date = Calendar.getInstance().getTime();
    }
}
