package com.example.android.activitygo;

import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.activitygo.model.Grupo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListaDeElementosJuntarGrupo extends Fragment {

    private TextView elementosGrupo;
    public static final String CHANNEL_1_ID = "channel1";
    private String nomeGrupo;
    private DatabaseReference databaseGrupo;
    private DatabaseReference databaseUsers;
    private Dialog dialogConfirmJoin;
    private NotificationManagerCompat notificationManager;

    private String username;
    private String ele;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lista_de_elementos_juntar_grupo, container, false);

        elementosGrupo = (TextView) v.findViewById(R.id.ElementosGrupo);
        dialogConfirmJoin = new Dialog(getContext());
        final Button join = (Button) v.findViewById(R.id.juntarPopup);

        nomeGrupo = getArguments().getString("NOMEGRUPO");
        username = getArguments().getString("USERNAME");
        databaseGrupo = FirebaseDatabase.getInstance().getReference("grupos");
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        databaseGrupo.addValueEventListener(new ValueEventListener() {
            private String valores = "";
            private Grupo g;
            private ArrayList<String> membros = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    g = userSnapshot.getValue(Grupo.class);
                    if (g.getNome().equals(nomeGrupo)) {
                        if (g.getElementosGrupo().size() == 1) {
                            membros.add(g.getElementosGrupo().get(0));
                            valores = g.getElementosGrupo().get(0);
                        } else {
                            for (String elem : g.getElementosGrupo()) {
                                membros.add(elem);
                                valores += elem + " ";
                            }
                        }
                    }
                }
                /*
                String[] elementos = valores.split(" ");
                String displayElem = "";
                for (String elem : elementos) {
                    displayElem += elem + "\n";
                    elementosGrupo.setText(displayElem);
                }*/

                String displayElem = "";
                for (String elem : membros) {
                    displayElem += elem + "\n";
                    elementosGrupo.setText(displayElem);
                }

                join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmJoin(nomeGrupo);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }

    public void confirmJoin(String grupo) {
        Button okButton;
        TextView close;
        TextView popupId;
        dialogConfirmJoin.setContentView(R.layout.popupjuntargrupo);
        dialogConfirmJoin.getWindow().getAttributes().windowAnimations = R.style.FadeAnimation;
        okButton = (Button) dialogConfirmJoin.findViewById(R.id.okButton);
        Button cancelButton = (Button) dialogConfirmJoin.findViewById(R.id.cancelButton);
        close = (TextView) dialogConfirmJoin.findViewById(R.id.txtClose);
        popupId = (TextView) dialogConfirmJoin.findViewById(R.id.popUpId);
        popupId.setText("\nTem a certeza que se quer juntar a " + grupo + "?");

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirmJoin.dismiss();
            }
        });
        final String group = grupo;

        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                databaseGrupo.orderByChild("nome").equalTo(group).addListenerForSingleValueEvent(new ValueEventListener() {
                    private String displayElem;
                    private String quemQuer = "";
                    private int querEntrar;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean encontrou = false;
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Grupo g = child.getValue(Grupo.class);
                            if (g.getNome().equals(group)) {
                                for (String elem : g.getElementosGrupo()) {
                                    if (elem.equals(username)) { // estou a tentar entrar no meu proprio grupo
                                        encontrou = false;
                                        Toast.makeText(getContext(), "Já pertence a este grupo", Toast.LENGTH_SHORT).show();
                                        break;
                                    } else { // outra pessoa a tentar entrar no grupo criado pelo criador
                                        encontrou = true;
                                    }
                                }
                            }

                            if (encontrou == true) {
                                querEntrar = 1;
                                quemQuer = username;
                                /*
                                ArrayList<String> arrayGrupoNovo = new ArrayList<>();
                                arrayGrupoNovo.addAll(g.getElementosGrupo());
                                arrayGrupoNovo.add(username);

                                databaseGrupo.child(child.getKey()).child("elementosGrupo").setValue(arrayGrupoNovo);
                                */
                                databaseGrupo.child(child.getKey()).child("quemQuer").setValue(username);
                                databaseGrupo.child(child.getKey()).child("querEntrar").setValue(querEntrar);

                                /*
                                ele = "";
                                for (String str : arrayGrupoNovo) {
                                    ele += str + "\n";
                                }
                                elementosGrupo.setText(ele);
                                */
                            }
                        }
                        // elementosGrupo.setText("");
                        dialogConfirmJoin.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirmJoin.dismiss();
            }
        });
        dialogConfirmJoin.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogConfirmJoin.show();
    }
}