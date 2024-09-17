package onetoone.HealthAndWellness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/exerciseBenefits")
public class ExerciseBenefitsController {

    private final ExerciseBenefitsRepository exerciseBenefitsRepository;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseBenefitsController(ExerciseBenefitsRepository exerciseBenefitsRepository, ExerciseRepository exerciseRepository) {
        this.exerciseBenefitsRepository = exerciseBenefitsRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createExerciseBenefits(@RequestBody ExerciseBenefitsDTO exerciseBenefitsDTO) {
        return exerciseRepository.findById(exerciseBenefitsDTO.getExerciseId())
                .map(exercise -> {
                    ExerciseBenefits exerciseBenefits = new ExerciseBenefits();
                    exerciseBenefits.setBenefits(exerciseBenefitsDTO.getBenefits());
                    exerciseBenefits.setExercise(exercise);
                    exerciseBenefits = exerciseBenefitsRepository.save(exerciseBenefits);

                    Map<String, Object> body = new HashMap<>();
                    body.put("message", "Exercise benefit created successfully");
                    body.put("id", exerciseBenefits.getId());
                    return new ResponseEntity<>(body, HttpStatus.CREATED);
                })
                .orElseGet(() -> {
                    Map<String, Object> body = new HashMap<>();
                    body.put("message", "Exercise not found");
                    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
                });
    }

    @GetMapping
    public ResponseEntity<List<ExerciseBenefitsDTO>> getAllExerciseBenefits() {
        List<ExerciseBenefitsDTO> exerciseBenefitsDTOs = exerciseBenefitsRepository.findAll().stream()
                .map(ExerciseBenefitsDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(exerciseBenefitsDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExerciseBenefitsById(@PathVariable Long id) {
        Optional<ExerciseBenefits> exerciseBenefitsOptional = exerciseBenefitsRepository.findById(id);
        return exerciseBenefitsOptional
                .map(benefits -> ResponseEntity.ok(ExerciseBenefitsDTO.fromEntity(benefits)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateExerciseBenefits(@PathVariable Long id, @RequestBody ExerciseBenefitsDTO exerciseBenefitsDTO) {
        return exerciseBenefitsRepository.findById(id)
                .map(existingExerciseBenefits -> {
                    existingExerciseBenefits.setBenefits(exerciseBenefitsDTO.getBenefits());
                    exerciseBenefitsRepository.save(existingExerciseBenefits);
                    return ResponseEntity.ok("Updated exercise benefit with ID: " + existingExerciseBenefits.getId());
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exercise benefit with ID: " + id + " not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExerciseBenefits(@PathVariable Long id) {
        if (exerciseBenefitsRepository.existsById(id)) {
            exerciseBenefitsRepository.deleteById(id);
            return ResponseEntity.ok("Deleted exercise benefit with ID: " + id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exercise benefit with ID: " + id + " not found");
        }
    }

}
