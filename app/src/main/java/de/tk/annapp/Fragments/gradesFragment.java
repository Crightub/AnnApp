package de.tk.annapp.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import de.tk.annapp.R;
import de.tk.annapp.Recycler.RVAdapterSubjectList;
import de.tk.annapp.SubjectManager;
import de.tk.annapp.subject;

public class gradesFragment extends Fragment {
    View root;

    private SubjectManager subjectManager = new SubjectManager();

    RecyclerView recyclerView;

    private ArrayList<subject> subjects = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Noten");
        root = inflater.inflate(R.layout.fragment_grades, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewSubjectsId);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new RVAdapterSubjectList(getActivity(), subjects));

        addTestGrades();

        subjects.add(subjectManager.getSubjectByName("Mathe"));
        subjects.add(subjectManager.getSubjectByName("Deutsch"));

        return root;
    }


    private void addTestGrades(){
        subjectManager.addSubject("Mathe", 2);
        subjectManager.addSubject("Deutsch", 2);

        subjectManager.getSubjectByName("Deutsch").addGrade(3, true, 1);

        subject mathe = subjectManager.getSubjectByName("Mathe");
        System.out.println(mathe.name);

        mathe.addGrade(4, true, 2);
        mathe.addGrade(3, true, 1);

        System.out.println(mathe.getGradePointAverage());

        mathe.addGrade(1, false , 3);
        mathe.addGrade(2, false, 1);

        System.out.println(mathe.getGradePointAverage());

        System.out.println("" + subjectManager.getWholeGradeAverage());
    }

}
