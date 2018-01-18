
package de.tk.annapp.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import de.tk.annapp.Lesson;
import de.tk.annapp.R;
import de.tk.annapp.Subject;
import de.tk.annapp.SubjectManager;
import de.tk.annapp.TimetableManager;

/**
 * Created by Tobi on 20.09.2017.
 */

public class loststuffFragment extends Fragment {
    View root;
    EditText lessonSubject;
    Button btn;
    SubjectManager subjectManager;

    TimetableManager timetableManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        subjectManager = SubjectManager.getInstance();
        subjectManager.load(getContext(), "subjects");

        timetableManager = TimetableManager.getInstance();
        timetableManager.load(this.getContext(), "timetable");

        getActivity().setTitle(getString(R.string.lostStuff));
        root = inflater.inflate(R.layout.fragment_loststuff, container, false);

        lessonSubject = (EditText) root.findViewById(R.id.lessonSubject);
        btn = (Button) root.findViewById(R.id.btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOnClick();
            }
        });



        return root;
    }

    void btnOnClick(){

        String txt = lessonSubject.getText().toString();
        Subject s = subjectManager.getSubjectByName(txt);

        timetableManager.setLesson(s, null, 3, 0);

        for (Lesson l :
                timetableManager.getDays().get(0).lessons) {
            try {
                System.out.println(l.subject.name);
            } catch(Exception e){
                System.out.println("Error in lessons");
            }
        }

        timetableManager.save(this.getContext(), "timetable");


    }
}

