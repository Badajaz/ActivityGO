package com.example.android.activitygo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.Toast;

public class MenuPrincipal extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private Fragment SelectedFragment;
    private Toolbar myToolbar;
    private Toolbar toolbarCima;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        SelectedFragment = new RunMenuInicial();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container, SelectedFragment,"RunFragment");
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
                            ftra.replace(R.id.fragment_container, SelectedFragment);
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
                            ftransactio.replace(R.id.fragment_container, SelectedFragment);
                            ftransactio.commit();
                            break;

                        case R.id.GroupsItem:
                            SelectedFragment = new GroupFragment();
                            FragmentManager fmana = getFragmentManager();
                            FragmentTransaction ftransacti = fmana.beginTransaction();
                            ftransacti.replace(R.id.fragment_container, SelectedFragment);
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
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.Settings:
                SelectedFragment = new SettingsFragment();
                FragmentManager fmSettings = getFragmentManager();
                FragmentTransaction ftSettings = fmSettings.beginTransaction();
                ftSettings.replace(R.id.fragment_container, SelectedFragment);
                ftSettings.commit();
                break;

            case R.id.BackButton:
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        return true;
    }
}
