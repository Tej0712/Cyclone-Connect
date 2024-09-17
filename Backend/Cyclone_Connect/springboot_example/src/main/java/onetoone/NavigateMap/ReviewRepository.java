package onetoone.NavigateMap;

import onetoone.NavigateMap.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Custom query method to fetch reviews by map ID

    List<Review> findByMapId(Long mapId);
}
