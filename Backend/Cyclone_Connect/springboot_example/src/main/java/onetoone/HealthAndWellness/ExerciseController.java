package onetoone.HealthAndWellness;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseRepository exerciseRepository;

    public ExerciseController(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    // Get all exercises

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllExercises() {
        List<ExerciseDTO> exerciseDTOs = exerciseRepository.findAll().stream()
                .map(ExerciseDTO::fromEntity)
                .collect(Collectors.toList());

        // Log the DTOs to see if the caloriesBurned field is correct
        exerciseDTOs.forEach(dto -> System.out.println(dto.getCaloriesBurned()));

        Map<String, Object> response = new HashMap<>();
        response.put("data", exerciseDTOs);
        response.put("message", "All exercises retrieved successfully!");
        return ResponseEntity.ok(response);
    }



    // Get a single exercise by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getExerciseById(@PathVariable Long id) {
        Optional<Exercise> exercise = exerciseRepository.findById(id);
        Map<String, Object> response = new HashMap<>();
        return exercise.map(value -> {
                    response.put("message", "Exercise retrieved successfully!");
                    response.put("data", ExerciseDTO.fromEntity(value));
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    response.put("message", "Exercise not found!");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }

    // Create a new exercise
        @PostMapping
    public ResponseEntity<Map<String, Object>> createExercise(@Valid @RequestBody Exercise exercise) {
        if (exercise.getCaloriesBurned() == null || exercise.getImageUrl() == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Missing caloriesBurned or imageUrl");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        Exercise savedExercise = exerciseRepository.save(exercise);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Exercise created successfully");
        response.put("exercise", savedExercise);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // Update an existing exercise

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Map<String, Object>> updateExercise(@PathVariable Long id, @RequestBody ExerciseDTO exerciseDTO) {
        Optional<Exercise> existingExercise = exerciseRepository.findById(id);
        Map<String, Object> response = new HashMap<>();
        if (existingExercise.isPresent()) {
            Exercise exercise = existingExercise.get();
            // Map properties from DTO to Exercise entity
            exercise.setExerciseName(exerciseDTO.getExerciseName());
            exercise.setTime(exerciseDTO.getTime());
            exercise.setCaloriesBurned(exerciseDTO.getCaloriesBurned());
            exercise.setImageUrl(exerciseDTO.getImageUrl());
            // Add more fields as necessary

            Exercise updatedExercise = exerciseRepository.save(exercise);
            response.put("message", "Exercise updated successfully!");
            response.put("data", ExerciseDTO.fromEntity(updatedExercise));
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Exercise not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }



    // Delete an exercise

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Map<String, Object>> deleteExercise(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        if (exerciseRepository.existsById(id)) {
            try {
                exerciseRepository.deleteById(id);
                response.put("message", "Exercise deleted successfully!");
                return ResponseEntity.ok(response);
            } catch (DataIntegrityViolationException e) {
                response.put("message", "Cannot delete the exercise because it's referenced by other entities.");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }
        } else {
            response.put("message", "Exercise not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


}
