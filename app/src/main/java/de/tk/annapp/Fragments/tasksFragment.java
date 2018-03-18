package de.tk.annapp.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.tk.annapp.R;
import de.tk.annapp.Recycler.RVAdapterSubjectList;
import de.tk.annapp.Recycler.RVAdapterTaskList;
import de.tk.annapp.Subject;
import de.tk.annapp.SubjectManager;
import de.tk.annapp.Task;

import static android.R.layout.simple_expandable_list_item_2;
import static android.R.layout.simple_spinner_dropdown_item;


public class tasksFragment extends Fragment  {
    View root;
    private SubjectManager subjectManager;
    RecyclerView recyclerView;
    RVAdapterTaskList adapterTaskList;
    private Date selectedDate;
    private boolean cal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.tasks));

        root = inflater.inflate(R.layout.fragment_tasks, container, false);

        subjectManager = SubjectManager.getInstance();
        subjectManager.load(getContext(), "subjects");

        FloatingActionButton fabAdd = (FloatingActionButton) root.findViewById(R.id.fabAdd2);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                createInputDialog();
            }
        });

        recyclerView = root.findViewById(R.id.recyclerViewTasksId);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new RVAdapterTaskList(getActivity()));

        return root;
    }

    public void createInputDialog(){
        AlertDialog.Builder ad = new  AlertDialog.Builder(this.getContext());

        View mView = View.inflate(this.getContext(), R.layout.fragment_task_input, null);

        final EditText task = (EditText) mView.findViewById(R.id.task);
        final Button btnExtra = (Button) mView.findViewById(R.id.btnExtra2);

        final ArrayList<String> subjectNames = new ArrayList<>();
        final ArrayList<String> time = new ArrayList<>();
        final ArrayList<String> kind = new ArrayList<>();

        kind.add("Hausaufgabe");
        kind.add("Schulaufgabe");
        kind.add("Notiz");

        time.add("Montag");
        time.add("Dienstag");
        time.add("Mittwoch");
        time.add("Donnerstag");
        time.add("Freitag");
        time.add("Samstag");
        time.add("Sonntag");

        cal = false;


        for (Subject s : subjectManager.getSubjects()) {
            subjectNames.add(s.name);
        }

        final Spinner subjectSelection = (Spinner) mView.findViewById(R.id.subjectSelection2);
        final Spinner timeSelection = (Spinner) mView.findViewById(R.id.time);
        final Spinner kindSelection = (Spinner) mView.findViewById(R.id.kindInput);

        ArrayAdapter<String> adapterKind = new ArrayAdapter<String>(this.getContext(), simple_spinner_dropdown_item, kind);
        ArrayAdapter<String> adapterSubject = new ArrayAdapter<String>(this.getContext(), simple_spinner_dropdown_item, subjectNames);
        ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(this.getContext(), simple_spinner_dropdown_item, time);

        subjectSelection.setAdapter(adapterSubject);
        timeSelection.setAdapter(adapterTime);
        kindSelection.setAdapter(adapterKind);

        btnExtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timeSelection.getVisibility() == View.VISIBLE){
                    createInputDialogCalendar();
                    timeSelection.setVisibility(View.GONE);
                    cal = true;
                }
                else{
                    timeSelection.setVisibility(View.VISIBLE);
                    cal = false;
                }
            }
        });

        ad      .setTitle(R.string.addTask)
                .setView(mView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(task.getText().toString().isEmpty()){
                            createAlertDialog(getString(R.string.warning), getString(R.string.warningMessage), android.R.drawable.ic_dialog_alert);
                            return;
                        }

                        if(subjectNames.isEmpty())
                            createAlertDialog(getString(R.string.warning), "Bitte fügen Sie zuerst ein neues Fach hinzu!", android.R.drawable.ic_dialog_alert);

                        Subject subject = subjectManager.getSubjectByName(subjectSelection.getSelectedItem().toString());

                        String shortTime;
                        if(timeSelection.getSelectedItem().toString().equals("Montag")){
                            shortTime = "Mo";
                        }
                        else if(timeSelection.getSelectedItem().toString().equals("Dienstag")){
                            shortTime = "Di";
                        }
                        else if(timeSelection.getSelectedItem().toString().equals("Mittwoch")){
                            shortTime = "Mi";
                        }
                        else if(timeSelection.getSelectedItem().toString().equals("Donnerstag")){
                            shortTime = "Do";
                        }
                        else if(timeSelection.getSelectedItem().toString().equals("Freitag")){
                            shortTime = "Fr";
                        }
                        else if(timeSelection.getSelectedItem().toString().equals("Samstag")){
                            shortTime = "Sa";
                        }
                        else if(timeSelection.getSelectedItem().toString().equals("Sonntag")){
                            shortTime = "So";
                        }
                        else{
                            shortTime = "";
                            createAlertDialog(getString(R.string.warning), "Bitte starten sie die App neu. Ein Fehler ist aufgetreten.", android.R.drawable.ic_dialog_alert);
                        }

                        String shortKind;
                        if(kindSelection.getSelectedItem().toString().equals("Hausaufgabe")){
                            shortKind = "HA";
                        }
                        else if(kindSelection.getSelectedItem().toString().equals("Schulaufgabe")){
                            shortKind = "SA";
                        }
                        else if(kindSelection.getSelectedItem().toString().equals("Notiz")){
                            shortKind = "No";
                        }
                        else{
                            shortKind = "";
                            createAlertDialog(getString(R.string.warning), "Bitte starten sie die App neu. Ein Fehler ist aufgetreten.", android.R.drawable.ic_dialog_alert);
                        }
                        subject.addTask(task.getText().toString(), selectedDate, shortKind, shortTime, cal);

                        for(Task task : subject.getAllTasks()){
                            System.out.println("Task: " + task.task + ", " + task.date + ", " + task.kind);
                        }

                        recyclerView.setAdapter(new RVAdapterTaskList(getActivity()));
                        subjectManager.save(getContext(), "subjects");
                    }
                })
                .show();
    }

    public void createInputDialogCalendar(){
        AlertDialog.Builder ad = new  AlertDialog.Builder(this.getContext());

        View mView = View.inflate(this.getContext(), R.layout.fragment_task_input_calendar, null);

        final CalendarView calendar = (CalendarView) mView.findViewById(R.id.calendarViewTasks);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                selectedDate = new Date(i, i1, i2);
            }
        });

        ad      .setTitle("Datum auswählen")
                .setView(mView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

    void createAlertDialog(String title, String text, int ic){
        AlertDialog.Builder builder;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            builder = new AlertDialog.Builder(this.getContext(), android.R.style.Theme_Material_Dialog_Alert);
        }
        else{
            builder = new AlertDialog.Builder(this.getContext());
        }
        builder.setTitle(title)
                .setMessage(text)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which){}})
                .setIcon(ic)
                .show();
    }
}
