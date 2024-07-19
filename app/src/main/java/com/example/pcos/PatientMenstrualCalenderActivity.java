package com.example.pcos;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import okhttp3.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PatientMenstrualCalenderActivity extends AppCompatActivity {
    private String username;
    private OkHttpClient client;
    private Gson gson;
    private MaterialCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_menstrual_calender);
        username = getIntent().getStringExtra("patient_name");

        calendarView = findViewById(R.id.calendarView);

        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        gson = new Gson();

        fetchDates();
    }

    private void fetchDates() {
        RequestBody body = new FormBody.Builder()
                .add("name", username)
                .build();
        Request request = new Request.Builder()
                .url(IpV4Connection.getUrl("calenderD.php")) // Replace with your actual URL
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("HTTP", "Error in fetching calendar dates", e);
                runOnUiThread(() -> Toast.makeText(PatientMenstrualCalenderActivity.this, "Error fetching data", Toast.LENGTH_LONG).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonResponse = response.body().string();
                Log.d("API Response", jsonResponse);  // Log the raw JSON response

                if (response.isSuccessful()) {
                    try {
                        Type type = new TypeToken<DataModel>(){}.getType();
                        DataModel data = gson.fromJson(jsonResponse, type);
                        runOnUiThread(() -> updateCalendar(data.getDates()));
                    } catch (JsonSyntaxException e) {
                        Log.e("JSON Parsing Error", "Error parsing JSON", e);
                        runOnUiThread(() -> Toast.makeText(PatientMenstrualCalenderActivity.this, "Parsing error in received data", Toast.LENGTH_LONG).show());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(PatientMenstrualCalenderActivity.this, "Error: " + response.message(), Toast.LENGTH_LONG).show());
                }
            }

        });
    }

    private void updateCalendar(List<String> dates) {
        List<CalendarDay> calendarDays = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (String date : dates) {
            try {
                // Parse the date from the JSON string
                LocalDate localDate = LocalDate.parse(date, formatter);
                // Create a CalendarDay object from the parsed LocalDate
                CalendarDay calendarDay = CalendarDay.from(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
                // Add the CalendarDay to the list
                calendarDays.add(calendarDay);
            } catch (Exception e) {
                Log.e("Date Parse Error", "Failed to parse date: " + date, e);
            }
        }

        // Highlight the dates on the calendar
        int highlightColor = getResources().getColor(R.color.highlight_color); // Get the highlight color
        calendarView.addDecorator(new EventDecorator(highlightColor, calendarDays));
    }

    // Define a model class to represent the JSON response
    private static class DataModel {
        private String name;
        private List<String> dates;

        public String getName() {
            return name;
        }

        public List<String> getDates() {
            return dates;
        }
    }
}
