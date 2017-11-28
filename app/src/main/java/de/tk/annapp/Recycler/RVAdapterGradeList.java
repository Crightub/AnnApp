package de.tk.annapp.Recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

import de.tk.annapp.R;
import de.tk.annapp.SubjectManager;
import de.tk.annapp.Grade;

public class RVAdapterGradeList extends RecyclerView.Adapter<RVAdapterGradeList.RecyclerVH> {

    Context c;
    private ArrayList<Grade> grades = new ArrayList<>();
    private SubjectManager subjectManager;
    private String subjectName;

    public RVAdapterGradeList(Context _c, String _subjectName){
        c = _c;
        subjectManager = SubjectManager.getInstance();
        subjectName = _subjectName;
        grades = subjectManager.getSubjectByName(_subjectName).getAllGrades();
    }

    @Override
    public RecyclerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_grade_list, parent, false);
        return new RecyclerVH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerVH holder, final int position) {
        holder.gradeTxt.setText("" + grades.get(position).grade);
        holder.expandableTextView.setText(grades.get(position).note + "\nWertung: " + grades.get(position).rating);

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Create InputDialog...");
                //TODO Create InputDialog
            }
        });
    }

    @Override
    public int getItemCount() {
        return grades.size();
    }

    //Viewholder Class
    public class RecyclerVH extends RecyclerView.ViewHolder{
        TextView gradeTxt;
        ExpandableTextView expandableTextView;
        ImageButton editButton;

        public RecyclerVH(View itemView){
            super(itemView);
            editButton = itemView.findViewById(R.id.item_grade_deleteButton);
            expandableTextView = itemView.findViewById(R.id.expandable_text_view);
            gradeTxt = itemView.findViewById(R.id.item_grade_grade);
        }
    }

}
