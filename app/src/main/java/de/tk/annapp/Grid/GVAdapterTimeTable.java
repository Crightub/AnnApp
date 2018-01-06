package de.tk.annapp.Grid;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;

import de.tk.annapp.Day;
import de.tk.annapp.Lesson;
import de.tk.annapp.R;
import de.tk.annapp.Subject;
import de.tk.annapp.TimetableManager;

public class GVAdapterTimeTable extends BaseAdapter{

    TimetableManager timetableManager = TimetableManager.getInstance();
    ArrayList<Day> Days;
    Context c;
    private int maxColumns = 5;

    public GVAdapterTimeTable(Context c){
        this.c = c;
        //Get Days
        Days = timetableManager.getDays();
    }

    @Override
    public int getCount() {
        //return the amount of fields;
        //5 columns
        //12 rows
        return 5*12;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(c);
        View customView = layoutInflater.inflate(R.layout.item_timetable_grid, parent, false);
        TextView textView = customView.findViewById(R.id.item_timetable_tv);

        //Set the text to the subject name
        if(position < maxColumns){
            textView.setText(Days.get(position).lessons.get(0).subject.name);
        }else {
            textView.setText(Days.get(position % maxColumns).lessons.get(mod(position, maxColumns)).subject.name);
        }

        return customView;
    }

    private int mod(int x, int y)
    {
        int result = x % y;
        return result < 0? result + y : result;
    }

}


