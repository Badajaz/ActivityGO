package com.example.android.activitygo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SobreNos extends AppCompatActivity {

    private TextView tvDescricao;
    private TextView descricao1;
    private TextView descricao2;
    private TextView descricao3;
    private TextView sobreNOS;
    private String usernameReceived;
    private String fn;
    private String ln;
    private DatabaseReference databaseUsers;
    private Toolbar toolbarCima;
    private String image_path = "";
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_nos);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            usernameReceived = getIntent().getStringExtra("USERNAME");
            image_path = getIntent().getStringExtra("URI");
            if (!TextUtils.isEmpty(image_path)) {
                fileUri = Uri.parse(image_path);
            }
        } else {
            usernameReceived = "";
        }

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        databaseUsers.orderByChild("username").equalTo(usernameReceived).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    fn = String.valueOf(child.child("firstName").getValue());
                    ln = String.valueOf(child.child("lastName").getValue());

                    toolbarCima = (Toolbar) findViewById(R.id.toolbarSobreNos);
                    setSupportActionBar(toolbarCima);
                    getSupportActionBar().setTitle("ActivityGO");

                    getSupportActionBar().setSubtitle("" + fn.charAt(0) + ln.charAt(0));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tvDescricao = (TextView) findViewById(R.id.tvDescricao);
        tvDescricao.setText("A aplicação Activity GO está a ser desenvolvida no âmbito da cadeira Computação Móvel do Mestrado de Engenharia Informática na Faculdade de Ciências da Universidade de Lisboa.");
        sobreNOS = (TextView) findViewById(R.id.tvThis);
        sobreNOS.setText("Sobre Nós: ");
        descricao1 = (TextView) findViewById(R.id.IMVDescricao1);
        descricao1.setText("Gonçalo Cardoso.\nEstudante da FCUL.");
        descricao3 = (TextView) findViewById(R.id.IMVDescricao3);
        descricao3.setText("Ricardo Calçado.\nEstudante da FCUL.");
        descricao2 = (TextView) findViewById(R.id.IMVDescricao2);
        descricao2.setText("Gonçalo Lobo.\nEstudante da FCUL");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.BackButton:
                goToSettings();
                break;
        }
        return true;
    }

    private void goToSettings() {
        Intent i = new Intent(this, SettingsActivity.class);
        i.putExtra("USERNAME", usernameReceived);
        i.putExtra("URI", image_path);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.aboutus_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        goToSettings();
    }
}
