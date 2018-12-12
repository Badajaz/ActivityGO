package com.example.android.activitygo;

import android.app.Notification;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.activitygo.model.Challenge;
import com.example.android.activitygo.model.Corrida;
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
    private DatabaseReference databaseDesafios;

    public static final String CHANNEL_1_ID = "channel1";

    private NotificationManagerCompat notificationManager;
    private DatabaseReference databaseCorrida;

    public ChalengeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Desafios:");
        View v = inflater.inflate(R.layout.fragment_chalenge, container, false);

        databaseChallenges = FirebaseDatabase.getInstance().getReference("challenges");
        databaseCorrida = FirebaseDatabase.getInstance().getReference("corrida");
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


        databaseChallenges.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String valores = "";
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Challenge c = userSnapshot.getValue(Challenge.class);
                    if (!c.getData().equals(dateFormat.format(date))) {
                        databaseChallenges.removeValue();
                        FirebaseDatabase.getInstance().getReference().child("desafios").setValue(0);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        ValueEventListener ola = FirebaseDatabase.getInstance().getReference().child("desafios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("DESAFIOS", "" + snapshot.getValue());
                if (snapshot.getValue() != null) {
                    if (Long.valueOf(snapshot.getValue().toString()) == 0) {
                        Toast.makeText(getContext(), "" + snapshot.getValue(), Toast.LENGTH_SHORT).show();
                        Log.d("DESAFIOS1", "" + snapshot.getValue());
                        Random randomChallenge = new Random();
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date();
                        String[] chalenges = {"Faça 2 km","Faça 2 km","Faça 2 km","Faça 2 km","Faça 2 km","Faça 2 km","Faça 2 km","Faça 2 km", "Faça 2 km antes dos 10 minutos", "Faça uma corrida de 30 minutos", "Faça uma corrida de 10 min", "Faça uma corrida de 5km",
                                "Faça 7km", "Faça 3km em 15 min"};

                        int[] points = {100,100,100,100,100,100,100,100, 200, 200, 50, 500, 1000, 2000};
                        for (int i = 0; i < 3; i++) {

                            int index = randomChallenge.nextInt(chalenges.length);
                            Log.d("DESAFIOS2", "" + snapshot.getValue());
                            String id = databaseChallenges.push().getKey();
                            Challenge c = new Challenge(username, "corrida", chalenges[index], dateFormat.format(date), points[index]);
                            databaseChallenges.child(id).setValue(c);

                            //NOTIFICACOES PARA NOVOS DESAFIOS
                            notificationManager = NotificationManagerCompat.from(getActivity());
                            Notification notification = new NotificationCompat.Builder(getActivity(), CHANNEL_1_ID)
                                    .setSmallIcon(R.drawable.notification_icon)
                                    .setContentTitle("Tem novas challenges!")
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                    .build();

                            notificationManager.notify(1, notification);

                            FirebaseDatabase.getInstance().getReference().child("desafios").setValue(1);
                        }
                    }
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

                        for (String s : descricaoChallenges) {
                            if (s.equals("Faça 2 km")) {

                                databaseCorrida.addValueEventListener(new ValueEventListener() {

                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date = new Date();
                                    String d = dateFormat.format(date);
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        double distanciaFeita = 0.0;
                                        String valores = "";
                                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                            Corrida c = userSnapshot.getValue(Corrida.class);
                                            if (c.getUsername().equals(username) && c.getData().equals(d)) {
                                                distanciaFeita += c.getDistancia();


                                            }
                                        }

                                        if (distanciaFeita >= 10) {


                                            if (primeiratv.getText().equals("Faça 2 km")){
                                                ConstraintLayout c = getView().findViewById(R.id.firstConstrainte);
                                                c.setBackgroundColor(Color.BLUE);

                                            }

                                            if (segundatv.getText().equals("Faça 2 km")){
                                                ConstraintLayout c = getView().findViewById(R.id.secondConstrainte);
                                                c.setBackgroundColor(Color.BLUE);
                                            }

                                            if (terceiratv.getText().equals("Faça 2 km")){
                                                ConstraintLayout c = getView().findViewById(R.id.ThirdConstrainte);
                                                c.setBackgroundColor(Color.BLUE);

                                            }

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



        databaseChallenges.removeEventListener(ola);

        ProgressBar corrida = (ProgressBar) v.findViewById(R.id.progressBarCorrida);
        corrida.setMax(5);
        corrida.setProgress(3);
        corrida.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FFA500")));
        return v;
    }


}





