package onetoone.CYfind;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cyfind_items")
public class CyFindItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Column(length = 1024)
    private String description;

    @Column
    @Temporal(TemporalType.DATE)
    private Date dateFound;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String location;

    // Assuming you'll be storing image paths as String. Adjust as necessary if using BLOBs or other methods.
    @Column
    private String imagePath;

    @Column(nullable = false)
    private String emailId;

    // Constructors
    public CyFindItem() {}

    public CyFindItem(String itemName, String description, Date dateFound, String category, String location, String imagePath, String emailId) {
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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}

