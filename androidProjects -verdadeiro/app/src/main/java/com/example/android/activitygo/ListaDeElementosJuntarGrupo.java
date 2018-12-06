package com.example.android.activitygo;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.activitygo.model.Grupo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaDeElementosJuntarGrupo extends Fragment {

    private TextView elementosGrupo;
    private String nomeGrupo;
    private DatabaseReference databaseGrupo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_lista_de_elementos_juntar_grupo, container, false);
        elementosGrupo = (TextView) v.findViewById(R.id.ElementosGrupo);
        nomeGrupo = getArguments().getString("NOMEGRUPO");
        databaseGrupo = FirebaseDatabase.getInstance().getReference("grupos");



        databaseGrupo.addValueEventListener(new ValueEventListener() {
            private String valores = "";
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Grupo g = userSnapshot.getValue(Grupo.class);
                    if (g.getNome().equals(nomeGrupo)){
                        if (g.getElementosGrupo().size() == 1){
                            valores = g.getElementosGrupo().get(0);
                        }else{
                            for (String elem: g.getElementosGrupo()){
                                valores+= elem+" ";
                            }
                        }
                    }

                }

                String[] elementos = valores.split( " ");

                for (String elem : elementos){
                    elementosGrupo.setText(elem+"\n");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });














        return v;
    }

}
