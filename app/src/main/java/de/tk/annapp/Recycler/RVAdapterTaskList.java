package de.tk.annapp.Recycler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.ArrayList;

import de.tk.annapp.Task;
import de.tk.annapp.R;
import de.tk.annapp.Subject;
import de.tk.annapp.SubjectManager;
import de.tk.annapp.Task;

/**
 * Created by Jakob on 13.01.2018.
 */

public class RVAdapterTaskList extends RecyclerView.Adapter<RVAdapterTaskList.RecyclerVH>{
    Context c;
    private ArrayList<Task> tasks = new ArrayList<>();
    private ArrayList<Subject> subjects = new ArrayList<>();

    public RVAdapterTaskList(Context _c, ArrayList<Subject> _subjectNames){
        c = _c;
        subjects = _subjectNames;
    }

    @Override
    public RecyclerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_task_list, parent, false);
        return new RecyclerVH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerVH holder, int position) {
        holder.nameTxt.setText(subjects.get(position).name);
        holder.taskTxt.setText("" + subjects.get(position).getAllTasks());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    //Viewholder Class
    public class RecyclerVH extends RecyclerView.ViewHolder{
        TextView taskTxt;
        TextView nameTxt;

        public RecyclerVH(View itemView){
            super(itemView);
            nameTxt = itemView.findViewById(R.id.item_task_name);
            taskTxt = itemView.findViewById(R.id.item_task_task);
        }
    }

/*
    Context c;
    private ArrayList<Task> tasks = new ArrayList<>();
    private SubjectManager subjectManager;
    private String subjectName;

    boolean isWrittenBool;
    AlertDialog adTrueDialog;

    public RVAdapterTaskList(Context _c, String _subjectName){
        c = _c;
        subjectManager = SubjectManager.getInstance();
        subjectName = _subjectName;
        tasks = subjectManager.getSubjectByName(subjectName).getAllTasks();
    }

    @Override
    public RVAdapterTaskList.RecyclerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_task_list, parent, false);
        return new RVAdapterTaskList.RecyclerVH(v);
    }


    //TODO Is wrong!!!
    @Override
    public void onBindViewHolder(RVAdapterTaskList.RecyclerVH holder, final int position) {
        tasks = subjectManager.getSubjectByName(subjectName).getAllTasks();
        holder.taskTxt.setText(String.valueOf(tasks.get(position).task));

        if(!tasks.get(position).note.isEmpty())
            holder.expandableTextView.setText(tasks.get(position).note + "\n" +  c.getString(R.string.ratingList) + tasks.get(position).rating);
        else
            holder.expandableTextView.setText(tasks.get(position).note +  c.getString(R.string.ratingList) + tasks.get(position).rating);

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Create InputDialog...");
                createEditDialog(subjectManager.getSubjectByName(subjectName), tasks.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    //Viewholder Class
    public class RecyclerVH extends RecyclerView.ViewHolder{
        TextView taskTxt;
        ImageButton editButton;

        public RecyclerVH(View itemView){
            super(itemView);
            editButton = itemView.findViewById(R.id.item_task_deleteButton);
            expandableTextView = itemView.findViewById(R.id.expandable_text_view);
            taskTxt = itemView.findViewById(R.id.item_task_task);
        }
    }




    public void createEditDialog(final Subject subject, final task task){

        AlertDialog.Builder ad = new  AlertDialog.Builder(c);



        //View mView = getLayoutInflater().inflate(R.layout.fragment_task_input, null);
        View mView = View.inflate(c, R.layout.fragment_task_edit, null);

        final EditText taskInput = (EditText) mView.findViewById(R.id.taskInput);
        taskInput.setText(String.valueOf(task.task));

        final  EditText ratingInput =(EditText) mView.findViewById(R.id.ratingInput);
        ratingInput.setText(String.valueOf(task.rating));

        final EditText note = (EditText) mView.findViewById(R.id.note);
        note.setText(task.note);

        final ImageView btnHelp = (ImageView) mView.findViewById(R.id.btnHelp);
        final Button btnExtra = (Button) mView.findViewById(R.id.btnExtra);
        final LinearLayout extraLayout = (LinearLayout) mView.findViewById(R.id.extraLayout);

        final Button btnDelete = (Button) mView.findViewById(R.id.btnDelete);
        final Button btnDeleteIcon = (Button) mView.findViewById(R.id.btnDeleteIcon);

        final RadioButton isWritten = mView.findViewById(R.id.isWritten);

        final RadioButton isNotWritten = mView.findViewById(R.id.isNotWritten);

        if(task.iswritten)
            isWritten.setChecked(true);
        else
            isNotWritten.setChecked(true);


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



        btnExtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(extraLayout.getVisibility() != View.VISIBLE)
                    extraLayout.setVisibility(View.VISIBLE);
                else
                    extraLayout.setVisibility(View.GONE);
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlertDialog(c.getString(R.string.rating), c.getString(R.string.ratingExplanation), 0);
            }
        });






        ad      .setTitle(c.getString(R.string.edittask) + subject.name)
                .setView(mView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        float rating = 1;

                        //testing which button is active for decision whether your task is written or whether it's not
                        if(isWritten.isChecked())
                            isWrittenBool = true;
                        else if(isNotWritten.isChecked())
                            isWrittenBool = false;

                        if(taskInput.getText().toString().isEmpty()){
                            createAlertDialog(c.getString(R.string.warning), c.getString(R.string.warningMessage), android.R.drawable.ic_dialog_alert);
                            return;
                        }

                        if(!ratingInput.getText().toString().isEmpty())
                            rating = Float.parseFloat(ratingInput.getText().toString());


                        /*Subject subject = subjectManager.getSubjectByName(subjectSelection.getSelectedItem().toString());
                        subject.addtask(Integer.valueOf(taskInput.getText().toString()), isWrittenBool, rating, note.getText().toString());

                        subject.edittask(task, Integer.valueOf(taskInput.getText().toString()), isWrittenBool, rating, note.getText().toString());
                        notifyItemChanged(tasks.indexOf(task));

                        subjectManager.save(c, "subjects");
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

    public void delete(final Subject subject, final task task){

        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(c, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(c);
        }
        builder.setTitle(R.string.deleteQuestion)
                .setMessage(c.getString(R.string.deleteQuestionMessage))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        notifyItemRemoved(tasks.indexOf(task));
                        notifyItemRangeChanged(tasks.indexOf(task), getItemCount());
                        subject.removetask(task);
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

    }*/


}
