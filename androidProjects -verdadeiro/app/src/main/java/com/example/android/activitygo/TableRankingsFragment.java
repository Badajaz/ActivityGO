package com.example.android.activitygo;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class TableRankingsFragment extends Fragment {

    private DatabaseReference databaseRankings;
    private DatabaseReference databaseUsers;
    private int i;
    private int rankPlace;
    private String aux = "";
    private static final String TAG = "TableRankingsFragment";

    public TableRankingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_table_rankings, container, false);
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        databaseRankings = FirebaseDatabase.getInstance().getReference("rankings");

        HashMap<String, Integer> mMap = new HashMap<String, Integer>();
        Bundle b = this.getArguments();
        if (b.getSerializable("hashmap") != null)
            mMap = (HashMap<String, Integer>) b.getSerializable("hashmap");

        ArrayList<String> listaNomes = b.getStringArrayList("listaNomes");
        //Toast.makeText(getContext(), "" + mMap.get("antonio"), Toast.LENGTH_LONG).show();

        int rank = 1;
        for (int i = 0; i < listaNomes.size(); i++) {

            TableLayout t = v.findViewById(R.id.TableRanking);
            TableRow tr = new TableRow(getContext());
            TextView tv1 = new TextView(getContext());
            TextView tv2 = new TextView(getContext());
            TextView tv3 = new TextView(getContext());
            tv1.setGravity(Gravity.CENTER);
            tv2.setGravity(Gravity.CENTER);
            tv3.setGravity(Gravity.CENTER);
            tv1.setText(Integer.toString(rank) + "º");
            tv2.setText(listaNomes.get(i));
            tv3.setText("" + mMap.get(listaNomes.get(i)));
            tr.addView(tv1);
            tr.addView(tv2);
            tr.addView(tv3);
            t.addView(tr);
            int lime = getResources().getColor(R.color.orange);
            int blue = getResources().getColor(R.color.BlueSeparator);
            if (i % 2 != 0) {

                int white = getResources().getColor(R.color.whiteLetters);

                tv1.setBackgroundColor(lime);
                tv2.setBackgroundColor(lime);
                tv3.setBackgroundColor(lime);
                tv1.setTextColor(white);
                tv2.setTextColor(white);
                tv3.setTextColor(white);

            } else {
                tv1.setTextColor(blue);
                tv2.setTextColor(blue);
                tv3.setTextColor(blue);
            }
            tv1.setTextSize(20);
            tv2.setTextSize(20);
            tv3.setTextSize(20);

            rank++;
        }

        /* databaseRankings.orderByChild("desporto").equalTo("corrida").addListenerForSingleValueEvent(new ValueEventListener() {
            private Ranking r;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    r = child.getValue(Ranking.class);

                }

                rankPlace = 1;
                for (i = r.getRankings().size() - 1; i >= 0; i--) {
                    aux = r.getRankings().get(i);


                    ValueEventListener user = databaseUsers.orderByChild("username").equalTo(aux).addValueEventListener(new ValueEventListener() {
                        private ArrayList<String> pontos = new ArrayList<>();

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {

                                    pontos.add(String.valueOf(child.child("pontos").getValue()));
                                }

                            }
                            int [] points = convertArrayListToArray(pontos);
                            Arrays.sort(points);
                            for (int s : points) {

                            }
                            rankPlace++;


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
        });*/
        return v;
    }

    private int[] convertArrayListToArray(ArrayList<String> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = Integer.parseInt(list.get(i));
        }
        return array;
    }
}