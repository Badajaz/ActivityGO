package com.example.android.activitygo;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.activitygo.model.Grupo;
import com.example.android.activitygo.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyGroupFragmentElements extends Fragment {

    private TextView tv;
    private DatabaseReference databaseGrupo;
    private String grupo;


    public MyGroupFragmentElements() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_group_fragment_elements, container, false);

         grupo = getArguments().getString("NOMEGRUPO");
        tv = (TextView) v.findViewById(R.id.ListaElementosMeusGrupos);


        databaseGrupo = FirebaseDatabase.getInstance().getReference("grupos");



        databaseGrupo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valores = "";
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Grupo g = userSnapshot.getValue(Grupo.class);
                    if (g.getNome().equals(grupo)){
                        for (String elem : g.getElementosGrupo()){
                            valores+= elem+"\n";
                        }
                    }


                }

                tv.setText(valores);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });











        return v;
    }

}
