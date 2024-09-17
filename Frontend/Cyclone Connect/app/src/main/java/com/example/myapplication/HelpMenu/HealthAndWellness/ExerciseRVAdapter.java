package com.example.myapplication.HelpMenu.HealthAndWellness;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.R;
import java.util.List;

public class ExerciseRVAdapter extends RecyclerView.Adapter<ExerciseRVAdapter.ExerciseViewHolder> {
    private final List<Exercise> exerciseList;
    private final ExerciseClickInterface exerciseClickInterface;

    public ExerciseRVAdapter(List<Exercise> exerciseList, ExerciseClickInterface exerciseClickInterface) {
        this.exerciseList = exerciseList;
        this.exerciseClickInterface = exerciseClickInterface;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_rv_item, parent, false);
        return new ExerciseViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        holder.exerciseNameTV.setText(exercise.getExerciseName());
        holder.exerciseLAV.setAnimationFromUrl(exercise.getImageUrl());
        holder.timeTV.setText("Time: " + exercise.getTime() + " MIN");
        holder.caloriesTV.setText("Calories: " + exercise.getCaloriesBurned() + " KCAL");
        holder.exerciseIdTV.setText("Exercise ID: " + exercise.getId());
        holder.itemView.setOnClickListener(v -> exerciseClickInterface.onExerciseClick(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private final TextView exerciseNameTV;
        private final TextView timeTV;
        private final TextView caloriesTV;
        private final TextView exerciseIdTV;
        private final LottieAnimationView exerciseLAV;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseNameTV = itemView.findViewById(R.id.exercise_name_text_view);
            timeTV = itemView.findViewById(R.id.exercise_time_text_view);
            caloriesTV = itemView.findViewById(R.id.exercise_calories_text_view);
            exerciseIdTV = itemView.findViewById(R.id.exercise_id_text_view);
            exerciseLAV = itemView.findViewById(R.id.exercise_animation_view);
        }
    }

    public interface ExerciseClickInterface {
        void onExerciseClick(int position);
    }
}