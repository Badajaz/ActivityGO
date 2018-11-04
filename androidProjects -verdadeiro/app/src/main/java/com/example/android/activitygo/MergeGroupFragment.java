package com.example.android.activitygo;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MergeGroupFragment extends Fragment {

    private CheckBox corrida;
    private CheckBox caminhada;
    private String nomegrupo;
    private EditText nomeGrupo;
    private EditText SearchGroup;
    private String searchGrupo;
    private ArrayList<String> possiveisResultados = new ArrayList<String>();
    private ArrayList<String> grupos = new ArrayList<String>();


    private ArrayAdapter<String> listViewAdapter;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_merge_group, container, false);
        possiveisResultados.add("Benfica");
        possiveisResultados.add("Braga");
        possiveisResultados.add("Sporting");
        possiveisResultados.add("Porto");
        possiveisResultados.add("LegiãoFC");


        nomeGrupo = (EditText) v.findViewById(R.id.NomeCriarGrupo);


        Button criar = (Button) v.findViewById(R.id.buttonCriar);
        corrida = v.findViewById(R.id.corridaCheck);
        caminhada = v.findViewById(R.id.CaminhadaCheck);
        criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomegrupo = nomeGrupo.getText().toString();
                    if (isOneChecked() && !nomegrupo.equals("")){
                        Toast.makeText(getActivity(),"O grupo "+nomegrupo+"Foi criado",Toast.LENGTH_LONG).show();
                        possiveisResultados.add(nomegrupo);
                        grupos.add(nomegrupo);
                    }else{
                        Toast.makeText(getActivity(),"erro",Toast.LENGTH_LONG).show();
                    }

            }
        });

        SearchGroup = (EditText) v.findViewById(R.id.NomeGrupo);
        Button procurar = (Button) v.findViewById(R.id.buttonSearch);


        listView = (ListView) v.findViewById(R.id.ListaResultados);


        procurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchGrupo = SearchGroup.getText().toString();
                String resultados = getResultados(possiveisResultados,searchGrupo);
                String [] resultadosArray = resultados.split(" ");
                listViewAdapter = new ArrayAdapter<String>(
                        getActivity(), android.R.layout.simple_list_item_1,resultadosArray);
                listView.setAdapter(listViewAdapter);
            }
        });



        Button meusGrupos = (Button) v.findViewById(R.id.MeusGrupos);
        meusGrupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MyGroupsFragment ldf = new MyGroupsFragment ();
                Bundle args = new Bundle();
                args.putStringArrayList("GRUPO",grupos);
                ldf.setArguments(args);
                getFragmentManager().beginTransaction().add(R.id.fragment_container, ldf).commit();




            }
        });














        return v;
    }



    public  boolean  isOneChecked(){

        if (corrida.isChecked() &&  caminhada.isChecked()){
            return false;
        }
       return true;
    }


    public String getResultados(ArrayList<String> possiveisResultados ,String pesquisa){
        String resultados="";
        for (String resultado:possiveisResultados){
            if (resultado.contains(pesquisa)){
                resultados+=resultado+" ";
            }
        }
        return resultados;
    }





}
