package com.example.android.activitygo;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    Marker marker;
    LocationListener locationListener;
    private ArrayList<Double> posicoes = new ArrayList<>();
    private double accKm = 0.0;


    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;

    private Fragment SelectedFragment;
    private boolean isStopped = false;

    private ArrayList<String> datas = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);


        String date = day + "/" + month + "/" + year;
        Log.d("DATA",date);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);*/
        //mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (isStopped == false) {


                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    double altitude = location.getAltitude();
                    posicoes.add(latitude);
                    posicoes.add(longitude);
                    posicoes.add(altitude);


                    if (posicoes.size() == 6) {
                        accKm += distance(posicoes.get(0), posicoes.get(3), posicoes.get(1), posicoes.get(4), posicoes.get(2), posicoes.get(5));
                        posicoes.remove(0);
                        posicoes.remove(1);
                        posicoes.remove(2);
                    }

  /*              if (posicoes.size() == 4) {
                    accKm += distance2(posicoes.get(0), posicoes.get(2), posicoes.get(1), posicoes.get(3));
                    posicoes.remove(0);
                    posicoes.remove(1);
                }*/


                    //get the location name from latitude and longitude
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {

                        List<Address> addresses =
                                geocoder.getFromLocation(latitude, longitude, 1);
                        String result = addresses.get(0).getLocality() + ":";
                        result += addresses.get(0).getCountryName();
                        LatLng latLng = new LatLng(latitude, longitude);
                        //marker = mMap.addMarker(new MarkerOptions().position(latLng).title("acc= " + Double.toString(accKm)));
                        // mMap.setMaxZoomPreference(20);
                        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 21.0f));
                        //Log.d("COORDENADAS", latitude + " " + longitude + " " + altitude);

                        DecimalFormat df = new DecimalFormat();
                        df.setMaximumFractionDigits(2);

                        TextView tv = (TextView) findViewById(R.id.kmTextViewRun);
                        tv.setText("" + df.format(accKm));
                        Toast.makeText(getApplicationContext(), "acc= " + df.format(accKm), Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);




        Button Stop = (Button) findViewById(R.id.StopRun);
        Button Start = (Button) findViewById(R.id.StartRun);
        chronometer = findViewById(R.id.chronometer);

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!running) {
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.start();
                    running = true;
                    isStopped = false;
                }
            }
        });

        Stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (running) {
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    chronometer.stop();
                    running = false;
                    isStopped = true;
                }

                /*SelectedFragment = new RunFragment();
                FragmentManager fmana = getFragmentManager();
                FragmentTransaction ftransacti = fmana.beginTransaction();
                ftransacti.replace(R.id.fragment_container, SelectedFragment,"StartCorridaFragment");
                ftransacti.addToBackStack("IrCorridaFragment");
                ftransacti.commit();*/
            }
        });



        Button finalizar = (Button) findViewById(R.id.Finalizar);
        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DATAS","ON CLICK");
                String data = day+"/"+month+"/"+year;
                datas.add(data);
                Fragment p = new HistoricoCorridas();
                Bundle args = new Bundle();
                args.putStringArrayList("DATAS", datas);
                p.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, p).commit();
                Log.d("DATAS","Faz comit");
            }
        });







    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }


    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }


    public double FlatDistance(double lat1, double lat2, double lon1, double lon2) {

        double latdif = Math.pow(Math.toRadians(lat2 - lat1), 2);
        double longdif = Math.pow(Math.toRadians(lon2 - lon1), 2);

        return Math.sqrt(latdif + longdif);

    }


    private static double distance2(double lat1, double lat2, double lon1, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::	This function converts decimal degrees to radians						 :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::	This function converts radians to decimal degrees						 :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


}