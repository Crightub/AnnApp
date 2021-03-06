package de.tk.annapp.Recycler;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import de.tk.annapp.Fragments.GradeChildFragment;
import de.tk.annapp.MainActivity;
import de.tk.annapp.R;

import de.tk.annapp.Subject;
import de.tk.annapp.SubjectManager;
import de.tk.annapp.Util;

public class RVAdapterSubjectList extends RecyclerView.Adapter<RVAdapterSubjectList.RecyclerVH> {

    private Context context;
    private ArrayList<Subject> subjects = new ArrayList<>();
    private SubjectManager subjectManager = SubjectManager.getInstance();

    public RVAdapterSubjectList(Context context,  ArrayList<Subject> subjects){
        this.context = context;
        this.subjects = subjects;
    }

    @Override
    public RecyclerVH onCreateViewHolder(ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_subject_list, parent, false);
        return new RecyclerVH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerVH holder, final int position) {
        holder.nameTxt.setText(subjects.get(position).getName());
        holder.cardView.setCardBackgroundColor(Util.getSubjectColor(context, subjects.get(position)));
        holder.nameTxt.setTextColor(context.getColor(android.R.color.white));
        holder.gradeTxt.setTextColor(context.getColor(android.R.color.white));
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                TextView subjectTextName = view.findViewById(R.id.item_subject_name);
                args.putSerializable("subject", subjects.get(position));
                ((MainActivity) context).setFragment(GradeChildFragment.TAG,args);
            }
        });
        holder.gradeTxt.setText( String.valueOf(subjects.get(position).getGradePointAverage()));
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    //Viewholder Class
    public class RecyclerVH extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView nameTxt;
        TextView gradeTxt;
        ConstraintLayout rl;

        public RecyclerVH(View itemView){
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);

            rl = itemView.findViewById(R.id.itme_subject_rl_woat_ever);
            nameTxt = itemView.findViewById(R.id.item_subject_name);
            gradeTxt = itemView.findViewById(R.id.item_subject_grade);
        }
    }
}
