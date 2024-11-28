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

    @NotBlank
    private String keyword;

    @NotBlank
    private String productStatus;

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
                .createdAt(post.getCreatedAt())
                .build();
    }
}
