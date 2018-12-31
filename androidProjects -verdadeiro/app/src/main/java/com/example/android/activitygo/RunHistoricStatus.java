package com.example.android.activitygo;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class RunHistoricStatus extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_run_historic_status, container, false);
        String statistics = getArguments().getString("RUN_STATISTICS");
        String[] splitStatistics = statistics.split("   ");
        TextView time = v.findViewById(R.id.TimeHistoric);
        TextView distance = v.findViewById(R.id.DistanceHistoric);
        TextView pace = v.findViewById(R.id.PaceHistoric);
        time.setText(splitStatistics[2]);
        distance.setText(splitStatistics[1]);
        pace.setText(splitStatistics[3]);


        return  v;

    }

}
