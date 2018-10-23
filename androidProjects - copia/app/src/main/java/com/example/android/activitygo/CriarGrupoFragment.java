package com.example.android.activitygo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class CriarGrupoFragment extends Fragment {

    private Button criarGrupo;
    private String txtPesquisa;
    private Bundle bundle;

    public CriarGrupoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Grupos");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_criar_grupo, container, false);
        EditText txt = v.findViewById(R.id.NomeGrupo);
        txtPesquisa = txt.getText().toString();

        criarGrupo = (Button) v.findViewById(R.id.buttonCriar);
        criarGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment SelectedFragment = new CriarGrupoFragment();
                FragmentManager fmCriarGrupoFragment = getFragmentManager();
                FragmentTransaction ftCriarGrupoFragment = fmCriarGrupoFragment.beginTransaction();
                ftCriarGrupoFragment.replace(R.id.fragment_container, SelectedFragment);

                ftCriarGrupoFragment.commit();
            }
        });
        return v;
    }
}
