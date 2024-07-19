package com.example.pcos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity33 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main33);

        // Set up each button with the day of the week it represents
        setupButtonWithDay(R.id.View2, "Monday");
        setupButtonWithDay(R.id.View5, "Tuesday");
        setupButtonWithDay(R.id.View4, "Wednesday");
        setupButtonWithDay(R.id.View3, "Thursday");
        setupButtonWithDay(R.id.View6, "Friday");
        setupButtonWithDay(R.id.button15, "Saturday");
    }

    private void setupButtonWithDay(int buttonId, String day) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity33.this, ExerciseList.class);
                intent.putExtra("day", day);
                startActivity(intent);
            }
        });
    }
}
