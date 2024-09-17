package onetoone.CYfind;

import java.util.Date;

public class CyFindItemDTO {

    private Long id;
    private String itemName;
    private String description;
    private Date dateFound;
    private String category;
    private String location;
    private String imagePath;

    private String emailId; // New field for Email ID

    // Constructors
    public CyFindItemDTO() {}

    public CyFindItemDTO(Long id, String itemName, String description, Date dateFound, String category, String location, String imagePath) {
        this.id = id;
        this.itemName = itemName;
        this.description = description;
        this.dateFound = dateFound;
        this.category = category;
        this.location = location;
        this.imagePath = imagePath;
        this.emailId = emailId; // Initialize the new field
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateFound() {
        return dateFound;
    }

    public void setDateFound(Date dateFound) {
        this.dateFound = dateFound;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getEmailId() {return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }
}
