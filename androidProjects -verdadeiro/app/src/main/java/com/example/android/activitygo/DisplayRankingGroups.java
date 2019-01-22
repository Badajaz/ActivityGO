package com.example.android.activitygo;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.activitygo.model.RankingGroups;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayRankingGroups extends Fragment {


    private DatabaseReference databaseRankingsGrupos;

    public DisplayRankingGroups() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_display_ranking_groups, container, false);

        databaseRankingsGrupos = FirebaseDatabase.getInstance().getReference("rankingGrupos");
        databaseRankingsGrupos.addListenerForSingleValueEvent(new ValueEventListener() {
            RankingGroups r;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    r = child.getValue(RankingGroups.class);
                }

                //r.getGruposRanking().entrySet().stream()
                  //      .sorted(Map.Entry.<String, Integer>comparingByValue().reversed());

                ArrayList<String> nomes = new ArrayList<>();
                ArrayList<Integer> valores = new ArrayList<>();
                for ( String nome:r.getGruposRanking().keySet()){
                    nomes.add(nome);
                    valores.add(r.getGruposRanking().get(nome));
                }


                Collections.sort(valores);
                ArrayList<String> ListaNomesOrdenada = new ArrayList<>();

                for (int pontuacao : valores){
                    for (String nome :r.getGruposRanking().keySet()){
                        if (pontuacao == r.getGruposRanking().get(nome) && ListaNomesOrdenada.indexOf(nome)==-1){
                            ListaNomesOrdenada.add(nome);
                        }
                    }
                }
                Collections.reverse(ListaNomesOrdenada);
                //


                int index = 1;
                for ( String grupo :ListaNomesOrdenada) {

                    TableLayout t = getView().findViewById(R.id.TableRankingGroups);
                    TableRow tr = new TableRow(getContext());
                    TextView tv1 = new TextView(getContext());
                    TextView tv2 = new TextView(getContext());
                    TextView tv3 = new TextView(getContext());
                    tv1.setGravity(Gravity.CENTER);
                    tv2.setGravity(Gravity.CENTER);
                    tv3.setGravity(Gravity.CENTER);
                    tv1.setText(Integer.toString(index)+"º");
                    tv2.setText(grupo);
                    tv3.setText("" + r.getGruposRanking().get(grupo));
                    tr.addView(tv1);
                    tr.addView(tv2);
                    tr.addView(tv3);
                    t.addView(tr);
                    int lime = getResources().getColor(R.color.orange);
                    int blue = getResources().getColor(R.color.BlueSeparator);
                    if (index%2 != 0){

                        int white = getResources().getColor(R.color.whiteLetters);
                        tv1.setBackgroundColor(lime);
                        tv2.setBackgroundColor(lime);
                        tv3.setBackgroundColor(lime);
                        tv1.setTextColor(white);
                        tv2.setTextColor(white);
                        tv3.setTextColor(white);

                    }else{
                        tv1.setTextColor(blue);
                        tv2.setTextColor(blue);
                        tv3.setTextColor(blue);
                    }
                    tv1.setTextSize(20);
                    tv2.setTextSize(20);
                    tv3.setTextSize(20);




                    index++;
                }


                //TextView tv =getActivity().findViewById(R.id.TESTE);
                //tv.setText(""+r.getGruposRanking().get("Teste"));
                //Toast.makeText(getContext(),""+r.getGruposRanking().get("Teste"),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });









        return v;
    }

}
