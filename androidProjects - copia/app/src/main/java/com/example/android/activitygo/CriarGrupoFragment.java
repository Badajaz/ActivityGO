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
public class CriarGrupoFragment extends Fragment {

    private Button criarGrupo;

    public CriarGrupoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Grupos");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_criar_grupo, container, false);

        criarGrupo = (Button) v.findViewById(R.id.buttonCriar);
        criarGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment SelectedFragment = new AllGroupsFragment();
                FragmentManager fmAllGroupsFragment = getFragmentManager();
                FragmentTransaction ftAllGroupsFragment = fmAllGroupsFragment.beginTransaction();
                ftAllGroupsFragment.replace(R.id.fragment_container, SelectedFragment);
                ftAllGroupsFragment.commit();
            }
        });
        return v;
    }
}
