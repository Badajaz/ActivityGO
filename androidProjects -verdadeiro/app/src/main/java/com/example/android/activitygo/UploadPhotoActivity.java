package com.example.android.activitygo;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.activitygo.model.Upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadPhotoActivity extends AppCompatActivity {

    private Button mButtonChooseImage;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonUploadImage;
    private Button mButtontakePhoto;
    private Uri mImageUri;
    private ImageView mImageView;
    private static int RESULT_IMAGE_CLICK = 1;
    private static final int CAMERA_REQUEST = 1888;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Toolbar toolbarCima;
    private String username;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageReference fileReference;
    private DatabaseReference databaseUsers;

    private StorageTask mUploadTask;
    private FirebaseAuth mAuth;
    private Date date;

    private static final int PERMISSION_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("USERNAME");
        } else {
            username = "";
        }

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
        } else {
            signInAnonymously();
        }

        mButtonChooseImage = findViewById(R.id.loadimage);
        mButtonUploadImage = findViewById(R.id.uploadImage);
        mButtontakePhoto = findViewById(R.id.takePicture);
        mImageView = findViewById(R.id.imageView2Here);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {

                } else {
                    uploadFile3();


                }
            }
        });

        mButtontakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
                uploadFile3();
            }
        });

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        databaseUsers.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String fn = String.valueOf(child.child("firstName").getValue());
                    String ln = String.valueOf(child.child("lastName").getValue());
                    toolbarCima = (Toolbar) findViewById(R.id.toolbar);
                    setSupportActionBar(toolbarCima);
                    getSupportActionBar().setTitle("ActivityGO");
                    getSupportActionBar().setSubtitle("" + fn.charAt(0) + ln.charAt(0));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void takePhoto() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(mImageView);
        }

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mImageUri = data.getData();
            mImageView.setImageBitmap(photo);
        }
    }

    private void signInAnonymously() {
        mAuth.signInAnonymously().addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("MainActivity", "signFailed****** ", exception);
            }
        });
    }

    private void uploadFile3() {
        if (mImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(username + "." + System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                }
                            }, 500);

                            final String ur = mImageUri.toString();
                            //Toast.makeText(UploadPhotoActivity.this, "Foto carregada com sucesso!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(UploadPhotoActivity.this, "ur! " + ur, Toast.LENGTH_SHORT).show();

                            Thread thread = new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(Toast.LENGTH_SHORT); // As I am using LENGTH_LONG in Toast
                                        Intent intent = new Intent(getApplicationContext(), MenuPrincipal.class);
                                        intent.putExtra("USERNAME", username);
                                        intent.putExtra("URI", ur);
                                        startActivity(intent);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            };

                            thread.start();

                            Upload upload = new Upload(username, username.trim(),
                                    fileReference.getDownloadUrl().toString());
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadPhotoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CAMERA) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {

            }
        }
    }
}