package de.tk.annapp.Recycler;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import de.tk.annapp.Task;
import de.tk.annapp.R;
import de.tk.annapp.Subject;
import de.tk.annapp.SubjectManager;
import de.tk.annapp.Util;

import static android.R.layout.simple_spinner_dropdown_item;

public class RVAdapterTaskList extends RecyclerView.Adapter<RVAdapterTaskList.RecyclerVHTask> {
    private Context context;
    private ArrayList<Task> tasks = new ArrayList<>();
    private SubjectManager subjectManager;
    int pos;

    public RVAdapterTaskList(Context context) {

        this.context = context;
        subjectManager = SubjectManager.getInstance();
        constructor();

    }

    void constructor() {
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_YEAR,-1);

        pos = -1;

        tasks.clear();
        ArrayList<Subject> subjects = subjectManager.getSubjects();


        for (Subject s : subjects) {
            if (s.getAllTasksSorted().isEmpty())
                continue;
            tasks.add(null);
            pos++;
            s.setPosition(pos);
            for (Task t : s.getAllTasksSorted()) {
                /*if (t.getDue().before(yesterday)) { The deletingstuff has to happen somewhere else
                    delete(t);
                    Toast.makeText(context,"Old Tasks were deleted",Toast.LENGTH_SHORT).show();
                    continue;
                }*/
                tasks.add(t);
                pos++;
            }
        }

