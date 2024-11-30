package project.MoongChee.domain.post.service;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.MoongChee.domain.image.domain.Image;
import project.MoongChee.domain.image.dto.request.ImageDto;
import project.MoongChee.domain.image.service.ImageService;
import project.MoongChee.domain.image.service.S3ImageService;
import project.MoongChee.domain.post.dto.PostRequestDTO;
import project.MoongChee.domain.post.dto.PostResponseDTO;
import project.MoongChee.domain.post.dto.PostUpdateRequestDTO;
import project.MoongChee.domain.post.entity.Post;
import project.MoongChee.domain.post.entity.PostStatus;
import project.MoongChee.domain.post.exception.PostNotFoundException;
import project.MoongChee.domain.post.repository.PostRepository;
import project.MoongChee.domain.user.domain.User;
import project.MoongChee.domain.user.service.UserService;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final S3ImageService s3ImageService;

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

    @Transactional//게시물 전체 조회
    public Page<PostResponseDTO> getAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(PostResponseDTO::from);
    }
}
