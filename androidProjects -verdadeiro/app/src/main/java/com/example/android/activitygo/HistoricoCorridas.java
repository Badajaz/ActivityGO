package com.example.android.activitygo;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.activitygo.model.Corrida;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoricoCorridas extends Fragment {

    private ListView lv1;
    private ArrayAdapter<String> listViewAdapter;
    private String value;
    private String chronometerTime;
    private double distancia;
    private ArrayList<LatLng> markers;
    private DatabaseReference databaseCorrida;
    ArrayList<String> dates = new ArrayList<>();
    private long tempoPace;
    private String concatena= "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_historico_corridas, container, false);
        databaseCorrida = FirebaseDatabase.getInstance().getReference("corrida");
        lv1 = (ListView) v.findViewById(R.id.ListaResultadosDatas);
        String timeS = getArguments().getString("TEMPO");
        distancia = getArguments().getDouble("DISTANCIA");
        markers = getArguments().getParcelableArrayList("Markers");
        value = getArguments().getString("DATAS");
        tempoPace = getArguments().getLong("TEMPOPACE");

        double timeSconverted = Double.valueOf(timeS);
        double time = timeSconverted / 60;
        int timeInteiro = (int) time;
        double minutos = time - timeInteiro;
        double segundos = minutos * 60;
        if (timeInteiro < 10) {
            if (segundos < 10) {
                chronometerTime = "0" + timeInteiro + ":" + "0" + (int) segundos;
            } else {
                chronometerTime = "0" + timeInteiro + ":" + (int) segundos;
            }
        } else {
            chronometerTime = "" + timeInteiro + ":" + (int) segundos;
        }


        if (distancia == 0) {
            tempoPace = 0;
        }

        String id = databaseCorrida.push().getKey();
        Corrida corrida = new Corrida(value, distancia, chronometerTime, tempoPace, markers);
        databaseCorrida.child(id).setValue(corrida);

        databaseCorrida.orderByChild("data").equalTo(value).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String d = String.valueOf(child.child("data").getValue());
                    //concatena = d+" ";
                    dates.add(d);
                }

                listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, dates);

                //       Toast.makeText(getContext(),"dataaaaa000"+dates.get(0),Toast.LENGTH_SHORT).show();
                lv1.setAdapter(listViewAdapter);
                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Fragment p = new HistoriaStatus();
                        Bundle args = new Bundle();
                        args.putString("Chronometer", chronometerTime);
                        args.putDouble("DISTANCE", distancia);
                        args.putParcelableArrayList("Markers", markers);
                        p.setArguments(args);
                        getFragmentManager().beginTransaction().replace(R.id.fragmentMap, p).commit();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        String[] arrayDates = concatena.split(" ");

        listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayDates);

    //       Toast.makeText(getContext(),"dataaaaa000"+dates.get(0),Toast.LENGTH_SHORT).show();
        lv1.setAdapter(listViewAdapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment p = new HistoriaStatus();
                Bundle args = new Bundle();
                args.putString("Chronometer", chronometerTime);
                args.putDouble("DISTANCE", distancia);
                args.putParcelableArrayList("Markers", markers);
                p.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragmentMap, p).commit();
            }
        });
        return v;
    }
}