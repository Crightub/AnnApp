package de.tk.annapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import de.tk.annapp.Fragments.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SubjectManager subjectManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.AppTheme_green);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Creates instance of SubjectManager
        subjectManager = SubjectManager.getInstance();
        subjectManager.setContext(this.getApplicationContext());
        subjectManager.setActivity(MainActivity.this);
        subjectManager.setSchoolLessonSystem(null);

        subjectManager.load();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /*Default Fragment:*/
        Fragment f = new HomeFragment();
        Bundle args = new Bundle();
        f.setArguments(args);

        //Set Default ColorScheme for Timetable
        try {
            Object obj;
            ObjectInputStream ois = new ObjectInputStream(this.openFileInput("colorSchemePosition"));
            obj = ois.readObject();
            ois.close();
        } catch (Exception e) {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(this.openFileOutput("colorSchemePosition", Context.MODE_PRIVATE));
                oos.writeObject(0);
                oos.close();
            } catch (IOException o) {
                o.printStackTrace();
            }
        }

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

            Fragment fragment = new GradesFragment();

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


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;


        //detects which item was selected -> initiating inserting of the fragment
        if (id == R.id.nav_myday) {
            fragment = new HomeFragment();
        } else if (id == R.id.nav_timetable) {
            fragment = new TimetableFragment();
        } else if (id == R.id.nav_grades) {
            fragment = new GradesFragment();
        } else if (id == R.id.nav_tasks) {
            fragment = new TasksFragment();
        } else if (id == R.id.nav_calendar) {
            fragment = new CalendarFragment();
        } else if (id == R.id.nav_privattuition) {
            fragment = new PrivateTuitionFragment();
        } else if (id == R.id.nav_saleofschoolsupplies) {
            fragment = new SaleOfSchoolSuppliesFragment();
        } else if (id == R.id.nav_loststuff) {
            fragment = new LostStuffFragment();
        } else if (id == R.id.nav_annanews) {
            fragment = new AnnanewsFragment();
        } else if (id == R.id.nav_settings) {
            fragment = new SettingsFragment();
        } else if (id == R.id.nav_feedback) {
            fragment = new FeedbackFragment();
        } else if (id == R.id.nav_share){
            //sharing stuff
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
            i.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.pax.qbt.annapp");
            startActivity(Intent.createChooser(i, getString(R.string.shareAnnApp)));
            return true;
        }

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



}
