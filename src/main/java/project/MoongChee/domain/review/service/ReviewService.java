package project.MoongChee.domain.review.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.MoongChee.domain.post.entity.Post;
import project.MoongChee.domain.post.exception.PostNotFoundException;
import project.MoongChee.domain.post.repository.PostRepository;
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

        /* // 사용자가 자신의 게시물에 리뷰를 남기는지 확인. 근데 리뷰 작성을 대여자만 작성하는지 대여를 해준 사람도 작성하는건지 확실하지 않아서 일단 주석처리
        if(post.getAuthor().equals(reviewer)) {
            throw new UnauthorizedReviewerException();
        }*/

        boolean alreadyExists = reviewRepository.existsByPostAndReviewer(post,
                reviewer);//한명의 사용자는 하나의 게시물에 한번만 리뷰 작성 가능
        if (alreadyExists) {
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

    @Transactional
    public List<ReviewResponseDTO> getMyReviews(String email) {
        User reviewee = userService.find(email);
        List<Review> reviews = reviewRepository.findByReviewee(reviewee);
        return reviews.stream()
                .map(ReviewResponseDTO::from)
                .collect(Collectors.toList());
    }
}
