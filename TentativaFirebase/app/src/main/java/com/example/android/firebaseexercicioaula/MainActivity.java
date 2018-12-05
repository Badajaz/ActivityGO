package com.example.android.firebaseexercicioaula;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText nome;
    private EditText pass;
    private TextView edit;
    ValueEventListener valueEventListener;

    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        databaseUsers = FirebaseDatabase.getInstance().getReference("users");


        nome = (EditText) findViewById(R.id.nome);
        pass = (EditText) findViewById(R.id.password);
        Button insert = (Button) findViewById(R.id.insert);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nome.getText().toString();
                String password = pass.getText().toString();

                if (name.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "campos vazios", Toast.LENGTH_LONG).show();
                } else {
                    String id = databaseUsers.push().getKey();
                    User u = new User(name, password);

                    databaseUsers.child(id).setValue(u);
                    Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();

                }


            }
        });


        Button getValue = (Button) findViewById(R.id.getValue);
        edit = (TextView) findViewById(R.id.txt);
        getValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                databaseUsers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String valores = "";
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            User u = userSnapshot.getValue(User.class);
                            valores += u.getName() + " ";

                        }

                        edit.setText(valores);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });


        Button update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Query q = FirebaseDatabase.getInstance().getReference("users").child("name").equalTo("carlos");
                final String name = nome.getText().toString();
                final String password = pass.getText().toString();
                databaseUsers.orderByChild("name").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            //Toast.makeText(getApplicationContext(),child.getKey(),Toast.LENGTH_LONG).show();
                            databaseUsers.child(child.getKey()).child("password").setValue(password);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




                /*

                String name = nome.getText().toString();
                String password = pass.getText().toString();
                final String key = databaseUsers.child("users").push().getKey();
                User u = new User(name, password);


                databaseUsers.orderByChild("name").equalTo(name).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot datas : dataSnapshot.getChildren()) {
                            String keys = datas.child("name").getValue().toString();

                            edit.setText(databaseUsers.child("name").equalTo("jon").toString());

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/


                /*try{
                    databaseUsers.child("-LSnjYbLljAzeui0XPRY").child("name").setValue(name);
                    databaseUsers.child("-LSnjYbLljAzeui0XPRY").child("password").setValue(password);
                }catch (Exception e){
                    e.printStackTrace();
                }*/

            }
        });


        Button delete = findViewById(R.id.Delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nome.getText().toString();
                databaseUsers.orderByChild("name").equalTo(name).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot datas : dataSnapshot.getChildren()) {
                            //  String keys = datas.child("name").getValue().toString();
                            databaseUsers.child("-LSneudLUqWtH4OnZ66l").removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });


    }
}
