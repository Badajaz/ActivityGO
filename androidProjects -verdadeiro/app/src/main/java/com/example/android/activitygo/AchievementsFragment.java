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
    private String melhorKm = "4000:00";
    private TextView melhorKmTv;
    private String dataDistancia;
    private String melhorKmData;
    private TextView melhorkmDataTv;
    private int count = 0;

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
        melhorkmDataTv= v.findViewById(R.id.melhorKmData);



        final String username = getArguments().getString("USERNAME");


        databaseCorrida = FirebaseDatabase.getInstance().getReference("corrida");

        databaseCorrida.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valores = "";

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Corrida c = userSnapshot.getValue(Corrida.class);
                    if (c.getUsername().equals(username) && c.getDistancia() > maiorDistancia) {
                        maiorDistancia = c.getDistancia();
                        dataDistancia = c.getData();

                    }

                    if (c.getDistancia() > 1000 && isLower(melhorKm, c.getMelhorkm())) {
                        melhorKm = c.getMelhorkm();
                        melhorKmData = c.getData();
                        //count ++;

                    }


                }


                tv.setText(Double.toString(maiorDistancia));
                tvdata.setText(dataDistancia);
                melhorKmTv.setText(melhorKm);
                melhorkmDataTv.setText(melhorKmData);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return v;
    }

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
    }


}