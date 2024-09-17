package com.example.myapplication.HelpMenu.HealthAndWellness;

public class Exercise {
    private long id;
    private String exerciseName;
    private int time;
    private int caloriesBurned;
    private String imageUrl;

    public Exercise(long id, String exerciseName, int time, int caloriesBurned, String imageUrl) {
        this.id = id;
        this.exerciseName = exerciseName;
        this.time = time;
        this.caloriesBurned = caloriesBurned;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getTime() {
        return time;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}