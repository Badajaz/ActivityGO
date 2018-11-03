package com.example.android.activitygo;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PopUpTerminarSessao extends Activity {

    private TextView txtclose;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow_layout);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;


        getWindow().setLayout((int)(width*0.8), (int)(height*0.2));

        Button simTerminar = (Button) findViewById(R.id.yesButton);
        Button naoTerminar = (Button) findViewById(R.id.noButton);
        txtclose = (TextView) findViewById(R.id.txtClose);

        simTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginScreen.class));
            }
        });
        naoTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment settings = new SettingsFragment();
                FragmentManager fmChangeProfile = getFragmentManager();
                FragmentTransaction ftChangeProfile = fmChangeProfile.beginTransaction();
                ftChangeProfile.replace(R.id.fragment_container,  settings,"SettingsFragment");
                //ftChangeProfile.addToBackStack("RunFragment");
                ftChangeProfile.commit();

            }
        });

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
