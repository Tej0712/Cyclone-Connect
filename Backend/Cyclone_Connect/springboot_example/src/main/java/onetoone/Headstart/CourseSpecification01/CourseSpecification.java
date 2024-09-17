package onetoone.Headstart.CourseSpecification01;

import jakarta.persistence.*;
import onetoone.Headstart.Courses01.Courses;

@Entity
@Table(name = "course_specifications")
public class CourseSpecification {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Courses course;

    private String programmingLanguages;
    private String framework;
    private String resources;
    private String difficultyLevel;
    private String timeComplexity;
    private String prerequisites;
    private Integer totalCredits;
    private String embeddedLinks;
    private String roadMap;
    private Double rating;
    private String editedBy;

    private String reviews;

    // Added new fields: prerequisites, totalCredits, embeddedLinks, roadMap, rating, editedBy
    // Constructors
    public CourseSpecification() {
    }

    public CourseSpecification(Courses course, String programmingLanguages, String framework, String resources, String difficultyLevel, String timeComplexity, String reviews) {
        this.course = course;
        this.programmingLanguages = programmingLanguages;
        this.framework = framework;
        this.resources = resources;
        this.difficultyLevel = difficultyLevel;
        this.timeComplexity = timeComplexity;
        this.reviews = reviews;
    }


    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public Integer getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(Integer totalCredits) {
        this.totalCredits = totalCredits;
    }

    public String getEmbeddedLinks() {
        return embeddedLinks;
    }

    public void setEmbeddedLinks(String embeddedLinks) {
        this.embeddedLinks = embeddedLinks;
    }

    public String getRoadMap() {
        return roadMap;
    }

    public void setRoadMap(String roadMap) {
        this.roadMap = roadMap;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Courses getCourse() {
        return course;
    }

    public void setCourse(Courses course) {
        this.course = course;
    }

    public String getProgrammingLanguages() {
        return programmingLanguages;
    }

    public void setProgrammingLanguages(String programmingLanguages) {
        this.programmingLanguages = programmingLanguages;
    }

    public String getFramework() {
        return framework;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getTimeComplexity() {
        return timeComplexity;
    }

    public void setTimeComplexity(String timeComplexity) {
        this.timeComplexity = timeComplexity;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }
}
