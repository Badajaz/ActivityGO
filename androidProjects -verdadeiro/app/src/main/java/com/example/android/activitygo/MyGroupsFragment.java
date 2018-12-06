package com.example.android.activitygo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.activitygo.model.Grupo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyGroupsFragment extends Fragment {

    private ArrayList<String> myGroups = new ArrayList<String>();
    private ListView listView;
    private ArrayAdapter<String> listViewAdapter;
    private DatabaseReference databaseGrupo;

    public MyGroupsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_groups, container, false);
        ((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Os meus grupos:");
        databaseGrupo = FirebaseDatabase.getInstance().getReference("grupos");



        databaseGrupo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valores = "";
                ArrayList<String> array = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Grupo g = userSnapshot.getValue(Grupo.class);
                     for (String nomeElemento : g.getElementosGrupo()) {
                        if (nomeElemento.equals("badajaz")) {
                            myGroups.add(g.getNome());
                        }
                    }

                }


                listView = (ListView) getView().findViewById(R.id.ListViewMeusGrupos);
                listViewAdapter = new ArrayAdapter<String>(
                        getActivity(), android.R.layout.simple_list_item_1, myGroups);
                listView.setAdapter(listViewAdapter);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }
}
