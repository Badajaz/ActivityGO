package com.example.android.activitygo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private TextView firstNameUser;
    private TextView secondNameUser;
    private TextView emailUser;
    private TextView pesoUser;
    private TextView alturaUser;
    private TextView passwordUser;

    private String firstName;
    private String secondName;
    private String email;
    private String peso;
    private String altura;
    private String password;
    private TextView confirmaPasswordUser;
    private String confirmaPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Preparar para guardar os valores
        firstNameUser = (TextView) findViewById(R.id.FirstNameText);
        secondNameUser = (TextView) findViewById(R.id.LastNameText);
        emailUser = (TextView) findViewById(R.id.EmailText);
        pesoUser = (TextView) findViewById(R.id.WeightText);
        alturaUser = (TextView) findViewById(R.id.HeightText);
        passwordUser = (TextView) findViewById(R.id.PasswordText);
        confirmaPasswordUser = (TextView) findViewById(R.id.reTypePasswordText);


        firstName = firstNameUser.getText().toString();
        secondName = secondNameUser.getText().toString();
        email = emailUser.getText().toString();
        peso = pesoUser.getText().toString();
        altura = alturaUser.getText().toString();

        Button criar = (Button) findViewById(R.id.button2);

            criar.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    password = passwordUser.getText().toString();
                    confirmaPassword = confirmaPasswordUser.getText().toString();
                    if (password.equals(confirmaPassword)) {
                        Intent intent = new Intent(getApplicationContext(), MenuPrincipal.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getBaseContext(), "Palavras passes nao são iguais",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });







/*
        Bundle args = new Bundle();

        args.putString("firstName", firstName);
        args.putString("secondName", secondName);
        args.putString("email", email);
        args.putString("peso", peso);
        args.putString("altura", altura);
        args.putString("password", password);
*/
    }

}