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
import project.MoongChee.domain.review.dto.ReviewGetResponseDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostGetDetailResponseDTO {
    private Long postId;

    @NotBlank
    private String authorName;

    private String profileImageUrl;

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
    private LocalDate date;

    @NotNull
    private Integer price;

    private LocalDateTime createdAt;

    private Integer reviewCount;

    private double averageScore;

    public static PostGetDetailResponseDTO from(Post post, ReviewGetResponseDTO ReviewResponseDTO) {
        List<String> productImageUrls = post.getProductImages().stream()
                .map(Image::getUrl)
                .collect(Collectors.toList());

        String profileImageUrl = null;
        if (post.getAuthor().getProfileImage() != null) {
            profileImageUrl = post.getAuthor().getProfileImage().getUrl();
        }

        return PostGetDetailResponseDTO.builder()
                .postId(post.getPostId())
                .authorName(post.getAuthor().getName())
                .profileImageUrl(profileImageUrl)
                .tradeType(post.getTradeType().name())
                .name(post.getName())
                .productImageUrls(productImageUrls)
                .productContent(post.getProductContent())
                .keyword(post.getKeyword().name())
                .postStatus(post.getPostStatus().name())
                .date(post.getDate())
                .price(post.getPrice())
                .createdAt(post.getCreatedAt())
                .reviewCount(ReviewResponseDTO.getReviewCount())
                .averageScore(ReviewResponseDTO.getAverageScore())
                .build();
    }
}
