package com.example.android.activitygo;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.activitygo.model.Grupo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyGroupsFragment extends Fragment {

    private ArrayList<String> myGroups = new ArrayList<String>();
    private ListView listView;
    private ArrayAdapter<String> listViewAdapter;
    private DatabaseReference databaseGrupo;

    private String username;
    private Dialog dialogResultadoInexistente;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_groups, container, false);
        ((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Os meus grupos:");
        databaseGrupo = FirebaseDatabase.getInstance().getReference("grupos");
        dialogResultadoInexistente = new Dialog(getContext());


        username = getArguments().getString("USERNAME");

        databaseGrupo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valores = "";
                ArrayList<String> array = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Grupo g = userSnapshot.getValue(Grupo.class);
                    for (String nomeElemento : g.getElementosGrupo()) {
                        if (nomeElemento.equals(username)) {
                            myGroups.add(g.getNome());
                        }
                    }

                }

                if (!myGroups.isEmpty()) {

                    listView = (ListView) getView().findViewById(R.id.ListViewMeusGrupos);
                    listViewAdapter = new ArrayAdapter<String>(
                            getActivity(), android.R.layout.simple_list_item_1, myGroups);
                    listView.setAdapter(listViewAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                        private MyGroupFragmentElements SelectedFragment;

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Bundle args = new Bundle();
                            args.putString("NOMEGRUPO",myGroups.get(position));
                            SelectedFragment = new MyGroupFragmentElements();
                            SelectedFragment.setArguments(args);
                            FragmentManager fmana = getFragmentManager();
                            FragmentTransaction ftransacti = fmana.beginTransaction();
                            ftransacti.replace(R.id.fragment_container, SelectedFragment, "GroupFragment");
                            ftransacti.commit();
                        }
                    });




                } else {
                    resultadosInexistentesPopup();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }


    public void resultadosInexistentesPopup() {
        Button okButton;
        TextView close;
        TextView popupId;
        dialogResultadoInexistente.setContentView(R.layout.popup_password_errada);
        okButton = (Button) dialogResultadoInexistente.findViewById(R.id.okButton);
        close = (TextView) dialogResultadoInexistente.findViewById(R.id.txtClose);
        popupId = (TextView) dialogResultadoInexistente.findViewById(R.id.popUpId);
        popupId.setText("\nNão Tem grupos adicionados.");
        okButton.setOnClickListener(new View.OnClickListener() {
            private MergeGroupFragment SelectedFragment;

            @Override
            public void onClick(View v) {
                dialogResultadoInexistente.dismiss();
                Bundle args = new Bundle();
                args.putString("USERNAME", username);
                SelectedFragment = new MergeGroupFragment();
                SelectedFragment.setArguments(args);
                FragmentManager fmana = getFragmentManager();
                FragmentTransaction ftransacti = fmana.beginTransaction();
                ftransacti.replace(R.id.fragment_container, SelectedFragment, "GroupFragment");
                ftransacti.commit();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogResultadoInexistente.dismiss();
            }
        });
        dialogResultadoInexistente.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogResultadoInexistente.show();
    }


}
