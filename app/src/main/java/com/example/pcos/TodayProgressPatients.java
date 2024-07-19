package com.example.pcos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class TodayProgressPatients extends AppCompatActivity {

    EditText editTextName, editTextCalories, editTextSteps, editTextDuration, editTextFeedback, editTextDate;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_progress_p);

        editTextName = findViewById(R.id.editTextText30);
        editTextCalories = findViewById(R.id.editTextText22);
        editTextSteps = findViewById(R.id.editTextText23);
        editTextDuration = findViewById(R.id.editTextText24);
        editTextFeedback = findViewById(R.id.editTextText25);
        editTextDate = findViewById(R.id.editTextText21);
        buttonSubmit = findViewById(R.id.button19);
        // In the onCreate method of TodayProgressPatients
        Intent intent = getIntent();
        int totalCalories = intent.getIntExtra("total_calories", 0); // Default to 0 if not found
        editTextCalories.setText(String.valueOf(totalCalories));


        String username = getIntent().getStringExtra("username");
        editTextName.setText(username);

        buttonSubmit.setOnClickListener(view -> submitData());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom);
            return insets;
        });
    }

    private void onCalorieResponse(String response) {
        try {
            Log.d("API Response", response);

            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean("success");
            if (success) {
                if (jsonResponse.has("total_calorie")) {
                    int calories = jsonResponse.getInt("total_calorie");
                    editTextCalories.setText(String.valueOf(calories));
                    Log.d("Calorie Data", "Calories fetched: " + calories);
                } else {
                    Log.d("API Error", "No total_calorie key found in the JSON response");
                    Toast.makeText(getApplicationContext(), "No calorie data found!", Toast.LENGTH_LONG).show();
                }
            } else {
                String message = jsonResponse.getString("message");
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                Log.d("API Error", "Error message: " + message);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error parsing JSON response: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("API Exception", "Error parsing JSON response: " + e.getMessage());
        }
    }


    private void submitData() {
        String url = IpV4Connection.getUrl("todayprogress.php");

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                this::handleResponse, this::handleError) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", editTextName.getText().toString());
                params.put("calories_taken", editTextCalories.getText().toString());
                params.put("no_of_steps", editTextSteps.getText().toString());
                params.put("exercise_duration", editTextDuration.getText().toString());
                params.put("todays_feedback", editTextFeedback.getText().toString());
                params.put("date", editTextDate.getText().toString());
                return params;
            }
        };
        queue.add(postRequest);
    }

    private void handleResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean("success");
            Toast.makeText(getApplicationContext(), jsonResponse.getString("message"), Toast.LENGTH_LONG).show();
            if (success) {
                Intent intent = new Intent(TodayProgressPatients.this, PatientHome.class);
                intent.putExtra("username", editTextName.getText().toString());
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error parsing JSON response: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void handleError(VolleyError error) {
        String message = "Error: ";
        if (error instanceof NetworkError) {
            message += "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ServerError) {
            message += "The server could not be found. Please try again later!";
        } else if (error instanceof AuthFailureError) {
            message += "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ParseError) {
            message += "Parsing error! Please try again!";
        } else if (error instanceof NoConnectionError) {
            message += "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof TimeoutError) {
            message += "Connection TimeOut! Please check your internet connection.";
        } else {
            message += error.getMessage();
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
