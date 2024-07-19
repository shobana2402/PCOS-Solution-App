package com.example.pcos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class RollSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.roll_selection);
        Button button = findViewById(R.id.button1);
        button.setOnClickListener(view -> {
            // When button3 is clicked, start doctor_login activity
            Intent intent = new Intent(RollSelection.this, DoctorLogin.class);
            startActivity(intent);
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(view -> {
            // When button3 is clicked, start doctor_login activity
            Intent intent = new Intent(RollSelection.this, PatientLogin.class);
            startActivity(intent);
        });

        TextView adminTextView = findViewById(R.id.adminTextView);
        adminTextView.setOnClickListener(view -> {
            // When button3 is clicked, start doctor_login activity
            Intent intent = new Intent(RollSelection.this, AdminPageActivity.class);
            startActivity(intent);
        });


        };
    }
