package com.example.android.activitygo;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyGroupsFragment extends Fragment {


    public MyGroupsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Os meus grupos:");

        View v = inflater.inflate(R.layout.fragment_my_groups, container, false);
        return v;
    }
}
