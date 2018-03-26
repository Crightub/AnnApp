package de.tk.annapp;

import java.io.Serializable;

/**
 * Created by Tobias Kiehnlein on 03.01.2018.
 */

public class Lesson implements Serializable {

    //the subject of this lesson
    private Subject subject;
    private int day;
    private int time;

    //the room in which this very lesson takes place
    private String room;

    public Lesson (Subject subject, String room, int day, int time){
        this.subject = subject;
        this.room = room;

        this.day = day;
        this.time = time;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getRoom() {
        if(room==null)
            return subject.getRoom();
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
