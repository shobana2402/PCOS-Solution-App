package com.example.pcos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SuccessfulActivity extends AppCompatActivity {
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.successful2);

        // Retrieve the intent that started this activity and extract the username
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        // Apply window insets as padding for the main view
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Delay for 3 seconds then navigate to the next activity
        new Handler().postDelayed(() -> {
            Intent nextIntent = new Intent(SuccessfulActivity.this, ProgressBar.class);
            nextIntent.putExtra("username", username);
            startActivity(nextIntent);
            finish(); // Finish this activity to prevent returning here with the back button
        }, 3000); // 3000 milliseconds = 3 seconds
    }
}
