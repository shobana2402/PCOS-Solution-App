package com.example.pcos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MeditationHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.meditation_home);

        setupButton(R.id.imageView24, "Reduce stress");
        setupButton(R.id.imageView25, "Reduce Anxiety");
        setupButton(R.id.imageView26, "increase happiness");
        setupButton(R.id.imageView27, "Better sleep");
        setupButton(R.id.imageButton3, "personal growth");
        setupButton(R.id.imageButton4, "Better Concentration");
    }

    private void setupButton(int buttonId, String category) {
        ImageButton button = findViewById(buttonId);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MeditationHomeActivity.this, MeditationMusic.class);
            intent.putExtra("CATEGORY", category);
            startActivity(intent);
        });
    }
}
