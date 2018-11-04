package com.example.android.activitygo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {

    private Dialog dialogTerminarSessao;
    private Dialog dialogCalendario;
    private Dialog dialogChangeProfile;
    private Dialog dialogFaqs;
    private Button confirmaAltPerfil;
    private Button contactosImp;
    private Button faQS;
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

    // perfil que o user coloca quando se regista
    private ArrayList<String> userProfileMenuPrincipal;

    // coisas novas que o user quer alterar
    private ArrayList<String> userProfileCpf = new ArrayList<>();

    // lista com as coisas alteradas e as inalteradas + genero + desportos favoritos
    private ArrayList<String> alteracoes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_layout);

        userProfileMenuPrincipal = (ArrayList<String>) getIntent().getSerializableExtra("USERPROFILE");
        dialogTerminarSessao = new Dialog(this);
        dialogChangeProfile = new Dialog(this);
        dialogCalendario = new Dialog(this);
        dialogFaqs = new Dialog(this);
        contactosImp = (Button) findViewById(R.id.contactosImportantesButton);
        contactosImp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContactosImportantesPopup();
            }
        });

        faQS = (Button) findViewById(R.id.faqsButton);
        faQS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFaqsPopup();
            }
        });

        buttonCalendario = (Button) findViewById(R.id.calendarioButton);
        buttonCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarioPopup();
            }
        });

        Button changeProfile = (Button) findViewById(R.id.buttonAlterarPerfil);
        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeProfilePopup();
            }
        });

        Button terminarSessao = (Button) findViewById(R.id.terminaaaaaaaaaaaaa);
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
        dialogTerminarSessao.setContentView(R.layout.popup_terminar_sessao);
        yesButton = (Button) dialogTerminarSessao.findViewById(R.id.yesButton);
        noButton = (Button) dialogTerminarSessao.findViewById(R.id.noButton);
        close = (TextView) dialogTerminarSessao.findViewById(R.id.txtClose);

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

    public void showChangeProfilePopup(){
        dialogChangeProfile.setContentView(R.layout.change_profile_popup);

        iniciarCampos(dialogChangeProfile);

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

        userProfileCpf.add(firstName);
        userProfileCpf.add(secondName);
        userProfileCpf.add(usernameStr);
        userProfileCpf.add(email);
        userProfileCpf.add(peso);
        userProfileCpf.add(altura);
        userProfileCpf.add(password);
        userProfileCpf.add(dataNascimentoStr);
        userProfileCpf.add(paisUserStr);

        alteracoes = detetarAlteracoesPerfis(userProfileMenuPrincipal, userProfileCpf);

        confirmaAltPerfil = (Button) dialogChangeProfile.findViewById(R.id.buttonConfirmarAlterarPerfil);
        confirmaAltPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            if(index < 9){
                if (!str.equals(listUserAlterada.get(index))) {
                    alterada.add(listUserAlterada.get(index));
                } else if(str.equals(listUserAlterada.get(index))){
                    alterada.add(str);
                }
                index++;
            } else {
                if(index == listUser.size()){
                    break;
                } else {
                    alterada.add(str);
                    index++;
                }
            }
        }

        return alterada;
    }

    private void iniciarCampos (Dialog d){
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
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + dayOfMonth + "/" + month + "/" + year);

                String date = dayOfMonth + "/" + month + "/" + year;
                dataNascimento.setText(date);
            }
        };
        paisUser = (TextView) d.findViewById(R.id.paisUserNameText);
    }

    private void showContactosImportantesPopup(){

    }

    private void showFaqsPopup(){
        TextView close;
        dialogFaqs.setContentView(R.layout.popup_faqs);
        close = (TextView) dialogFaqs.findViewById(R.id.txtClose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFaqs.dismiss();
            }
        });
        dialogFaqs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogFaqs.show();
    }

    private void showCalendarioPopup(){
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
}
