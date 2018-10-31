package com.example.android.activitygo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginScreen extends AppCompatActivity {

    private EditText user;
    private EditText password;
    private ArrayList<String> profile;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        user = (EditText) findViewById(R.id.usernameText);
        String username = getIntent().getStringExtra("USERNAME");
        pass = getIntent().getStringExtra("PASSWORD");
        user.setText(username);

        Button button = (Button) findViewById(R.id.buttonLogin);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SignUp s = new SignUp();
                int alreadyRRR = getIntent().getIntExtra("ALREADYREGISTER", 0);
                if (alreadyRRR == 1) {
                    profile = (ArrayList<String>) getIntent().getSerializableExtra("USERPROFILE");
                }

                password = (EditText) findViewById(R.id.passwordText);
                String userTxt = user.getText().toString();
                String passwordTxt = password.getText().toString();

                if (!userTxt.equals("") && !passwordTxt.equals("") && passwordTxt.equals(pass)) {
                    Intent intent = new Intent(getApplicationContext(), MenuPrincipal.class);
                    intent.putExtra("USERPROFILE", profile);
                    startActivity(intent);
                } else {
                    if (userTxt.equals("")) {
                        user.setError("O Texto não foi preenchido");
                    }

                    if (passwordTxt.equals("")) {
                        password.setError("O texto não foi preenchido");
                    }

                    if (!passwordTxt.equals(pass)) {
                        password.setError("A password não corresponde");
                    }
                }
            }
        });
    }

    public void criarConta(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}