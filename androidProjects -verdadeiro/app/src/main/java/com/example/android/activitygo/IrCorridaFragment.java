package com.example.android.activitygo;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class IrCorridaFragment extends Fragment {

    private String username;
    private static final String TAG = "IrCorridaFragment";
    private String image_path = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ir_corrida, container, false);
        Button start = (Button) v.findViewById(R.id.buttonStart);
        username = getArguments().getString("USERNAME");
        image_path = getArguments().getString("URI");

        start.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent i = new Intent(getContext(), MapsActivity.class);
                i.putExtra("USERNAME", username);
                i.putExtra("URI", image_path);
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