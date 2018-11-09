package com.example.android.activitygo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoricoCorridas extends Fragment {

    private ListView lv1;
    private ArrayAdapter<String> listViewAdapter;
    private ArrayList<String> value;
    private String chronometerTime;
    private double distancia;

    public HistoricoCorridas() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_historico_corridas, container, false);

        lv1 = (ListView) v.findViewById(R.id.ListaResultadosDatas);
        String timeS = getArguments().getString("TEMPO");
        distancia = getArguments().getDouble("DISTANCIA");
        double timeSconverted = Double.valueOf(timeS);

        double time = timeSconverted / 60;
        int timeInteiro = (int) time;

        double minutos = time - timeInteiro;
        double segundos = minutos * 60;
        chronometerTime = "" + timeInteiro + ":" + (int) segundos;


        Toast.makeText(getContext(), chronometerTime, Toast.LENGTH_LONG).show();
        value = getArguments().getStringArrayList("DATAS");
        listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, value);
        lv1.setAdapter(listViewAdapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Fragment p = new HistoriaStatus();
                Bundle args = new Bundle();
                args.putString("Chronometer", chronometerTime);
                args.putDouble("DISTANCE", distancia);
                p.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragmentMap, p).commit();



                /*
                passar valores para uma nova actividade
                 */


                Toast.makeText(getContext(), "" + value.get(position), Toast.LENGTH_LONG).show();
            }
        });


        return v;
    }


}