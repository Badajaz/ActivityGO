package com.example.android.activitygo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.service.notification.NotificationListenerService;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.activitygo.model.Challenge;
import com.example.android.activitygo.model.Corrida;
import com.example.android.activitygo.model.Grupo;
import com.example.android.activitygo.model.Ranking;
import com.example.android.activitygo.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RankingsFragment extends Fragment {


    private static final String TAG = "RankingsFragment";
    private Button classificacaoCorridaGeral;
    private Button classificacaoCiclismoGeral;
    private Button classificacaoCorridaGrupo;
    private Button classificacaoCiclismoGrupo;
    private DatabaseReference databaseRankings;
    private DatabaseReference databaseCorridas;
    private DatabaseReference databaseUsers;
    private Corrida c;
    private User u;
    private Ranking r;
    private String username;
    private String[] rankingsNomes;
    private ArrayList<String> actualizada;

    public RankingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rankings, container, false);

        ((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Rankings:");
        username = getArguments().getString("USERNAME");

        databaseRankings = FirebaseDatabase.getInstance().getReference("rankings");
        databaseCorridas = FirebaseDatabase.getInstance().getReference("corrida");
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        classificacaoCorridaGeral = (Button) v.findViewById(R.id.buttonCorridaGeral);

        databaseUsers.orderByChild("username").addListenerForSingleValueEvent(new ValueEventListener() {

            ArrayList<User> listaUsers = new ArrayList<User>();


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    u = child.getValue(User.class);
                    if (u.getPontos() >= 0) {
                        // lista inversa com os users para obter os pontos de cada
                        listaUsers.add(u);
                        // Collections.reverse(listaUsers);
                    }
                }
                rankingsNomes = new String[listaUsers.size()];


                for (int i = 0; i < listaUsers.size(); i++) {

                    if (i == 0) {
                        rankingsNomes[0] = listaUsers.get(0).getUsername();
                    } else {
                        for (int j = 0; j < i; j++) {
                            if (listaUsers.get(j).getPontos() < listaUsers.get(i).getPontos()) {
                                String aux = rankingsNomes[j];
                                rankingsNomes[j] = listaUsers.get(i).getUsername();
                                rankingsNomes[i] = aux;

                            } else {
                                rankingsNomes[i] = listaUsers.get(i).getUsername();
                            }
                        }
                    }

                }

                actualizada = new ArrayList<String>();
                actualizada.addAll(convertListaToArratList(rankingsNomes));


                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.hasChild("rankings")) {
                            Ranking r = new Ranking("corrida", actualizada);
                            String id = databaseRankings.push().getKey();
                            databaseRankings.child(id).setValue(r);
                        } else {
                            databaseRankings.orderByChild("desporto").equalTo("corrida").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                                        Ranking r = child.getValue(Ranking.class);
                                        databaseRankings.child(child.getKey()).child("rankings").setValue(actualizada);
                                    }

                                    classificacaoCorridaGeral.setText("Classificacao: " + getRankingUser(username,actualizada) + "/" + actualizada.size());


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                // OBTER RANKING GERAL CORRIDA
                /*databaseRankings.orderByChild("desporto").equalTo("corrida").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                r = child.getValue(Ranking.class);

                                // lista dos rankings de corrida
                                final ArrayList<String> lista = r.getRankings();
                                int lugar = lista.indexOf(username) + 1;
                                int total = lista.size();
                                classificacaoCorridaGeral.setText("Classificacao: " + lugar + "/" + total);
                            }
                        } else {
                            Ranking r = new Ranking("corrida", listaUsers);
                            String id = databaseRankings.push().getKey();
                            databaseRankings.child(id).setValue(r);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/
                /*
                Collections.reverse(listaUsers);

                //Ranking r = new Ranking("Classificacao Geral Corrida", "corrida", listaUsers);
                Ranking r = new Ranking("corrida", listaUsers);
                String id = databaseRankings.push().getKey();
                databaseRankings.child(id).setValue(r);*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        classificacaoCorridaGeral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment SelectedFragment = new TableRankingsFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, SelectedFragment, "RankingsFragment");
                ft.addToBackStack("RankingsFragment");
                ft.commit();
            }
        });
        return v;
    }


    private ArrayList convertListaToArratList(String[] array) {
        ArrayList<String> novo = new ArrayList<>();
        for (String s : array) {
            novo.add(s);
        }

        return novo;
    }


    private int getRankingUser(String username, ArrayList<String> rank) {
        int i;
        for (i = 0; i < rank.size(); i++) {
            if (rank.equals(username)) {
                return i;
            }
        }

        return i;
    }


}