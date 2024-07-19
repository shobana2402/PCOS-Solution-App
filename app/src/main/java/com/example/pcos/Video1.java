package com.example.pcos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Video1 extends AppCompatActivity {
    private static final String EXTRA_VIDEO_URL = "VIDEO_URL";

    private TextView textViewName, textViewDuration, textViewProcess, textViewDescription;
    private String videoUrl; // Store video URL to be used in button click

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video1);

        String exerciseName = getIntent().getStringExtra("exerciseName");

        textViewName = findViewById(R.id.textView43);
        textViewDuration = findViewById(R.id.textView44);
        textViewProcess = findViewById(R.id.textView45);
        textViewDescription = findViewById(R.id.textView46);
        Button watchButton = findViewById(R.id.button6);

        // Fetch details immediately upon view creation
        fetchExerciseDetails(exerciseName);

        // Set up the button listener to only play the video when clicked
        watchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoUrl != null && !videoUrl.isEmpty()) {
                    Intent intent = new Intent(Video1.this, VideoPlayerActivity.class);
                    intent.putExtra(EXTRA_VIDEO_URL, videoUrl);
                    startActivity(intent);
                } else {
                    Toast.makeText(Video1.this, "Video URL is missing", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchExerciseDetails(String exerciseName) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = IpV4Connection.getUrl("exercise.php");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("API Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.has("error")) {
                                textViewName.setText(jsonObject.getString("exercise_name"));
                                textViewDuration.setText(jsonObject.getString("duration"));
                                textViewProcess.setText(jsonObject.getString("process"));
                                textViewDescription.setText(jsonObject.getString("video_description"));
                                videoUrl = jsonObject.getString("video_url");
                                Log.d("Video URL", videoUrl); // Log to see what URL is fetched
                            } else {
                                Toast.makeText(Video1.this, jsonObject.getString("error"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Video1.this, "Failed to parse data", Toast.LENGTH_SHORT).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Video1.this, "That didn't work!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("exercise_name", exerciseName);
                return params;
            }
        };

        queue.add(stringRequest);
    }
}
