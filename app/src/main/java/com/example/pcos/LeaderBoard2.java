package com.example.pcos;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class LeaderBoard2 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LeaderboardAdapter adapter;
    private List<LeaderboardEntry> entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);
        recyclerView = findViewById(R.id.recycler4);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        entries = new ArrayList<>();
        adapter = new LeaderboardAdapter(entries, this);
        recyclerView.setAdapter(adapter);
        loadLeaderboardData();
        recyclerView.addItemDecoration(new SpacesItemDecoration(20));
    }

    private void loadLeaderboardData() {
        String url = IpV4Connection.getUrl("leaderboard.php");
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray topScores = response.getJSONArray("top_scores");
                        for (int i = 0; i < topScores.length(); i++) {
                            JSONObject score = topScores.getJSONObject(i);
                            String name = score.getString("Name");
                            int totalScore = score.getInt("Totalscore");
                            String profileImage = score.getString("ProfileImage");
                            entries.add(new LeaderboardEntry(name, totalScore, profileImage));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Toast.makeText(this, "Error parsing JSON data", Toast.LENGTH_LONG).show();
                    }
                }, error -> Toast.makeText(this, "Error loading data", Toast.LENGTH_LONG).show()
        );

        queue.add(jsonObjectRequest);
    }
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = space; // Add space at the bottom of each item
        }
    }

}
