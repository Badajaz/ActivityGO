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
public class SettingsFragment extends Fragment {

    private Button changeProfile;
    private Button changeMedidas;
    private Button centroAjuda;
    private Button calendario;
    private Button contactosImportantes;
    private Button faqs;
    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_settings_layout, container, false);
        ((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Definições:");
        Button changeProfile = (Button) v.findViewById(R.id.alterarPerfil);
        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment SelectedChangeProfileFragment = new ChangeProfileFragment();
                FragmentManager fmChangeProfile = getFragmentManager();
                FragmentTransaction ftChangeProfile = fmChangeProfile.beginTransaction();
                ftChangeProfile.replace(R.id.fragment_container,  SelectedChangeProfileFragment,"ChangeProfile");
                //ftChangeProfile.addToBackStack("RunFragment");
                ftChangeProfile.commit();
            }
        });
        return v;
    }
}
