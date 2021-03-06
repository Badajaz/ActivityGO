package com.example.android.activitygo;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.activitygo.model.Corrida;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AchievementsFragment extends Fragment {

    private DatabaseReference databaseCorrida;
    private TextView tv;
    private double maiorDistancia = 0.0;
    private TextView tvdata;
    private String melhorKm = "00:00";
    private String melhor2Km = "00:00";
    private String melhor5Km = "00:00";
    private TextView melhorKmTv;
    private String dataDistancia;
    private String melhorKmData;
    private String melhor2KmData;
    private String melhor5KmData;
    private TextView melhorkmDataTv;
    private int count = 0;
    private double accKm = 0.0;
    private TextView Totalkm;
    private TextView melhor2KmTv;
    private TextView melhor2kmDataTv;
    private TextView melhor5KmTv;
    private TextView melhor5kmDataTv;

    public AchievementsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Conquistas:");
        View v = inflater.inflate(R.layout.fragment_achievements, container, false);
        tv = v.findViewById(R.id.valorTextViewPrimeiraCaixaCaminhada);
        tvdata = v.findViewById(R.id.dataTextViewPrimeiraCaixaCaminhada);
        melhorKmTv = v.findViewById(R.id.melhorKmTempo);
        melhorkmDataTv = v.findViewById(R.id.melhorKmData);
        Totalkm = v.findViewById(R.id.TotalKmPercorridosValor);


        melhor2KmTv = v.findViewById(R.id.melhorKmTempo2);
        melhor2kmDataTv = v.findViewById(R.id.melhorKmData2);

        melhor5KmTv = v.findViewById(R.id.melhorKmTempo5);
        melhor5kmDataTv = v.findViewById(R.id.melhorKmData5);


        final String username = getArguments().getString("USERNAME");


        databaseCorrida = FirebaseDatabase.getInstance().getReference("corrida");

        databaseCorrida.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valores = "";
                boolean first = false;
                boolean second = false;
                boolean fifth = false;

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Corrida c = userSnapshot.getValue(Corrida.class);
                    if (c.getUsername().equals(username)) {
                        accKm += c.getDistancia();
                    }

                    if (c.getUsername().equals(username) && c.getDistancia() > maiorDistancia) {
                        maiorDistancia = c.getDistancia();
                        dataDistancia = c.getData();

                    }

                    if (c.getDistancia() >= 1000 && c.getUsername().equals(username)) {
                        if (first == false) {
                            melhorKm = c.getMelhorkm();
                            melhorKmData = c.getData();
                            first = true;
                        } else {


                            if (isLower(melhorKm, c.getMelhorkm())) {
                                melhorKm = c.getMelhorkm();
                                melhorKmData = c.getData();
                            }

                        }


                        //count ++;

                    }


                    if (c.getDistancia() >= 2000 && c.getUsername().equals(username)) {
                        if (second == false) {
                            melhor2Km = c.getMelhorSegundokm();
                            melhor2KmData = c.getData();
                            second = true;
                        } else {


                            if (isLower(melhor2Km, c.getMelhorSegundokm())) {
                                melhor2Km = c.getMelhorSegundokm();
                                melhor2KmData = c.getData();
                            }

                        }


                        //count ++;

                    }


                    if (c.getDistancia() >= 5000 && c.getUsername().equals(username)) {
                        if (fifth == false) {
                            melhor5Km = c.getMelhorQuintokm();
                            melhor5KmData = c.getData();
                            fifth = true;
                        } else {


                            if (isLower(melhor5Km, c.getMelhorSegundokm())) {
                                melhor5Km = c.getMelhorQuintokm();
                                melhor5KmData = c.getData();
                            }

                        }


                        //count ++;

                    }


                }


                tv.setText(Double.toString(Math.round(maiorDistancia)));
                tvdata.setText(dataDistancia);
                melhorKmTv.setText(melhorKm);
                melhorkmDataTv.setText(melhorKmData);
                Totalkm.setText("" + accKm);
                melhor2KmTv.setText(melhor2Km);
                melhor2kmDataTv.setText(melhor2KmData);
                melhor5KmTv.setText(melhor5Km);
                melhor5kmDataTv.setText(melhor5KmData);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }

    public boolean isLower(String actual, String ciclo) {

        String[] Atime = actual.split(":"); // min,seg
        String[] Ctime = ciclo.split(":"); // min,seg

        if (Integer.parseInt(Atime[0]) > Integer.parseInt(Ctime[0])) {
            return true;
        } else {
            if (Integer.parseInt(Atime[0]) == Integer.parseInt(Ctime[0])) {
                if (Integer.parseInt(Atime[1]) > Integer.parseInt(Ctime[1])) {
                    return true;
                } else {
                    return false;
                }

            } else {
                return false;
            }
        }


    }














    /*

    public boolean isLower(String actual, String cycle) {
        boolean a = false;

        if (count ==0) {

            int aaa = Integer.parseInt(actual.substring(0, 3));
            int bbb = Integer.parseInt(actual.substring(5, 6));


            int cycleMin = Integer.parseInt(cycle.substring(0, 2));
            int cycleSec = Integer.parseInt(cycle.substring(4, 5));

            if (cycleMin < aaa) {
                a = true;
            } else {

                if (cycleMin == aaa) {
                    if (cycleSec < bbb) {
                        a = true;
                    }
                }

            }

            count ++;
        }else {

            int actualMin = Integer.parseInt(actual.substring(0, 2));
            int actualSec = Integer.parseInt(actual.substring(3, 5));

            int cycleMin = Integer.parseInt(cycle.substring(0, 2));
            int cycleSec = Integer.parseInt(cycle.substring(3, 5));

            if (cycleMin < actualMin) {
                a = true;
            } else {

                if (cycleMin == actualMin) {
                    if (cycleSec < actualSec) {
                        a = true;
                    }
                }


            }

        }
        return a;
    }*/


}