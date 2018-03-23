package de.tk.annapp;

import java.io.Serializable;
import java.util.ArrayList;


public class Day implements Serializable{
    public ArrayList<Lesson> lessons = new ArrayList<>();

    public void setLesson(Subject subject, String room, int time /*Number of the lesson (1st lesson, 2nd lesson, ...)*/) {
        Lesson lesson = new Lesson(subject, room==null?subject.room:room);

        while (lessons.size() <= time)
            lessons.add(null);

        System.out.println(lessons.size());

        lessons.set(time, lesson);
    }
}


