package onetoone.Notes_with_userID;

import jakarta.persistence.*;
import onetoone.Users.User;

@Entity(name = "UserNotes") // This changes the JPA entity name
@Table(name = "notes_with_user_ID")
public class Notes_Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;


    // Constructors, Getters, and Setters
    public Notes_Entity() {
    }

    public Notes_Entity(String title, String content) {
        this.title = title;
        this.content = content;

    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Getters and setters for all fields
}
