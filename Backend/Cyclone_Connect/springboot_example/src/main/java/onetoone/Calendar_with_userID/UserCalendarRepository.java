package onetoone.Calendar_with_userID;

import io.micrometer.observation.ObservationFilter;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserCalendarRepository extends JpaRepository<CalendarEntity, Long> {

    @Query("SELECT ce FROM CalendarEntityWithUserID ce WHERE ce.user.userId = :userId")
    List<CalendarEntity> findByUserId(@Param("userId") Long userId);
    @Query("SELECT ce FROM CalendarEntityWithUserID ce JOIN FETCH ce.user")
    List<CalendarEntity> findAllWithUsers();

    Optional<CalendarEntity> findByIdAndUser_UserId(Long id, Long userId);

    boolean existsByUser_UserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);


    @Modifying
    @Transactional
    @Query("DELETE FROM CalendarEntityWithUserID ce WHERE ce.id = :eventId AND ce.user.userId = :userId")
    void deleteByIdAndUser_UserId(@Param("eventId") Long eventId, @Param("userId") Long userId);

}


