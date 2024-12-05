package project.MoongChee.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.MoongChee.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
