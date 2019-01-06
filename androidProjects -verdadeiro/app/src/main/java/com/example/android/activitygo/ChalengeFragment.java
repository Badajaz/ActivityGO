package com.example.android.activitygo;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.activitygo.model.Challenge;
import com.example.android.activitygo.model.Corrida;
import com.example.android.activitygo.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class ChalengeFragment extends Fragment {

    public static final String CHANNEL_1_ID = "channel1";
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
    private NotificationManagerCompat notificationManager;
    private DatabaseReference databaseCorrida;
    private boolean inseriu = false;
    private ValueEventListener o;
    private Drawable background;
    private DatabaseReference databaseUsers;

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
        databaseDesafios = FirebaseDatabase.getInstance().getReference("desafios");
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        //databaseAllChallenges = FirebaseDatabase.getInstance().getReference("all-challenges");
        //primeiratv = v.findViewById(R.id.descricaoTextViewPrimeiraCaixaCaminhad);
        //segundatv = v.findViewById(R.id.secondChallenge);
        //terceiratv = v.findViewById(R.id.ThirdChallenge);

        Bundle extras = getArguments();
        if (extras != null) {
            username = extras.getString("USERNAME");
        } else {
            username = "";
        }


        final Button obterChallenge = v.findViewById(R.id.getChallenge);
        //final RelativeLayout chalengeFrame = v.findViewById(R.id.chalengeFrame);
        primeiratv = v.findViewById(R.id.descricaoTextViewPrimeiraCaixaCaminhad);
        segundatv = v.findViewById(R.id.secondChallenge);
        terceiratv = v.findViewById(R.id.ThirdChallenge);
        ConstraintLayout c = (ConstraintLayout) v.findViewById(R.id.ThirdConstrainte);
        background = c.getBackground();


        obterChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random randomChallenge = new Random();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String[] chalenges = {"Faça 2 km", "Faça 3 km", "Faça 5 km", "Faça 7 km", "Faça 10 km", "Corra 10 minutos", "Corra 20 minutos", "Corra 30 minutos", "Faça 100m em menos de 9 segundos", "Faça 200m em menos de 18 segundos"};

                int[] points = {100, 200, 300, 500, 700, 50, 200, 400, 300, 500};

                int index = randomChallenge.nextInt(chalenges.length);
                String id = databaseChallenges.push().getKey();
                Challenge c = new Challenge(username, "corrida", chalenges[index], dateFormat.format(date), points[index], 0);
                databaseChallenges.child(id).setValue(c);
            }

        });


        o = databaseChallenges.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> challengeDesricao = new ArrayList<>();

                int count = 0;
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Challenge c = userSnapshot.getValue(Challenge.class);
                    if (c.getmUsername().equals(username)) {
                        challengeDesricao.add(c.getDescricao());

                        if (count == 0) {
                            primeiratv.setText(c.getDescricao());
                        }

                        if (count == 1) {
                            segundatv.setText(c.getDescricao());
                        }

                        if (count == 2) {
                            terceiratv.setText(c.getDescricao());
                            obterChallenge.setVisibility(View.GONE);
                        }
                        count++;
                    }
                }

                for (String s : challengeDesricao) {
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

                                if (distanciaFeita >= 2000) {
                                    int lime = getResources().getColor(R.color.orange);
                                    databaseChallenges.orderByChild("mUsername").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                                Challenge c = child.getValue(Challenge.class);
                                                if (c.getDescricao().equals("Faça 2 km")) {
                                                    databaseChallenges.child(child.getKey()).child("completed").setValue(1);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });

                                    if (primeiratv.getText().equals("Faça 2 km")) {
                                        ConstraintLayout c = getView().findViewById(R.id.firstConstrainte);
                                        c.setBackgroundColor(lime);
                                    }

                                    if (segundatv.getText().equals("Faça 2 km")) {
                                        ConstraintLayout c = getView().findViewById(R.id.secondConstrainte);
                                        c.setBackgroundColor(lime);
                                    }

                                    if (terceiratv.getText().equals("Faça 2 km")) {
                                        ConstraintLayout c = getView().findViewById(R.id.ThirdConstrainte);
                                        c.setBackgroundColor(lime);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }

                    if (s.equals("Faça 3 km")) {

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

                                if (distanciaFeita >= 3000) {
                                    int lime = getResources().getColor(R.color.orange);
                                    databaseChallenges.orderByChild("mUsername").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                                Challenge c = child.getValue(Challenge.class);
                                                if (c.getDescricao().equals("Faça 3 km")) {
                                                    databaseChallenges.child(child.getKey()).child("completed").setValue(1);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                    if (primeiratv.getText().equals("Faça 3 km")) {
                                        ConstraintLayout c = getView().findViewById(R.id.firstConstrainte);
                                        c.setBackgroundColor(lime);
                                    }

                                    if (segundatv.getText().equals("Faça 3 km")) {
                                        ConstraintLayout c = getView().findViewById(R.id.secondConstrainte);
                                        c.setBackgroundColor(lime);
                                    }

                                    if (terceiratv.getText().equals("Faça 3 km")) {
                                        ConstraintLayout c = getView().findViewById(R.id.ThirdConstrainte);
                                        c.setBackgroundColor(lime);
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


        final ConstraintLayout conn = (ConstraintLayout) v.findViewById(R.id.ThirdConstrainte);

        conn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lime = getResources().getColor(R.color.orange);
                //conn.getBackground()
                databaseChallenges.orderByChild("mUsername").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    Challenge c;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            c = child.getValue(Challenge.class);
                            if (c.getDescricao().equals(terceiratv.getText().toString()) && c.getCompleted() == 1) {
                                String id = child.getKey();
                                databaseChallenges.child(id).removeValue();
                                terceiratv.setText("");

                                databaseUsers.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                                            User u = child.getValue(User.class);
                                            int pontos = u.getPontos() + c.getPontos();
                                            databaseUsers.child(child.getKey()).child("pontos").setValue(pontos);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                                Random randomChallenge = new Random();
                                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                Date date = new Date();
                                String[] chalenges = {"Faça 2 km", "Faça 3 km", "Faça 5 km", "Faça 7 km", "Faça 10 km", "Corra 10 minutos", "Corra 20 minutos", "Corra 30 minutos", "Faça 100m em menos de 9 segundos", "Faça 200m em menos de 18 segundos"};

                                int[] points = {100, 200, 300, 500, 700, 50, 200, 400, 300, 500};

                                int index = randomChallenge.nextInt(chalenges.length);
                                String id3 = databaseChallenges.push().getKey();
                                Challenge c3 = new Challenge(username, "corrida", chalenges[index], dateFormat.format(date), points[index], 0);
                                databaseChallenges.child(id3).setValue(c3);
                                conn.setBackgroundDrawable(background);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        });

        final ConstraintLayout con = (ConstraintLayout) v.findViewById(R.id.firstConstrainte);

        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lime = getResources().getColor(R.color.orange);
                databaseChallenges.orderByChild("mUsername").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    Challenge c;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            c = child.getValue(Challenge.class);
                            if (c.getDescricao().equals(primeiratv.getText().toString()) && c.getCompleted() == 1) {
                                String id = child.getKey();
                                databaseChallenges.child(id).removeValue();
                                primeiratv.setText("");
                                databaseUsers.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                                            User u = child.getValue(User.class);
                                            int pontos = u.getPontos() + c.getPontos();
                                            databaseUsers.child(child.getKey()).child("pontos").setValue(pontos);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                Random randomChallenge = new Random();
                                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                Date date = new Date();
                                String[] chalenges = {"Faça 2 km", "Faça 3 km", "Faça 5 km", "Faça 7 km", "Faça 10 km", "Corra 10 minutos", "Corra 20 minutos", "Corra 30 minutos", "Faça 100m em menos de 9 segundos", "Faça 200m em menos de 18 segundos"};

                                int[] points = {100, 200, 300, 500, 700, 50, 200, 400, 300, 500};

                                int index = randomChallenge.nextInt(chalenges.length);
                                String id2 = databaseChallenges.push().getKey();
                                Challenge ca = new Challenge(username, "corrida", chalenges[index], dateFormat.format(date), points[index], 0);
                                databaseChallenges.child(id2).setValue(ca);
                                con.setBackgroundDrawable(background);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        final ConstraintLayout co = (ConstraintLayout) v.findViewById(R.id.secondConstrainte);
        co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lime = getResources().getColor(R.color.orange);
                databaseChallenges.orderByChild("mUsername").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    Challenge c;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            c = child.getValue(Challenge.class);
                            if (c.getDescricao().equals(segundatv.getText().toString()) && c.getCompleted() == 1) {
                                String id = child.getKey();
                                databaseChallenges.child(id).removeValue();
                                segundatv.setText("");

                                databaseUsers.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                                            User u = child.getValue(User.class);
                                            int pontos = u.getPontos() + c.getPontos();
                                            databaseUsers.child(child.getKey()).child("pontos").setValue(pontos);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                Random randomChallenge = new Random();
                                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                Date date = new Date();
                                String[] chalenges = {"Faça 2 km", "Faça 3 km", "Faça 5 km", "Faça 7 km", "Faça 10 km", "Corra 10 minutos", "Corra 20 minutos", "Corra 30 minutos", "Faça 100m em menos de 9 segundos", "Faça 200m em menos de 18 segundos"};

                                int[] points = {100, 200, 300, 500, 700, 50, 200, 400, 300, 500};

                                int index = randomChallenge.nextInt(chalenges.length);
                                String id1 = databaseChallenges.push().getKey();
                                Challenge ch = new Challenge(username, "corrida", chalenges[index], dateFormat.format(date), points[index], 0);
                                databaseChallenges.child(id1).setValue(ch);
                                co.setBackgroundDrawable(background);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        return v;
    }
}


                /* for(int i = 0;i<3;i++) {

                    TextView tv = new TextView(getContext());//(TextView) v.inflate(getContext(),R.layout.textview_challenges,null);
                    tv.setText("CRIOU");
                    tv.setTextSize(40f);
                    tv.setLines(Color.BLUE);
                    tv.setGravity(Gravity.CENTER);
                    tv.setPadding(0, (30 + i*100), 0, 0);
                    chalengeFrame.addView(tv);
                }*/

               /* RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams)tv.getLayoutParams();
                p.leftMargin = 50;
                p.topMargin = 50;
                tv.setLayoutParams(p);
            }
        });*/


        /*databaseDesafios.orderByChild("username").equalTo(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Desafio d = child.getValue(Desafio.class);

                        // se o valor for zero faz novos challenges
                        if (d.getDesafio() == 0) {
                            Random randomChallenge = new Random();
                            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date date = new Date();
                            String[] chalenges = {"Faça 2 km", "Faça 3 km", "Faça 5 km", "Faça 7 km", "Faça 10 km", "Corra 10 minutos", "Corra 20 minutos", "Corra 30 minutos", "Faça 100m em menos de 9 segundos", "Faça 200m em menos de 18 segundos"};

                            int[] points = {100, 200, 300, 500, 700, 50, 200, 400, 300, 500};
                            for (int i = 0; i < 3; i++) {

                                int index = randomChallenge.nextInt(chalenges.length);
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

                                //FirebaseDatabase.getInstance().getReference().child("desafios").setValue(1);


                            }


                            databaseDesafios.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                                        //Toast.makeText(getApplicationContext(),child.getKey(),Toast.LENGTH_LONG).show();
                                        databaseDesafios.child(child.getKey()).child("desafio").setValue(1);


                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }

                            });

                            databaseChallenges.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String valores = "";
                                    ArrayList<String> arrayChallenges = new ArrayList();
                                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                        Challenge c = userSnapshot.getValue(Challenge.class);
                                        if (c.getmUsername().equals(username)) {
                                            arrayChallenges.add(c.getDescricao());
                                        }


                                    }
                                    primeiratv.setText(arrayChallenges.get(0));
                                    segundatv.setText(arrayChallenges.get(1));
                                    terceiratv.setText(arrayChallenges.get(2));


                                    for (String s : arrayChallenges) {
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

                                                    if (distanciaFeita >= 2000) {
                                                        int lime = getResources().getColor(R.color.orange);

                                                        if (primeiratv.getText().equals("Faça 2 km")) {
                                                            ConstraintLayout c = getView().findViewById(R.id.firstConstrainte);
                                                            c.setBackgroundColor(lime);

                                                        }

                                                        if (segundatv.getText().equals("Faça 2 km")) {
                                                            ConstraintLayout c = getView().findViewById(R.id.secondConstrainte);
                                                            c.setBackgroundColor(lime);
                                                        }

                                                        if (terceiratv.getText().equals("Faça 2 km")) {
                                                            ConstraintLayout c = getView().findViewById(R.id.ThirdConstrainte);
                                                            c.setBackgroundColor(lime);

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


                        } else {

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
                                            break;
                                            // FirebaseDatabase.getInstance().getReference().child("desafios").setValue(0);


                                        }
                                    }


                                    databaseDesafios.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                                //Toast.makeText(getApplicationContext(),child.getKey(),Toast.LENGTH_LONG).show();
                                                databaseDesafios.child(child.getKey()).child("desafio").setValue(0);


                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }

                                    });*/


                                   /* databaseDesafios.addValueEventListener(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                                Desafio d = child.getValue(Desafio.class);
                                                if (d.getUsername().equals(username)){
                                                    databaseDesafios.child(child.getKey()).child("desafio").setValue(0);
                                                }


                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });*/


                            /*    }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                        }
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/




























       /* ValueEventListener ola = FirebaseDatabase.getInstance().getReference().child("desafios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("DESAFIOS", "" + snapshot.getValue());
                if (snapshot.getValue() != null) {
                    if (Long.valueOf(snapshot.getValue().toString()) == 0) {
                        Toast.makeText(getContext(), "" + snapshot.getValue(), Toast.LENGTH_SHORT).show();
                        Log.d("DESAFIOS1", "" + snapshot.getValue());

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


                            }


                            if (s.equals("Faça 3 km")) {

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

                                        if (distanciaFeita >= 3000) {
                                            int lime = getResources().getColor(R.color.orange);

                                            if (primeiratv.getText().equals("Faça 3 km")){
                                                ConstraintLayout c = getView().findViewById(R.id.firstConstrainte);
                                                c.setBackgroundColor(lime);

                                            }

                                            if (segundatv.getText().equals("Faça 3 km")){
                                                ConstraintLayout c = getView().findViewById(R.id.secondConstrainte);
                                                c.setBackgroundColor(lime);
                                            }

                                            if (terceiratv.getText().equals("Faça 3 km")){
                                                ConstraintLayout c = getView().findViewById(R.id.ThirdConstrainte);
                                                c.setBackgroundColor(lime);

                                            }

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }



                            if (s.equals("Faça 5 km")) {

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

                                        if (distanciaFeita >= 5000) {
                                            int lime = getResources().getColor(R.color.orange);

                                            if (primeiratv.getText().equals("Faça 5 km")){
                                                ConstraintLayout c = getView().findViewById(R.id.firstConstrainte);
                                                c.setBackgroundColor(lime);

                                            }

                                            if (segundatv.getText().equals("Faça 5 km")){
                                                ConstraintLayout c = getView().findViewById(R.id.secondConstrainte);
                                                c.setBackgroundColor(lime);
                                            }

                                            if (terceiratv.getText().equals("Faça 5 km")){
                                                ConstraintLayout c = getView().findViewById(R.id.ThirdConstrainte);
                                                c.setBackgroundColor(lime);

                                            }

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }


                            if (s.equals("Faça 7 km")) {

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

                                        if (distanciaFeita >= 7000) {
                                            int lime = getResources().getColor(R.color.orange);

                                            if (primeiratv.getText().equals("Faça 7 km")){
                                                ConstraintLayout c = getView().findViewById(R.id.firstConstrainte);
                                                c.setBackgroundColor(lime);

                                            }

                                            if (segundatv.getText().equals("Faça 7 km")){
                                                ConstraintLayout c = getView().findViewById(R.id.secondConstrainte);
                                                c.setBackgroundColor(lime);
                                            }

                                            if (terceiratv.getText().equals("Faça 7 km")){
                                                ConstraintLayout c = getView().findViewById(R.id.ThirdConstrainte);
                                                c.setBackgroundColor(lime);

                                            }

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }


                            if (s.equals("Faça 10 km")) {

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

                                        if (distanciaFeita >= 10000) {
                                            int lime = getResources().getColor(R.color.orange);

                                            if (primeiratv.getText().equals("Faça 10 km")){
                                                ConstraintLayout c = getView().findViewById(R.id.firstConstrainte);
                                                c.setBackgroundColor(lime);

                                            }

                                            if (segundatv.getText().equals("Faça 10 km")){
                                                ConstraintLayout c = getView().findViewById(R.id.secondConstrainte);
                                                c.setBackgroundColor(lime);
                                            }

                                            if (terceiratv.getText().equals("Faça 10 km")){
                                                ConstraintLayout c = getView().findViewById(R.id.ThirdConstrainte);
                                                c.setBackgroundColor(lime);

                                            }

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }


                            if (s.equals("Corra 10 minutos")) {

                                databaseCorrida.addValueEventListener(new ValueEventListener() {

                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date = new Date();
                                    String d = dateFormat.format(date);
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String valores = "";
                                        boolean ehMaior = false;
                                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                            Corrida c = userSnapshot.getValue(Corrida.class);
                                            if (c.getUsername().equals(username) && c.getData().equals(d)) {
                                                String time = c.getTempo().substring(0,2);
                                               if (Integer.parseInt(time) >= 10 && c.getDistancia() >= 1000){
                                                   ehMaior = true;
                                               }


                                            }
                                        }

                                        if (ehMaior == true) {
                                            int lime = getResources().getColor(R.color.orange);

                                            if (primeiratv.getText().equals("Corra 10 minutos")){
                                                ConstraintLayout c = getView().findViewById(R.id.firstConstrainte);
                                                c.setBackgroundColor(lime);

                                            }

                                            if (segundatv.getText().equals("Corra 10 minutos")){
                                                ConstraintLayout c = getView().findViewById(R.id.secondConstrainte);
                                                c.setBackgroundColor(lime);
                                            }

                                            if (terceiratv.getText().equals("Corra 10 minutos")){
                                                ConstraintLayout c = getView().findViewById(R.id.ThirdConstrainte);
                                                c.setBackgroundColor(lime);

                                            }

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }



                            if (s.equals("Corra 20 minutos")) {

                                databaseCorrida.addValueEventListener(new ValueEventListener() {

                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date = new Date();
                                    String d = dateFormat.format(date);
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String valores = "";
                                        boolean ehMaior = false;
                                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                            Corrida c = userSnapshot.getValue(Corrida.class);
                                            if (c.getUsername().equals(username) && c.getData().equals(d)) {
                                                String time = c.getTempo().substring(0,2);
                                                if (Integer.parseInt(time) >= 20 && c.getDistancia() >= 2000){
                                                    ehMaior = true;
                                                }


                                            }
                                        }

                                        if (ehMaior == true) {
                                            int lime = getResources().getColor(R.color.orange);

                                            if (primeiratv.getText().equals("Corra 20 minutos")){
                                                ConstraintLayout c = getView().findViewById(R.id.firstConstrainte);
                                                c.setBackgroundColor(lime);

                                            }

                                            if (segundatv.getText().equals("Corra 20 minutos")){
                                                ConstraintLayout c = getView().findViewById(R.id.secondConstrainte);
                                                c.setBackgroundColor(lime);
                                            }

                                            if (terceiratv.getText().equals("Corra 20 minutos")){
                                                ConstraintLayout c = getView().findViewById(R.id.ThirdConstrainte);
                                                c.setBackgroundColor(lime);

                                            }

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }



                            if (s.equals("Corra 30 minutos")) {

                                databaseCorrida.addValueEventListener(new ValueEventListener() {

                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date = new Date();
                                    String d = dateFormat.format(date);
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String valores = "";
                                        boolean ehMaior = false;
                                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                            Corrida c = userSnapshot.getValue(Corrida.class);
                                            if (c.getUsername().equals(username) && c.getData().equals(d)) {
                                                String time = c.getTempo().substring(0,2);
                                                if (Integer.parseInt(time) >= 30 && c.getDistancia() >= 3000){
                                                    ehMaior = true;
                                                }


                                            }
                                        }

                                        if (ehMaior == true) {
                                            int lime = getResources().getColor(R.color.orange);

                                            if (primeiratv.getText().equals("Corra 30 minutos")){
                                                ConstraintLayout c = getView().findViewById(R.id.firstConstrainte);
                                                c.setBackgroundColor(lime);

                                            }

                                            if (segundatv.getText().equals("Corra 30 minutos")){
                                                ConstraintLayout c = getView().findViewById(R.id.secondConstrainte);
                                                c.setBackgroundColor(lime);
                                            }

                                            if (terceiratv.getText().equals("Corra 30 minutos")){
                                                ConstraintLayout c = getView().findViewById(R.id.ThirdConstrainte);
                                                c.setBackgroundColor(lime);

                                            }

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }


                            if (s.equals("Faça 100m em menos de 9 segundos")) {

                                databaseCorrida.addValueEventListener(new ValueEventListener() {

                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date = new Date();
                                    String d = dateFormat.format(date);
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String valores = "";
                                        boolean ehMaior = false;
                                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                            Corrida c = userSnapshot.getValue(Corrida.class);
                                            if (c.getUsername().equals(username) && c.getData().equals(d)) {
                                                String timesec = c.getTempo().substring(3,5);
                                                String timemin = c.getTempo().substring(0,2);
                                                //Toast.makeText(getContext(),"TIME "+time,Toast.LENGTH_SHORT).show();
                                                if (Integer.parseInt(timemin) == 0 && Integer.parseInt(timesec) < 9 && c.getDistancia() >= 100 ){
                                                    ehMaior = true;
                                                }


                                            }
                                        }

                                        if (ehMaior == true) {
                                            int lime = getResources().getColor(R.color.orange);

                                            if (primeiratv.getText().equals("Faça 100m em menos de 9 segundos")){
                                                ConstraintLayout c = getView().findViewById(R.id.firstConstrainte);
                                                c.setBackgroundColor(lime);

                                            }

                                            if (segundatv.getText().equals("Faça 100m em menos de 9 segundos")){
                                                ConstraintLayout c = getView().findViewById(R.id.secondConstrainte);
                                                c.setBackgroundColor(lime);
                                            }

                                            if (terceiratv.getText().equals("Faça 100m em menos de 9 segundos")){
                                                ConstraintLayout c = getView().findViewById(R.id.ThirdConstrainte);
                                                c.setBackgroundColor(lime);

                                            }

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }



                            if (s.equals("Faça 200m em menos de 18 segundos")) {

                                databaseCorrida.addValueEventListener(new ValueEventListener() {

                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date = new Date();
                                    String d = dateFormat.format(date);
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String valores = "";
                                        boolean ehMaior = false;
                                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                            Corrida c = userSnapshot.getValue(Corrida.class);
                                            if (c.getUsername().equals(username) && c.getData().equals(d)) {
                                                String timesec = c.getTempo().substring(3,5);
                                                String timemin = c.getTempo().substring(0,2);
                                                //Toast.makeText(getContext(),"TIME "+timemin+":"+timesec,Toast.LENGTH_SHORT).show();
                                                if (Integer.parseInt(timemin) == 0 && Integer.parseInt(timesec) < 18 && c.getDistancia() >= 200 ){
                                                    ehMaior = true;
                                                }


                                            }
                                        }

                                        if (ehMaior == true) {
                                            int lime = getResources().getColor(R.color.orange);

                                            if (primeiratv.getText().equals("Faça 200m em menos de 18 segundos")){
                                                ConstraintLayout c = getView().findViewById(R.id.firstConstrainte);
                                                c.setBackgroundColor(lime);

                                            }

                                            if (segundatv.getText().equals("Faça 200m em menos de 18 segundos")){
                                                ConstraintLayout c = getView().findViewById(R.id.secondConstrainte);
                                                c.setBackgroundColor(lime);
                                            }

                                            if (terceiratv.getText().equals("Faça 200m em menos de 18 segundos")){
                                                ConstraintLayout c = getView().findViewById(R.id.ThirdConstrainte);
                                                c.setBackgroundColor(lime);

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

*/

//databaseChallenges.removeEventListener(ola);

//ProgressBar corrida = (ProgressBar) v.findViewById(R.id.progressBarCorrida);
//corrida.setMax(5);
//corrida.setProgress(3);
//corrida.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FFA500")));






