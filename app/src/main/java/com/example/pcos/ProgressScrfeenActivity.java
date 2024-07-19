package com.example.pcos;

import android.graphics.Color;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProgressScrfeenActivity extends AppCompatActivity {

    private String username; // Variable to store the username

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.progress_bar);

        Intent incomingIntent = getIntent();
        username = incomingIntent.getStringExtra("username");

        // Initialize PieChart
        PieChart pieChart = findViewById(R.id.chart_donut);
        fetchDataAndSetupChart(pieChart);

        Button button = findViewById(R.id.button12);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("hideButton") && intent.getBooleanExtra("hideButton", false)) {
            button.setVisibility(View.GONE);
        }
    }

    private void fetchDataAndSetupChart(final PieChart pieChart) {
        String url = IpV4Connection.getUrl("category.php"); // Change to your actual URL

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse.getBoolean("success")) {
                                JSONObject data = jsonResponse.getJSONObject("data");
                                float mild = (float) data.getDouble("mild");
                                float moderate = (float) data.getDouble("moderate");
                                float severe = (float) data.getDouble("severe");

                                setupDonutChart(pieChart, mild, moderate, severe);
                            } else {
                                // Handle error
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", username);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void setupDonutChart(PieChart pieChart, float mild, float moderate, float severe) {
        // Convert scores to percentages of total score (total score = 15)
        float total = 15;
        float mildPercentage = (mild / total) * 100;
        float moderatePercentage = (moderate / total) * 100;
        float severePercentage = (severe / total) * 100;

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        pieChart.animateY(0); // Stop animations

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(mildPercentage));
        entries.add(new PieEntry(moderatePercentage));
        entries.add(new PieEntry(severePercentage));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setDrawValues(false); // Disable values on the slices

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#6a01f4"));  // Purple
        colors.add(Color.parseColor("#02f194"));  // Green
        colors.add(Color.parseColor("#ff7455"));  // Orange

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(false); // Disable values on the chart

        pieChart.setData(data);

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setEnabled(true);

        pieChart.invalidate(); // Refresh the chart
    }


}
