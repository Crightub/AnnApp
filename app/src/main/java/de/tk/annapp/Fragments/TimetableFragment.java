package de.tk.annapp.Fragments;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.INotificationSideChannel;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.app.Fragment;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.tk.annapp.Day;
import de.tk.annapp.Lesson;
import de.tk.annapp.R;
import de.tk.annapp.SubjectManager;
import de.tk.annapp.TableView.MyTableViewListener;
import de.tk.annapp.TableView.TVAdapterTimetable;
import de.tk.annapp.TableView.model.Cell;
import de.tk.annapp.TableView.model.ColumnHeader;
import de.tk.annapp.TableView.model.RowHeader;
import de.tk.annapp.TimetableManager;
import de.tk.annapp.Util;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimetableFragment extends Fragment {

    //public static final int COLUMN_SIZE = 5;
    //public static final int ROW_SIZE = 11;

    private List<RowHeader> mRowHeaderList;
    private List<ColumnHeader> mColumnHeaderList;
    private List<List<Cell>> mCellList;

    //private AbstractTableAdapter mTableViewAdapter;
    //private TableView mTableView;

    TableLayout tableLayout;

    TimetableManager timetableManager;
    SubjectManager subjectManager;

    public TimetableFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRowSize();
        //initData();
    }

    void setRowSize(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        getActivity().setTitle("Stundenplan");
        View root = inflater.inflate(R.layout.fragment_timetable, container, false);

        timetableManager = TimetableManager.getInstance();
        subjectManager = SubjectManager.getInstance();

        //RelativeLayout fragment_container = root.findViewById(R.id.fragment_container);

        tableLayout = root.findViewById(R.id.tableLayout);

        initializeTableView();

        // Create Table view
        //mTableView = createTableView();
        //fragment_container.addView(mTableView);

        //loadData();
        return root;
    }



    void initializeTableView(){

        //default spacing
        int spacing = 10;
        timetableManager.load(getContext(), "timetable");

        /*timetableManager.setLesson(subjectManager.getSubjectByName("Mathe"), "E 201", 11, 4);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 203",1,0);
        timetableManager.setLesson(subjectManager.getSubjectByName("Mathe"), "E 203",2,0);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 203",3,0);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 203",4,0);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 203",5,0);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 203",6,0);

        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 213",1,1);
        timetableManager.setLesson(subjectManager.getSubjectByName("Mathe"), "E 213",2,1);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 213",3,1);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 213",4,1);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 213",5,1);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 213",6,1);

        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 223",1,2);
        timetableManager.setLesson(subjectManager.getSubjectByName("Mathe"), "E 223",2,2);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 223",3,2);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 223",4,2);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 223",5,2);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 223",6,2);

        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 233",1,3);
        timetableManager.setLesson(subjectManager.getSubjectByName("Mathe"), "E 233",2,3);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 233",3,3);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 233",4,3);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 233",5,3);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 233",6,3);

        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 243",1,4);
        timetableManager.setLesson(subjectManager.getSubjectByName("Mathe"), "E 243",2,4);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 243",3,4);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 243",4,4);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 243",5,4);
        timetableManager.setLesson(subjectManager.getSubjectByName("Deutsch"), "E 243",6,4);*/


        int accentColor = (new Util()).getAccentColor(getContext());

        for(int i = 0; i<(getLongestDaysNumberOfLessons()); i++){
            TableRow tableRow = new TableRow(this.getContext());


            if(i==0){
                Button b = getHeaderButton(accentColor);
                tableRow.addView(b);
                Space spa = new Space(this.getContext());
                spa.setMinimumWidth(spacing);
                tableRow.addView(spa);

                //TODO Register Rowheaders
                int f = 0;
                for (Day d :
                        timetableManager.getDays()) {
                    Button btn = getHeaderButton(accentColor);

                    switch (f){
                        case (0):
                            btn.setText("Montag");
                            break;
                        case (1) :
                            btn.setText("Dienstag");
                            break;
                        case (2):
                            btn.setText("Mittwoch");
                            break;
                        case (3) :
                            btn.setText("Donnerstag");
                            break;
                        case (4):
                            btn.setText("Freitag");
                            break;
                        default:
                            btn.setText("Unnamed Day");
                            break;
                    }
                    f++;



                    tableRow.addView(btn);

                    //horizontal space between buttons
                    Space space = new Space(this.getContext());
                    space.setMinimumWidth(spacing);
                    space.setMinimumHeight(spacing);
                    tableRow.addView(space);

                }
            }else{
                //TODO Register Columnheaders and Cells

                //vertical space between buttons
                Space sp = new Space(this.getContext());
                sp.setMinimumHeight(spacing);
                TableRow t = new TableRow(this.getContext());
                t.addView(sp);
                tableLayout.addView(t);

                //add row header
                Button btn = getHeaderButton(accentColor);
                btn.setText(i +". Stunde");
                tableRow.addView(btn);

                for (Day d :
                        timetableManager.getDays()) {

                    String cellName;

                    View cell;
                    try {
                        cellName = d.lessons.get(i).subject.getName();
                        //add cell
                        cell = getCellButton(accentColor);
                        ((Button)cell).setText(cellName);
                    } catch (Exception e){
                        cell = new Space(this.getContext());
                        cell.setMinimumWidth(spacing);
                        System.out.println(e);
                    }

                    //add spacing
                    Space v = new Space(this.getContext());
                    v.setMinimumWidth(spacing);
                    tableRow.addView(v);

                    tableRow.addView(cell);
                }

            }


            tableLayout.addView(tableRow);
        }


    }

    Button getHeaderButton(int accentColor){
        Button btn = new Button(this.getContext());

        //general Settings for headers
        btn.setTextColor(getResources().getColor(R.color.bg_line));
        btn.setBackgroundColor(accentColor);
        btn.setTypeface(null,Typeface.BOLD);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Clicked ", Toast.LENGTH_SHORT).show();
            }
        });

        return btn;
    }

    Button getCellButton(int accentColor){
        Button btn = new Button(this.getContext());

        //general Settings for Cells
        btn.setTextColor(getResources().getColor(R.color.bg_line));

        btn.setBackgroundColor(accentColor);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Clicked ", Toast.LENGTH_SHORT).show();
            }
        });

        return btn;
    }

    int getLongestDaysNumberOfLessons(){
        ArrayList<Day> days = timetableManager.getDays();
        int x = 0;
        for (Day d : days) {
            int i=0;
            for (Lesson l : d.lessons)
                i++;
            if(i>x)
                x=i;
        }
        return x;
    }

