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
import java.util.HashMap;
import java.util.Map;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class DoctorLogin extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button buttonLogin;
    private TextView forgotPasswordText;

    private static final String TAG = "DoctorLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_login);

        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        forgotPasswordText = findViewById(R.id.forgotPasswordText);

        buttonLogin.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            if (!username.isEmpty() && !password.isEmpty()) {
                login(username, password);
            } else {
                Toast.makeText(DoctorLogin.this, "Username or password cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        forgotPasswordText.setOnClickListener(view -> {
            Intent intent = new Intent(DoctorLogin.this, ForgotPassword.class);
            startActivity(intent);
        });
    }

    private void login(String username, String password) {
        String url = IpV4Connection.getUrl("doctorlogin.php");
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Log.d(TAG, "Response: " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            Intent intent = new Intent(DoctorLogin.this, DoctorHomepage.class);
                            startActivity(intent);
                            finish();  // Call finish to remove this activity from the stack
                            Toast.makeText(DoctorLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            String message = jsonObject.getString("message");
                            Toast.makeText(DoctorLogin.this, message, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(DoctorLogin.this, "JSON error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Log.e(TAG, "Login error: " + error.toString());
                    Toast.makeText(DoctorLogin.this, "Volley error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("doctor_id", username);
                params.put("password", password);
                return params;
            }
        };

        queue.add(stringRequest);
    }
}
