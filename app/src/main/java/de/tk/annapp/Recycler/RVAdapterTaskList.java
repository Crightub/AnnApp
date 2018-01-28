package de.tk.annapp.Recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.PrintStream;
import java.util.ArrayList;

import de.tk.annapp.Task;
import de.tk.annapp.R;
import de.tk.annapp.Subject;
import de.tk.annapp.SubjectManager;

public class RVAdapterTaskList extends RecyclerView.Adapter<RVAdapterTaskList.RecyclerVHTask>{
    Context c;
    private ArrayList<Task> tasks = new ArrayList<>();
    private ArrayList<Subject> subjects = new ArrayList<>();
    private SubjectManager subjectManager;

    public RVAdapterTaskList(Context _c){
        c = _c;
        subjectManager = SubjectManager.getInstance();
        subjects = subjectManager.getSubjects();



        for (Subject s :
                subjectManager.getSubjects()) {
            tasks.add(new Task(s.getName(), ""));
            for (Task t :
                    s.getAllTasks()) {
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
    public void onBindViewHolder(RecyclerVHTask holder, int position) {
        holder.nameTxt.setText(tasks.get(position).date);
        holder.taskTxt.setText(tasks.get(position).task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    //Viewholder Class
    public class RecyclerVHTask extends RecyclerView.ViewHolder{
        TextView taskTxt;
        TextView nameTxt;

        public RecyclerVHTask(View itemView){
            super(itemView);
            nameTxt = itemView.findViewById(R.id.item_task_name);
            taskTxt = itemView.findViewById(R.id.item_task_task);
        }
    }
}
