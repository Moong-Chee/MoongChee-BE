package project.MoongChee.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.MoongChee.domain.post.dto.PostRequestDTO;
import project.MoongChee.domain.post.dto.PostResponseDTO;
import project.MoongChee.domain.post.entity.Post;
import project.MoongChee.domain.post.entity.PostStatus;
import project.MoongChee.domain.post.repository.PostRepository;
import project.MoongChee.domain.user.domain.User;
import project.MoongChee.domain.user.service.UserService;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public PostResponseDTO createPost(PostRequestDTO requestDTO, String email) {//대여 게시물 생성
        User author = userService.find(email);//현재 로그인한 사용자 조회
        Post post = Post.builder()
                .author(author)
                .name(requestDTO.getName())
                .productImageUrl(requestDTO.getProductImageUrl())
                .productContent(requestDTO.getProductContent())
                .keyword(requestDTO.getKeyword())
                .productStatus(requestDTO.getProductStatus())
                .postStatus(PostStatus.ACTIVE)//기본값 ACTIVE
                .build();
        postRepository.save(post);
        return PostResponseDTO.from(post);
    }
}
