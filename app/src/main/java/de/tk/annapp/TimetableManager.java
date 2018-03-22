package de.tk.annapp;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Time;
import java.util.ArrayList;

public class TimetableManager {

    private static final TimetableManager timetableManager = new TimetableManager();

    ArrayList<Day> days = new ArrayList<>();
    Context context;


    private TimetableManager(){
        System.out.println("Create TimetableManager...");
        days.add(new Day());
        days.add(new Day());
        days.add(new Day());
        days.add(new Day());
        days.add(new Day());

    }

    //Returns the singelton subjectManager
    public static TimetableManager getInstance(){
        return timetableManager;
    }

    //TODO Duplicate wit Day.setLesson
    public void setLesson(Subject subject, String room, int time /*Number of the lesson (1st lesson, 2nd lesson, ...)*/, int _day){
        Lesson lesson = new Lesson(subject, room==null?subject.room:room);

        days.get(_day).setLesson(subject, room, time);

        save(context, "timetable");
    }

    public void setContext(Context context){
        this.context = context;
    }

    public ArrayList<Day> getDays(){
        return days;
    }

    public void load(Context cntxt, String filename)
    {
        try {
            ObjectInputStream ois = new ObjectInputStream(cntxt.openFileInput(filename));
            days = (ArrayList<Day>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(Context cntxt, String filename){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(cntxt.openFileOutput(filename, Context.MODE_PRIVATE));
            oos.writeObject(days);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
