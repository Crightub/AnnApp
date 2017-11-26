package de.tk.annapp;


import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SubjectManager {

    private static final SubjectManager subjectManager = new SubjectManager();

    private SubjectManager(){
        System.out.println("Create SubjetManager...");
    }

    //Returns the singelton subjectManager
    public static SubjectManager getInstance(){
        return subjectManager;
    }

    //Contains all subjects
    ArrayList<Subject> subjects = new ArrayList<>();

    public void addSubject(String _name, int _rating){
        //Add a Subject to the subjects Arraylist
        subjects.add(new Subject(_name, _rating));
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

        //Goes through all subjects and get the GradePointaverage and adds it to the wholeAverageGrade
        for(Subject _subject : subjects){
            wholeGradeAverage += _subject.getGradePointAverage();
        }

        wholeGradeAverage /= subjects.size();

        return wholeGradeAverage;
    }

    public void load(Context c, String filename)
    {
        try {
            ObjectInputStream ois = new ObjectInputStream(c.openFileInput(filename));
            subjects = (ArrayList<Subject>) ois.readObject();
            ois.close();
        } catch (Exception e) {
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
    }
}
