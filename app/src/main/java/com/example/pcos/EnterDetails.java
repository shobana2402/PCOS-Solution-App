package com.example.pcos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.text.TextWatcher;
import android.text.Editable;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EnterDetails extends AppCompatActivity {

    private EditText nameEditText, ageEditText, mobileNoEditText, heightEditText, weightEditText, bmiEditText, anyotherText, hipEditText, waistEditText, hipwaistEditText;
    private Spinner obstetricSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_details);

        initializeEditTexts();
        setupSpinner();
        setupTextWatchers();

        // Retrieve the username passed from the PatientLogin activity
        Intent intent = getIntent();
        if (intent.hasExtra("username")) {
            String username = intent.getStringExtra("username");
            nameEditText.setText(username);
        }

        Button submitButton = findViewById(R.id.button11);
        submitButton.setOnClickListener(view -> sendDetailsToServer());
    }

    private void initializeEditTexts() {
        nameEditText = findViewById(R.id.editTextText5);
        ageEditText = findViewById(R.id.editTextText6);
        mobileNoEditText = findViewById(R.id.editTextText7);
        heightEditText = findViewById(R.id.editTextText8);
        weightEditText = findViewById(R.id.editTextText9);
        bmiEditText = findViewById(R.id.editTextText10);
        anyotherText = findViewById(R.id.editTextText11);
        obstetricSpinner = findViewById(R.id.editTextText12);
        hipEditText = findViewById(R.id.editTextText13);
        waistEditText = findViewById(R.id.editTextText14);
        hipwaistEditText = findViewById(R.id.editTextText15);
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                new String[]{"No Child", "Single Child", "Two Child", "More than 2 Child", "Unmarried"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        obstetricSpinner.setAdapter(adapter);
    }

    private void setupTextWatchers() {
        TextWatcher watcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateAndDisplayBMIAndHipWaistRatio();
            }
            public void afterTextChanged(Editable s) {}
        };
        heightEditText.addTextChangedListener(watcher);
        weightEditText.addTextChangedListener(watcher);
        hipEditText.addTextChangedListener(watcher);
        waistEditText.addTextChangedListener(watcher);
    }

    private void calculateAndDisplayBMIAndHipWaistRatio() {
        try {
            double height = Double.parseDouble(heightEditText.getText().toString()) / 100;
            double weight = Double.parseDouble(weightEditText.getText().toString());
            double hipValue = Double.parseDouble(hipEditText.getText().toString());
            double waistValue = Double.parseDouble(waistEditText.getText().toString());

            if (height > 0 && weight > 0) {
                double bmi = weight / (height * height);
                bmiEditText.setText(String.format("%.2f", bmi));
            }
            if (hipValue > 0 && waistValue > 0) {
                double hipWaistRatio = waistValue / hipValue;
                hipwaistEditText.setText(String.format("%.2f", hipWaistRatio));
            }
        } catch (NumberFormatException e) {
            bmiEditText.setText("");
            hipwaistEditText.setText("");
        }
    }

    private boolean validateFields() {
        return !nameEditText.getText().toString().trim().isEmpty() &&
                !ageEditText.getText().toString().trim().isEmpty() &&
                !mobileNoEditText.getText().toString().trim().isEmpty() &&
                !heightEditText.getText().toString().trim().isEmpty() &&
                !weightEditText.getText().toString().trim().isEmpty() &&
                !bmiEditText.getText().toString().trim().isEmpty() &&
                !anyotherText.getText().toString().trim().isEmpty() &&
                !hipEditText.getText().toString().trim().isEmpty() &&
                !waistEditText.getText().toString().trim().isEmpty() &&
                !hipwaistEditText.getText().toString().trim().isEmpty();
    }

    private void sendDetailsToServer() {
        if (!validateFields()) {
            Toast.makeText(getApplicationContext(), "Please fill in all fields.", Toast.LENGTH_LONG).show();
            return;
        }

        String url = IpV4Connection.getUrl("personaldetails.php");
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(getApplicationContext(), "Data submitted successfully!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EnterDetails.this, Welcome.class);

                    // Get the username from the EditText or from the intent extras
                    String username = nameEditText.getText().toString();
                    intent.putExtra("username", username);
                    startActivity(intent);

                },
                error -> Toast.makeText(getApplicationContext(), "Failed to submit data: " + error.toString(), Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", nameEditText.getText().toString());
                params.put("age", ageEditText.getText().toString());
                params.put("Mobile_No", mobileNoEditText.getText().toString());
                params.put("height", heightEditText.getText().toString());
                params.put("weight", weightEditText.getText().toString());
                params.put("bmi", bmiEditText.getText().toString());
                params.put("otherdisease", anyotherText.getText().toString());
                params.put("obstetricscore", obstetricSpinner.getSelectedItem().toString());
                params.put("hip", hipEditText.getText().toString());
                params.put("waist", waistEditText.getText().toString());
                params.put("hipwaist", hipwaistEditText.getText().toString());
                return params;
            }
        };
        queue.add(postRequest);
    }
}
