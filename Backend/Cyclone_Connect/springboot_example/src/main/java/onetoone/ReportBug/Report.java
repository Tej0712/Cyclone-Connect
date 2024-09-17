package onetoone.ReportBug;

import jakarta.persistence.*;
import onetoone.Users.User;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")

    private User user;

    // Default constructor
    public Report() {}

    // Constructor with subject, description, and user
    public Report(String subject, String description, User user) {
        this.subject = subject;
        this.description = description;
        this.user = user;
    }

    // Getters and setters for all fields
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Override toString() method for convenient logging and debugging
    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", user=" + (user != null ? user.getUserId() : "null") +
                '}';
    }
}
