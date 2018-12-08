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

    public AchievementsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Conquistas:");
        View v = inflater.inflate(R.layout.fragment_achievements, container, false);
        tv = v.findViewById(R.id.valorTextViewPrimeiraCaixaCaminhada);
        final String username = getArguments().getString("USERNAME");




        databaseCorrida = FirebaseDatabase.getInstance().getReference("corrida");

        databaseCorrida.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valores = "";
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Corrida c = userSnapshot.getValue(Corrida.class);
                    if (c.getUsername().equals(username) &&  c.getDistancia() > maiorDistancia){
                        maiorDistancia = c.getDistancia();
                    }

                        tv.setText(Double.toString(maiorDistancia));

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        return v;
    }
}