package com.example.android.activitygo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.android.activitygo.model.Ranking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class TableRankingsFragment extends Fragment {

    private DatabaseReference databaseRankings;
    private DatabaseReference databaseUsers;

    public TableRankingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_table_rankings, container, false);
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        databaseRankings = FirebaseDatabase.getInstance().getReference("rankings");
        databaseRankings.orderByChild("desporto").equalTo("corrida").addListenerForSingleValueEvent(new ValueEventListener() {
            private Ranking r;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    r = child.getValue(Ranking.class);

                }



                int i = 0;
                for (final String username : r.getRankings()) {

                    databaseUsers.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                        private String pontos;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    pontos = String.valueOf(child.child("pontos").getValue());

                                }

                            TableLayout t = getView().findViewById(R.id.TableRanking);
                            TableRow tr = new TableRow(getContext());
                            TextView tv1 = new TextView(getContext());
                            TextView tv2 = new TextView(getContext());
                            TextView tv3 = new TextView(getContext());
                            tv1.setGravity(Gravity.CENTER);
                            tv2.setGravity(Gravity.CENTER);
                            tv3.setGravity(Gravity.CENTER);
                            tv1.setText("1");
                            tv2.setText(username);
                            tv3.setText(pontos);
                            tr.addView(tv1);
                            tr.addView(tv2);
                            tr.addView(tv3);
                            t.addView(tr);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    i++;

                }
            }

                @Override
                public void onCancelled (@NonNull DatabaseError databaseError){

                }
            });





        return v;
    }
}