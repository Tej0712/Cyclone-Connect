package onetoone.GPACalculator;

public class GPARecordDTO {

    private Long id;
    private String subjectName;
    private Integer credit;
    private String grade;
    private Long userId; // Only include the user ID

    // Default constructor for frameworks that require it
    public GPARecordDTO() {}

    // Parameterized constructor for manual instantiation
    public GPARecordDTO(Long id, String subjectName, Integer credit, String grade, Long userId) {
        this.id = id;
        this.subjectName = subjectName;
        this.credit = credit;
        this.grade = grade;
        this.userId = userId;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Converts an entity to a DTO
    public static GPARecordDTO fromEntity(GPARecord gpaRecord) {
        return new GPARecordDTO(
                gpaRecord.getId(),
                gpaRecord.getSubjectName(),
                gpaRecord.getCredit(),
                gpaRecord.getGrade(),
                gpaRecord.getUser() != null ? gpaRecord.getUser().getUserId() : null
        );
    }
}
