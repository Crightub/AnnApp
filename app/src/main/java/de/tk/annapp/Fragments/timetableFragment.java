package de.tk.annapp.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.tk.annapp.R;
import de.tk.annapp.SubjectManager;

/**
 * Created by Tobi on 20.09.2017.
 */

public class timetableFragment extends Fragment  {
    View root;
    SubjectManager subjectManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Get Singelton subjectManager
        subjectManager = SubjectManager.getInstance();

        getActivity().setTitle("Stundenplan");
        root = inflater.inflate(R.layout.fragment_timetable, container, false);

        Button btnAddSubject = (Button) root.findViewById(R.id.btnAddSubject);
        final EditText subjectName = (EditText) root.findViewById(R.id.subjectName);
        final EditText subjectRating = (EditText) root.findViewById(R.id.subjectRating);

        btnAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(subjectName.getText().toString().isEmpty() || subjectRating.getText().toString().isEmpty()){
                    Toast.makeText(view.getContext(), "Alle Felder ausfüllen!", Toast.LENGTH_LONG).show();
                    return;
                }


                subjectManager.addSubject(subjectName.getText().toString(), Integer.parseInt(subjectRating.getText().toString()), null, null);
                subjectName.setText("");
                subjectRating.setText("");
                subjectManager.save(getContext(), "subjects");
                Toast.makeText(getContext(), "Fach erfolgreich hinzugefügt!", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}
