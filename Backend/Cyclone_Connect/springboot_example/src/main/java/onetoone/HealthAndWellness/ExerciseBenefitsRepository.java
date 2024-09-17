package onetoone.HealthAndWellness;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseBenefitsRepository extends JpaRepository<ExerciseBenefits, Long> {
    // Custom queries (if needed)
}
