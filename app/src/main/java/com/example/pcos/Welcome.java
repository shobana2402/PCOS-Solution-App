package com.example.pcos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {

    private String username; // Declare a variable to store the username

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.welcome);

        // Retrieve the username passed from the previous activity
        Intent incomingIntent = getIntent();
        username = incomingIntent.getStringExtra("username"); // Ensure the key matches what was put in the previous activity

        ImageView imageView = findViewById(R.id.imageView3); // Replace imageView with your actual ImageView ID
        imageView.setOnClickListener(view -> {
            // Start the QuestionAndAnswers1 activity and pass the username
            Intent intent = new Intent(Welcome.this, QuestionAndAnswers1.class);
            intent.putExtra("username", username); // Pass the username to the next activity
            startActivity(intent);
        });
    }
}
