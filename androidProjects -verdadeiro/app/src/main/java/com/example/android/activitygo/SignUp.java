package com.example.android.activitygo;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.activitygo.model.Grupo;
import com.example.android.activitygo.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {


    private TextView firstNameUser;
    private TextView secondNameUser;
    private TextView username;
    private TextView emailUser;
    private TextView pesoUser;
    private TextView alturaUser;
    private TextView passwordUser;
    private TextView dataNascimento;
    private TextView paisUser;

    private TextView textTargetUri;
    private Button buttonLoadImage;

    private String firstName;
    private String secondName;
    private String usernameStr;
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
    private ArrayList<String> sports = new ArrayList<>();
    private String genero;

    private static final String TAG = "SignUpActivity";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public int alreadyRegister = 0;

    private DatabaseReference databaseUsers;

    private boolean existe = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        firstNameUser = (TextView) findViewById(R.id.FirstNameText);
        secondNameUser = (TextView) findViewById(R.id.LastNameText);
        username = (TextView) findViewById(R.id.username);
        emailUser = (TextView) findViewById(R.id.EmailText);
        pesoUser = (TextView) findViewById(R.id.WeightText);
        alturaUser = (TextView) findViewById(R.id.HeightText);
        passwordUser = (TextView) findViewById(R.id.PasswordText);
        confirmaPasswordUser = (TextView) findViewById(R.id.reTypePasswordText);
        paisUser = (TextView) findViewById(R.id.paisUser);

        dataNascimento = (TextView) findViewById(R.id.DataNascimento);
        dataNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SignUp.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                //Log.d(TAG, "onDateSet: mm/dd/yyy: " + dayOfMonth + "/" + month + "/" + year);
                String date = dayOfMonth + "/" + month + "/" + year;
                dataNascimento.setText(date);
            }
        };

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
                usernameStr = username.getText().toString();
                secondName = secondNameUser.getText().toString();
                email = emailUser.getText().toString();

                peso = pesoUser.getText().toString();
                altura = alturaUser.getText().toString();

                password = passwordUser.getText().toString();
                confirmaPassword = confirmaPasswordUser.getText().toString();
                dataNascimentoStr = dataNascimento.getText().toString();
                paisUserStr = paisUser.getText().toString();

                databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            User g = userSnapshot.getValue(User.class);
                            if (g.getUsername().equals(usernameStr)) {
                                existe = true;
                            }else{
                                existe = false;
                            }

                        }

                        if (password.equals(confirmaPassword) && !password.equals("") && !firstName.equals("") &&
                                !secondName.equals("") && !usernameStr.equals("") && !email.equals("") && !peso.equals("") && !altura.equals("") &&
                                validaPeso(peso) && validaAltura(altura) && validate(email) &&
                                ((masculinoChecked == 1 && femininoChecked == 0) || (masculinoChecked == 0 && femininoChecked == 1)) &&
                                isAnyItemCheck() && !dataNascimentoStr.equals("") && !paisUserStr.equals("") && existe == false) {

                            userProfile.add(firstName);
                            userProfile.add(secondName);
                            userProfile.add(usernameStr);
                            userProfile.add(email);
                            userProfile.add(peso);
                            userProfile.add(altura);
                            userProfile.add(password);
                            userProfile.add(dataNascimentoStr);
                            userProfile.add(paisUserStr);
                            if (masculinoChecked == 1) {
                                userProfile.add(masculino.getText().toString());
                                genero = "MASCULINO";
                            }
                            if (femininoChecked == 1) {
                                userProfile.add(feminino.getText().toString());
                                genero = "FEMININO";
                            }
                            if (caminhadaChecked == 1) {
                                userProfile.add(caminhada.getText().toString());
                                sports.add("CAMINHADA");
                            }
                            if (corridaChecked == 1) {
                                userProfile.add(corrida.getText().toString());
                                sports.add("CORRIDA");
                            }
                            if (ciclismoChecked == 1) {
                                userProfile.add(ciclismo.getText().toString());
                                sports.add("CICLISMO");
                            }
                            if (futebolChecked == 1) {
                                userProfile.add(futebol.getText().toString());
                                sports.add("FUTEBOL");
                            }

                            // para confirmar que está registado
                            alreadyRegister = 1;

                            String id = databaseUsers.push().getKey();

                            User user = new User(firstName, secondName, dataNascimentoStr, genero, paisUserStr, email, peso, altura, usernameStr, password, sports, "");
                            databaseUsers.child(id).setValue(user);

                            Intent intent = new Intent(getBaseContext(), LoginScreen.class);
                            //intent.putExtra("USERNAME", usernameStr);
                            //intent.putExtra("PASSWORD", password);
                            //intent.putExtra("USERPROFILE", userProfile);
                            //intent.putExtra("ALREADYREGISTER", alreadyRegister);
                            startActivity(intent);
                        } else {
                            if (firstName.equals("")) {
                                firstNameUser.setError("Não preencheu o primeiro nome!");
                            }

                            if (secondName.equals("")) {
                                secondNameUser.setError("Não preencheu o apelido!");
                            }

                            if (email.equals("")) {
                                emailUser.setError("Não preencheu o email!");
                            }

                            if (peso.equals("")) {
                                pesoUser.setError("Não preencheu o peso!");
                            }

                            if (altura.equals("")) {
                                alturaUser.setError("Não preencheu a altura!");
                            }

                            if (password.equals("")) {
                                passwordUser.setError("Não preencheu a password!");
                            }

                            if (confirmaPassword.equals("")) {
                                confirmaPasswordUser.setError("Não confirmou a password!");
                            }

                            if (!validate(email)) {
                                emailUser.setError("email não é válido");

                            }
                            if (!validaPeso(peso)) {
                                pesoUser.setError("Não tem os digitos certos!");
                            }

                            if (!validaAltura(altura)) {
                                alturaUser.setError("Não tem os digitos certos!");
                            }

                            if (!password.equals(confirmaPassword)) {
                                passwordUser.setError("As passwords não são iguais");
                                confirmaPasswordUser.setError("As passwords não são iguais");
                            }

                            if (masculinoChecked == 0 && femininoChecked == 0) {
                                masculino.setError("Não selecionou o seu sexo!");
                                masculino.requestFocus();
                                feminino.setError("Não selecionou o seu sexo!");
                                feminino.requestFocus();
                            }

                            if (isAnyItemCheck() == false) {
                                caminhada.setError("Não selecionou nenhuma caixa!");
                                caminhada.requestFocus();
                                corrida.setError("Não selecionou nenhuma a caixa!");
                                corrida.requestFocus();
                                futebol.setError("Não selecionou nenhuma a caixa!");
                                futebol.requestFocus();
                                ciclismo.setError("Não selecionou nenhuma a caixa!");
                                ciclismo.requestFocus();
                            }

                            if (paisUser.equals("")) {
                                firstNameUser.setError("Não preencheu o país!");
                            }
                            if (existe == true) {
                                username.setError("o user já existe");
                            }

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });


            }

        });
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

    public boolean validaPeso(String peso) {
        if (peso.equals("")) {
            return false;
        } else {
            return Double.parseDouble(peso) >= 50 && Double.parseDouble(peso) <= 100;
        }
    }

    public boolean validaAltura(String altura) {
        if (altura.equals("")) {
            return false;
        } else {
            return Integer.parseInt(altura) >= 150 && Integer.parseInt(altura) <= 200;
        }
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}