package de.tk.annapp.Recycler;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import de.tk.annapp.Task;
import de.tk.annapp.R;
import de.tk.annapp.Subject;
import de.tk.annapp.SubjectManager;
import de.tk.annapp.Util;

import static android.R.layout.simple_spinner_dropdown_item;

public class RVAdapterTaskList extends RecyclerView.Adapter<RVAdapterTaskList.RecyclerVHTask>{
    private Context context;
    private ArrayList<Task> tasks = new ArrayList<>();
    private ArrayList<Subject> subjects = new ArrayList<>();
    private ArrayList<Subject> subjectsWithTasks = new ArrayList<>();
    private SubjectManager subjectManager;
    private Date selectedDate;
    AlertDialog adTrueDialog;
    private boolean cal;
    int pos;

    public RVAdapterTaskList(Context context){

        this.context = context;
        subjectManager = SubjectManager.getInstance();
        constructor();

    }

    void constructor(){

        Calendar now = Calendar.getInstance();

        pos = -1;

        subjectsWithTasks.clear();
        tasks.clear();
        subjects = subjectManager.getSubjects();

        for (Subject sj : subjects){
            if(!sj.getAllTasks().isEmpty()){
                subjectsWithTasks.add(sj);
            }
        }

        if(subjectsWithTasks.isEmpty()){//TODO Default if nothing is added
            //tasks.add(new Task(null, null, null, null, context.getString(R.string.insertTask)));
        }

        for(Subject s : subjectsWithTasks){
            tasks.add(null);
            pos++;
            s.setPosition(pos);
            for(Task t : s.getAllTasksSorted()){
                if(t.getDue().before(now)){
                    delete(s, t);
                }
                //TODO: Delete Task, if it is in the past Done?!
                tasks.add(t);
                pos++;
            }
        }
    }

