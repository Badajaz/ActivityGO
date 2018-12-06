package com.example.android.activitygo;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.activitygo.model.Grupo;
import com.example.android.activitygo.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProcuraGrupos extends Fragment {

    private ArrayAdapter<String> listViewAdapter;
    private DatabaseReference databaseGrupo;

    public ProcuraGrupos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_procura_grupos, container, false);
        databaseGrupo = FirebaseDatabase.getInstance().getReference("grupos");

        final String pesquisa = getArguments().getString("PESQUISA");


        databaseGrupo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valores = "";
                ArrayList<String> array = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Grupo g = userSnapshot.getValue(Grupo.class);
                    if (g.getNome().contains(pesquisa)){
                        array.add(g.getNome());
                    }

                    /* for (String nomeElemento : g.getElementosGrupo()) {
                        if (nomeElemento.equals("badajaz") && g.getNome().contains(pesquisa)) {
                            array.add(g.getNome());


                        }
                    }*/

                }


                ListView listView = (ListView) getView().findViewById(R.id.ListaResultados);
                //String[] value = getArguments().getStringArray("PROCURA");

                listViewAdapter = new ArrayAdapter<String>(
                        getActivity(), android.R.layout.simple_list_item_1, array);
                listView.setAdapter(listViewAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }
}

