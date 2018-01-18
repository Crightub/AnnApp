package de.tk.annapp.Recycler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

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
}
