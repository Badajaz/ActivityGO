package com.example.android.activitygo;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TextViewJuntarFragment extends Fragment {

    private String text;
    private TextView tv1;

    public TextViewJuntarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_text_view_juntar, container, false);
        text = getArguments().getString("Pesquisa");
        tv1 = (TextView) v.findViewById(R.id.ShowGroups);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        tv1.setText(text);

    }
}
