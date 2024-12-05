package project.MoongChee.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.MoongChee.domain.post.entity.Post;
import project.MoongChee.domain.post.exception.PostNotFoundException;
import project.MoongChee.domain.post.repository.PostRepository;
import project.MoongChee.domain.review.dto.ReviewRequestDTO;
import project.MoongChee.domain.review.dto.ReviewResponseDTO;
import project.MoongChee.domain.review.entity.Review;
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
}
