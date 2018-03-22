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

    public Grade(int grade, boolean iswritten, float rating, String note){
        this.grade = grade;
        this.iswritten = iswritten;
        this.rating = rating;
        this.note = note;
    }
}
