package com.example.pcos;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;

import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrackShowActivity extends AppCompatActivity {


    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_view);
        barChart = findViewById(R.id.bar_chart);
        setupBarChart();
        setupAllPieCharts();;  // Ensure this is called here
        String username = getIntent().getStringExtra("username");
        fetchChartData(username);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Retrieve patient name from the intent
        String patientName = getIntent().getStringExtra("patient_name");
        fetchChartData(patientName);
        fetchDataFromServerSteps(patientName);
        if (patientName == null) patientName = "Default Name"; // Use a default name if none is provided

        // Set up click listener for the leaderboard text view
        TextView textView71 = findViewById(R.id.textView71);
        textView71.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrackShowActivity.this, LeaderBoard2.class);
                startActivity(intent);
            }
        });

        // Set up click listener for the assessment button
        Button assessmentButton = findViewById(R.id.assessment_button);
        String finalPatientName = patientName;
        assessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrackShowActivity.this, WeeklyProgressActivity.class);
                // Pass the patient name to the next activity
                intent.putExtra("patient_name", finalPatientName);
                startActivity(intent);
            }

        });
                Button category = findViewById(R.id.patient_category_button);
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrackShowActivity.this, ProgressScrfeenActivity.class);
                // Pass the patient name to the next activity
                intent.putExtra("username", finalPatientName);
                intent.putExtra("hideButton", true);
                startActivity(intent);
            }

        });
    }
    private void fetchDataFromServerSteps(String name) {
        String url =  IpV4Connection.getUrl("stepsgraph.php");  // Adjust the URL to your actual server's URL

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    parseDataAndSetupBarChart(response);
                },
                error -> {
                    Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }


    private void parseDataAndSetupBarChart(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getBoolean("success")) {
                JSONArray days = jsonObject.getJSONArray("days");
                JSONArray stepsCounts = jsonObject.getJSONArray("steps_counts");

                List<BarEntry> entries = new ArrayList<>();
                for (int i = 0; i < days.length(); i++) {
                    // Assuming days start from 1 and are sequential
                    float day = Float.parseFloat(days.getString(i));
                    float steps = (float) stepsCounts.getDouble(i);
                    entries.add(new BarEntry(day, steps));
                }

                BarDataSet dataSet = new BarDataSet(entries, "Steps");
                dataSet.setColor(getResources().getColor(R.color.steps));
                BarData barData = new BarData(dataSet);
                barChart.setData(barData);
                barChart.invalidate(); // Refresh the chart
            } else {
                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error parsing data: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void setupBarChart() {
        List<BarEntry> entries = new ArrayList<>();
        // Sample data - replace with actual data
        entries.add(new BarEntry(1f, 2000f)); // Day 1, 2000 steps
        entries.add(new BarEntry(2f, 5000f)); // Day 2, 5000 steps
        entries.add(new BarEntry(3f, 3000f)); // Day 3, 3000 steps
        entries.add(new BarEntry(4f, 7000f)); // Day 4, 7000 steps
        entries.add(new BarEntry(5f, 8000f)); // Day 5, 8000 steps

        BarDataSet dataSet = new BarDataSet(entries, "Steps");
        dataSet.setColor(getResources().getColor(R.color.steps));
        BarData barData = new BarData(dataSet);

        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(1000);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"", "Day 1", "Day 2", "Day 3", "Day 4", "Day 5"}));

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(10000f); // Assuming 10,000 is the maximum step count

        barChart.getAxisRight().setEnabled(false); // Disable right Y-Axis

        barChart.invalidate(); // Refresh the chart
    }
    public void setupAllPieCharts() {
        PieChart pieChartMain = findViewById(R.id.pieChart);
        int[] colorsMain = {Color.parseColor("#ffc700"), Color.parseColor("#FFFFFF")}; // Gold and White
        setupPieChart(pieChartMain, colorsMain, 1200f, 2000f);

        PieChart pieChart1 = findViewById(R.id.pieChart1);
        int[] colors1 = {Color.parseColor("#0f87ff"), Color.parseColor("#FFFFFF")}; // Blue and White
        setupPieChart(pieChart1, colors1, 1500f, 2500f);

        PieChart pieChart3 = findViewById(R.id.pieChart3);
        int[] colors3 = {Color.parseColor("#846cf8"), Color.parseColor("#FFFFFF")}; // Purple and White
        setupPieChart(pieChart3, colors3, 800f, 1800f);
    }
    public void fetchChartData(String userName) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL( IpV4Connection.getUrl("percentage.php"));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setDoOutput(true);

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write("name=" + URLEncoder.encode(userName, "UTF-8"));
                    writer.flush();
                    writer.close();
                    os.close();

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        // Call function to parse JSON and update UI
                        updatePieCharts(new JSONObject(response.toString()));
                    } else {
                        Log.e("HTTP_ERROR", "Response Code: " + responseCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void updatePieCharts(JSONObject jsonResponse) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (jsonResponse.getBoolean("success")) {
                        JSONObject percentages = jsonResponse.getJSONObject("percentages");
                        int caloriesPercentage = percentages.getInt("calories_percentage");
                        int stepsPercentage = percentages.getInt("steps_percentage");
                        int feedbackPercentage = percentages.getInt("feedback_percentage");

                        // Assuming max values are still 2000 for calories, 2500 for steps, and 1800 for feedback
                        PieChart pieChartCalories = findViewById(R.id.pieChart);
                        PieChart pieChartSteps = findViewById(R.id.pieChart1);
                        PieChart pieChartFeedback = findViewById(R.id.pieChart3);

                        setupPieChart(pieChartCalories, new int[]{Color.parseColor("#ffc700"), Color.parseColor("#FFFFFF")}, caloriesPercentage, 100);
                        setupPieChart(pieChartSteps, new int[]{Color.parseColor("#0f87ff"), Color.parseColor("#FFFFFF")}, stepsPercentage, 100);
                        setupPieChart(pieChartFeedback, new int[]{Color.parseColor("#846cf8"), Color.parseColor("#FFFFFF")}, feedbackPercentage, 100);
                    } else {
                        Toast.makeText(getApplicationContext(), jsonResponse.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setupPieChart(PieChart pieChart, int[] colors, float categoryAmount, float totalAmount) {
        float remainder = totalAmount - categoryAmount;
        float percentageConsumed = (categoryAmount / totalAmount) * 100; // Calculate percentage consumed

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(categoryAmount, ""));
        entries.add(new PieEntry(remainder, ""));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(colors); // Set specific colors

        PieData data = new PieData(dataSet);
        data.setDrawValues(false); // Hide values on the slices

        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setHoleRadius(70f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawEntryLabels(false);

        pieChart.setCenterText(String.format("%.1f%%", percentageConsumed));
        pieChart.setCenterTextSize(14f);
        pieChart.setCenterTextColor(Color.BLACK);

        pieChart.getLegend().setEnabled(false);

        pieChart.animateY(1400, Easing.EaseInOutQuad);
        pieChart.invalidate();
    }
}
