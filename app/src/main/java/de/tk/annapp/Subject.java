package de.tk.annapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.stream.Collectors;

public class Subject implements Serializable {

    //Contains all grades of one Subject
    ArrayList<Grade> grades = new ArrayList<>();

    //Contains all tasks of one Subject
    ArrayList<Task> tasks = new ArrayList<>();

    int position;

    //name of the Subject
    public String name;

    //determines how much a written Grade is worth
    //int the Subject class
    //(1 or 2)
    int ratingSub;

    //name of the Teacher
    public String teacher;

    //Name of the room the subject normally takes place
    public String room;

    //Junge, junge, wer hat hier geschlampt?
    public Subject(String name, int rating, String teacher, String room) {
        this.name = name;
        ratingSub = rating;
        this.teacher = teacher;
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }


    public void setPosition(int position) {
        this.position = position;
    }

    //TODO WOOOO?
    public int getPosition() {
        return position;
    }

    public void sortTasks() {
        //TODO replaxesdhfhbabuhldhfusbuhadfhsb Remove this f*** method
    }

    public void addGrade(int _grade, boolean _iswritten, float _ratingGrade, String _note) {
        //Adding new Grade
        grades.add(new Grade(_grade, _iswritten, _ratingGrade, _note));
    }

    public void removeGrade(Grade _gradePosition) {
        grades.remove(grades.indexOf(_gradePosition));
    }

    public void editGrade(Grade _grade, int _grade_grade, boolean _iswritten, float _ratingGrade, String _note) {
        Grade grade = grades.get(grades.indexOf(_grade));
        grade.grade = _grade_grade;
        grade.iswritten = _iswritten;
        grade.rating = _ratingGrade;
        grade.note = _note;
    }

    //Returns the gradePointAverage
    public float getGradePointAverage() {
        float gradePointAverage;
        float writtenGradeAverage = 0f;
        float writtenGrades = 0f;
        float vocalGradeAverage = 0f;
        float vocalGrades = 0f;

        //loops through all 'grades' in grades
        for (Grade _grade : grades) {
            //Adds the Grade to the average
            if (_grade.iswritten) {
                writtenGradeAverage += _grade.grade * _grade.rating;
                writtenGrades += _grade.rating;
            } else if (!_grade.iswritten) {
                vocalGradeAverage += _grade.grade * _grade.rating;
                vocalGrades += _grade.rating;
            }
        }

        writtenGradeAverage /= writtenGrades;
        vocalGradeAverage /= vocalGrades;

        if (Float.isNaN(writtenGradeAverage)) {
            gradePointAverage = vocalGradeAverage;
        } else if (Float.isNaN(vocalGradeAverage)) {
            gradePointAverage = writtenGradeAverage;
        } else {
            gradePointAverage = (ratingSub * writtenGradeAverage + vocalGradeAverage) / (ratingSub + 1);
        }

        return Util.round(gradePointAverage, 2);
    }

    //Returns all Grades
    public ArrayList<Grade> getAllGrades() {
        return grades;
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    public ArrayList<Task> getAllTasksSorted() {
        System.out.println(tasks);
        Collections.sort(tasks);
        //tasks.stream().sorted().collect(Collectors.toList())
        return tasks;
    }

    @Override
    public String toString() {
        return name;
    }
}
