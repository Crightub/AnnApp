package de.tk.annapp.Fragments;

import android.app.Fragment;
import android.app.TimePickerDialog;
import android.icu.text.TimeZoneFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Space;

import java.util.Calendar;
import java.util.GregorianCalendar;

import de.tk.annapp.Lesson;
import de.tk.annapp.R;
import de.tk.annapp.Subject;
import de.tk.annapp.SubjectManager;
import de.tk.annapp.Util;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Tobi on 20.09.2017.
 */

public class HomeFragment extends Fragment {

    View root;
    LinearLayout timeTable;
    LinearLayout tasks;
    SubjectManager subjectManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle(getString(R.string.Home));
        root = inflater.inflate(R.layout.fragment_home, container, false);

        subjectManager = SubjectManager.getInstance();

        timeTable = root.findViewById(R.id.timeTable);

        tasks = root.findViewById(R.id.tasks);

        setTimeTable();

        return root;


    }

    void setTimeTable() {
        GregorianCalendar gc = new GregorianCalendar();
        int i = gc.get(Calendar.DAY_OF_WEEK);
        int dayOfWeek = -1;
        switch (i) {
            case 2:
                dayOfWeek = 0;
                break;
            case 3:
                dayOfWeek = 1;
                break;
            case 4:
                dayOfWeek = 2;
                break;
            case 5:
                dayOfWeek = 3;
                break;
            case 6:
                dayOfWeek = 4;
                break;
            case 7:
                dayOfWeek = -1;
                break;
            case 8:
                dayOfWeek = -1;
                break;
        }

        if (dayOfWeek != -1) {
            for (Lesson l :
                    subjectManager.getDays()[dayOfWeek].getLessons()) {

                Button btn;

                System.out.println(subjectManager.getDays()[dayOfWeek].getLessons());

                if (l == null || l.getSubject() == null)
                    btn = getEmptyCellButton("");
                else {
                    btn = getCellButton(l.getSubject(), "");
                    btn.setText(l.getSubject().getName());
                }

                timeTable.addView(btn);
                Space space = new Space(getContext());
                space.setMinimumHeight(1);
                timeTable.addView(space);
            }
        }
    }


    Button getCellButton(Subject subject, String position) {
        Button btn = new Button(this.getContext());

        //general Settings for Cells
        btn.setTextColor(getResources().getColor(R.color.default_background_color));

        int color = 0;

        int index = subjectManager.getSubjects().indexOf(subject);
        for (int i; index > 14; index = index - 14) {
        }

        int colorSchemePosition;

        try {
            colorSchemePosition = (int) getActivity().getPreferences(MODE_PRIVATE).getInt("colorSchemePosition", 0);
        } catch (Exception e) {
            colorSchemePosition = 0; //Default value
        }

        if (colorSchemePosition == 0) {
            color = new Util().getAccentColor(this.getContext());
        } else if (colorSchemePosition == 1) {
            switch (index) {
                case 0:
                    color = getResources().getColor(R.color.cs1_0);
                    break;
                case 1:
                    color = getResources().getColor(R.color.cs1_1);
                    break;
                case 2:
                    color = getResources().getColor(R.color.cs1_2);
                    break;
                case 3:
                    color = getResources().getColor(R.color.cs1_3);
                    break;
                case 4:
                    color = getResources().getColor(R.color.cs1_4);
                    break;
                case 5:
                    color = getResources().getColor(R.color.cs1_5);
                    break;
                case 6:
                    color = getResources().getColor(R.color.cs1_6);
                    break;
                case 7:
                    color = getResources().getColor(R.color.cs1_7);
                    break;
                case 8:
                    color = getResources().getColor(R.color.cs1_8);
                    break;
                case 9:
                    color = getResources().getColor(R.color.cs1_9);
                    break;
                case 10:
                    color = getResources().getColor(R.color.cs1_10);
                    break;
                case 11:
                    color = getResources().getColor(R.color.cs1_11);
                    break;
                case 12:
                    color = getResources().getColor(R.color.cs1_12);
                    break;
                case 13:
                    color = getResources().getColor(R.color.cs1_13);
                    break;
                case 14:
                    color = getResources().getColor(R.color.cs1_14);
                    break;
            }
        } else if (colorSchemePosition == 2) {
            switch (index) {
                case 0:
                    color = getResources().getColor(R.color.cs2_0);
                    break;
                case 1:
                    color = getResources().getColor(R.color.cs2_1);
                    break;
                case 2:
                    color = getResources().getColor(R.color.cs2_2);
                    break;
                case 3:
                    color = getResources().getColor(R.color.cs2_3);
                    break;
                case 4:
                    color = getResources().getColor(R.color.cs2_4);
                    break;
                case 5:
                    color = getResources().getColor(R.color.cs2_5);
                    break;
                case 6:
                    color = getResources().getColor(R.color.cs2_6);
                    break;
                case 7:
                    color = getResources().getColor(R.color.cs2_7);
                    break;
                case 8:
                    color = getResources().getColor(R.color.cs2_8);
                    break;
                case 9:
                    color = getResources().getColor(R.color.cs2_9);
                    break;
                case 10:
                    color = getResources().getColor(R.color.cs2_10);
                    break;
                case 11:
                    color = getResources().getColor(R.color.cs2_11);
                    break;
                case 12:
                    color = getResources().getColor(R.color.cs2_12);
                    break;
                case 13:
                    color = getResources().getColor(R.color.cs2_13);
                    break;
                case 14:
                    color = getResources().getColor(R.color.cs2_14);
                    break;
            }
        } else if (colorSchemePosition == 3) {
            switch (index) {
                case 0:
                    color = getResources().getColor(R.color.cs3_0);
                    break;
                case 1:
                    color = getResources().getColor(R.color.cs3_1);
                    break;
                case 2:
                    color = getResources().getColor(R.color.cs3_2);
                    break;
                case 3:
                    color = getResources().getColor(R.color.cs3_3);
                    break;
                case 4:
                    color = getResources().getColor(R.color.cs3_4);
                    break;
                case 5:
                    color = getResources().getColor(R.color.cs3_5);
                    break;
                case 6:
                    color = getResources().getColor(R.color.cs3_6);
                    break;
                case 7:
                    color = getResources().getColor(R.color.cs3_7);
                    break;
                case 8:
                    color = getResources().getColor(R.color.cs3_8);
                    break;
                case 9:
                    color = getResources().getColor(R.color.cs3_9);
                    break;
                case 10:
                    color = getResources().getColor(R.color.cs3_10);
                    break;
                case 11:
                    color = getResources().getColor(R.color.cs3_11);
                    break;
                case 12:
                    color = getResources().getColor(R.color.cs3_12);
                    break;
                case 13:
                    color = getResources().getColor(R.color.cs3_13);
                    break;
                case 14:
                    color = getResources().getColor(R.color.cs3_14);
                    break;
            }
        }

        btn.setBackgroundColor(color);

        btn.setTag(position);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO go to TimeTable
            }
        });

        return btn;
    }

    Button getEmptyCellButton(String position) {
        Button btn = new Button(this.getContext());

        //general Settings for empty cells

        btn.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        btn.setTag(position);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO go to TimeTable
            }
        });
        return btn;
    }

}
