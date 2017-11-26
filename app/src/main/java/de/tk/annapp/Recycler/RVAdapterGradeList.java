package de.tk.annapp.Recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

import de.tk.annapp.R;
import de.tk.annapp.grade;

public class RVAdapterGradeList extends RecyclerView.Adapter<RVAdapterGradeList.RecyclerVH> {

    Context c;
    private ArrayList<grade> grades = new ArrayList<>();

    public RVAdapterGradeList(Context _c, ArrayList<grade> _grades){
        c = _c;
        grades = _grades;
    }

    @Override
    public RecyclerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_grade_list, parent, false);
        return new RecyclerVH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerVH holder, int position) {
        holder.gradeTxt.setText("" + grades.get(position).grade);
        holder.expandableTextView.setText(grades.get(position).note + "\nWertung: " + grades.get(position).rating);
    }

    @Override
    public int getItemCount() {
        return grades.size();
    }

    //Viewholder Class
    public class RecyclerVH extends RecyclerView.ViewHolder{
        TextView gradeTxt;
        ExpandableTextView expandableTextView;

        public RecyclerVH(View itemView){
            super(itemView);

            expandableTextView = itemView.findViewById(R.id.expandable_text_view);
            gradeTxt = itemView.findViewById(R.id.item_grade_grade);
        }
    }
}
