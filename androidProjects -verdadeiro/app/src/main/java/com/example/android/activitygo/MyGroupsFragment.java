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
public class MyGroupsFragment extends Fragment {

    private ArrayList<String> myGroups = new ArrayList<String>();
    private ListView listView;
    private ArrayAdapter<String> listViewAdapter;

    public MyGroupsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_groups, container, false);
        ((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Os meus grupos:");
        ArrayList<String> value = getArguments().getStringArrayList("GRUPO");
        myGroups.addAll(value);

        listView = (ListView) v.findViewById(R.id.ListViewMeusGrupos);
        listViewAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1,myGroups);
        listView.setAdapter(listViewAdapter);




        return v;
    }
}
