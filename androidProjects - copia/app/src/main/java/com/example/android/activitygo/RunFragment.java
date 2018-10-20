package com.example.android.activitygo;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class RunFragment extends Fragment {

    Button firstLineButton;
    public RunFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_run, container, false);

        firstLineButton = (Button) v.findViewById(R.id.plusButtonLine1);
        firstLineButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle extras = new Bundle();
                showRunStats(v, extras);
            }
        });

        return v;
    }

    public void showRunStats(View view, Bundle extras){
        Intent intent = new Intent(getActivity().getApplicationContext(), EstatisticasActivity.class);
        //TextView tv1 = (TextView) view.findViewById(R.id.dataTextView);
        //TextView tv2 = (TextView) view.findViewById(R.id.duracaoTextView);
        //TextView tv3 = (TextView) view.findViewById(R.id.distanciaTextView);
        //extras.putString("data", tv1.getText().toString());
        //extras.putString("duracao", tv2.getText().toString());
        //extras.putString("distancia", tv3.getText().toString());
        //intent.putExtra("data", tv1.getText().toString());
        startActivity(intent);
    }
}
