package com.example.android.activitygo;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public HistoriaStatus() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

        double distancia = getArguments().getDouble("DISTANCE");
        long tempoPace = getArguments().getLong("TEMPOPACE");
        String timeS = getArguments().getString("TEMPO");
        String data = getArguments().getString("DATAS");
        username = getArguments().getString("USERNAME");
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

        String id = databaseCorrida.push().getKey();
        Corrida corrida = new Corrida(username, data, distancia, chronometerTime, tempoPace, markers);
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
                // For showing a move to my location button
                //googleMap.setMyLocationEnabled(true);

                for (LatLng l : markers) {
                    googleMap.addMarker(new MarkerOptions().position(l).title("FUNCIONA"));
                    options.add(l);

                }
                googleMap.addPolyline(options);

                // For dropping a marker at a point on the Map

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers.get(markers.size() - 1), 15.0f));
                //googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),17));
                // For zooming automatically to the location of the marker
                //CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
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