package com.example.pcos;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class DoctorHomepage extends AppCompatActivity implements D_HomeAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private D_HomeAdapter adapter;
    private ArrayList<Patient> patientList = new ArrayList<>();
    private ArrayList<Patient> filteredList = new ArrayList<>();
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_homepage);

        recyclerView = findViewById(R.id.recyclerview_patients);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new D_HomeAdapter(patientList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        Button btnAddPatient = findViewById(R.id.button5);
        btnAddPatient.setOnClickListener(view -> navigateToAddPatient());

        ImageView imgLogout = findViewById(R.id.imageButton9);
        imgLogout.setOnClickListener(view -> confirmLogout());

        fetchPatients();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.draw_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.action_search) {
            // Open search view
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    private void filter(String searchText) {
        filteredList.clear();
        for (Patient patient : patientList) {
            if (patient.getUsername().toLowerCase(Locale.getDefault()).contains(searchText.toLowerCase(Locale.getDefault()))) {
                filteredList.add(patient);
            }
        }
        adapter.filterList(filteredList);
    }

    private void navigateToAddPatient() {
        Intent intent = new Intent(this, AddPatients.class);
        startActivity(intent);
    }

    private void confirmLogout() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("OK", (dialog, which) -> logout())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void logout() {
        Intent intent = new Intent(this, RollSelection.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    private void fetchPatients() {
        String url = IpV4Connection.getUrl("patientlist.php");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("success")) {
                            JSONArray usernames = jsonObject.getJSONArray("usernames");
                            patientList.clear();
                            for (int i = 0; i < usernames.length(); i++) {
                                String username = usernames.getString(i);
                                patientList.add(new Patient(username));
                            }
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> error.printStackTrace());
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onItemClick(View view, int position) {
        Patient patient = patientList.get(position);
        Intent intent = new Intent(this, DoctorPatientView.class);
        intent.putExtra("patient_name", patient.getUsername());
        startActivity(intent);
    }
}
