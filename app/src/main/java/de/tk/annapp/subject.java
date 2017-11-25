package de.tk.annapp;

import java.util.ArrayList;

public class subject {

    //Contains all grades of one subject
    ArrayList<grade> grades = new ArrayList<>();


    //name of the subject
    public String name;

    //determines how much a written grade is worth
    //int the subject class
    //(1 or 2)
    int ratingSub;

    public subject(String _name, int _rating){
        name = _name;
        ratingSub = _rating;
    }

    public void addGrade(int _grade, boolean _iswritten, float _ratingGrade, String _note){
        //Adding new Grade
        grades.add(new grade(_grade, _iswritten, _ratingGrade, _note));
    }

    //Returns the gradePointAverage
    public float getGradePointAverage(){
        float gradePointAverage;
        float writtenGradeAverage = 0f;
        float writtenGrades = 0f;
        float vocalGradeAverage = 0f;
        float vocalGrades = 0f;

        //loops through all 'grades' in grades
        for(grade _grade : grades){
            //Adds the grade to the average
            if(_grade.iswritten){
                writtenGradeAverage += _grade.grade * _grade.rating;
                writtenGrades += _grade.rating;
            }else if(!_grade.iswritten){
                vocalGradeAverage += _grade.grade * _grade.rating;
                vocalGrades += _grade.rating;
            }
        }

        writtenGradeAverage /= writtenGrades;
        vocalGradeAverage /= vocalGrades;

        if(Float.isNaN(writtenGradeAverage)){
            gradePointAverage = vocalGradeAverage;
        } else if(Float.isNaN(vocalGradeAverage)){
            gradePointAverage = writtenGradeAverage;
        } else {
            gradePointAverage = (ratingSub * writtenGradeAverage + vocalGradeAverage)/(ratingSub + 1);
        }

        return round(gradePointAverage, 2);
    }

    //fastest way to round a float to a certain scale
    public static float round(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        float tmp = number * pow;
        return ( (float) ( (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) ) ) / pow;
    }

    //Returns all Grades
    public ArrayList<grade> getAllGrades(){
        return grades;
    }
}
