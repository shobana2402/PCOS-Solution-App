package com.example.pcos;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONObject;

public class PatientProfile extends AppCompatActivity {

    private TextView nameTextView, ageTextView, mobileNoTextView, heightTextView, weightTextView, bmiTextView, otherDiseaseTextView, obstetricScoreTextView, hipTextView, waistTextView, hipWaistTextView;
    private ImageView patientImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_profile);
        String username = getIntent().getStringExtra("username");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeTextViews();
        fetchPatientDetails(username);
    }

    private void initializeTextViews() {
        nameTextView = findViewById(R.id.editTextText13);
        ageTextView = findViewById(R.id.editTextText14);
        mobileNoTextView = findViewById(R.id.editTextText15);
        heightTextView = findViewById(R.id.editTextText16);
        weightTextView = findViewById(R.id.editTextText17);
        bmiTextView = findViewById(R.id.editTextText18);
        otherDiseaseTextView = findViewById(R.id.editTextText19);
        obstetricScoreTextView = findViewById(R.id.editTextText20);
        hipTextView = findViewById(R.id.editTextText26);
        waistTextView = findViewById(R.id.editTextText27);
        hipWaistTextView = findViewById(R.id.editTextText28);
        patientImageView = findViewById(R.id.imageView7); // Initialize ImageView
    }

    private void fetchPatientDetails(String name) {
        String url = IpV4Connection.getUrl("profile.php?name=") + name;

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse.getBoolean("success")) {
                                JSONObject details = jsonResponse.getJSONObject("patient_details");
                                nameTextView.setText(details.getString("name"));
                                ageTextView.setText(String.valueOf(details.getInt("age")));
                                mobileNoTextView.setText(details.getString("Mobile_No"));
                                heightTextView.setText(details.getString("height"));
                                weightTextView.setText(details.getString("weight"));
                                bmiTextView.setText(details.getString("bmi"));
                                otherDiseaseTextView.setText(details.getString("otherdisease"));
                                obstetricScoreTextView.setText(details.getString("obstetricscore"));
                                hipTextView.setText(details.getString("hip"));
                                waistTextView.setText(details.getString("waist"));
                                hipWaistTextView.setText(details.getString("hipwaist"));

                                // Load image using Glide
                                if (details.has("profile_image")) {
                                    String imageUrl = details.getString("profile_image");
                                    Glide.with(PatientProfile.this)
                                            .load(imageUrl)
                                            .apply(RequestOptions.circleCropTransform()) // Apply circular transformation
                                            .placeholder(R.drawable.icon_profile) // Placeholder image while loading
                                            .error(R.drawable.icon_profile) // Error image if loading fails
                                            .into(patientImageView);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), jsonResponse.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error parsing response", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Failed to fetch data: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(stringRequest);
    }
}

