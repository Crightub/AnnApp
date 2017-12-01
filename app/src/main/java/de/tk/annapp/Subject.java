package de.tk.annapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Subject implements Serializable {

    //Contains all grades of one Subject
    ArrayList<Grade> grades = new ArrayList<>();


    //name of the Subject
    public String name;

    //determines how much a written Grade is worth
    //int the Subject class
    //(1 or 2)
    int ratingSub;

    public Subject(String _name, int _rating){
        name = _name;
        ratingSub = _rating;
    }

    public void addGrade(int _grade, boolean _iswritten, float _ratingGrade, String _note){
        //Adding new Grade
        grades.add(new Grade(_grade, _iswritten, _ratingGrade, _note));
    }

    public void removeGrade(Grade _gradePosition){
        grades.remove(grades.indexOf(_gradePosition));
    }

    public void editGrade(Grade _grade, int _grade_grade, boolean _iswritten, float _ratingGrade, String _note){
        Grade grade = grades.get(grades.indexOf(_grade));
        grade.grade = _grade_grade;
        grade.iswritten = _iswritten;
        grade.rating = _ratingGrade;
        grade.note = _note;
    }

    //Returns the gradePointAverage
    public float getGradePointAverage(){
        float gradePointAverage;
        float writtenGradeAverage = 0f;
        float writtenGrades = 0f;
        float vocalGradeAverage = 0f;
        float vocalGrades = 0f;

        //loops through all 'grades' in grades
        for(Grade _grade : grades){
            //Adds the Grade to the average
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
    public ArrayList<Grade> getAllGrades(){
        return grades;
    }
}
