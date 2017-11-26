package de.tk.annapp;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Grade implements Serializable{

    //actual Grade
    public int grade;

    //Grade iswritten = true;
    public boolean iswritten;

    public float rating;

    public String note;

    public Grade(int _grade, boolean _iswritten, float _rating, String _note){
        grade = _grade;
        iswritten = _iswritten;
        rating = _rating;
        note = _note;
    }
}
