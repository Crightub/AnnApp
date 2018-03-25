package de.tk.annapp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.app.Fragment;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import de.tk.annapp.Day;
import de.tk.annapp.Lesson;
import de.tk.annapp.R;
import de.tk.annapp.Subject;
import de.tk.annapp.SubjectManager;
import de.tk.annapp.TableView.model.Cell;
import de.tk.annapp.TableView.model.ColumnHeader;
import de.tk.annapp.TableView.model.RowHeader;
import de.tk.annapp.Util;

import static android.R.layout.simple_spinner_dropdown_item;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimetableFragment extends Fragment {

    //public static final int COLUMN_SIZE = 5;
    //public static final int ROW_SIZE = 11;

    private List<RowHeader> mRowHeaderList;
    private List<ColumnHeader> mColumnHeaderList;
    private List<List<Cell>> mCellList;

    //default spacing
    int spacing = 5;

    Subject lastSubject;
    Subject uglyAsHellWayToCreateAOtherCoiseOption = new Subject("neues Fach", 0, null, null);

    //private AbstractTableAdapter mTableViewAdapter;
    //private TableView mTableView;

    TableLayout tableLayout;

    SubjectManager subjectManager;

    public TimetableFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRowSize();
        //initData();
    }

    void setRowSize() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        getActivity().setTitle("Stundenplan");
        View root = inflater.inflate(R.layout.fragment_timetable, container, false);
        subjectManager = SubjectManager.getInstance();

        if (!subjectManager.getSubjects().isEmpty())
            lastSubject = subjectManager.getSubjects().get(0);
        else
            lastSubject = uglyAsHellWayToCreateAOtherCoiseOption;
        //RelativeLayout fragment_container = root.findViewById(R.id.fragment_container);

        tableLayout = root.findViewById(R.id.tableLayout);

        initializeTableView();

        // Create Table view
        //mTableView = createTableView();
        //fragment_container.addView(mTableView);

        //loadData();
        return root;
    }


    void initializeTableView() {
        /*timetableManager.setLesson(subjectManager.getSubjectByName("Mathe"), "E 201", 11, 4);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 203",1,0);
        timetableManager.setLesson(subjectManager.getSubjectByName("Mathe"), "E 203",2,0);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 203",3,0);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 203",4,0);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 203",5,0);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 203",6,0);

        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 213",1,1);
        timetableManager.setLesson(subjectManager.getSubjectByName("Mathe"), "E 213",2,1);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 213",3,1);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 213",4,1);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 213",5,1);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 213",6,1);

        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 223",1,2);
        timetableManager.setLesson(subjectManager.getSubjectByName("Mathe"), "E 223",2,2);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 223",3,2);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 223",4,2);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 223",5,2);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 223",6,2);

        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 233",1,3);
        timetableManager.setLesson(subjectManager.getSubjectByName("Mathe"), "E 233",2,3);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 233",3,3);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 233",4,3);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 233",5,3);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 233",6,3);

        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 243",1,4);
        timetableManager.setLesson(subjectManager.getSubjectByName("Mathe"), "E 243",2,4);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 243",3,4);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 243",4,4);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 243",5,4);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 243",6,4);*/

        tableLayout.removeAllViews();

        int accentColor = (new Util()).getAccentColor(getContext());

        for (int i = 0; i < 12/*TODO Later you should be able to change this in the settings*/; i++) {
            TableRow tableRow = new TableRow(this.getContext());


            if (i == 0) {
                Button b = getHeaderButton(accentColor);
                tableRow.addView(b);
                tableRow.addView(getDefaultSpace());

                //TODO Register Rowheaders
                int f = 0;
                for (int d = 0; d < 5; d++) {
                    Button btn = getHeaderButton(accentColor);

                    switch (f) {
                        case (0):
                            btn.setText("Montag");
                            break;
                        case (1):
                            btn.setText("Dienstag");
                            break;
                        case (2):
                            btn.setText("Mittwoch");
                            break;
                        case (3):
                            btn.setText("Donnerstag");
                            break;
                        case (4):
                            btn.setText("Freitag");
                            break;
                        default:
                            btn.setText("Unnamed Day");
                            break;
                    }
                    f++;


                    tableRow.addView(btn);

                    //horizontal space between buttons
                    tableRow.addView(getDefaultSpace());

                }
            } else {
                //TODO Register Columnheaders and Cells

                //vertical space between buttons
                TableRow t = new TableRow(this.getContext());
                t.addView(getDefaultSpace());
                tableLayout.addView(t);

                //add row header
                Button btn = getHeaderButton(accentColor);
                btn.setText(i + ". Stunde");
                tableRow.addView(btn);

                int x = 0;

                for (Day d :
                        subjectManager.getDays()) {

                    x++;
                    String cellName;

                    View cell;


                    try {
                        cellName = d.getLesseon(i).getSubject().getName();
                        //add cell
                        cell = getCellButton(accentColor, String.valueOf(x) + "#" + String.valueOf(i));
                        ((Button) cell).setText(cellName);
                    } catch (Exception e) {
                        cell = getEmptyCellButton(String.valueOf(x) + "#" + String.valueOf(i));
                    }

                    //add spacing
                    tableRow.addView(getDefaultSpace());

                    tableRow.addView(cell);
                }

            }


            tableLayout.addView(tableRow);
        }


    }

    Space getDefaultSpace() {
        Space s = new Space(this.getContext());
        s.setMinimumWidth(spacing);
        s.setMinimumHeight(spacing);
        return s;
    }

    Button getHeaderButton(int accentColor) {
        Button btn = new Button(this.getContext());

        //general Settings for headers
        btn.setTextColor(getResources().getColor(R.color.bg_line));
        btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btn.setTypeface(null, Typeface.BOLD);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Clicked ", Toast.LENGTH_SHORT).show();
            }
        });

        return btn;
    }

    Button getCellButton(int accentColor, String position) {
        Button btn = new Button(this.getContext());

        //general Settings for Cells
        btn.setTextColor(getResources().getColor(R.color.bg_line));

        btn.setBackgroundColor(accentColor);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Clicked ", Toast.LENGTH_SHORT).show();
            }
        });

        return btn;
    }

    Button getEmptyCellButton(String position) {
        Button btn = new Button(this.getContext());

        //general Settings for empty cells
        btn.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        btn.setTag(position);

        btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Toast.makeText(getContext(), "Empty Cell pressed on Position" + view.getTag(), Toast.LENGTH_SHORT).show();
                addLesson(view);
                return false;
            }
        });

        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO create new lesson
                Toast.makeText(getContext(), "Empty Cell pressed on Position" + view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });*/


        return btn;
    }

    void addLesson(View view) {
        //get time out of tag
        String[] s = view.getTag().toString().split("#");
        int x = Integer.valueOf(s[0]);
        int y = Integer.valueOf(s[1]);

        //subjectManager.setLesson(subjectManager.getSubjects().get(1), "", y, x-1);

        createInputDialog(x - 1, y, null);

    }

    public void createInputDialog(final int day, final int time, final Subject subject) {
        //AlertDialog.Builder ad = new  AlertDialog.Builder(this.getContext());
        final BottomSheetDialog bsd = new BottomSheetDialog(getContext(), R.style.NewDialog);


        View mView = View.inflate(this.getContext(), R.layout.fragment_lesson_input, null);
        //mView.setBackgroundColor(getResources().getColor(android.R.color.transparent));


        final FloatingActionButton btnOK = (FloatingActionButton) mView.findViewById(R.id.btnOK);
        final FloatingActionButton btnClear = (FloatingActionButton) mView.findViewById(R.id.btnClearLesson);
        final FloatingActionButton btnDelete = (FloatingActionButton) mView.findViewById(R.id.btnDeleteSubject);
        final EditText roomInput = (EditText) mView.findViewById(R.id.roomInput);
        final ImageView btnHelp = (ImageView) mView.findViewById(R.id.btnHelp);
        final Button btnExtra = (Button) mView.findViewById(R.id.btnExtra);
        final LinearLayout extraLayout = (LinearLayout) mView.findViewById(R.id.extraLayout);
        final RadioButton radioBtn1 = mView.findViewById(R.id.rating_1);
        final RadioButton radioBtn2 = mView.findViewById(R.id.rating_2);
        final EditText teacherEdittext = mView.findViewById(R.id.teacherInput);
        final EditText nameEdittext = mView.findViewById(R.id.subjectNameInput);

        if (subject == null) {
            btnDelete.setVisibility(View.GONE);
        }

        btnExtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (extraLayout.getVisibility() != View.VISIBLE) {
                    ((Button) view).setText("Reduzieren");
                    extraLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "These changes will affect all lessons of this subject!", Toast.LENGTH_LONG).show();
                } else {
                    ((Button) view).setText("Erweitern");
                    extraLayout.setVisibility(View.GONE);
                }
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlertDialog("Raum", "Raum muss nur eingetragen werden falls er sich von dem für das Fach als Standard eingestellten Raum unterscheiden sollte.", 0);
            }
        });


        final Spinner subjectSelection = (Spinner) mView.findViewById(R.id.subjectSelection);
        ArrayList<Subject> spinnerlist = (ArrayList<Subject>) subjectManager.getSubjects().clone();
        spinnerlist.add(uglyAsHellWayToCreateAOtherCoiseOption);


        ArrayAdapter<Subject> adapter = new ArrayAdapter<>(this.getContext(), simple_spinner_dropdown_item, spinnerlist);
        subjectSelection.setAdapter(adapter);
        subjectSelection.setSelection(spinnerlist.indexOf(lastSubject));
        subjectSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos == subjectSelection.getAdapter().getCount() - 1) {
                    if (extraLayout.getVisibility() == View.GONE) {
                        btnExtra.callOnClick();
                    }
                    nameEdittext.setText("");
                    radioBtn1.setChecked(true);
                    teacherEdittext.setText("");
                    roomInput.setText("");
                    btnExtra.setVisibility(View.GONE);

                } else {
                    Subject selectedSubject = (Subject) subjectSelection.getSelectedItem();
                    nameEdittext.setText(selectedSubject.getName());
                    if (selectedSubject.getRatingSub() == 1)
                        radioBtn1.setChecked(true);
                    else
                        radioBtn2.setChecked(true);
                    teacherEdittext.setText(selectedSubject.getTeacher());
                    roomInput.setText(selectedSubject.getRoom());
                    btnExtra.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        if (subject != null)
            subjectSelection.setSelection(spinnerlist.indexOf(subject));

        bsd.setTitle("Stunde hinzufügen");
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Subject oCSubject;
                Subject selectedsubject = (Subject) subjectSelection.getSelectedItem();

                if (subjectSelection.getSelectedItemPosition() == subjectSelection.getAdapter().getCount()-1) { //Neues Subject
                    if (nameEdittext.getText().toString().equals("")) {
                        createAlertDialog("Fehler im Namen", "Der Name darf nicht leer sein", 0);
                        return;
                    }
                    oCSubject = new Subject(nameEdittext.getText().toString(),
                            radioBtn1.isChecked() ? 1 : 2,
                            teacherEdittext.getText().toString(),
                            roomInput.getText().toString());
                    if (subjectManager.getSubjects().contains(oCSubject)) {
                        createAlertDialog("Fehler im Namen", "Es existiert bereits ein Fach mit dem Namen \"" + oCSubject.getName() + "\"", 0);
                        return;
                    }
                    subjectManager.addSubject(oCSubject);
                } else if (nameEdittext.getText().toString().equals(selectedsubject.getName()) &
                        teacherEdittext.getText().toString().equals(selectedsubject.getTeacher()) &
                        (radioBtn1.isChecked() ? 1 : 2) == selectedsubject.getRatingSub()) {
                    oCSubject = selectedsubject;
                } else {
                    if (nameEdittext.getText().toString().equals("")) {
                        createAlertDialog("Fehler im Namen", "Der Name darf nicht leer sein", 0);
                        return;
                    }
                    selectedsubject.setName(nameEdittext.getText().toString());
                    selectedsubject.setPosition(radioBtn1.isChecked() ? 1 : 2);
                    selectedsubject.setTeacher(teacherEdittext.getText().toString());
                    selectedsubject.setRoom(roomInput.getText().toString());
                    oCSubject = selectedsubject;
                }
                System.out.println(oCSubject+"\\"+ (roomInput.getText().toString().isEmpty() ? null : roomInput.getText().toString())+"\\"+day+"\\"+ time);
                subjectManager.setLesson(new Lesson(oCSubject, roomInput.getText().toString().isEmpty() ? null : roomInput.getText().toString(), day, time));

                initializeTableView();

                bsd.cancel();
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subjectManager.setLesson(new Lesson(null,null, day, time));
                initializeTableView();
                bsd.cancel();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((subjectSelection.getSelectedItemPosition() == subjectSelection.getAdapter().getCount()-1))
                    return;
                //TODO Warning dialoge
                subjectManager.removeSubject(((Subject) subjectSelection.getSelectedItem()));
            }
        });
        bsd.setContentView(mView);
        bsd.show();
    }
    void createAlertDialog(String title, String text, int ic) {
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
                    }
                })
                .setIcon(ic)
                .show();
    }
}

