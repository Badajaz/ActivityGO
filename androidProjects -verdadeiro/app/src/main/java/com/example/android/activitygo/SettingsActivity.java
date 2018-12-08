package com.example.android.activitygo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.activitygo.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {

    private Dialog dialogTerminarSessao;
    private Dialog dialogCalendario;
    private Dialog dialogChangeProfile;
    private Button confirmaAltPerfil;
    private Button informacoesPrivadas;
    private Button buttonCalendario;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListenerForCalendar;
    private static final String TAG = "SettingsActivity";

    private TextView firstNameUser;
    private TextView secondNameUser;
    private TextView username;
    private TextView emailUser;
    private TextView pesoUser;
    private TextView alturaUser;
    private TextView passwordUser;
    private TextView dataNascimento;
    private TextView paisUser;
    private TextView confirmaPasswordUser;
    private ImageView imv;
    private TextView displayName;
    private TextView displayDesporto;

    private String firstName;
    private String secondName;
    private String usernameStr;
    private String email;
    private String peso;
    private String altura;
    private String password;
    private String confirmaPassword;
    private String dataNascimentoStr;
    private String paisUserStr;

    private String fn;
    private String ln;

    private String usernameReceived;

    // perfil que o user coloca quando se regista
    private ArrayList<String> userProfileMenuPrincipal;

    // coisas novas que o user quer alterar
    private ArrayList<String> userProfileCpf = new ArrayList<>();

    // lista com as coisas alteradas e as inalteradas + genero + desportos favoritos
    private ArrayList<String> alteracoes = new ArrayList<>();

    private Toolbar toolbarCima;
    private User userUpdate;

    private DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_layout);
        usernameReceived = getIntent().getStringExtra("USERNAME");

        dialogTerminarSessao = new Dialog(this);
        dialogChangeProfile = new Dialog(this);
        dialogCalendario = new Dialog(this);
        imv = (ImageView) findViewById(R.id.imageViewSettings);
        displayName = (TextView) findViewById(R.id.namePessoaMenuSettings);
        displayDesporto = (TextView) findViewById(R.id.desportoFavoritoSettings);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        databaseUsers.orderByChild("username").equalTo(usernameReceived).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    fn = String.valueOf(child.child("firstName").getValue());
                    ln = String.valueOf(child.child("lastName").getValue());
                    toolbarCima = (Toolbar) findViewById(R.id.toolbarSettings);
                    setSupportActionBar(toolbarCima);
                    getSupportActionBar().setTitle("ActivityGO");

                    getSupportActionBar().setSubtitle("" + fn.charAt(0) + ln.charAt(0));
                    displayName.setText("Olá " + fn + " " + ln + "!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button changeProfile = (Button) findViewById(R.id.buttonAlterarPerfilSettings);
        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeProfilePopup();
            }
        });

        buttonCalendario = (Button) findViewById(R.id.buttonCalendarioSettings);
        buttonCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarioPopup();
            }
        });

        Button terminarSessao = (Button) findViewById(R.id.buttonTerminarSessao);
        terminarSessao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTerminarSessaoPopup();
            }
        });
    }

    public void showTerminarSessaoPopup() {
        Button yesButton;
        Button noButton;
        TextView close;
        TextView popupId;
        dialogTerminarSessao.setContentView(R.layout.popup_terminar_sessao);
        yesButton = (Button) dialogTerminarSessao.findViewById(R.id.yesButton);
        noButton = (Button) dialogTerminarSessao.findViewById(R.id.noButton);
        close = (TextView) dialogTerminarSessao.findViewById(R.id.txtClose);
        popupId = (TextView) dialogTerminarSessao.findViewById(R.id.popUpId);
        popupId.setText("Tem a certeza?");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTerminarSessao.dismiss();
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginScreen.class));
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTerminarSessao.dismiss();
            }
        });
        dialogTerminarSessao.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogTerminarSessao.show();
    }

    public void showChangeProfilePopup() {
        dialogChangeProfile.setContentView(R.layout.change_profile_popup);
        iniciarCampos(dialogChangeProfile);

        //alteracoes = detetarAlteracoesPerfis(userProfileMenuPrincipal, userProfileCpf);

        confirmaAltPerfil = (Button) dialogChangeProfile.findViewById(R.id.buttonConfirmarAlterarPerfil);
        confirmaAltPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // obtem os valores mudados
                firstName = firstNameUser.getText().toString();
                secondName = secondNameUser.getText().toString();
                dataNascimentoStr = dataNascimento.getText().toString();
                paisUserStr = paisUser.getText().toString();
                email = emailUser.getText().toString();
                peso = pesoUser.getText().toString();
                altura = alturaUser.getText().toString();
                usernameStr = username.getText().toString();
                password = passwordUser.getText().toString();
                confirmaPassword = confirmaPasswordUser.getText().toString();

                databaseUsers = FirebaseDatabase.getInstance().getReference("users");
                databaseUsers.orderByChild("username").equalTo(usernameReceived).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            String fn = String.valueOf(child.child("firstName").getValue());
                            String sn = String.valueOf(child.child("lastName").getValue());
                            String dn = String.valueOf(child.child("date").getValue());
                            String email2 = String.valueOf(child.child("email").getValue());
                            //String u = String.valueOf(child.child("username").getValue());
                            String pwd = String.valueOf(child.child("password").getValue());
                            String w = String.valueOf(child.child("weight").getValue());
                            String h = String.valueOf(child.child("hight").getValue());
                            String pais = String.valueOf(child.child("country").getValue());
                            if (!firstName.equals(fn) && !firstName.equals("")) {
                                databaseUsers.child(child.getKey()).child("firstName").setValue(firstName);
                            }

                            if (!secondName.equals(sn) && !secondName.equals("")) {
                                databaseUsers.child(child.getKey()).child("lastName").setValue(secondName);
                            }
                            if (!dataNascimentoStr.equals(dn) && !dataNascimentoStr.equals("")) {
                                databaseUsers.child(child.getKey()).child("date").setValue(dataNascimentoStr);
                            }
                            if (!paisUserStr.equals(pais) && !paisUserStr.equals("")) {
                                databaseUsers.child(child.getKey()).child("country").setValue(paisUserStr);
                            }
                            if (!email.equals(email2) && !email.equals("")) {
                                databaseUsers.child(child.getKey()).child("email").setValue(email);
                            }
                            if (!peso.equals(w) && !peso.equals("")) {
                                databaseUsers.child(child.getKey()).child("weight").setValue(peso);
                            }
                            if (!altura.equals(h) && !altura.equals("")) {
                                databaseUsers.child(child.getKey()).child("hight").setValue(altura);
                            }
                            if (!password.equals(pwd) && !password.equals("")) {
                                databaseUsers.child(child.getKey()).child("password").setValue(password);
                            }
                            // NAO ESTA A MUDAR O USERNAME
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
/*
                if (!firstName.equals("")) {
                    userUpdate.setFirstName(firstName);
                }

                if (!secondName.equals("")) {
                    userUpdate.setLastName(secondName);
                }

                if (!dataNascimentoStr.equals("")) {
                    userUpdate.setDate(dataNascimentoStr);
                }

                if (!paisUserStr.equals("")) {
                    userUpdate.setDate(paisUserStr);
                }

                if (!email.equals("")) {
                    userUpdate.setEmail(email);
                }

                if (!peso.equals("")) {
                    userUpdate.setWeight(peso);
                }

                if (!altura.equals("")) {
                    userUpdate.setHight(altura);
                }

                if (!usernameStr.equals("")) {
                    userUpdate.setUsername(usernameStr);
                }

                if (!password.equals("")) {
                    userUpdate.setPassword(password);
                }*/

                dialogChangeProfile.dismiss();


            }
        });
        dialogChangeProfile.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogChangeProfile.show();
    }

    public ArrayList<String> detetarAlteracoesPerfis(ArrayList<String> listUser, ArrayList<String> listUserAlterada) {
        // index da listaUserAlterada
        ArrayList<String> alterada = new ArrayList<>();
        int index = 0;
        for (String str : listUser) {
            if (index < 9) {
                if (!str.equals(listUserAlterada.get(index))) {
                    alterada.add(listUserAlterada.get(index));
                } else if (str.equals(listUserAlterada.get(index))) {
                    alterada.add(str);
                }
                index++;
            } else {
                if (index == listUser.size()) {
                    break;
                } else {
                    alterada.add(str);
                    index++;
                }
            }
        }

        return alterada;
    }

    private void iniciarCampos(Dialog d) {
        // Preparar para guardar os valores
        firstNameUser = (TextView) d.findViewById(R.id.primeiroNomeText);
        secondNameUser = (TextView) d.findViewById(R.id.apelidoNameText);
        username = (TextView) d.findViewById(R.id.privateUsernameNameText);
        emailUser = (TextView) d.findViewById(R.id.emailNameText);
        pesoUser = (TextView) d.findViewById(R.id.weightNameText);
        alturaUser = (TextView) d.findViewById(R.id.heightNameText);
        passwordUser = (TextView) d.findViewById(R.id.privatePasswordNameText);
        confirmaPasswordUser = (TextView) d.findViewById(R.id.privateInfoPasswordNameText);
        dataNascimento = (TextView) d.findViewById(R.id.DataNascimentoChangePopUp);
        dataNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SettingsActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
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
        paisUser = (TextView) d.findViewById(R.id.paisUserNameText);
    }

    private void showCalendarioPopup() {
        TextView close;
        dialogCalendario.setContentView(R.layout.popup_calendario);
        close = (TextView) dialogCalendario.findViewById(R.id.txtClose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCalendario.dismiss();
            }
        });

        dialogCalendario.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogCalendario.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.BackButton:
                getFragmentManager().popBackStack();
        }
        return true;
    }
}