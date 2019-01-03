package com.example.android.activitygo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.activitygo.model.Grupo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MergeGroupFragment extends Fragment {

    private CheckBox corrida;
    private CheckBox caminhada;
    private CheckBox ciclismo;
    private CheckBox futebol;

    private String nomegrupo;
    private String descricaogrupo;

    private EditText nomeGrupo;
    private EditText SearchGroup;
    private EditText descricaoGrupo;
    private String searchGrupo;
    private ArrayList<String> possiveisResultados = new ArrayList<String>();
    private ArrayList<String> grupos = new ArrayList<String>();

    private ArrayAdapter<String> listViewAdapter;
    private ListView listView;
    private DatabaseReference databaseGrupo;
    private String grupo = "";
    private String sport;

    private String username;
    private static final String TAG = "MergeGroupFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_merge_group, container, false);
        ((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Grupos:");
        possiveisResultados.add("Benfica");
        possiveisResultados.add("Braga");
        possiveisResultados.add("Sporting");
        possiveisResultados.add("Porto");
        possiveisResultados.add("LegiãoFC");

        username = getArguments().getString("USERNAME");

        databaseGrupo = FirebaseDatabase.getInstance().getReference("grupos");

        nomeGrupo = (EditText) v.findViewById(R.id.NomeCriarGrupo);
        descricaoGrupo = (EditText) v.findViewById(R.id.DescricaoGrupo);

        Button criar = (Button) v.findViewById(R.id.buttonCriar);
        corrida = v.findViewById(R.id.corridaCheck);
        ciclismo = v.findViewById(R.id.CiclismoCheck);
        futebol = v.findViewById(R.id.FutebolCheck);
        caminhada = v.findViewById(R.id.CaminhadaCheck);

        criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nomegrupo = nomeGrupo.getText().toString();
                descricaogrupo = descricaoGrupo.getText().toString();
                sport = getSport();

                databaseGrupo.orderByChild("nome").equalTo(nomegrupo).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            grupo = String.valueOf(child.child("nome").getValue());
                            if (grupo.equals(nomegrupo)) {
                                nomeGrupo.setError("ja existe esse nome");
                            }
                        }

                        if (isOnlyOneChecked() && !nomegrupo.equals("") && !descricaoGrupo.equals("") && !sport.equals("") && !grupo.equals(nomegrupo)) {

                            String id = databaseGrupo.push().getKey();
                            Grupo g = new Grupo(nomegrupo, descricaogrupo, sport);
                            Toast.makeText(getContext(), grupo, Toast.LENGTH_LONG).show();

                            g.addElementToList(username);
                            databaseGrupo.child(id).setValue(g);

                            //  Toast.makeText(getActivity(), "O grupo " + nomegrupo + " foi criado", Toast.LENGTH_LONG).show();
                            possiveisResultados.add(nomegrupo);
                            grupos.add(nomegrupo);
                            Toast.makeText(getActivity(), "O grupo " + nomegrupo + " foi criado", Toast.LENGTH_LONG).show();
                            nomeGrupo.setText("");
                            descricaoGrupo.setText("");
                            //TODO falta dar uncheck das boxes
                            if (corrida.isChecked()) {
                                corrida.toggle();
                            }
                            if (ciclismo.isChecked()) {
                                ciclismo.toggle();
                            }
                            if (caminhada.isChecked()) {
                                caminhada.toggle();
                            }
                            if (futebol.isChecked()) {
                                futebol.toggle();
                            }


                        } else {
                            if (!isOnlyOneChecked()) {
                                corrida.setError("Só pode escolher um desporto!");
                                ciclismo.setError("Só pode escolher um desporto!");
                                futebol.setError("Só pode escolher um desporto!");
                                caminhada.setError("Só pode escolher um desporto!");
                            }

                            if (nomegrupo.equals("")) {
                                nomeGrupo.setError("Tem de preencher o nome");
                            }

                            if (descricaogrupo.equals("")) {
                                descricaoGrupo.setError("Tem de preencher a descricao");
                            }

                            if (grupo.equals(nomegrupo)) {
                                nomeGrupo.setError("já existe esse nome para grupo");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        SearchGroup = (EditText) v.findViewById(R.id.NomeGrupo);
        Button procurar = (Button) v.findViewById(R.id.buttonSearch);

        procurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchGrupo = SearchGroup.getText().toString();
                String resultados = getResultados(possiveisResultados, searchGrupo);
                String[] resultadosArray = resultados.split(" ");
                ProcuraGrupos p = new ProcuraGrupos();
                Bundle args = new Bundle();
                args.putString("PESQUISA", searchGrupo);
                args.putString("USERNAME", username);
                //args.putStringArray("PROCURA", resultadosArray);
                p.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, p).commit();
            }
        });

        Button meusGrupos = (Button) v.findViewById(R.id.MeusGrupos);
        meusGrupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyGroupsFragment ldf = new MyGroupsFragment();
                Bundle args = new Bundle();
                //args.putStringArrayList("GRUPO", grupos);
                args.putString("USERNAME", username);
                ldf.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, ldf).commit();
            }
        });

        return v;
    }

    public boolean isOnlyOneChecked() {
        int count = 0;

        if (corrida.isChecked()) {
            count++;
        }

        if (caminhada.isChecked()) {
            count++;
        }

        if (ciclismo.isChecked()) {
            count++;
        }

        if (futebol.isChecked()) {
            count++;
        }
        return count == 1;
    }

    public String getSport() {
        if (corrida.isChecked()) {
            return "CORRIDA";
        }

        if (caminhada.isChecked()) {
            return "CAMINHADA";
        }

        if (ciclismo.isChecked()) {
            return "CAMINHADA";
        }

        if (futebol.isChecked()) {
            return "FUTEBOL";
        }
        return "";
    }

    public String getResultados(ArrayList<String> possiveisResultados, String pesquisa) {
        String resultados = "";
        for (String resultado : possiveisResultados) {
            if (resultado.contains(pesquisa)) {
                resultados += resultado + " ";
            }
        }
        return resultados;
    }
}