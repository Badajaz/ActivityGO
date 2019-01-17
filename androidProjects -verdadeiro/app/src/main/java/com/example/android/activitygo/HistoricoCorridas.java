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
import android.widget.TextView;

import com.example.android.activitygo.model.Corrida;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private int ClassInt;
    private String melhorkm;
    private ArrayList<String> dates;

    private GraphView graph;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_historico_corridas, container, false);
        databaseCorrida = FirebaseDatabase.getInstance().getReference("corrida");
        graph = (GraphView) v.findViewById(R.id.graph);

        lv1 = (ListView) v.findViewById(R.id.ListaResultadosDatas);
        timeS = getArguments().getString("TEMPO");
        distancia = getArguments().getDouble("DISTANCIA");
        markers = getArguments().getParcelableArrayList("MARKERS");
        value = getArguments().getString("DATA");
        tempoPace = getArguments().getLong("TEMPOPACE");
        username = getArguments().getString("USERNAME");
        ClassInt = getArguments().getInt("CLASSINT");

        databaseCorrida.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            Map<String, double[]> coordenatesByStatus = new HashMap<>();
            private ArrayList<String> datasCorridas = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Corrida c = child.getValue(Corrida.class);
                    if (ClassInt == 0) {
                        if (c.getData().equals(value) && c.getCoordenadas() != null) {
                            String status = c.getData() + "   " + Math.round(c.getDistancia()) + "m   " + c.getTempo() + "   " + c.getPace();
                            datasCorridas.add(status);
                            double[] currentCoordinates = ArrayListToArray(c.getCoordenadas());
                            coordenatesByStatus.put(status, currentCoordinates);
                        }
                    } else {
                        String status = c.getData() + "   " + Math.round(c.getDistancia()) + "m   " + c.getTempo() + "   " + c.getPace();
                        datasCorridas.add(status);
                        double[] currentCoordinates = ArrayListToArray(c.getCoordenadas());
                        coordenatesByStatus.put(status, currentCoordinates);
                    }
                }

                if (datasCorridas.size() == 0) {
                    TextView noResults = getView().findViewById(R.id.NoResults);
                    noResults.setText("Não foram encontrados resultados");
                    int lime = getResources().getColor(R.color.orange);
                    noResults.setTextColor(lime);
                    noResults.setTextSize(20);
                }


                listViewAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, datasCorridas);
                lv1.setAdapter(listViewAdapter);
                lv1.setOnItemClickListener((parent, view, position, id) -> {
                    Fragment p = new RunHistoricStatus();
                    Bundle args = new Bundle();
                    args.putString("RUN_STATISTICS", datasCorridas.get(position));
                    args.putDoubleArray("COORDINATES", coordenatesByStatus.get(datasCorridas.get(position)));
                    /* args.putString("TEMPO", timeS);
                    args.putDouble("DISTANCIA", distancia);
                    args.putParcelableArrayList("Markers", markers);
                    args.putLong("TEMPOPACE", tempoPace);
                    args.putString("USERNAME", username);*/
                    p.setArguments(args);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, p, "RunFragment").commit();
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseCorrida.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> datas = new ArrayList<>();
                ArrayList<Double> distance = new ArrayList<>();
                String firstDate = ""; // primeira data de uma corrida
                String lastDate = ""; // ultima data

                if (dataSnapshot.exists()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Corrida c = child.getValue(Corrida.class);
                        int raceMonth = Integer.parseInt(c.getData().split("/")[1]);
                        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
                        if (raceMonth == currentMonth) {
                            if (datas.indexOf(c.getData()) == -1){
                                datas.add(c.getData());
                                distance.add(c.getDistancia());
                            }else{
                                int index = datas.indexOf(c.getData());
                                distance.set(index,c.getDistancia()+distance.get(index));
                            }

                        }

                    }
                }

                DataPoint dpArray[] = new DataPoint[datas.size()];

                for (int index = 0; index < datas.size(); index++) {
                    try {
                        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(datas.get(index));
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date1);
                        DataPoint dp = new DataPoint(cal.get(Calendar.DAY_OF_MONTH), distance.get(index));
                        dpArray[index] = dp;

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                DataPoint dpDisplayArray[] = new DataPoint[dpArray.length];
                for (int counter = 0; counter < dpDisplayArray.length; counter++) {
                    // ATENCAO: SE OS VALORES FOREM DE MESES DIFERENTES (POR EXEMPLO, UMA CORRIDA A 30 DE DEZEMBRO E UMA
                    // A 1 DE JANEIRO ENTAO VAI DAR ERRO, PORQUE FICA COM UMA LISTA [30,1] E A LISTA NAO ESTA ORDENADA
                    // ESTA TUDO BEM PORQUE AS PESSOAS IRAO CORRER SEMPRE NO MESMO MES, NAO NUNCA EM MESES DIFERENTES
                    dpDisplayArray[dpDisplayArray.length - 1 - counter] = dpArray[dpArray.length - 1 - counter];
                }

               LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dpDisplayArray);
                graph.addSeries(series);
                graph.setTitle("Progresso no último mês (km/dia)");

                graph.getViewport().setYAxisBoundsManual(true);
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setMinX(getMinDistance(dpDisplayArray));
                graph.getViewport().setMaxX(getMaxDistance(dpDisplayArray));
                graph.getViewport().setMaxY(Collections.max(distance) + 1000);
                double a = Collections.max(distance) + 1000;
                graph.getViewport().setMinY(0);

                graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                    @Override
                    public String formatLabel(double value, boolean isValueX) {
                        if (isValueX) {
                            // show normal x values
                            return super.formatLabel(value, isValueX);
                        } else {
                            // show currency for y values
                            return super.formatLabel(value, isValueX) + " m";
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       /* LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)});
        graph.addSeries(series);
        graph.setTitle("Progresso no último mês (km/dia)");

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);
        //graph.getViewport().setMinX(dpDisplayArray[0].getX() + 1);
       // graph.getViewport().setMaxX(dpDisplayArray[dpDisplayArray.length - 1].getX() - 1);
       // graph.getViewport().setMaxY(Collections.max(distance) + 1000);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    // show currency for y values
                    return super.formatLabel(value / 1000, isValueX) + " km";
                }
            }
        });*/





        return v;
    }

    private double[] ArrayListToArray(ArrayList<Double> array) {
        double[] arrayDouble = new double[array.size()];
        int i = 0;
        for (double coordenada : array) {
            arrayDouble[i] = coordenada;
            i++;
        }

        return arrayDouble;
    }

    private Double getMaxDistance(DataPoint[] array){
        double max = 0;
        for (DataPoint d : array){
            if (max < d.getX()){
                max = d.getX();
            }
        }
        return  max;
    }


    private Double getMinDistance(DataPoint[] array){
        double min = array[0].getX();
        for (DataPoint d : array){
            if (min > d.getX()){
                min = d.getX();
            }
        }
        return  min;
    }



}