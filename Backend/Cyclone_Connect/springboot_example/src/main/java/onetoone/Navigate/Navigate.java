package onetoone.Navigate;

import jakarta.persistence.*;
import onetoone.Users.User;

@Entity
@Table(name = "navigations")
public class Navigate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destination;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    // Default constructor
    public Navigate() {}


    // Constructor with destination, description, and user
    public Navigate(String destination, String description, User user) {
        this.destination = destination;
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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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
        return "Navigate{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", description='" + description + '\'' +
                ", user=" + (user != null ? user.getUserId() : "null") +
                '}';
    }
}
