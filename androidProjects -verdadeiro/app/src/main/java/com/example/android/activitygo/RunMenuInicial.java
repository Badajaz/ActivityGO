package com.example.android.activitygo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RunMenuInicial extends Fragment {


    private String username;
    private TextView name1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_run_menu_inicial, container, false);

        //ArrayList<String> arraylist = getArguments().getStringArrayList("USERPROFILE");
        username = getArguments().getString("USERNAME");

        //((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Activity GO:");

        final Button historial = (Button) v.findViewById(R.id.buttonHistorial);
        Button irCorrida = (Button) v.findViewById(R.id.buttonIrCorrida);
        Button meusGrupos = (Button) v.findViewById(R.id.buttonMeusGrupos);
        //Button alterar = (Button) v.findViewById(R.id.alterarDesportoPraticado);
        name1 = (TextView) v.findViewById(R.id.namePessoaMenuInicial);
        // name1.setText("Bem vindo " + arraylist.get(0) + " " + arraylist.get(1) + "!");

        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment SelectedFragment = new RunFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, SelectedFragment, "RunMenuInicial");
                ft.addToBackStack("RunFragment");
                ft.commit();
            }
        });

        irCorrida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment SelectedFragment = new IrCorridaFragment();
                Bundle toRunMenuInicial = new Bundle();
                toRunMenuInicial.putString("USERNAME", username);
                SelectedFragment.setArguments(toRunMenuInicial);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, SelectedFragment, "RunMenuInicial");
                ft.addToBackStack("RunFragment");
                ft.commit();

            }
        });

        meusGrupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment SelectedFragmentMeusGrupos = new MergeGroupFragment();
                FragmentManager fmMeusGrupos = getFragmentManager();
                FragmentTransaction ftMeusGrupos = fmMeusGrupos.beginTransaction();
                ftMeusGrupos.replace(R.id.fragment_container, SelectedFragmentMeusGrupos, "RunMenuInicial");
                ftMeusGrupos.addToBackStack("RunFragment");
                ftMeusGrupos.commit();

            }
        });

        /*alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */
        return v;
    }
}