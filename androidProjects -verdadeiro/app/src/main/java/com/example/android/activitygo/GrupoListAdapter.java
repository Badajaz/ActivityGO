package com.example.android.activitygo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.activitygo.model.Grupo;

import java.util.ArrayList;

class GrupoListAdapter extends ArrayAdapter<Grupo> {

    private final static String TAG = "GrupoListAdapter";
    private Context mContext;
    int mResource;

    public GrupoListAdapter(Context context, int resource, ArrayList<Grupo> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String criador = getItem(position).getCriador();
        String nome = getItem(position).getNome();
        String descricao = getItem(position).getDescricao();
        String desporto = getItem(position).getDesporto();
        int querEntrar = getItem(position).getQuerEntrar();
        String quemQuer = getItem(position).getQuemQuer();

        Grupo g = new Grupo(criador, nome, descricao, desporto, querEntrar, quemQuer);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvCriador = (TextView) convertView.findViewById(R.id.tvCriador);
        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvDescricao = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvDesporto = (TextView) convertView.findViewById(R.id.textView3);

        tvCriador.setText(criador);
        tvName.setText(nome);
        tvDescricao.setText(descricao);
        tvDesporto.setText(desporto.charAt(0) + desporto.substring(1, desporto.length()).toLowerCase());

        return convertView;
    }
}
