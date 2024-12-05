package project.MoongChee.domain.review.dto;

import jakarta.validation.constraints.NotNull;
import project.MoongChee.domain.review.entity.ReviewScore;

public class ReviewRequestDTO {
    @NotNull ReviewScore reviewScore;
    @NotNull String reviewContent;

}
