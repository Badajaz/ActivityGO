package com.example.android.activitygo;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MenuPrincipal extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private Fragment SelectedFragment;
    private Toolbar myToolbar;
    private Toolbar toolbarCima;
    private ArrayList<String> profile;
    private String selectedSport;
    private Dialog myDialog;
    private CheckBox caminhadaCheckBox;
    private CheckBox corridaCheckBox;
    private int corridaChecked = 0;
    private int caminhadaChecked = 0;
    private int futebolChecked = 0;
    private int ciclismoChecked = 0;
    private ArrayList<CheckBox> checkboxesPopup = new ArrayList<CheckBox>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        myDialog = new Dialog(this);
        profile = (ArrayList<String>) getIntent().getSerializableExtra("USERPROFILE");
        selectedSport = "";

        // mostra popup inicial para a pessoa escolher o desporto
        // showPopup();

        Bundle toRunMenuInicial = new Bundle();
        toRunMenuInicial.putStringArrayList("USERPROFILE", profile);
        SelectedFragment = new RunMenuInicial();
        SelectedFragment.setArguments(toRunMenuInicial);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container, SelectedFragment, "RunFragment");
        //ft.addToBackStack("RunFragment");
        ft.commit();

        toolbarCima = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbarCima);
        getSupportActionBar().setTitle("ActivityGO");
        String iniciais = "" + profile.get(0).charAt(0) + profile.get(1).charAt(0);
        getSupportActionBar().setSubtitle(iniciais);

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
                            //Fragmento Corrida
                            Bundle toRunMenuInicial = new Bundle();
                            toRunMenuInicial.putStringArrayList("USERPROFILE", profile);
                            SelectedFragment = new RunMenuInicial();
                            SelectedFragment.setArguments(toRunMenuInicial);
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.fragment_container, SelectedFragment);
                            ft.addToBackStack("RunFragment");
                            ft.commit();
                            break;
                        case R.id.chalengeItem:
                            //Fragmento Desafios
                            menuItem.setIcon(R.drawable.bicepe_icon23);
                            SelectedFragment = new ChalengeFragment();
                            FragmentManager fman = getFragmentManager();
                            FragmentTransaction ftra = fman.beginTransaction();
                            ftra.replace(R.id.fragment_container, SelectedFragment, "ChalengeFragment");
                            ftra.commit();
                            break;

                        case R.id.achievementItem:
                            // chamar tab das achievements
                            //menuItem.setIcon(R.drawable.trophy_icon222black);
                            Intent intent = new Intent(getApplicationContext(), AchievementsTab.class);
                            startActivity(intent);

                        case R.id.RankingItem:
                            //Fragmento Ranking
                            SelectedFragment = new RankingsFragment();
                            FragmentManager fmanag = getFragmentManager();
                            menuItem.setIcon(R.drawable.trophy_icon333);
                            FragmentTransaction ftransactio = fmanag.beginTransaction();
                            ftransactio.replace(R.id.fragment_container, SelectedFragment, "Ranking Item");
                            ftransactio.commit();
                            break;

                        case R.id.GroupsItem:
                            /*SelectedFragment = new GroupFragment();
                            FragmentManager fmana = getFragmentManager();
                            FragmentTransaction ftransacti = fmana.beginTransaction();
                            ftransacti.replace(R.id.fragment_container, SelectedFragment, "GroupFragment");
                            ftransacti.commit();
                            break;*/
                            SelectedFragment = new MergeGroupFragment();
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
                //Bundle bundle = new Bundle();
                //bundle.putString("FIRSTNAME", profile.get(0));
                //bundle.putString("LASTNAME", profile.get(1));
                //ChangeProfileFragment cpf = new ChangeProfileFragment();
                //bundle.putStringArrayList("USERPROFILE", profile);
                //bundle.putSerializable("USERPROFILE", profile);
                //cpf.setArguments(bundle);

                Intent i = new Intent(this, SettingsActivity.class);
                i.putExtra("USERPROFILE", profile);
                startActivity(i);
                break;

            case R.id.BackButton:
                getFragmentManager().popBackStack();
        }
        return true;
    }

    public ArrayList<String> getProfile() {
        return this.profile;
    }

    public void showPopup() {
        TextView txtclose;
        TextView txtname;
        TextView txtusername;
        Button confirmButton;

        myDialog.setContentView(R.layout.popup_inicial);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclosePopupInicial);
        txtname = (TextView) myDialog.findViewById(R.id.idName);
        txtusername = (TextView) myDialog.findViewById(R.id.idUsername);
        caminhadaCheckBox = (CheckBox) myDialog.findViewById(R.id.opcaoPraticar1);
        corridaCheckBox = (CheckBox) myDialog.findViewById(R.id.opcaoPraticar2);
        confirmButton = (Button) myDialog.findViewById(R.id.confirmButton);
        txtname.setText(profile.get(0));
        txtusername.setText(profile.get(2));
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void getCorridaItemPopup(View v) {
        corridaChecked = 1;
        checkboxesPopup.add(corridaCheckBox);

        //desporto que a pessoa irá praticar
        selectedSport = corridaCheckBox.getText().toString();
    }

    public void getCaminhadaItemPopup(View v) {
        caminhadaChecked = 1;
        checkboxesPopup.add(caminhadaCheckBox);

        //desporto que a pessoa irá praticar
        selectedSport = caminhadaCheckBox.getText().toString();
    }
}