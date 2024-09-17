package onetoone.NavigateMap;

import onetoone.NavigateMap.Review;

import java.util.List;

public interface ReviewService {
    Review addReviewToMap(Long mapId, Review review);
    List<Review> getAllReviewsByMapId(Long mapId);

    Review updateReview(Long mapId, Long reviewId, Review reviewDetails); // Updated method signature
    boolean deleteReview(Long mapId, Long reviewId); // Updated method signature
}
