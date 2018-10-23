package com.example.android.activitygo;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class RunEstatisticsFragment extends Fragment {

    private TextView err;
    private String strtext;
    private TextView duracaoValueView;
    private String duracaoValue;
    private TextView dataView;
    private TextView dateView;
    private String dataValue;
    private String date;
    private String distancia;
    private TextView distanciaView;
    private String distance;
    private TextView distanceView;
    private TextView caloriasTV;

    public RunEstatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_run_estatistics, container, false);
        strtext = getArguments().getString("CID");
        err = (TextView) v.findViewById(R.id.duracaoEst);

        duracaoValue = getArguments().getString("Duração");
        duracaoValueView = (TextView) v.findViewById(R.id.duracaoValue);

        dataValue = getArguments().getString("data");
        dataView = (TextView) v.findViewById(R.id.dataLabel);

        date = getArguments().getString("date");
        dateView = (TextView) v.findViewById(R.id.dataValue);

        distancia = getArguments().getString("distancia");
        distanciaView = (TextView) v.findViewById(R.id.distanciaLabel);

        distance = getArguments().getString("distance");
        distanceView = (TextView) v.findViewById(R.id.distanciaValue);

        caloriasTV = (TextView) v.findViewById(R.id.caloriasValue);
        caloriasTV.setText("50 kcal");

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        err.setText(strtext);
        duracaoValueView.setText(duracaoValue);
        dataView.setText(dataValue);
        dateView.setText(date);
        distanciaView.setText(distancia);
        distanceView.setText(distance);
    }
}
