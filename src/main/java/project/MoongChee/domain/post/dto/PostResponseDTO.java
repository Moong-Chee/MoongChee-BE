package project.MoongChee.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.MoongChee.domain.post.entity.Post;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDTO {
    private Long postId;

    @NotBlank
    private String authorName;

    @NotBlank
    private String name;

    private String productImageUrl;

    @NotBlank
    private String productContent;

    @NotBlank//PostRequestDTO에서 PostKeyword keyword로 설정하고 여기서는 String 타입으로 keyword를 설정해서 스웨거에서는 string으로 보일 것입니다.
    private String keyword;

    @NotBlank
    private String productStatus;

    @NotBlank//PostRequestDTO에서 PostStatus postStatus로 설정하고 여기서는 String 타입으로 postStatus를 설정해서 스웨거에서는 string으로 보일 것입니다.
    private String postStatus;

    private LocalDateTime createdAt;

    public static PostResponseDTO from(Post post) {
        return PostResponseDTO.builder()
                .postId(post.getPostId())
                .authorName(post.getAuthor().getName())
                .name(post.getName())
                .productImageUrl(post.getProductImageUrl())
                .productContent(post.getProductContent())
                .keyword(post.getKeyword().name())
                .productStatus(post.getProductStatus())
                .postStatus(post.getPostStatus().name())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
