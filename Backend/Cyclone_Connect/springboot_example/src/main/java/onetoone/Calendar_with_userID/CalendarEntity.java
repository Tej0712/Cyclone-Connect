package onetoone.Calendar_with_userID;

import jakarta.persistence.*;
import onetoone.Users.User;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "CalendarEntityWithUserID")
@Table(name = "calendar_with_userID")
public class CalendarEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // The column name in the database that holds the foreign key
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;
    private String description;

    @Column(nullable = false) // Ensure a date is provided
    private LocalDate date;

    private LocalTime startTime;
    private LocalTime endTime;

    // Constructors, Getters, and Setters

    // ... Constructor for creating new events
    public CalendarEntity() {
    }


    public CalendarEntity(String title, String description, LocalDate date, LocalTime startTime, LocalTime endTime){
        this.title = title;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;

    }

    // ... Other constructors, getters, and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

}
