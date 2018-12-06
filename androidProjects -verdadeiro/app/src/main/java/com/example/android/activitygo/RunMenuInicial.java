package com.example.android.activitygo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class RunMenuInicial extends Fragment {

    private String username;
    private static final int PICK_IMAGE_REQUEST = 71;
    private TextView name1;
    private Button selectImage;
    private Button uploadImage;
    private ImageView imv;

    //Images
    private FirebaseStorage storage;
    StorageReference storageReference;
    private Uri filepath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_run_menu_inicial, container, false);

        //ArrayList<String> arraylist = getArguments().getStringArrayList("USERPROFILE");
        username = getArguments().getString("USERNAME");
        imv = (ImageView) v.findViewById(R.id.imageView2);

        //Firebase images
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("fotos");

        //((MenuPrincipal) getActivity()).getSupportActionBar().setTitle("Activity GO:");

        final Button historial = (Button) v.findViewById(R.id.buttonHistorial);
        Button irCorrida = (Button) v.findViewById(R.id.buttonIrCorrida);
        Button meusGrupos = (Button) v.findViewById(R.id.buttonMeusGrupos);
        //Button alterar = (Button) v.findViewById(R.id.alterarDesportoPraticado);
        name1 = (TextView) v.findViewById(R.id.namePessoaMenuInicial);
        // name1.setText("Bem vindo " + arraylist.get(0) + " " + arraylist.get(1) + "!");

        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment SelectedFragment = new RunFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, SelectedFragment, "RunMenuInicial");
                ft.addToBackStack("RunFragment");
                ft.commit();
            }
        });

        irCorrida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment SelectedFragment = new IrCorridaFragment();
                Bundle toRunMenuInicial = new Bundle();
                toRunMenuInicial.putString("USERNAME", username);
                SelectedFragment.setArguments(toRunMenuInicial);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, SelectedFragment, "RunMenuInicial");
                ft.addToBackStack("RunFragment");
                ft.commit();

            }
        });

        meusGrupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment SelectedFragmentMeusGrupos = new MergeGroupFragment();
                FragmentManager fmMeusGrupos = getFragmentManager();
                FragmentTransaction ftMeusGrupos = fmMeusGrupos.beginTransaction();
                ftMeusGrupos.replace(R.id.fragment_container, SelectedFragmentMeusGrupos, "RunMenuInicial");
                ftMeusGrupos.addToBackStack("RunFragment");
                ftMeusGrupos.commit();

            }
        });

        /*alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */

        selectImage = (Button) v.findViewById(R.id.loadimage);
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        uploadImage = (Button) v.findViewById(R.id.uploadImage);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        return v;
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == -1 && data != null && data.getData() != null) {
            filepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver()
                        , filepath);
                //
            } catch (IOException o) {
                o.printStackTrace();
            }
        }
    }


    private void uploadImage() {

        imv.setImage
        /*if (filepath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child(UUID.randomUUID().toString());
            ref.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded" + (int) progress + "%");
                }
            });
        }*/


    }
}