        if (tasks.isEmpty()) {//TODO Default if nothing is added
            //tasks.add(new Task(null, null, null, null, context.getString(R.string.insertTask)));
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
        if (tasks.get(position) == null) {
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
            holder.subjectTxt.setVisibility(View.VISIBLE);
            holder.subjectTxt.setText(tasks.get(position + 1).getSubject().getName());
            return;
        }
        holder.dateTxt.setText(Util.getDateString(tasks.get(position).getDue())); //When the Task is due
        holder.taskTxt.setText(tasks.get(position).getTask());
        holder.kindTxt.setText(tasks.get(position).getKind());

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Create InputDialog...");
                createEditDialog(tasks.get(position));
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askDelete(tasks.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    //Viewholder Class
    public class RecyclerVHTask extends RecyclerView.ViewHolder {
        TextView taskTxt;
        TextView dateTxt;
        TextView kindTxt;
        TextView subjectTxt;
        ImageButton editButton;
        ImageButton deleteButton;

        public RecyclerVHTask(View itemView) {
            super(itemView);
            dateTxt = itemView.findViewById(R.id.item_task_date);
            taskTxt = itemView.findViewById(R.id.item_task_task);
            kindTxt = itemView.findViewById(R.id.item_task_kind);
            subjectTxt = itemView.findViewById(R.id.item_task_subject);
            editButton = itemView.findViewById(R.id.button_item_task_edit);
            deleteButton = itemView.findViewById(R.id.button_item_task_delete);
        }
    }

    public void createEditDialog(final Task task) {
        AlertDialog.Builder ad = new AlertDialog.Builder(context);

        View mView = View.inflate(context, R.layout.fragment_task_edit, null);

        Calendar now = Calendar.getInstance();
        String[] duedates;
        if (task.getDue().get(Calendar.YEAR) == now.get(Calendar.YEAR) & task.getDue().get(Calendar.WEEK_OF_YEAR) == now.get(Calendar.WEEK_OF_YEAR))
            duedates = new String[]{"Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag", "Datum auswählen"};
        else
            duedates = new String[]{"Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag", Util.getFullDate(task.getDue()), "Datum auswählen"};

        String[] kinds = new String[]{"Hausaufgabe", "Schulaufgabe", "Notiz"};

        final EditText taskInput = (EditText) mView.findViewById(R.id.taskInput);
        taskInput.setText(String.valueOf(task.getTask()));

        final Spinner kindSelection = (Spinner) mView.findViewById(R.id.spinner_task_input_kind);
        ArrayAdapter<String> adapterKind = new ArrayAdapter<String>(context, simple_spinner_dropdown_item, kinds);
        kindSelection.setAdapter(adapterKind);
        switch (task.getKind()) {
            case "HA":
                kindSelection.setSelection(0);
                break;
            case "SA":
                kindSelection.setSelection(1);
                break;
            case "No":
                kindSelection.setSelection(2);
                break;
        }

        final Spinner timeSelection = (Spinner) mView.findViewById(R.id.timeInput);
        ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(context, simple_spinner_dropdown_item, duedates);
        timeSelection.setAdapter(adapterTime);
        timeSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (((String) timeSelection.getItemAtPosition(i)).equals("Datum auswählen")) {
                    Calendar date = Calendar.getInstance();
                    if (((String) timeSelection.getItemAtPosition(i - 1)).matches("\\d*\\.\\d*\\.\\d*")) {
                        date = Util.getCalendarFromFullString(((String) timeSelection.getItemAtPosition(i - 1)));
                    }
                    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                            String[] pos = new String[]{"Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag", dayOfMonth + "." + monthOfYear + "." + year, "Datum auswählen"};
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
                    datePickerDialog.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (task.getDue().get(Calendar.YEAR) == now.get(Calendar.YEAR) & task.getDue().get(Calendar.WEEK_OF_YEAR) == now.get(Calendar.WEEK_OF_YEAR))
            switch (Util.getWeekDayShort(task.getDue())) {
                case "Mo":
                    timeSelection.setSelection(0);
                    break;
                case "Di":
                    timeSelection.setSelection(1);
                    break;
                case "Mi":
                    timeSelection.setSelection(2);
                    break;
                case "Do":
                    timeSelection.setSelection(3);
                    break;
                case "Fr":
                    timeSelection.setSelection(4);
                    break;
                case "Sa":
                    timeSelection.setSelection(5);
                    break;
                case "So":
                    timeSelection.setSelection(6);
                    break;
            }
        else
            timeSelection.setSelection(7);

        ad.setTitle(context.getString(R.string.editTask) + task.getSubject().getName())
                .setView(mView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (taskInput.getText().toString().isEmpty()) {
                            createAlertDialog(context.getString(R.string.warning), context.getString(R.string.warningMessage), android.R.drawable.ic_dialog_alert);
                            return;
                        }

                        Calendar due = Calendar.getInstance();
                        if (timeSelection.getSelectedItem().toString().equals("Montag")) {
                            due.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                        } else if (timeSelection.getSelectedItem().toString().equals("Dienstag")) {
                            due.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                        } else if (timeSelection.getSelectedItem().toString().equals("Mittwoch")) {
                            due.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                        } else if (timeSelection.getSelectedItem().toString().equals("Donnerstag")) {
                            due.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                        } else if (timeSelection.getSelectedItem().toString().equals("Freitag")) {
                            due.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                        } else if (timeSelection.getSelectedItem().toString().equals("Samstag")) {
                            due.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                        } else if (timeSelection.getSelectedItem().toString().equals("Sonntag")) {
                            due.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                        } else if (timeSelection.getSelectedItem().toString().matches("\\d*\\.\\d*\\.\\d*")) {
                            due = Util.getCalendarFromFullString(timeSelection.getSelectedItem().toString());
                        } else {
                            createAlertDialog(/*getString(R.string.warning)*/"Achtung", "Bitte starten sie die App neu. Ein Fehler ist aufgetreten.", android.R.drawable.ic_dialog_alert);
                            return;
                        }

                        String shortKind;//TODO change this... somehow
                        switch ((String) kindSelection.getSelectedItem()) {
                            case "Hausaufgabe":
                                shortKind = "HA";
                                break;
                            case "Schulaufgabe":
                                shortKind = "SA";
                                break;
                            case "Notiz":
                                shortKind = "No";
                                break;
                            default:
                                shortKind = "Error!!!";
                        }

                        task.setDue(due);
                        task.setKind(shortKind);
                        task.setTask(taskInput.getText().toString());
                        int altindex = tasks.indexOf(task);
                        constructor();
                        int newindex = tasks.indexOf(task);
                        notifyItemMoved(altindex,newindex);//TODO Testing this movement

                        subjectManager.save(context, "tasks");
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Do nothing

                    }
                });

        ad.show();
    }


    void createAlertDialog(String title, String text, int ic) {
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

    public void askDelete(final Task task) {

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
                        delete(task);
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

    private void delete(Task task) {
        int index = tasks.indexOf(task);
        task.getSubject().removeTask(task);
        if (task.getSubject().getAllTasks().isEmpty()) {
            tasks.remove(index - 1);
            notifyItemRemoved(index - 1);
            index--;
        }

        tasks.remove(task);
        notifyItemRemoved(index);

        subjectManager.save(context, "subjects");
    }
    public void addTask(Task task){
        constructor();
        if(tasks.contains(task))
            notifyItemInserted(tasks.indexOf(task));
    }
}
