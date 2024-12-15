package project.MoongChee.domain.user.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.MoongChee.domain.post.dto.MyPostByStatusResponseDTO;
import project.MoongChee.domain.post.dto.PostResponseDTO;
import project.MoongChee.domain.post.entity.Post;
import project.MoongChee.domain.post.entity.PostStatus;
import project.MoongChee.domain.post.repository.PostRepository;
import project.MoongChee.domain.review.dto.ReviewGetResponseDTO;
import project.MoongChee.domain.review.service.ReviewService;
import project.MoongChee.domain.user.domain.Department;
import project.MoongChee.domain.user.domain.User;
import project.MoongChee.domain.user.dto.response.MyPageResponseDTO;
import project.MoongChee.domain.user.dto.response.MyProfileResponse;
import project.MoongChee.domain.user.dto.response.UserProfileResponse;
import project.MoongChee.domain.user.exception.UserNotFoundException;
import project.MoongChee.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ReviewService reviewService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PostRepository postRepository;

    //마이페이지 비즈니스 로직
    @Transactional
    public MyPageResponseDTO getMyPage(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        ReviewGetResponseDTO reviewGetResponseDTO = reviewService.getMyReviews(email);
        return MyPageResponseDTO.from(user, reviewGetResponseDTO);
    }

    //자신의 프로필 반환
    @Transactional
    public MyProfileResponse getMyProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        Department department = user.getDepartment();
        long studentNumber = user.getStudentNumber();
        LocalDate birthday = user.getBirthday();

        return MyProfileResponse.from(user, department, studentNumber, birthday);
    }

    //특정 사용자의 프로필 반환
    @Transactional
    public UserProfileResponse getUserProfile(Long userId) {
        User user = userRepository.findIdById(userId)
                .orElseThrow(UserNotFoundException::new);
        return UserProfileResponse.from(user);
    }

    @Transactional//관심 게시물 조회
    public List<PostResponseDTO> getLikePosts(String email) {
        User user = userService.find(email);
        return user.getLikes().stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .map(PostResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional//진행중인 거래 조회
    public List<MyPostByStatusResponseDTO> getMyActivePosts(String email) {
        User user = userService.find(email);
        List<Post> myActivePosts = postRepository.findByAuthorAndPostStatusNot(user, PostStatus.CLOSED);
        return myActivePosts.stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .map(MyPostByStatusResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional//종료된 거래 조회
    public List<MyPostByStatusResponseDTO> getMyClosedPosts(String email) {
        User user = userService.find(email);
        List<Post> myActivePosts = postRepository.findByAuthorAndPostStatus(user, PostStatus.CLOSED);
        return myActivePosts.stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .map(MyPostByStatusResponseDTO::from)
                .collect(Collectors.toList());
    }
}
