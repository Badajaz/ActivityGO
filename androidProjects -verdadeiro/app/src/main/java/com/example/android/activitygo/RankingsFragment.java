package com.example.android.activitygo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankingsFragment extends Fragment {

    public RankingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Rankings:");

        View v = inflater.inflate(R.layout.fragment_rankings, container, false);
        Button corridaGeral = (Button) v.findViewById(R.id.buttonCorridaGeral);
        corridaGeral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment SelectedFragment = new TableRankingsFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,  SelectedFragment,"TableRankingsFragment");
                ft.addToBackStack("RankingsFragment");
                ft.commit();
            }
        });
        return v;
    }
}