package com.example.android.activitygo;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartCorridaFragment extends Fragment {

    private Fragment SelectedFragment;
    public StartCorridaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_start_corrida, container, false);
        Button Stop = (Button) v.findViewById(R.id.StopRun);

        Stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SelectedFragment = new RunFragment();
                FragmentManager fmana = getFragmentManager();
                FragmentTransaction ftransacti = fmana.beginTransaction();
                ftransacti.replace(R.id.fragment_container, SelectedFragment,"StartCorridaFragment");
                ftransacti.addToBackStack("IrCorridaFragment");
                ftransacti.commit();
            }
        });




        return v;
    }

}
