package com.example.android.activitygo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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


    private CheckBox masculino;
    private CheckBox feminino;
    private CheckBox corrida;
    private CheckBox caminhada;
    private CheckBox futebol;
    private CheckBox ciclismo;


    private int masculinoChecked = 0;
    private int femininoChecked = 0;
    private int corridaChecked = 0;
    private int caminhadaChecked = 0;
    private int futebolChecked = 0;
    private int ciclismoChecked = 0;
    private ArrayList<CheckBox> checkboxes = new ArrayList<CheckBox>();





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

        masculino = (CheckBox) findViewById(R.id.Masculino);
        feminino = (CheckBox) findViewById(R.id.Feminino);
        caminhada = (CheckBox) findViewById(R.id.caminhada);
        corrida = (CheckBox) findViewById(R.id.corrida);
        futebol = (CheckBox) findViewById(R.id.futebol);
        ciclismo  = (CheckBox) findViewById(R.id.ciclismo);





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
                        !secondName.equals("") && !email.equals("") && !peso.equals("") && !altura.equals("") &&
                        peso.matches("^[0-9][0-9]{2,3}$") && altura.matches("^[0-9][0-9]{2,3}$") &&
                        ((masculinoChecked == 1 && femininoChecked == 0)||(masculinoChecked == 0 && femininoChecked == 1)) &&
                        isAnyItemCheck()) {

                    Intent intent = new Intent(getApplicationContext(), MenuPrincipal.class);
                    startActivity(intent);
                } else {

                    if (firstName.equals("")) {
                        firstNameUser.setError("O  Primeiro nome não foi preenchido");
                    }

                    if (secondName.equals("")) {
                        secondNameUser.setError("O  segundo nome não foi preenchido");
                    }

                    if (email.equals("")) {
                        emailUser.setError("O email não foi preenchido");
                    }

                    if (peso.equals("")) {
                        pesoUser.setError("O peso não foi preenchido");
                    }

                    if (altura.equals("")) {
                        alturaUser.setError("A altura não foi preenchida");
                    }

                    if (password.equals("")) {
                        passwordUser.setError("A password não foi preenchida");
                    }

                    if (confirmaPassword.equals("")) {
                        confirmaPasswordUser.setError("A confirmação não foi preenchida");
                    }

                    if (!peso.matches("^[0-9][0-9]{2,3}$")) {
                        pesoUser.setError("Não tem os digitos certos");
                    }

                    if (!altura.matches("^[0-9][0-9]{2,3}$")) {
                        alturaUser.setError("Não tem os digitos certos");
                    }


                    if (!password.equals(confirmaPassword)) {
                        passwordUser.setError("As passwords não são iguais");
                        confirmaPasswordUser.setError("As passwords não são iguais");
                    }

                    if (masculinoChecked == 0 && femininoChecked == 0){
                        masculino.setError("Não selecionou a caixa");
                        masculino.requestFocus();
                        feminino.setError("Não selecionou a caixa");
                        feminino.requestFocus();
                    }

                    if (isAnyItemCheck() == false){
                        caminhada.setError("Não selecionou nenhuma caixa");
                        caminhada.requestFocus();
                        corrida.setError("Não selecionou nenhuma a caixa");
                        corrida.requestFocus();
                        futebol.setError("Não selecionou nenhuma a caixa");
                        futebol.requestFocus();
                        ciclismo.setError("Não selecionou nenhuma a caixa");
                        ciclismo.requestFocus();
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


    public void getMasculinoItem(View v){
        masculinoChecked = 1;

    }

    public void getFemininoItem(View v){
        femininoChecked = 1;
    }



    public void getCaminhadaItem(View v){
        caminhadaChecked = 1;
        caminhada = (CheckBox) findViewById(R.id.caminhada);
        checkboxes.add(caminhada);
    }


    public void getCorridaItem(View v){
        corridaChecked = 1;
        checkboxes.add(corrida);
    }


    public void getFutebolItem(View v){
        futebolChecked = 1;
        checkboxes.add(futebol);

    }


    public void getCiclismoItem(View v){
        ciclismoChecked = 1;
        checkboxes.add(ciclismo);

    }


    public boolean isAnyItemCheck(){
        for (CheckBox checkBox: checkboxes){
            if (checkBox.isChecked()){
                return true;
            }
        }
        return false;
    }





}