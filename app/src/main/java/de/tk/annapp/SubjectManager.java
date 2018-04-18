package de.tk.annapp;


import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

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
        Set s = new HashSet<Integer>();
        s.add(1);
        s.add(3);
        schoolLessonSystem = new SchoolLessonSystem(480, 45, 20, s);//TODO From Preferences
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

    public void load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(context.openFileInput(filename));
            subjects = (ArrayList<Subject>) ois.readObject();
            days = (Day[]) ois.readObject();
            //TODO Experimental
            sortSubjects();
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
            //TODO Experimental
            sortSubjects();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setLesson(Lesson lesson){
        int day = lesson.getDay();
        System.out.println(lesson.getDay());
        int time = lesson.getTime()-1;
        System.out.println(lesson.getTime());
        System.out.println("Lesson: " + lesson.toString());
        if(lesson.getSubject() == null){
            days[day].getLesson(time).getSubject().removeLesson(days[day].getLesson(time));
        }else if(days[day].getLesson(time) == null) {

        }else if(days[day].getLesson(time).getSubject() == null){
            lesson.getSubject().addLesson(lesson);
        }else if(!days[day].getLesson(time).getSubject().equals(lesson.getSubject())){
            days[day].getLesson(time).getSubject().removeLesson(days[day].getLesson(time));
            lesson.getSubject().addLesson(lesson);
        }
        System.out.println("InSubMan");
        days[day].setLesson(lesson);
        save();
    }

    public void deleteLesson(Lesson lesson){
        for (Day d :
                days) {
            for (Lesson l :
                    d.getLessons()) {
                if(l==lesson) {
                    d.getLessons().set(lesson.getTime(), null);
                    return;
                }
            }
        }
    }

    public void deleteAllLessons(Lesson lesson){
        for (Day d :
                days) {
            for (Lesson l :
                    d.getLessons()) {
                if(l == null)
                    continue;
                if(l.getSubject()==lesson.getSubject())
                    d.getLessons().set(l.getTime(), null);
            }
        }
    }

    public void deleteSubject(Subject subject){
        subjects.remove(subject);
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

    public void sortSubjects(){
        Collections.sort(subjects,
                (o1, o2) -> o1.getName().compareTo(o2.getName()));
    }


}
class CustomComparator implements Comparator<Subject> {
    @Override
    public int compare(Subject o1, Subject o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
