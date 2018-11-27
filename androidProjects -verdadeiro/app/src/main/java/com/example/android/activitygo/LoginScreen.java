package com.example.android.activitygo;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.activitygo.model.AppDatabase;
import com.example.android.activitygo.model.User;
import com.example.android.activitygo.model.UserDao;

import java.util.ArrayList;

public class LoginScreen extends AppCompatActivity {

    private EditText user;
    private EditText password;
    private ArrayList<String> profile;
    private String pass;
    private UserDao mUserDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);


        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, "db-contacts")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build()
                .getUserDAO();



        user = (EditText) findViewById(R.id.usernameText);
        final String username = getIntent().getStringExtra("USERNAME");
        pass = getIntent().getStringExtra("PASSWORD");
        user.setText(username);

        Button button = (Button) findViewById(R.id.buttonLogin);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SignUp s = new SignUp();
                int alreadyRRR = getIntent().getIntExtra("ALREADYREGISTER", 0);
                if (alreadyRRR == 1) {
                    //profile = (ArrayList<String>) getIntent().getSerializableExtra("USERPROFILE");
                }

                password = (EditText) findViewById(R.id.passwordText);
                String userTxt = user.getText().toString();
                String passwordTxt = password.getText().toString();


                try {
                    User u = mUserDAO.getUser(userTxt);
                    if (u == null){
                        Toast.makeText(LoginScreen.this, "nao existe o utilizador", Toast.LENGTH_SHORT).show();
                    }else {


                        if (u.getUsername().equals(userTxt) && u.getPassword().equals(passwordTxt)) {
                            Intent intent = new Intent(getApplicationContext(), MenuPrincipal.class);
                            intent.putExtra("INICIAL",""+u.getFirstName().charAt(0));
                            intent.putExtra("FINAL",""+u.getLastName().charAt(0));
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginScreen.this, "nao e igual", Toast.LENGTH_SHORT).show();
                        }
                    }


                } catch (SQLiteConstraintException e) {
                    Toast.makeText(LoginScreen.this, "erro", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    public void criarConta(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}