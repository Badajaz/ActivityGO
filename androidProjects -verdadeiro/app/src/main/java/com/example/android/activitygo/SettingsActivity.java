package com.example.android.activitygo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button eliminarContaButton;
    private Button informacoesPrivadas;
    private Button alterarDesportoFavorito;
    private Button buttonCalendario;
    private Button buttonAboutUs;

    private Dialog challengePopup;

    private Dialog eliminarContaDialog;
    private Dialog desportoFavoritoDialog;
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
    private User u;
    private String usernameReceived;

    private Toolbar toolbarCima;

    private DatabaseReference databaseUsers;

    // key usada apenas para a parte das checkboxes, IMPORTANTE
    private String key = "";
    private String image_path = "";

    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_layout);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            usernameReceived = getIntent().getStringExtra("USERNAME");
            image_path = getIntent().getStringExtra("URI");
            if (!TextUtils.isEmpty(image_path)) {
                fileUri = Uri.parse(image_path);
            }
        } else {
            usernameReceived = "";
        }

        dialogTerminarSessao = new Dialog(this);
        dialogChangeProfile = new Dialog(this);
        dialogCalendario = new Dialog(this);
        eliminarContaDialog = new Dialog(this);
        desportoFavoritoDialog = new Dialog(this);
        challengePopup = new Dialog(this);
        imv = (ImageView) findViewById(R.id.imageViewSettings);

        if (fileUri != null) {
            imv.setImageURI(fileUri);
        }
        displayName = (TextView) findViewById(R.id.namePessoaMenuSettings);
        displayDesporto = (TextView) findViewById(R.id.desportoFavoritoSettings);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        databaseUsers.orderByChild("username").equalTo(usernameReceived).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    fn = String.valueOf(child.child("firstName").getValue());
                    ln = String.valueOf(child.child("lastName").getValue());

                    //TODO exibir desporto/desportos favoritos da pessoa

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
                Toast toast = Toast.makeText(getApplicationContext(), "Altere as definições do seu perfil:", Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
                toastMessage.setTextColor(getResources().getColor(R.color.BlueSeparator));
                toast.show();
            }
        });

        buttonCalendario = (Button) findViewById(R.id.buttonCalendarioSettings);
        buttonCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarioPopup();
            }
        });
        buttonCalendario.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showCalendarioPopup();
                return true;
            }
        });

        Button terminarSessao = (Button) findViewById(R.id.buttonTerminarSessao);
        terminarSessao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTerminarSessaoPopup();
            }
        });
        terminarSessao.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showTerminarSessaoPopup();
                return true;
            }
        });

        eliminarContaButton = (Button) findViewById(R.id.eliminarConta);
        eliminarContaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEliminarContaPopup();
            }
        });
        eliminarContaButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showEliminarContaPopup();
                return true;
            }
        });

        alterarDesportoFavorito = (Button) findViewById(R.id.buttonAlterarDesportoFavorito);
        alterarDesportoFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDesportoFavoritoPopup();
            }
        });
        alterarDesportoFavorito.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDesportoFavoritoPopup();
                return true;
            }
        });

        buttonAboutUs = (Button) findViewById(R.id.buttonAboutUs);
        buttonAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SobreNos.class);
                intent.putExtra("USERNAME", usernameReceived);
                intent.putExtra("URI", image_path);
                startActivity(intent);
            }
        });
        buttonAboutUs.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Uma pequena descrição de quem desenvolveu a aplicação", Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
                toastMessage.setTextColor(getResources().getColor(R.color.BlueSeparator));
                toast.show();
                return true;
            }
        });
    }

    public void showDesportoFavoritoPopup() {
        TextView close, popupId;
        final Button confirm;
        final CheckBox checkBoxCorrida, checkBoxCaminhada, checkBoxFutebol, checkBoxCiclismo;
        desportoFavoritoDialog.setContentView(R.layout.change_favorite_sport);
        desportoFavoritoDialog.getWindow().getAttributes().windowAnimations = R.style.SlideAnimation;
        close = (TextView) desportoFavoritoDialog.findViewById(R.id.txtClose);
        popupId = (TextView) desportoFavoritoDialog.findViewById(R.id.popUpId);
        confirm = (Button) desportoFavoritoDialog.findViewById(R.id.buttonConfirmar);
        checkBoxCaminhada = (CheckBox) desportoFavoritoDialog.findViewById(R.id.caminhada);
        checkBoxCorrida = (CheckBox) desportoFavoritoDialog.findViewById(R.id.corrida);
        checkBoxFutebol = (CheckBox) desportoFavoritoDialog.findViewById(R.id.futebol);
        checkBoxCiclismo = (CheckBox) desportoFavoritoDialog.findViewById(R.id.ciclismo);
        popupId.setText("Alterar o meu desporto favorito:");

        databaseUsers.orderByChild("username").equalTo(usernameReceived).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                // lista com os desportos
                ArrayList<String> array = new ArrayList<>();

                // lista para os novos desportos
                final ArrayList<String> listaCurrentSports = new ArrayList<>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    u = child.getValue(User.class);
                    if (key.equals("")) {
                        key = child.getKey();
                    }
                    array = u.getSports();
                }

                for (String str : array) {
                    switch (str) {
                        case "CAMINHADA":
                            checkBoxCaminhada.setChecked(true);
                            listaCurrentSports.add("CAMINHADA");

                            checkBoxCaminhada.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (checkBoxCaminhada.isChecked()) {
                                        listaCurrentSports.add("CAMINHADA");
                                    } else {
                                        if (listaCurrentSports.size() - 1 == 0) {
                                            checkBoxCaminhada.requestFocus();
                                            listaCurrentSports.remove("CAMINHADA");
                                        } else {
                                            listaCurrentSports.remove("CAMINHADA");
                                        }
                                    }
                                }
                            });
                            checkBoxCiclismo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (checkBoxCiclismo.isChecked()) {
                                        listaCurrentSports.add("CICLISMO");
                                    } else {
                                        if (listaCurrentSports.size() - 1 == 0) {
                                            checkBoxCiclismo.requestFocus();
                                            listaCurrentSports.remove("CICLISMO");
                                        } else {
                                            listaCurrentSports.remove("CICLISMO");
                                        }
                                    }
                                }
                            });
                            checkBoxCorrida.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (checkBoxCorrida.isChecked()) {
                                        listaCurrentSports.add("CORRIDA");
                                    } else {
                                        if (listaCurrentSports.size() - 1 == 0) {
                                            checkBoxCorrida.requestFocus();
                                            listaCurrentSports.remove("CORRIDA");
                                        } else {
                                            listaCurrentSports.remove("CORRIDA");
                                        }
                                    }
                                }
                            });
                            checkBoxFutebol.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (checkBoxFutebol.isChecked()) {
                                        listaCurrentSports.add("FUTEBOL");
                                    } else {
                                        if (listaCurrentSports.size() - 1 == 0) {
                                            checkBoxFutebol.requestFocus();
                                            listaCurrentSports.remove("FUTEBOL");
                                        } else {
                                            listaCurrentSports.remove("FUTEBOL");
                                        }
                                    }
                                }
                            });

                            break;
                        case "CORRIDA":
                            checkBoxCorrida.setChecked(true);
                            listaCurrentSports.add("CORRIDA");

                            checkBoxCorrida.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (checkBoxCorrida.isChecked()) {
                                        listaCurrentSports.add("CORRIDA");
                                    } else {
                                        if (listaCurrentSports.size() - 1 == 0) {
                                            checkBoxCorrida.requestFocus();
                                            listaCurrentSports.remove("CORRIDA");
                                        } else {
                                            listaCurrentSports.remove("CORRIDA");
                                        }
                                    }
                                }
                            });
                            checkBoxCaminhada.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (checkBoxCaminhada.isChecked()) {
                                        listaCurrentSports.add("CAMINHADA");
                                    } else {
                                        if (listaCurrentSports.size() - 1 == 0) {
                                            checkBoxCaminhada.requestFocus();
                                            listaCurrentSports.remove("CAMINHADA");
                                        } else {
                                            listaCurrentSports.remove("CAMINHADA");
                                        }
                                    }
                                }
                            });
                            checkBoxFutebol.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (checkBoxFutebol.isChecked()) {
                                        listaCurrentSports.add("FUTEBOL");
                                    } else {
                                        if (listaCurrentSports.size() - 1 == 0) {
                                            checkBoxFutebol.requestFocus();
                                            listaCurrentSports.remove("FUTEBOL");
                                        } else {
                                            listaCurrentSports.remove("FUTEBOL");
                                        }
                                    }
                                }
                            });
                            checkBoxCiclismo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (checkBoxCiclismo.isChecked()) {
                                        listaCurrentSports.add("CICLISMO");
                                    } else {
                                        if (listaCurrentSports.size() - 1 == 0) {
                                            checkBoxCiclismo.requestFocus();
                                            listaCurrentSports.remove("CICLISMO");
                                        } else {
                                            listaCurrentSports.remove("CICLISMO");
                                        }
                                    }
                                }
                            });

                            break;
                        case "CICLISMO":
                            checkBoxCiclismo.setChecked(true);
                            listaCurrentSports.add("CICLISMO");

                            checkBoxCiclismo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (checkBoxCiclismo.isChecked()) {
                                        listaCurrentSports.add("CICLISMO");
                                    } else {
                                        if (listaCurrentSports.size() - 1 == 0) {
                                            checkBoxCiclismo.requestFocus();
                                            listaCurrentSports.remove("CICLISMO");
                                        } else {
                                            listaCurrentSports.remove("CICLISMO");
                                        }
                                    }
                                }
                            });
                            checkBoxFutebol.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (checkBoxFutebol.isChecked()) {
                                        listaCurrentSports.add("FUTEBOL");
                                    } else {
                                        if (listaCurrentSports.size() - 1 == 0) {
                                            checkBoxFutebol.requestFocus();
                                            listaCurrentSports.remove("FUTEBOL");
                                        } else {
                                            listaCurrentSports.remove("FUTEBOL");
                                        }
                                    }
                                }
                            });
                            checkBoxCaminhada.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (checkBoxCaminhada.isChecked()) {
                                        listaCurrentSports.add("CAMINHADA");
                                    } else {
                                        if (listaCurrentSports.size() - 1 == 0) {
                                            checkBoxCaminhada.requestFocus();
                                            listaCurrentSports.remove("CAMINHADA");
                                        } else {
                                            listaCurrentSports.remove("CAMINHADA");
                                        }
                                    }
                                }
                            });
                            checkBoxCorrida.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (checkBoxCorrida.isChecked()) {
                                        listaCurrentSports.add("CORRIDA");
                                    } else {
                                        if (listaCurrentSports.size() - 1 == 0) {
                                            checkBoxCorrida.requestFocus();
                                            listaCurrentSports.remove("CORRIDA");
                                        } else {
                                            listaCurrentSports.remove("CORRIDA");
                                        }
                                    }
                                }
                            });

                            break;
                        case "FUTEBOL":
                            checkBoxFutebol.setChecked(true);
                            listaCurrentSports.add("FUTEBOL");

                            checkBoxFutebol.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (checkBoxFutebol.isChecked()) {
                                        listaCurrentSports.add("FUTEBOL");
                                    } else {
                                        if (listaCurrentSports.size() - 1 == 0) {
                                            checkBoxFutebol.requestFocus();
                                            listaCurrentSports.remove("FUTEBOL");
                                        } else {
                                            listaCurrentSports.remove("FUTEBOL");
                                        }
                                    }
                                }
                            });
                            checkBoxCiclismo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (checkBoxCiclismo.isChecked()) {
                                        listaCurrentSports.add("CICLISMO");
                                    } else {
                                        if (listaCurrentSports.size() - 1 == 0) {
                                            checkBoxCiclismo.requestFocus();
                                            listaCurrentSports.remove("CICLISMO");
                                        } else {
                                            listaCurrentSports.remove("CICLISMO");
                                        }
                                    }
                                }
                            });
                            checkBoxCorrida.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (checkBoxCorrida.isChecked()) {
                                        listaCurrentSports.add("CORRIDA");
                                    } else {
                                        if (listaCurrentSports.size() - 1 == 0) {
                                            checkBoxCorrida.requestFocus();
                                            listaCurrentSports.remove("CORRIDA");
                                        } else {
                                            listaCurrentSports.remove("CORRIDA");
                                        }
                                    }
                                }
                            });
                            checkBoxCaminhada.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (checkBoxCaminhada.isChecked()) {
                                        listaCurrentSports.add("CAMINHADA");
                                    } else {
                                        if (listaCurrentSports.size() - 1 == 0) {
                                            checkBoxCaminhada.requestFocus();
                                            listaCurrentSports.remove("CAMINHADA");
                                        } else {
                                            listaCurrentSports.remove("CAMINHADA");
                                        }
                                    }
                                }
                            });

                            break;
                        default:
                            break;
                    }
                }
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseUsers.child(key).child("sports").setValue(listaCurrentSports);
                        desportoFavoritoDialog.dismiss();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        close.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                desportoFavoritoDialog.dismiss();
            }
        });
        desportoFavoritoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        desportoFavoritoDialog.show();
    }

    public void showEliminarContaPopup() {
        Button yesButton;
        Button noButton;
        TextView close;
        TextView popupId;
        eliminarContaDialog.setContentView(R.layout.popup_eliminar_conta);
        eliminarContaDialog.getWindow().getAttributes().windowAnimations = R.style.FadeAnimation;
        yesButton = (Button) eliminarContaDialog.findViewById(R.id.yesButton);
        noButton = (Button) eliminarContaDialog.findViewById(R.id.noButton);
        close = (TextView) eliminarContaDialog.findViewById(R.id.txtClose);
        popupId = (TextView) eliminarContaDialog.findViewById(R.id.popUpId);
        popupId.setText("Ao eliminar a sua conta os seus dados serão eliminados dos nossos servidores. Tem a certeza?");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarContaDialog.dismiss();
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseUsers.orderByChild("username").equalTo(usernameReceived).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            String id = child.getKey();
                            databaseUsers.child(id).removeValue();
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
                startActivity(new Intent(getApplicationContext(), LoginScreen.class));
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarContaDialog.dismiss();
            }
        });
        eliminarContaDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        eliminarContaDialog.show();
    }

    public void showTerminarSessaoPopup() {
        Button yesButton;
        Button noButton;
        TextView close;
        TextView popupId;
        dialogTerminarSessao.setContentView(R.layout.popup_terminar_sessao);
        dialogTerminarSessao.getWindow().getAttributes().windowAnimations = R.style.FadeAnimation;
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
                Toast.makeText(SettingsActivity.this, "Alterou os dados com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });
        dialogChangeProfile.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogChangeProfile.show();
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
        dialogCalendario.getWindow().getAttributes().windowAnimations = R.style.SlideAnimation;
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

    private void goToMainMenu() {
        Intent i = new Intent(this, MenuPrincipal.class);
        i.putExtra("USERNAME", usernameReceived);
        i.putExtra("URI", image_path);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.BackButton:
                goToMainMenu();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        goToMainMenu();
    }
}