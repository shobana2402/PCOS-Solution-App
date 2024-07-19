package com.example.pcos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MeditationMusic extends AppCompatActivity {

    private TextView tvAudioName, tvDuration, tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.meditation_music);
        String category = getIntent().getStringExtra("CATEGORY");
        tvAudioName = findViewById(R.id.textView43);
        tvDuration = findViewById(R.id.textView44);
        tvDescription = findViewById(R.id.textView46);
        Button button6 = findViewById(R.id.button6);

        fetchMeditationAudio(category);

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeditationMusic.this, MusicPlayerActivity.class);
                intent.putExtra("AUDIO_URL", tvAudioName.getTag().toString()); // Using tag to store the URL
                startActivity(intent);
            }
        });
    }

    private void fetchMeditationAudio(String category) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IpV4Connection.getBaseUrl()) // Adjust with your server URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MeditationApiService service = retrofit.create(MeditationApiService.class);
        Call<MeditationResponse> call = service.fetchMeditationAudio(category);

        call.enqueue(new Callback<MeditationResponse>() {
            @Override
            public void onResponse(Call<MeditationResponse> call, Response<MeditationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MeditationResponse meditation = response.body();
                    tvAudioName.setText(meditation.audio_name);
                    tvDuration.setText(meditation.duration);
                    tvDescription.setText(meditation.audio_description);
                    tvAudioName.setTag(meditation.audio_path); // Store URL in tag for use when clicking play
                } else {
                    tvAudioName.setText("Error: Audio not found");
                    tvDuration.setText("");
                    tvDescription.setText("");
                }
            }

            @Override
            public void onFailure(Call<MeditationResponse> call, Throwable t) {
                tvAudioName.setText("Network error: " + t.getMessage());
                tvDuration.setText("");
                tvDescription.setText("");
            }
        });
    }
}
