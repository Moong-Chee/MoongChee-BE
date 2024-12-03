package project.MoongChee.domain.post.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.MoongChee.domain.image.domain.Image;
import project.MoongChee.domain.image.dto.request.ImageDto;
import project.MoongChee.domain.image.service.S3ImageService;
import project.MoongChee.domain.post.dto.PostRequestDTO;
import project.MoongChee.domain.post.dto.PostResponseDTO;
import project.MoongChee.domain.post.dto.PostUpdateRequestDTO;
import project.MoongChee.domain.post.entity.Post;
import project.MoongChee.domain.post.entity.PostKeyword;
import project.MoongChee.domain.post.entity.PostStatus;
import project.MoongChee.domain.post.exception.KeywordNotFoundException;
import project.MoongChee.domain.post.exception.NameNotFoundException;
import project.MoongChee.domain.post.exception.PostAlreadyLikedException;
import project.MoongChee.domain.post.exception.PostNotFoundException;
import project.MoongChee.domain.post.repository.PostRepository;
import project.MoongChee.domain.user.domain.User;
import project.MoongChee.domain.user.repository.UserRepository;
import project.MoongChee.domain.user.service.UserService;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final S3ImageService s3ImageService;
    private final UserRepository userRepository;

    @Transactional
    public PostResponseDTO createPost(PostRequestDTO requestDTO, List<MultipartFile> productImages, String email)
            throws IOException {//대여 게시물 생성
        User author = userService.find(email);//현재 로그인한 사용자 조회
        Post post = Post.builder()
                .author(author)
                .name(requestDTO.getName())
                .productContent(requestDTO.getProductContent())
                .keyword(requestDTO.getKeyword())
                .productStatus(requestDTO.getProductStatus())
                .postStatus(PostStatus.ACTIVE)//기본값 ACTIVE
                .returnDate(requestDTO.getReturnDate())
                .rentalPrice(requestDTO.getRentalPrice())
                .build();
        postRepository.save(post);
        if (productImages != null && !productImages.isEmpty()) {
            List<ImageDto> imageDtos = s3ImageService.uploadImages(productImages);
            for (ImageDto dto : imageDtos) {
                Image image = Image.from(dto, post);
                post.addImage(image);
            }
        }
        return PostResponseDTO.from(post);
    }

    @Transactional
    public PostResponseDTO updatePost(Long postId, PostUpdateRequestDTO requestDTO, List<MultipartFile> productImages,
                                      String email) throws IOException {//대여 게시물 수정
        User author = userService.find(email);
        Post post = postRepository.findByPostIdAndAuthor(postId, author)
                .orElseThrow(PostNotFoundException::new);//게시물이 없거나 작성자가 아니면 예외

        post.setName(requestDTO.getName());
        post.setProductContent(requestDTO.getProductContent());
        post.setKeyword(requestDTO.getKeyword());
        post.setProductStatus(requestDTO.getProductStatus());
        post.setPostStatus(requestDTO.getPostStatus());
        post.setReturnDate(requestDTO.getReturnDate());
        post.setRentalPrice(requestDTO.getRentalPrice());

        if (productImages != null && !productImages.isEmpty()) {
            post.getProductImages().clear();
            List<ImageDto> imageDtos = s3ImageService.uploadImages(productImages);
            for (ImageDto dto : imageDtos) {
                Image image = Image.from(dto, post);
                post.addImage(image);
            }
        }
        postRepository.save(post);
        return PostResponseDTO.from(post);
    }

    @Transactional//리스트를 통한 게시물 전체 조회 기능 구현
    public List<PostResponseDTO> getAllPosts() {
        List<Post> postPage = postRepository.findByPostStatusNot(PostStatus.CLOSED)
                .stream()
                .collect(Collectors.toList());

        return postPage.stream()
                .map(PostResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        return PostResponseDTO.from(post);
    }

    @Transactional//리스트를 이용한 게시물 검색으로 수정
    public List<PostResponseDTO> searchPosts(String name, PostKeyword keyword) {
        List<Post> searchPosts = postRepository.searchPosts(name, keyword)
                .stream()
                .collect(Collectors.toList());

        if (searchPosts.isEmpty()) {
            if (name != null && keyword != null) {
                throw new PostNotFoundException();
            } else if (name != null) {
                throw new NameNotFoundException();
            } else if (keyword != null) {
                throw new KeywordNotFoundException();
            } else {
                throw new PostNotFoundException();
            }
        }

        return searchPosts.stream()
                .map(PostResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional//관심 게시물 등록
    public void addLikePost(Long postId, String email) {
        User user = userService.find(email);
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if (user.getLikes().contains(post)) {
            throw new PostAlreadyLikedException();
        }
        user.addLike(post);
        userRepository.save(user);
    }


}
