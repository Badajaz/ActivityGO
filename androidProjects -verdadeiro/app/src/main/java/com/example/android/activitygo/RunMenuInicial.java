package com.example.android.activitygo;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class RunMenuInicial extends Fragment {

    private String username;
    private TextView name1;
    private static final int STORAGE_PERMISSION_CODE = 1;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int REQUEST_CODE = 1;
    private DatabaseReference mDatabaseRef;

    private Button photoActivityButton;
    private ImageView mImageView;
    private DatabaseReference databaseUsers;

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

        final Button historial = (Button) v.findViewById(R.id.buttonHistorial);
        Button irCorrida = (Button) v.findViewById(R.id.buttonIrCorrida);
        Button meusGrupos = (Button) v.findViewById(R.id.buttonMeusGrupos);
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