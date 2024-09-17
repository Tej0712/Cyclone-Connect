package onetoone.Trivia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

// ... other imports ...

@RestController
@RequestMapping("/api/trivia")
public class triviacontroller {

    private final triviaservice triviaService;
    private final ObjectMapper objectMapper; // ObjectMapper to parse JSON

    @Autowired
    public triviacontroller(triviaservice triviaService, ObjectMapper objectMapper) {
        this.triviaService = triviaService;
        this.objectMapper = objectMapper;
    }

    // ... existing code ...

    @GetMapping("/current")
    public triviaentity getCurrentTrivia() {
        return triviaService.getCurrentTrivia();
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<TriviaDTO> getTriviaDetailsById(@PathVariable Long id) {
        TriviaDTO trivia = triviaService.getTriviaById(id);
        return ResponseEntity.ok(trivia);
    }


    @PostMapping(value = "/upload", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> handleFileUpload(@RequestBody String metadataJson) {
        try {
            TriviaMetadata metadata = objectMapper.readValue(metadataJson, TriviaMetadata.class);

            // Save the trivia data using the service
            triviaService.saveTrivia(metadata.getPhotoUrl(), metadata.getDescription());

            return ResponseEntity.ok("Trivia saved successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to parse metadata: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while saving trivia: " + e.getMessage());
        }
    }

    // A DTO class for holding metadata
    public static class TriviaMetadata {
        private String photoUrl;
        private String description;

        // Getters and setters
        public String getPhotoUrl() { return photoUrl; }
        public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}






