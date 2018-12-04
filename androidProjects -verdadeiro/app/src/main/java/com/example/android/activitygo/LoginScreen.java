package com.example.android.activitygo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    private DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

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
                                startActivity(intent);
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
}