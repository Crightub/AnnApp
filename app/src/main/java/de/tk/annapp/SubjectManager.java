package de.tk.annapp;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SubjectManager {

    private static final SubjectManager subjectManager = new SubjectManager();
    public float overallGradePointAverage;

    Context c;

    TextView textViewGrade;

    //Contains all subjects
    ArrayList<Subject> subjects = new ArrayList<>();

    private SubjectManager(){
        System.out.println("Create SubjectManager...");
    }

    //Returns the singelton subjectManager
    public static SubjectManager getInstance(){
        return subjectManager;
    }

    public ArrayList<Subject> getSubjects(){return subjects;}

    public void addSubject(String _name, int _rating, String _teacher, String _room){
        //Add a Subject to the subjects Arraylist
        subjects.add(new Subject(_name, _rating, _teacher, _room));
        save(c, "subjects");
    }

    public void setContext(Context c){
        this.c =c;
    }

    public Context getContext(){
        return c;
    }

    //Goes through all subjects and gives the one with the same name back
    public Subject getSubjectByName(String _name){
        for(Subject _subject : subjects){
            if(_subject.name.equals(_name)){
                return _subject;
            }
        }
        //NO Subject with this name found
        return null;
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
        return wholeGradeAverage;
    }

    public void load(Context c, String filename)
    {
        try {
            ObjectInputStream ois = new ObjectInputStream(c.openFileInput(filename));
            subjects = (ArrayList<Subject>) ois.readObject();
            ois.close();
            setGradeTextView(true, null);
            System.out.println("loading done ---------------------------------------------------------------------------------------------------------");
        } catch (Exception e) {
            System.out.println("loading failed ---------------------------------------------------------------------------------------------------------");
            e.printStackTrace();
        }
    }

    public void save(Context c, String filename){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(c.openFileOutput(filename, Context.MODE_PRIVATE));
            oos.writeObject(subjects);
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
}
