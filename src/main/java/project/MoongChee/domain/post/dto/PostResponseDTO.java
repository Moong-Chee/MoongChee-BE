package project.MoongChee.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.MoongChee.domain.image.domain.Image;
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
    private String tradeType;

    @NotBlank
    private String name;

    private List<String> productImageUrls;

    @NotBlank
    private String productContent;

    @NotBlank
    private String keyword;

    @NotBlank
    private String postStatus;

    @NotNull
    private LocalDate returnDate;

    @NotNull
    private Integer price;

    private LocalDateTime createdAt;

    public static PostResponseDTO from(Post post) {
        List<String> productImageUrls = post.getProductImages().stream()
                .map(Image::getUrl)
                .collect(Collectors.toList());

        return PostResponseDTO.builder()
                .postId(post.getPostId())
                .authorName(post.getAuthor().getName())
                .tradeType(post.getTradeType().name())
                .name(post.getName())
                .productImageUrls(productImageUrls)
                .productContent(post.getProductContent())
                .keyword(post.getKeyword().name())
                .postStatus(post.getPostStatus().name())
                .returnDate(post.getReturnDate())
                .price(post.getPrice())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
