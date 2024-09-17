package onetoone.HealthAndWellness;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    // You can define custom database queries here if needed
//    @Override
//    @EntityGraph(attributePaths = {"benefits"})
//    List<Exercise> findAll();
}
