package com.example.pcos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class PatientEditProfileActivity extends AppCompatActivity {

    private TextView nameTextView, ageTextView, mobileNoTextView, heightTextView, weightTextView, bmiTextView, otherDiseaseTextView, obstetricScoreTextView, hipTextView, waistTextView, hipWaistTextView;
    private ImageView patientImageView;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_edit_profile);

        // Initialize UI components
        initializeTextViews();

        // Retrieve the username from the intent
        String username = getIntent().getStringExtra("username"); // Use the correct key here
        if (username != null) {
            fetchPatientDetails(username);
        } else {
            Toast.makeText(this, "Patient name is missing!", Toast.LENGTH_SHORT).show();
            return;
        }

        Button submitButton = findViewById(R.id.button13);
        submitButton.setOnClickListener(this::submitDetails);

        patientImageView = findViewById(R.id.imageView7);
        patientImageView.setOnClickListener(v -> openGallery());
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            String username = getIntent().getStringExtra("username");
            if (username != null) {
                uploadImage(imageUri, username);
            } else {
                Toast.makeText(this, "Username is missing!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void uploadImage(Uri imageUri, String username) {
        String url = IpV4Connection.getUrl("profileimage.php");

        StringRequest imageUploadRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), jsonResponse.getString("message"), Toast.LENGTH_LONG).show();
                        if (jsonResponse.getBoolean("success")) {
                            // Optional: Update the image in the ImageView with the newly uploaded image
                            String imageUrl = jsonResponse.getString("image_url");
                            Glide.with(this)
                                    .load(imageUrl)
                                    .error(R.drawable.group35)
                                    .into(patientImageView);

                            // Navigate back to the PatientHomePageActivity with the username
                            Intent intent = new Intent(PatientEditProfileActivity.this, PatientHome.class);
                            intent.putExtra("username", username); // Pass the username to the next activity
                            startActivity(intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Error parsing JSON: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Error during image upload: " + error.getMessage(), Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", username);
                params.put("image", imageToBase64(imageUri)); // Convert image URI to Base64 string
                return params;
            }
        };

        Volley.newRequestQueue(this).add(imageUploadRequest);
    }

    private String imageToBase64(Uri imageUri) {
        InputStream inputStream;
        try {
            inputStream = getContentResolver().openInputStream(imageUri);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
        patientImageView = findViewById(R.id.imageView7);
    }

    private void fetchPatientDetails(String name) {
        try {
            String encodedName = URLEncoder.encode(name, "UTF-8");  // URL encode the name
            String url = IpV4Connection.getUrl("profile.php?name=") + encodedName;

            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response -> {
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

                                if (details.has("profile_image")) {
                                    String imageUrl = details.getString("profile_image");
                                    Glide.with(PatientEditProfileActivity.this)
                                            .load(imageUrl)
                                            .apply(RequestOptions.circleCropTransform()) // Apply circular transformation
                                            .placeholder(R.drawable.icon_profile) // Placeholder image while loading
                                            .error(R.drawable.icon_profile) // Error image if loading fails
                                            .into(patientImageView);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), jsonResponse.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Error parsing JSON: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    },
                    error -> Toast.makeText(getApplicationContext(), "Failed to fetch data: " + error.getMessage(), Toast.LENGTH_LONG).show());

            queue.add(stringRequest);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void submitDetails(View view) {
        Map<String, String> params = new HashMap<>();
        params.put("name", nameTextView.getText().toString());
        params.put("age", ageTextView.getText().toString());
        params.put("Mobile_No", mobileNoTextView.getText().toString());
        params.put("height", heightTextView.getText().toString());
        params.put("weight", weightTextView.getText().toString());
        params.put("bmi", bmiTextView.getText().toString());
        params.put("otherdisease", otherDiseaseTextView.getText().toString());
        // Add any other details you need to submit

        String url = IpV4Connection.getUrl("edit.php");

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), jsonResponse.getString("message"), Toast.LENGTH_LONG).show();
                        if (jsonResponse.getBoolean("success")) {
                            finish();  // Closes the current activity and returns to the previous one
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "JSON parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Error during data submission: " + error.getMessage(), Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        Volley.newRequestQueue(this).add(postRequest);
    }

}