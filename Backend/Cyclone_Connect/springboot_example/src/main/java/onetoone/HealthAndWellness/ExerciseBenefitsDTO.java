package onetoone.HealthAndWellness;

public class ExerciseBenefitsDTO {
    private Long id;
    private String benefits;
    // Assuming you don't need the Exercise reference in the DTO
    // If you do, add Exercise or ExerciseDTO as per your requirement
    private Long exerciseId; // This is included only if you want to keep the reference back to Exercise

    public ExerciseBenefitsDTO() {
    }

    // Standard getters and setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public static ExerciseBenefitsDTO fromEntity(ExerciseBenefits exerciseBenefits) {
        ExerciseBenefitsDTO dto = new ExerciseBenefitsDTO();
        dto.setId(exerciseBenefits.getId());
        dto.setBenefits(exerciseBenefits.getBenefits());

        // Make sure the exercise field is not null
        if (exerciseBenefits.getExercise() != null) {
            dto.setExerciseId(exerciseBenefits.getExercise().getExerciseID());
        }

        return dto;
    }


    // Add any other fields you have in your ExerciseBenefits entity
}
