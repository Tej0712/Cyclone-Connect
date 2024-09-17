package onetoone.Headstart.Courses01;

//import CourseDTO;
import onetoone.Headstart.Major01.MajorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final MajorRepository majorRepository;

    @Autowired
    public CourseController(CourseRepository courseRepository, MajorRepository majorRepository) {
        this.courseRepository = courseRepository;
        this.majorRepository = majorRepository;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createCourse(@RequestBody CourseDTO courseDTO) {
        Map<String, Object> response = new HashMap<>();
        return majorRepository.findById(courseDTO.getMajorId())
                .map(major -> {
                    Courses course = new Courses(); // Assuming you have a no-arg constructor
                    course.setCourseName(courseDTO.getCourseName());
                    course.setCourseNumber(courseDTO.getCourseNumber());
                    course.setDescription(courseDTO.getDescription());
                    course.setMajor(major); // Set the major based on the found major
                    course = courseRepository.save(course);
                    response.put("message", "Course created successfully");
                    response.put("courseId", course.getId());
                    return new ResponseEntity<>(response, HttpStatus.CREATED);
                })
                .orElseGet(() -> {
                    response.put("error", "Major not found with ID: " + courseDTO.getMajorId());
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                });
    }

    @GetMapping
    public ResponseEntity<List<Courses>> getAllCourses() {
        List<Courses> courses = courseRepository.findAll();
        return courses.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        return courseRepository.findById(id)
                .<ResponseEntity<?>>map(course -> ResponseEntity.ok(course)) // Cast to ResponseEntity<?>
                .orElseGet(() -> {
                    Map<String, Object> error = new HashMap<>();
                    error.put("error", "Course not found with ID: " + id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error); // This matches ResponseEntity<?>
                });
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        return courseRepository.findById(id).map(existingCourse -> {
            existingCourse.setCourseName(courseDTO.getCourseName());
            existingCourse.setCourseNumber(courseDTO.getCourseNumber());
            existingCourse.setDescription(courseDTO.getDescription());
            majorRepository.findById(courseDTO.getMajorId())
                    .ifPresent(existingCourse::setMajor); // Only set the major if it's found
            Courses updatedCourse = courseRepository.save(existingCourse);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Course updated successfully");
            response.put("courseId", updatedCourse.getId());
            return ResponseEntity.ok(response);
        }).orElseGet(() -> {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Course not found with ID: " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        return courseRepository.findById(id)
                .map(course -> {
                    courseRepository.deleteById(id);
                    return ResponseEntity.ok("Course deleted successfully with ID: " + id);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Course not found with ID: " + id));
    }
}