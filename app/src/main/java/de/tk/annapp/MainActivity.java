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

import de.tk.annapp.Fragments.annanewsFragment;
import de.tk.annapp.Fragments.calendarFragment;
import de.tk.annapp.Fragments.feedbackFragment;
import de.tk.annapp.Fragments.gradeChildFragment;
import de.tk.annapp.Fragments.gradesFragment;
import de.tk.annapp.Fragments.loststuffFragment;
import de.tk.annapp.Fragments.mydayFragment;
import de.tk.annapp.Fragments.privatetuitionFragment;
import de.tk.annapp.Fragments.saleofschoolsuppliesFragment;
import de.tk.annapp.Fragments.settingsFragment;
import de.tk.annapp.Fragments.tasksFragment;
import de.tk.annapp.Fragments.timetableFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SubjectManager subjectManager = new SubjectManager();

    /*

                               :oooooooooo:
                        ::oOOOOooo:::::ooooO88Oo:
                     :oOOo:                   :oO8O:
                  :O8O:                           oO8o
                O8O:                                 :OO:
              o8o                                      :8o
             88:                                         O8:
           o8o                                            :8o
          8O:                                               oO
         8o                                                  oO
        8o                                                    Oo
      :8o                                              :      :8:
     :8:                                               :       :8:
    :8:                                               :o        oO
    O::o                                              oo         8:
   :o:ooO                                             oo         o8
   8    oo                                            :o          8o
  oo     8o                                            OO:        :8
  8      o8:                                            O8O:       8:
 :o      :88                                             :8O       :O
 o:     ::88:                                             O8        8
 8       888:                                             O8o       O
:8       :88:                                             888Ooo:   oo
oo       oO8:                                             888888o   :O
Oo       O88           :oO88O:            oO88Oo:         O8888O    :O
O:  ::   :88      :oO888888888:          8888888888OOo:    8888:    :O
O:  :o    8o   o88888888888888:          88888888888888O:   o88     :O
Oo   oo  oO  o888888888888888O           8888888888888888o   88     :O
:O    O: oo  8888888888888888:          o88888888888888888   88     :O
 8    :o:8:  8888888888888888:::         :8888888888888888  o888o:::8O
 O:   :88:   O88888888888888888           O888888888888888  o88888888O
  o   :8O    o888888888888888O            O88888888888888O   88888888o
  O:  :8:    88888888888888O:      :      O888888888888888   O8888888:
  :O  :8     88888888888888       o88:    :o88888888888888    8888888
   OoooO    :8888888888O:8o      :8O88       :O8888888888o     O888oo
    888:     8888888o:  8O       :8o88O         O888888O:          :
    :88       oOOo:   :O:        :8:888          8o
     88              o:          88oO88o      :: :oo            :
     88::               oo      O88O:888o    O8o:   :   ::     : :
     8o o:                     o88oo O888    :8        :88   :: ::
     oO  oo     o              O8o : O888     :o       oO8o ::  O
      O:  Oo  ::o              OO    OO :             :oO88 O::O:
       O  :8: :88o             OO    Oo             :O888O  8OO:
       oo  Oo  O888o           :O:o:  :            o8888o  :O
        :O888  :88888           :o  Oo            o8888o   8:
           :8:  O8888               8            :O888O   :8
            Oo  :8888               o             :888    :O
            :O   o888  ::                         o88:    Oo
            :O    O88OO8oOOoOOoOooOooOOOOOOOOOO88O88O     O:
            OO     888:: o :o: o  o   O: 8: O o::o 8:     8
           :8O     :88o:  :    :  :      ::        8      O
           O8o      Oo:8oo:    :          :   :::oo8     :O
           88o      Oo O :ooOOOoOO:OO   :8O:O O  o O     :O
           88o      Oo :      o:o: :o    Oo : :  o       :O
           88O       :oo:oo:o o :  :: oo :: o O::o       o:
           888         :::::oO8O8OoO8O88oOOoo:         :Oo
            O88Oo::               ::                  :O
             :oO8888o                               :Oo
                 :o88o                      oo    o8O:
                    O8o                     Oo  o88o
                     o8O     Oo    o           o88
-hrr-                 :88o        O:          O88
                        O88o     :8o        o88O
                          o88ooOO888O::::oO88O:
                            o8888888888888Oo
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
        if (drawer.isDrawerOpen(GravityCompat.START)) {
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
            startActivity(Intent.createChooser(i, "AnnAppp teilen..."));

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

    public void onClickRVItem(View view){
        Fragment fragment = new gradeChildFragment();

        Bundle args = new Bundle();
        TextView subjectTextName = view.findViewById(R.id.item_subject_name);
        System.out.print(subjectTextName.getText().toString());
        args.putString("subjectName", subjectTextName.getText().toString());

        fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }
}
