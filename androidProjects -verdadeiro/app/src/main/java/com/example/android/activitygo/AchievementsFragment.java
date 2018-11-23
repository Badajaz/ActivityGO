package com.example.android.activitygo;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class AchievementsFragment extends Fragment {

    public AchievementsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Conquistas:");
        View v = inflater.inflate(R.layout.fragment_achievements, container, false);







        return v;
    }
}