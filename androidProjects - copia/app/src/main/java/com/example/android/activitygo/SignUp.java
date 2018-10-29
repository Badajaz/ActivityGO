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








        Button criar = (Button) findViewById(R.id.button2);

            criar.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    firstName = firstNameUser.getText().toString();
                    secondName = secondNameUser.getText().toString();
                    email = emailUser.getText().toString();

                    //TODO /*validar o facto de ser um número*/
                    peso = pesoUser.getText().toString();
                    //TODO /*validar o facto de ser um número e decidir se vai ser em cm ou m*/
                    altura = alturaUser.getText().toString();

                    password = passwordUser.getText().toString();
                    confirmaPassword = confirmaPasswordUser.getText().toString();

                    if (password.equals(confirmaPassword) && !password.equals("") && !firstName.equals("") &&
                            !secondName.equals("") && !email.equals("") && !peso.equals("") && !altura.equals("") && peso.matches("^[0-9]{2,3}$") && altura.matches("^[0-9]{2,3}$")) {
                        Intent intent = new Intent(getApplicationContext(), MenuPrincipal.class);
                        startActivity(intent);
                    }else{

                        if (firstName.equals("")){
                            firstNameUser.setError("O  Primeiro nome não foi preenchido");
                        }

                        if (secondName.equals("")){
                            secondNameUser.setError("O  segundo nome não foi preenchido");
                        }

                        if (email.equals("")){
                            emailUser.setError("O email não foi preenchido");
                        }

                        if (peso.equals("")){
                            pesoUser.setError("O peso não foi preenchido");
                        }

                        if (altura.equals("")){
                            alturaUser.setError("A altura não foi preenchida");
                        }

                        if (password.equals("")){
                            passwordUser.setError("A password não foi preenchida");
                        }

                        if (confirmaPassword.equals("")){
                            confirmaPasswordUser.setError("A confirmação não foi preenchida");
                        }

                        if (!password.equals(confirmaPassword)){
                            passwordUser.setError("As passwords não são iguais");
                            confirmaPasswordUser.setError("As passwords não são iguais");
                        }
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