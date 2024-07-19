package com.example.pcos;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class TodayProgressDoctor extends AppCompatActivity {

    private EditText dateEditText, caloriesEditText, exerciseEditText, feedbackEditText, stepsEditText, nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.today_progress_d);

        dateEditText = findViewById(R.id.editTextText21);
        caloriesEditText = findViewById(R.id.editTextText22);
        exerciseEditText = findViewById(R.id.editTextText24);
        feedbackEditText = findViewById(R.id.editTextText25);
        stepsEditText = findViewById(R.id.editTextText23);
        nameEditText = findViewById(R.id.editTextText30);

        // Disable Editing
        dateEditText.setFocusable(false);
        caloriesEditText.setFocusable(false);
        exerciseEditText.setFocusable(false);
        feedbackEditText.setFocusable(false);
        stepsEditText.setFocusable(false);
        nameEditText.setFocusable(false);

        // Retrieve the patient name from intent
        String patientName = getIntent().getStringExtra("patient_name");
        if (patientName != null && !patientName.isEmpty()) {
            fetchTodayProgress(patientName);
        } else {
            Toast.makeText(this, "No patient name provided!", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchTodayProgress(String name) {
        String url = IpV4Connection.getUrl("todayprogress-D.php?name=") + name;
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("success")) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            dateEditText.setText(data.getString("Date"));
                            caloriesEditText.setText(String.valueOf(data.getInt("calories_taken")));
                            exerciseEditText.setText(String.valueOf(data.getInt("exercise_duration")));
                            feedbackEditText.setText(data.getString("todays_feedback"));
                            stepsEditText.setText(String.valueOf(data.getInt("no_of_steps")));
                            nameEditText.setText(name);  // Set the patient's name to the EditText
                        } else {
                            Toast.makeText(TodayProgressDoctor.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(TodayProgressDoctor.this, "Error parsing JSON data", Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(TodayProgressDoctor.this, "Failed to connect to the server", Toast.LENGTH_LONG).show());

        queue.add(stringRequest);
    }
}
