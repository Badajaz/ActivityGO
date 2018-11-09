package com.example.android.activitygo;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoriaStatus extends Fragment {


    public HistoriaStatus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_historia_status, container, false);

        String time = getArguments().getString("Chronometer");
        double distancia = getArguments().getDouble("DISTANCE");
        TextView tv = v.findViewById(R.id.Tempo);
        tv.setText(time);

        TextView tv2 = v.findViewById(R.id.Distance);
        tv2.setText(""+distancia);




        return v;
    }

}
