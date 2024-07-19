package com.example.pcos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {
    private List<LeaderboardEntry> leaderboardEntries;
    private android.content.Context context;

    public LeaderboardAdapter(List<LeaderboardEntry> leaderboardEntries, android.content.Context context) {
        this.leaderboardEntries = leaderboardEntries;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leaderboard_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LeaderboardEntry entry = leaderboardEntries.get(position);
        holder.nameTextView.setText(entry.getName());
        holder.scoreTextView.setText(String.valueOf(entry.getTotalScore()));
        if (entry.getProfileImage() != null && !entry.getProfileImage().isEmpty()) {
            Glide.with(context)
                    .load(entry.getProfileImage())
                    .apply(RequestOptions.circleCropTransform()) // Apply circular transformation
                    .placeholder(R.drawable.icon_profile) // Placeholder image while loading
                    .error(R.drawable.icon_profile) // Error image if loading fails
                    .into(holder.profileImageView);
        }
    }

    @Override
    public int getItemCount() {
        return leaderboardEntries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView scoreTextView;
        public ImageView profileImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
            profileImageView = itemView.findViewById(R.id.profileImageView);
        }
    }
}
