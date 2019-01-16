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

    private int page;

    private String username;

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
        username = getArguments().getString("USERNAME");

        juntargrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(v.getContext(), GroupTab.class);
                intent.putExtra("One", page);
                startActivity(intent);*/
                Fragment SelectedFragment = new JoinGroup();
                Bundle args = new Bundle();
                args.putString("USERNAME", username);
                SelectedFragment.setArguments(args);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,  SelectedFragment,"GroupFragment");
                ft.addToBackStack("GroupFragment");
                ft.commit();




            }
        });

        criargrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*page = 1;
                Intent intent = new Intent(v.getContext(), GroupTab.class);
                intent.putExtra("Two", page);
                startActivity(intent);*/

                Fragment SelectedFragment = new CreateGroup();
                Bundle args = new Bundle();
                args.putString("USERNAME", username);
                SelectedFragment.setArguments(args);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,  SelectedFragment,"CriarGrupoFragment");
                ft.addToBackStack("GroupFragment");
                ft.commit();
            }
        });

        myGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyGroupsFragment ldf = new MyGroupsFragment();
                FragmentManager fmMyGroupsFragment = getFragmentManager();
                FragmentTransaction ftMyGroupsFragment = fmMyGroupsFragment.beginTransaction();
                Bundle args = new Bundle();
                //args.putStringArrayList("GRUPO", grupos);
                args.putString("USERNAME", username);
                ldf.setArguments(args);
                ftMyGroupsFragment.replace(R.id.fragment_container, ldf, "MyGroupsFragment");
                ftMyGroupsFragment.addToBackStack("MyGroupsFragment");
                ftMyGroupsFragment.commit();
            }
        });

        return v;
    }
}