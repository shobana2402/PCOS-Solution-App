package com.example.pcos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

public class ForgotPassword extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, newPasswordEditText, confirmPasswordEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        // Initialize UI components
        usernameEditText = findViewById(R.id.editTextText);
        emailEditText = findViewById(R.id.editTextText2);
        newPasswordEditText = findViewById(R.id.editTextText3);
        confirmPasswordEditText = findViewById(R.id.editTextText4);
        submitButton = findViewById(R.id.button3);

        // Set up the submit button click listener
        submitButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String newPassword = newPasswordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            // Check if passwords match
            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(ForgotPassword.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Send request to reset password
            sendResetRequest(username, email, newPassword);
        });
    }

    private void sendResetRequest(String username, String email, String newPassword) {
        String url = IpV4Connection.getUrl("forgotpassword.php");
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(ForgotPassword.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        if (jsonObject.getString("status").equals("success")) {
                            // Close this activity and return to the previous one
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(ForgotPassword.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("newpassword", newPassword);
                // Note: do not send confirmPassword to the server, as it's already checked on client-side
                return params;
            }
        };
        queue.add(request);
    }

}
