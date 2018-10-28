package com.example.android.activitygo;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class TextViewJuntarFragment extends Fragment {

    private String text;
    private TextView tv1;
    private Button plus;
    private Button minus;

    public TextViewJuntarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_text_view_juntar, container, false);

        String[]  possiveisResultados = {"Benfica","Braga","Sporting","Porto","Legião FC"};
        text = getArguments().getString("Pesquisa");

        String resultados = getResultados(possiveisResultados,text);
        String [] resultadosArray = resultados.split(" ");

        LinearLayout linearLayout = (LinearLayout)v.findViewById(R.id.layoutResultados);


        if (resultados.equals("")){
            createTextView("Não Existem resultados", getContext(), linearLayout);
        }else{
            for (int i = 0 ;i < resultadosArray.length;i++)
                createTextView(resultadosArray[i], getContext(), linearLayout);

        }

        tv1 = (TextView) v.findViewById(R.id.ShowGroups);

        plus = (Button) v.findViewById(R.id.JoinGroup);
        minus = (Button) v.findViewById(R.id.LeaveGroup);
        minus.setVisibility(v.GONE);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plus.setVisibility(v.GONE);
                minus.setVisibility(v.VISIBLE);

            }
        }
        );

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minus.setVisibility(v.GONE);
                plus.setVisibility(v.VISIBLE);
            }
        });

        return v;
    }

    /*
    Obtem os resultados do array
     */
    public String getResultados(String[]possiveisResultados ,String pesquisa){
        String resultados="";
        for (String resultado:possiveisResultados){
            if (resultado.contains(pesquisa)){
                resultados+=resultado+" ";
            }
        }
     return resultados;
    }

    /*
     cria uma TextView
     */
    public TextView createTextView(String sText, Context con,LinearLayout linearLayout){
        TextView b  = new TextView (con);
            b.setTextSize(30);
            b.setWidth(200);
            b.setHeight(55);
            b.setTextSize(20);
            b.setGravity(Gravity.CENTER);
            b.setBackground(Drawable.createFromPath("@drawable/edit_text_shape"));
            b.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            b.setText(sText);
            linearLayout.addView(b);

        return b;
    }




    @Override
    public void onResume() {
        super.onResume();
        tv1.setText(text);

    }
}
