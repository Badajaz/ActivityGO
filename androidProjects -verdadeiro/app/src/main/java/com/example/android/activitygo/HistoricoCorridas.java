package com.example.android.activitygo;

import android.app.Fragment;
import android.content.ClipData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.activitygo.model.Corrida;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
    private BarChart barChart;
    private ArrayList<String> dates;
    private Random random;
    private ArrayList<BarEntry> barEntries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_historico_corridas, container, false);
        databaseCorrida = FirebaseDatabase.getInstance().getReference("corrida");

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
                        datasCorridas.add(c.getData() + "   " + Math.round(c.getDistancia()) + "m   " + c.getTempo() + "   " + c.getPace());
                    }
                }

                listViewAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, datasCorridas);

                lv1.setAdapter(listViewAdapter);
                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Fragment p = new RunHistoricStatus();
                        Bundle args = new Bundle();
                        args.putString("RUN_STATISTICS", datasCorridas.get(position));
                      //  args.putSerializable("COORDINATES", coordenatesByStatus.get(datasCorridas.get(position)));
                        /* args.putString("TEMPO", timeS);
                        args.putDouble("DISTANCIA", distancia);
                        args.putParcelableArrayList("Markers", markers);
                        args.putLong("TEMPOPACE", tempoPace);
                        args.putString("USERNAME", username);*/
                        p.setArguments(args);
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, p, "RunFragment").commit();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        barChart = (BarChart) v.findViewById(R.id.bargraph);

        databaseCorrida.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> datas = new ArrayList<>();
                ArrayList<Double> distance = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        //String pwd = String.valueOf(child.child("password").getValue());
                        Corrida c = child.getValue(Corrida.class);
                        datas.add(c.getData());
//                        Log.d("DATASSSSS",c.getData());
                        distance.add(c.getDistancia());
                    }
                }

                ArrayList<BarEntry> barEntry = new ArrayList<>();
                int i = 0;
                for (double s : distance) {
                    barEntry.add(new BarEntry((float) s, i));
                    i++;
                }
                BarDataSet barDataSet = new BarDataSet(barEntry, "Datas");

                BarData theData = new BarData(datas, barDataSet);

                barChart.setData(theData);
                barChart.setTouchEnabled(true);
                barChart.setDragEnabled(true);
                barChart.setScaleEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
       /* BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");
        BarData barData = new BarData(dates, barDataSet);
        barChart.setData(barData);
        barChart.setDescription("My First Bar Graph!");

        createRandomBarGraph("2016/05/05","2016/06/01");
*/
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


    /* public void createRandomBarGraph(String Date1, String Date2) {


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        try {
            Date date1 = simpleDateFormat.parse(Date1);
            Date date2 = simpleDateFormat.parse(Date2);

            Calendar mDate1 = Calendar.getInstance();
            Calendar mDate2 = Calendar.getInstance();
            mDate1.clear();
            mDate2.clear();

            mDate1.setTime(date1);
            mDate2.setTime(date2);

            dates = new ArrayList<>();
            dates = getList(mDate1, mDate2);

            barEntries = new ArrayList<>();
            float max = 0f;
            float value = 0f;
            random = new Random();
            for (int j = 0; j < dates.size(); j++) {
                max = 100f;
                value = random.nextFloat() * max;
                barEntries.add(new BarEntry(value, j));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");
        BarData barData = new BarData(dates, barDataSet);
        barChart.setData(barData);
        barChart.setDescription("My First Bar Graph!");

    }

    public ArrayList<String> getList(Calendar startDate, Calendar endDate) {
        ArrayList<String> list = new ArrayList<String>();
        while (startDate.compareTo(endDate) <= 0) {
            list.add(getDate(startDate));
            startDate.add(Calendar.DAY_OF_MONTH, 1);
        }
        return list;
    }

    public String getDate(Calendar cld) {
        String curDate = cld.get(Calendar.YEAR) + "/" + (cld.get(Calendar.MONTH) + 1) + "/"
                + cld.get(Calendar.DAY_OF_MONTH);
        try {
            Date date = new SimpleDateFormat("yyyy/MM/dd").parse(curDate);
            curDate = new SimpleDateFormat("yyy/MM/dd").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return curDate;
    }*/
}