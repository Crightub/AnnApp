package de.tk.annapp;

import java.io.Serializable;

/**
 * Created by Tobias Kiehnlein on 03.01.2018.
 */

public class Lesson implements Serializable {

    //the subject of this lesson
    Subject subject;

    //the room in which this very lesson takes place
    String room;

    public Lesson (Subject _subject, String _room){
        subject = _subject;
        room = _room;
    }

}
