package onetoone.Profile_Image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import onetoone.Users.User;
import onetoone.Users.UserServices;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@RestController
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserServices userServices;

    private static final String DIRECTORY = System.getProperty("user.home") + "/images";  // example path

    @GetMapping(value = "/users/{userId}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getUserImage(@PathVariable Long userId) {
        Image image = imageRepository.findByUserUserId(userId);  // Updated method name
        if (image != null) {
            try {
                File imgFile = new File(image.getFilePath());
                if (imgFile.exists()) {
                    byte[] imageBytes = Files.readAllBytes(imgFile.toPath());
                    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
                }
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/users/{userId}/image")
    public ResponseEntity<String> uploadUserImage(@PathVariable Long userId, @RequestParam("image") MultipartFile imageFile) {
        User user = userServices.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        File directory = new File(DIRECTORY);
        if (!directory.exists() && !directory.mkdirs()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create image storage directory");
        }

        try {
            File destinationFile = new File(DIRECTORY, UUID.randomUUID() + "_" + imageFile.getOriginalFilename());
            imageFile.transferTo(destinationFile);

            Image image = new Image();
            image.setFilePath(destinationFile.getAbsolutePath());
            image.setUser(user);
            imageRepository.save(image);

            return ResponseEntity.ok("Image uploaded successfully: " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
        }
    }


    @PutMapping("/users/{userId}/image")
    public ResponseEntity<String> updateUserImage(@PathVariable Long userId, @RequestParam("image") MultipartFile imageFile) {
        User user = userServices.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Image image = imageRepository.findByUserUserId(userId);  // Updated method name
        if (image == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existing image found for user");
        }

        File oldImageFile = new File(image.getFilePath());
        if (oldImageFile.exists()) {
            oldImageFile.delete();
        }

        try {
            File destinationFile = new File(DIRECTORY, UUID.randomUUID() + "_" + imageFile.getOriginalFilename());
            imageFile.transferTo(destinationFile);

            image.setFilePath(destinationFile.getAbsolutePath());
            imageRepository.save(image);

            return ResponseEntity.ok("Image updated successfully: " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update image: " + e.getMessage());
        }
    }

    @DeleteMapping("/users/{userId}/image")
    public ResponseEntity<String> deleteUserImage(@PathVariable Long userId) {
        Image image = imageRepository.findByUserUserId(userId);  // Updated method name
        if (image != null) {
            File imgFile = new File(image.getFilePath());
            if (imgFile.exists() && imgFile.delete()) {
                imageRepository.delete(image);
                return ResponseEntity.ok("Image deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete image file on disk.");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found for user with ID: " + userId);
    }

}
