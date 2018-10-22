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
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class JuntarGrupoFragment extends Fragment {

    private String message;
    public JuntarGrupoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_juntar_grupo, container, false);
        Button search = (Button) v.findViewById(R.id.buttonSearch);
        EditText txtDescription = v.findViewById(R.id.NomeGrupo);
        message = txtDescription.getText().toString();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView myAwesomeTextView = v.findViewById(R.id.textViewPesquisar);
                myAwesomeTextView.setText(message);
            }
        });



        return v;
    }

}
