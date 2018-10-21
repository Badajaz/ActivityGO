package com.example.android.activitygo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class RunFragment extends Fragment {

    Button firstLineButton;
    public RunFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_run, container, false);

        firstLineButton = (Button) v.findViewById(R.id.plusButtonLine1);
        firstLineButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                /*T*/
                TextView tv2 = (TextView) getView().findViewById(R.id.duracaoTextView);
                String duracao = tv2.getText().toString();
                Fragment fr=new RunEstatisticsFragment();
                FragmentManager fm=getFragmentManager();
                android.app.FragmentTransaction ft=fm.beginTransaction();
                Bundle args = new Bundle();
                args.putString("CID", duracao);
                fr.setArguments(args);
                ft.replace(R.id.fragment_container, fr);
                ft.commit();





/*
                Toast.makeText(getActivity(), duracao,
                        Toast.LENGTH_LONG).show();
                //TextView err = (TextView)getView().findViewById(R.id.DuracaoEstatistica);
                //err.setText(duracao);
                Fragment SelectedFragment = new RunEstatisticsFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,  SelectedFragment);
                ft.commit();
*/
            }
        });

        return v;
    }


}
