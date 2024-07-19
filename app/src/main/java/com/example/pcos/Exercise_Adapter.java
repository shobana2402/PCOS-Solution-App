package com.example.pcos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Exercise_Adapter extends RecyclerView.Adapter<Exercise_Adapter.ViewHolder> {

    private List<String> exerciseList;
    private int[] images;
    private Context context;

    public Exercise_Adapter(List<String> exerciseList, int[] images, Context context) {
        this.exerciseList = exerciseList;
        this.images = images;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_list_cell, parent, false);
        return new ViewHolder(view, context, exerciseList);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.exerciseName.setText(exerciseList.get(position));
        holder.exerciseImage.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView exerciseName;
        public ImageView exerciseImage;
        private Context context;
        private List<String> exerciseList;

        public ViewHolder(View itemView, Context context, List<String> exerciseList) {
            super(itemView);
            this.context = context;
            this.exerciseList = exerciseList;
            exerciseName = itemView.findViewById(R.id.textView111);
            exerciseImage = itemView.findViewById(R.id.imageView10);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                String exercise = exerciseList.get(position);
                Intent intent = new Intent(context, Video1.class);
                intent.putExtra("exerciseName", exercise);  // Pass the exercise name to Video1 activity
                context.startActivity(intent);
            }
        }

    }
}
