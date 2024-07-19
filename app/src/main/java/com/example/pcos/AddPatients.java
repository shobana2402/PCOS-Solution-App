package com.example.pcos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class AddPatients extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.add_patients);

        Button doneButton = findViewById(R.id.button3);
        EditText usernameField = findViewById(R.id.editTextText);
        EditText emailField = findViewById(R.id.editTextText2);
        EditText passwordField = findViewById(R.id.editTextText3);
        EditText confirmPasswordField = findViewById(R.id.editTextText4);

        doneButton.setOnClickListener(view -> {
            String username = usernameField.getText().toString();
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();
            String confirmPassword = confirmPasswordField.getText().toString();

            if (validateInput(username, email, password, confirmPassword)) {
                registerUser(username, email, password, confirmPassword);
            } else {
                // Display an error message or do something similar
                Toast.makeText(this, "Please check your input fields.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validateInput(String username, String email, String password, String confirmPassword) {
        // Add more validation logic as needed
        return !username.isEmpty() && !email.isEmpty() && password.equals(confirmPassword) && !password.isEmpty();
    }

    private void registerUser(String username, String email, String password, String confirmPassword) {
        String url = IpV4Connection.getUrl("signup.php"); // Change to your actual URL
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        if (jsonResponse.getBoolean("success")) {
                            Toast.makeText(this, "Patient Added Successfully", Toast.LENGTH_LONG).show();
                            // Navigate back to Doctor Homepage
                            Intent intent = new Intent(AddPatients.this, DoctorHomepage.class);
                            startActivity(intent);
                            finish(); // Ensures this activity is removed from the stack
                        } else {
                            Toast.makeText(this, jsonResponse.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error processing JSON", Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Log.d("Error.Response", String.valueOf(error));
                    Toast.makeText(this, "Failed to connect to the server", Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                params.put("confirm_password", confirmPassword);
                return params;
            }
        };
        queue.add(postRequest);
    }
}
