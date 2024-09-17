package onetoone.ReportBug;

public class ReportDTO {
    private Long id;
    private String subject;
    private String description;
    private Long userId; // Assuming you want to include the user's ID

    // Default constructor
    public ReportDTO() {}

    // Constructor with fields

    public ReportDTO(Long id, String subject, String description, Long userId) {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.userId = userId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // toString() method for debugging
    @Override
    public String toString() {
        return "ReportDTO{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                '}';
    }
}


