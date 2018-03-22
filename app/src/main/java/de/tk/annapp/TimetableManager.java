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

    public void setLesson(Subject _subject, String _room, int _time /*Number of the lesson (1st lesson, 2nd lesson, ...)*/, int _day){
        String room;



        if (_room == null)
            room = _subject.room;
        else
            room = _room;

        Lesson lesson = new Lesson(_subject, room);

        days.get(_day).setLesson(_subject, _room, _time);

        save(context, "timetable");
    }

    public void setContext(Context context){
        this.context = context;
    }

    public ArrayList<Day> getDays(){
        return days;
    }

    public void load(Context c, String filename)
    {
        try {
            ObjectInputStream ois = new ObjectInputStream(c.openFileInput(filename));
            days = (ArrayList<Day>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(Context c, String filename){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(c.openFileOutput(filename, Context.MODE_PRIVATE));
            oos.writeObject(days);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
