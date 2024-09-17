package onetoone.ReportBug;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    // This method retrieves all reports associated with the given user ID

//    List<Report> findByUserId(Long userId);
}
