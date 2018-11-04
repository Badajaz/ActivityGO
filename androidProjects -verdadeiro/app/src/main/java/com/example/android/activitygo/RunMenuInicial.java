package com.example.android.activitygo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * A simple {@link Fragment} subclass.
 */
public class RunMenuInicial extends Fragment {

    public RunMenuInicial() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_run_menu_inicial, container, false);

        ((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Activity GO:");

        final Button historial = (Button) v.findViewById(R.id.buttonHistorial);
        Button irCorrida = (Button) v.findViewById(R.id.buttonIrCorrida);
        Button meusGrupos = (Button) v.findViewById(R.id.buttonMeusGrupos);
        Button alterar = (Button) v.findViewById(R.id.alterarDesportoPraticado);

        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment SelectedFragment = new RunFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,  SelectedFragment,"RunMenuInicial");
                ft.addToBackStack("RunFragment");
                ft.commit();
            }
        });

        irCorrida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment SelectedFragment = new IrCorridaFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,  SelectedFragment,"RunMenuInicial");
                ft.addToBackStack("RunFragment");
                ft.commit();

            }
        });

        meusGrupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment SelectedFragmentMeusGrupos = new MyGroupsFragment();
                FragmentManager fmMeusGrupos = getFragmentManager();
                FragmentTransaction ftMeusGrupos = fmMeusGrupos.beginTransaction();
                ftMeusGrupos.replace(R.id.fragment_container,  SelectedFragmentMeusGrupos,"RunMenuInicial");
                ftMeusGrupos.addToBackStack("RunFragment");
                ftMeusGrupos.commit();

            }
        });

        alterar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //MenuPrincipal mp = new MenuPrincipal();
                //mp.showPopup();
                View popupView = getLayoutInflater().inflate(R.layout.popup_inicial, null);

                PopupWindow popupWindow = new PopupWindow(popupView,
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                // If the PopupWindow should be focusable
                popupWindow.setFocusable(true);

                // If you need the PopupWindow to dismiss when when touched outside
                popupWindow.setBackgroundDrawable(new ColorDrawable());

                int location[] = new int[2];

                // Get the View's(the one that was clicked in the Fragment) location
                v.getLocationOnScreen(location);

                // Using location, the PopupWindow will be displayed right under anchorView
                popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
                        location[0], location[1] + v.getHeight());
            }
        });
        return v;
    }
}