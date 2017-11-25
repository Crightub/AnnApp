package de.tk.annapp;

import java.util.Calendar;
import java.util.Date;

public class grade {

    //actual grade
    public int grade;

    //grade iswritten = true;
    boolean iswritten;

    public float rating;

    public String note;

    public grade(int _grade, boolean _iswritten, float _rating, String _note){
        grade = _grade;
        iswritten = _iswritten;
        rating = _rating;
        note = _note;
    }
}
