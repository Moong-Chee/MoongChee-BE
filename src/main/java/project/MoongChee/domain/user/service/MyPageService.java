package project.MoongChee.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.MoongChee.domain.review.dto.ReviewGetResponseDTO;
import project.MoongChee.domain.review.service.ReviewService;
import project.MoongChee.domain.user.domain.User;
import project.MoongChee.domain.user.dto.response.MyPageResponseDTO;
import project.MoongChee.domain.user.exception.UserNotFoundException;
import project.MoongChee.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final ReviewService reviewService;
    private final UserRepository userRepository;

    //마이페이지 비즈니스 로직
    public MyPageResponseDTO getMyPage(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        ReviewGetResponseDTO reviewGetResponseDTO = reviewService.getMyReviews(email);
        return MyPageResponseDTO.from(user, reviewGetResponseDTO);
    }
}
