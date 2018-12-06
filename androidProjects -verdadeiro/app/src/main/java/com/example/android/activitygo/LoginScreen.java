package com.example.android.activitygo;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginScreen extends AppCompatActivity {


    private EditText user;
    private EditText password;
    private ArrayList<String> profile;
    private String pass;
    private String username;
    private Dialog dialogWrongPassword;
    private DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        dialogWrongPassword = new Dialog(this);
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        Button button = (Button) findViewById(R.id.buttonLogin);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                user = (EditText) findViewById(R.id.usernameText);
                username = user.getText().toString();
                password = (EditText) findViewById(R.id.passwordText);
                pass = password.getText().toString();

                databaseUsers.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            String pwd = String.valueOf(child.child("password").getValue());
                            if (pwd.equals(pass)) {
                                Intent intent = new Intent(getApplicationContext(), MenuPrincipal.class);
                                intent.putExtra("USERNAME", username);
                                startActivity(intent);
                            } else {
                                showWrongPasswordPopup();
                                password.getText().clear();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public void criarConta(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void showWrongPasswordPopup() {
        Button okButton;
        TextView close;
        TextView popupId;
        dialogWrongPassword.setContentView(R.layout.popup_password_errada);
        okButton = (Button) dialogWrongPassword.findViewById(R.id.okButton);
        close = (TextView) dialogWrongPassword.findViewById(R.id.txtClose);
        popupId = (TextView) dialogWrongPassword.findViewById(R.id.popUpId);
        popupId.setText("\nUsername ou Password errados.\nTenta de novo.");
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogWrongPassword.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogWrongPassword.dismiss();
            }
        });
        dialogWrongPassword.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogWrongPassword.show();
    }
}