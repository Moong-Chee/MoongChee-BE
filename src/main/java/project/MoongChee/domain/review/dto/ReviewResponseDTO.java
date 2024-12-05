package project.MoongChee.domain.review.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.MoongChee.domain.review.entity.Review;
import project.MoongChee.domain.review.entity.ReviewScore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseDTO {
    Long id;
    ReviewScore reviewScore;
    String content;
    String reviewerName;
    String revieweeName;
    Long postId;
    LocalDateTime createdAt;

    public static ReviewResponseDTO from(Review review) {
        return ReviewResponseDTO.builder()
                .id(review.getId())
                .reviewScore(review.getReviewScore())
                .reviewerName(review.getReviewer().getName())
                .revieweeName(review.getReviewee().getName())
                .postId(review.getPost().getPostId())
                .createdAt(review.getCreatedAt())
                .build();
    }

}
