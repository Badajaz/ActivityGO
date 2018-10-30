package com.example.android.activitygo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {

    private TextView firstNameUser;
    private TextView secondNameUser;
    private TextView emailUser;
    private TextView pesoUser;
    private TextView alturaUser;
    private TextView passwordUser;
    private TextView dataNascimento;
    private TextView paisUser;

    private String firstName;
    private String secondName;
    private String email;
    private String peso;
    private String altura;
    private String password;
    private TextView confirmaPasswordUser;
    private String confirmaPassword;
    private String dataNascimentoStr;
    private String paisUserStr;

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
    private ArrayList<String> userProfile = new ArrayList<>();

    public int alreadyRegister = 0;

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
        dataNascimento = (TextView) findViewById(R.id.DataNascimento);
        paisUser = (TextView) findViewById(R.id.paisUser);

        masculino = (CheckBox) findViewById(R.id.Masculino);
        feminino = (CheckBox) findViewById(R.id.Feminino);
        caminhada = (CheckBox) findViewById(R.id.caminhada);
        corrida = (CheckBox) findViewById(R.id.corrida);
        futebol = (CheckBox) findViewById(R.id.futebol);
        ciclismo = (CheckBox) findViewById(R.id.ciclismo);

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
                dataNascimentoStr = dataNascimento.getText().toString();
                paisUserStr = paisUser.getText().toString();


                if (password.equals(confirmaPassword) && !password.equals("") && !firstName.equals("") &&
                        !secondName.equals("") && !email.equals("") && !peso.equals("") && !altura.equals("") &&
                        peso.matches("[1-9]\\d{1,2}") && altura.matches("^[1-9]\\d{1,2}") &&
                        ((masculinoChecked == 1 && femininoChecked == 0) || (masculinoChecked == 0 && femininoChecked == 1)) &&
                        isAnyItemCheck() && !dataNascimentoStr.equals("") && !paisUserStr.equals("")) {

                    userProfile.add(secondName);
                    userProfile.add(email);
                    userProfile.add(peso);
                    userProfile.add(altura);
                    userProfile.add(password);
                    userProfile.add(dataNascimentoStr);
                    userProfile.add(paisUserStr);
                    if (masculinoChecked == 1){
                        userProfile.add(masculino.getText().toString());
                    }
                    if (femininoChecked == 1){
                        userProfile.add(feminino.getText().toString());
                    }
                    if (caminhadaChecked == 1){
                        userProfile.add(caminhada.getText().toString());
                    }
                    if (corridaChecked == 1){
                        userProfile.add(corrida.getText().toString());
                    }
                    if (ciclismoChecked == 1){
                        userProfile.add(ciclismo.getText().toString());
                    }

                    if (futebolChecked == 1){
                        userProfile.add(futebol.getText().toString());
                    }

                    alreadyRegister = 1;


                    Intent intent = new Intent(getBaseContext(), LoginScreen.class);
                    intent.putExtra("FIRSTNAME",firstName);
                    intent.putExtra("PASSWORD",password);
                    intent.putExtra("USERPROFILE",userProfile);
                    startActivity(intent);
                } else {

                    if (firstName.equals("")) {
                        firstNameUser.setError("O Primeiro nome não foi preenchido");
                    }

                    if (secondName.equals("")) {
                        secondNameUser.setError("O apelido não foi preenchido");
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

                    if (!peso.matches("^[1-9]\\d{1,2}")) {
                        pesoUser.setError("Não tem os digitos certos");
                    }

                    if (!altura.matches("^[1-9]\\d{1,2}")) {
                        alturaUser.setError("Não tem os digitos certos");
                    }

                    if (!password.equals(confirmaPassword)) {
                        passwordUser.setError("As passwords não são iguais");
                        confirmaPasswordUser.setError("As passwords não são iguais");
                    }

                    if (masculinoChecked == 0 && femininoChecked == 0) {
                        masculino.setError("Não selecionou a caixa");
                        masculino.requestFocus();
                        feminino.setError("Não selecionou a caixa");
                        feminino.requestFocus();
                    }

                    if (isAnyItemCheck() == false) {
                        caminhada.setError("Não selecionou nenhuma caixa");
                        caminhada.requestFocus();
                        corrida.setError("Não selecionou nenhuma a caixa");
                        corrida.requestFocus();
                        futebol.setError("Não selecionou nenhuma a caixa");
                        futebol.requestFocus();
                        ciclismo.setError("Não selecionou nenhuma a caixa");
                        ciclismo.requestFocus();
                    }

                    if (paisUser.equals("")) {
                        firstNameUser.setError("O País não foi preenchido");
                    }

                }
            }
        });/*
        Bundle args = new Bundle();

        args.putString("firstName", firstName);
        args.putString("secondName", secondName);
        args.putString("email", email);
        args.putString("peso", peso);
        args.putString("altura", altura);
        args.putString("password", password);
*/
    }

    public void getMasculinoItem(View v) {
        masculinoChecked = 1;

    }

    public void getFemininoItem(View v) {
        femininoChecked = 1;
    }

    public void getCaminhadaItem(View v) {
        caminhadaChecked = 1;
        caminhada = (CheckBox) findViewById(R.id.caminhada);
        checkboxes.add(caminhada);
    }

    public void getCorridaItem(View v) {
        corridaChecked = 1;
        checkboxes.add(corrida);
    }

    public void getFutebolItem(View v) {
        futebolChecked = 1;
        checkboxes.add(futebol);
    }

    public void getCiclismoItem(View v) {
        ciclismoChecked = 1;
        checkboxes.add(ciclismo);
    }

    public boolean isAnyItemCheck() {
        for (CheckBox checkBox : checkboxes) {
            if (checkBox.isChecked()) {
                return true;
            }
        }
        return false;
    }

}