package com.example.android.activitygo;

import android.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartCorridaFragment extends Fragment {

    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;

    private Fragment SelectedFragment;

    public StartCorridaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_start_corrida, container, false);
        Button Stop = (Button) v.findViewById(R.id.StopRun);
        Button Start = (Button) v.findViewById(R.id.StartRun);
        chronometer = v.findViewById(R.id.chronometer);

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!running) {
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.start();
                    running = true;
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
                }

                /*SelectedFragment = new RunFragment();
                FragmentManager fmana = getFragmentManager();
                FragmentTransaction ftransacti = fmana.beginTransaction();
                ftransacti.replace(R.id.fragment_container, SelectedFragment,"StartCorridaFragment");
                ftransacti.addToBackStack("IrCorridaFragment");
                ftransacti.commit();*/
            }
        });

        return v;
    }
}