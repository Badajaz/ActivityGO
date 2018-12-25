package com.example.android.activitygo;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class RunFragment extends Fragment {

    private String date;
    private TextView dataCorridaTv;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button searchAllCorridas;
    private Button procurarDatas;

    private ArrayList<String> possiveisResultadosDatas = new ArrayList<String>();
    private static final String TAG = "Run Fragment";

    public RunFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_run, container, false);
        possiveisResultadosDatas.add("02/02/2018");
        possiveisResultadosDatas.add("04/02/2018");
        possiveisResultadosDatas.add("05/02/2018");
        possiveisResultadosDatas.add("06/02/2018");
        possiveisResultadosDatas.add("10/02/2018");

        dataCorridaTv = (TextView) v.findViewById(R.id.DataCorridaProcuraTextView);
        dataCorridaTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                date = dayOfMonth + "/" + month + "/" + year;
                dataCorridaTv.setText(date);
                dataCorridaTv.setTextColor(Color.parseColor("#006aff"));
            }
        };

        searchAllCorridas = (Button) v.findViewById(R.id.todasAsCorridasButtonSearch);
        searchAllCorridas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getArguments().getString("USERNAME");
                HistoricoCorridas hc = new HistoricoCorridas();
                Bundle args = new Bundle();
                String[] array = new String[possiveisResultadosDatas.size()];
                array = possiveisResultadosDatas.toArray(array);
                args.putStringArray("DATAS", array);
                args.putString("USERNAME", username);
                args.putInt("CLASSINT", 1);
                hc.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, hc).commit();
            }
        });

        procurarDatas = (Button) v.findViewById(R.id.botaoProcurarDatas);
        procurarDatas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getArguments().getString("USERNAME");
                HistoricoCorridas hc = new HistoricoCorridas();
                TextView dataTextView = getView().findViewById(R.id.DataCorridaProcuraTextView);
                String data = dataTextView.getText().toString();
                if (data != "" ){
                    Bundle args = new Bundle();
                    args.putInt("CLASSINT", 0);
                    args.putString("DATA", data);
                    args.putString("USERNAME", username);
                    hc.setArguments(args);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, hc).commit();
                }else{
                    Toast.makeText(getContext(),"INSIRA A DATA",Toast.LENGTH_LONG).show();
                }
            }
        });
        return v;
    }

    private String getResultados(ArrayList<String> possiveisResultados, String pesquisa) {
        String resultados = "";
        for (String resultado : possiveisResultados) {
            if (resultado.contains(pesquisa)) {
                resultados += resultado + " ";
            }
        }
        return resultados;
    }
}