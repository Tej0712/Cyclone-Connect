package onetoone.Navigate;

public class NavigateDTO {
    private Long id;
    private String destination;
    private String description;

    private Long userId; // To include the user's ID

    // Default constructor
    public NavigateDTO() {}

    // Constructor with fields
    public NavigateDTO(Long id, String destination, String description, Long userId) {
        this.id = id;
        this.destination = destination;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // toString() method for debugging
    @Override
    public String toString() {
        return "NavigateDTO{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                '}';
    }
}
