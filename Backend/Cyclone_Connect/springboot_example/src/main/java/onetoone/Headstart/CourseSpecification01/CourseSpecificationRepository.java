package onetoone.Headstart.CourseSpecification01;

import onetoone.Headstart.Courses01.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseSpecificationRepository extends JpaRepository<CourseSpecification, Long> {

    // Custom query method to find a CourseSpecification by its associated Courses entity
    Optional<CourseSpecification> findByCourse(Courses course);
}
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface CourseSpecificationRepository extends JpaRepository<CourseSpecification, Long> {
//
//    Optional<CourseSpecification> findByCourseId(String courseId);
//}
