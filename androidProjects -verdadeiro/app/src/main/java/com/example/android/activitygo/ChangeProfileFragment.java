package com.example.android.activitygo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeProfileFragment extends Fragment {

    private TextView firstNameUser;
    private TextView secondNameUser;
    private TextView username;
    private TextView emailUser;
    private TextView pesoUser;
    private TextView alturaUser;
    private TextView passwordUser;
    private TextView dataNascimento;
    private TextView paisUser;
    private TextView confirmaPasswordUser;

    private String firstName;
    private String secondName;
    private String usernameStr;
    private String email;
    private String peso;
    private String altura;
    private String password;
    private String confirmaPassword;
    private String dataNascimentoStr;
    private String paisUserStr;

    // perfil que o user coloca quando se regista
    private ArrayList<String> userProfileMenuPrincipal;

    // coisas novas que o user quer alterar
    private ArrayList<String> userProfileCpf = new ArrayList<>();

    // lista com as coisas alteradas e as inalteradas + genero + desportos favoritos
    private ArrayList<String> alteracoes = new ArrayList<>();

    private Fragment selectedFragment;

    public ChangeProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Alterar Informações do Perfil:");
        View v = inflater.inflate(R.layout.fragment_change_profile, container, false);

        MenuPrincipal mp = (MenuPrincipal) getActivity();
        userProfileMenuPrincipal = mp.getProfile();

        inicializarLayout(v);

        Button criar = (Button) v.findViewById(R.id.buttonConfirmarAlterarPerfil);
        criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstName = firstNameUser.getText().toString();
                secondName = secondNameUser.getText().toString();
                dataNascimentoStr = dataNascimento.getText().toString();
                paisUserStr = paisUser.getText().toString();
                email = emailUser.getText().toString();
                //TODO /*validar o facto de ser um número*/
                peso = pesoUser.getText().toString();
                //TODO /*validar o facto de ser um número e decidir se vai ser em cm ou m*/
                altura = alturaUser.getText().toString();

                usernameStr = username.getText().toString();
                password = passwordUser.getText().toString();
                confirmaPassword = confirmaPasswordUser.getText().toString();

                userProfileCpf.add(firstName);
                userProfileCpf.add(secondName);
                userProfileCpf.add(usernameStr);
                userProfileCpf.add(email);
                userProfileCpf.add(peso);
                userProfileCpf.add(altura);
                userProfileCpf.add(password);
                userProfileCpf.add(dataNascimentoStr);
                userProfileCpf.add(paisUserStr);

                alteracoes = detetarAlteracoesPerfis(userProfileMenuPrincipal, userProfileCpf);

                Toast toast = Toast.makeText(getActivity(), " Alterou os seus dados com sucesso!",
                        Toast.LENGTH_SHORT);
                toast.show();

                /*selectedFragment = new SettingsFragment();
                FragmentManager fmSF = getFragmentManager();
                FragmentTransaction ftSF = fmSF.beginTransaction();
                ftSF.add(R.id.fragment_container, selectedFragment, "SettingsFragment");
                //ft.addToBackStack("RunFragment");
                ftSF.commit();*/
            }
        });

        return v;
    }

    public ArrayList<String> detetarAlteracoesPerfis(ArrayList<String> listUser, ArrayList<String> listUserAlterada) {
        // index da listaUserAlterada
        ArrayList<String> alterada = new ArrayList<>();
        int index = 0;
        for (String str : listUser) {
            if(index < 9){
                if (!str.equals(listUserAlterada.get(index))) {
                    alterada.add(listUserAlterada.get(index));
                } else if(str.equals(listUserAlterada.get(index))){
                    alterada.add(str);
                }
                index++;
            } else {
                if(index == listUser.size()){
                    break;
                } else {
                    alterada.add(str);
                    index++;
                }
            }
        }

        return alterada;
    }

    private void inicializarLayout(View v) {
        // Preparar para guardar os valores
        firstNameUser = (TextView) v.findViewById(R.id.primeiroNomeText);
        secondNameUser = (TextView) v.findViewById(R.id.apelidoNameText);
        username = (TextView) v.findViewById(R.id.privateUsernameNameText);
        emailUser = (TextView) v.findViewById(R.id.emailNameText);
        pesoUser = (TextView) v.findViewById(R.id.weightNameText);
        alturaUser = (TextView) v.findViewById(R.id.heightNameText);
        passwordUser = (TextView) v.findViewById(R.id.privatePasswordNameText);
        confirmaPasswordUser = (TextView) v.findViewById(R.id.privateInfoPasswordNameText);
        dataNascimento = (TextView) v.findViewById(R.id.dataNascimentoDateText);
        paisUser = (TextView) v.findViewById(R.id.paisUserNameText);
    }

    public ArrayList<String> getListaAlteracoes(){
        return this.alteracoes;
    }
}