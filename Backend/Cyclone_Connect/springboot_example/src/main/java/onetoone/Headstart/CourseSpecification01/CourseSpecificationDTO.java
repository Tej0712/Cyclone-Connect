package onetoone.Headstart.CourseSpecification01;

public class CourseSpecificationDTO {
    private Long courseId;
    private String programmingLanguages;
    private String framework;
    private String resources;
    private String difficultyLevel;
    private String timeComplexity;
    private String courseName;
    private String courseDescription;
    private String prerequisites;
    private Integer totalCredits;
    private String embeddedLinks;
    private String roadMap;
    private Double rating;
    private String editedBy;

    private String reviews;

    // Default constructor
    public CourseSpecificationDTO() {
    }

    // Parametrized constructor
    public CourseSpecificationDTO(Long courseId, String programmingLanguages, String framework, String resources, String difficultyLevel, String timeComplexity, String reviews) {
        this.courseId = courseId;
        this.programmingLanguages = programmingLanguages;
        this.framework = framework;
        this.resources = resources;
        this.difficultyLevel = difficultyLevel;
        this.timeComplexity = timeComplexity;
        this.reviews = reviews;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
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
    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
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
