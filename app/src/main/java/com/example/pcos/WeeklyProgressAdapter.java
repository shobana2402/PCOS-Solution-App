package com.example.pcos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class WeeklyProgressAdapter extends RecyclerView.Adapter<WeeklyProgressAdapter.ViewHolder> {

    private List<WeeklyProgress> items = new ArrayList<>();

    public void setData(List<WeeklyProgress> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekly_progress_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeeklyProgress item = items.get(position);
        holder.textView96.setText(item.getDate());
        holder.textView97.setText(item.getDay());
        holder.textView98.setText(item.getExerciseDuration());
        holder.textView99.setText(item.getCaloriesTaken());
        holder.textView100.setText(item.getSleepingHours());
        holder.textView101.setText(item.getNoOfSteps());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView96, textView97, textView98, textView99, textView100, textView101;

        ViewHolder(View view) {
            super(view);
            textView96 = view.findViewById(R.id.textView96);
            textView97 = view.findViewById(R.id.textView97);
            textView98 = view.findViewById(R.id.textView98);
            textView99 = view.findViewById(R.id.textView99);
            textView100 = view.findViewById(R.id.textView100);
            textView101 = view.findViewById(R.id.textView101);
        }
    }
}
