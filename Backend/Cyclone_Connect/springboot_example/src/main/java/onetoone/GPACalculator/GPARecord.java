package onetoone.GPACalculator;

import jakarta.persistence.*;
import onetoone.Users.User;

@Entity
public class GPARecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subjectName;
    private Integer credit;
    private String grade; // Grade as a letter (e.g., "A", "B+", "C-")

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    // Default constructor
    public GPARecord() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    // Parameterized constructor
    public GPARecord(String subjectName, Integer credit, String grade, User user) {
        this.subjectName = subjectName;
        this.credit = credit;
        this.grade = grade;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
