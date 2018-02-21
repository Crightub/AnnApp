package de.tk.annapp.TableView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.evrencoskun.tableview.listener.ITableViewListener;

import de.tk.annapp.Lesson;
import de.tk.annapp.TimetableManager;

public class MyTableViewListener implements ITableViewListener {

    private TimetableManager timetableManager = TimetableManager.getInstance();

    @Override
    public void onCellClicked(@NonNull RecyclerView.ViewHolder cellView, int columnPosition, int
            rowPosition) {
        System.out.println("Cell: " + String.valueOf(columnPosition) + " - " + String.valueOf(rowPosition));

    }

    @Override
    public void onColumnHeaderClicked(@NonNull RecyclerView.ViewHolder columnHeaderView, int
            columnPosition) {
        // Do what you want.
    }

    @Override
    public void onColumnHeaderLongPressed(@NonNull RecyclerView.ViewHolder columnHeaderView, int
            columnPosition) {
        // Do what you want.
    }

    @Override
    public void onRowHeaderClicked(@NonNull RecyclerView.ViewHolder rowHeaderView, int
            rowPosition) {
        // Do what you want.

    }


    @Override
    public void onRowHeaderLongPressed(@NonNull RecyclerView.ViewHolder rowHeaderView, int
            rowPosition) {
        // Do what you want.

    }

}
