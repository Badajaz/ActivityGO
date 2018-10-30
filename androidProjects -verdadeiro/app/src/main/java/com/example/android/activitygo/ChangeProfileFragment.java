package com.example.android.activitygo;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeProfileFragment extends Fragment {

    public ChangeProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_profile, container, false);
        ((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Alterar Perfil:");
        return v;
    }
}
