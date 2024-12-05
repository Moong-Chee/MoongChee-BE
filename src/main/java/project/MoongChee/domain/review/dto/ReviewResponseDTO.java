package project.MoongChee.domain.review.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.MoongChee.domain.image.domain.Image;
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
    String reviewContent;
    String reviewerName;
    String revieweeName;
    Long postId;
    List<String> productImageUrls;
    LocalDateTime createdAt;

    public static ReviewResponseDTO from(Review review) {

        List<String> imageUrls = review.getPost().getProductImages().stream()
                .map(Image::getUrl)
                .collect(Collectors.toList());

        return ReviewResponseDTO.builder()
                .id(review.getId())
                .reviewScore(review.getReviewScore())
                .reviewContent(review.getContent())
                .reviewerName(review.getReviewer().getName())
                .revieweeName(review.getReviewee().getName())
                .postId(review.getPost().getPostId())
                .productImageUrls(imageUrls)
                .createdAt(review.getCreatedAt())
                .build();
    }

}
