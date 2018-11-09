package com.example.android.activitygo;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoriaStatus extends Fragment {


    private MapView mMapView;
    private GoogleMap googleMap;

    public HistoriaStatus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_historia_status, container, false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String time = getArguments().getString("Chronometer");
        double distancia = getArguments().getDouble("DISTANCE");
        final ArrayList<LatLng> markers = getArguments().getParcelableArrayList("Markers");
        TextView tv = v.findViewById(R.id.Tempo);
        tv.setText(time);

        TextView tv2 = v.findViewById(R.id.Distance);
        tv2.setText(""+distancia);

        mMapView.getMapAsync(new OnMapReadyCallback() {


            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
                // For showing a move to my location button
                //googleMap.setMyLocationEnabled(true);

                for (LatLng l : markers){
                    googleMap.addMarker(new MarkerOptions().position(l).title("FUNCIONA"));
                    options.add(l);

                }
                googleMap.addPolyline(options);


                // For dropping a marker at a point on the Map

                LatLngBounds.Builder builder= new LatLngBounds.Builder();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers.get(markers.size()-1), 15.0f));
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
