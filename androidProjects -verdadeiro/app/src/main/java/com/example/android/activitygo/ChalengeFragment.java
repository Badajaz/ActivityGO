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
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.activitygo.model.Challenge;
import com.example.android.activitygo.model.Grupo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
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

    private TextView primeiratv;
    private TextView segundatv;
    private TextView terceiratv;

    private ArrayList<String> descricaoChallenges = new ArrayList<>();


    public ChalengeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Desafios:");
        View v = inflater.inflate(R.layout.fragment_chalenge, container, false);

        databaseChallenges = FirebaseDatabase.getInstance().getReference("challenges");
        //databaseAllChallenges = FirebaseDatabase.getInstance().getReference("all-challenges");
        primeiratv = v.findViewById(R.id.descricaoTextViewPrimeiraCaixaCaminhad);
        segundatv = v.findViewById(R.id.secondChallenge);
        terceiratv = v.findViewById(R.id.ThirdChallenge);


        Bundle extras = getArguments();
        if (extras != null) {
            username = extras.getString("USERNAME");
        } else {
            username = "";
        }

        Random randomChallenge = new Random();

        String[] chalenges = {"Faça 2 km", "Faça 2 km antes dos 10 minutos", "Faça uma corrida de 30 minutos", "Faça uma corrida de 10 min", "Faça uma corrida de 5km",
                "Faça 7km", "Faça 3km em 15 min"};

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        for (int i = 0; i < 3; i++) {

            int index = randomChallenge.nextInt(chalenges.length);
            String id = databaseChallenges.push().getKey();
            Challenge c = new Challenge(username, "corrida", chalenges[index], dateFormat.format(date));
            databaseChallenges.child(id).setValue(c);

        }


        databaseChallenges.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valores = "";
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Challenge c = userSnapshot.getValue(Challenge.class);
                    if (c.getmUsername().equals(username)) {
                        descricaoChallenges.add(c.getDescricao());
                    }

                }

                primeiratv.setText(descricaoChallenges.get(0));
                segundatv.setText(descricaoChallenges.get(1));
                terceiratv.setText(descricaoChallenges.get(2));

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        ProgressBar corrida = (ProgressBar) v.findViewById(R.id.progressBarCorrida);
        corrida.setMax(5);
        corrida.setProgress(3);
        corrida.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FFA500")));

       /* databaseAllChallenges.orderByChild("Corrida").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Random randomGenerator = new Random();
                int index = randomGenerator.nextInt(listaCorrida.size());
                String randomValue = listaCorrida.get(index);

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c);
                String id = databaseChallenges.push().getKey();
                Challenge ch = new Challenge("Corrida", randomValue, formattedDate);
                databaseChallenges.child(id).setValue(ch);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        return v;
    }
}
