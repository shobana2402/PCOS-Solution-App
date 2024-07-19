package com.example.pcos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class FoodAdapter extends ArrayAdapter<String[]> {
    public FoodAdapter(Context context, List<String[]> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        // Check if the convertView is null, if so, inflate the layout
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.nutrition, parent, false);

            // Initialize the ViewHolder and bind the views
            viewHolder = new ViewHolder();
            viewHolder.textViewFoodName = convertView.findViewById(R.id.textViewFoodName);
            viewHolder.textViewCalorie = convertView.findViewById(R.id.textViewCalorie);

            // Set the viewHolder as tag for later access
            convertView.setTag(viewHolder);
        } else {
            // If convertView is not null, reuse the views from the tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the data item for this position
        String[] item = getItem(position);

        // Check if the item is not null and contains at least two elements
        if (item != null && item.length >= 2) {
            // Set the text for textViewFoodName and textViewCalorie
            viewHolder.textViewFoodName.setText(item[0]);
            viewHolder.textViewCalorie.setText(item[1]);
        }

        return convertView;
    }

    // ViewHolder pattern to improve ListView performance
    private static class ViewHolder {
        TextView textViewFoodName;
        TextView textViewCalorie;
    }
}
