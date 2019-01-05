package com.example.android.activitygo;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class IrCorridaFragment extends Fragment {

    private String username;
    private static final String TAG = "IrCorridaFragment";
    private String image_path = "";
    private String changeText = "";
    private String desporto;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ir_corrida, container, false);
        final Button start = (Button) v.findViewById(R.id.buttonStart);
        Button corrida = (Button) v.findViewById(R.id.CorridaChoose);
        Button caminhada = (Button) v.findViewById(R.id.CaminhadaChoose);
        Button futebol = (Button) v.findViewById(R.id.futebolChoose);
        Button cilismo = (Button) v.findViewById(R.id.CiclismoChoose);


        username = getArguments().getString("USERNAME");
        image_path = getArguments().getString("URI");



        corrida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeText = "Começar Corrida";
                start.setText(changeText);
                desporto = "Corrida";
            }
        });

        caminhada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeText = "Começar Caminhada";
                start.setText(changeText);
                desporto = "Caminhada";
            }
        });


        futebol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeText = "Começar Futebol";
                start.setText(changeText);
                desporto = "Futebol";
            }
        });

        cilismo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeText = "Começar Ciclismo";
                start.setText(changeText);
                desporto = "Ciclismo";
            }
        });





        start.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

               if (changeText.equals("")){
                   Toast.makeText(getContext(),"Tem que escolher o desporto",Toast.LENGTH_LONG).show();
               }else{
                   Intent i = new Intent(getContext(), MapsActivity.class);
                   i.putExtra("USERNAME", username);
                   i.putExtra("URI", image_path);
                   i.putExtra("DESPORTO",desporto);
                   startActivity(i);
               }




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