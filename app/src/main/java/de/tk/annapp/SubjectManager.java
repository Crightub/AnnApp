package de.tk.annapp;


import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SubjectManager {

    private static final SubjectManager subjectManager = new SubjectManager();

    Context context;

    String filename;

    private SchoolLessonSystem schoolLessonSystem;

    //Contains all subjects
    ArrayList<Subject> subjects = new ArrayList<Subject>();
    Day[] days;

    private SubjectManager(){
        System.out.println("Create SubjectManager...");
        days = new Day[]{new Day(0),new Day(1),new Day(2),new Day(3),new Day(4)};
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

    public void addSubject(Subject subject){
        if(subjects.contains(subject))
            return;
        subjects.add(subject);
        save();
    }

    public void removeSubject(Subject subject){
        for(Lesson lesson: subject.getLessons())
            subjectManager.setLesson(new Lesson(null,null, lesson.getDay(), lesson.getTime()));
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
    }


    public void setLesson(Lesson lesson){
        int day =lesson.getDay();
        int time = lesson.getTime();
        if(lesson.getSubject()==null){
            days[day].getLesseon(time).getSubject().removeLesson(days[day].getLesseon(time));
        }else if(days[day].getLesseon(time).getSubject()==null){
            lesson.getSubject().addLesson(lesson);
        }else if(!days[day].getLesseon(time).getSubject().equals(lesson.getSubject())){
            days[day].getLesseon(time).getSubject().removeLesson(days[day].getLesseon(time));
            lesson.getSubject().addLesson(lesson);
        }
        System.out.println("InSubMan");
        days[day].setLesson(lesson);
        save();
    }

    public Day[] getDays(){
        return days;
    }

    public SchoolLessonSystem getSchoolLessonSystem() {
        return schoolLessonSystem;
    }

    public void setSchoolLessonSystem(SchoolLessonSystem schoolLessonSystem) {
        this.schoolLessonSystem = schoolLessonSystem;
    }

    public News getNews(int position){
        return new News("Titel "+position, "Beschreibung",context.getDrawable(R.drawable.ic_fehler_bild));
    }
    public int getNewsCount(){
        return 5;
    }
}
