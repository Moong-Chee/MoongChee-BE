package project.MoongChee.domain.review.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewGetResponseDTO {
    Integer reviewCount;
    Double averageScore;
    List<ReviewResponseDTO> reviews;
}
