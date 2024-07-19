package com.example.pcos;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeeklyProgressActivity extends AppCompatActivity {

    private EditText editTextDateStart, editTextDateEnd;
    private Button viewButton;
    private RecyclerView recyclerView;
    private WeeklyProgressAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekly_progress);

        editTextDateStart = findViewById(R.id.editTextDate);
        editTextDateEnd = findViewById(R.id.editTextDate2);
        viewButton = findViewById(R.id.button16);
        recyclerView = findViewById(R.id.patient_weekly_report);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WeeklyProgressAdapter();
        recyclerView.setAdapter(adapter);

// Add space between items (adjust the value as needed)
        int spaceInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spaceInPixels));


        setupDatePicker(editTextDateStart);
        setupDatePicker(editTextDateEnd);

        String patientName = getIntent().getStringExtra("patient_name");

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startDate = editTextDateStart.getText().toString().trim();
                String endDate = editTextDateEnd.getText().toString().trim();
                String name = patientName;

                if (startDate.isEmpty() || endDate.isEmpty() || name.isEmpty()) {
                    Toast.makeText(WeeklyProgressActivity.this, "Please enter all fields.", Toast.LENGTH_SHORT).show();
                } else {
                    fetchData(name, startDate, endDate);
                }
            }
        });
    }

    private void setupDatePicker(EditText editText) {
        editText.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view1, year1, monthOfYear, dayOfMonth) -> {
                        String formattedMonth = (monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : String.valueOf(monthOfYear + 1);
                        String formattedDay = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                        editText.setText(year1 + "-" + formattedMonth + "-" + formattedDay);
                    }, year, month, day);
            datePickerDialog.show();
        });
    }

    private void fetchData(String name, String startDate, String endDate) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            String result;
            try {
                URL url = new URL(IpV4Connection.getUrl("week.php"));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setDoOutput(true);

                String postData = "name=" + name + "&from_date=" + startDate + "&to_date=" + endDate;

                OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
                wr.write(postData);
                wr.flush();
                wr.close();

                int HttpResult = connection.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    br.close();
                    result = sb.toString();
                } else {
                    result = connection.getResponseMessage();
                }
            } catch (Exception e) {
                result = e.toString();
            }

            final String finalResult = result;
            handler.post(() -> processResult(finalResult));
        });
    }

    private void processResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            if (response.getBoolean("success")) {
                JSONArray data = response.getJSONArray("data");
                List<WeeklyProgress> items = new ArrayList<>();
                for (int i = 0; i < data.length(); i++) {
                    JSONObject item = data.getJSONObject(i);
                    WeeklyProgress progress = new WeeklyProgress(
                            item.getString("date"),
                            item.getString("day")+" day",
                            item.getString("exercise_duration")+" mins",
                            item.getString("calories_taken")+" Kcal",
                            item.getString("todays_feedback")+" hrs",
                            item.getString("no_of_steps")+" Steps"
                    );
                    items.add(progress);
                }
                adapter.setData(items);
            } else {
                Toast.makeText(WeeklyProgressActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(WeeklyProgressActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
