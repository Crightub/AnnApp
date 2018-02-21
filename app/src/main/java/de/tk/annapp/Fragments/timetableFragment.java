package de.tk.annapp.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;

import java.util.ArrayList;
import java.util.List;

import de.tk.annapp.Day;
import de.tk.annapp.R;
import de.tk.annapp.SubjectManager;
import de.tk.annapp.TableView.MyTableViewListener;
import de.tk.annapp.TableView.TVAdapterTimetable;
import de.tk.annapp.TableView.model.Cell;
import de.tk.annapp.TableView.model.ColumnHeader;
import de.tk.annapp.TableView.model.RowHeader;
import de.tk.annapp.TimetableManager;

public class timetableFragment extends Fragment  {
    View root;
    TimetableManager timetableManager;
    ArrayList<Day> days = new ArrayList<>();

    TableView tableView;

    private List<RowHeader> mRowHeaderList;
    private List<ColumnHeader> mColumnHeaderList;
    private List<List<Cell>> mCellList;

    private AbstractTableAdapter mTableViewAdapter;

    public static final int COLUMN_SIZE = 5;
    public static final int ROW_SIZE = 10;

    private String[] nameDays = {"Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag"};
    private String[] nameLessons= {"8.00 - 8.45", "8.45 - 9.30", "9.45 - 10.30", "10.30 - 11.15", "11.30 - 12.15", "12.15 - 13.00", "13.00 - 13.45", "13.45 - 14.30", "14.30 - 15.15", "15.25 - 16.10", "16.10 - 16.55"};


    public timetableFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Get Singelton subjectManager
        timetableManager = TimetableManager.getInstance();

        days = timetableManager.getDays();

        getActivity().setTitle("Stundenplan");
        root = inflater.inflate(R.layout.fragment_timetable, container, false);

        tableView = root.findViewById(R.id.timetable_tableview);
        mTableViewAdapter = new TVAdapterTimetable(getContext());
        tableView.setAdapter(mTableViewAdapter);

        tableView.setTableViewListener(new MyTableViewListener());

        loadData();

        /*
        Button btnAddSubject = (Button) root.findViewById(R.id.btnAddSubject);
        final EditText subjectName = (EditText) root.findViewById(R.id.subjectName);
        final EditText subjectRating = (EditText) root.findViewById(R.id.subjectRating);

        btnAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(subjectName.getText().toString().isEmpty() || subjectRating.getText().toString().isEmpty()){
                    Toast.makeText(view.getContext(), "Alle Felder ausfüllen!", Toast.LENGTH_LONG).show();
                    return;
                }


                subjectManager.addSubject(subjectName.getText().toString(), Integer.parseInt(subjectRating.getText().toString()), null, null);
                subjectName.setText("");
                subjectRating.setText("");
                subjectManager.save(getContext(), "subjects");
                Toast.makeText(getContext(), "Fach erfolgreich hinzugefügt!", Toast.LENGTH_SHORT).show();
            }
        });
        */

        return root;
    }

    private void initData() {
        mRowHeaderList = new ArrayList<>();
        mColumnHeaderList = new ArrayList<>();
        mCellList = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            mCellList.add(new ArrayList<Cell>());
        }
    }

    private void loadData() {
        List<RowHeader> rowHeaders = getRowHeaderList();
        List<List<Cell>> cellList = getCellList();
        List<ColumnHeader> columnHeaders = getColumnHeaderList();

        mRowHeaderList.addAll(rowHeaders);
        for (int i = 0; i < cellList.size(); i++) {
            System.out.println();
            mCellList.get(i).addAll(cellList.get(i));
        }

        // Load all data
        mColumnHeaderList.addAll(columnHeaders);
        mTableViewAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);

    }

    private List<RowHeader> getRowHeaderList() {
        List<RowHeader> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            String title = nameLessons[i];
            RowHeader header = new RowHeader(String.valueOf(i), title);
            list.add(header);
        }

        return list;
    }


    private List<ColumnHeader> getColumnHeaderList() {
        List<ColumnHeader> list = new ArrayList<>();

        for(int i = 0; i < nameDays.length; i++){
            String title = nameDays[i];
            ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
            list.add(header);
        }

        return list;
    }

    private List<List<Cell>> getCellList() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < COLUMN_SIZE; j++) {
                String text;
                try{
                   text = days.get(j).lessons.get(i).subject.name;
                }catch (Exception e){
                    text = "";
                }
                String id = j + " - " + i;

                Cell cell = new Cell(id, text);
                cellList.add(cell);
            }
            list.add(cellList);
        }

        return list;
    }
}
