package com.example.pcos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MealDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.meal_description);

        String username = getIntent().getStringExtra("username");
        ArrayList<String> selectedItems = getIntent().getStringArrayListExtra("selectedItems");
        int totalCalories = getIntent().getIntExtra("totalCalories", 0);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button button = findViewById(R.id.button14);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MealDescription.this, PatientHome.class);
            intent.putExtra("username", username);
            startActivity(intent);
            finish();
        });

        TextView textViewItems = findViewById(R.id.textView58);
        StringBuilder itemsDescription = new StringBuilder();
        if (selectedItems != null) {
            for (String item : selectedItems) {
                itemsDescription.append(item).append("\n");
            }
        } else {
            itemsDescription.append("No items selected.");
        }
        textViewItems.setText(itemsDescription.toString());

        TextView textViewCalories = findViewById(R.id.textView57);
        textViewCalories.setText("Total Calories: " + totalCalories);
    }

}
