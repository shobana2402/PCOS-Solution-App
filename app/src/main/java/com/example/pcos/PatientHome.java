package com.example.pcos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.util.Calendar;

public class PatientHome extends AppCompatActivity {
    private String username;
    private TextView title;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private ImageButton imageButton; // Reference to the navigation drawer button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_home);

        // Retrieve username from intent using the correct key
        username = getIntent().getStringExtra("username");
        String username = getIntent().getStringExtra("username");

        drawerLayout = findViewById(R.id.main);
        navigationView = findViewById(R.id.navigation_view);
        imageButton = findViewById(R.id.imageButton);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.edit_profile) {
                    Intent editProfileIntent = new Intent(PatientHome.this, PatientEditProfileActivity.class);
                    editProfileIntent.putExtra("username", username);
                    startActivity(editProfileIntent);
                } else if (id == R.id.about_pcos) {
                    startActivity(new Intent(PatientHome.this, MainActivity36.class));
                } else if (id == R.id.Reports) {
                    Intent Reprts = new Intent(PatientHome.this, MedicalReports2.class);
                    Reprts.putExtra("username", username);
                    startActivity(Reprts);
                } else if (id == R.id.nav_logout) {
                    showLogoutConfirmation();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });



        imageButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        title = findViewById(R.id.textView62);
        title.setText(username != null && !username.isEmpty() ? "Hi " + username + ", Start your Journey!" : "Hi, Start your Journey!");

        setupButtons();
    }


    private void setupButtons() {
       findViewById(R.id.viewCenter).setOnClickListener(v -> {
           Intent intent = new Intent(this, Track.class);
            intent.putExtra("username", username);
            startActivity(intent);

        });
        findViewById(R.id.imageViewMiddle).setOnClickListener(v -> {
            Intent intent = new Intent(this, Calender.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });
        findViewById(R.id.view15).setOnClickListener(v -> {
            Intent intent = new Intent(this, Foodgif.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });
        findViewById(R.id.View6).setOnClickListener(v -> {
            // Creating an AlertDialog.Builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Exercise Caution");
            builder.setMessage("Avoid exercise that causes pain or discomfort.");

            // Adding OK button
            builder.setPositiveButton("OK", (dialog, which) -> {
                // User clicked OK button, navigate to next screen
                Intent intent = new Intent(this, MainActivity33.class);
                intent.putExtra("username", username);
                startActivity(intent);
            });

            // Adding Cancel button
            builder.setNegativeButton("Cancel", (dialog, which) -> {
                // User cancelled the dialog
                dialog.dismiss();
            });

            // Creating and showing the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        findViewById(R.id.imageButton2).setOnClickListener(v -> {
            Intent intent = new Intent(this, PatientProfile.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });
        findViewById(R.id.secondView).setOnClickListener(v -> fetchCaloriesAndProceed());
        findViewById(R.id.thirdView).setOnClickListener(v -> {
            Intent intent = new Intent(this, MeditationHomeActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });
    }
    private void fetchCaloriesAndProceed() {
        String url = IpV4Connection.getUrl("totalcalorie.php?name=") + username;

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        if (jsonResponse.getBoolean("success")) {
                            int totalCalories = jsonResponse.getInt("total_calorie");
                            Intent intent = new Intent(PatientHome.this, TodayProgressPatients.class);
                            intent.putExtra("username", username);
                            intent.putExtra("total_calories", totalCalories);
                            startActivity(intent);
                        } else {
                            Toast.makeText(PatientHome.this, "No calorie data found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(PatientHome.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(PatientHome.this, "Request error: " + error.getMessage(), Toast.LENGTH_SHORT).show());

        queue.add(stringRequest);
    }

    private void showLogoutConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Logout Confirmation")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked OK button, navigate to RollSelection page
                        Intent intent = new Intent(PatientHome.this, RollSelection.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}

