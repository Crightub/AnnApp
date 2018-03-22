package de.tk.annapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Subject implements Serializable {

    //Contains all grades of one Subject
    ArrayList<Grade> grades = new ArrayList<>();

    //Contains all tasks of one Subject
    ArrayList<Task> tasks = new ArrayList<>();

    ArrayList<Task> tasksSorted = new ArrayList<>();

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

    public void addTask(String task, Date date, String kind, String day, boolean cal) {
        tasks.add(new Task(task, date, kind, this.getName(), day, cal));
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    //TODO Relocate this method & Rewrite
    public void editTask(Task task, String taskContent, String day, Date date, boolean cal) {
        task.task = taskContent;
        task.weekday = cal;
        if (!cal) {
            task.date = day;
            task.dateDiff();
        } else {
            task.dateNumber = date.getTime();
            task.date = date.getDate() + "." + (date.getMonth() + 1) + ".";
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void sortTasks() {
        ArrayList<Task> tasksLeft = new ArrayList<>();
        Task lowest = new Task(null, null, null, null, null, false);
        tasksSorted.clear();
        for (Task t : tasks) {
            tasksLeft.add(t);
        }
        for (int x = 0; x < tasks.size(); x++) {
            int i = 0;
            for (Task left : tasksLeft) {
                if (i == 0) {
                    lowest = left;
                    i++;
                }
                if (left.dateNumber < lowest.dateNumber) {
                    lowest = left;
                }
            }
            tasksSorted.add(lowest);
            tasksLeft.remove(lowest);
        }
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
        sortTasks();
        return tasksSorted;
    }
}
