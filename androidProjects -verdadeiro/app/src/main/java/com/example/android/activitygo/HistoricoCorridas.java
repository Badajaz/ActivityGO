package com.example.android.activitygo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HistoricoCorridas extends Fragment {

    private ListView lv1;
    private ArrayAdapter<String> listViewAdapter;
    private String value;
    private String chronometerTime;
    private double distancia;
    private ArrayList<LatLng> markers;
    private DatabaseReference databaseCorrida;
    private long tempoPace;
    private String username;
    private String timeS;
    private ArrayList<String> datasCorridas = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_historico_corridas, container, false);
        databaseCorrida = FirebaseDatabase.getInstance().getReference("corrida");
        lv1 = (ListView) v.findViewById(R.id.ListaResultadosDatas);
        timeS = getArguments().getString("TEMPO");
        distancia = getArguments().getDouble("DISTANCE");
        markers = getArguments().getParcelableArrayList("MARKERS");
        value = getArguments().getString("DATAS");
        tempoPace = getArguments().getLong("TEMPOPACE");
        username = getArguments().getString("USERNAME");

        databaseCorrida.orderByChild("data").equalTo(value).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String d = String.valueOf(child.child("data").getValue());
                    datasCorridas.add(d);
                }

                listViewAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, datasCorridas);

                lv1.setAdapter(listViewAdapter);
                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Fragment p = new HistoriaStatus();
                        Bundle args = new Bundle();
                        args.putString("TEMPO", timeS);
                        args.putDouble("DISTANCE", distancia);
                        args.putParcelableArrayList("Markers", markers);
                        args.putLong("TEMPOPACE", tempoPace);
                        args.putString("USERNAME", username);
                        p.setArguments(args);
                        getFragmentManager().beginTransaction().replace(R.id.fragmentMap, p).commit();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }
}