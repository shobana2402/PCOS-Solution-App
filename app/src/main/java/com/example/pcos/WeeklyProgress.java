package com.example.pcos;

public class WeeklyProgress {
    private String date, day, exerciseDuration, caloriesTaken, sleepingHours, noOfSteps;

    public WeeklyProgress(String date, String day, String exerciseDuration, String caloriesTaken, String sleepingHours, String noOfSteps) {
        this.date = date;
        this.day = day;
        this.exerciseDuration = exerciseDuration;
        this.caloriesTaken = caloriesTaken;
        this.sleepingHours = sleepingHours;
        this.noOfSteps = noOfSteps;
    }

    public String getDate() { return date; }
    public String getDay() { return day; }
    public String getExerciseDuration() { return exerciseDuration; }
    public String getCaloriesTaken() { return caloriesTaken; }
    public String getSleepingHours() { return sleepingHours; }
    public String getNoOfSteps() { return noOfSteps; }
}
