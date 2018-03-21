package de.tk.annapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import de.tk.annapp.Fragments.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SubjectManager subjectManager;

    TextView textViewGrade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewGrade = (TextView) findViewById(R.id.grade);

        //Creates instance of SubjectManager
        subjectManager = SubjectManager.getInstance();
        subjectManager.setContext(this.getApplicationContext());

        subjectManager.setTextView(textViewGrade);

        subjectManager.load(this.getApplicationContext(), "subjects");
        subjectManager.setGradeTextView(false, null);

        //Add test subjects
        /*subjectManager.addSubject("Mathe", 1, "fdsh", "hjsr");
        subjectManager.addSubject("Deutsch", 1, "fdsh", "hjsr");
        subjectManager.addSubject("Latein", 1, "fdsh", "hjsr");
        subjectManager.addSubject("Englisch", 1, "fdsh", "hjsr");*/



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /*Default Fragment:*/ Fragment f = new mydayFragment();
        Bundle args = new Bundle();
        f.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, f)
                .commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Fragment myFragment = getFragmentManager().findFragmentByTag("GradeChildFragment");
        if (myFragment != null && myFragment.isVisible()) {

            Fragment fragment = new gradesFragment();

            Bundle args = new Bundle();
            fragment.setArguments(args);

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();

            drawer.closeDrawer(GravityCompat.START);
        }
        else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //the option menue on the top right
        int id = item.getItemId();

        //detecting which item was selected -> initiating inserting of the fragment
        if (id == R.id.action_settings) {
            onNavigationItemSelected(item);
        } else if (id==R.id.action_feedback) {
            onNavigationItemSelected(item);
        } else if (id==R.id.action_share){

            //sharing stuff
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
            i.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.pax.qbt.annapp");
            startActivity(Intent.createChooser(i, getString(R.string.shareAnnApp)));

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;


        //detects which item was selected -> initiating inserting of the fragment
        if (id == R.id.nav_myday) {
            fragment = new mydayFragment();
        } else if (id == R.id.nav_timetable) {
            fragment = new timetableFragment();
        } else if (id == R.id.nav_grades) {
            fragment = new gradesFragment();
        } else if (id == R.id.nav_tasks) {
            fragment = new tasksFragment();
        } else if (id == R.id.nav_calendar) {
            fragment = new calendarFragment();
        } else if (id == R.id.nav_privattuition) {
            fragment = new privatetuitionFragment();
        } else if (id == R.id.nav_saleofschoolsupplies) {
            fragment = new saleofschoolsuppliesFragment();
        } else if (id == R.id.nav_loststuff) {
            fragment = new loststuffFragment();
        } else if (id == R.id.nav_annanews) {
            fragment = new annanewsFragment();
        } else if (id == R.id.action_settings) {
            fragment = new settingsFragment();
        } else if (id == R.id.action_feedback) {
            fragment = new feedbackFragment();
        }

        if(id == R.id.nav_grades)
            subjectManager.setGradeTextView(true, null);
        else
            subjectManager.setGradeTextView(false,null);

        if (fragment == null) {
            System.out.println("Main Activity: Your button for the fragment has no fragment defined to put into the Layout");
            return false;
        }
        Bundle args = new Bundle();
        fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //onClickListener for the Subject Item
    //Opens the grades
    public void onClickRVItem(View view){
        Fragment fragment = new gradeChildFragment();

        Bundle args = new Bundle();
        TextView subjectTextName = view.findViewById(R.id.item_subject_name);
        System.out.print(subjectTextName.getText().toString());
        args.putString("subjectName", subjectTextName.getText().toString());

        subjectManager.setGradeTextView(true, subjectManager.getSubjectByName(subjectTextName.getText().toString()));

        fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment, "GradeChildFragment")
                .commit();
    }


}
