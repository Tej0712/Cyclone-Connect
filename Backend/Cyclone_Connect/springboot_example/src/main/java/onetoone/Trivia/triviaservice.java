package onetoone.Trivia;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class triviaservice {

    @Autowired
    private triviarepository triviaRepository;

    @Autowired
    private FileStorageService fileStorageService;

    // This method should be called every 12 hours to update the trivia.
    @Scheduled(fixedRate = 43200000) // 12 hours in milliseconds
    public void updateTrivia() {
        // Logic to select a new URL and description
        String newPhotoUrl = "..."; // Define how to get the new URL
        String newDescription = "..."; // Define how to get the new description

        saveTrivia(newPhotoUrl, newDescription); // Save the new trivia
    }

//    public void saveTrivia(MultipartFile file, String description) throws IOException {
//        // Store the file on the server's file system and get the path
//        String photoUrl = fileStorageService.store(file);
//
//        // Create a new trivia entity with the photo URL and description
//        triviaentity newTrivia = new triviaentity();
//        newTrivia.setPhotoUrl(photoUrl);
//        newTrivia.setDescription(description);
//
//        // Save the new trivia entity
//        triviaRepository.save(newTrivia);
//    }

    public TriviaDTO getTriviaById(Long id) {
        return triviaRepository.findById(id).map(trivia -> {
            TriviaDTO dto = new TriviaDTO();
            dto.setPhotoUrl(trivia.getPhotoUrl());
            dto.setDescription(trivia.getDescription());
            return dto;
        }).orElseThrow(() -> new EntityNotFoundException("Trivia not found with id " + id));
    }


    public triviaentity getCurrentTrivia() {
        List<triviaentity> triviaList = triviaRepository.findAll();
        if (!triviaList.isEmpty()) {
            // Assuming the latest one is the current trivia.
            return triviaList.get(triviaList.size() - 1);
        }
        return new triviaentity(); // Return an empty trivia if none found.
    }


    public void saveTrivia(String photoUrl, String description) {
        triviaentity trivia = new triviaentity();
        trivia.setPhotoUrl(photoUrl); // URL from the JSON
        trivia.setDescription(description); // Description from the JSON

        // Save the new trivia entity
        triviaRepository.save(trivia);
    }

}
