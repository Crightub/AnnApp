package de.tk.annapp.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;


import java.util.ArrayList;

import javax.security.auth.Subject;

import de.tk.annapp.R;
import de.tk.annapp.Recycler.RVAdapterSubjectList;
import de.tk.annapp.SubjectManager;
import de.tk.annapp.subject;

public class gradesFragment extends Fragment {
    View root;

    boolean isWrittenBool;

    private SubjectManager subjectManager;

    RecyclerView recyclerView;

    private ArrayList<subject> subjects = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Noten");
        root = inflater.inflate(R.layout.fragment_grades, container, false);

        //Get Singelton
        subjectManager = SubjectManager.getInstance();

        FloatingActionButton fabAdd = (FloatingActionButton) root.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("bla");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    createInputDialog();
                }
            }
        });

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

        subjectManager.getSubjectByName("Deutsch").addGrade(3, true, 1, "grrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");

        subject mathe = subjectManager.getSubjectByName("Mathe");
        System.out.println(mathe.name);

        mathe.addGrade(4, true, 2, "KLAUSUR");
        mathe.addGrade(3, true, 1, "weffffffffffffffffffffffffffffffffffffffffff");

        System.out.println(mathe.getGradePointAverage());

        mathe.addGrade(1, false , 3, "wfeqqqqqtttttttttttttttttttttttt");
        mathe.addGrade(2, false, 1, "gggggggggggggggggggggggggggggggggggggggggggggggggggggggg");

        System.out.println(mathe.getGradePointAverage());

        System.out.println("" + subjectManager.getWholeGradeAverage());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createInputDialog(){

        AlertDialog.Builder ad = new  AlertDialog.Builder(this.getContext());

        View mView = getLayoutInflater().inflate(R.layout.fragment_grade_input, null);

        final EditText gradeInput = (EditText) mView.findViewById(R.id.gradeInput);
        final  EditText ratingInput =(EditText) mView.findViewById(R.id.ratingInput);
        final EditText note = (EditText) mView.findViewById(R.id.note);

        final Spinner subjectSelection = (Spinner) mView.findViewById(R.id.subjectSelection);

        String[] subjectNames;


        final RadioButton isWritten = (RadioButton) mView.findViewById(R.id.isWritten);

        final RadioButton isNotWritten = (RadioButton) mView.findViewById(R.id.isNotWritten);




        ad      .setTitle("Note hinzufügen")
                .setView(mView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //testing which button is active for decicion whether your grade is written or wheter it's not
                        if(isWritten.isChecked())
                            isWrittenBool = true;
                        else if(isNotWritten.isChecked())
                            isWrittenBool = false;


                        subject subject = subjectManager.getSubjectByName( "Mathe" /*later ... if spinner is working : subjectSelection.getSelectedItem().toString()*/);
                        subject.addGrade(Integer.valueOf(gradeInput.getText().toString()), isWrittenBool, Integer.valueOf(ratingInput.getText().toString()), note.getText().toString());

                    }
                })
                .show();
    }

}
