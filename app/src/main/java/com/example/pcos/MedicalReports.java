package com.example.pcos;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MedicalReports extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageAdapter adapter;
    private String username;
    private List<MedicalRecord> records = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        username = getIntent().getStringExtra("username");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medical_reports);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ImageAdapter(this, records);
        recyclerView.setAdapter(adapter);

        fetchMedicalRecords(username);
    }

    private void fetchMedicalRecords(String name) {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("name", name)
                .build();
        Request request = new Request.Builder()
                .url(IpV4Connection.getUrl("view_reports.php"))
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(MedicalReports.this, "Error fetching records", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    runOnUiThread(() -> updateUI(jsonResponse));
                } else {
                    runOnUiThread(() -> Toast.makeText(MedicalReports.this, "Server error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void updateUI(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray jsonArray = jsonObject.getJSONArray("records");
            records.clear(); // Clear existing data
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject record = jsonArray.getJSONObject(i);
                records.add(new MedicalRecord(record.getString("record_path")));
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            Toast.makeText(this, "Error parsing data", Toast.LENGTH_SHORT).show();
        }
    }
}
