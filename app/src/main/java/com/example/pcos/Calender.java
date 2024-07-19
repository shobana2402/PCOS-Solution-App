package com.example.pcos;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Calender extends AppCompatActivity {
    private MaterialCalendarView calendarView;
    private SelectionDecorator selectionDecorator;
    private List<String> selectedDates = new ArrayList<>();
    private String username;
    private OkHttpClient client;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender);

        username = getIntent().getStringExtra("username");
        calendarView = findViewById(R.id.calendarView);
        selectionDecorator = new SelectionDecorator(Color.WHITE);
        calendarView.addDecorator(selectionDecorator);

        client = new OkHttpClient();
        gson = new Gson();

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                selectionDecorator.toggleDate(date);
                widget.invalidateDecorators();
                handleDateSelection(date, selected);
            }
        });
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
                runOnUiThread(() -> Toast.makeText(Calender.this, "Error fetching data", Toast.LENGTH_LONG).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonResponse = response.body().string();
                Log.d("API Response", jsonResponse);  // Log the raw JSON response

                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        String name = jsonObject.getString("name");
                        JSONArray datesArray = jsonObject.getJSONArray("dates");
                        List<String> dates = new ArrayList<>();
                        for (int i = 0; i < datesArray.length(); i++) {
                            dates.add(datesArray.getString(i));
                        }
                        runOnUiThread(() -> updateCalendar(dates));
                    } catch (JSONException e) {
                        Log.e("JSON Parsing Error", "Error parsing JSON", e);
                        runOnUiThread(() -> Toast.makeText(Calender.this, "Parsing error in received data", Toast.LENGTH_LONG).show());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(Calender.this, "Error: " + response.message(), Toast.LENGTH_LONG).show());
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

    private void handleDateSelection(CalendarDay date, boolean selected) {
        String selectedDate = String.format("%d-%02d-%02d", date.getYear(), date.getMonth() + 1, date.getDay());
        if (selected) {
            if (!selectedDates.contains(selectedDate)) {
                selectedDates.add(selectedDate);
            } else {
                selectedDates.remove(selectedDate);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (!selectedDates.isEmpty()) {
            sendDatesToServer();
        } else {
            super.onBackPressed();
        }
    }

    private void sendDatesToServer() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sending data to server...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        FormBody.Builder formBuilder = new FormBody.Builder();

        if (username != null) {
            formBuilder.add("name", username);
        } else {
            Toast.makeText(Calender.this, "Username is not provided.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            finish();
            return;
        }

        for (String date : selectedDates) {
            formBuilder.add("dates[]", date);
        }

        RequestBody body = formBuilder.build();
        Request request = new Request.Builder()
                .url(IpV4Connection.getUrl("calender.php"))
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                progressDialog.dismiss();
                runOnUiThread(() -> Toast.makeText(Calender.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                finish();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    runOnUiThread(() -> {
                        try {
                            Toast.makeText(Calender.this, "Response: " + response.body().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Toast.makeText(Calender.this, "Error reading response: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(Calender.this, "Failed to send dates", Toast.LENGTH_SHORT).show());
                }
                finish();
            }
        });
    }

    private static class SelectionDecorator implements DayViewDecorator {
        private final HashSet<CalendarDay> dates = new HashSet<>();
        private final int colorWhite;

        public SelectionDecorator(int color) {
            this.colorWhite = color;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(colorWhite)); // Change the text color instead of background
        }

        public void toggleDate(CalendarDay date) {
            if (!dates.remove(date)) {
                dates.add(date);
            }
        }
    }
}
