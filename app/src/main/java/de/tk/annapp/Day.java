package de.tk.annapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Tobias Kiehnlein on 03.01.2018.
 */

public class Day implements Serializable{
    ArrayList<Lesson> lessons = new ArrayList<>();

    public Day(){}

    public void setLesson(Subject _subject, String _room, int _time /*Number of the lesson (1st lesson, 2nd lesson, ...)*/) {
        String room;
        if (_room.isEmpty())
            room = _subject.room;
        else
            room = _room;

        Lesson lesson = new Lesson(_subject, room);

        while (lessons.size() > _time)
            lessons.add(null);

        lessons.set(_time, lesson);
    }
}


