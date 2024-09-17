package onetoone.Calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarEntity, Long> {
    // Removed the findAllWithUsers method to reflect the removal of the User relationship
}
