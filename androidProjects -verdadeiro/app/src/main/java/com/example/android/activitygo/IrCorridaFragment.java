package com.example.android.activitygo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class IrCorridaFragment extends Fragment {

    public IrCorridaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_ir_corrida,container,false);

       Button start = (Button) v.findViewById(R.id.buttonStart);


       start.setOnClickListener(new View.OnClickListener() {

           public void onClick(View v) {
               Intent i = new Intent(getContext(),MapsActivity.class);
               startActivity(i);

               /*Fragment SelectedFragment = new StartCorridaFragment();
               FragmentManager fm = getFragmentManager();
               FragmentTransaction ft = fm.beginTransaction();
               ft.replace(R.id.fragment_container,  SelectedFragment,"IrCorridaEasy");
               ft.addToBackStack("IrCorridaFragment");
               ft.commit();*/
           }
       });


       return v;
    }


}
