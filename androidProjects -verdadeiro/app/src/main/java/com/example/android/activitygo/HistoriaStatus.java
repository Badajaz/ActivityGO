package com.example.android.activitygo;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.activitygo.model.Corrida;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HistoriaStatus extends Fragment {

    private MapView mMapView;
    private GoogleMap googleMap;
    private String chronometerTime;
    private DatabaseReference databaseCorrida;
    private String username;
    private Button irHistoricoCorridas;
    private String data;
    private double distancia;
    private long tempoPace;
    private String timeS;
    private ArrayList<LatLng> marcadores = new ArrayList<>();
    private String melhorkm;

    public HistoriaStatus() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_historia_status, container, false);
        databaseCorrida = FirebaseDatabase.getInstance().getReference("corrida");
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        distancia = getArguments().getDouble("DISTANCIA");
        tempoPace = getArguments().getLong("TEMPOPACE");
        timeS = getArguments().getString("TEMPO");
        data = getArguments().getString("DATAS");
        username = getArguments().getString("USERNAME");
        marcadores = getArguments().getParcelableArrayList("Markers");
        melhorkm = getArguments().getString("MELHORKM");
        double pace = tempoPace / distancia;
        if (distancia == 0) {
            pace = 0;
        }

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

        final ArrayList<LatLng> markers = getArguments().getParcelableArrayList("Markers");
        final ArrayList<Double> markersParaInserirNaDB = new ArrayList<>();

        for (LatLng l : markers) {
            markersParaInserirNaDB.add(l.latitude);
            markersParaInserirNaDB.add(l.longitude);
        }

        String id = databaseCorrida.push().getKey();
        Toast.makeText(getActivity(), melhorkm, Toast.LENGTH_SHORT).show();
        Corrida corrida = new Corrida(username, data, distancia, chronometerTime, tempoPace, markersParaInserirNaDB, melhorkm);
        databaseCorrida.child(id).setValue(corrida);

        TextView tv = v.findViewById(R.id.Tempo);
        tv.setText(chronometerTime);

        TextView tv2 = v.findViewById(R.id.Distance);
        tv2.setText("" + distancia);

        TextView pacetv = v.findViewById(R.id.Pace);
        pacetv.setText("" + pace);

        mMapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);

                for (LatLng l : markers) {
                    googleMap.addMarker(new MarkerOptions().position(l).title("FUNCIONA"));
                    options.add(l);
                }

                googleMap.addPolyline(options);

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers.get(markers.size() - 1), 15.0f));
            }
        });

        irHistoricoCorridas = (Button) v.findViewById(R.id.VerMais);
        irHistoricoCorridas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment p = new HistoricoCorridas();
                Bundle args = new Bundle();
                args.putString("DATAS", data);
                args.putString("USERNAME", username);
                args.putDouble("DISTANCIA", distancia);
                args.putLong("TEMPOPACE", tempoPace);
                args.putString("TEMPO", timeS);
                args.putParcelableArrayList("MARKERS", markers);
                args.putString("USERNAME", username);
                p.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragmentMap, p).commit();
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}