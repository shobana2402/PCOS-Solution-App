package com.example.pcos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PatientLogin extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_login);


        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewForgotPassword = findViewById(R.id.forgotPasswordText);

        buttonLogin.setOnClickListener(view -> {
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                login(username, password);
            }
        });

        textViewForgotPassword.setOnClickListener(view -> {
            startActivity(new Intent(PatientLogin.this, ForgotPassword.class));

        });
    }

    private void login(String username, String password) {
        String url = IpV4Connection.getUrl("login.php");
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                this::handleResponse,
                error -> Toast.makeText(getApplicationContext(), "Login failed: " + error.toString(), Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        queue.add(postRequest);
    }

    private void handleResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean("success");
            String message = jsonResponse.getString("message");

            if (success) {
                Intent intent;
                String username = editTextUsername.getText().toString().trim(); // Retrieve username from EditText
                if (message.equals("Login Successful, user details are complete.")) {
                    intent = new Intent(PatientLogin.this, PatientHome.class);
                    intent.putExtra("username", username);  // Pass username as an extra
                    startActivity(intent);
                    finish(); // Finish LoginActivity to remove it from the back stack
                } else if (message.equals("Login Successful, please complete your personal details.")) {
                    intent = new Intent(PatientLogin.this, EnterDetails.class);
                    intent.putExtra("username", username);  // Pass username as an extra
                    startActivity(intent);
                    finish(); // Finish LoginActivity to remove it from the back stack
                } else {
                    Toast.makeText(getApplicationContext(), "Login unsuccessful: " + message, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Login failed: " + message, Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error parsing response: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


}
