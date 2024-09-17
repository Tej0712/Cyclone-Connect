package onetoone.Notes_with_userID;

public class NotesDTO {
    private Long id;
    private static String title;
    private static String content;
    private Long userId; // Assuming you want to include the user's ID



    // Default constructor
    public NotesDTO() {
    }

    // Constructor with fields
    public NotesDTO(Long id, String title, String content, Long userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;

    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        return "NoteDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                '}';
    }
}
