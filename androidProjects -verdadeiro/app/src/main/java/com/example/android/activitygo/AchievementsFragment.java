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

    public AchievementsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Conquistas:");
        View v = inflater.inflate(R.layout.fragment_achievements, container, false);
        tv = v.findViewById(R.id.valorTextViewPrimeiraCaixaCaminhada);


        databaseCorrida = FirebaseDatabase.getInstance().getReference("corrida");

        databaseCorrida.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valores = "";
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Corrida c = userSnapshot.getValue(Corrida.class);
                    if (c.getData().equals("8/11/2018")){
                        tv.setText(Double.toString(c.getDistancia()));
                    }




                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        return v;
    }
}