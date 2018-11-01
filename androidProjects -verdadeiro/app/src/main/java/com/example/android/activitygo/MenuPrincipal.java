package com.example.android.activitygo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MenuPrincipal extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private Fragment SelectedFragment;
    private Toolbar myToolbar;
    private Toolbar toolbarCima;
    private ArrayList<String> profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        profile = (ArrayList<String>) getIntent().getSerializableExtra("USERPROFILE");
        if (profile != null) {
            Toast.makeText(getApplicationContext(), "PROFILE != NULL", Toast.LENGTH_LONG).show();
        }

        SelectedFragment = new RunMenuInicial();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container, SelectedFragment, "RunFragment");
        //ft.addToBackStack("RunFragment");
        ft.commit();

        toolbarCima = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbarCima);
        getSupportActionBar().setTitle("ActivityGO");


        /*
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        */
        BottomNavigationView mMainNav = findViewById(R.id.NavBar);
        mMainNav.setOnNavigationItemSelectedListener(navListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment SelectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.runIntem:
                            SelectedFragment = new RunMenuInicial();
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.fragment_container, SelectedFragment);
                            ft.commit();
                            break;
                        case R.id.chalengeItem:
                            SelectedFragment = new ChalengeFragment();
                            FragmentManager fman = getFragmentManager();
                            FragmentTransaction ftra = fman.beginTransaction();
                            ftra.replace(R.id.fragment_container, SelectedFragment, "ChalengeFragment");
                            ftra.commit();
                            break;

                        case R.id.achievementItem:
                            SelectedFragment = new AchievementsFragment();
                            FragmentManager fmanager = getFragmentManager();
                            FragmentTransaction ftransaction = fmanager.beginTransaction();
                            ftransaction.replace(R.id.fragment_container, SelectedFragment);
                            ftransaction.commit();
                            break;

                        case R.id.RankingItem:
                            SelectedFragment = new RankingsFragment();
                            FragmentManager fmanag = getFragmentManager();
                            FragmentTransaction ftransactio = fmanag.beginTransaction();
                            ftransactio.replace(R.id.fragment_container, SelectedFragment, "Ranking Item");
                            ftransactio.commit();
                            break;

                        case R.id.GroupsItem:
                            SelectedFragment = new GroupFragment();
                            FragmentManager fmana = getFragmentManager();
                            FragmentTransaction ftransacti = fmana.beginTransaction();
                            ftransacti.replace(R.id.fragment_container, SelectedFragment, "GroupFragment");
                            ftransacti.commit();
                            break;

                    }
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_container, SelectedFragment);

                    return true;
                }
            };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Fragment currentFragment = getFragmentManager().findFragmentById(R.id.fragment_container);
        // Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + ViewPager.getCurrentItem());

        if (currentFragment.isVisible() && (currentFragment instanceof RunMenuInicial)) {
            //MenuItem item = menu.findItem(R.id.BackButton);
            //item.setVisible(false);
        }


        // Fragment whichFragment=getVisibleFragment();
        /*
        if(whichFragment.getTag().toString().equals("RunFragment")){
            MenuItem item = menu.findItem(R.id.BackButton);
            item.setVisible(false);
        }else{
            MenuItem item = menu.findItem(R.id.BackButton);
            item.setVisible(true);
        }*/

        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private Fragment getVisibleFragment() {
        FragmentManager fmana = getFragmentManager();
        //FragmentManager fragmentManager = MenuPrincipal.this.getSupportFragmentManager();
        List<Fragment> fragments = fmana.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Settings:
                SelectedFragment = new SettingsFragment();
                FragmentManager fmSettings = getFragmentManager();
                FragmentTransaction ftSettings = fmSettings.beginTransaction();
                ftSettings.replace(R.id.fragment_container, SelectedFragment);
                ftSettings.commit();

                Bundle bundle = new Bundle();
                bundle.putString("FIRSTNAME", profile.get(0));
                bundle.putString("LASTNAME", profile.get(1));
                ChangeProfileFragment cpf = new ChangeProfileFragment();
                // bundle.putStringArrayList("USERPROFILE", profile);
                //bundle.putSerializable("USERPROFILE", profile);
                cpf.setArguments(bundle);

                break;

            case R.id.BackButton:
                getFragmentManager().popBackStack();
        }
        return true;
    }

    public ArrayList<String> getProfile(){
        return this.profile;
    }
}
