package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText username;
    EditText password;
    Button authenticate;
    Button signUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_lo);

        mAuth = FirebaseAuth.getInstance();
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        authenticate = (Button) findViewById(R.id.authenticate);
        signUp = (Button) findViewById(R.id.signUp);




        authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = username.getText().toString();
                String pass = password.getText().toString();

                mAuth.signInWithEmailAndPassword(userID, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i1);
                        } else {
                            Toast.makeText(Login.this, "Username or Password is incorrect. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userID = username.getText().toString();
                final String pass = password.getText().toString();
                if(userID.equals("") || pass.equals("")) {
                    Toast.makeText(Login.this, "Invalid username and password combination.", Toast.LENGTH_LONG).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(userID, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i1);
                            } else {
                                Toast.makeText(Login.this, "We are unable to process this request.", Toast.LENGTH_LONG).show();

                            }


                        }
                    });
                }
            }

        });


    }

    @Override
    public void onBackPressed(){

    }
}
