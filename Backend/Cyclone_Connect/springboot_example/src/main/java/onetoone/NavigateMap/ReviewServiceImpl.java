package onetoone.NavigateMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MapsRepository mapsRepository;

    @Override
    public Review addReviewToMap(Long mapId, Review review) {
        Optional<Maps> mapOptional = mapsRepository.findById(mapId);
        if (mapOptional.isPresent()) {
            review.setMap(mapOptional.get());
            return reviewRepository.save(review);
        }
        return null; // Or handle this scenario as needed
    }

    @Override
    public List<Review> getAllReviewsByMapId(Long mapId) {
        return reviewRepository.findByMapId(mapId);
    }

    @Override
    public Review updateReview(Long mapId, Long reviewId, Review reviewDetails) {
        Optional<Review> existingReview = reviewRepository.findById(reviewId);
        if (existingReview.isPresent()) {
            Review review = existingReview.get();
            review.setRating(reviewDetails.getRating());
            review.setReviewText(reviewDetails.getReviewText());
            // Update other fields as necessary
            return reviewRepository.save(review);
        }
        return null; // Or handle this scenario
    }

    @Override
    public boolean deleteReview(Long mapId, Long reviewId) {
        reviewRepository.deleteById(reviewId);
        return false;
    }
}

