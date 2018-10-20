package com.example.android.activitygo;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.activitygo.MenuPrincipal;
import com.example.android.activitygo.R;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        Button button = (Button) findViewById(R.id.buttonLogin);

    }

    public void fazerLogin(View view){
        Intent intent = new Intent(this, MenuPrincipal.class);
        startActivity(intent);
    }

    public void criarConta(View view){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}