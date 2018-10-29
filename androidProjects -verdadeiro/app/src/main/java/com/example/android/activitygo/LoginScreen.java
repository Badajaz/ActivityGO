package com.example.android.activitygo;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.activitygo.MenuPrincipal;
import com.example.android.activitygo.R;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        Button button = (Button) findViewById(R.id.buttonLogin);
        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                EditText user = (EditText) findViewById(R.id.usernameText);
                EditText password = (EditText) findViewById(R.id.passwordText);

                String userTxt = user.getText().toString();
                String passwordTxt = password.getText().toString();
                if (!userTxt.equals("") && !passwordTxt.equals("")){
                    Intent intent = new Intent(getApplicationContext(), MenuPrincipal.class);
                    startActivity(intent);
                }else{
                    if (userTxt.equals("")){
                        user.setError("O Texto não foi preenchido");
                    }

                    if (passwordTxt.equals("")){
                        password.setError("O texto não foi preenchido");
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