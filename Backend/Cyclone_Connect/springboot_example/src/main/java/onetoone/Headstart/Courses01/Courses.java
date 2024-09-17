package onetoone.Headstart.Courses01;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import onetoone.Headstart.CourseSpecification01.CourseSpecification;
import onetoone.Headstart.Major01.Major;

@Entity
public class Courses {
    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL)
    private CourseSpecification courseSpecification;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;
    private String courseNumber;
    private String description;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id")
    private Major major;

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
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    // Constructors, getters, and setters
}