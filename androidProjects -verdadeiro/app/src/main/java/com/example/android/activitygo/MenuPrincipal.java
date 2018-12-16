package com.example.android.activitygo;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuPrincipal extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private Fragment SelectedFragment;
    private Toolbar myToolbar;
    private Toolbar toolbarCima;
    private String selectedSport;
    private Dialog myDialog;
    private Dialog dialogTerminarSessao;
    private CheckBox caminhadaCheckBox;
    private CheckBox corridaCheckBox;
    private int corridaChecked = 0;
    private int caminhadaChecked = 0;
    private int futebolChecked = 0;
    private int ciclismoChecked = 0;
    private MenuItem mi;
    private ArrayList<CheckBox> checkboxesPopup = new ArrayList<CheckBox>();
    private String username;
    private String firstName = "";
    private String lastName = "";
    private String image_path = "";

    private static final String TAG = "MenuPrincipal";

    private DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            username = getIntent().getStringExtra("USERNAME");
            image_path = getIntent().getStringExtra("URI");
            if (!TextUtils.isEmpty(image_path)) {
                Uri fileUri = Uri.parse(image_path);
            }
        } else {
            username = "";
        }

        myDialog = new Dialog(this);
        dialogTerminarSessao = new Dialog(this);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        databaseUsers.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    firstName = String.valueOf(child.child("firstName").getValue());
                    lastName = String.valueOf(child.child("lastName").getValue());
                    toolbarCima = (Toolbar) findViewById(R.id.toolbar);
                    setSupportActionBar(toolbarCima);
                    getSupportActionBar().setTitle("ActivityGO");

                    getSupportActionBar().setSubtitle("" + firstName.charAt(0) + lastName.charAt(0) + ":");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Bundle toRunMenuInicial = new Bundle();
        toRunMenuInicial.putString("USERNAME", username);
        toRunMenuInicial.putString("URI", image_path);
        SelectedFragment = new RunMenuInicial();
        SelectedFragment.setArguments(toRunMenuInicial);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container, SelectedFragment, "RunFragment");
        //ft.addToBackStack("RunFragment");
        ft.commit();

        BottomNavigationView mMainNav = findViewById(R.id.NavBar);
        mMainNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment SelectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.run:
                            //Fragmento Corrida
                            SelectedFragment = new RunMenuInicial();
                            Bundle toRunMenuInicial = new Bundle();
                            toRunMenuInicial.putString("USERNAME", username);
                            toRunMenuInicial.putString("URI", image_path);
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
                            Bundle toChallengeFragment = new Bundle();
                            toChallengeFragment.putString("USERNAME", username);
                            SelectedFragment.setArguments(toChallengeFragment);
                            FragmentManager fman = getFragmentManager();
                            FragmentTransaction ftra = fman.beginTransaction();
                            ftra.replace(R.id.fragment_container, SelectedFragment, "ChalengeFragment");
                            ftra.commit();
                            break;

                        case R.id.achievementItem:
                            Bundle bundle = new Bundle();
                            bundle.putString("USERNAME", username);
                            SelectedFragment = new AchievementsFragment();
                            SelectedFragment.setArguments(bundle);
                            FragmentManager f = getFragmentManager();
                            FragmentTransaction fte = f.beginTransaction();
                            fte.replace(R.id.fragment_container, SelectedFragment, "AchievementFragment");
                            fte.addToBackStack("AchievementFragment");
                            fte.commit();
                            break;

                        case R.id.RankingItem:
                            //Fragmento Ranking
                            Bundle b1 = new Bundle();
                            b1.putString("USERNAME", username);
                            SelectedFragment = new RankingsFragment();
                            SelectedFragment.setArguments(b1);
                            FragmentManager fmanag = getFragmentManager();
                            menuItem.setIcon(R.drawable.trophy_icon333);
                            FragmentTransaction ftransactio = fmanag.beginTransaction();
                            ftransactio.replace(R.id.fragment_container, SelectedFragment, "RankingsFragment");
                            ftransactio.addToBackStack("RankingsFragment");
                            ftransactio.commit();
                            break;

                        case R.id.GroupsItem:
                            Bundle args = new Bundle();
                            args.putString("USERNAME", username);
                            SelectedFragment = new MergeGroupFragment();
                            FragmentManager fmana = getFragmentManager();
                            SelectedFragment.setArguments(args);
                            FragmentTransaction ftransacti = fmana.beginTransaction();
                            ftransacti.replace(R.id.fragment_container, SelectedFragment, "MergeGroupFragment");
                            ftransacti.addToBackStack("MergeGroupFragment");
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
                Intent i = new Intent(this, SettingsActivity.class);
                i.putExtra("USERNAME", username);
                i.putExtra("URI", image_path);
                startActivity(i);
                break;

            case R.id.BackButton:
                //getFragmentManager().popBackStack();
                //RankingsFragment
                RankingsFragment myFragment = (RankingsFragment) getFragmentManager().findFragmentByTag("RankingsFragment");
                TableRankingsFragment myFragment2 = (TableRankingsFragment) getFragmentManager().findFragmentByTag("TableRankingsFragment");
                MergeGroupFragment mergeGroupFragment = (MergeGroupFragment) getFragmentManager().findFragmentByTag("MergeGroupFragment");

                if (myFragment != null && myFragment.isVisible()) {
                    getFragmentManager().popBackStack();
                } else if (myFragment2 != null && myFragment2.isVisible()) {
                    getFragmentManager().popBackStack();
                } else if (mergeGroupFragment != null && mergeGroupFragment.isVisible()) {
                    getFragmentManager().popBackStack();
                }
        }
        return true;
    }

    public void showTerminarSessaoPopup() {
        Button yesButton;
        Button noButton;
        TextView close;
        TextView popupId;
        dialogTerminarSessao.setContentView(R.layout.popup_terminar_sessao);
        yesButton = (Button) dialogTerminarSessao.findViewById(R.id.yesButton);
        noButton = (Button) dialogTerminarSessao.findViewById(R.id.noButton);
        close = (TextView) dialogTerminarSessao.findViewById(R.id.txtClose);
        popupId = (TextView) dialogTerminarSessao.findViewById(R.id.popUpId);
        popupId.setText("Tem a certeza?");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTerminarSessao.dismiss();
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginScreen.class));
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTerminarSessao.dismiss();
            }
        });
        dialogTerminarSessao.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogTerminarSessao.show();
    }

    /*
        public ArrayList<String> getProfile() {
            return this.profile;
        }
    */
    public void showPopup(@NonNull MenuItem menuItem) {
        TextView txtclose;
        TextView txtname;
        TextView txtusername;
        Button confirmButton;
        mi = menuItem;

        myDialog.setContentView(R.layout.popup_inicial);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclosePopupInicial);
        txtname = (TextView) myDialog.findViewById(R.id.idName);
        txtusername = (TextView) myDialog.findViewById(R.id.idUsername);
        caminhadaCheckBox = (CheckBox) myDialog.findViewById(R.id.opcaoPraticar1);
        corridaCheckBox = (CheckBox) myDialog.findViewById(R.id.opcaoPraticar2);
        confirmButton = (Button) myDialog.findViewById(R.id.confirmButton);
        //txtname.setText(profile.get(0));
        //txtusername.setText(profile.get(2));
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String opcao = getSelectedSport();
                changePlusOption(opcao);
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    private void changePlusOption(String opcao) {
        switch (opcao) {
            case "Corrida":
                mi.setIcon(R.drawable.outline_directions_run_black_18dp);
                break;
            case "Caminhada":
                mi.setIcon(R.drawable.walk_icon);
                break;
        }
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

    public String getSelectedSport() {
        return this.selectedSport;
    }
}