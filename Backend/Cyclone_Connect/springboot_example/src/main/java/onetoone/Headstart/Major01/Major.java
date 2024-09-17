package onetoone.Headstart.Major01;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import onetoone.Headstart.Courses01.Courses;


import java.util.List;

@Entity
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long majorID;

    private String majorName;

    @JsonManagedReference
    @OneToMany(mappedBy = "major", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Courses> courses;

    public Long getMajorID() {
        return majorID;
    }

    public void setMajorID(Long majorID) {
        this.majorID = majorID;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public List<Courses> getCourses() {
        return courses;
    }

    public void setCourses(List<Courses> courses) {
        this.courses = courses;
    }

    // Constructors, getters, and setters
}