package de.tk.annapp.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;


import java.util.ArrayList;

import de.tk.annapp.R;
import de.tk.annapp.Recycler.RVAdapterSubjectList;
import de.tk.annapp.SubjectManager;
import de.tk.annapp.subject;

import static android.R.layout.simple_spinner_dropdown_item;

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

        //Get Singelton subjectManager
        subjectManager = SubjectManager.getInstance();

        FloatingActionButton fabAdd = (FloatingActionButton) root.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("bla");
                createInputDialog();
            }
        });

        recyclerView = root.findViewById(R.id.recyclerViewSubjectsId);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new RVAdapterSubjectList(getActivity(), subjects));

        //addTestGrades();

        subjectManager.addSubject("Mathe", 2);
        subjectManager.addSubject("Deutsch", 2);

        subjects.add(subjectManager.getSubjectByName("Mathe"));
        subjects.add(subjectManager.getSubjectByName("Deutsch"));

        return root;
    }

/*
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
    }*/

    public void createInputDialog(){

        AlertDialog.Builder ad = new  AlertDialog.Builder(this.getContext());



        //View mView = getLayoutInflater().inflate(R.layout.fragment_grade_input, null);
        View mView = View.inflate(this.getContext(), R.layout.fragment_grade_input, null);

        final EditText gradeInput = (EditText) mView.findViewById(R.id.gradeInput);
        final  EditText ratingInput =(EditText) mView.findViewById(R.id.ratingInput);
        final EditText note = (EditText) mView.findViewById(R.id.note);
        final ImageView btnHelp = (ImageView) mView.findViewById(R.id.btnHelp);

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlertDialog("Wertung", "Die Wertung ist der Betrag, der das Verhältnis zu den anderen Noten des selben Types bestimmt.\n\nz.B.:\nWertung: 2\nDie Note wird doppelt so stark einberechnet wie andere Noten.\n\nWertung: 0,5\nDie Note wird halb so stark einberechnet wie andere Noten.", 0);
            }
        });

        ArrayList<String> subjectNames = new ArrayList<>();

        for (subject s :
                subjects) {
            subjectNames.add(s.name);
        }

        final Spinner subjectSelection = (Spinner) mView.findViewById(R.id.subjectSelection);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), simple_spinner_dropdown_item, subjectNames);

        subjectSelection.setAdapter(adapter);

        final RadioButton isWritten = mView.findViewById(R.id.isWritten);

        final RadioButton isNotWritten = mView.findViewById(R.id.isNotWritten);




        ad      .setTitle("Note hinzufügen")
                .setView(mView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //testing which button is active for decision whether your grade is written or whether it's not
                        if(isWritten.isChecked())
                            isWrittenBool = true;
                        else if(isNotWritten.isChecked())
                            isWrittenBool = false;

                        if(gradeInput.getText().toString().isEmpty() || ratingInput.getText().toString().isEmpty()){
                            createAlertDialog("Achtung", "Bitte füllen Sie alle notwendigen Felder aus!", android.R.drawable.ic_dialog_alert);
                            return;
                        }


                        subject subject = subjectManager.getSubjectByName(subjectSelection.getSelectedItem().toString());
                        subject.addGrade(Integer.valueOf(gradeInput.getText().toString()), isWrittenBool, Integer.valueOf(ratingInput.getText().toString()), note.getText().toString());
                        recyclerView.setAdapter(new RVAdapterSubjectList(getActivity(), subjects));
                    }
                })
                .show();
    }

    void createAlertDialog(String title, String text, int ic){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this.getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this.getContext());
        }
        builder.setTitle(title)
                .setMessage(text)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            createInputDialog();
                        }
                    }
                })
                .setIcon(ic)
                .show();
    }
}
