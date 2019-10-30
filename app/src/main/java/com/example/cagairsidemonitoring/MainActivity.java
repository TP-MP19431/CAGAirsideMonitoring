package com.example.cagairsidemonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static com.squareup.okhttp.internal.http.HttpDate.format;

public class MainActivity extends AppCompatActivity {


    private TextView dateDisplay, timeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date, time;

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


        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView dateDisplay = findViewById(R.id.tvDate);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy' at 'HH:mm:ss z ");
                                String currentDate = sdf.format(date);
                                dateDisplay.setText(currentDate);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();

            /*Calendar calendar = Calendar.getInstance();

            String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
            TextView dateDisplay = findViewById(R.id.tvDate);
            dateDisplay.setText(currentDate);

            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            TextView timeDisplay = findViewById(R.id.tvTime);
            timeDisplay.setText(currentTime);
            */

        Staff = (EditText) findViewById(R.id.etStaff);
        PIN = (EditText) findViewById(R.id.etPIN);
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


