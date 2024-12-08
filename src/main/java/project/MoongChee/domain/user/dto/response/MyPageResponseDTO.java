package project.MoongChee.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.MoongChee.domain.review.dto.ReviewGetResponseDTO;
import project.MoongChee.domain.user.domain.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPageResponseDTO {
    private String name;
    private String profileImageUrl;
    private Integer reviewCount;
    private double averageScore;

    public static MyPageResponseDTO from(User user, ReviewGetResponseDTO reviewGetResponseDTO) {
        String profileImageUrl = null;
        if (user.getProfileImage() != null) {
            profileImageUrl = user.getProfileImage().getUrl();
        }

        return MyPageResponseDTO.builder()
                .name(user.getName())
                .profileImageUrl(user.getProfileImage().getUrl())
                .reviewCount(reviewGetResponseDTO.getReviewCount())
                .averageScore(reviewGetResponseDTO.getAverageScore())
                .build();
    }
}
