package de.tk.annapp;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SubjectManager {

    private static final SubjectManager subjectManager = new SubjectManager();

    Context context;

    String filename;

    TextView textViewGrade;

    //Contains all subjects
    ArrayList<Subject> subjects = new ArrayList<Subject>();
    Day[] days;

    private SubjectManager(){
        System.out.println("Create SubjectManager...");
        days = new Day[]{new Day(),new Day(),new Day(),new Day(),new Day()};
    }

    //Returns the singelton subjectManager
    public static SubjectManager getInstance(){
        return subjectManager;
    }

    public void setContext(Context c){
        this.context =c;
    }

    public void setFilename(String filename){
        this.filename = filename;
    }

    public ArrayList<Subject> getSubjects(){return subjects;}

    public void addSubject(String name, int rating, String teacher, String room){
        //Add a Subject to the subjects Arraylist
        System.out.println(subjects);
        for(Subject s : subjects){
            Subject newSubject = new Subject(name, rating, teacher, room);

            if(newSubject.name.equals(s.name)){
                System.out.println(getClass().getName() + ": Subject already exists. addSubject() failed.");
                return;
            }
        }

        subjects.add(new Subject(name, rating, teacher, room));
        save();
    }

    //Gives back the average of all subjects
    public float getWholeGradeAverage(){
        float wholeGradeAverage = 0;
        int emptySubjects = 0;

        //Goes through all subjects and get the GradePointaverage and adds it to the wholeAverageGrade
        for(Subject _subject : subjects){
            wholeGradeAverage += _subject.getGradePointAverage();
            System.out.println(wholeGradeAverage);
        }

        for(Subject _subject : subjects){
            if(_subject.grades.isEmpty()){
                emptySubjects += 1;
            }
        }

        wholeGradeAverage /= (subjects.size() - emptySubjects);
        System.out.println(wholeGradeAverage);
        System.out.println("size: " + subjects.size());
        return  Util.round(wholeGradeAverage, 2);
    }

    public void load()
    {
        try {
            ObjectInputStream ois = new ObjectInputStream(context.openFileInput(filename));
            subjects = (ArrayList<Subject>) ois.readObject();
            days = (Day[]) ois.readObject();
            ois.close();
            setGradeTextView(true, null);
        } catch (Exception e) {
            System.out.println("loading failed ---------------------------------------------------------------------------------------------------------");
            e.printStackTrace();
        }
    }

    public void save(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(context.openFileOutput(filename, Context.MODE_PRIVATE));
            oos.writeObject(subjects);
            oos.writeObject(days);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setGradeTextView(true, null);
    }

    public void setTextView(TextView tw){
        textViewGrade = tw;
    }

    public void setGradeTextView(boolean isVisible, Subject subject){
        if(isVisible) {
            textViewGrade.setVisibility(View.VISIBLE);
            if(subject == null) {
                if(Float.toString(subjectManager.getWholeGradeAverage()).equals("NaN")){
                    setGradeTextView(false, null);
                    return;
                }
                textViewGrade.setText(Float.toString(subjectManager.getWholeGradeAverage()));
            } else {

                if (Float.toString(subject.getGradePointAverage()).equals("NaN")){
                    setGradeTextView(false, null);
                    return;
                } else
                    textViewGrade.setText(Float.toString(subject.getGradePointAverage()));
            }

        }
        else
            textViewGrade.setVisibility(View.GONE);
    }

    public void setLesson(Subject subject, String room, int time /*Number of the lesson (1st lesson, 2nd lesson, ...)*/, int day){
        days[day].setLesson(subject, room, time);
        if(!subjects.contains(subject))
            Log.i("MANAGER: ","Holy shit, this should not happen!-------------------------");
        save();
    }

    public Day[] getDays(){
        return days;
    }

}
