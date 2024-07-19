package com.example.pcos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import pl.droidsonroids.gif.GifImageView;

public class Track extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.track);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");

        GifImageView gifImageView = findViewById(R.id.gifImageView);
        gifImageView.setImageResource(R.drawable.track); // Ensure you have a GIF in your drawable folder named track

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent nextIntent = new Intent(Track.this, TrackView.class);
                nextIntent.putExtra("username", username);
                startActivity(nextIntent);
                finish();
            }
        }, 3000); // 3000 milliseconds = 3 seconds
    }
}
