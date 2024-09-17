package onetoone.NavigateMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maps/{mapId}/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> addReviewToMap(@PathVariable Long mapId, @RequestBody Review review) {
        Review addedReview = reviewService.addReviewToMap(mapId, review);
        if (addedReview != null) {
            return ResponseEntity.ok(addedReview);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviewsByMapId(@PathVariable Long mapId) {
        List<Review> reviews = reviewService.getAllReviewsByMapId(mapId);
        if (!reviews.isEmpty()) {
            return ResponseEntity.ok(reviews);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long mapId, @PathVariable Long reviewId, @RequestBody Review reviewDetails) {
        Review updatedReview = reviewService.updateReview(mapId, reviewId, reviewDetails);
        if (updatedReview != null) {
            return ResponseEntity.ok(updatedReview);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long mapId, @PathVariable Long reviewId) {
        boolean deleted = reviewService.deleteReview(mapId, reviewId);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
