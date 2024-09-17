package onetoone.Profile_Image;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    // Method to find an Image by user's userId
    Image findByUserUserId(Long userId);
}
