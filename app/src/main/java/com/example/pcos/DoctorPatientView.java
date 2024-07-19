package com.example.pcos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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
import org.json.JSONException;
import org.json.JSONObject;

public class DoctorPatientView extends AppCompatActivity {

    private String patientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_patient_view);
        patientName = getIntent().getStringExtra("patient_name");

        ImageView imgDelete = findViewById(R.id.imageButton8);
        imgDelete.setOnClickListener(view -> deletePatient(patientName));

        // Enable edge-to-edge
        EdgeToEdge.enable(this);

        // Set up window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Setup listeners for all views that need to pass the patientName
        setupViewListeners(patientName);
    }

    private void deletePatient(String patientName) {
        new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete this patient?")
                .setPositiveButton("OK", (dialog, which) -> executeDeleteRequest(patientName))
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void executeDeleteRequest(String patientName) {
        String url = IpV4Connection.getUrl("delete.php?username=")+ patientName;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        if (jsonResponse.getString("status").equals("success")) {
                            Toast.makeText(DoctorPatientView.this, "Patient deleted successfully.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, DoctorHomepage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(DoctorPatientView.this, "Error: " + jsonResponse.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(DoctorPatientView.this, "Failed to communicate with server.", Toast.LENGTH_LONG).show();
            error.printStackTrace();
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void setupViewListeners(String patientName) {
        View View1 = findViewById(R.id.View1);
        View1.setOnClickListener(view -> {
            Intent intent = new Intent(DoctorPatientView.this, MedicalReports.class);
            intent.putExtra("patient_name", patientName);
            intent.putExtra("username", patientName);
            startActivity(intent);
        });

        View View2 = findViewById(R.id.View2);
        View2.setOnClickListener(view -> {
            Intent intent = new Intent(DoctorPatientView.this, PatientEditProfile.class);
            intent.putExtra("patient_name", patientName); // `patientName` should be the name of the patient
            startActivity(intent);

        });

        View View3 = findViewById(R.id.View3);
        View3.setOnClickListener(view -> {
            Intent intent = new Intent(DoctorPatientView.this, TrackShowActivity.class);
            intent.putExtra("patient_name", patientName);
            startActivity(intent);
        });

        View View4 = findViewById(R.id.View4);
        View4.setOnClickListener(view -> {
            Intent intent = new Intent(DoctorPatientView.this, PatientMenstrualCalenderActivity.class);
            intent.putExtra("patient_name", patientName);
            startActivity(intent);
        });

        View View5 = findViewById(R.id.View5);
        View5.setOnClickListener(view -> {
            Intent intent = new Intent(DoctorPatientView.this, TodayProgressDoctor.class);
            intent.putExtra("patient_name", patientName);
            startActivity(intent);
        });
    }

}
