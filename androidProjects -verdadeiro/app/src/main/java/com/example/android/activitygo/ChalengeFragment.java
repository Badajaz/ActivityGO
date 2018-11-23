package com.example.android.activitygo;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChalengeFragment extends Fragment {

    public ChalengeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Desafios:");
        View v = inflater.inflate(R.layout.fragment_chalenge, container, false);
        ProgressBar corrida = (ProgressBar) v.findViewById(R.id.progressBarCorrida);
        corrida.setMax(5);
        corrida.setProgress(3);
        corrida.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FFA500")));

        return v;
    }
}
