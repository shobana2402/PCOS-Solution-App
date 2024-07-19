package com.example.pcos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class AddDoctorsActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText, emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_doctors);

        usernameEditText = findViewById(R.id.editTextText29);
        passwordEditText = findViewById(R.id.editTextTextEmailAddress);
        emailEditText = findViewById(R.id.editTextTextPassword);

        Button addButton = findViewById(R.id.button17);
        addButton.setOnClickListener(view -> addDoctor());
    }

    private void addDoctor() {
        // Get the text entered in EditText fields
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String role = "doctor"; // Assuming role is fixed for adding doctors

        // Check if any field is empty
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a JSON object with the data to be sent
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("password", password);
            jsonBody.put("email", email);
            jsonBody.put("role", role);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = IpV4Connection.getUrl("add_doctor.php");

        // Create a JsonObjectRequest to send the data to the server
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                response -> {
                    // Handle the response from the server
                    try {
                        boolean success = response.getBoolean("success");
                        if (success) {
                            Toast.makeText(AddDoctorsActivity.this, "Doctor added successfully", Toast.LENGTH_SHORT).show();
                            // Optionally, you can navigate back to a previous activity upon successful addition
                            Intent intent = new Intent(AddDoctorsActivity.this, RollSelection.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AddDoctorsActivity.this, "Failed to add doctor", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(AddDoctorsActivity.this, "JSON error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // Handle errors that occur during the request
                    error.printStackTrace();
                    Toast.makeText(AddDoctorsActivity.this, "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                });

        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest);
    }

}
