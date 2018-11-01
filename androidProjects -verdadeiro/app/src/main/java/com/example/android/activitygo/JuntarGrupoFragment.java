package com.example.android.activitygo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class JuntarGrupoFragment extends android.support.v4.app.Fragment {


    private static final String TAG = "CriarGrupo";

    private String message;
    private Bundle bundle;
    private String txtPesquisa;
    private String nomeGrupo;

    public JuntarGrupoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_juntar_grupo, container, false);
        Button search = (Button) v.findViewById(R.id.buttonSearch);
        final EditText txtDescription = v.findViewById(R.id.NomeGrupo);



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                message = txtDescription.getText().toString();
                //Toast.makeText(getActivity(), message,
                        //Toast.LENGTH_LONG).show();
                bundle = new Bundle();
                bundle.putString("Pesquisa", message);

               // TextView myAwesomeTextView = v.findViewById(R.id.textViewPesquisar);
                //myAwesomeTextView.setText(message);

                Fragment SelectedFragment = new TextViewJuntarFragment();
                android.support.v4.app.FragmentTransaction tran = getFragmentManager().beginTransaction();
                tran.replace(R.id.frame_test, SelectedFragment);
                SelectedFragment.setArguments(bundle);
                tran.commit();
            }
        });
        return v;
    }

}
