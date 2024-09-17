package onetoone.GPACalculator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GPARecordRepository extends JpaRepository<GPARecord, Long> {
    List<GPARecord> findByUser_UserId(Long userId);
}




