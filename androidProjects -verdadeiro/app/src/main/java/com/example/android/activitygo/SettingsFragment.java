package com.example.android.activitygo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Button changeProfile;
    private Button changeMedidas;
    private Button centroAjuda;
    private Button calendario;
    private Button contactosImportantes;
    private Button faqs;
    private Spinner sp;
    private Spinner sp2;
    private ArrayAdapter <CharSequence> adapter;
    private ArrayAdapter <CharSequence> adapter2;
    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_settings_layout, container, false);
        ((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Definições:");

        sp = (Spinner) v.findViewById(R.id.contactosImportantesSpinner);
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.contactosImportantes, R.layout.spinner_item_layout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(this);

        sp2 = (Spinner) v.findViewById(R.id.faqsSpinner);
        adapter2 = ArrayAdapter.createFromResource(getContext(), R.array.faqs, R.layout.spinner_faqs_layout);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adapter2);
        sp2.setOnItemSelectedListener(this);

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

        Button terminarSessao = (Button) v.findViewById(R.id.terminarSessao);
        terminarSessao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), PopUpTerminarSessao.class));
            }
        });

        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
