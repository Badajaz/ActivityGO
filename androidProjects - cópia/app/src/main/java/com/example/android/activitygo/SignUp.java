package com.example.android.activitygo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }


    public void paginaToolbar(View view){
        Intent intent = new Intent(this, MenuPrincipal.class);
        startActivity(intent);


    }



}