    @Override
    public RecyclerVHTask onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_task_list, parent, false);
        return new RecyclerVHTask(v);
    }

    @Override
    public void onBindViewHolder(RecyclerVHTask holder, final int position) {
        //TODO: Change Task to "Mo", "Di", ..., if it is in less than one week
        if(tasks.get(position)==null){
            holder.editButton.setVisibility(View.GONE);
            holder.subjectTxt.setVisibility(View.VISIBLE);
            holder.subjectTxt.setText(tasks.get(position).getSubject().getName());
            return;
        }
        holder.dateTxt.setText(Util.getDateString(tasks.get(position).getDue())); //When the Task is due
        holder.taskTxt.setText(tasks.get(position).getTask());
        holder.kindTxt.setText(tasks.get(position).getKind());

        /*else{ TODO Can someone look up, if this is really needed
            holder.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Create InputDialog...");
                    createEditDialog(tasks.get(position).subject, tasks.get(position));
                }
            });
        }*/
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

    public void createEditDialog(final String subjectname, final Task task){
        final Subject subject = subjectManager.getSubjectByName(subjectname);
        AlertDialog.Builder ad = new  AlertDialog.Builder(context);

        View mView = View.inflate(context, R.layout.fragment_task_edit, null);

        Calendar now = Calendar.getInstance();
        String[] pos;
        if(task.getDue().get(Calendar.YEAR)==now.get(Calendar.YEAR) & task.getDue().get(Calendar.WEEK_OF_YEAR)==now.get(Calendar.WEEK_OF_YEAR))
            pos= new String[]{"Montag","Dienstag","Mittwoch","Donnerstag","Freitag","Samstag","Sonntag","Datum auswählen"};
        else
            pos= new String[]{"Montag","Dienstag","Mittwoch","Donnerstag","Freitag","Samstag","Sonntag",Util.getFullDate(task.getDue()),"Datum auswählen"};

        final EditText taskInput = (EditText) mView.findViewById(R.id.taskInput);
        taskInput.setText(String.valueOf(task.getTask()));

        final Spinner timeSelection = (Spinner) mView.findViewById(R.id.timeInput);
        ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(context, simple_spinner_dropdown_item, pos);
        timeSelection.setAdapter(adapterTime);
        timeSelection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(((String)timeSelection.getItemAtPosition(i)).equals("Datum auswählen") | ((String)timeSelection.getItemAtPosition(i)).matches("\\d*\\.\\d*\\.\\d*")){
                    Calendar date= Calendar.getInstance();
                    if(((String)timeSelection.getItemAtPosition(i)).matches("\\d*\\.\\d*\\.\\d*")){
                        date = Util.getCalendarFromFullString(((String)timeSelection.getItemAtPosition(i)));
                    }
                    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener(){

                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                            String[] pos = new String[]{"Montag","Dienstag","Mittwoch","Donnerstag","Freitag","Samstag","Sonntag",dayOfMonth+"."+monthOfYear+"."+year,"Datum auswählen"};
                            ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(context, simple_spinner_dropdown_item, pos);
                            timeSelection.setAdapter(adapterTime);
                            timeSelection.setSelection(7);
                            //TODO If nothing was selected
                        }
                    };
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            context, onDateSetListener, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.setTitle("Datum auswählen");
                    datePickerDialog.setCanceledOnTouchOutside(false);
                }

            }
        });
        if(task.getDue().get(Calendar.YEAR)==now.get(Calendar.YEAR) & task.getDue().get(Calendar.WEEK_OF_YEAR)==now.get(Calendar.WEEK_OF_YEAR))
            switch (Util.getWeekDayShort(task.getDue())){
                case "Mo": timeSelection.setSelection(0); break;
                case "Di": timeSelection.setSelection(1); break;
                case "Mi": timeSelection.setSelection(2); break;
                case "Do": timeSelection.setSelection(3); break;
                case "Fr": timeSelection.setSelection(4); break;
                case "Sa": timeSelection.setSelection(5); break;
                case "So": timeSelection.setSelection(6); break;
            }
            else
                timeSelection.setSelection(7);


        final Button btnDelete = (Button) mView.findViewById(R.id.btnDeleteTask);//TODO Remove next to edit!
        final Button btnDeleteIcon = (Button) mView.findViewById(R.id.btnDeleteIcon);

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

        ad      .setTitle(context.getString(R.string.editTask) + subject.name)
                .setView(mView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(taskInput.getText().toString().isEmpty()){
                            createAlertDialog(context.getString(R.string.warning), context.getString(R.string.warningMessage), android.R.drawable.ic_dialog_alert);
                            return;
                        }

                        Calendar due = Calendar.getInstance();
                        if(timeSelection.getSelectedItem().toString().equals("Montag")){
                            due.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
                        }
                        else if(timeSelection.getSelectedItem().toString().equals("Dienstag")){
                            due.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
                        }
                        else if(timeSelection.getSelectedItem().toString().equals("Mittwoch")){
                            due.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
                        }
                        else if(timeSelection.getSelectedItem().toString().equals("Donnerstag")){
                            due.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
                        }
                        else if(timeSelection.getSelectedItem().toString().equals("Freitag")){
                            due.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
                        }
                        else if(timeSelection.getSelectedItem().toString().equals("Samstag")){
                            due.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
                        }
                        else if(timeSelection.getSelectedItem().toString().equals("Sonntag")){
                            due.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
                        }
                        else if(timeSelection.getSelectedItem().toString().matches("\\d*\\.\\d*\\.\\d*")){
                            due = Util.getCalendarFromFullString(timeSelection.getSelectedItem().toString());
                        }else{
                            createAlertDialog(/*getString(R.string.warning)*/"Achtung", "Bitte starten sie die App neu. Ein Fehler ist aufgetreten.", android.R.drawable.ic_dialog_alert);
                            return;
                        }

                        task.setDue(due);
                        task.setTask(taskInput.getText().toString());
                        notifyItemChanged(tasks.indexOf(task));//TODO Total reload?

                        constructor();
                        //TODO: Change place of task if date changed

                        subjectManager.save(context, "tasks");
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

    public void createInputDialogCalendar(){//TODO Remove

        return ;
        /*datePickerDialog.setTitle("Datum auswählen");
        datePickerDialog.show();
        AlertDialog.Builder ad = new  AlertDialog.Builder(context);

        View mView = View.inflate(context, R.layout.fragment_task_input_calendar, null);

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
                .show();*/
    }

    void createAlertDialog(String title, String text, int ic){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
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
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle(R.string.deleteQuestion)
                .setMessage(context.getString(R.string.deleteQuestionMessageTask))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        subject.removeTask(task);
                        //System.out.println("Remove task: " + task.task);

                        notifyItemRemoved(tasks.indexOf(task));
                        notifyItemRangeChanged(tasks.indexOf(task), getItemCount());

                        //Only remove the Subject title if the subject has no tasks left
                        if(task.getSubject().getAllTasks().isEmpty()) {
                            notifyItemRemoved(task.getSubject().getPosition());
                            notifyItemRangeChanged(task.getSubject().getPosition(), getItemCount());
                        }

                        constructor();

                        subjectManager.save(context,"subjects");

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