/*
    private TableView createTableView() {
        TableView tableView = new TableView(getContext());

        // Set adapter
        mTableViewAdapter = new TVAdapterTimetable(getContext());
        tableView.setAdapter(mTableViewAdapter);

        // Set layout params
        FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams
                .MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        tableView.setLayoutParams(tlp);

        // Set TableView listener
        tableView.setTableViewListener(new MyTableViewListener(tableView));
        return tableView;
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
        List<List<Cell>> cellList = getCellListForSortingTest(); // getCellList();
        List<ColumnHeader> columnHeaders = getColumnHeaderList(); //getRandomColumnHeaderList(); //

        mRowHeaderList.addAll(rowHeaders);
        for (int i = 0; i < cellList.size(); i++) {
            mCellList.get(i).addAll(cellList.get(i));
        }

        // Load all data
        mColumnHeaderList.addAll(columnHeaders);
        mTableViewAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);

    }

    private List<RowHeader> getRowHeaderList() {
        List<RowHeader> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            RowHeader header = new RowHeader(String.valueOf(i), (i+1) + ". Stunde");
            list.add(header);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */      /*
    public static List<RowHeader> getRowHeaderList(int startIndex) {
        List<RowHeader> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            RowHeader header = new RowHeader(String.valueOf(i), "row " + (startIndex + i));
            list.add(header);
        }

        return list;
    }


    private List<ColumnHeader> getColumnHeaderList() {
        List<ColumnHeader> list = new ArrayList<>();

        for (int i = 0; i < COLUMN_SIZE; i++) {
            String title;
            if(i==0)
                title = "Montag";
            else if(i==1)
                title = "Dienstag";
            else if(i==2)
                title = "Mittwoch";
            else if(i==3)
                title = "Donnerstag";
            else if(i==4)
                title = "Freitag";
            else
                title = "undefined Day";


            ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
            list.add(header);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */    /*
    private List<ColumnHeader> getRandomColumnHeaderList() {
        List<ColumnHeader> list = new ArrayList<>();

        for (int i = 0; i < COLUMN_SIZE; i++) {
            String title = "column " + i;
            int nRandom = new Random().nextInt();
            if (nRandom % 4 == 0 || nRandom % 3 == 0 || nRandom == i) {
                title = "large column " + i;
            }

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
                String text = "cell " + j + " " + i;
                if (j % 4 == 0 && i % 5 == 0) {
                    text = "large cell " + j + " " + i + ".";
                }
                String id = j + "-" + i;

                Cell cell = new Cell(id, text);
                cellList.add(cell);
            }
            list.add(cellList);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */    /*
    private List<List<Cell>> getCellListForSortingTest() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < COLUMN_SIZE; j++) {
                Object text = "cell " + j + " " + i;

                if (j == 0) {
                    text = i;
                } else if (j == 1) {
                    int random = new Random().nextInt();
                    text = random;
                }

                // Create dummy id.
                String id = j + "-" + i;

                Cell cell = new Cell(id, text);
                cellList.add(cell);
            }
            list.add(cellList);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */   /*
    private List<List<Cell>> getRandomCellList() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            list.add(cellList);
            for (int j = 0; j < COLUMN_SIZE; j++) {
                String text = "cell " + j + " " + i;
                int random = new Random().nextInt();
                if (random % 2 == 0 || random % 5 == 0 || random == j) {
                    text = "large cell  " + j + " " + i + getRandomString() + ".";
                }

                // Create dummy id.
                String id = j + "-" + i;

                Cell cell = new Cell(id, text);
                cellList.add(cell);
            }
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */    /*
    public static List<List<Cell>> getRandomCellList(int startIndex) {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            list.add(cellList);
            for (int j = 0; j < COLUMN_SIZE; j++) {
                String text = "cell " + j + " " + (i + startIndex);
                int random = new Random().nextInt();
                if (random % 2 == 0 || random % 5 == 0 || random == j) {
                    text = "large cell  " + j + " " + (i + startIndex) + getRandomString() + ".";
                }

                String id = j + "-" + (i + startIndex);

                Cell cell = new Cell(id, text);
                cellList.add(cell);
            }
        }

        return list;
    }*/


    private static String getRandomString() {
        Random r = new Random();
        String str = " a ";
        for (int i = 0; i < r.nextInt(); i++) {
            str = str + " a ";
        }

        return str;
    }
}

