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
import android.widget.TextView;

public class GradeChildFragment extends Fragment {
    View root;

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        Subject subject = (Subject) args.get("subject");
        getActivity().setTitle(subject.getName());
        if(!subject.getAllGrades().isEmpty()){
            getActivity().findViewById(R.id.grade).setVisibility(View.VISIBLE);
            ((TextView)getActivity().findViewById(R.id.grade)).setText(String.valueOf(subject.getGradePointAverage()));
        }
        root = inflater.inflate(R.layout.fragment_gradeschild, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewGradesId);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new RVAdapterGradeList(getActivity(), subject));

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().findViewById(R.id.grade).setVisibility(View.GONE);
    }
}

