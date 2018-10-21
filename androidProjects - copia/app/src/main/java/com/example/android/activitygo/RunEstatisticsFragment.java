package com.example.android.activitygo;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class RunEstatisticsFragment extends Fragment {


    public RunEstatisticsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String strtext = getArguments().getString("CID");
        Toast.makeText(getActivity(), strtext,
                Toast.LENGTH_LONG).show();
        //TextView err = (TextView)getView().findViewById(R.id.duracaoEst);
        //err.setText(strtext);
        return inflater.inflate(R.layout.fragment_run_estatistics, container, false);




    }

}
