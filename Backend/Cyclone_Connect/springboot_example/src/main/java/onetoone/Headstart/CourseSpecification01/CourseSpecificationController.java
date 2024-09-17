package onetoone.Headstart.CourseSpecification01;

import onetoone.Headstart.Courses01.CourseRepository;
import onetoone.Headstart.Courses01.Courses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/courseSpecifications")
public class CourseSpecificationController {

    private final CourseSpecificationRepository courseSpecificationRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    public CourseSpecificationController(CourseSpecificationRepository courseSpecificationRepository) {
        this.courseSpecificationRepository = courseSpecificationRepository;
    }

    @PostMapping
    public ResponseEntity<?> createCourseSpecification(@RequestBody CourseSpecificationDTO courseSpecificationDTO) {
        Optional<Courses> existingCourse = courseRepository.findById(courseSpecificationDTO.getCourseId());
        if (existingCourse.isPresent() && courseSpecificationRepository.findByCourse(existingCourse.get()).isPresent()) {
            return ResponseEntity.badRequest().body("Course Specification for this course already exists.");
        }

        CourseSpecification courseSpecification = convertToEntity(courseSpecificationDTO);
        courseSpecification = courseSpecificationRepository.save(courseSpecification);
        return ResponseEntity.ok(convertToDTO(courseSpecification));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<CourseSpecificationDTO> getCourseSpecificationByCourseId(@PathVariable Long courseId) {
        Optional<Courses> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            Optional<CourseSpecification> courseSpecification = courseSpecificationRepository.findByCourse(course.get());
            return courseSpecification.map(cs -> ResponseEntity.ok(convertToDTO(cs)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/course/{courseId}")
    public ResponseEntity<CourseSpecificationDTO> updateCourseSpecificationByCourseId(@PathVariable Long courseId,
                                                                                      @RequestBody CourseSpecificationDTO courseSpecificationDTO) {
        Optional<Courses> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            Optional<CourseSpecification> existingSpecification = courseSpecificationRepository.findByCourse(course.get());
            if (existingSpecification.isPresent()) {
                updateCourseSpecificationFromDTO(existingSpecification.get(), courseSpecificationDTO);
                CourseSpecification updatedSpecification = courseSpecificationRepository.save(existingSpecification.get());
                return ResponseEntity.ok(convertToDTO(updatedSpecification));
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

// Inside your CourseSpecificationController class

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourseSpecificationById(@PathVariable Long id) {
        Optional<CourseSpecification> courseSpecificationOptional = courseSpecificationRepository.findById(id);
        if (courseSpecificationOptional.isPresent()) {
            courseSpecificationRepository.deleteById(id);
            return ResponseEntity.ok().body("Course Specification with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Helper methods to convert between entity and DTO
    // You will need to implement these methods based on your entity and DTO structures
    // Inside CourseSpecificationController

    private CourseSpecificationDTO convertToDTO(CourseSpecification courseSpecification) {
        CourseSpecificationDTO dto = new CourseSpecificationDTO();

        CourseSpecificationDTO courseSpecificationDTO = new CourseSpecificationDTO();
        courseSpecificationDTO.setCourseId(courseSpecification.getCourse().getId()); // Assuming Course is the entity related to CourseSpecification
        courseSpecificationDTO.setProgrammingLanguages(courseSpecification.getProgrammingLanguages());
        courseSpecificationDTO.setFramework(courseSpecification.getFramework());
        courseSpecificationDTO.setResources(courseSpecification.getResources());
        courseSpecificationDTO.setDifficultyLevel(courseSpecification.getDifficultyLevel());
        courseSpecificationDTO.setTimeComplexity(courseSpecification.getTimeComplexity());
        courseSpecificationDTO.setCourseName(courseSpecification.getCourse().getCourseName());
        courseSpecificationDTO.setCourseDescription(courseSpecification.getCourse().getDescription());
        courseSpecificationDTO.setPrerequisites(courseSpecification.getPrerequisites());
        courseSpecificationDTO.setTotalCredits(courseSpecification.getTotalCredits());
        courseSpecificationDTO.setEmbeddedLinks(courseSpecification.getEmbeddedLinks());
        courseSpecificationDTO.setRoadMap(courseSpecification.getRoadMap());
        courseSpecificationDTO.setRating(courseSpecification.getRating());
        courseSpecificationDTO.setEditedBy(courseSpecification.getEditedBy());
        courseSpecificationDTO.setReviews(courseSpecification.getReviews());
        return courseSpecificationDTO;
    }

    private CourseSpecification convertToEntity(CourseSpecificationDTO courseSpecificationDTO) {

        CourseSpecification courseSpecification = new CourseSpecification();
        // Use the autowired repository to fetch the course
        Courses course = courseRepository.findById(courseSpecificationDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        courseSpecification.setCourse(course);
        courseSpecification.setProgrammingLanguages(courseSpecificationDTO.getProgrammingLanguages());
        courseSpecification.setFramework(courseSpecificationDTO.getFramework());
        courseSpecification.setResources(courseSpecificationDTO.getResources());
        courseSpecification.setDifficultyLevel(courseSpecificationDTO.getDifficultyLevel());
        courseSpecification.setTimeComplexity(courseSpecificationDTO.getTimeComplexity());
        courseSpecification.setPrerequisites(courseSpecificationDTO.getPrerequisites());
        courseSpecification.setTotalCredits(courseSpecificationDTO.getTotalCredits());
        courseSpecification.setEmbeddedLinks(courseSpecificationDTO.getEmbeddedLinks());
        courseSpecification.setRoadMap(courseSpecificationDTO.getRoadMap());
        courseSpecification.setRating(courseSpecificationDTO.getRating());
        courseSpecification.setEditedBy(courseSpecificationDTO.getEditedBy());
        courseSpecification.setReviews(courseSpecificationDTO.getReviews());
        return courseSpecification;
    }

    private void updateCourseSpecificationFromDTO(CourseSpecification courseSpecification, CourseSpecificationDTO courseSpecificationDTO) {
        // Assuming that the courseId cannot be changed after creation
        courseSpecification.setProgrammingLanguages(courseSpecificationDTO.getProgrammingLanguages());
        courseSpecification.setFramework(courseSpecificationDTO.getFramework());
        courseSpecification.setResources(courseSpecificationDTO.getResources());
        courseSpecification.setDifficultyLevel(courseSpecificationDTO.getDifficultyLevel());
        courseSpecification.setTimeComplexity(courseSpecificationDTO.getTimeComplexity());
        courseSpecification.setPrerequisites(courseSpecificationDTO.getPrerequisites());
        courseSpecification.setTotalCredits(courseSpecificationDTO.getTotalCredits());
        courseSpecification.setEmbeddedLinks(courseSpecificationDTO.getEmbeddedLinks());
        courseSpecification.setRoadMap(courseSpecificationDTO.getRoadMap());
        courseSpecification.setRating(courseSpecificationDTO.getRating());
        courseSpecification.setEditedBy(courseSpecificationDTO.getEditedBy());
        courseSpecification.setReviews(courseSpecificationDTO.getReviews());
        // No need to save here, as this will be handled in the calling method
    }

}
