package de.tk.annapp.Recycler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import de.tk.annapp.Grade;
import de.tk.annapp.Task;
import de.tk.annapp.R;
import de.tk.annapp.Subject;
import de.tk.annapp.SubjectManager;
import de.tk.annapp.Task;

import static android.R.layout.simple_spinner_dropdown_item;

public class RVAdapterTaskList extends RecyclerView.Adapter<RVAdapterTaskList.RecyclerVHTask>{
    Context c;
    private ArrayList<Task> tasks = new ArrayList<>();
    private ArrayList<Subject> subjects = new ArrayList<>();
    private ArrayList<Subject> subjectsWithTasks = new ArrayList<>();
    private SubjectManager subjectManager;
    private Date selectedDate;
    AlertDialog adTrueDialog;
    private boolean cal;
    int pos;

    public RVAdapterTaskList(Context _c){

        c = _c;
        subjectManager = SubjectManager.getInstance();
        constructor();

    }

    void constructor(){

        Calendar calend = new GregorianCalendar();

        pos = -1;

        subjectsWithTasks.clear();
        tasks.clear();
        subjects = subjectManager.getSubjects();

        for (Subject sj : subjects){
            if(!sj.getAllTasks().isEmpty()){
                subjectsWithTasks.add(sj);
            }
        }

        if(subjectsWithTasks.isEmpty()){
            tasks.add(new Task(null, null, null, null, c.getString(R.string.insertTask), false));
        }

        for(Subject s : subjectsWithTasks){
            tasks.add(new Task(null, null, null, s.getName(), null, false));
            pos++;
            s.setPosition(pos);
            for(Task t : s.getAllTasksSorted()){
                if(t.dateNumber < calend.getTime().getTime()){
                    delete(s, t);
                }
                //TODO: Delete Task, if it is in the past
                //TODO: Change Task to "Mo", "Di", ..., if it is in less than one week
                tasks.add(t);
                pos++;
            }
        }
    }

    @Override
    public RecyclerVHTask onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_task_list, parent, false);
        return new RecyclerVHTask(v);
    }

    @Override
    public void onBindViewHolder(RecyclerVHTask holder, final int position) {
        holder.dateTxt.setText((CharSequence) tasks.get(position).date);
        holder.taskTxt.setText(tasks.get(position).task);
        holder.kindTxt.setText(tasks.get(position).kind);
        holder.subjectTxt.setText(tasks.get(position).subject);

        if(holder.taskTxt.getText().toString().isEmpty()) {
            holder.editButton.setVisibility(View.GONE);
            holder.subjectTxt.setVisibility(View.VISIBLE);
        }
        else{
            holder.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Create InputDialog...");
                    createEditDialog(tasks.get(position).subject, tasks.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    //Viewholder Class
    public class RecyclerVHTask extends RecyclerView.ViewHolder{
        TextView taskTxt;
        TextView dateTxt;
        TextView kindTxt;
        TextView subjectTxt;
        ImageButton editButton;

        public RecyclerVHTask(View itemView){
            super(itemView);
            dateTxt = itemView.findViewById(R.id.item_task_date);
            taskTxt = itemView.findViewById(R.id.item_task_task);
            kindTxt = itemView.findViewById(R.id.item_task_kind);
            subjectTxt = itemView.findViewById(R.id.item_task_subject);
            editButton = itemView.findViewById(R.id.item_task_deleteButton);
        }
    }

    public void createEditDialog(final String _subject, final Task task){
        final Subject subject = subjectManager.getSubjectByName(_subject);
        AlertDialog.Builder ad = new  AlertDialog.Builder(c);

        View mView = View.inflate(c, R.layout.fragment_task_edit, null);

        final ArrayList<String> time = new ArrayList<>();
        time.add("Montag");
        time.add("Dienstag");
        time.add("Mittwoch");
        time.add("Donnerstag");
        time.add("Freitag");
        time.add("Samstag");
        time.add("Sonntag");

        final EditText taskInput = (EditText) mView.findViewById(R.id.taskInput);
        taskInput.setText(String.valueOf(task.task));

        final Spinner timeSelection = (Spinner) mView.findViewById(R.id.timeInput);
        ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(c, simple_spinner_dropdown_item, time);
        timeSelection.setAdapter(adapterTime);

        final Button btnExtra = (Button) mView.findViewById(R.id.btnExtra3);

        if(task.date != "Mo" && task.date != "Di" && task.date != "Mi" && task.date != "Do" && task.date != "Fr" && task.date != "Sa" && task.date != "So") {
            timeSelection.setVisibility(View.GONE);
            cal = true;
        }
        else{
            timeSelection.setVisibility(View.VISIBLE);
            cal = false;
        }

        final Button btnDelete = (Button) mView.findViewById(R.id.btnDeleteTask);
        final Button btnDeleteIcon = (Button) mView.findViewById(R.id.btnDeleteIcon);

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

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(subject, task);
            }
        });

        btnDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(subject, task);
            }
        });

        ad      .setTitle(c.getString(R.string.editTask) + subject.name)
                .setView(mView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(taskInput.getText().toString().isEmpty()){
                            createAlertDialog(c.getString(R.string.warning), c.getString(R.string.warningMessage), android.R.drawable.ic_dialog_alert);
                            return;
                        }

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
                            createAlertDialog(/*getString(R.string.warning)*/"Achtung", "Bitte starten sie die App neu. Ein Fehler ist aufgetreten.", android.R.drawable.ic_dialog_alert);
                        }

                        subject.editTask(task, taskInput.getText().toString(), shortTime, selectedDate, cal);
                        notifyItemChanged(tasks.indexOf(task));

                        constructor();
                        //TODO: Change place of task if date changed

                        subjectManager.save(c, "tasks");
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Do nothing

                    }
                });

        adTrueDialog = ad.show();
    }

    public void createInputDialogCalendar(){
        AlertDialog.Builder ad = new  AlertDialog.Builder(c);

        View mView = View.inflate(c, R.layout.fragment_task_input_calendar, null);

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(c, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(c);
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

    public void delete(final Subject subject, final Task task){

        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(c, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(c);
        }
        builder.setTitle(R.string.deleteQuestion)
                .setMessage(c.getString(R.string.deleteQuestionMessageTask))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        subject.removeTask(task);
                        //System.out.println("Remove task: " + task.task);

                        notifyItemRemoved(tasks.indexOf(task));
                        notifyItemRangeChanged(tasks.indexOf(task), getItemCount());

                        //Only remove the Subject title if the subject has no tasks left
                        if(subjectManager.getSubjectByName(task.subject).getAllTasks().isEmpty()) {
                            notifyItemRemoved(subjectManager.getSubjectByName(task.subject).getPosition());
                            notifyItemRangeChanged(subjectManager.getSubjectByName(task.subject).getPosition(), getItemCount());
                        }

                        constructor();

                        subjectManager.save(c,"subjects");

                        adTrueDialog.cancel();



                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //I think - do Nothing - but if you want
                    }
                })
                .setIcon(android.R.drawable.ic_delete)
                .show();


    }
}
