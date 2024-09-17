package onetoone.Headstart.Courses01;

import onetoone.Headstart.CourseSpecification01.CourseSpecificationDTO;

public class CourseDTO {

    private CourseSpecificationDTO courseSpecification;

    private Long id;
    private String courseName;
    private String courseNumber; // New field for course number
    private String description;
    private Long majorId;

    // Constructors, getters, and setters
    public CourseDTO() {
    }

    public CourseDTO(Long id, String courseName, String courseNumber, String description, Long majorId) {
        this.id = id;
        this.courseName = courseName;
        this.courseNumber = courseNumber; // Initialize course number
        this.description = description;
        this.majorId = majorId;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseNumber() {
        return courseNumber; // Getter for course number
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber; // Setter for course number
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }
}
