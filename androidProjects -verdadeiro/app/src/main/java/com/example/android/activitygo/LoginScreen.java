package com.example.android.activitygo;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.activitygo.MenuPrincipal;
import com.example.android.activitygo.R;

public class LoginScreen extends AppCompatActivity {

    private EditText user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        user = (EditText) findViewById(R.id.usernameText);
        String firstName = getIntent().getStringExtra("EXTRA_SESSION_ID");
        user.setText(firstName);

        Button button = (Button) findViewById(R.id.buttonLogin);
        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {



                EditText password = (EditText) findViewById(R.id.passwordText);
                String passwordSign = getIntent().getStringExtra("PASSWORD");
                String userTxt = user.getText().toString();
                String passwordTxt = password.getText().toString();
                if (!userTxt.equals("") && !passwordTxt.equals("") && passwordTxt.equals(passwordSign)){
                    Intent intent = new Intent(getApplicationContext(), MenuPrincipal.class);
                    startActivity(intent);
                }else{
                    if (userTxt.equals("")){
                        user.setError("O Texto não foi preenchido");
                    }

                    if (passwordTxt.equals("")){
                        password.setError("O texto não foi preenchido");
                    }

                    if (!passwordTxt.equals(passwordSign)){
                        password.setError("A password não corresponde");
                    }

                }








            }
        });

    }

    public void criarConta(View view){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}