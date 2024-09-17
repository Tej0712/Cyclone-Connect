package onetoone.HealthAndWellness;

import java.util.List;
import java.util.stream.Collectors;

public class ExerciseDTO {
    private Long id;
    private String exerciseName;
    private Integer time; // Assuming you have this field based on your earlier code

    private Integer caloriesBurned;
    private String imageUrl;
    private List<ExerciseBenefitsDTO> benefits;

    public ExerciseDTO() {
    }

    // Standard getters and setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(Integer caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    // Getter and setter for imageUrl
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<ExerciseBenefitsDTO> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<ExerciseBenefitsDTO> benefits) {
        this.benefits = benefits;
    }

    // Convert domain model to DTO
// Ensure the fromEntity method in ExerciseDTO looks like this
    public static ExerciseDTO fromEntity(Exercise exercise) {
        ExerciseDTO dto = new ExerciseDTO();
        dto.setId(exercise.getExerciseID());
        dto.setExerciseName(exercise.getExerciseName());
        dto.setTime(exercise.getTime());
        dto.setCaloriesBurned(exercise.getCaloriesBurned()); // Make sure this method exists and is called correctly
        dto.setImageUrl(exercise.getImageUrl());     // Set the image URL

        if (exercise.getBenefits() != null) {
            dto.setBenefits(exercise.getBenefits().stream()
                    .map(benefit -> {
                        ExerciseBenefitsDTO benefitDTO = new ExerciseBenefitsDTO();
                        benefitDTO.setId(benefit.getId());
                        benefitDTO.setBenefits(benefit.getBenefits());
                        benefitDTO.setExerciseId(benefit.getExercise().getExerciseID());
                        return benefitDTO;
                    })
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
