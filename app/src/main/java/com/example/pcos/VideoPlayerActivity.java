package com.example.pcos;

import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class VideoPlayerActivity extends AppCompatActivity {

    private VideoView videoView;
    private TextView textViewError;
    private MediaController mediaController;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_video_player);

            videoView = findViewById(R.id.videoView);

            // Get video URL from intent
            String videoUrl = getIntent().getStringExtra("VIDEO_URL");
            if (videoUrl == null || videoUrl.isEmpty()) {
                Toast.makeText(this, "Video URL is missing", Toast.LENGTH_LONG).show();
                return;
            }

            // Set up MediaController
            mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            videoView.setVideoPath(videoUrl);
            videoView.start();

            findViewById(R.id.closeButton2).setOnClickListener(view -> finish());
        }
    }


