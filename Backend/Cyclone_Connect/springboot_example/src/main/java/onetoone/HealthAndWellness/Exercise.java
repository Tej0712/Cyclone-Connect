package onetoone.HealthAndWellness;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exerciseID;
    @OneToMany(mappedBy = "exercise", fetch = FetchType.EAGER) // or LAZY, depending on your requirements
    private List<ExerciseBenefits> benefits;

    private String exerciseName;
    private Integer time; // Time in minutes as a whole number

    private Integer caloriesBurned;
    private String imageUrl;


    public Exercise() {
    }

    public Long getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(Long exerciseID) {
        this.exerciseID = exerciseID;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public List<ExerciseBenefits> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<ExerciseBenefits> benefits) {
        this.benefits = benefits;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
