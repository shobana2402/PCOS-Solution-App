package com.example.pcos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QuestionAndAnswers1 extends AppCompatActivity {

    private JSONArray questions;
    private int currentQuestionIndex = 0;
    private TextView questionTextView;
    private TextView[] optionTextViews = new TextView[7];
    private CheckBox[] checkBoxes = new CheckBox[7];
    private Button nextButton;
    private String username;
    private int mild = 0;
    private int moderate = 0;
    private int severe = 0;
    private boolean isCheckboxClicked = false; // Track whether any checkbox is clicked

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ques_ans_alt2);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        questionTextView = findViewById(R.id.textView13);
        optionTextViews[0] = findViewById(R.id.textView15);
        optionTextViews[1] = findViewById(R.id.textView16);
        optionTextViews[2] = findViewById(R.id.textView17);
        optionTextViews[3] = findViewById(R.id.textView18);
        optionTextViews[4] = findViewById(R.id.textView19);
        optionTextViews[5] = findViewById(R.id.textView20);
        optionTextViews[6] = findViewById(R.id.textView21);
        nextButton = findViewById(R.id.button4);

        checkBoxes[0] = findViewById(R.id.checkBox);
        checkBoxes[1] = findViewById(R.id.checkBox2);
        checkBoxes[2] = findViewById(R.id.checkBox3);
        checkBoxes[3] = findViewById(R.id.checkBox4);
        checkBoxes[4] = findViewById(R.id.checkBox5);
        checkBoxes[5] = findViewById(R.id.checkBox6);
        checkBoxes[6] = findViewById(R.id.checkBox7);

        nextButton.setVisibility(View.GONE); // Initially hide the next button
        setUpCheckboxListeners();
        fetchQuestions();

        nextButton.setOnClickListener(view -> {
            if (currentQuestionIndex < questions.length() - 1) {
                // Increment to the next question
                currentQuestionIndex++;
                resetCheckboxes();
                try {
                    displayCurrentQuestion();
                } catch (JSONException e) {
                    Toast.makeText(this, "Error displaying question", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            } else {
                // Send results when the last question is answered
                sendResults();
            }
        });
    }

    private void setUpCheckboxListeners() {
        for (int i = 0; i < checkBoxes.length; i++) {
            int index = i; // to use in lambda expression
            checkBoxes[i].setOnClickListener(v -> {
                resetCheckboxes();
                checkBoxes[index].setChecked(true);
                calculateScores(index);
                isCheckboxClicked = true; // Set flag to true when checkbox is clicked
                nextButton.setVisibility(View.VISIBLE); // Show the next button when a checkbox is clicked
            });
        }
    }

    private void resetCheckboxes() {
        for (CheckBox cb : checkBoxes) {
            cb.setChecked(false);
        }
        isCheckboxClicked = false; // Reset flag when resetting checkboxes
    }

    private void calculateScores(int index) {
        if (index < 3) {
            mild += 1; // Increment mild if the selected index is among the first three options
        } else if (index < 5) {
            moderate += 1; // Increment moderate if the selected index is the fourth or fifth option
        } else {
            severe += 1; // Increment severe if the selected index is the sixth or seventh option
        }
    }


    private void displayCurrentQuestion() throws JSONException {
        JSONObject question = questions.getJSONObject(currentQuestionIndex);
        questionTextView.setText(question.getString("question"));
        JSONArray options = question.getJSONArray("options");

        for (int i = 0; i < optionTextViews.length; i++) {
            if (i < options.length()) {
                JSONObject option = options.getJSONObject(i);
                optionTextViews[i].setText(option.getString("option_text"));
                optionTextViews[i].setVisibility(View.VISIBLE);
                checkBoxes[i].setVisibility(View.VISIBLE);
            } else {
                optionTextViews[i].setVisibility(View.GONE);
                checkBoxes[i].setVisibility(View.GONE);
            }
        }
        // Hide the next button if no checkbox is clicked
        if (!isCheckboxClicked) {
            nextButton.setVisibility(View.GONE);
        }
    }

    private void sendResults() {
        String url = IpV4Connection.getBaseUrl() + "ans.php"; // Update URL if needed
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject postData = new JSONObject();
        try {
            postData.put("name", username);
            postData.put("mild", mild);
            postData.put("moderate", moderate);
            postData.put("severe", severe);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                response -> {
                    try {
                        if (response.has("success")) {
                            Toast.makeText(this, response.getString("success"), Toast.LENGTH_LONG).show();
                            navigateToResultActivity(); // Navigate to the next activity upon success
                        } else {
                            Toast.makeText(this, "Failed to update records: " + response.getString("error"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(this, "Error parsing response", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(this, "Failed to send results", Toast.LENGTH_LONG).show();
            error.printStackTrace();
        });

        queue.add(jsonObjectRequest);
    }

    private void navigateToResultActivity() {
        Intent intent = new Intent(QuestionAndAnswers1.this,Gifloader.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void fetchQuestions() {
        String url = IpV4Connection.getBaseUrl() + "qns.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        questions = response.getJSONArray("questions");
                        displayCurrentQuestion();
                    } catch (JSONException e) {
                        Toast.makeText(this, "Failed to fetch questions", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(this, "Failed to fetch questions", Toast.LENGTH_LONG).show();
            error.printStackTrace();
        });

        queue.add(jsonObjectRequest);
    }
}
