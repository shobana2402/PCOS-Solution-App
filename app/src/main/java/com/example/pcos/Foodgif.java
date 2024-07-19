package com.example.pcos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import pl.droidsonroids.gif.GifImageView;

public class Foodgif extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mealgif);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");

        GifImageView gifImageView = findViewById(R.id.gifImageView);
        gifImageView.setImageResource(R.drawable.food); // Ensure you have a GIF in your drawable folder named main

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent nextIntent = new Intent(Foodgif.this, FoodMenu.class);
                nextIntent.putExtra("username", username);
                startActivity(nextIntent);
                finish();
            }
        }, 3000); // 3000 milliseconds = 3 seconds
    }
}
