package com.example.pcos;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayerActivity extends AppCompatActivity {
    private ImageButton btnPlayPause;
    private MediaPlayer mediaPlayer;
    private SeekBar musicSeekBar;
    private Timer timer;
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player);

        btnPlayPause = findViewById(R.id.btnPlayPause);
        musicSeekBar = findViewById(R.id.musicSeekBar);

        String audioUrl = getIntent().getStringExtra("AUDIO_URL");
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnPreparedListener(mp -> {
            btnPlayPause.setImageResource(R.drawable.play_button);
            setupSeekBar();
        });

        mediaPlayer.setOnErrorListener((mp, what, extra) -> {
            // Log or handle the error here
            return true;
        });

        btnPlayPause.setOnClickListener(v -> togglePlayback());

        musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void setupSeekBar() {
        musicSeekBar.setMax(mediaPlayer.getDuration());
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> musicSeekBar.setProgress(mediaPlayer.getCurrentPosition()));
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private void togglePlayback() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            btnPlayPause.setImageResource(R.drawable.play_button);
        } else {
            mediaPlayer.start();
            btnPlayPause.setImageResource(R.drawable.baseline_pause_circle_filled_24);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer.isPlaying()) {
            togglePlayback();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
