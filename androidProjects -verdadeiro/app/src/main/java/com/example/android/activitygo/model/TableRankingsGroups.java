package com.example.android.activitygo.model;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.activitygo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TableRankingsGroups extends Fragment {


    public TableRankingsGroups() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_table_rankings_grups, container, false);
        return v;
    }

}
