package com.example.android.activitygo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /* Preparar para guardar os valores
        firstNameUser = (TextView) findViewById(R.id.FirstNameText);
        secondNameUser = (TextView) findViewById(R.id.LastNameText);
        emailUser = (TextView) findViewById(R.id.EmailText);
        pesoUser = (TextView) findViewById(R.id.WeightText);
        alturaUser = (TextView) findViewById(R.id.HeightText);
        passwordUser = (TextView) findViewById(R.id.PasswordText);

        firstName = firstNameUser.getText().toString();
        secondName = secondNameUser.getText().toString();
        email = emailUser.getText().toString();
        peso = pesoUser.getText().toString();
        altura = alturaUser.getText().toString();
        password = passwordUser.getText().toString();

        Bundle args = new Bundle();

        args.putString("firstName", firstName);
        args.putString("secondName", secondName);
        args.putString("email", email);
        args.putString("peso", peso);
        args.putString("altura", altura);
        args.putString("password", password);
        */
    }


    public void paginaToolbar(View view) {
        Intent intent = new Intent(this, MenuPrincipal.class);
        startActivity(intent);
    }
}