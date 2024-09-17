package onetoone.NavigateMap;

import jakarta.persistence.*;
import onetoone.NavigateMap.Maps;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; // Reviewer's username or identifier

    private String reviewText; // The content of the review
    private Integer rating; // Rating, for example, from 1 to 5

    @ManyToOne
    @JoinColumn(name = "map_id", nullable = false)
    private Maps map;

    public Review() {
    }

    // Constructor with parameters as needed

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Maps getMap() {
        return map;
    }

    public void setMap(Maps map) {
        this.map = map;
    }
}
