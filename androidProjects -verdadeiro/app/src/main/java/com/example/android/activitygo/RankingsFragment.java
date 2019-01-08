package com.example.android.activitygo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.activitygo.model.Corrida;
import com.example.android.activitygo.model.Grupo;
import com.example.android.activitygo.model.Ranking;
import com.example.android.activitygo.model.TableRankingsGroups;
import com.example.android.activitygo.model.User;
import com.example.android.activitygo.model.RankingGroups;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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


    private HashMap<String, Integer> NomesPontos = new HashMap<>();
    private DatabaseReference databaseGrupo;
    private DatabaseReference databaseRankingsGrupos;

    public RankingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rankings, container, false);

       // ((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Rankings:");
        username = getArguments().getString("USERNAME");

        databaseRankings = FirebaseDatabase.getInstance().getReference("rankings");
        databaseCorridas = FirebaseDatabase.getInstance().getReference("corrida");
        databaseRankingsGrupos = FirebaseDatabase.getInstance().getReference("rankingGrupos");
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        classificacaoCorridaGeral = (Button) v.findViewById(R.id.buttonCorridaGeral);
        classificacaoCorridaGrupo = (Button) v.findViewById(R.id.buttonCorridaParaGrupos);

        databaseGrupo = FirebaseDatabase.getInstance().getReference("grupos");


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

                int[] listForSort = new int[listaUsers.size()];
                actualizada = new ArrayList<>();


                for (int i = 0; i < listaUsers.size(); i++) {
                    listForSort[i] = listaUsers.get(i).getPontos();
                }

                Arrays.sort(listForSort);

                ArrayList<String> nomesRepetidos = new ArrayList<>();


                for (int j = 0; j < listForSort.length; j++) {
                    for (int k = 0; k < listForSort.length; k++) {
                        if (listForSort[j] == listaUsers.get(k).getPontos() && nomesRepetidos.indexOf(listaUsers.get(k).getUsername()) == -1) {
                            actualizada.add(listaUsers.get(k).getUsername());
                            nomesRepetidos.add(listaUsers.get(k).getUsername());
                            NomesPontos.put(listaUsers.get(k).getUsername(), listaUsers.get(k).getPontos());
                        }
                    }
                }


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
                                    Collections.reverse(actualizada);
                                    classificacaoCorridaGeral.setText("Classificacao: " + getRankingUser(username, actualizada) + "/" + actualizada.size());


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

        databaseGrupo.orderByChild("nome").addValueEventListener(new ValueEventListener() {
            private Map<String, Integer> rankingGruposPontos = new HashMap<>();
            private Grupo g;
            private int pontucaototal = 0;



            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                     g = child.getValue(Grupo.class);


                    databaseUsers.orderByChild("username").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                User u = child.getValue(User.class);

                                if (g.getElementosGrupo().indexOf(u)!= -1){
                                    pontucaototal += u.getPontos();
                                }
                            }




                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                rankingGruposPontos.put(g.getNome(),pontucaototal);
                String id = databaseRankingsGrupos.push().getKey();
                RankingGroups rank = new RankingGroups(rankingGruposPontos);
                databaseRankingsGrupos.child(id).setValue(rank);
                pontucaototal = 0;
                //rankingGruposPontos.put(g.getNome(),)


            }


            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });


        classificacaoCorridaGeral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("hashmap", NomesPontos);
                bundle.putSerializable("listaNomes", actualizada);
                Fragment SelectedFragment = new TableRankingsFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SelectedFragment.setArguments(bundle);
                ft.replace(R.id.fragment_container, SelectedFragment, "RankingsFragment");
                ft.addToBackStack("RankingsFragment");
                ft.commit();
            }
        });


        classificacaoCorridaGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                //bundle.putSerializable("hashmap",NomesPontos);
                //bundle.putSerializable("listaNomes",actualizada);
                Fragment SelectedFragment = new TableRankingsGroups();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SelectedFragment.setArguments(bundle);
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
        int user = -1;
        for (int i = 0; i < rank.size(); i++) {
            if (rank.get(i).equals(username)) {
                user = i + 1;
            }
        }

        return user;
    }





}