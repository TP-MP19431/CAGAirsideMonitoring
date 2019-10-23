package com.example.cagairsidemonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    private EditText userName, userPIN, userEmail;
    private Button regButton;
    private TextView userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (validate()){
                   //upload data to database
               }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity (new Intent(Registration.this, MainActivity.class));
            }
        });
    }

    private void setupUIViews (){
        userName = (EditText)findViewById(R.id.etName); //associate variable with ID in XML file
        userPIN = (EditText)findViewById(R.id.etPIN);
        userEmail = (EditText)findViewById(R.id.etEmail);
        regButton = (Button)findViewById(R.id.btnRegister);
        userLogin = (TextView)findViewById(R.id.tvUserLogin);
    }

    private Boolean validate(){
        Boolean result = false;

        String name = userName.getText().toString();
        String PIN = userPIN.getText().toString();
        String email = userEmail.getText().toString();

        if (name.isEmpty()&& PIN.isEmpty() && email.isEmpty()){
            Toast.makeText(this, "Please complete the form with all required details.", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }


}
