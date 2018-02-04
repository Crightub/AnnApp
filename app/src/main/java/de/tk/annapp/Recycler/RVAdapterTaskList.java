package de.tk.annapp.Recycler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;

import de.tk.annapp.Grade;
import de.tk.annapp.Task;
import de.tk.annapp.R;
import de.tk.annapp.Subject;
import de.tk.annapp.SubjectManager;

public class RVAdapterTaskList extends RecyclerView.Adapter<RVAdapterTaskList.RecyclerVHTask>{
    Context c;
    private ArrayList<Task> tasks = new ArrayList<>();
    private ArrayList<Subject> subjects = new ArrayList<>();
    private ArrayList<Subject> subjectsWithTasks = new ArrayList<>();
    private SubjectManager subjectManager;
    AlertDialog adTrueDialog;

    public RVAdapterTaskList(Context _c){
        c = _c;
        subjectManager = SubjectManager.getInstance();
        subjects = subjectManager.getSubjects();

        for (Subject sj : subjects){
            if(!sj.getAllTasks().isEmpty()){
                subjectsWithTasks.add(sj);
            }
        }

        if(subjectsWithTasks.isEmpty()){
            tasks.add(new Task(null, c.getString(R.string.insertTask), null, null));
        }

        for (Subject s : subjectsWithTasks) {
            tasks.add(new Task(null, s.getName(), null, null));
            for (Task t : s.getAllTasks()) {
                tasks.add(t);
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
        if(holder.taskTxt.getText().toString().isEmpty()) {
            holder.editButton.setVisibility(View.GONE);
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
        ImageButton editButton;

        public RecyclerVHTask(View itemView){
            super(itemView);
            dateTxt = itemView.findViewById(R.id.item_task_date);
            taskTxt = itemView.findViewById(R.id.item_task_task);
            kindTxt = itemView.findViewById(R.id.item_task_kind);
            editButton = itemView.findViewById(R.id.item_task_deleteButton);
        }
    }

    public void createEditDialog(final String _subject, final Task task){
        final Subject subject = subjectManager.getSubjectByName(_subject);
        AlertDialog.Builder ad = new  AlertDialog.Builder(c);

        View mView = View.inflate(c, R.layout.fragment_task_edit, null);

        final EditText taskInput = (EditText) mView.findViewById(R.id.taskInput);
        taskInput.setText(String.valueOf(task.task));

        final EditText dateInput =(EditText) mView.findViewById(R.id.dateInput);
        dateInput.setText(String.valueOf(task.date));

        final Button btnDelete = (Button) mView.findViewById(R.id.btnDeleteTask);
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

        ad      .setTitle(c.getString(R.string.editTask) + subject.name)
                .setView(mView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(taskInput.getText().toString().isEmpty()){
                            createAlertDialog(c.getString(R.string.warning), c.getString(R.string.warningMessage), android.R.drawable.ic_dialog_alert);
                            return;
                        }

                        if(dateInput.getText().toString().isEmpty()) {
                            createAlertDialog(c.getString(R.string.warning), c.getString(R.string.warningMessage), android.R.drawable.ic_dialog_alert);
                            return;
                        }

                        subject.editTask(task, taskInput.getText().toString(), dateInput.getText().toString());
                        notifyItemChanged(tasks.indexOf(task));

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

                        notifyItemRemoved(tasks.indexOf(task));
                        notifyItemRangeChanged(tasks.indexOf(task), getItemCount());
                        subject.removeTask(task);
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

        //TODO: reload activity
    }
}
