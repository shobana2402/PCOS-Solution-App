package com.example.pcos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FoodListActivity extends AppCompatActivity {
    ListView listView;
    FoodAdapter adapter;
    Button nextButton;
    View lastSelectedView = null;
    String username;
    String type;
    int totalCalories = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutitionlist);

        listView = findViewById(R.id.listView);
        nextButton = findViewById(R.id.buttonNext);
        ArrayList<String> selectedItems = new ArrayList<>();


        Intent intent = getIntent();
        if (intent != null) {
            username = intent.getStringExtra("username");
            type = intent.getStringExtra("type");
        }

        ArrayList<String[]> foodData = CsvReader.readCsv(this, "Nutrition.csv");
        adapter = new FoodAdapter(this, foodData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (lastSelectedView != null) {
                    lastSelectedView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
                view.setBackgroundColor(getResources().getColor(R.color.pink));
                lastSelectedView = view;

                String[] selectedItem = adapter.getItem(position);
                if (selectedItem != null && selectedItem.length >= 2) {
                    if (!selectedItems.contains(selectedItem[0])) {  // Avoid duplicates if necessary
                        selectedItems.add(selectedItem[0]);
                        totalCalories += Integer.parseInt(selectedItem[1]);
                    }
                }
            }

        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCalorieData(username, type, totalCalories);
                Intent intent = new Intent(FoodListActivity.this, MealDescription.class);
                intent.putExtra("selectedItems", selectedItems); // Ensure selectedItems is not null here
                intent.putExtra("username", username);
                intent.putExtra("totalCalories", totalCalories);
                startActivity(intent);



            }
        });
    }

    private void sendCalorieData(String name, String type, int calorie) {
        String url = IpV4Connection.getUrl("insertFood.php"); // Adjust your server URL
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("API Response", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API Error", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("type", type);
                params.put("calorie", String.valueOf(calorie));
                return params;
            }

        };
        queue.add(stringRequest);
    }
}
