package com.example.android.activitygo;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProcuraGrupos extends Fragment {


    private ArrayAdapter<String> listViewAdapter;

    public ProcuraGrupos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View v = inflater.inflate(R.layout.fragment_procura_grupos, container, false);
        ListView listView = (ListView) v.findViewById(R.id.ListaResultados);
        String [] value = getArguments().getStringArray("PROCURA");

        listViewAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1,value);
        listView.setAdapter(listViewAdapter);
        return v;
    }

}
