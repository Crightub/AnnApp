package de.tk.annapp;


import java.util.ArrayList;

public class SubjectManager {

    //Contains all subjects
    ArrayList<subject> subjects = new ArrayList<>();

    public void addSubject(String _name, int _rating){
        //Add a Subject to the subjects Arraylist
        subjects.add(new subject(_name, _rating));
    }

    //Goes through all subjects and gives the one with the same name back
    public subject getSubjectByName(String _name){
        for(subject _subject : subjects){
            if(_subject.name.equals(_name)){
                return _subject;
            }
        }
        //NO subject with this name found
        return null;
    }

    //Gives back the average of all subjects
    public float getWholeGradeAverage(){
        float wholeGradeAverage = 0;

        //Goes through all subjects and get the GradePointaverage and adds it to the wholeAverageGrade
        for(subject _subject : subjects){
            wholeGradeAverage += _subject.getGradePointAverage();
        }

        wholeGradeAverage /= subjects.size();

        return wholeGradeAverage;
    }
}
