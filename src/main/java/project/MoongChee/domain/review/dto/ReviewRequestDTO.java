package project.MoongChee.domain.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.MoongChee.domain.review.entity.ReviewScore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDTO {
    @NotNull ReviewScore reviewScore;
    @NotNull String reviewContent;

}
