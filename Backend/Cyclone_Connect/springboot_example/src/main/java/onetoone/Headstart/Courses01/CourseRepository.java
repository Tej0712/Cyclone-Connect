package onetoone.Headstart.Courses01;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Courses, Long> {
    // Method to find courses by name containing a specified string, ignoring case
    List<Courses> findByCourseNameContainingIgnoreCase(String courseName);

    // Method to find courses by course number containing a specified string, ignoring case
    List<Courses> findByCourseNumberContainingIgnoreCase(String courseNumber);
}
