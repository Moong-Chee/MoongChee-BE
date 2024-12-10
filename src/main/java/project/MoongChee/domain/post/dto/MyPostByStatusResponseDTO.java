package project.MoongChee.domain.post.dto;

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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPostByStatusResponseDTO {
    private Long postId;
    private String name;
    private List<String> productImageUrls;
    private Integer price;
    private String postStatus;
    private LocalDateTime createdAt;

    public static MyPostByStatusResponseDTO from(Post post) {
        List<String> productImageUrls = post.getProductImages().stream()
                .map(Image::getUrl)
                .collect(Collectors.toList());

        return MyPostByStatusResponseDTO.builder()
                .postId(post.getPostId())
                .name(post.getName())
                .productImageUrls(productImageUrls)
                .price(post.getprice())
                .postStatus(post.getPostStatus().name())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
