package com.example.android.activitygo;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.activitygo.model.Grupo;
import com.example.android.activitygo.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProcuraGrupos extends Fragment {

    private ArrayAdapter<String> listViewAdapter;
    private DatabaseReference databaseGrupo;
    private Dialog dialogResultadoInexistente;
    private String username;
    private Dialog dialogWrongPassword;
    private ArrayList<String> listaPessoas;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_procura_grupos, container, false);
        databaseGrupo = FirebaseDatabase.getInstance().getReference("grupos");
        dialogResultadoInexistente = new Dialog(getContext());
        dialogWrongPassword = new Dialog(getContext());


        final String pesquisa = getArguments().getString("PESQUISA");
        username = getArguments().getString("USERNAME");


        databaseGrupo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valores = "";
                final ArrayList<String> array = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Grupo g = userSnapshot.getValue(Grupo.class);
                    if (g.getNome().contains(pesquisa)) {
                        array.add(g.getNome());
                    }

                    /* for (String nomeElemento : g.getElementosGrupo()) {
                        if (nomeElemento.equals("badajaz") && g.getNome().contains(pesquisa)) {
                            array.add(g.getNome());


                        }
                    }*/

                }
                if (!array.isEmpty()) {
                    ListView listView = (ListView) getView().findViewById(R.id.ListaResultados);
                    //String[] value = getArguments().getStringArray("PROCURA");

                    listViewAdapter = new ArrayAdapter<String>(
                            getActivity(), android.R.layout.simple_list_item_1, array);
                    listView.setAdapter(listViewAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //Toast.makeText(getContext(),array.get(position),Toast.LENGTH_LONG).show();
                            confirmJoin(array.get(position));


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
        popupId.setText("\nNão existem resultados possíveis.\nTenta de novo.");
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


    public void confirmJoin(String grupo) {
        Button okButton;
        TextView close;
        TextView popupId;
        dialogWrongPassword.setContentView(R.layout.popupjuntargrupo);
        okButton = (Button) dialogWrongPassword.findViewById(R.id.okButton);
        Button cancelButton = (Button) dialogWrongPassword.findViewById(R.id.cancelButton);
        close = (TextView) dialogWrongPassword.findViewById(R.id.txtClose);
        popupId = (TextView) dialogWrongPassword.findViewById(R.id.popUpId);
        popupId.setText("\nTem a certeza que se quer juntar a " + grupo);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogWrongPassword.dismiss();
            }
        });
        final String group = grupo;

        okButton.setOnClickListener(new View.OnClickListener() {
            private ListaDeElementosJuntarGrupo SelectedFragment;

            @Override
            public void onClick(View v) {
                listaPessoas = new ArrayList<>();
                listaPessoas.add(username);
                databaseGrupo.orderByChild("elementosGrupo").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Toast.makeText(getContext(), "OLEOEOEOEOELEL", Toast.LENGTH_SHORT).show();
                            GenericTypeIndicator<Map<String, Object>> t = new GenericTypeIndicator<Map<String, Object>>() {
                            };
                            Map<String, Object> map = dataSnapshot.getValue(t);

                            /*Iterator myVeryOwnIterator = map.keySet().iterator();
                            while(myVeryOwnIterator.hasNext()) {
                                String key=(String)myVeryOwnIterator.next();
                                String value=map.get(key).toString();
                                Toast.makeText(getContext(), "Key: "+key+" Value: "+value, Toast.LENGTH_LONG).show();
                            }*/

                            //databaseGrupo.child(child.getKey()).child("elementosGrupo").setValue(listaPessoas);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Toast.makeText(getContext(), "OOIOIOIOIOIOI", Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                args.putString("NOMEGRUPO", group);
                SelectedFragment = new ListaDeElementosJuntarGrupo();
                SelectedFragment.setArguments(args);
                FragmentManager fmana = getFragmentManager();
                FragmentTransaction ftransacti = fmana.beginTransaction();
                ftransacti.replace(R.id.fragment_container, SelectedFragment, "GroupFragment");
                ftransacti.commit();
                dialogWrongPassword.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogWrongPassword.dismiss();
            }
        });
        dialogWrongPassword.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogWrongPassword.show();
    }


}

