package onetoone.Trivia;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class triviaentity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String photoUrl;
    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    // Getters and setters...
}
