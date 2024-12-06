package project.MoongChee.domain.review.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.MoongChee.domain.post.entity.Post;
import project.MoongChee.domain.post.exception.PostNotFoundException;
import project.MoongChee.domain.post.repository.PostRepository;
import project.MoongChee.domain.review.dto.ReviewGetResponseDTO;
import project.MoongChee.domain.review.dto.ReviewRequestDTO;
import project.MoongChee.domain.review.dto.ReviewResponseDTO;
import project.MoongChee.domain.review.entity.Review;
import project.MoongChee.domain.review.exception.DuplicateReviewException;
import project.MoongChee.domain.review.repository.ReviewRepository;
import project.MoongChee.domain.user.domain.User;
import project.MoongChee.domain.user.service.UserService;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public ReviewResponseDTO createReview(Long postId, ReviewRequestDTO requestDTO, String email) {
        User reviewer = userService.find(email);

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if (reviewRepository.existsByPostAndReviewer(post, reviewer)) {
            throw new DuplicateReviewException();
        }

        User reviewee = post.getAuthor();

        Review review = Review.builder()
                .reviewScore(requestDTO.getReviewScore())
                .content(requestDTO.getReviewContent())
                .reviewer(reviewer)
                .reviewee(reviewee)
                .post(post)
                .build();
        reviewRepository.save(review);
        return ReviewResponseDTO.from(review);
    }

    @Transactional//자기 자신의 리뷰 조회
    public ReviewGetResponseDTO getMyReviews(String email) {
        User reviewee = userService.find(email);
        List<Review> reviews = reviewRepository.findByReviewee(reviewee);
        List<ReviewResponseDTO> responseDTOS = reviews.stream()
                .map(ReviewResponseDTO::from)
                .collect(Collectors.toList());
        int reviewCount = reviews.size();
        double averageScore = reviews.stream()
                .mapToInt(review -> review.getReviewScore().ordinal() + 1)
                .average()
                .orElse(0.0);
        return new ReviewGetResponseDTO(reviewCount, averageScore, responseDTOS);
    }

    @Transactional//특정사용자의 리뷰 조회
    public ReviewGetResponseDTO getReviews(Long userId) {
        User reviewee = userService.find(userId);
        List<Review> reviews = reviewRepository.findByReviewee(reviewee);
        List<ReviewResponseDTO> responseDTOS = reviews.stream()
                .map(ReviewResponseDTO::from)
                .collect(Collectors.toList());
        int reviewCount = reviews.size();
        double averageScore = reviews.stream()
                .mapToInt(review -> review.getReviewScore().ordinal() + 1)
                .average()
                .orElse(0.0);
        return new ReviewGetResponseDTO(reviewCount, averageScore, responseDTOS);
    }
}
