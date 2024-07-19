package com.example.pcos;

import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public class ExerciseList extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.exercise_list);  // Ensure this layout has a RecyclerView with the ID below

        recyclerView = findViewById(R.id.exercise_list);  // Ensure this ID matches your layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the day passed from MainActivity
        String day = getIntent().getStringExtra("day");
        if (day == null) {
            Toast.makeText(this, "No day provided", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] exercises = getExercisesForDay(day);
        List<String> exerciseList = Arrays.asList(exercises);
        int[] imageResources = getImagesForExercises(exerciseList.size()); // Retrieves image IDs based on the number of exercises
        Exercise_Adapter adapter = new Exercise_Adapter(exerciseList, imageResources, this);
        recyclerView.setAdapter(adapter);
    }


    private String[] getExercisesForDay(String day) {
        switch (day) {
            case "Monday":
                return new String[]{"Jumping Jacks", "Assisted Lunges", "Plie Squats", "Walking Squats", "Burpees", "Mountain Climber", "Pendulum Swings", "Mountain Climber (again)", "Flutter Kicks", "Side Lunges", "Backward Lunge", "Crab Kicks", "Deep Squat Hold", "Shoulder Stretch (Left)", "Shoulder Stretch (Right)", "Straight Leg Fire Hydrant (Left)", "Straight Leg Fire Hydrant (Right)", "Glute Kick Back (Left)", "Glute Kick Back (Right)"};
            case "Tuesday":
                return new String[]{"Jumping Jacks", "Assisted Lunges", "Standing Bicycle Crunches", "Russian Twist", "Mountain Climber", "Flutter Kicks", "Leg Raises", "Butt Bridge", "Skipping without Rope", "Standing Bicycle Crunches (again)", "Plank", "Reverse Crunches", "Heel Touch", "Reclined Oblique Twist", "Heels to the Heavens", "Cross Knee Plank", "Cross Arm Crunches", "X-Man Crunch", "Side Crunches (Left/Right)"};
            case "Wednesday":
                return new String[]{"Dynamic Chest","Triceps Dips","Push-Ups","Diagonal Plank","Incline Push-Ups","star Crawl","Arm Scissors","Triceps Dips (again)","Push-Ups (again)","Diagonal Plank (again)","Incline Push-Ups (again)","Elbows Back","Leg Barbell Curl (Left)","Leg Barbell Curl (Right)","Jumping Jacks","Plank Taps","Elbows Back","Leg Barbell Curl (Left)", "Leg Barbell Curl (Right)","Jumping Jacks"};
            case "Thursday":
                return new String[]{"Burpees", "Mountain Climber","Pendulum Swings","Mountain Climber (again)","Side Lunges","Quick Feet", "Butt Kicks","Skipping Without Rope", "Side Hop", "Squat Pulses","Straight Leg Fire Hydrant (Left)","Straight Leg Fire Hydrant (Right)","Glute Kick Back (Left)","Glute Kick Back (Right)", "Fast Spider Lunges","Side Leg Circuits (Left)", "Side Leg Circuits (Right)", "Deep Squat Hold","Diagonal Plank","Reclined Oblique Twist"};
            case "Friday":
                return new String[]{"Plie Squats","Walking Squats","Burpees","Mountain Climber","Pendulum Swings","Mountain Climber (again)","Flutter Kicks","Side Lunges,Backward Lunge","Crab Kicks,Skipping without Rope,Side Hop,Quick Feet,Butt Kicks,Squat Pulses,Straight Leg Fire Hydrant (Left)","Straight Leg Fire Hydrant (Right)","Glute Kick Back (Left)","Glute Kick Back (Right)","Arm Scissors"};
            case "Saturday":
                return new String[]{"Standing Bicycle Crunches","Russian Twist","Mountain Climber","Flutter Kicks","Leg Raises","Butt Bridge","Skipping without Rope","Standing Bicycle Crunches (again)","Plank,Reverse Crunches","Heel Touch","Reclined Oblique Twist","Heels to the Heavens","Cross Knee Plank","Cross Arm Crunches","X-Man Crunch","Side Crunches (Left)","Side Crunches (Right)","V-Hold","Cobra Stretch"};
            default:
                return new String[]{"No exercises for this day"};
        }
    }
    private int[] getImagesForExercises(int count) {
        String[] imgNames = {"pic27", "pic28", "yoga3", "yoga5", "yoga6","yoga4", "pic27", "pic28", "yoga3", "yoga5", "yoga6","yoga4","pic27", "pic28", "yoga3", "yoga5", "yoga6","yoga4",  "yoga3", "yoga4", "pic27", "pic28", "pic5", "yoga6"};
        int[] images = new int[count];
        for (int i = 0; i < count; i++) {
            images[i] = getResources().getIdentifier(imgNames[i % imgNames.length], "drawable", getPackageName());
        }
        return images;
    }
}
