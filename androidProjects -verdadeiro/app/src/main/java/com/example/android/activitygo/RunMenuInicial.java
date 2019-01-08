package com.example.android.activitygo;

import android.Manifest;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.activitygo.model.Grupo;
import com.example.android.activitygo.model.PedidoGrupo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RunMenuInicial extends Fragment {

    private int num_pedidos;
    private boolean pedidos;
    private String username;
    private String userQueQuerEntrar;
    private String nomeDoGrupo;
    private DataSnapshot childdd;
    private TextView name1;
    private static final int STORAGE_PERMISSION_CODE = 1;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int REQUEST_CODE = 1;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference databasePedidosGrupo;
    private DatabaseReference databaseGrupo;
    private NotificationManagerCompat notificationManager;
    public static final String CHANNEL_1_ID = "channel1";

    private Button photoActivityButton;
    private Button meusGrupos;
    private ImageView mImageView;
    private DatabaseReference databaseUsers;
    private Dialog dialogPermissaoGrupo;
    private Drawable drawableDefault = null;

    private String image_path = "";
    private Uri fileUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_run_menu_inicial, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            username = bundle.getString("USERNAME");
            image_path = bundle.getString("URI");
            if (!TextUtils.isEmpty(image_path)) { // image_path recebido do upload
                fileUri = Uri.parse(image_path);
            }
        } else {
            username = "";
            image_path = "";
        }

        dialogPermissaoGrupo = new Dialog(getActivity());
        photoActivityButton = v.findViewById(R.id.uploadActivity);
        mImageView = v.findViewById(R.id.imageView2);
        if (fileUri != null) {
            mImageView.setImageURI(fileUri);
        } else {
            mImageView.setImageResource(R.drawable.ola_logo);
            mImageView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    final Animation anim_out = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
                    final Animation anim_in = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
                    anim_out.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            mImageView.setImageResource(R.drawable.user_photo_icon);
                            anim_in.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                }
                            });
                            mImageView.startAnimation(anim_in);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    mImageView.startAnimation(anim_out);

                }
            }, TimeUnit.MINUTES.toSeconds(20));
        }
        meusGrupos = (Button) v.findViewById(R.id.buttonMeusGrupos);
        final Drawable drawableNotifyOne = ContextCompat.getDrawable(getContext(), R.drawable.notify_one);
        drawableDefault = ContextCompat.getDrawable(getContext(), R.drawable.icon_groups);

        databaseGrupo = FirebaseDatabase.getInstance().getReference("grupos");

        // Buscar pedidos para entrar nos grupos do username corrente (criador)
        databasePedidosGrupo = FirebaseDatabase.getInstance().getReference("pedidosGrupo");
        databasePedidosGrupo.orderByChild("criador").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    pedidos = true;
                    childdd = child;
                    PedidoGrupo pg = child.getValue(PedidoGrupo.class);
                    userQueQuerEntrar = pg.getUseQueQuerEntrar();
                    nomeDoGrupo = pg.getNomeGrupo();
                    if ((int) dataSnapshot.getChildrenCount() == 1) {
                        num_pedidos = 1;
                        meusGrupos.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableNotifyOne, null);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final Button historial = (Button) v.findViewById(R.id.buttonHistorial);
        Button irCorrida = (Button) v.findViewById(R.id.buttonIrCorrida);

        name1 = (TextView) v.findViewById(R.id.namePessoaMenuInicial);
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        databaseUsers.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String fn = String.valueOf(child.child("firstName").getValue());
                    String ln = String.valueOf(child.child("lastName").getValue());
                    name1.setText("Bem vindo " + fn + " " + ln + "!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("USERNAME", username);
                Fragment SelectedFragment = new RunFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SelectedFragment.setArguments(args);
                ft.replace(R.id.fragment_container, SelectedFragment, "historialCorrida");
                ft.addToBackStack("RunFragment");
                ft.commit();
            }
        });

        irCorrida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestThemStoragePermissions();
                requestThemLocationPermissions();
                Fragment SelectedFragment = new IrCorridaFragment();
                Bundle toRunMenuInicial = new Bundle();
                toRunMenuInicial.putString("USERNAME", username);
                toRunMenuInicial.putString("URI", image_path);
                SelectedFragment.setArguments(toRunMenuInicial);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, SelectedFragment, "IrCorridaFragment");
                ft.addToBackStack("IrCorridaFragment");
                ft.commit();
            }
        });

        meusGrupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pedidos) { // se tiver pedidos (1, 2 ou mais)
                    showPopUpPermissaoGrupos(userQueQuerEntrar, nomeDoGrupo, childdd, num_pedidos);
                }

                Bundle args = new Bundle();
                args.putString("USERNAME", username);
                Fragment SelectedFragmentMeusGrupos = new MergeGroupFragment();
                SelectedFragmentMeusGrupos.setArguments(args);
                FragmentManager fmMeusGrupos = getFragmentManager();
                FragmentTransaction ftMeusGrupos = fmMeusGrupos.beginTransaction();
                ftMeusGrupos.replace(R.id.fragment_container, SelectedFragmentMeusGrupos, "MergeGroupFragment");
                ftMeusGrupos.addToBackStack("MergeGroupFragment");
                ftMeusGrupos.commit();

            }
        });

        photoActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UploadPhotoActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        return v;
    }

    private void showPopUpPermissaoGrupos(final String pessoaQueQuerEntrar, final String nomeDoGrupo, final DataSnapshot childPedidosGrupo, final int numero_pedidos) {
        Button yesButton;
        Button noButton;
        TextView close;
        TextView popupId;
        dialogPermissaoGrupo.setContentView(R.layout.popup_permissao_grupos);
        yesButton = (Button) dialogPermissaoGrupo.findViewById(R.id.yesButton);
        noButton = (Button) dialogPermissaoGrupo.findViewById(R.id.noButton);
        close = (TextView) dialogPermissaoGrupo.findViewById(R.id.txtClose);
        popupId = (TextView) dialogPermissaoGrupo.findViewById(R.id.popUpId);
        popupId.setText("O " + pessoaQueQuerEntrar + " quer entrar no seu grupo " + nomeDoGrupo + ".\n Quer aceitar ou rejeitar o pedido?");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPermissaoGrupo.dismiss();
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseGrupo.orderByChild("nome").equalTo(nomeDoGrupo).addListenerForSingleValueEvent(new ValueEventListener() {
                    ArrayList<String> arrayGrupoNovo = new ArrayList<>();

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Grupo g = child.getValue(Grupo.class);
                            arrayGrupoNovo.addAll(g.getElementosGrupo());
                            arrayGrupoNovo.add(pessoaQueQuerEntrar);
                            databaseGrupo.child(child.getKey()).child("elementosGrupo").setValue(arrayGrupoNovo);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                databasePedidosGrupo.child(childPedidosGrupo.getKey()).removeValue();
                if (numero_pedidos == 1) {
                    meusGrupos.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableDefault, null);
                }
                dialogPermissaoGrupo.dismiss();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // faz reset dos valores e não mete no grupo
                databasePedidosGrupo.child(childPedidosGrupo.getKey()).removeValue();
                if (numero_pedidos == 1) {
                    meusGrupos.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableDefault, null);
                }
                dialogPermissaoGrupo.dismiss();
            }
        });
        dialogPermissaoGrupo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogPermissaoGrupo.show();
    }

    private void requestThemLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    private void requestThemStoragePermissions() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        } else {
            requestStoragePermission();
        }
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}