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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private ArrayList<String> datasCorridas = new ArrayList<>();
    private String melhorkm;

    private BarChart barChart;
    private ArrayList<String> dates;
    private Random random;
    private ArrayList<BarEntry> barEntries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_historico_corridas, container, false);
        databaseCorrida = FirebaseDatabase.getInstance().getReference("corrida");

        lv1 = (ListView) v.findViewById(R.id.ListaResultadosDatas);
        timeS = getArguments().getString("TEMPO");
        distancia = getArguments().getDouble("DISTANCIA");
        markers = getArguments().getParcelableArrayList("MARKERS");
        value = getArguments().getString("DATAS");
        tempoPace = getArguments().getLong("TEMPOPACE");
        username = getArguments().getString("USERNAME");

        databaseCorrida.orderByChild("data").equalTo(value).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String d = String.valueOf(child.child("data").getValue());
                    datasCorridas.add(d);
                }

                listViewAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, datasCorridas);

                lv1.setAdapter(listViewAdapter);
                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Fragment p = new HistoriaStatus();
                        Bundle args = new Bundle();
                        args.putString("TEMPO", timeS);
                        args.putDouble("DISTANCIA", distancia);
                        args.putParcelableArrayList("Markers", markers);
                        args.putLong("TEMPOPACE", tempoPace);
                        args.putString("USERNAME", username);
                        p.setArguments(args);
                        getFragmentManager().beginTransaction().replace(R.id.fragmentMap, p).commit();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }

    public void createRandomBarGraph(String Date1, String Date2) {

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
    }
}