package com.example.android.activitygo;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.activitygo.model.Challenge;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ChalengeFragment extends Fragment {

    private String nomeChallenge;
    private String username;
    private String descricaoChallenge;
    private String dataChallenge;
    private DatabaseReference databaseChallenges;
    private DatabaseReference databaseAllChallenges;

    public ChalengeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Desafios:");
        View v = inflater.inflate(R.layout.fragment_chalenge, container, false);

        databaseChallenges = FirebaseDatabase.getInstance().getReference("challenges");
        databaseAllChallenges = FirebaseDatabase.getInstance().getReference("all-challenges");

        Bundle extras = getArguments();
        if (extras != null) {
            username = extras.getString("USERNAME");
        } else {
            username = "";
        }

        ProgressBar corrida = (ProgressBar) v.findViewById(R.id.progressBarCorrida);
        corrida.setMax(5);
        corrida.setProgress(3);
        corrida.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FFA500")));

        databaseAllChallenges.orderByChild("Corrida").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*
                Random randomGenerator = new Random();
                int index = randomGenerator.nextInt(listaCorrida.size());
                String randomValue = listaCorrida.get(index);

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c);
                String id = databaseChallenges.push().getKey();
                Challenge ch = new Challenge("Corrida", randomValue, formattedDate);
                databaseChallenges.child(id).setValue(ch);*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }
}
