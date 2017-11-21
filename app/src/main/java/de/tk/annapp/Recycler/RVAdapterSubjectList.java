package de.tk.annapp.Recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import de.tk.annapp.R;

import de.tk.annapp.subject;

public class RVAdapterSubjectList extends RecyclerView.Adapter<RVAdapterSubjectList.RecyclerVH> {

    Context c;
    private ArrayList<subject> subjects = new ArrayList<>();

    public RVAdapterSubjectList(Context _c,  ArrayList<subject> _subjects){
        c = _c;
        subjects = _subjects;
    }

    @Override
    public RecyclerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_subject_list, parent, false);
        return new RecyclerVH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerVH holder, int position) {
        holder.nameTxt.setText(subjects.get(position).name);
        holder.gradeTxt.setText("" + subjects.get(position).getGradePointAverage());
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    //Viewholder Class
    public class RecyclerVH extends RecyclerView.ViewHolder{
        TextView nameTxt;
        TextView gradeTxt;

        public RecyclerVH(View itemView){
            super(itemView);

            nameTxt = itemView.findViewById(R.id.item_subject_name);
            gradeTxt = itemView.findViewById(R.id.item_subject_grade);
        }
    }
}
