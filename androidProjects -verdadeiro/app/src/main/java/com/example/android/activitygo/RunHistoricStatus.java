package com.example.android.activitygo;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class RunHistoricStatus extends Fragment {


    private MapView mMapView;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_run_historic_status, container, false);


        String statistics = getArguments().getString("RUN_STATISTICS");
        double[] coordenadas = getArguments().getDoubleArray("COORDINATES");
        final ArrayList<LatLng> CoordinatesForMap = transformDoubleArrayInLatLng(coordenadas);

        mMapView = (MapView) v.findViewById(R.id.MapHistoric);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        mMapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);

               // googleMap.addMarker(new MarkerOptions().position(new LatLng(17.385044, 78.486671)).title("FUNCIONA"));

                for (LatLng l : CoordinatesForMap) {
                    googleMap.addMarker(new MarkerOptions().position(l).title("FUNCIONA"));
                    options.add(l);
                }

                googleMap.addPolyline(options);

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CoordinatesForMap.get(0), 17.0f));
            }
        });


        Toast.makeText(getContext(), "" + coordenadas[0], Toast.LENGTH_LONG).show();

        String[] splitStatistics = statistics.split("   ");
        TextView time = v.findViewById(R.id.TimeHistoric);
        TextView distance = v.findViewById(R.id.DistanceHistoric);
        TextView pace = v.findViewById(R.id.PaceHistoric);
        TextView data = v.findViewById(R.id.dateHistoric);
        data.setText(splitStatistics[0]);
        time.setText(splitStatistics[2]);
        distance.setText(splitStatistics[1]);
        pace.setText(splitStatistics[3]);


        return v;

    }

    private ArrayList<LatLng> transformDoubleArrayInLatLng(double[] coordenadas) {
        ArrayList<LatLng> l = new ArrayList<>();
        for (int i = 0; i < coordenadas.length; i += 2) {
            l.add(new LatLng(coordenadas[i], coordenadas[i + 1]));
        }

        return l;
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




