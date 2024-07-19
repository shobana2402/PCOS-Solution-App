package com.example.pcos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context context;
    private List<MedicalRecord> records;

    public ImageAdapter(Context context, List<MedicalRecord> records) {
        this.context = context;
        this.records = records;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        MedicalRecord record = records.get(position);
        String imageUrl = record.getRecordPath();

        Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.pic1)
                .error(R.drawable.pic1)
                .into(holder.imageView);

        // Set the click listener to launch the ImageViewerActivity
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ImageViewerActivity.class);
            intent.putExtra("image_url", imageUrl); // Pass the URL to the ImageViewerActivity
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
