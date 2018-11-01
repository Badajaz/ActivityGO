package com.example.android.activitygo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment {

    public GroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Grupos");

        View v = inflater.inflate(R.layout.fragment_group, container, false);
        Button criargrupo = (Button) v.findViewById(R.id.buttonCriarGrupo);
        Button juntargrupo = (Button) v.findViewById(R.id.buttonJuntarGrupo);
        Button myGroups = (Button) v.findViewById(R.id.buttonMyGroups);

        juntargrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Fragment SelectedFragment = new JuntarGrupoFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,  SelectedFragment,"JuntarGrupoFragment");
                ft.addToBackStack("GroupFragment");
                ft.commit();*/
            }
        });

        criargrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), GroupTab.class);
                startActivity(intent);
                /*Fragment SelectedFragment = new CriarGrupoFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,  SelectedFragment,"CriarGrupoFragment");
                ft.addToBackStack("GroupFragment");
                ft.commit();*/
            }
        });

        myGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment myGroupsSelectedFragment = new MyGroupsFragment();
                FragmentManager fmMyGroups = getFragmentManager();
                FragmentTransaction ftMyGroups = fmMyGroups.beginTransaction();
                ftMyGroups.replace(R.id.fragment_container, myGroupsSelectedFragment, "CriarGrupoFragment");
                ftMyGroups.addToBackStack("GroupFragment");
                ftMyGroups.commit();
            }
        });

        return v;
    }
}