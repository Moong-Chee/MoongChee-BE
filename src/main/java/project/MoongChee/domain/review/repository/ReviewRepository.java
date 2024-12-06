package project.MoongChee.domain.review.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.MoongChee.domain.post.entity.Post;
import project.MoongChee.domain.review.entity.Review;
import project.MoongChee.domain.user.domain.User;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByReviewee(User reviewee);

    boolean existsByPostAndReviewer(Post post, User reviewer);
}
