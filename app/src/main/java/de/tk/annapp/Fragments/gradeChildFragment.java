package de.tk.annapp.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.tk.annapp.R;
import de.tk.annapp.Recycler.RVAdapterGradeList;
import de.tk.annapp.Subject;
import de.tk.annapp.SubjectManager;
import de.tk.annapp.Grade;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class gradeChildFragment extends Fragment {
    View root;

    RecyclerView recyclerView;

    private SubjectManager subjectManager;
    private Subject subject;
    private ArrayList<Grade> grades = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        String subjectName = args.getString("subjectName");
        getActivity().setTitle(subjectName);
        root = inflater.inflate(R.layout.fragment_gradeschild, container, false);

        subjectManager = SubjectManager.getInstance();

        subject = subjectManager.getSubjectByName(subjectName);

        grades = this.subject.getAllGrades();

        recyclerView = root.findViewById(R.id.recyclerViewGradesId);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new RVAdapterGradeList(getActivity(), grades));

        return root;
    }

}

