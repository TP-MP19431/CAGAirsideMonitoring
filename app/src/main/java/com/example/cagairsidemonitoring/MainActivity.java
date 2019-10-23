package com.example.cagairsidemonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText Staff;
    private EditText PIN;
    private TextView Info;
    private Button Enter;
    private int counter = 5;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Staff = (EditText) findViewById(R.id.etStaff);
        PIN = (EditText) findViewById(R.id.etPIN);
        Info = (TextView) findViewById(R.id.tvInfo);
        Enter = (Button) findViewById(R.id.btnEnter);

        firebaseAuth = FirebaseAuth.getInstance();
        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signInWithEmailAndPassword(Staff.getText().toString(), PIN.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(MainActivity.this, HomePage.class));
                        } else {
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }



     /*   Info.setText("Number of attempts remaining: 5");

        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Staff.getText().toString(), PIN.getText().toString());
            }
        });

    }

    private void validate(String userName, String userPIN) {
        if ((userName.equals("user")) && (userPIN.equals("0000"))) {
            Intent intent = new Intent(MainActivity.this, HomePage.class);
            startActivity(intent);
        } else {

                   counter--;
                   Info.setText("Number of attempts remaining: " + String.valueOf(counter));

                   if (counter == 0) {
                       Enter.setEnabled(false);
                   }


                }




        }*/
    }


    /test